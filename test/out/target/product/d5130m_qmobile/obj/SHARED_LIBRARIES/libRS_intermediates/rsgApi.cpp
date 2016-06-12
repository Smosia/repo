/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "rsDevice.h"
#include "rsContext.h"
#include "rsThreadIO.h"
#include "rsgApiStructs.h"
#include "rsgApiFuncDecl.h"
#include "rsFifo.h"

using namespace android;
using namespace android::renderscript;


typedef struct RsApiEntrypoints {
    void (* ContextDestroy) (RsContext rsc);
    RsMessageToClientType (* ContextGetMessage) (RsContext rsc, void * data, size_t data_length, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length);
    RsMessageToClientType (* ContextPeekMessage) (RsContext rsc, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length);
    void (* ContextSendMessage) (RsContext rsc, uint32_t id, const uint8_t * data, size_t data_length);
    void (* ContextInitToClient) (RsContext rsc);
    void (* ContextDeinitToClient) (RsContext rsc);
    void (* ContextSetCacheDir) (RsContext rsc, const char * cacheDir, size_t cacheDir_length);
    RsType (* TypeCreate) (RsContext rsc, RsElement e, uint32_t dimX, uint32_t dimY, uint32_t dimZ, bool mipmaps, bool faces, uint32_t yuv);
    RsType (* TypeCreate2) (RsContext rsc, const RsTypeCreateParams * dat, size_t dat_length);
    RsAllocation (* AllocationCreateTyped) (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, uint32_t usages, uintptr_t ptr);
    RsAllocation (* AllocationCreateFromBitmap) (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages);
    RsAllocation (* AllocationCubeCreateFromBitmap) (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages);
    RsNativeWindow (* AllocationGetSurface) (RsContext rsc, RsAllocation alloc);
    void (* AllocationSetSurface) (RsContext rsc, RsAllocation alloc, RsNativeWindow sur);
    RsAllocation (* AllocationAdapterCreate) (RsContext rsc, RsType vtype, RsAllocation baseAlloc);
    void (* AllocationAdapterOffset) (RsContext rsc, RsAllocation alloc, const uint32_t * offsets, size_t offsets_length);
    void (* ContextFinish) (RsContext rsc);
    void (* ContextDump) (RsContext rsc, int32_t bits);
    void (* ContextSetPriority) (RsContext rsc, int32_t priority);
    void (* ContextDestroyWorker) (RsContext rsc);
    void (* AssignName) (RsContext rsc, RsObjectBase obj, const char * name, size_t name_length);
    void (* ObjDestroy) (RsContext rsc, RsAsyncVoidPtr objPtr);
    RsElement (* ElementCreate) (RsContext rsc, RsDataType mType, RsDataKind mKind, bool mNormalized, uint32_t mVectorSize);
    RsElement (* ElementCreate2) (RsContext rsc, const RsElement * elements, size_t elements_length, const char ** names, size_t names_length_length, const size_t * names_length, const uint32_t * arraySize, size_t arraySize_length);
    void (* AllocationCopyToBitmap) (RsContext rsc, RsAllocation alloc, void * data, size_t data_length);
    void * (* AllocationGetPointer) (RsContext rsc, RsAllocation va, uint32_t lod, RsAllocationCubemapFace face, uint32_t z, uint32_t array, size_t * stride, size_t stride_length);
    void (* Allocation1DData) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, const void * data, size_t data_length);
    void (* Allocation1DElementData) (RsContext rsc, RsAllocation va, uint32_t x, uint32_t lod, const void * data, size_t data_length, size_t comp_offset);
    void (* AllocationElementData) (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, const void * data, size_t data_length, size_t comp_offset);
    void (* Allocation2DData) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, const void * data, size_t data_length, size_t stride);
    void (* Allocation3DData) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, const void * data, size_t data_length, size_t stride);
    void (* AllocationGenerateMipmaps) (RsContext rsc, RsAllocation va);
    void (* AllocationRead) (RsContext rsc, RsAllocation va, void * data, size_t data_length);
    void (* Allocation1DRead) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, void * data, size_t data_length);
    void (* AllocationElementRead) (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, void * data, size_t data_length, size_t comp_offset);
    void (* Allocation2DRead) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, void * data, size_t data_length, size_t stride);
    void (* Allocation3DRead) (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, void * data, size_t data_length, size_t stride);
    void (* AllocationSyncAll) (RsContext rsc, RsAllocation va, RsAllocationUsageType src);
    void (* AllocationResize1D) (RsContext rsc, RsAllocation va, uint32_t dimX);
    void (* AllocationCopy2DRange) (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destMip, uint32_t destFace, uint32_t width, uint32_t height, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcMip, uint32_t srcFace);
    void (* AllocationCopy3DRange) (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destZoff, uint32_t destMip, uint32_t width, uint32_t height, uint32_t depth, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcZoff, uint32_t srcMip);
    RsClosure (* ClosureCreate) (RsContext rsc, RsScriptKernelID kernelID, RsAllocation returnValue, RsScriptFieldID * fieldIDs, size_t fieldIDs_length, uintptr_t * values, size_t values_length, int * sizes, size_t sizes_length, RsClosure * depClosures, size_t depClosures_length, RsScriptFieldID * depFieldIDs, size_t depFieldIDs_length);
    RsClosure (* InvokeClosureCreate) (RsContext rsc, RsScriptInvokeID invokeID, const void * params, size_t params_length, const RsScriptFieldID * fieldIDs, size_t fieldIDs_length, const uintptr_t * values, size_t values_length, const int * sizes, size_t sizes_length);
    void (* ClosureSetArg) (RsContext rsc, RsClosure closureID, uint32_t index, uintptr_t value, size_t valueSize);
    void (* ClosureSetGlobal) (RsContext rsc, RsClosure closureID, RsScriptFieldID fieldID, uintptr_t value, size_t valueSize);
    RsSampler (* SamplerCreate) (RsContext rsc, RsSamplerValue magFilter, RsSamplerValue minFilter, RsSamplerValue wrapS, RsSamplerValue wrapT, RsSamplerValue wrapR, float mAniso);
    void (* ScriptBindAllocation) (RsContext rsc, RsScript vtm, RsAllocation va, uint32_t slot);
    void (* ScriptSetTimeZone) (RsContext rsc, RsScript s, const char * timeZone, size_t timeZone_length);
    RsScriptInvokeID (* ScriptInvokeIDCreate) (RsContext rsc, RsScript s, uint32_t slot);
    void (* ScriptInvoke) (RsContext rsc, RsScript s, uint32_t slot);
    void (* ScriptInvokeV) (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length);
    void (* ScriptForEach) (RsContext rsc, RsScript s, uint32_t slot, RsAllocation ain, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length);
    void (* ScriptForEachMulti) (RsContext rsc, RsScript s, uint32_t slot, RsAllocation * ains, size_t ains_length, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length);
    void (* ScriptSetVarI) (RsContext rsc, RsScript s, uint32_t slot, int value);
    void (* ScriptSetVarObj) (RsContext rsc, RsScript s, uint32_t slot, RsObjectBase value);
    void (* ScriptSetVarJ) (RsContext rsc, RsScript s, uint32_t slot, int64_t value);
    void (* ScriptSetVarF) (RsContext rsc, RsScript s, uint32_t slot, float value);
    void (* ScriptSetVarD) (RsContext rsc, RsScript s, uint32_t slot, double value);
    void (* ScriptSetVarV) (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length);
    void (* ScriptGetVarV) (RsContext rsc, RsScript s, uint32_t slot, void * data, size_t data_length);
    void (* ScriptSetVarVE) (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length, RsElement e, const uint32_t * dims, size_t dims_length);
    RsScript (* ScriptCCreate) (RsContext rsc, const char * resName, size_t resName_length, const char * cacheDir, size_t cacheDir_length, const char * text, size_t text_length);
    RsScript (* ScriptIntrinsicCreate) (RsContext rsc, uint32_t id, RsElement eid);
    RsScriptKernelID (* ScriptKernelIDCreate) (RsContext rsc, RsScript sid, int slot, int sig);
    RsScriptFieldID (* ScriptFieldIDCreate) (RsContext rsc, RsScript sid, int slot);
    RsScriptGroup (* ScriptGroupCreate) (RsContext rsc, RsScriptKernelID * kernels, size_t kernels_length, RsScriptKernelID * src, size_t src_length, RsScriptKernelID * dstK, size_t dstK_length, RsScriptFieldID * dstF, size_t dstF_length, const RsType * type, size_t type_length);
    void (* ScriptGroupSetOutput) (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc);
    void (* ScriptGroupSetInput) (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc);
    void (* ScriptGroupExecute) (RsContext rsc, RsScriptGroup group);
    RsScriptGroup2 (* ScriptGroup2Create) (RsContext rsc, const char * name, size_t name_length, const char * cacheDir, size_t cacheDir_length, RsClosure * closures, size_t closures_length);
    void (* AllocationIoSend) (RsContext rsc, RsAllocation alloc);
    void (* AllocationIoReceive) (RsContext rsc, RsAllocation alloc);
    RsProgramStore (* ProgramStoreCreate) (RsContext rsc, bool colorMaskR, bool colorMaskG, bool colorMaskB, bool colorMaskA, bool depthMask, bool ditherEnable, RsBlendSrcFunc srcFunc, RsBlendDstFunc destFunc, RsDepthFunc depthFunc);
    RsProgramRaster (* ProgramRasterCreate) (RsContext rsc, bool pointSprite, RsCullMode cull);
    void (* ProgramBindConstants) (RsContext rsc, RsProgram vp, uint32_t slot, RsAllocation constants);
    void (* ProgramBindTexture) (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsAllocation a);
    void (* ProgramBindSampler) (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsSampler s);
    RsProgramFragment (* ProgramFragmentCreate) (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length);
    RsProgramVertex (* ProgramVertexCreate) (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length);
    RsFont (* FontCreateFromFile) (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi);
    RsFont (* FontCreateFromMemory) (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi, const void * data, size_t data_length);
    RsMesh (* MeshCreate) (RsContext rsc, RsAllocation * vtx, size_t vtx_length, RsAllocation * idx, size_t idx_length, uint32_t * primType, size_t primType_length);
    void (* ContextBindProgramStore) (RsContext rsc, RsProgramStore pgm);
    void (* ContextBindProgramFragment) (RsContext rsc, RsProgramFragment pgm);
    void (* ContextBindProgramVertex) (RsContext rsc, RsProgramVertex pgm);
    void (* ContextBindProgramRaster) (RsContext rsc, RsProgramRaster pgm);
    void (* ContextBindFont) (RsContext rsc, RsFont pgm);
    void (* ContextSetSurface) (RsContext rsc, uint32_t width, uint32_t height, RsNativeWindow sur);
    void (* ContextBindRootScript) (RsContext rsc, RsScript sampler);
    void (* ContextPause) (RsContext rsc);
    void (* ContextResume) (RsContext rsc);
} RsApiEntrypoints_t;

static void LF_ContextDestroy (RsContext rsc)
{
    rsi_ContextDestroy((Context *)rsc);
};

static void RF_ContextDestroy (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextDestroy;
    io->asyncWrite(&cmdID, sizeof(cmdID));






    io->asyncGetReturn(NULL, 0);
}

static RsMessageToClientType LF_ContextGetMessage (RsContext rsc, void * data, size_t data_length, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    return rsi_ContextGetMessage((Context *)rsc, data, data_length, receiveLen, receiveLen_length, usrID, usrID_length);
};

static RsMessageToClientType RF_ContextGetMessage (RsContext rsc, void * data, size_t data_length, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextGetMessage;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& data_length, sizeof(data_length));
    io->asyncWrite(& receiveLen_length, sizeof(receiveLen_length));
    io->asyncWrite(& usrID_length, sizeof(usrID_length));



    io->asyncGetReturn(data, data_length);
    io->asyncGetReturn(receiveLen, receiveLen_length);
    io->asyncGetReturn(usrID, usrID_length);


    RsMessageToClientType retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsMessageToClientType LF_ContextPeekMessage (RsContext rsc, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    return rsi_ContextPeekMessage((Context *)rsc, receiveLen, receiveLen_length, usrID, usrID_length);
};

