/*
 * Copyright (c) 2013-2014 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include "drStd.h"
#include "DrApi/DrApi.h"

#include "TlApiTplayMarshal.h"
#include "drTplayCommon.h"
#include "drTplayUtils.h"

DECLARE_STACK(drIpchStack, 4096);

/* Global variables */
threadid_t gLastIpcClient = NILTHREAD;
uint32_t   gLastPageNum   = 0;

/**
 * Performs security checks to find out output address is
 * in the range of secure protected memory block
 */
static int32_t drIpchSecurityCheck(
        phys_addr_t   physAddr,
        uint32_t length ) {
    uint32_t type = 0;

    /**
     * Update the function accordingly
     */
    if (DRAPI_OK == drApiGetPhysMemType64(&type, physAddr, length)) {
        drDbgPrintLnf("[DRM Driver] %s: physical address 0x%08x%08x is type %d.",
                      __func__, HIGHU32(physAddr), LOWU32(physAddr), type);
        switch (type) {
            case DRAPI_PHYS_MEM_TYPE_HIGH_SECURE:
            case DRAPI_PHYS_MEM_TYPE_SECURE:
                return TLAPI_DRM_OK;
            default:
                /*TOCHECK*/
                break ;
        }
    } else {
        drDbgPrintLnf("[DRM Driver] %s: ERROR: drApiGetPhysMemType(phys=0x%08x%08x,size=%d) failed.",
                      __func__, HIGHU32(physAddr), LOWU32(physAddr), length);
    }
    return E_TLAPI_DRM_REGION_NOT_SECURE;
}

/**
 * IPC handler loop. this is the function where IPC messages are handled
 */
