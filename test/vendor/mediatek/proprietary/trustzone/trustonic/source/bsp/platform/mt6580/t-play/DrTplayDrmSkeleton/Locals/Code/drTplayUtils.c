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

#include "drTplayCommon.h"
#include "drTplayUtils.h"


/**
 * Exchange registers of current thread or another thread.
 *
 * @param  threadNo   Thread no
 * @param  ip         ip value to be set
 * @param  sp         sp value to be set
 *
 * @retval E_OK or relevant error code.
 */
drApiResult_t drUtilsRestartThread(
        threadno_t threadNo,
        addr_t ip,
        addr_t sp )
{
    drApiResult_t ret  = E_INVALID;
    uint32_t      ctrl = THREAD_EX_REGS_IP | THREAD_EX_REGS_SP;

    /* Set ip and sp registers */
    ret = drApiThreadExRegs(threadNo,
                            ctrl,
                            ip,
                            sp);
    if (ret != E_OK)
    {
        return ret;
    }

    /* Resume thread */
    ret = drApiResumeThread(threadNo);

    return ret;
}


/**
 * Makes request to RTM for updating notification thread
 *
 * @param  threadNo   Thread no
 *
 * @retval E_OK or relevant error code.
 */
drApiResult_t drUtilsUpdateNotificationThread( threadno_t threadNo )
{
    drApiResult_t ret =  E_INVALID;
    threadid_t  notificationThread;

    /* Retrieve task id */
    taskid_t task = drApiGetTaskid();
    if (task == NILTASK)
    {
        return ret;
    }

    /* Retrieve thread id based on task id and thread no */
    notificationThread = drApiTaskidGetThreadid(task, threadNo);

    /* IPC data for setting notification handler */
    threadid_t  ipcClient = notificationThread;
    message_t   ipcMsg    = MSG_SET_NOTIFICATION_HANDLER;
    uint32_t    ipcData   = 0;

    /* Make IPC call to IPCH and wait for a response */
    ret = drApiIpcCallToIPCH(&ipcClient, &ipcMsg, &ipcData);

    return ret;
}


/* This is the Handle allowing to choose the Output
 * physical address in processDrmContent
 * By default the Handle is a variable defined here,
 * but its physical address can be changed via DCI interface */
int32_t gOutputHandle = 0;
phys_addr_t gOutputHandlePhysAddr = 0;

/* This is the Table making correspondency between Output Handles
 * and Output physical addresses used in processDrmContent.
 * By default the Table is an array defined here,
 * but its physical address and its size can be changed via DCI interface */
phys_addr_t gHandle2PhysTablePhysAddr = 0;
uint32_t gHandle2PhysTableSize = 0;

#ifdef DRM_HARDCODED_MAP
/**
 * TODO : comment
 * For test purpose and for portability, we emulate physical
 * secured memory in a static buffer.
 * handle2phys table will be updated with drApiVirt2Phys.
 */
#define DRM_FAKE_OUTPUT_BUFFER1_SIZE 0x1000
#define DRM_FAKE_OUTPUT_BUFFER2_SIZE 0xA000
#define DRM_FAKE_OUTPUT_BUFFER3_SIZE 0x0E89
#define DRM_FAKE_OUTPUT_BUFFER4_SIZE 0x0100
#define DRM_FAKE_OUTPUT_BUFFER5_SIZE 0x0040
#define DRM_FAKE_OUTPUT_BUFFER_SIZE (DRM_FAKE_OUTPUT_BUFFER1_SIZE \
                                    +DRM_FAKE_OUTPUT_BUFFER2_SIZE \
                                    +DRM_FAKE_OUTPUT_BUFFER3_SIZE \
                                    +DRM_FAKE_OUTPUT_BUFFER4_SIZE \
                                    +DRM_FAKE_OUTPUT_BUFFER5_SIZE)
static uint8_t _drmFakeOutputBuffer[DRM_FAKE_OUTPUT_BUFFER_SIZE];

/**
 * TODO : comment
 * For test purpose and for portability, we emulate the handle2physical table in a static buffer
 * physical addresses are fake here and will be updated with initHandle2PhysTable(drApiVirt2Phys)
 */