static RsMessageToClientType RF_ContextPeekMessage (RsContext rsc, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextPeekMessage;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& receiveLen_length, sizeof(receiveLen_length));
    io->asyncWrite(& usrID_length, sizeof(usrID_length));



    io->asyncGetReturn(receiveLen, receiveLen_length);
    io->asyncGetReturn(usrID, usrID_length);


    RsMessageToClientType retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ContextSendMessage (RsContext rsc, uint32_t id, const uint8_t * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextSendMessage((Context *)rsc, id, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextSendMessage);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_ContextSendMessage *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ContextSendMessage *>(io->coreHeader(RS_CMD_ID_ContextSendMessage, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ContextSendMessage *>(io->coreHeader(RS_CMD_ID_ContextSendMessage, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->id = id;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const uint8_t *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ContextSendMessage (RsContext rsc, uint32_t id, const uint8_t * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextSendMessage;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& id, sizeof(id));
    io->coreWrite(& data_length, sizeof(data_length));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ContextInitToClient (RsContext rsc)
{
    rsi_ContextInitToClient((Context *)rsc);
};

static void RF_ContextInitToClient (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextInitToClient;
    io->asyncWrite(&cmdID, sizeof(cmdID));






    io->asyncGetReturn(NULL, 0);
}

static void LF_ContextDeinitToClient (RsContext rsc)
{
    rsi_ContextDeinitToClient((Context *)rsc);
};

static void RF_ContextDeinitToClient (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextDeinitToClient;
    io->asyncWrite(&cmdID, sizeof(cmdID));






    io->asyncGetReturn(NULL, 0);
}

static void LF_ContextSetCacheDir (RsContext rsc, const char * cacheDir, size_t cacheDir_length)
{
    rsi_ContextSetCacheDir((Context *)rsc, cacheDir, cacheDir_length);
};

static void RF_ContextSetCacheDir (RsContext rsc, const char * cacheDir, size_t cacheDir_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextSetCacheDir;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& cacheDir_length, sizeof(cacheDir_length));

    io->asyncWrite(cacheDir, cacheDir_length);




    io->asyncGetReturn(NULL, 0);
}

static RsType LF_TypeCreate (RsContext rsc, RsElement e, uint32_t dimX, uint32_t dimY, uint32_t dimZ, bool mipmaps, bool faces, uint32_t yuv)
{
    return rsi_TypeCreate((Context *)rsc, e, dimX, dimY, dimZ, mipmaps, faces, yuv);
};

static RsType RF_TypeCreate (RsContext rsc, RsElement e, uint32_t dimX, uint32_t dimY, uint32_t dimZ, bool mipmaps, bool faces, uint32_t yuv)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_TypeCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& e, sizeof(e));
    io->asyncWrite(& dimX, sizeof(dimX));
    io->asyncWrite(& dimY, sizeof(dimY));
    io->asyncWrite(& dimZ, sizeof(dimZ));
    io->asyncWrite(& mipmaps, sizeof(mipmaps));
    io->asyncWrite(& faces, sizeof(faces));
    io->asyncWrite(& yuv, sizeof(yuv));





    RsType retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsType LF_TypeCreate2 (RsContext rsc, const RsTypeCreateParams * dat, size_t dat_length)
{
    return rsi_TypeCreate2((Context *)rsc, dat, dat_length);
};

static RsType RF_TypeCreate2 (RsContext rsc, const RsTypeCreateParams * dat, size_t dat_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_TypeCreate2;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& dat_length, sizeof(dat_length));

    io->asyncWrite(dat, dat_length);




    RsType retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsAllocation LF_AllocationCreateTyped (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, uint32_t usages, uintptr_t ptr)
{
    return rsi_AllocationCreateTyped((Context *)rsc, vtype, mipmaps, usages, ptr);
};

static RsAllocation RF_AllocationCreateTyped (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, uint32_t usages, uintptr_t ptr)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCreateTyped;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& vtype, sizeof(vtype));
    io->asyncWrite(& mipmaps, sizeof(mipmaps));
    io->asyncWrite(& usages, sizeof(usages));
    io->asyncWrite(& ptr, sizeof(ptr));





    RsAllocation retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsAllocation LF_AllocationCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    return rsi_AllocationCreateFromBitmap((Context *)rsc, vtype, mipmaps, data, data_length, usages);
};

static RsAllocation RF_AllocationCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCreateFromBitmap;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& vtype, sizeof(vtype));
    io->asyncWrite(& mipmaps, sizeof(mipmaps));
    io->asyncWrite(& data_length, sizeof(data_length));
    io->asyncWrite(& usages, sizeof(usages));

    io->asyncWrite(data, data_length);




    RsAllocation retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsAllocation LF_AllocationCubeCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    return rsi_AllocationCubeCreateFromBitmap((Context *)rsc, vtype, mipmaps, data, data_length, usages);
};

static RsAllocation RF_AllocationCubeCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCubeCreateFromBitmap;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& vtype, sizeof(vtype));
    io->asyncWrite(& mipmaps, sizeof(mipmaps));
    io->asyncWrite(& data_length, sizeof(data_length));
    io->asyncWrite(& usages, sizeof(usages));

    io->asyncWrite(data, data_length);




    RsAllocation retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsNativeWindow LF_AllocationGetSurface (RsContext rsc, RsAllocation alloc)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_AllocationGetSurface((Context *)rsc, alloc);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationGetSurface);
    RS_CMD_AllocationGetSurface *cmd = static_cast<RS_CMD_AllocationGetSurface *>(io->coreHeader(RS_CMD_ID_AllocationGetSurface, size));
    cmd->alloc = alloc;
    io->coreCommit();

    RsNativeWindow ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsNativeWindow RF_AllocationGetSurface (RsContext rsc, RsAllocation alloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationGetSurface;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));





    RsNativeWindow retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_AllocationSetSurface (RsContext rsc, RsAllocation alloc, RsNativeWindow sur)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationSetSurface((Context *)rsc, alloc, sur);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationSetSurface);
    RS_CMD_AllocationSetSurface *cmd = static_cast<RS_CMD_AllocationSetSurface *>(io->coreHeader(RS_CMD_ID_AllocationSetSurface, size));
    cmd->alloc = alloc;
    cmd->sur = sur;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_AllocationSetSurface (RsContext rsc, RsAllocation alloc, RsNativeWindow sur)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationSetSurface;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));
    io->coreWrite(& sur, sizeof(sur));





    io->coreGetReturn(NULL, 0);
}

static RsAllocation LF_AllocationAdapterCreate (RsContext rsc, RsType vtype, RsAllocation baseAlloc)
{
    return rsi_AllocationAdapterCreate((Context *)rsc, vtype, baseAlloc);
};

static RsAllocation RF_AllocationAdapterCreate (RsContext rsc, RsType vtype, RsAllocation baseAlloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationAdapterCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& vtype, sizeof(vtype));
    io->asyncWrite(& baseAlloc, sizeof(baseAlloc));





    RsAllocation retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_AllocationAdapterOffset (RsContext rsc, RsAllocation alloc, const uint32_t * offsets, size_t offsets_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationAdapterOffset((Context *)rsc, alloc, offsets, offsets_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationAdapterOffset);
    size_t dataSize = 0;
    dataSize += offsets_length;
    RS_CMD_AllocationAdapterOffset *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_AllocationAdapterOffset *>(io->coreHeader(RS_CMD_ID_AllocationAdapterOffset, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_AllocationAdapterOffset *>(io->coreHeader(RS_CMD_ID_AllocationAdapterOffset, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->alloc = alloc;
    if (offsets_length == 0) {
        cmd->offsets = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, offsets, offsets_length);
        cmd->offsets = (const uint32_t *)(payload - ((uint8_t *)&cmd[1]));
        payload += offsets_length;
    } else {
        cmd->offsets = offsets;
    }
    cmd->offsets_length = offsets_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_AllocationAdapterOffset (RsContext rsc, RsAllocation alloc, const uint32_t * offsets, size_t offsets_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationAdapterOffset;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));
    io->coreWrite(& offsets_length, sizeof(offsets_length));

    io->coreWrite(offsets, offsets_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ContextFinish (RsContext rsc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextFinish((Context *)rsc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextFinish);
    RS_CMD_ContextFinish *cmd = static_cast<RS_CMD_ContextFinish *>(io->coreHeader(RS_CMD_ID_ContextFinish, size));
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_ContextFinish (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextFinish;
    io->coreWrite(&cmdID, sizeof(cmdID));






    io->coreGetReturn(NULL, 0);
}

static void LF_ContextDump (RsContext rsc, int32_t bits)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextDump((Context *)rsc, bits);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextDump);
    RS_CMD_ContextDump *cmd = static_cast<RS_CMD_ContextDump *>(io->coreHeader(RS_CMD_ID_ContextDump, size));
    cmd->bits = bits;
    io->coreCommit();
};

static void RF_ContextDump (RsContext rsc, int32_t bits)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextDump;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& bits, sizeof(bits));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextSetPriority (RsContext rsc, int32_t priority)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextSetPriority((Context *)rsc, priority);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextSetPriority);
    RS_CMD_ContextSetPriority *cmd = static_cast<RS_CMD_ContextSetPriority *>(io->coreHeader(RS_CMD_ID_ContextSetPriority, size));
    cmd->priority = priority;
    io->coreCommit();
};