_NORETURN void drIpchLoop( void ) {

    /* Set IPC parameters for initial MSG_RD to IPCH */
    threadid_t                  ipcClient   = NILTHREAD;
    message_t                   ipcMsg      = MSG_RD;
    uint32_t                    ipcData     = 0;
    uint32_t                    ipcMsgId    = 0;
    tlApiResult_t               tlRet       = TLAPI_OK;
    drApiResult_t               drRet       = E_DRAPI_IPC_ERROR;
    tplayMarshalingParam_ptr    pMarshal;
    tlDrmApiDrmContent_t        drmContent;
    tlDrmApiLink_t              link;
    phys_addr_t                 physOutAddr = 0;
    addr_t                      virtOutAddr;
    uint32_t                    callerRootId = 0;
    uint32_t                    callerSpId = 0;
    uint32_t                    offset;
    uint8_t                     nBlock;
    uint32_t                    j;

    /**
     * Check if there is a pending client. If there is, this is an
     * indication that IPC handler thread crashed before completing
     * the request. Respond with failure.
     */
    if (!threadid_is_null(gLastIpcClient)) {
        ipcClient = gLastIpcClient;
        ipcMsg    = MSG_RS;
        ipcData   = E_TLAPI_DRV_UNKNOWN;
    }

    // Initialize handle2Physical table with default values.
    initHandle2PhysTable();

    dbgSN("[DRM Driver] drIpchLoop(): IPC handler thread started");
    for (;;) {

        /* Reset last IPC client */
        gLastIpcClient = NILTHREAD;

        /*
         * When called first time sends ready message to IPC server and
         * then waits for IPC requests
         */
        if (E_OK != drApiIpcCallToIPCH(&ipcClient, &ipcMsg, &ipcData)) {
            dbgSN("[DRM Driver] drIpchLoop(): drApiIpcCallToIPCH failed");
            continue;
        }

        /* Update last IPC client */
        gLastIpcClient = ipcClient;

        ipcMsgId=drApiExtractMsgCmd(ipcMsg);

        /* Dispatch request */
        switch (ipcMsgId) {
            case MSG_CLOSE_TRUSTLET:
                /**
                 * Trusted Application close message
                 */
                ipcMsg = MSG_CLOSE_TRUSTLET_ACK;
                ipcData = TLAPI_OK;
                break;
            case MSG_CLOSE_DRIVER:
                /**
                 * Driver close message
                 */
                ipcMsg = MSG_CLOSE_DRIVER_ACK;
                ipcData = TLAPI_OK;
                break;
            case MSG_GET_DRIVER_VERSION:
                /**
                 * Driver version message
                 */
                ipcMsg = (message_t) TLAPI_OK;
                ipcData = DRIVER_VERSION  ;
                break;
            case MSG_RQ:
                {
                    ipcMsg  = MSG_RS; // send response message...
                    ipcData = E_TLAPI_DRV_INVALID_PARAMETERS;
                }
                break;

            case MSG_RQ_EX:

                /* init tlRet value */
                tlRet = E_TLAPI_DRM_INTERNAL;

                if ((drApiExtractMsgLen(ipcMsg))<sizeof(reqPayloadHeader_t)){
                      ipcMsg  = MSG_RS; // send response message...
                      ipcData = E_TLAPI_DRV_INVALID_PARAMETERS;
                      break;
                  }

                /* Before proceeding further, check if the caller is system Trusted Application */
                if (E_OK != drApiGetClientRootAndSpId(
                        &callerRootId,
                        &callerSpId,
                        ipcClient)) {
                    dbgSN("[DRM Driver] drIpchLoop(): drApiGetClientRootAndSpId! failed");
                    continue ;
                }

                /**
                 * Handle incoming IPC requests via TL API.
                 * Map the caller Trusted Application to access to the marshaling data
                 */
                drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                                            (addr_t )ipcData,
                                            sizeof(tplayMarshalingParam_t),
                                            MAP_READABLE|MAP_WRITABLE,
                                            (void **)&pMarshal);
                if (drRet != DRAPI_OK) {
                    continue ;
                }

                if ((callerRootId != ROOTID_SYSTEM) && (callerSpId != SPID_SYSTEM)) {
                    dbgSN("[DRM Driver] drIpchLoop(): Called not a system Trusted Application!");
                    pMarshal->header.retVal = E_TLAPI_DRM_PERMISSION_DENIED;
                    break;
                }

                /* Process the request */
                switch (pMarshal->header.functionId) {

                    case FID_DR_PROCESS_DRM_CONTENT:
                        /**
                         * Handle secure cipher operation
                         */

                        drmContent.sHandle                     = pMarshal->payload.drmContent.sHandle;
                        drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                                                    (addr_t )pMarshal->payload.drmContent.decryptCtx.key,
                                                    pMarshal->payload.drmContent.decryptCtx.keylen,
                                                    MAP_READABLE|MAP_ALLOW_NONSECURE,
                                                    (void **)&drmContent.decryptCtx.key);
                        if (drRet != DRAPI_OK) {
                            break;
                        }
                        drmContent.decryptCtx.keylen           = pMarshal->payload.drmContent.decryptCtx.keylen;
                        drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                                                    (addr_t )pMarshal->payload.drmContent.decryptCtx.iv,
                                                    pMarshal->payload.drmContent.decryptCtx.ivlen,
                                                    MAP_READABLE|MAP_ALLOW_NONSECURE,
                                                    (void **)&drmContent.decryptCtx.iv);
                        if (drRet != DRAPI_OK) {
                            break;
                        }
                        drmContent.decryptCtx.ivlen            = pMarshal->payload.drmContent.decryptCtx.ivlen;
                        drmContent.decryptCtx.alg              = pMarshal->payload.drmContent.decryptCtx.alg;
                        drmContent.decryptCtx.outputoffet      = pMarshal->payload.drmContent.decryptCtx.outputoffet;
                        drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                                                    (addr_t )pMarshal->payload.drmContent.input,
                                                    pMarshal->payload.drmContent.inputDesc.nTotalSize,
                                                    MAP_READABLE|MAP_ALLOW_NONSECURE,
                                                    (void **)&drmContent.input );
                        if (drRet != DRAPI_OK) {
                            break;
                        }
                        drmContent.inputDesc.nTotalSize        = pMarshal->payload.drmContent.inputDesc.nTotalSize;
                        drmContent.inputDesc.nNumBlocks        = pMarshal->payload.drmContent.inputDesc.nNumBlocks;

                        for(nBlock = 0, offset = 0; nBlock < TLAPI_DRM_INPUT_PAIR_NUMBER; nBlock++) {
                            drmContent.inputDesc.aPairs[nBlock].nSize   = pMarshal->payload.drmContent.inputDesc.aPairs[nBlock].nSize;
                            drmContent.inputDesc.aPairs[nBlock].nOffset = pMarshal->payload.drmContent.inputDesc.aPairs[nBlock].nOffset;
                            if (nBlock < drmContent.inputDesc.nNumBlocks) {
                                /* Check input overflow */
                                if (drmContent.inputDesc.aPairs[nBlock].nOffset >= drmContent.inputDesc.nTotalSize ||
                                        drmContent.inputDesc.aPairs[nBlock].nSize > (int32_t)(drmContent.inputDesc.nTotalSize
                                                                               - drmContent.inputDesc.aPairs[nBlock].nOffset)) {
                                    break ;
                                }
                                /* Check blocks are ordered and are not overlapping */
                                if (drmContent.inputDesc.aPairs[nBlock].nOffset < offset) {
                                    break ;
                                }
                                offset += drmContent.inputDesc.aPairs[nBlock].nSize;
                            }
                        }
                        if (nBlock != TLAPI_DRM_INPUT_PAIR_NUMBER) {
                            dbgSN("[DRM Driver] drIpchLoop(): ERROR - Invalid parameters received (inputDesc overflow/ordering/overlap).");
                            pMarshal->header.retVal = E_TLAPI_DRM_INVALID_PARAMS;
                            break ;
                        }

                        drmContent.processMode                 = pMarshal->payload.drmContent.processMode;
                        //drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                        //                            (addr_t )pMarshal->payload.drmContent.output,
                        //                            pMarshal->payload.drmContent.decryptCtx.ivlen,
                        //                            MAP_READABLE|MAP_WRITABLE|MAP_ALLOW_NONSECURE,
                        //                            (void **)&drmContent.output );
                        //if (drRet != DRAPI_OK) {
                        //    break;
                        //}

                        /**
                         * Segment must not exceed DR_MAX_INPUT_LENGTH. To be Check the DR_MAX_INPUT_LENGTH
                         */

                        if ((!drmContent.input) ||
                                (!drmContent.decryptCtx.key) ||
                                (!drmContent.decryptCtx.iv) ||
                                (drmContent.decryptCtx.ivlen == 0) ||
                                (drmContent.inputDesc.nTotalSize == 0) ||
                                (drmContent.inputDesc.nNumBlocks == 0) ||
                                (drmContent.inputDesc.nNumBlocks > TLAPI_DRM_INPUT_PAIR_NUMBER)) {
                            dbgSN("[DRM Driver] drIpchLoop(): ERROR - Invalid parameters received.");
                            pMarshal->header.retVal = E_TLAPI_DRM_INVALID_PARAMS;
                            break ;
                        }
                        /**
                         * Initialize crypto hardware if not already done.
                         */

                        /*
                         * MAP Output buffer to driver virtual address space.
                         */
                        addr_t bufVirtAddr;
                        if (DRAPI_OK != drMapAndGetOutputAddress(&bufVirtAddr, &virtOutAddr, &physOutAddr)) {
                            dbgSN("[DRM Driver] drIpchLoop(): ERROR - Bad output mapping");
                            pMarshal->header.retVal = E_TLAPI_DRM_MAP;
                            break;
                        }
                        virtOutAddr = (addr_t)((word_t)virtOutAddr + drmContent.decryptCtx.outputoffet);

                        /**
                         * Do security check. This is to ensure that
                         * Output buffer is within the range of protected memory.
                         * Protected memory region is really protected.
                         */
                        if (TLAPI_DRM_OK != drIpchSecurityCheck(physOutAddr, drmContent.inputDesc.nTotalSize
                                                                             + drmContent.decryptCtx.outputoffet)) {
                            dbgSN("[DRM Driver] drIpchLoop(): ERROR - Security check failed");
                            pMarshal->header.retVal = E_TLAPI_DRM_PERMISSION_DENIED;
                            //break;
                        }

                        /**
                         * Check the HDCP link if necessary.
                         */

                        /**
                         * Decrypt the data according to the input parameters passed in.
                         * and set return value accordingly E.g pMarshal->header.retVal = DR_E_OK
                         */

                        drDbgPrintf("[DRM Driver] drIpchLoop(): ready for decrypt. input=%p, totalSize=%u, blocks=%u"
                                    ", physOut=0x%08x%08x, physOutOffset=0x%08x, virtOut=0x%08x\n",
                                    drmContent.input, drmContent.inputDesc.nTotalSize, drmContent.inputDesc.nNumBlocks,
                                    HIGHU32(physOutAddr), LOWU32(physOutAddr), drmContent.decryptCtx.outputoffet, virtOutAddr);

                        /*
                         * SAMPLE: process clear data
                         * In this sample clear data is inserted between encrypted blocks as given in InputDesc.
                         * Previous check has ensured that blocks are ordered and are not overlapping.
                         * This can be done another way according needs. */
                        nBlock = 0;
                        offset = 0;
                        do {
                            int nextBlockOff = (nBlock < drmContent.inputDesc.nNumBlocks
                                                ? drmContent.inputDesc.aPairs[nBlock].nOffset
                                                : drmContent.inputDesc.nTotalSize);
                            //process clear data preceding next block (or clear data following last block)
                            for ( ; offset < nextBlockOff; offset++) {
                                if (virtOutAddr) *((uint8_t*)((word_t)virtOutAddr+offset)) = drmContent.input[offset];
                            }
                            if (nBlock >= drmContent.inputDesc.nNumBlocks) {
                                break ;
                            }
                            offset += drmContent.inputDesc.aPairs[nBlock].nSize;
                            nBlock++;
                        } while (offset < drmContent.inputDesc.nTotalSize);
                        /*
                         * SAMPLE: process each encrypted frame
                         * ___JUST FOR TEST PURPOSE___, we display the 16 first bytes of each frame/block.
                         */
                        for(nBlock = 0, offset = 0; nBlock < drmContent.inputDesc.nNumBlocks; nBlock++) {
                            drDbgPrintf("[DRM Driver] drIpchLoop(): + block #%u, offset=%u, size=%u, value: ",
                                        nBlock, drmContent.inputDesc.aPairs[nBlock].nOffset, drmContent.inputDesc.aPairs[nBlock].nSize);
                            /*
                             * SAMPLE: DUMMY decrypt and display 16 first bytes
                             *
                             * ___JUST FOR TEST PURPOSE__ and in alignment with TlcPlayTci, the dummy "encryption"
                             * done by TLC is removed and the "decrypted" content is displayed.
                             * -> data[i] = data[i] - key[i%key_size] + iv[i%iv_size]
                             *
                             */
                            for (j=0; j < drmContent.inputDesc.aPairs[nBlock].nSize; j++) {
                                uint8_t c;
                                offset = drmContent.inputDesc.aPairs[nBlock].nOffset+j;
                                if (drmContent.processMode == TLAPI_DRM_PROCESS_ENCRYPTED_DATA) {
                                    c =  (uint8_t)(drmContent.input[offset]
                                                                    - drmContent.decryptCtx.key[offset % drmContent.decryptCtx.keylen]
                                                                    + drmContent.decryptCtx.iv[offset % drmContent.decryptCtx.ivlen]);
                                } else {
                                    c = (uint8_t)(drmContent.input[offset]);
                                }
                                if (j < 16) {
                                    drDbgPrintf("%x ", c);
                                }
                                if (virtOutAddr) *((uint8_t*)((word_t)virtOutAddr+offset)) = c;
                            }
                            drDbgPrintf("...\n");
                        }

                        /* UnMap Output Buffer */
                        if (bufVirtAddr != NULL) drUnMap(bufVirtAddr);

                        tlRet = E_TLAPI_DRM_DRIVER_NOT_IMPLEMENTED;
                        dbgSN("[DRM Driver] : FID_DR_PROCESS_DRM_CONTENT - DRIVER_NOT_IMPLEMENTED");
                        break;

                    case FID_DR_OPEN_SESSION: {
                        /**
                         * Load and authenticate video firmware if required.
                         */

                        /**
                         * Initialize security features, such as protected buffers.
                         */

                        /**
                         *  assign a new session ID
                         */
                        uint8_t sHandle = 1;
                        uint8_t *psHandle;
                        drRet = drApiMapTaskBuffer( THREADID_TO_TASKID(ipcClient),
                                                    (addr_t )pMarshal->payload.returned_sHandle,
                                                    sizeof(uint8_t),
                                                    MAP_READABLE|MAP_WRITABLE|MAP_ALLOW_NONSECURE,
                                                    (void **)&psHandle);
                        if (drRet != DRAPI_OK) {
                            break;
                        }
                        *psHandle = sHandle;

                        /**
                         * Set return value accordingly. E.g pMarshal->header.retVal = DR_E_OK
                         */

                        tlRet = E_TLAPI_DRM_DRIVER_NOT_IMPLEMENTED;
                        dbgSN("[DRM Driver] : FID_DR_OPEN_SESSION - DRIVER_NOT_IMPLEMENTED");
                        break;
                    }
                    case FID_DR_CLOSE_SESSION: {
                        /**
                         *  close respective session
                         */
                        drDbgPrintLnf("[DRM Driver] : FID_DR_CLOSE_SESSION - handle to close 0x%08x", pMarshal->payload.sHandle_to_close);

                        /**
                         * Cleanup buffers used in previous session, i.e. fill with 0x00
                         */

                        /**
                         * Disable any security features that are no longer required
                         */

                        /**
                         * Set return value accordingly. E.g pMarshal->header.retVal = DR_E_OK
                         */

                        tlRet = E_TLAPI_DRM_DRIVER_NOT_IMPLEMENTED;
                        dbgSN("[DRM Driver] IPC - FID_DR_CLOSE_SESSION - DRIVER_NOT_IMPLEMENTED");
                        break;
                    }
                    case FID_DR_CHECK_LINK:
                        /**
                         * Check the external link (HDCPv1, HDCPv2, AirPlay, and DTCP) status
                         */
                        link = pMarshal->payload.link;
                        tlRet = E_TLAPI_DRM_DRIVER_NOT_IMPLEMENTED;

                        switch(link.link) {
                            case TLAPI_DRM_LINK_HDCP_1:
                                dbgSN("[DRM Driver] IPC - FID_DR_CHECK_LINK : HDCPv1   - DRIVER_NOT_IMPLEMENTED");
                                break;
                            case TLAPI_DRM_LINK_HDCP_2:
                                dbgSN("[DRM Driver] IPC - FID_DR_CHECK_LINK : HDCPv2   - DRIVER_NOT_IMPLEMENTED");
                                break;
                            case TLAPI_DRM_LINK_AIRPLAY:
                                dbgSN("[DRM Driver] IPC - FID_DR_CHECK_LINK : AIR PLAY - DRIVER_NOT_IMPLEMENTED");
                                break;
                            case TLAPI_DRM_LINK_DTCP:
                                dbgSN("[DRM Driver] IPC - FID_DR_CHECK_LINK : DTCP     - DRIVER_NOT_IMPLEMENTED");
                                break;
                            default:
                                dbgSN("[DRM Driver] IPC - FID_DR_CHECK_LINK : UNKNOWN  - DRIVER_NOT_IMPLEMENTED");
                                break;
                        } /* !switch(link.link) */
                        break;

                    default:
                        /* Unknown message has been received*/
                        tlRet = E_TLAPI_DRM_INVALID_COMMAND;
                        dbgSN("[DRM Driver] IPC - TL_DRM_E_INVALID_COMMAND");
                        break;

                } /* ! switch (pMarshal->header.functionId) */

                /* Update response data */
                ipcMsg  = MSG_RS;
                ipcData =  TLAPI_OK;
                pMarshal->header.retVal = tlRet;

                /* Unmap all buffers for this client task */
                drApiUnmapTaskBuffers(THREADID_TO_TASKID(ipcClient));
                break;
           default:
               drApiIpcUnknownMessage(&ipcClient, &ipcMsg, &ipcData);
               /* Unknown message has been received*/
               ipcMsg = (message_t) E_TLAPI_DRV_UNKNOWN;
               ipcData = 0;
               break;
        } /* !switch (ipcMsg) */

    } /* !for(;;) */
}


//------------------------------------------------------------------------------
_THREAD_ENTRY void drIpch( void )
{
    drIpchLoop();
}


//------------------------------------------------------------------------------
void drIpchInit( void )
{
    /* ensure thread stack is clean */
    clearStack(drIpchStack);

    /**
     * Start IPC handler thread. Exception handler thread becomes local
     * exception handler of IPC handler thread
     */
    if (E_OK != drApiStartThread(
            DRIVER_THREAD_NO_IPCH,
            FUNC_PTR(drIpch),
            getStackTop(drIpchStack),
            IPCH_PRIORITY,
            DRIVER_THREAD_NO_EXCH))
    {
        dbgSN("[DRM Driver] drIpchInit(): drApiStartThread failed");
    }
}