#define DRM_HANDLE_TO_PHYS_TABLE_SIZE 5
static handleToPhys_t gHandle2PhysTable[DRM_HANDLE_TO_PHYS_TABLE_SIZE] = {
        { 0x00000000, /*fake*/ 0x00FF0010, DRM_FAKE_OUTPUT_BUFFER1_SIZE },
        { 0x00000001, /*fake*/ 0x00FF1010, DRM_FAKE_OUTPUT_BUFFER2_SIZE },
        { 0x00000002, /*fake*/ 0x00FF2010, DRM_FAKE_OUTPUT_BUFFER3_SIZE },
        { 0x00000003, /*fake*/ 0x00FF3010, DRM_FAKE_OUTPUT_BUFFER4_SIZE },
        { 0x00000004, /*fake*/ 0x00FF4010, DRM_FAKE_OUTPUT_BUFFER5_SIZE }
};
/**
 * TODO : comment
 * Just for test the emulated physical memory and emulated handle2phys Table are
 * linked together below.
 */
drApiResult_t initHandle2PhysTable(void) {
    taskid_t taskid;
    int i;
    int offset;
    int size;
    phys_addr_t phys = 0;

    taskid = drApiGetTaskid();
    //Initialize gOutputHandle physical address
    if (DRAPI_OK != drApiVirt2Phys64(taskid, &gOutputHandle, &gOutputHandlePhysAddr)) {
        drDbgPrintLnf("[DRM Driver] %s: ERROR: could not get phys address for gOutputHandle.", __func__);
    } else {
        drDbgPrintLnf("[DRM Driver] %s: gOutputHandle physical address is 0x%012x.", __func__, gOutputHandlePhysAddr);
    }
    //Initialize gHandle2PhysTable physical address
    if (DRAPI_OK != drApiVirt2Phys64(taskid, gHandle2PhysTable, &gHandle2PhysTablePhysAddr)) {
        drDbgPrintLnf("[DRM Driver] %s: ERROR: could not get phys address for gHandle2PhysTable.", __func__);
    } else {
        drDbgPrintLnf("[DRM Driver] %s: gHandle2PhysTable physical address is 0x%012x.", __func__, gHandle2PhysTablePhysAddr);
    }
    //Fill the gHandle2PhysTable with physical addresses of emulated secured memory (_drmFakeOutputBuffer)
    gHandle2PhysTableSize = DRM_HANDLE_TO_PHYS_TABLE_SIZE;
    for (i = 0, offset = 0; i < gHandle2PhysTableSize; i++) {
        size = gHandle2PhysTable[i].size;
        if (offset + size > DRM_FAKE_OUTPUT_BUFFER_SIZE) {
            drDbgPrintLnf("[DRM Driver] %s: ERROR: _drmFakeOutputBuffer too small.", __func__);
            return E_DRAPI_CANNOT_MAP;
        }
        if (DRAPI_OK == drApiVirt2Phys64(taskid, (addr_t)(_drmFakeOutputBuffer + offset) , &phys)) {
            gHandle2PhysTable[i].phys_addr = phys;
        } else {
            drDbgPrintLnf("[DRM Driver] %s: ERROR: could not get phys address (#%d).", __func__, i);
        }
        offset += size;
    }
    return DRAPI_OK;
}
#else
drApiResult_t initHandle2PhysTable() {
    return DRAPI_OK;
}
#endif

drApiResult_t drUnMap(addr_t bufVirtAddr) {
    drApiResult_t ret = DRAPI_OK;

    ret = drApiUnmapBuffer(bufVirtAddr);
    if (DRAPI_OK != ret) {
        drDbgPrintLnf("[DRM Driver] %s: ERROR(0x%x): could not unmap.", __func__, ret);
    }

    return ret;
}

drApiResult_t drMap(phys_addr_t physAddr, uint32_t len, addr_t *bufVirtAddr, addr_t *targetVirtAddr) {
    uint32_t offset;
    phys_addr_t physpage;
    drApiResult_t drApiResult;
    uint32_t sectionSize = SIZE_4KB;

    /* get page and align size */
    physpage = _getPageStart64(physAddr, sectionSize);
    offset = _getOffsetIntoPage64(physAddr, sectionSize);
    len = (((len + offset) & SIZE_TO_MASK(sectionSize)));
    if (len == 0)
        len += sectionSize;



    drDbgPrintLnf("[DRM Driver] %s: mapping page phys=0x%08x%08x, mapsize=0x%08x, offset=0x%08x\n",
                  __func__, HIGHU32(physpage), LOWU32(physpage), (int32_t)len, (int32_t)offset);

    /* Map physical memory */
    drApiResult = drApiMapPhysicalBuffer(physpage, len, MAP_READABLE | MAP_WRITABLE /*| MAP_UNCACHED*/ /* | MAP_IO */, (void **)bufVirtAddr);
    if (DRAPI_OK == drApiResult) {
        /* retrieve pointer to handle in mapped memory */
        *targetVirtAddr = PTR_OFFS(*bufVirtAddr, offset);
    }

    return drApiResult;
}