static void RF_ContextSetPriority (RsContext rsc, int32_t priority)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextSetPriority;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& priority, sizeof(priority));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextDestroyWorker (RsContext rsc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextDestroyWorker((Context *)rsc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextDestroyWorker);
    RS_CMD_ContextDestroyWorker *cmd = static_cast<RS_CMD_ContextDestroyWorker *>(io->coreHeader(RS_CMD_ID_ContextDestroyWorker, size));
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_ContextDestroyWorker (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextDestroyWorker;
    io->coreWrite(&cmdID, sizeof(cmdID));






    io->coreGetReturn(NULL, 0);
}

static void LF_AssignName (RsContext rsc, RsObjectBase obj, const char * name, size_t name_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AssignName((Context *)rsc, obj, name, name_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AssignName);
    size_t dataSize = 0;
    dataSize += name_length;
    RS_CMD_AssignName *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_AssignName *>(io->coreHeader(RS_CMD_ID_AssignName, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_AssignName *>(io->coreHeader(RS_CMD_ID_AssignName, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->obj = obj;
    if (name_length == 0) {
        cmd->name = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, name, name_length);
        cmd->name = (const char *)(payload - ((uint8_t *)&cmd[1]));
        payload += name_length;
    } else {
        cmd->name = name;
    }
    cmd->name_length = name_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_AssignName (RsContext rsc, RsObjectBase obj, const char * name, size_t name_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AssignName;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& obj, sizeof(obj));
    io->coreWrite(& name_length, sizeof(name_length));

    io->coreWrite(name, name_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ObjDestroy (RsContext rsc, RsAsyncVoidPtr objPtr)
{
    LF_ObjDestroy_handcode((Context *)rsc, objPtr);
};

static void RF_ObjDestroy (RsContext rsc, RsAsyncVoidPtr objPtr)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ObjDestroy;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& objPtr, sizeof(objPtr));





    io->coreGetReturn(NULL, 0);
}

static RsElement LF_ElementCreate (RsContext rsc, RsDataType mType, RsDataKind mKind, bool mNormalized, uint32_t mVectorSize)
{
    return rsi_ElementCreate((Context *)rsc, mType, mKind, mNormalized, mVectorSize);
};

static RsElement RF_ElementCreate (RsContext rsc, RsDataType mType, RsDataKind mKind, bool mNormalized, uint32_t mVectorSize)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ElementCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& mType, sizeof(mType));
    io->asyncWrite(& mKind, sizeof(mKind));
    io->asyncWrite(& mNormalized, sizeof(mNormalized));
    io->asyncWrite(& mVectorSize, sizeof(mVectorSize));





    RsElement retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsElement LF_ElementCreate2 (RsContext rsc, const RsElement * elements, size_t elements_length, const char ** names, size_t names_length_length, const size_t * names_length, const uint32_t * arraySize, size_t arraySize_length)
{
    return rsi_ElementCreate2((Context *)rsc, elements, elements_length, names, names_length_length, names_length, arraySize, arraySize_length);
};

static RsElement RF_ElementCreate2 (RsContext rsc, const RsElement * elements, size_t elements_length, const char ** names, size_t names_length_length, const size_t * names_length, const uint32_t * arraySize, size_t arraySize_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ElementCreate2;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& elements_length, sizeof(elements_length));
    io->asyncWrite(& names_length_length, sizeof(names_length_length));
    io->asyncWrite(& arraySize_length, sizeof(arraySize_length));

    io->asyncWrite(elements, elements_length);
    io->asyncWrite(names_length, names_length_length);
    io->asyncWrite(arraySize, arraySize_length);

    for (size_t ct = 0; ct < (names_length_length / sizeof(names_length)); ct++) {
        io->asyncWrite(names[ct], names_length[ct]);
    }



    RsElement retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_AllocationCopyToBitmap (RsContext rsc, RsAllocation alloc, void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationCopyToBitmap((Context *)rsc, alloc, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationCopyToBitmap);
    RS_CMD_AllocationCopyToBitmap *cmd = static_cast<RS_CMD_AllocationCopyToBitmap *>(io->coreHeader(RS_CMD_ID_AllocationCopyToBitmap, size));
    cmd->alloc = alloc;
    cmd->data = data;
    cmd->data_length = data_length;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_AllocationCopyToBitmap (RsContext rsc, RsAllocation alloc, void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCopyToBitmap;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));
    io->coreWrite(& data_length, sizeof(data_length));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void * LF_AllocationGetPointer (RsContext rsc, RsAllocation va, uint32_t lod, RsAllocationCubemapFace face, uint32_t z, uint32_t array, size_t * stride, size_t stride_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_AllocationGetPointer((Context *)rsc, va, lod, face, z, array, stride, stride_length);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationGetPointer);
    RS_CMD_AllocationGetPointer *cmd = static_cast<RS_CMD_AllocationGetPointer *>(io->coreHeader(RS_CMD_ID_AllocationGetPointer, size));
    cmd->va = va;
    cmd->lod = lod;
    cmd->face = face;
    cmd->z = z;
    cmd->array = array;
    cmd->stride = stride;
    cmd->stride_length = stride_length;
    io->coreCommit();

    void * ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static void * RF_AllocationGetPointer (RsContext rsc, RsAllocation va, uint32_t lod, RsAllocationCubemapFace face, uint32_t z, uint32_t array, size_t * stride, size_t stride_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationGetPointer;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& face, sizeof(face));
    io->coreWrite(& z, sizeof(z));
    io->coreWrite(& array, sizeof(array));
    io->coreWrite(& stride_length, sizeof(stride_length));



    io->coreGetReturn(stride, stride_length);


    void * retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_Allocation1DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, const void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation1DData((Context *)rsc, va, xoff, lod, count, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation1DData);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_Allocation1DData *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_Allocation1DData *>(io->coreHeader(RS_CMD_ID_Allocation1DData, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_Allocation1DData *>(io->coreHeader(RS_CMD_ID_Allocation1DData, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->lod = lod;
    cmd->count = count;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_Allocation1DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, const void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation1DData;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& count, sizeof(count));
    io->coreWrite(& data_length, sizeof(data_length));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation1DElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation1DElementData((Context *)rsc, va, x, lod, data, data_length, comp_offset);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation1DElementData);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_Allocation1DElementData *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_Allocation1DElementData *>(io->coreHeader(RS_CMD_ID_Allocation1DElementData, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_Allocation1DElementData *>(io->coreHeader(RS_CMD_ID_Allocation1DElementData, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->va = va;
    cmd->x = x;
    cmd->lod = lod;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    cmd->comp_offset = comp_offset;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_Allocation1DElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation1DElementData;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& x, sizeof(x));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& comp_offset, sizeof(comp_offset));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationElementData((Context *)rsc, va, x, y, z, lod, data, data_length, comp_offset);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationElementData);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_AllocationElementData *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_AllocationElementData *>(io->coreHeader(RS_CMD_ID_AllocationElementData, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_AllocationElementData *>(io->coreHeader(RS_CMD_ID_AllocationElementData, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->va = va;
    cmd->x = x;
    cmd->y = y;
    cmd->z = z;
    cmd->lod = lod;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    cmd->comp_offset = comp_offset;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_AllocationElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationElementData;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& x, sizeof(x));
    io->coreWrite(& y, sizeof(y));
    io->coreWrite(& z, sizeof(z));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& comp_offset, sizeof(comp_offset));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation2DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, const void * data, size_t data_length, size_t stride)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation2DData((Context *)rsc, va, xoff, yoff, lod, face, w, h, data, data_length, stride);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation2DData);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_Allocation2DData *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_Allocation2DData *>(io->coreHeader(RS_CMD_ID_Allocation2DData, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_Allocation2DData *>(io->coreHeader(RS_CMD_ID_Allocation2DData, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->yoff = yoff;
    cmd->lod = lod;
    cmd->face = face;
    cmd->w = w;
    cmd->h = h;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    cmd->stride = stride;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_Allocation2DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, const void * data, size_t data_length, size_t stride)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation2DData;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& yoff, sizeof(yoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& face, sizeof(face));
    io->coreWrite(& w, sizeof(w));
    io->coreWrite(& h, sizeof(h));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& stride, sizeof(stride));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation3DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, const void * data, size_t data_length, size_t stride)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation3DData((Context *)rsc, va, xoff, yoff, zoff, lod, w, h, d, data, data_length, stride);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation3DData);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_Allocation3DData *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_Allocation3DData *>(io->coreHeader(RS_CMD_ID_Allocation3DData, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_Allocation3DData *>(io->coreHeader(RS_CMD_ID_Allocation3DData, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->yoff = yoff;
    cmd->zoff = zoff;
    cmd->lod = lod;
    cmd->w = w;
    cmd->h = h;
    cmd->d = d;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    cmd->stride = stride;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_Allocation3DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, const void * data, size_t data_length, size_t stride)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation3DData;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& yoff, sizeof(yoff));
    io->coreWrite(& zoff, sizeof(zoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& w, sizeof(w));
    io->coreWrite(& h, sizeof(h));
    io->coreWrite(& d, sizeof(d));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& stride, sizeof(stride));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationGenerateMipmaps (RsContext rsc, RsAllocation va)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationGenerateMipmaps((Context *)rsc, va);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationGenerateMipmaps);
    RS_CMD_AllocationGenerateMipmaps *cmd = static_cast<RS_CMD_AllocationGenerateMipmaps *>(io->coreHeader(RS_CMD_ID_AllocationGenerateMipmaps, size));
    cmd->va = va;
    io->coreCommit();
};

static void RF_AllocationGenerateMipmaps (RsContext rsc, RsAllocation va)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationGenerateMipmaps;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));





    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationRead (RsContext rsc, RsAllocation va, void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationRead((Context *)rsc, va, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationRead);
    RS_CMD_AllocationRead *cmd = static_cast<RS_CMD_AllocationRead *>(io->coreHeader(RS_CMD_ID_AllocationRead, size));
    cmd->va = va;
    cmd->data = data;
    cmd->data_length = data_length;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_AllocationRead (RsContext rsc, RsAllocation va, void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationRead;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& data_length, sizeof(data_length));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation1DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation1DRead((Context *)rsc, va, xoff, lod, count, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation1DRead);
    RS_CMD_Allocation1DRead *cmd = static_cast<RS_CMD_Allocation1DRead *>(io->coreHeader(RS_CMD_ID_Allocation1DRead, size));
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->lod = lod;
    cmd->count = count;
    cmd->data = data;
    cmd->data_length = data_length;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_Allocation1DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation1DRead;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& count, sizeof(count));
    io->coreWrite(& data_length, sizeof(data_length));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationElementRead (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, void * data, size_t data_length, size_t comp_offset)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationElementRead((Context *)rsc, va, x, y, z, lod, data, data_length, comp_offset);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationElementRead);
    RS_CMD_AllocationElementRead *cmd = static_cast<RS_CMD_AllocationElementRead *>(io->coreHeader(RS_CMD_ID_AllocationElementRead, size));
    cmd->va = va;
    cmd->x = x;
    cmd->y = y;
    cmd->z = z;
    cmd->lod = lod;
    cmd->data = data;
    cmd->data_length = data_length;
    cmd->comp_offset = comp_offset;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_AllocationElementRead (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, void * data, size_t data_length, size_t comp_offset)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationElementRead;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& x, sizeof(x));
    io->coreWrite(& y, sizeof(y));
    io->coreWrite(& z, sizeof(z));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& comp_offset, sizeof(comp_offset));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation2DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, void * data, size_t data_length, size_t stride)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation2DRead((Context *)rsc, va, xoff, yoff, lod, face, w, h, data, data_length, stride);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation2DRead);
    RS_CMD_Allocation2DRead *cmd = static_cast<RS_CMD_Allocation2DRead *>(io->coreHeader(RS_CMD_ID_Allocation2DRead, size));
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->yoff = yoff;
    cmd->lod = lod;
    cmd->face = face;
    cmd->w = w;
    cmd->h = h;
    cmd->data = data;
    cmd->data_length = data_length;
    cmd->stride = stride;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_Allocation2DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, void * data, size_t data_length, size_t stride)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation2DRead;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& yoff, sizeof(yoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& face, sizeof(face));
    io->coreWrite(& w, sizeof(w));
    io->coreWrite(& h, sizeof(h));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& stride, sizeof(stride));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_Allocation3DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, void * data, size_t data_length, size_t stride)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_Allocation3DRead((Context *)rsc, va, xoff, yoff, zoff, lod, w, h, d, data, data_length, stride);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_Allocation3DRead);
    RS_CMD_Allocation3DRead *cmd = static_cast<RS_CMD_Allocation3DRead *>(io->coreHeader(RS_CMD_ID_Allocation3DRead, size));
    cmd->va = va;
    cmd->xoff = xoff;
    cmd->yoff = yoff;
    cmd->zoff = zoff;
    cmd->lod = lod;
    cmd->w = w;
    cmd->h = h;
    cmd->d = d;
    cmd->data = data;
    cmd->data_length = data_length;
    cmd->stride = stride;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_Allocation3DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, void * data, size_t data_length, size_t stride)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_Allocation3DRead;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& xoff, sizeof(xoff));
    io->coreWrite(& yoff, sizeof(yoff));
    io->coreWrite(& zoff, sizeof(zoff));
    io->coreWrite(& lod, sizeof(lod));
    io->coreWrite(& w, sizeof(w));
    io->coreWrite(& h, sizeof(h));
    io->coreWrite(& d, sizeof(d));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& stride, sizeof(stride));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationSyncAll (RsContext rsc, RsAllocation va, RsAllocationUsageType src)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationSyncAll((Context *)rsc, va, src);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationSyncAll);
    RS_CMD_AllocationSyncAll *cmd = static_cast<RS_CMD_AllocationSyncAll *>(io->coreHeader(RS_CMD_ID_AllocationSyncAll, size));
    cmd->va = va;
    cmd->src = src;
    io->coreCommit();
};

static void RF_AllocationSyncAll (RsContext rsc, RsAllocation va, RsAllocationUsageType src)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationSyncAll;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& src, sizeof(src));





    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationResize1D (RsContext rsc, RsAllocation va, uint32_t dimX)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationResize1D((Context *)rsc, va, dimX);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationResize1D);
    RS_CMD_AllocationResize1D *cmd = static_cast<RS_CMD_AllocationResize1D *>(io->coreHeader(RS_CMD_ID_AllocationResize1D, size));
    cmd->va = va;
    cmd->dimX = dimX;
    io->coreCommit();
};

static void RF_AllocationResize1D (RsContext rsc, RsAllocation va, uint32_t dimX)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationResize1D;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& dimX, sizeof(dimX));





    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationCopy2DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destMip, uint32_t destFace, uint32_t width, uint32_t height, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcMip, uint32_t srcFace)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationCopy2DRange((Context *)rsc, dest, destXoff, destYoff, destMip, destFace, width, height, src, srcXoff, srcYoff, srcMip, srcFace);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationCopy2DRange);
    RS_CMD_AllocationCopy2DRange *cmd = static_cast<RS_CMD_AllocationCopy2DRange *>(io->coreHeader(RS_CMD_ID_AllocationCopy2DRange, size));
    cmd->dest = dest;
    cmd->destXoff = destXoff;
    cmd->destYoff = destYoff;
    cmd->destMip = destMip;
    cmd->destFace = destFace;
    cmd->width = width;
    cmd->height = height;
    cmd->src = src;
    cmd->srcXoff = srcXoff;
    cmd->srcYoff = srcYoff;
    cmd->srcMip = srcMip;
    cmd->srcFace = srcFace;
    io->coreCommit();
};

static void RF_AllocationCopy2DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destMip, uint32_t destFace, uint32_t width, uint32_t height, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcMip, uint32_t srcFace)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCopy2DRange;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& dest, sizeof(dest));
    io->coreWrite(& destXoff, sizeof(destXoff));
    io->coreWrite(& destYoff, sizeof(destYoff));
    io->coreWrite(& destMip, sizeof(destMip));
    io->coreWrite(& destFace, sizeof(destFace));
    io->coreWrite(& width, sizeof(width));
    io->coreWrite(& height, sizeof(height));
    io->coreWrite(& src, sizeof(src));
    io->coreWrite(& srcXoff, sizeof(srcXoff));
    io->coreWrite(& srcYoff, sizeof(srcYoff));
    io->coreWrite(& srcMip, sizeof(srcMip));
    io->coreWrite(& srcFace, sizeof(srcFace));





    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationCopy3DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destZoff, uint32_t destMip, uint32_t width, uint32_t height, uint32_t depth, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcZoff, uint32_t srcMip)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationCopy3DRange((Context *)rsc, dest, destXoff, destYoff, destZoff, destMip, width, height, depth, src, srcXoff, srcYoff, srcZoff, srcMip);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationCopy3DRange);
    RS_CMD_AllocationCopy3DRange *cmd = static_cast<RS_CMD_AllocationCopy3DRange *>(io->coreHeader(RS_CMD_ID_AllocationCopy3DRange, size));
    cmd->dest = dest;
    cmd->destXoff = destXoff;
    cmd->destYoff = destYoff;
    cmd->destZoff = destZoff;
    cmd->destMip = destMip;
    cmd->width = width;
    cmd->height = height;
    cmd->depth = depth;
    cmd->src = src;
    cmd->srcXoff = srcXoff;
    cmd->srcYoff = srcYoff;
    cmd->srcZoff = srcZoff;
    cmd->srcMip = srcMip;
    io->coreCommit();
};

static void RF_AllocationCopy3DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destZoff, uint32_t destMip, uint32_t width, uint32_t height, uint32_t depth, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcZoff, uint32_t srcMip)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationCopy3DRange;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& dest, sizeof(dest));
    io->coreWrite(& destXoff, sizeof(destXoff));
    io->coreWrite(& destYoff, sizeof(destYoff));
    io->coreWrite(& destZoff, sizeof(destZoff));
    io->coreWrite(& destMip, sizeof(destMip));
    io->coreWrite(& width, sizeof(width));
    io->coreWrite(& height, sizeof(height));
    io->coreWrite(& depth, sizeof(depth));
    io->coreWrite(& src, sizeof(src));
    io->coreWrite(& srcXoff, sizeof(srcXoff));
    io->coreWrite(& srcYoff, sizeof(srcYoff));
    io->coreWrite(& srcZoff, sizeof(srcZoff));
    io->coreWrite(& srcMip, sizeof(srcMip));





    io->coreGetReturn(NULL, 0);
}

static RsClosure LF_ClosureCreate (RsContext rsc, RsScriptKernelID kernelID, RsAllocation returnValue, RsScriptFieldID * fieldIDs, size_t fieldIDs_length, uintptr_t * values, size_t values_length, int * sizes, size_t sizes_length, RsClosure * depClosures, size_t depClosures_length, RsScriptFieldID * depFieldIDs, size_t depFieldIDs_length)
{
    return rsi_ClosureCreate((Context *)rsc, kernelID, returnValue, fieldIDs, fieldIDs_length, values, values_length, sizes, sizes_length, depClosures, depClosures_length, depFieldIDs, depFieldIDs_length);
};

static RsClosure RF_ClosureCreate (RsContext rsc, RsScriptKernelID kernelID, RsAllocation returnValue, RsScriptFieldID * fieldIDs, size_t fieldIDs_length, uintptr_t * values, size_t values_length, int * sizes, size_t sizes_length, RsClosure * depClosures, size_t depClosures_length, RsScriptFieldID * depFieldIDs, size_t depFieldIDs_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ClosureCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& kernelID, sizeof(kernelID));
    io->asyncWrite(& returnValue, sizeof(returnValue));
    io->asyncWrite(& fieldIDs_length, sizeof(fieldIDs_length));
    io->asyncWrite(& values_length, sizeof(values_length));
    io->asyncWrite(& sizes_length, sizeof(sizes_length));
    io->asyncWrite(& depClosures_length, sizeof(depClosures_length));
    io->asyncWrite(& depFieldIDs_length, sizeof(depFieldIDs_length));



    io->asyncGetReturn(fieldIDs, fieldIDs_length);
    io->asyncGetReturn(values, values_length);
    io->asyncGetReturn(sizes, sizes_length);
    io->asyncGetReturn(depClosures, depClosures_length);
    io->asyncGetReturn(depFieldIDs, depFieldIDs_length);


    RsClosure retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsClosure LF_InvokeClosureCreate (RsContext rsc, RsScriptInvokeID invokeID, const void * params, size_t params_length, const RsScriptFieldID * fieldIDs, size_t fieldIDs_length, const uintptr_t * values, size_t values_length, const int * sizes, size_t sizes_length)
{
    return rsi_InvokeClosureCreate((Context *)rsc, invokeID, params, params_length, fieldIDs, fieldIDs_length, values, values_length, sizes, sizes_length);
};

static RsClosure RF_InvokeClosureCreate (RsContext rsc, RsScriptInvokeID invokeID, const void * params, size_t params_length, const RsScriptFieldID * fieldIDs, size_t fieldIDs_length, const uintptr_t * values, size_t values_length, const int * sizes, size_t sizes_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_InvokeClosureCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& invokeID, sizeof(invokeID));
    io->asyncWrite(& params_length, sizeof(params_length));
    io->asyncWrite(& fieldIDs_length, sizeof(fieldIDs_length));
    io->asyncWrite(& values_length, sizeof(values_length));
    io->asyncWrite(& sizes_length, sizeof(sizes_length));

    io->asyncWrite(params, params_length);
    io->asyncWrite(fieldIDs, fieldIDs_length);
    io->asyncWrite(values, values_length);
    io->asyncWrite(sizes, sizes_length);




    RsClosure retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ClosureSetArg (RsContext rsc, RsClosure closureID, uint32_t index, uintptr_t value, size_t valueSize)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ClosureSetArg((Context *)rsc, closureID, index, value, valueSize);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ClosureSetArg);
    RS_CMD_ClosureSetArg *cmd = static_cast<RS_CMD_ClosureSetArg *>(io->coreHeader(RS_CMD_ID_ClosureSetArg, size));
    cmd->closureID = closureID;
    cmd->index = index;
    cmd->value = value;
    cmd->valueSize = valueSize;
    io->coreCommit();
};

static void RF_ClosureSetArg (RsContext rsc, RsClosure closureID, uint32_t index, uintptr_t value, size_t valueSize)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ClosureSetArg;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& closureID, sizeof(closureID));
    io->coreWrite(& index, sizeof(index));
    io->coreWrite(& value, sizeof(value));
    io->coreWrite(& valueSize, sizeof(valueSize));





    io->coreGetReturn(NULL, 0);
}

static void LF_ClosureSetGlobal (RsContext rsc, RsClosure closureID, RsScriptFieldID fieldID, uintptr_t value, size_t valueSize)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ClosureSetGlobal((Context *)rsc, closureID, fieldID, value, valueSize);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ClosureSetGlobal);
    RS_CMD_ClosureSetGlobal *cmd = static_cast<RS_CMD_ClosureSetGlobal *>(io->coreHeader(RS_CMD_ID_ClosureSetGlobal, size));
    cmd->closureID = closureID;
    cmd->fieldID = fieldID;
    cmd->value = value;
    cmd->valueSize = valueSize;
    io->coreCommit();
};

static void RF_ClosureSetGlobal (RsContext rsc, RsClosure closureID, RsScriptFieldID fieldID, uintptr_t value, size_t valueSize)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ClosureSetGlobal;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& closureID, sizeof(closureID));
    io->coreWrite(& fieldID, sizeof(fieldID));
    io->coreWrite(& value, sizeof(value));
    io->coreWrite(& valueSize, sizeof(valueSize));





    io->coreGetReturn(NULL, 0);
}

static RsSampler LF_SamplerCreate (RsContext rsc, RsSamplerValue magFilter, RsSamplerValue minFilter, RsSamplerValue wrapS, RsSamplerValue wrapT, RsSamplerValue wrapR, float mAniso)
{
    return rsi_SamplerCreate((Context *)rsc, magFilter, minFilter, wrapS, wrapT, wrapR, mAniso);
};

static RsSampler RF_SamplerCreate (RsContext rsc, RsSamplerValue magFilter, RsSamplerValue minFilter, RsSamplerValue wrapS, RsSamplerValue wrapT, RsSamplerValue wrapR, float mAniso)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_SamplerCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& magFilter, sizeof(magFilter));
    io->asyncWrite(& minFilter, sizeof(minFilter));
    io->asyncWrite(& wrapS, sizeof(wrapS));
    io->asyncWrite(& wrapT, sizeof(wrapT));
    io->asyncWrite(& wrapR, sizeof(wrapR));
    io->asyncWrite(& mAniso, sizeof(mAniso));





    RsSampler retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ScriptBindAllocation (RsContext rsc, RsScript vtm, RsAllocation va, uint32_t slot)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptBindAllocation((Context *)rsc, vtm, va, slot);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptBindAllocation);
    RS_CMD_ScriptBindAllocation *cmd = static_cast<RS_CMD_ScriptBindAllocation *>(io->coreHeader(RS_CMD_ID_ScriptBindAllocation, size));
    cmd->vtm = vtm;
    cmd->va = va;
    cmd->slot = slot;
    io->coreCommit();
};

static void RF_ScriptBindAllocation (RsContext rsc, RsScript vtm, RsAllocation va, uint32_t slot)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptBindAllocation;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& vtm, sizeof(vtm));
    io->coreWrite(& va, sizeof(va));
    io->coreWrite(& slot, sizeof(slot));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetTimeZone (RsContext rsc, RsScript s, const char * timeZone, size_t timeZone_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetTimeZone((Context *)rsc, s, timeZone, timeZone_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetTimeZone);
    size_t dataSize = 0;
    dataSize += timeZone_length;
    RS_CMD_ScriptSetTimeZone *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ScriptSetTimeZone *>(io->coreHeader(RS_CMD_ID_ScriptSetTimeZone, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ScriptSetTimeZone *>(io->coreHeader(RS_CMD_ID_ScriptSetTimeZone, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->s = s;
    if (timeZone_length == 0) {
        cmd->timeZone = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, timeZone, timeZone_length);
        cmd->timeZone = (const char *)(payload - ((uint8_t *)&cmd[1]));
        payload += timeZone_length;
    } else {
        cmd->timeZone = timeZone;
    }
    cmd->timeZone_length = timeZone_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ScriptSetTimeZone (RsContext rsc, RsScript s, const char * timeZone, size_t timeZone_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetTimeZone;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& timeZone_length, sizeof(timeZone_length));

    io->coreWrite(timeZone, timeZone_length);




    io->coreGetReturn(NULL, 0);
}

static RsScriptInvokeID LF_ScriptInvokeIDCreate (RsContext rsc, RsScript s, uint32_t slot)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_ScriptInvokeIDCreate((Context *)rsc, s, slot);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptInvokeIDCreate);
    RS_CMD_ScriptInvokeIDCreate *cmd = static_cast<RS_CMD_ScriptInvokeIDCreate *>(io->coreHeader(RS_CMD_ID_ScriptInvokeIDCreate, size));
    cmd->s = s;
    cmd->slot = slot;
    io->coreCommit();

    RsScriptInvokeID ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsScriptInvokeID RF_ScriptInvokeIDCreate (RsContext rsc, RsScript s, uint32_t slot)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptInvokeIDCreate;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));





    RsScriptInvokeID retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ScriptInvoke (RsContext rsc, RsScript s, uint32_t slot)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptInvoke((Context *)rsc, s, slot);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptInvoke);
    RS_CMD_ScriptInvoke *cmd = static_cast<RS_CMD_ScriptInvoke *>(io->coreHeader(RS_CMD_ID_ScriptInvoke, size));
    cmd->s = s;
    cmd->slot = slot;
    io->coreCommit();
};