drApiResult_t drMapAndGetOutputAddress(addr_t *bufVirtAddr, addr_t *targetVirtAddr, phys_addr_t *pPhysAddr) {
    phys_addr_t physaddr;
    phys_addr_t value;
    uint32_t u32value;
    int i;
    addr_t maddr;
    uint32_t size;
    drApiResult_t drApiResult;
    handleToPhys_t *handle2Phys;

    /* Map gOutputHandle Physical Address to available space */
    physaddr = gOutputHandlePhysAddr;
    addr_t localBufVirtAddr;
    drApiResult = drMap(physaddr, sizeof(phys_addr_t), &localBufVirtAddr, &maddr);
    if (DRAPI_OK != drApiResult) {
        drDbgPrintLnf("[DRM Driver] %s: 1. ERROR: cannot map gOutputHandlePhysAddr 0x%08x%08x", __func__, HIGHU32(physaddr), LOWU32(physaddr));
        return drApiResult;
    }
    drDbgPrintLnf("[DRM Driver] %s: 1. handle phys=0x%08x%08x mapped. value=0x%08x\n", __func__, HIGHU32(physaddr), LOWU32(physaddr), *((int32_t*)maddr));

    // NOW GET HANDLE VALUE
    value = *((phys_addr_t*)maddr);
    u32value = (uint32_t) value;

    //Unmap
    drApiResult = drUnMap(localBufVirtAddr);
    if (DRAPI_OK != drApiResult) {
        return drApiResult;
    }

#ifdef DRM_USE_HANDLE2PHYS_TABLE
    /* Here the Handle2Physical table is used.
     * The Handle got previously in physaddr will be searched in the table.
     */
    drApiResult = drMap(gHandle2PhysTablePhysAddr, gHandle2PhysTableSize*sizeof(handleToPhys_t), &localBufVirtAddr, &maddr);
    if (DRAPI_OK != drApiResult) {
        drDbgPrintLnf("[DRM Driver] %s: 1b. ERROR(0x%x): cannot map gHandle2PhysTablePhysAddr: 0x%08x%08x",
                      __func__, drApiResult, HIGHU32(gHandle2PhysTablePhysAddr), LOWU32(gHandle2PhysTablePhysAddr));
        return drApiResult;
    }
    handle2Phys = (handleToPhys_t*)maddr;
    for(i=0; i < gHandle2PhysTableSize; i++) {
        if (handle2Phys[i].handle == u32value) {
            physaddr = handle2Phys[i].phys_addr;
            u32value = handle2Phys[i].size;
            drDbgPrintLnf("[DRM Driver] %s: 1c. handle 0x%08x found. phys_addr=0x%08x%08x, size=0x%08x\n",
                          __func__, handle2Phys[i].handle, HIGHU32(physaddr), LOWU32(physaddr), handle2Phys[i].size);
            break ;
        }
    }
    drApiResult = drUnMap(localBufVirtAddr);
    if (DRAPI_OK != drApiResult) {
        return drApiResult;
    }
    if (i == DRM_HANDLE_TO_PHYS_TABLE_SIZE) {
        // Handle not found in table. abort.
        drDbgPrintLnf("[DRM Driver] %s: 1c. ERROR: handle 0x%08x not found.\n", __func__, u32value);
        return E_DRAPI_CANNOT_MAP;
    }
    size = u32value;
#else
    /* For Test purpose, the "physaddr" got previously is a physical address
     * It will be mapped in driver adress space without treatment.
     * NOTE: Just for testing purpose: revealing output buffer physical address is not a good thing.
     */
    physaddr = value;
    size = SIZE_4KB; //arbitrary non working value
#endif

    /* Map physical memory to available space */
    drApiResult = drMap(physaddr, size, &localBufVirtAddr, &maddr);
    if (DRAPI_OK != drApiResult) {
        drDbgPrintLnf("[DRM Driver] %s: 2. ERROR(0x%x): cannot map outputBuffer 0x%08x%08x\n",
                      __func__, drApiResult, HIGHU32(physaddr), LOWU32(physaddr));
        return drApiResult;
    }
    /* get mapped output buffer pointer */
    drDbgPrintLnf("[DRM Driver] %s: 2. outputPhys 0x%08x%08x mapped at 0x%08x. value=%08x\n",
                  __func__, HIGHU32(physaddr), LOWU32(physaddr), (int32_t)maddr, *((int32_t*)maddr));
    *bufVirtAddr = localBufVirtAddr;
    *targetVirtAddr = maddr;
    *pPhysAddr = physaddr;
    return DRAPI_OK;
}