static void RF_ScriptInvoke (RsContext rsc, RsScript s, uint32_t slot)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptInvoke;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptInvokeV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptInvokeV((Context *)rsc, s, slot, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptInvokeV);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_ScriptInvokeV *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ScriptInvokeV *>(io->coreHeader(RS_CMD_ID_ScriptInvokeV, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ScriptInvokeV *>(io->coreHeader(RS_CMD_ID_ScriptInvokeV, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->s = s;
    cmd->slot = slot;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ScriptInvokeV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptInvokeV;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& data_length, sizeof(data_length));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptForEach (RsContext rsc, RsScript s, uint32_t slot, RsAllocation ain, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptForEach((Context *)rsc, s, slot, ain, aout, usr, usr_length, sc, sc_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptForEach);
    size_t dataSize = 0;
    dataSize += usr_length;
    dataSize += sc_length;
    RS_CMD_ScriptForEach *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ScriptForEach *>(io->coreHeader(RS_CMD_ID_ScriptForEach, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ScriptForEach *>(io->coreHeader(RS_CMD_ID_ScriptForEach, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->s = s;
    cmd->slot = slot;
    cmd->ain = ain;
    cmd->aout = aout;
    if (usr_length == 0) {
        cmd->usr = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, usr, usr_length);
        cmd->usr = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += usr_length;
    } else {
        cmd->usr = usr;
    }
    cmd->usr_length = usr_length;
    if (sc_length == 0) {
        cmd->sc = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, sc, sc_length);
        cmd->sc = (const RsScriptCall *)(payload - ((uint8_t *)&cmd[1]));
        payload += sc_length;
    } else {
        cmd->sc = sc;
    }
    cmd->sc_length = sc_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ScriptForEach (RsContext rsc, RsScript s, uint32_t slot, RsAllocation ain, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptForEach;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& ain, sizeof(ain));
    io->coreWrite(& aout, sizeof(aout));
    io->coreWrite(& usr_length, sizeof(usr_length));
    io->coreWrite(& sc_length, sizeof(sc_length));

    io->coreWrite(usr, usr_length);
    io->coreWrite(sc, sc_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptForEachMulti (RsContext rsc, RsScript s, uint32_t slot, RsAllocation * ains, size_t ains_length, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptForEachMulti((Context *)rsc, s, slot, ains, ains_length, aout, usr, usr_length, sc, sc_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptForEachMulti);
    RS_CMD_ScriptForEachMulti *cmd = static_cast<RS_CMD_ScriptForEachMulti *>(io->coreHeader(RS_CMD_ID_ScriptForEachMulti, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->ains = ains;
    cmd->ains_length = ains_length;
    cmd->aout = aout;
    cmd->usr = usr;
    cmd->usr_length = usr_length;
    cmd->sc = sc;
    cmd->sc_length = sc_length;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_ScriptForEachMulti (RsContext rsc, RsScript s, uint32_t slot, RsAllocation * ains, size_t ains_length, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptForEachMulti;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& ains_length, sizeof(ains_length));
    io->coreWrite(& aout, sizeof(aout));
    io->coreWrite(& usr_length, sizeof(usr_length));
    io->coreWrite(& sc_length, sizeof(sc_length));

    io->coreWrite(usr, usr_length);
    io->coreWrite(sc, sc_length);


    io->coreGetReturn(ains, ains_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarI (RsContext rsc, RsScript s, uint32_t slot, int value)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarI((Context *)rsc, s, slot, value);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarI);
    RS_CMD_ScriptSetVarI *cmd = static_cast<RS_CMD_ScriptSetVarI *>(io->coreHeader(RS_CMD_ID_ScriptSetVarI, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->value = value;
    io->coreCommit();
};

static void RF_ScriptSetVarI (RsContext rsc, RsScript s, uint32_t slot, int value)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarI;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& value, sizeof(value));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarObj (RsContext rsc, RsScript s, uint32_t slot, RsObjectBase value)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarObj((Context *)rsc, s, slot, value);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarObj);
    RS_CMD_ScriptSetVarObj *cmd = static_cast<RS_CMD_ScriptSetVarObj *>(io->coreHeader(RS_CMD_ID_ScriptSetVarObj, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->value = value;
    io->coreCommit();
};

static void RF_ScriptSetVarObj (RsContext rsc, RsScript s, uint32_t slot, RsObjectBase value)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarObj;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& value, sizeof(value));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarJ (RsContext rsc, RsScript s, uint32_t slot, int64_t value)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarJ((Context *)rsc, s, slot, value);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarJ);
    RS_CMD_ScriptSetVarJ *cmd = static_cast<RS_CMD_ScriptSetVarJ *>(io->coreHeader(RS_CMD_ID_ScriptSetVarJ, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->value = value;
    io->coreCommit();
};

static void RF_ScriptSetVarJ (RsContext rsc, RsScript s, uint32_t slot, int64_t value)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarJ;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& value, sizeof(value));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarF (RsContext rsc, RsScript s, uint32_t slot, float value)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarF((Context *)rsc, s, slot, value);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarF);
    RS_CMD_ScriptSetVarF *cmd = static_cast<RS_CMD_ScriptSetVarF *>(io->coreHeader(RS_CMD_ID_ScriptSetVarF, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->value = value;
    io->coreCommit();
};

static void RF_ScriptSetVarF (RsContext rsc, RsScript s, uint32_t slot, float value)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarF;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& value, sizeof(value));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarD (RsContext rsc, RsScript s, uint32_t slot, double value)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarD((Context *)rsc, s, slot, value);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarD);
    RS_CMD_ScriptSetVarD *cmd = static_cast<RS_CMD_ScriptSetVarD *>(io->coreHeader(RS_CMD_ID_ScriptSetVarD, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->value = value;
    io->coreCommit();
};

static void RF_ScriptSetVarD (RsContext rsc, RsScript s, uint32_t slot, double value)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarD;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& value, sizeof(value));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarV((Context *)rsc, s, slot, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarV);
    size_t dataSize = 0;
    dataSize += data_length;
    RS_CMD_ScriptSetVarV *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ScriptSetVarV *>(io->coreHeader(RS_CMD_ID_ScriptSetVarV, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ScriptSetVarV *>(io->coreHeader(RS_CMD_ID_ScriptSetVarV, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->s = s;
    cmd->slot = slot;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ScriptSetVarV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarV;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& data_length, sizeof(data_length));

    io->coreWrite(data, data_length);




    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptGetVarV (RsContext rsc, RsScript s, uint32_t slot, void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptGetVarV((Context *)rsc, s, slot, data, data_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptGetVarV);
    RS_CMD_ScriptGetVarV *cmd = static_cast<RS_CMD_ScriptGetVarV *>(io->coreHeader(RS_CMD_ID_ScriptGetVarV, size));
    cmd->s = s;
    cmd->slot = slot;
    cmd->data = data;
    cmd->data_length = data_length;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_ScriptGetVarV (RsContext rsc, RsScript s, uint32_t slot, void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGetVarV;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& data_length, sizeof(data_length));



    io->coreGetReturn(data, data_length);


    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptSetVarVE (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length, RsElement e, const uint32_t * dims, size_t dims_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptSetVarVE((Context *)rsc, s, slot, data, data_length, e, dims, dims_length);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptSetVarVE);
    size_t dataSize = 0;
    dataSize += data_length;
    dataSize += dims_length;
    RS_CMD_ScriptSetVarVE *cmd = NULL;
    if (dataSize < io->getMaxInlineSize()) {;
        cmd = static_cast<RS_CMD_ScriptSetVarVE *>(io->coreHeader(RS_CMD_ID_ScriptSetVarVE, dataSize + size));
    } else {
        cmd = static_cast<RS_CMD_ScriptSetVarVE *>(io->coreHeader(RS_CMD_ID_ScriptSetVarVE, size));
    }
    uint8_t *payload = (uint8_t *)&cmd[1];
    cmd->s = s;
    cmd->slot = slot;
    if (data_length == 0) {
        cmd->data = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, data, data_length);
        cmd->data = (const void *)(payload - ((uint8_t *)&cmd[1]));
        payload += data_length;
    } else {
        cmd->data = data;
    }
    cmd->data_length = data_length;
    cmd->e = e;
    if (dims_length == 0) {
        cmd->dims = NULL;
    } else if (dataSize < io->getMaxInlineSize()) {
        memcpy(payload, dims, dims_length);
        cmd->dims = (const uint32_t *)(payload - ((uint8_t *)&cmd[1]));
        payload += dims_length;
    } else {
        cmd->dims = dims;
    }
    cmd->dims_length = dims_length;
    io->coreCommit();
    if (dataSize >= io->getMaxInlineSize()) {
        io->coreGetReturn(NULL, 0);
    }
};

static void RF_ScriptSetVarVE (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length, RsElement e, const uint32_t * dims, size_t dims_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptSetVarVE;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& s, sizeof(s));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& data_length, sizeof(data_length));
    io->coreWrite(& e, sizeof(e));
    io->coreWrite(& dims_length, sizeof(dims_length));

    io->coreWrite(data, data_length);
    io->coreWrite(dims, dims_length);




    io->coreGetReturn(NULL, 0);
}

static RsScript LF_ScriptCCreate (RsContext rsc, const char * resName, size_t resName_length, const char * cacheDir, size_t cacheDir_length, const char * text, size_t text_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_ScriptCCreate((Context *)rsc, resName, resName_length, cacheDir, cacheDir_length, text, text_length);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptCCreate);
    RS_CMD_ScriptCCreate *cmd = static_cast<RS_CMD_ScriptCCreate *>(io->coreHeader(RS_CMD_ID_ScriptCCreate, size));
    cmd->resName = resName;
    cmd->resName_length = resName_length;
    cmd->cacheDir = cacheDir;
    cmd->cacheDir_length = cacheDir_length;
    cmd->text = text;
    cmd->text_length = text_length;
    io->coreCommit();

    RsScript ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsScript RF_ScriptCCreate (RsContext rsc, const char * resName, size_t resName_length, const char * cacheDir, size_t cacheDir_length, const char * text, size_t text_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptCCreate;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& resName_length, sizeof(resName_length));
    io->coreWrite(& cacheDir_length, sizeof(cacheDir_length));
    io->coreWrite(& text_length, sizeof(text_length));

    io->coreWrite(resName, resName_length);
    io->coreWrite(cacheDir, cacheDir_length);
    io->coreWrite(text, text_length);




    RsScript retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsScript LF_ScriptIntrinsicCreate (RsContext rsc, uint32_t id, RsElement eid)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_ScriptIntrinsicCreate((Context *)rsc, id, eid);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptIntrinsicCreate);
    RS_CMD_ScriptIntrinsicCreate *cmd = static_cast<RS_CMD_ScriptIntrinsicCreate *>(io->coreHeader(RS_CMD_ID_ScriptIntrinsicCreate, size));
    cmd->id = id;
    cmd->eid = eid;
    io->coreCommit();

    RsScript ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsScript RF_ScriptIntrinsicCreate (RsContext rsc, uint32_t id, RsElement eid)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptIntrinsicCreate;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& id, sizeof(id));
    io->coreWrite(& eid, sizeof(eid));





    RsScript retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsScriptKernelID LF_ScriptKernelIDCreate (RsContext rsc, RsScript sid, int slot, int sig)
{
    return rsi_ScriptKernelIDCreate((Context *)rsc, sid, slot, sig);
};

static RsScriptKernelID RF_ScriptKernelIDCreate (RsContext rsc, RsScript sid, int slot, int sig)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptKernelIDCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& sid, sizeof(sid));
    io->asyncWrite(& slot, sizeof(slot));
    io->asyncWrite(& sig, sizeof(sig));





    RsScriptKernelID retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsScriptFieldID LF_ScriptFieldIDCreate (RsContext rsc, RsScript sid, int slot)
{
    return rsi_ScriptFieldIDCreate((Context *)rsc, sid, slot);
};

static RsScriptFieldID RF_ScriptFieldIDCreate (RsContext rsc, RsScript sid, int slot)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptFieldIDCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& sid, sizeof(sid));
    io->asyncWrite(& slot, sizeof(slot));





    RsScriptFieldID retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsScriptGroup LF_ScriptGroupCreate (RsContext rsc, RsScriptKernelID * kernels, size_t kernels_length, RsScriptKernelID * src, size_t src_length, RsScriptKernelID * dstK, size_t dstK_length, RsScriptFieldID * dstF, size_t dstF_length, const RsType * type, size_t type_length)
{
    return rsi_ScriptGroupCreate((Context *)rsc, kernels, kernels_length, src, src_length, dstK, dstK_length, dstF, dstF_length, type, type_length);
};

static RsScriptGroup RF_ScriptGroupCreate (RsContext rsc, RsScriptKernelID * kernels, size_t kernels_length, RsScriptKernelID * src, size_t src_length, RsScriptKernelID * dstK, size_t dstK_length, RsScriptFieldID * dstF, size_t dstF_length, const RsType * type, size_t type_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGroupCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& kernels_length, sizeof(kernels_length));
    io->asyncWrite(& src_length, sizeof(src_length));
    io->asyncWrite(& dstK_length, sizeof(dstK_length));
    io->asyncWrite(& dstF_length, sizeof(dstF_length));
    io->asyncWrite(& type_length, sizeof(type_length));

    io->asyncWrite(type, type_length);


    io->asyncGetReturn(kernels, kernels_length);
    io->asyncGetReturn(src, src_length);
    io->asyncGetReturn(dstK, dstK_length);
    io->asyncGetReturn(dstF, dstF_length);


    RsScriptGroup retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ScriptGroupSetOutput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptGroupSetOutput((Context *)rsc, group, kernel, alloc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptGroupSetOutput);
    RS_CMD_ScriptGroupSetOutput *cmd = static_cast<RS_CMD_ScriptGroupSetOutput *>(io->coreHeader(RS_CMD_ID_ScriptGroupSetOutput, size));
    cmd->group = group;
    cmd->kernel = kernel;
    cmd->alloc = alloc;
    io->coreCommit();
};

static void RF_ScriptGroupSetOutput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGroupSetOutput;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& group, sizeof(group));
    io->coreWrite(& kernel, sizeof(kernel));
    io->coreWrite(& alloc, sizeof(alloc));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptGroupSetInput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptGroupSetInput((Context *)rsc, group, kernel, alloc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptGroupSetInput);
    RS_CMD_ScriptGroupSetInput *cmd = static_cast<RS_CMD_ScriptGroupSetInput *>(io->coreHeader(RS_CMD_ID_ScriptGroupSetInput, size));
    cmd->group = group;
    cmd->kernel = kernel;
    cmd->alloc = alloc;
    io->coreCommit();
};

static void RF_ScriptGroupSetInput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGroupSetInput;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& group, sizeof(group));
    io->coreWrite(& kernel, sizeof(kernel));
    io->coreWrite(& alloc, sizeof(alloc));





    io->coreGetReturn(NULL, 0);
}

static void LF_ScriptGroupExecute (RsContext rsc, RsScriptGroup group)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ScriptGroupExecute((Context *)rsc, group);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ScriptGroupExecute);
    RS_CMD_ScriptGroupExecute *cmd = static_cast<RS_CMD_ScriptGroupExecute *>(io->coreHeader(RS_CMD_ID_ScriptGroupExecute, size));
    cmd->group = group;
    io->coreCommit();
};

static void RF_ScriptGroupExecute (RsContext rsc, RsScriptGroup group)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGroupExecute;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& group, sizeof(group));





    io->coreGetReturn(NULL, 0);
}

static RsScriptGroup2 LF_ScriptGroup2Create (RsContext rsc, const char * name, size_t name_length, const char * cacheDir, size_t cacheDir_length, RsClosure * closures, size_t closures_length)
{
    return rsi_ScriptGroup2Create((Context *)rsc, name, name_length, cacheDir, cacheDir_length, closures, closures_length);
};

static RsScriptGroup2 RF_ScriptGroup2Create (RsContext rsc, const char * name, size_t name_length, const char * cacheDir, size_t cacheDir_length, RsClosure * closures, size_t closures_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ScriptGroup2Create;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& name_length, sizeof(name_length));
    io->asyncWrite(& cacheDir_length, sizeof(cacheDir_length));
    io->asyncWrite(& closures_length, sizeof(closures_length));

    io->asyncWrite(name, name_length);
    io->asyncWrite(cacheDir, cacheDir_length);


    io->asyncGetReturn(closures, closures_length);


    RsScriptGroup2 retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_AllocationIoSend (RsContext rsc, RsAllocation alloc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationIoSend((Context *)rsc, alloc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationIoSend);
    RS_CMD_AllocationIoSend *cmd = static_cast<RS_CMD_AllocationIoSend *>(io->coreHeader(RS_CMD_ID_AllocationIoSend, size));
    cmd->alloc = alloc;
    io->coreCommit();
};

static void RF_AllocationIoSend (RsContext rsc, RsAllocation alloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationIoSend;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));





    io->coreGetReturn(NULL, 0);
}

static void LF_AllocationIoReceive (RsContext rsc, RsAllocation alloc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_AllocationIoReceive((Context *)rsc, alloc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_AllocationIoReceive);
    RS_CMD_AllocationIoReceive *cmd = static_cast<RS_CMD_AllocationIoReceive *>(io->coreHeader(RS_CMD_ID_AllocationIoReceive, size));
    cmd->alloc = alloc;
    io->coreCommit();
};

static void RF_AllocationIoReceive (RsContext rsc, RsAllocation alloc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_AllocationIoReceive;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& alloc, sizeof(alloc));





    io->coreGetReturn(NULL, 0);
}

static RsProgramStore LF_ProgramStoreCreate (RsContext rsc, bool colorMaskR, bool colorMaskG, bool colorMaskB, bool colorMaskA, bool depthMask, bool ditherEnable, RsBlendSrcFunc srcFunc, RsBlendDstFunc destFunc, RsDepthFunc depthFunc)
{
    return rsi_ProgramStoreCreate((Context *)rsc, colorMaskR, colorMaskG, colorMaskB, colorMaskA, depthMask, ditherEnable, srcFunc, destFunc, depthFunc);
};

static RsProgramStore RF_ProgramStoreCreate (RsContext rsc, bool colorMaskR, bool colorMaskG, bool colorMaskB, bool colorMaskA, bool depthMask, bool ditherEnable, RsBlendSrcFunc srcFunc, RsBlendDstFunc destFunc, RsDepthFunc depthFunc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramStoreCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& colorMaskR, sizeof(colorMaskR));
    io->asyncWrite(& colorMaskG, sizeof(colorMaskG));
    io->asyncWrite(& colorMaskB, sizeof(colorMaskB));
    io->asyncWrite(& colorMaskA, sizeof(colorMaskA));
    io->asyncWrite(& depthMask, sizeof(depthMask));
    io->asyncWrite(& ditherEnable, sizeof(ditherEnable));
    io->asyncWrite(& srcFunc, sizeof(srcFunc));
    io->asyncWrite(& destFunc, sizeof(destFunc));
    io->asyncWrite(& depthFunc, sizeof(depthFunc));





    RsProgramStore retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsProgramRaster LF_ProgramRasterCreate (RsContext rsc, bool pointSprite, RsCullMode cull)
{
    return rsi_ProgramRasterCreate((Context *)rsc, pointSprite, cull);
};

static RsProgramRaster RF_ProgramRasterCreate (RsContext rsc, bool pointSprite, RsCullMode cull)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramRasterCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& pointSprite, sizeof(pointSprite));
    io->asyncWrite(& cull, sizeof(cull));





    RsProgramRaster retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ProgramBindConstants (RsContext rsc, RsProgram vp, uint32_t slot, RsAllocation constants)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ProgramBindConstants((Context *)rsc, vp, slot, constants);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ProgramBindConstants);
    RS_CMD_ProgramBindConstants *cmd = static_cast<RS_CMD_ProgramBindConstants *>(io->coreHeader(RS_CMD_ID_ProgramBindConstants, size));
    cmd->vp = vp;
    cmd->slot = slot;
    cmd->constants = constants;
    io->coreCommit();
};

static void RF_ProgramBindConstants (RsContext rsc, RsProgram vp, uint32_t slot, RsAllocation constants)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramBindConstants;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& vp, sizeof(vp));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& constants, sizeof(constants));





    io->coreGetReturn(NULL, 0);
}

static void LF_ProgramBindTexture (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsAllocation a)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ProgramBindTexture((Context *)rsc, pf, slot, a);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ProgramBindTexture);
    RS_CMD_ProgramBindTexture *cmd = static_cast<RS_CMD_ProgramBindTexture *>(io->coreHeader(RS_CMD_ID_ProgramBindTexture, size));
    cmd->pf = pf;
    cmd->slot = slot;
    cmd->a = a;
    io->coreCommit();
};

static void RF_ProgramBindTexture (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsAllocation a)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramBindTexture;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pf, sizeof(pf));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& a, sizeof(a));





    io->coreGetReturn(NULL, 0);
}

static void LF_ProgramBindSampler (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsSampler s)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ProgramBindSampler((Context *)rsc, pf, slot, s);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ProgramBindSampler);
    RS_CMD_ProgramBindSampler *cmd = static_cast<RS_CMD_ProgramBindSampler *>(io->coreHeader(RS_CMD_ID_ProgramBindSampler, size));
    cmd->pf = pf;
    cmd->slot = slot;
    cmd->s = s;
    io->coreCommit();
};

static void RF_ProgramBindSampler (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsSampler s)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramBindSampler;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pf, sizeof(pf));
    io->coreWrite(& slot, sizeof(slot));
    io->coreWrite(& s, sizeof(s));





    io->coreGetReturn(NULL, 0);
}

static RsProgramFragment LF_ProgramFragmentCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    return rsi_ProgramFragmentCreate((Context *)rsc, shaderText, shaderText_length, textureNames, textureNames_length_length, textureNames_length, params, params_length);
};

static RsProgramFragment RF_ProgramFragmentCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramFragmentCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& shaderText_length, sizeof(shaderText_length));
    io->asyncWrite(& textureNames_length_length, sizeof(textureNames_length_length));
    io->asyncWrite(& params_length, sizeof(params_length));

    io->asyncWrite(shaderText, shaderText_length);
    io->asyncWrite(textureNames_length, textureNames_length_length);
    io->asyncWrite(params, params_length);

    for (size_t ct = 0; ct < (textureNames_length_length / sizeof(textureNames_length)); ct++) {
        io->asyncWrite(textureNames[ct], textureNames_length[ct]);
    }



    RsProgramFragment retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsProgramVertex LF_ProgramVertexCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    return rsi_ProgramVertexCreate((Context *)rsc, shaderText, shaderText_length, textureNames, textureNames_length_length, textureNames_length, params, params_length);
};

static RsProgramVertex RF_ProgramVertexCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ProgramVertexCreate;
    io->asyncWrite(&cmdID, sizeof(cmdID));

    io->asyncWrite(& shaderText_length, sizeof(shaderText_length));
    io->asyncWrite(& textureNames_length_length, sizeof(textureNames_length_length));
    io->asyncWrite(& params_length, sizeof(params_length));

    io->asyncWrite(shaderText, shaderText_length);
    io->asyncWrite(textureNames_length, textureNames_length_length);
    io->asyncWrite(params, params_length);

    for (size_t ct = 0; ct < (textureNames_length_length / sizeof(textureNames_length)); ct++) {
        io->asyncWrite(textureNames[ct], textureNames_length[ct]);
    }



    RsProgramVertex retValue;
    io->asyncGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsFont LF_FontCreateFromFile (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_FontCreateFromFile((Context *)rsc, name, name_length, fontSize, dpi);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_FontCreateFromFile);
    RS_CMD_FontCreateFromFile *cmd = static_cast<RS_CMD_FontCreateFromFile *>(io->coreHeader(RS_CMD_ID_FontCreateFromFile, size));
    cmd->name = name;
    cmd->name_length = name_length;
    cmd->fontSize = fontSize;
    cmd->dpi = dpi;
    io->coreCommit();

    RsFont ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsFont RF_FontCreateFromFile (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_FontCreateFromFile;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& name_length, sizeof(name_length));
    io->coreWrite(& fontSize, sizeof(fontSize));
    io->coreWrite(& dpi, sizeof(dpi));

    io->coreWrite(name, name_length);




    RsFont retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsFont LF_FontCreateFromMemory (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi, const void * data, size_t data_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_FontCreateFromMemory((Context *)rsc, name, name_length, fontSize, dpi, data, data_length);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_FontCreateFromMemory);
    RS_CMD_FontCreateFromMemory *cmd = static_cast<RS_CMD_FontCreateFromMemory *>(io->coreHeader(RS_CMD_ID_FontCreateFromMemory, size));
    cmd->name = name;
    cmd->name_length = name_length;
    cmd->fontSize = fontSize;
    cmd->dpi = dpi;
    cmd->data = data;
    cmd->data_length = data_length;
    io->coreCommit();

    RsFont ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsFont RF_FontCreateFromMemory (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi, const void * data, size_t data_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_FontCreateFromMemory;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& name_length, sizeof(name_length));
    io->coreWrite(& fontSize, sizeof(fontSize));
    io->coreWrite(& dpi, sizeof(dpi));
    io->coreWrite(& data_length, sizeof(data_length));

    io->coreWrite(name, name_length);
    io->coreWrite(data, data_length);




    RsFont retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static RsMesh LF_MeshCreate (RsContext rsc, RsAllocation * vtx, size_t vtx_length, RsAllocation * idx, size_t idx_length, uint32_t * primType, size_t primType_length)
{
    if (((Context *)rsc)->isSynchronous()) {
        return rsi_MeshCreate((Context *)rsc, vtx, vtx_length, idx, idx_length, primType, primType_length);
    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_MeshCreate);
    RS_CMD_MeshCreate *cmd = static_cast<RS_CMD_MeshCreate *>(io->coreHeader(RS_CMD_ID_MeshCreate, size));
    cmd->vtx = vtx;
    cmd->vtx_length = vtx_length;
    cmd->idx = idx;
    cmd->idx_length = idx_length;
    cmd->primType = primType;
    cmd->primType_length = primType_length;
    io->coreCommit();

    RsMesh ret;
    io->coreGetReturn(&ret, sizeof(ret));
    return ret;
};

static RsMesh RF_MeshCreate (RsContext rsc, RsAllocation * vtx, size_t vtx_length, RsAllocation * idx, size_t idx_length, uint32_t * primType, size_t primType_length)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_MeshCreate;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& vtx_length, sizeof(vtx_length));
    io->coreWrite(& idx_length, sizeof(idx_length));
    io->coreWrite(& primType_length, sizeof(primType_length));



    io->coreGetReturn(vtx, vtx_length);
    io->coreGetReturn(idx, idx_length);
    io->coreGetReturn(primType, primType_length);


    RsMesh retValue;
    io->coreGetReturn(&retValue, sizeof(retValue));
    return retValue;
}

static void LF_ContextBindProgramStore (RsContext rsc, RsProgramStore pgm)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindProgramStore((Context *)rsc, pgm);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindProgramStore);
    RS_CMD_ContextBindProgramStore *cmd = static_cast<RS_CMD_ContextBindProgramStore *>(io->coreHeader(RS_CMD_ID_ContextBindProgramStore, size));
    cmd->pgm = pgm;
    io->coreCommit();
};

static void RF_ContextBindProgramStore (RsContext rsc, RsProgramStore pgm)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindProgramStore;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pgm, sizeof(pgm));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextBindProgramFragment (RsContext rsc, RsProgramFragment pgm)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindProgramFragment((Context *)rsc, pgm);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindProgramFragment);
    RS_CMD_ContextBindProgramFragment *cmd = static_cast<RS_CMD_ContextBindProgramFragment *>(io->coreHeader(RS_CMD_ID_ContextBindProgramFragment, size));
    cmd->pgm = pgm;
    io->coreCommit();
};

static void RF_ContextBindProgramFragment (RsContext rsc, RsProgramFragment pgm)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindProgramFragment;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pgm, sizeof(pgm));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextBindProgramVertex (RsContext rsc, RsProgramVertex pgm)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindProgramVertex((Context *)rsc, pgm);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindProgramVertex);
    RS_CMD_ContextBindProgramVertex *cmd = static_cast<RS_CMD_ContextBindProgramVertex *>(io->coreHeader(RS_CMD_ID_ContextBindProgramVertex, size));
    cmd->pgm = pgm;
    io->coreCommit();
};

static void RF_ContextBindProgramVertex (RsContext rsc, RsProgramVertex pgm)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindProgramVertex;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pgm, sizeof(pgm));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextBindProgramRaster (RsContext rsc, RsProgramRaster pgm)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindProgramRaster((Context *)rsc, pgm);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindProgramRaster);
    RS_CMD_ContextBindProgramRaster *cmd = static_cast<RS_CMD_ContextBindProgramRaster *>(io->coreHeader(RS_CMD_ID_ContextBindProgramRaster, size));
    cmd->pgm = pgm;
    io->coreCommit();
};

static void RF_ContextBindProgramRaster (RsContext rsc, RsProgramRaster pgm)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindProgramRaster;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pgm, sizeof(pgm));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextBindFont (RsContext rsc, RsFont pgm)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindFont((Context *)rsc, pgm);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindFont);
    RS_CMD_ContextBindFont *cmd = static_cast<RS_CMD_ContextBindFont *>(io->coreHeader(RS_CMD_ID_ContextBindFont, size));
    cmd->pgm = pgm;
    io->coreCommit();
};

static void RF_ContextBindFont (RsContext rsc, RsFont pgm)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindFont;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& pgm, sizeof(pgm));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextSetSurface (RsContext rsc, uint32_t width, uint32_t height, RsNativeWindow sur)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextSetSurface((Context *)rsc, width, height, sur);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextSetSurface);
    RS_CMD_ContextSetSurface *cmd = static_cast<RS_CMD_ContextSetSurface *>(io->coreHeader(RS_CMD_ID_ContextSetSurface, size));
    cmd->width = width;
    cmd->height = height;
    cmd->sur = sur;
    io->coreCommit();
    io->coreGetReturn(NULL, 0);
};

static void RF_ContextSetSurface (RsContext rsc, uint32_t width, uint32_t height, RsNativeWindow sur)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextSetSurface;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& width, sizeof(width));
    io->coreWrite(& height, sizeof(height));
    io->coreWrite(& sur, sizeof(sur));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextBindRootScript (RsContext rsc, RsScript sampler)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextBindRootScript((Context *)rsc, sampler);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextBindRootScript);
    RS_CMD_ContextBindRootScript *cmd = static_cast<RS_CMD_ContextBindRootScript *>(io->coreHeader(RS_CMD_ID_ContextBindRootScript, size));
    cmd->sampler = sampler;
    io->coreCommit();
};

static void RF_ContextBindRootScript (RsContext rsc, RsScript sampler)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextBindRootScript;
    io->coreWrite(&cmdID, sizeof(cmdID));

    io->coreWrite(& sampler, sizeof(sampler));





    io->coreGetReturn(NULL, 0);
}

static void LF_ContextPause (RsContext rsc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextPause((Context *)rsc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextPause);
    RS_CMD_ContextPause *cmd = static_cast<RS_CMD_ContextPause *>(io->coreHeader(RS_CMD_ID_ContextPause, size));
    io->coreCommit();
};

static void RF_ContextPause (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextPause;
    io->coreWrite(&cmdID, sizeof(cmdID));






    io->coreGetReturn(NULL, 0);
}

static void LF_ContextResume (RsContext rsc)
{
    if (((Context *)rsc)->isSynchronous()) {
        rsi_ContextResume((Context *)rsc);
    return;    }

    ThreadIO *io = &((Context *)rsc)->mIO;
    const size_t size = sizeof(RS_CMD_ContextResume);
    RS_CMD_ContextResume *cmd = static_cast<RS_CMD_ContextResume *>(io->coreHeader(RS_CMD_ID_ContextResume, size));
    io->coreCommit();
};

static void RF_ContextResume (RsContext rsc)
{
    ThreadIO *io = &((Context *)rsc)->mIO;
    const uint32_t cmdID = RS_CMD_ID_ContextResume;
    io->coreWrite(&cmdID, sizeof(cmdID));






    io->coreGetReturn(NULL, 0);
}


static RsApiEntrypoints_t s_LocalTable = {
    LF_ContextDestroy,
    LF_ContextGetMessage,
    LF_ContextPeekMessage,
    LF_ContextSendMessage,
    LF_ContextInitToClient,
    LF_ContextDeinitToClient,
    LF_ContextSetCacheDir,
    LF_TypeCreate,
    LF_TypeCreate2,
    LF_AllocationCreateTyped,
    LF_AllocationCreateFromBitmap,
    LF_AllocationCubeCreateFromBitmap,
    LF_AllocationGetSurface,
    LF_AllocationSetSurface,
    LF_AllocationAdapterCreate,
    LF_AllocationAdapterOffset,
    LF_ContextFinish,
    LF_ContextDump,
    LF_ContextSetPriority,
    LF_ContextDestroyWorker,
    LF_AssignName,
    LF_ObjDestroy,
    LF_ElementCreate,
    LF_ElementCreate2,
    LF_AllocationCopyToBitmap,
    LF_AllocationGetPointer,
    LF_Allocation1DData,
    LF_Allocation1DElementData,
    LF_AllocationElementData,
    LF_Allocation2DData,
    LF_Allocation3DData,
    LF_AllocationGenerateMipmaps,
    LF_AllocationRead,
    LF_Allocation1DRead,
    LF_AllocationElementRead,
    LF_Allocation2DRead,
    LF_Allocation3DRead,
    LF_AllocationSyncAll,
    LF_AllocationResize1D,
    LF_AllocationCopy2DRange,
    LF_AllocationCopy3DRange,
    LF_ClosureCreate,
    LF_InvokeClosureCreate,
    LF_ClosureSetArg,
    LF_ClosureSetGlobal,
    LF_SamplerCreate,
    LF_ScriptBindAllocation,
    LF_ScriptSetTimeZone,
    LF_ScriptInvokeIDCreate,
    LF_ScriptInvoke,
    LF_ScriptInvokeV,
    LF_ScriptForEach,
    LF_ScriptForEachMulti,
    LF_ScriptSetVarI,
    LF_ScriptSetVarObj,
    LF_ScriptSetVarJ,
    LF_ScriptSetVarF,
    LF_ScriptSetVarD,
    LF_ScriptSetVarV,
    LF_ScriptGetVarV,
    LF_ScriptSetVarVE,
    LF_ScriptCCreate,
    LF_ScriptIntrinsicCreate,
    LF_ScriptKernelIDCreate,
    LF_ScriptFieldIDCreate,
    LF_ScriptGroupCreate,
    LF_ScriptGroupSetOutput,
    LF_ScriptGroupSetInput,
    LF_ScriptGroupExecute,
    LF_ScriptGroup2Create,
    LF_AllocationIoSend,
    LF_AllocationIoReceive,
    LF_ProgramStoreCreate,
    LF_ProgramRasterCreate,
    LF_ProgramBindConstants,
    LF_ProgramBindTexture,
    LF_ProgramBindSampler,
    LF_ProgramFragmentCreate,
    LF_ProgramVertexCreate,
    LF_FontCreateFromFile,
    LF_FontCreateFromMemory,
    LF_MeshCreate,
    LF_ContextBindProgramStore,
    LF_ContextBindProgramFragment,
    LF_ContextBindProgramVertex,
    LF_ContextBindProgramRaster,
    LF_ContextBindFont,
    LF_ContextSetSurface,
    LF_ContextBindRootScript,
    LF_ContextPause,
    LF_ContextResume,
};

static RsApiEntrypoints_t s_RemoteTable = {
    RF_ContextDestroy,
    RF_ContextGetMessage,
    RF_ContextPeekMessage,
    RF_ContextSendMessage,
    RF_ContextInitToClient,
    RF_ContextDeinitToClient,
    RF_ContextSetCacheDir,
    RF_TypeCreate,
    RF_TypeCreate2,
    RF_AllocationCreateTyped,
    RF_AllocationCreateFromBitmap,
    RF_AllocationCubeCreateFromBitmap,
    RF_AllocationGetSurface,
    RF_AllocationSetSurface,
    RF_AllocationAdapterCreate,
    RF_AllocationAdapterOffset,
    RF_ContextFinish,
    RF_ContextDump,
    RF_ContextSetPriority,
    RF_ContextDestroyWorker,
    RF_AssignName,
    RF_ObjDestroy,
    RF_ElementCreate,
    RF_ElementCreate2,
    RF_AllocationCopyToBitmap,
    RF_AllocationGetPointer,
    RF_Allocation1DData,
    RF_Allocation1DElementData,
    RF_AllocationElementData,
    RF_Allocation2DData,
    RF_Allocation3DData,
    RF_AllocationGenerateMipmaps,
    RF_AllocationRead,
    RF_Allocation1DRead,
    RF_AllocationElementRead,
    RF_Allocation2DRead,
    RF_Allocation3DRead,
    RF_AllocationSyncAll,
    RF_AllocationResize1D,
    RF_AllocationCopy2DRange,
    RF_AllocationCopy3DRange,
    RF_ClosureCreate,
    RF_InvokeClosureCreate,
    RF_ClosureSetArg,
    RF_ClosureSetGlobal,
    RF_SamplerCreate,
    RF_ScriptBindAllocation,
    RF_ScriptSetTimeZone,
    RF_ScriptInvokeIDCreate,
    RF_ScriptInvoke,
    RF_ScriptInvokeV,
    RF_ScriptForEach,
    RF_ScriptForEachMulti,
    RF_ScriptSetVarI,
    RF_ScriptSetVarObj,
    RF_ScriptSetVarJ,
    RF_ScriptSetVarF,
    RF_ScriptSetVarD,
    RF_ScriptSetVarV,
    RF_ScriptGetVarV,
    RF_ScriptSetVarVE,
    RF_ScriptCCreate,
    RF_ScriptIntrinsicCreate,
    RF_ScriptKernelIDCreate,
    RF_ScriptFieldIDCreate,
    RF_ScriptGroupCreate,
    RF_ScriptGroupSetOutput,
    RF_ScriptGroupSetInput,
    RF_ScriptGroupExecute,
    RF_ScriptGroup2Create,
    RF_AllocationIoSend,
    RF_AllocationIoReceive,
    RF_ProgramStoreCreate,
    RF_ProgramRasterCreate,
    RF_ProgramBindConstants,
    RF_ProgramBindTexture,
    RF_ProgramBindSampler,
    RF_ProgramFragmentCreate,
    RF_ProgramVertexCreate,
    RF_FontCreateFromFile,
    RF_FontCreateFromMemory,
    RF_MeshCreate,
    RF_ContextBindProgramStore,
    RF_ContextBindProgramFragment,
    RF_ContextBindProgramVertex,
    RF_ContextBindProgramRaster,
    RF_ContextBindFont,
    RF_ContextSetSurface,
    RF_ContextBindRootScript,
    RF_ContextPause,
    RF_ContextResume,
};
static RsApiEntrypoints_t *s_CurrentTable = &s_LocalTable;

extern "C" void rsContextDestroy (RsContext rsc)
{
    s_CurrentTable->ContextDestroy((Context *)rsc);
}

extern "C" RsMessageToClientType rsContextGetMessage (RsContext rsc, void * data, size_t data_length, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    return s_CurrentTable->ContextGetMessage((Context *)rsc, data, data_length, receiveLen, receiveLen_length, usrID, usrID_length);
}

extern "C" RsMessageToClientType rsContextPeekMessage (RsContext rsc, size_t * receiveLen, size_t receiveLen_length, uint32_t * usrID, size_t usrID_length)
{
    return s_CurrentTable->ContextPeekMessage((Context *)rsc, receiveLen, receiveLen_length, usrID, usrID_length);
}

extern "C" void rsContextSendMessage (RsContext rsc, uint32_t id, const uint8_t * data, size_t data_length)
{
    s_CurrentTable->ContextSendMessage((Context *)rsc, id, data, data_length);
}

extern "C" void rsContextInitToClient (RsContext rsc)
{
    s_CurrentTable->ContextInitToClient((Context *)rsc);
}

extern "C" void rsContextDeinitToClient (RsContext rsc)
{
    s_CurrentTable->ContextDeinitToClient((Context *)rsc);
}

extern "C" void rsContextSetCacheDir (RsContext rsc, const char * cacheDir, size_t cacheDir_length)
{
    s_CurrentTable->ContextSetCacheDir((Context *)rsc, cacheDir, cacheDir_length);
}

extern "C" RsType rsTypeCreate (RsContext rsc, RsElement e, uint32_t dimX, uint32_t dimY, uint32_t dimZ, bool mipmaps, bool faces, uint32_t yuv)
{
    return s_CurrentTable->TypeCreate((Context *)rsc, e, dimX, dimY, dimZ, mipmaps, faces, yuv);
}

extern "C" RsType rsTypeCreate2 (RsContext rsc, const RsTypeCreateParams * dat, size_t dat_length)
{
    return s_CurrentTable->TypeCreate2((Context *)rsc, dat, dat_length);
}

extern "C" RsAllocation rsAllocationCreateTyped (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, uint32_t usages, uintptr_t ptr)
{
    return s_CurrentTable->AllocationCreateTyped((Context *)rsc, vtype, mipmaps, usages, ptr);
}

extern "C" RsAllocation rsAllocationCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    return s_CurrentTable->AllocationCreateFromBitmap((Context *)rsc, vtype, mipmaps, data, data_length, usages);
}

extern "C" RsAllocation rsAllocationCubeCreateFromBitmap (RsContext rsc, RsType vtype, RsAllocationMipmapControl mipmaps, const void * data, size_t data_length, uint32_t usages)
{
    return s_CurrentTable->AllocationCubeCreateFromBitmap((Context *)rsc, vtype, mipmaps, data, data_length, usages);
}

extern "C" RsNativeWindow rsAllocationGetSurface (RsContext rsc, RsAllocation alloc)
{
    return s_CurrentTable->AllocationGetSurface((Context *)rsc, alloc);
}

extern "C" void rsAllocationSetSurface (RsContext rsc, RsAllocation alloc, RsNativeWindow sur)
{
    s_CurrentTable->AllocationSetSurface((Context *)rsc, alloc, sur);
}

extern "C" RsAllocation rsAllocationAdapterCreate (RsContext rsc, RsType vtype, RsAllocation baseAlloc)
{
    return s_CurrentTable->AllocationAdapterCreate((Context *)rsc, vtype, baseAlloc);
}

extern "C" void rsAllocationAdapterOffset (RsContext rsc, RsAllocation alloc, const uint32_t * offsets, size_t offsets_length)
{
    s_CurrentTable->AllocationAdapterOffset((Context *)rsc, alloc, offsets, offsets_length);
}

extern "C" void rsContextFinish (RsContext rsc)
{
    s_CurrentTable->ContextFinish((Context *)rsc);
}

extern "C" void rsContextDump (RsContext rsc, int32_t bits)
{
    s_CurrentTable->ContextDump((Context *)rsc, bits);
}

extern "C" void rsContextSetPriority (RsContext rsc, int32_t priority)
{
    s_CurrentTable->ContextSetPriority((Context *)rsc, priority);
}

extern "C" void rsContextDestroyWorker (RsContext rsc)
{
    s_CurrentTable->ContextDestroyWorker((Context *)rsc);
}

extern "C" void rsAssignName (RsContext rsc, RsObjectBase obj, const char * name, size_t name_length)
{
    s_CurrentTable->AssignName((Context *)rsc, obj, name, name_length);
}

extern "C" void rsObjDestroy (RsContext rsc, RsAsyncVoidPtr objPtr)
{
    s_CurrentTable->ObjDestroy((Context *)rsc, objPtr);
}

extern "C" RsElement rsElementCreate (RsContext rsc, RsDataType mType, RsDataKind mKind, bool mNormalized, uint32_t mVectorSize)
{
    return s_CurrentTable->ElementCreate((Context *)rsc, mType, mKind, mNormalized, mVectorSize);
}

extern "C" RsElement rsElementCreate2 (RsContext rsc, const RsElement * elements, size_t elements_length, const char ** names, size_t names_length_length, const size_t * names_length, const uint32_t * arraySize, size_t arraySize_length)
{
    return s_CurrentTable->ElementCreate2((Context *)rsc, elements, elements_length, names, names_length_length, names_length, arraySize, arraySize_length);
}

extern "C" void rsAllocationCopyToBitmap (RsContext rsc, RsAllocation alloc, void * data, size_t data_length)
{
    s_CurrentTable->AllocationCopyToBitmap((Context *)rsc, alloc, data, data_length);
}

extern "C" void * rsAllocationGetPointer (RsContext rsc, RsAllocation va, uint32_t lod, RsAllocationCubemapFace face, uint32_t z, uint32_t array, size_t * stride, size_t stride_length)
{
    return s_CurrentTable->AllocationGetPointer((Context *)rsc, va, lod, face, z, array, stride, stride_length);
}

extern "C" void rsAllocation1DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, const void * data, size_t data_length)
{
    s_CurrentTable->Allocation1DData((Context *)rsc, va, xoff, lod, count, data, data_length);
}

extern "C" void rsAllocation1DElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    s_CurrentTable->Allocation1DElementData((Context *)rsc, va, x, lod, data, data_length, comp_offset);
}

extern "C" void rsAllocationElementData (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, const void * data, size_t data_length, size_t comp_offset)
{
    s_CurrentTable->AllocationElementData((Context *)rsc, va, x, y, z, lod, data, data_length, comp_offset);
}

extern "C" void rsAllocation2DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, const void * data, size_t data_length, size_t stride)
{
    s_CurrentTable->Allocation2DData((Context *)rsc, va, xoff, yoff, lod, face, w, h, data, data_length, stride);
}

extern "C" void rsAllocation3DData (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, const void * data, size_t data_length, size_t stride)
{
    s_CurrentTable->Allocation3DData((Context *)rsc, va, xoff, yoff, zoff, lod, w, h, d, data, data_length, stride);
}

extern "C" void rsAllocationGenerateMipmaps (RsContext rsc, RsAllocation va)
{
    s_CurrentTable->AllocationGenerateMipmaps((Context *)rsc, va);
}

extern "C" void rsAllocationRead (RsContext rsc, RsAllocation va, void * data, size_t data_length)
{
    s_CurrentTable->AllocationRead((Context *)rsc, va, data, data_length);
}

extern "C" void rsAllocation1DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t lod, uint32_t count, void * data, size_t data_length)
{
    s_CurrentTable->Allocation1DRead((Context *)rsc, va, xoff, lod, count, data, data_length);
}

extern "C" void rsAllocationElementRead (RsContext rsc, RsAllocation va, uint32_t x, uint32_t y, uint32_t z, uint32_t lod, void * data, size_t data_length, size_t comp_offset)
{
    s_CurrentTable->AllocationElementRead((Context *)rsc, va, x, y, z, lod, data, data_length, comp_offset);
}

extern "C" void rsAllocation2DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t lod, RsAllocationCubemapFace face, uint32_t w, uint32_t h, void * data, size_t data_length, size_t stride)
{
    s_CurrentTable->Allocation2DRead((Context *)rsc, va, xoff, yoff, lod, face, w, h, data, data_length, stride);
}

extern "C" void rsAllocation3DRead (RsContext rsc, RsAllocation va, uint32_t xoff, uint32_t yoff, uint32_t zoff, uint32_t lod, uint32_t w, uint32_t h, uint32_t d, void * data, size_t data_length, size_t stride)
{
    s_CurrentTable->Allocation3DRead((Context *)rsc, va, xoff, yoff, zoff, lod, w, h, d, data, data_length, stride);
}

extern "C" void rsAllocationSyncAll (RsContext rsc, RsAllocation va, RsAllocationUsageType src)
{
    s_CurrentTable->AllocationSyncAll((Context *)rsc, va, src);
}

extern "C" void rsAllocationResize1D (RsContext rsc, RsAllocation va, uint32_t dimX)
{
    s_CurrentTable->AllocationResize1D((Context *)rsc, va, dimX);
}

extern "C" void rsAllocationCopy2DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destMip, uint32_t destFace, uint32_t width, uint32_t height, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcMip, uint32_t srcFace)
{
    s_CurrentTable->AllocationCopy2DRange((Context *)rsc, dest, destXoff, destYoff, destMip, destFace, width, height, src, srcXoff, srcYoff, srcMip, srcFace);
}

extern "C" void rsAllocationCopy3DRange (RsContext rsc, RsAllocation dest, uint32_t destXoff, uint32_t destYoff, uint32_t destZoff, uint32_t destMip, uint32_t width, uint32_t height, uint32_t depth, RsAllocation src, uint32_t srcXoff, uint32_t srcYoff, uint32_t srcZoff, uint32_t srcMip)
{
    s_CurrentTable->AllocationCopy3DRange((Context *)rsc, dest, destXoff, destYoff, destZoff, destMip, width, height, depth, src, srcXoff, srcYoff, srcZoff, srcMip);
}

extern "C" RsClosure rsClosureCreate (RsContext rsc, RsScriptKernelID kernelID, RsAllocation returnValue, RsScriptFieldID * fieldIDs, size_t fieldIDs_length, uintptr_t * values, size_t values_length, int * sizes, size_t sizes_length, RsClosure * depClosures, size_t depClosures_length, RsScriptFieldID * depFieldIDs, size_t depFieldIDs_length)
{
    return s_CurrentTable->ClosureCreate((Context *)rsc, kernelID, returnValue, fieldIDs, fieldIDs_length, values, values_length, sizes, sizes_length, depClosures, depClosures_length, depFieldIDs, depFieldIDs_length);
}

extern "C" RsClosure rsInvokeClosureCreate (RsContext rsc, RsScriptInvokeID invokeID, const void * params, size_t params_length, const RsScriptFieldID * fieldIDs, size_t fieldIDs_length, const uintptr_t * values, size_t values_length, const int * sizes, size_t sizes_length)
{
    return s_CurrentTable->InvokeClosureCreate((Context *)rsc, invokeID, params, params_length, fieldIDs, fieldIDs_length, values, values_length, sizes, sizes_length);
}

extern "C" void rsClosureSetArg (RsContext rsc, RsClosure closureID, uint32_t index, uintptr_t value, size_t valueSize)
{
    s_CurrentTable->ClosureSetArg((Context *)rsc, closureID, index, value, valueSize);
}

extern "C" void rsClosureSetGlobal (RsContext rsc, RsClosure closureID, RsScriptFieldID fieldID, uintptr_t value, size_t valueSize)
{
    s_CurrentTable->ClosureSetGlobal((Context *)rsc, closureID, fieldID, value, valueSize);
}

extern "C" RsSampler rsSamplerCreate (RsContext rsc, RsSamplerValue magFilter, RsSamplerValue minFilter, RsSamplerValue wrapS, RsSamplerValue wrapT, RsSamplerValue wrapR, float mAniso)
{
    return s_CurrentTable->SamplerCreate((Context *)rsc, magFilter, minFilter, wrapS, wrapT, wrapR, mAniso);
}

extern "C" void rsScriptBindAllocation (RsContext rsc, RsScript vtm, RsAllocation va, uint32_t slot)
{
    s_CurrentTable->ScriptBindAllocation((Context *)rsc, vtm, va, slot);
}

extern "C" void rsScriptSetTimeZone (RsContext rsc, RsScript s, const char * timeZone, size_t timeZone_length)
{
    s_CurrentTable->ScriptSetTimeZone((Context *)rsc, s, timeZone, timeZone_length);
}

extern "C" RsScriptInvokeID rsScriptInvokeIDCreate (RsContext rsc, RsScript s, uint32_t slot)
{
    return s_CurrentTable->ScriptInvokeIDCreate((Context *)rsc, s, slot);
}

extern "C" void rsScriptInvoke (RsContext rsc, RsScript s, uint32_t slot)
{
    s_CurrentTable->ScriptInvoke((Context *)rsc, s, slot);
}

extern "C" void rsScriptInvokeV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    s_CurrentTable->ScriptInvokeV((Context *)rsc, s, slot, data, data_length);
}

extern "C" void rsScriptForEach (RsContext rsc, RsScript s, uint32_t slot, RsAllocation ain, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    s_CurrentTable->ScriptForEach((Context *)rsc, s, slot, ain, aout, usr, usr_length, sc, sc_length);
}

extern "C" void rsScriptForEachMulti (RsContext rsc, RsScript s, uint32_t slot, RsAllocation * ains, size_t ains_length, RsAllocation aout, const void * usr, size_t usr_length, const RsScriptCall * sc, size_t sc_length)
{
    s_CurrentTable->ScriptForEachMulti((Context *)rsc, s, slot, ains, ains_length, aout, usr, usr_length, sc, sc_length);
}

extern "C" void rsScriptSetVarI (RsContext rsc, RsScript s, uint32_t slot, int value)
{
    s_CurrentTable->ScriptSetVarI((Context *)rsc, s, slot, value);
}

extern "C" void rsScriptSetVarObj (RsContext rsc, RsScript s, uint32_t slot, RsObjectBase value)
{
    s_CurrentTable->ScriptSetVarObj((Context *)rsc, s, slot, value);
}

extern "C" void rsScriptSetVarJ (RsContext rsc, RsScript s, uint32_t slot, int64_t value)
{
    s_CurrentTable->ScriptSetVarJ((Context *)rsc, s, slot, value);
}

extern "C" void rsScriptSetVarF (RsContext rsc, RsScript s, uint32_t slot, float value)
{
    s_CurrentTable->ScriptSetVarF((Context *)rsc, s, slot, value);
}

extern "C" void rsScriptSetVarD (RsContext rsc, RsScript s, uint32_t slot, double value)
{
    s_CurrentTable->ScriptSetVarD((Context *)rsc, s, slot, value);
}

extern "C" void rsScriptSetVarV (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length)
{
    s_CurrentTable->ScriptSetVarV((Context *)rsc, s, slot, data, data_length);
}

extern "C" void rsScriptGetVarV (RsContext rsc, RsScript s, uint32_t slot, void * data, size_t data_length)
{
    s_CurrentTable->ScriptGetVarV((Context *)rsc, s, slot, data, data_length);
}

extern "C" void rsScriptSetVarVE (RsContext rsc, RsScript s, uint32_t slot, const void * data, size_t data_length, RsElement e, const uint32_t * dims, size_t dims_length)
{
    s_CurrentTable->ScriptSetVarVE((Context *)rsc, s, slot, data, data_length, e, dims, dims_length);
}

extern "C" RsScript rsScriptCCreate (RsContext rsc, const char * resName, size_t resName_length, const char * cacheDir, size_t cacheDir_length, const char * text, size_t text_length)
{
    return s_CurrentTable->ScriptCCreate((Context *)rsc, resName, resName_length, cacheDir, cacheDir_length, text, text_length);
}

extern "C" RsScript rsScriptIntrinsicCreate (RsContext rsc, uint32_t id, RsElement eid)
{
    return s_CurrentTable->ScriptIntrinsicCreate((Context *)rsc, id, eid);
}

extern "C" RsScriptKernelID rsScriptKernelIDCreate (RsContext rsc, RsScript sid, int slot, int sig)
{
    return s_CurrentTable->ScriptKernelIDCreate((Context *)rsc, sid, slot, sig);
}

extern "C" RsScriptFieldID rsScriptFieldIDCreate (RsContext rsc, RsScript sid, int slot)
{
    return s_CurrentTable->ScriptFieldIDCreate((Context *)rsc, sid, slot);
}

extern "C" RsScriptGroup rsScriptGroupCreate (RsContext rsc, RsScriptKernelID * kernels, size_t kernels_length, RsScriptKernelID * src, size_t src_length, RsScriptKernelID * dstK, size_t dstK_length, RsScriptFieldID * dstF, size_t dstF_length, const RsType * type, size_t type_length)
{
    return s_CurrentTable->ScriptGroupCreate((Context *)rsc, kernels, kernels_length, src, src_length, dstK, dstK_length, dstF, dstF_length, type, type_length);
}

extern "C" void rsScriptGroupSetOutput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    s_CurrentTable->ScriptGroupSetOutput((Context *)rsc, group, kernel, alloc);
}

extern "C" void rsScriptGroupSetInput (RsContext rsc, RsScriptGroup group, RsScriptKernelID kernel, RsAllocation alloc)
{
    s_CurrentTable->ScriptGroupSetInput((Context *)rsc, group, kernel, alloc);
}

extern "C" void rsScriptGroupExecute (RsContext rsc, RsScriptGroup group)
{
    s_CurrentTable->ScriptGroupExecute((Context *)rsc, group);
}

extern "C" RsScriptGroup2 rsScriptGroup2Create (RsContext rsc, const char * name, size_t name_length, const char * cacheDir, size_t cacheDir_length, RsClosure * closures, size_t closures_length)
{
    return s_CurrentTable->ScriptGroup2Create((Context *)rsc, name, name_length, cacheDir, cacheDir_length, closures, closures_length);
}

extern "C" void rsAllocationIoSend (RsContext rsc, RsAllocation alloc)
{
    s_CurrentTable->AllocationIoSend((Context *)rsc, alloc);
}

extern "C" void rsAllocationIoReceive (RsContext rsc, RsAllocation alloc)
{
    s_CurrentTable->AllocationIoReceive((Context *)rsc, alloc);
}

extern "C" RsProgramStore rsProgramStoreCreate (RsContext rsc, bool colorMaskR, bool colorMaskG, bool colorMaskB, bool colorMaskA, bool depthMask, bool ditherEnable, RsBlendSrcFunc srcFunc, RsBlendDstFunc destFunc, RsDepthFunc depthFunc)
{
    return s_CurrentTable->ProgramStoreCreate((Context *)rsc, colorMaskR, colorMaskG, colorMaskB, colorMaskA, depthMask, ditherEnable, srcFunc, destFunc, depthFunc);
}

extern "C" RsProgramRaster rsProgramRasterCreate (RsContext rsc, bool pointSprite, RsCullMode cull)
{
    return s_CurrentTable->ProgramRasterCreate((Context *)rsc, pointSprite, cull);
}

extern "C" void rsProgramBindConstants (RsContext rsc, RsProgram vp, uint32_t slot, RsAllocation constants)
{
    s_CurrentTable->ProgramBindConstants((Context *)rsc, vp, slot, constants);
}

extern "C" void rsProgramBindTexture (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsAllocation a)
{
    s_CurrentTable->ProgramBindTexture((Context *)rsc, pf, slot, a);
}

extern "C" void rsProgramBindSampler (RsContext rsc, RsProgramFragment pf, uint32_t slot, RsSampler s)
{
    s_CurrentTable->ProgramBindSampler((Context *)rsc, pf, slot, s);
}

extern "C" RsProgramFragment rsProgramFragmentCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    return s_CurrentTable->ProgramFragmentCreate((Context *)rsc, shaderText, shaderText_length, textureNames, textureNames_length_length, textureNames_length, params, params_length);
}

extern "C" RsProgramVertex rsProgramVertexCreate (RsContext rsc, const char * shaderText, size_t shaderText_length, const char ** textureNames, size_t textureNames_length_length, const size_t * textureNames_length, const uintptr_t * params, size_t params_length)
{
    return s_CurrentTable->ProgramVertexCreate((Context *)rsc, shaderText, shaderText_length, textureNames, textureNames_length_length, textureNames_length, params, params_length);
}

extern "C" RsFont rsFontCreateFromFile (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi)
{
    return s_CurrentTable->FontCreateFromFile((Context *)rsc, name, name_length, fontSize, dpi);
}

extern "C" RsFont rsFontCreateFromMemory (RsContext rsc, const char * name, size_t name_length, float fontSize, uint32_t dpi, const void * data, size_t data_length)
{
    return s_CurrentTable->FontCreateFromMemory((Context *)rsc, name, name_length, fontSize, dpi, data, data_length);
}

extern "C" RsMesh rsMeshCreate (RsContext rsc, RsAllocation * vtx, size_t vtx_length, RsAllocation * idx, size_t idx_length, uint32_t * primType, size_t primType_length)
{
    return s_CurrentTable->MeshCreate((Context *)rsc, vtx, vtx_length, idx, idx_length, primType, primType_length);
}

extern "C" void rsContextBindProgramStore (RsContext rsc, RsProgramStore pgm)
{
    s_CurrentTable->ContextBindProgramStore((Context *)rsc, pgm);
}

extern "C" void rsContextBindProgramFragment (RsContext rsc, RsProgramFragment pgm)
{
    s_CurrentTable->ContextBindProgramFragment((Context *)rsc, pgm);
}

extern "C" void rsContextBindProgramVertex (RsContext rsc, RsProgramVertex pgm)
{
    s_CurrentTable->ContextBindProgramVertex((Context *)rsc, pgm);
}

extern "C" void rsContextBindProgramRaster (RsContext rsc, RsProgramRaster pgm)
{
    s_CurrentTable->ContextBindProgramRaster((Context *)rsc, pgm);
}

extern "C" void rsContextBindFont (RsContext rsc, RsFont pgm)
{
    s_CurrentTable->ContextBindFont((Context *)rsc, pgm);
}

extern "C" void rsContextSetSurface (RsContext rsc, uint32_t width, uint32_t height, RsNativeWindow sur)
{
    s_CurrentTable->ContextSetSurface((Context *)rsc, width, height, sur);
}

extern "C" void rsContextBindRootScript (RsContext rsc, RsScript sampler)
{
    s_CurrentTable->ContextBindRootScript((Context *)rsc, sampler);
}

extern "C" void rsContextPause (RsContext rsc)
{
    s_CurrentTable->ContextPause((Context *)rsc);
}

extern "C" void rsContextResume (RsContext rsc)
{
    s_CurrentTable->ContextResume((Context *)rsc);
}

