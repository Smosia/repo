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

namespace android {
namespace renderscript {

void rsp_ContextSendMessage(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ContextSendMessage *cmd = static_cast<const RS_CMD_ContextSendMessage *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ContextSendMessage)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ContextSendMessage(con,
           cmd->id,
           cmd->data_length == 0 ? NULL : (const uint8_t *)&baseData[(intptr_t)cmd->data],
           cmd->data_length);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ContextSendMessage))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_AllocationGetSurface(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationGetSurface *cmd = static_cast<const RS_CMD_AllocationGetSurface *>(vp);
    
    RsNativeWindow ret = rsi_AllocationGetSurface(con,
           cmd->alloc);
    con->mIO.coreSetReturn(&ret, sizeof(ret));
};

void rsp_AllocationSetSurface(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationSetSurface *cmd = static_cast<const RS_CMD_AllocationSetSurface *>(vp);
    rsi_AllocationSetSurface(con,
           cmd->alloc,
           cmd->sur);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_AllocationAdapterOffset(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationAdapterOffset *cmd = static_cast<const RS_CMD_AllocationAdapterOffset *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_AllocationAdapterOffset)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_AllocationAdapterOffset(con,
           cmd->alloc,
           cmd->offsets_length == 0 ? NULL : (const uint32_t *)&baseData[(intptr_t)cmd->offsets],
           cmd->offsets_length);
    size_t totalSize = 0;
    totalSize += cmd->offsets_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_AllocationAdapterOffset))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ContextFinish(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ContextFinish *cmd = static_cast<const RS_CMD_ContextFinish *>(vp);
    rsi_ContextFinish(con);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_ContextDump(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ContextDump *cmd = static_cast<const RS_CMD_ContextDump *>(vp);
    rsi_ContextDump(con,
           cmd->bits);
};

void rsp_ContextSetPriority(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ContextSetPriority *cmd = static_cast<const RS_CMD_ContextSetPriority *>(vp);
    rsi_ContextSetPriority(con,
           cmd->priority);
};

void rsp_ContextDestroyWorker(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ContextDestroyWorker *cmd = static_cast<const RS_CMD_ContextDestroyWorker *>(vp);
    rsi_ContextDestroyWorker(con);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_AssignName(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AssignName *cmd = static_cast<const RS_CMD_AssignName *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_AssignName)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_AssignName(con,
           cmd->obj,
           cmd->name_length == 0 ? NULL : (const char *)&baseData[(intptr_t)cmd->name],
           cmd->name_length);
    size_t totalSize = 0;
    totalSize += cmd->name_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_AssignName))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ObjDestroy(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ObjDestroy *cmd = static_cast<const RS_CMD_ObjDestroy *>(vp);
    rsi_ObjDestroy(con,
           cmd->objPtr);
};

void rsp_AllocationCopyToBitmap(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationCopyToBitmap *cmd = static_cast<const RS_CMD_AllocationCopyToBitmap *>(vp);
    rsi_AllocationCopyToBitmap(con,
           cmd->alloc,
           cmd->data,
           cmd->data_length);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_AllocationGetPointer(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationGetPointer *cmd = static_cast<const RS_CMD_AllocationGetPointer *>(vp);
    
    void * ret = rsi_AllocationGetPointer(con,
           cmd->va,
           cmd->lod,
           cmd->face,
           cmd->z,
           cmd->array,
           cmd->stride,
           cmd->stride_length);
    con->mIO.coreSetReturn(&ret, sizeof(ret));
};

void rsp_Allocation1DData(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation1DData *cmd = static_cast<const RS_CMD_Allocation1DData *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_Allocation1DData)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_Allocation1DData(con,
           cmd->va,
           cmd->xoff,
           cmd->lod,
           cmd->count,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_Allocation1DData))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_Allocation1DElementData(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation1DElementData *cmd = static_cast<const RS_CMD_Allocation1DElementData *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_Allocation1DElementData)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_Allocation1DElementData(con,
           cmd->va,
           cmd->x,
           cmd->lod,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length,
           cmd->comp_offset);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_Allocation1DElementData))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_AllocationElementData(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationElementData *cmd = static_cast<const RS_CMD_AllocationElementData *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_AllocationElementData)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_AllocationElementData(con,
           cmd->va,
           cmd->x,
           cmd->y,
           cmd->z,
           cmd->lod,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length,
           cmd->comp_offset);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_AllocationElementData))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_Allocation2DData(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation2DData *cmd = static_cast<const RS_CMD_Allocation2DData *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_Allocation2DData)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_Allocation2DData(con,
           cmd->va,
           cmd->xoff,
           cmd->yoff,
           cmd->lod,
           cmd->face,
           cmd->w,
           cmd->h,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length,
           cmd->stride);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_Allocation2DData))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_Allocation3DData(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation3DData *cmd = static_cast<const RS_CMD_Allocation3DData *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_Allocation3DData)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_Allocation3DData(con,
           cmd->va,
           cmd->xoff,
           cmd->yoff,
           cmd->zoff,
           cmd->lod,
           cmd->w,
           cmd->h,
           cmd->d,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length,
           cmd->stride);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_Allocation3DData))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_AllocationGenerateMipmaps(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationGenerateMipmaps *cmd = static_cast<const RS_CMD_AllocationGenerateMipmaps *>(vp);
    rsi_AllocationGenerateMipmaps(con,
           cmd->va);
};

void rsp_AllocationRead(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationRead *cmd = static_cast<const RS_CMD_AllocationRead *>(vp);
    rsi_AllocationRead(con,
           cmd->va,
           cmd->data,
           cmd->data_length);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_Allocation1DRead(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation1DRead *cmd = static_cast<const RS_CMD_Allocation1DRead *>(vp);
    rsi_Allocation1DRead(con,
           cmd->va,
           cmd->xoff,
           cmd->lod,
           cmd->count,
           cmd->data,
           cmd->data_length);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_AllocationElementRead(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationElementRead *cmd = static_cast<const RS_CMD_AllocationElementRead *>(vp);
    rsi_AllocationElementRead(con,
           cmd->va,
           cmd->x,
           cmd->y,
           cmd->z,
           cmd->lod,
           cmd->data,
           cmd->data_length,
           cmd->comp_offset);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_Allocation2DRead(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation2DRead *cmd = static_cast<const RS_CMD_Allocation2DRead *>(vp);
    rsi_Allocation2DRead(con,
           cmd->va,
           cmd->xoff,
           cmd->yoff,
           cmd->lod,
           cmd->face,
           cmd->w,
           cmd->h,
           cmd->data,
           cmd->data_length,
           cmd->stride);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_Allocation3DRead(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_Allocation3DRead *cmd = static_cast<const RS_CMD_Allocation3DRead *>(vp);
    rsi_Allocation3DRead(con,
           cmd->va,
           cmd->xoff,
           cmd->yoff,
           cmd->zoff,
           cmd->lod,
           cmd->w,
           cmd->h,
           cmd->d,
           cmd->data,
           cmd->data_length,
           cmd->stride);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_AllocationSyncAll(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationSyncAll *cmd = static_cast<const RS_CMD_AllocationSyncAll *>(vp);
    rsi_AllocationSyncAll(con,
           cmd->va,
           cmd->src);
};

void rsp_AllocationResize1D(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationResize1D *cmd = static_cast<const RS_CMD_AllocationResize1D *>(vp);
    rsi_AllocationResize1D(con,
           cmd->va,
           cmd->dimX);
};

void rsp_AllocationCopy2DRange(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationCopy2DRange *cmd = static_cast<const RS_CMD_AllocationCopy2DRange *>(vp);
    rsi_AllocationCopy2DRange(con,
           cmd->dest,
           cmd->destXoff,
           cmd->destYoff,
           cmd->destMip,
           cmd->destFace,
           cmd->width,
           cmd->height,
           cmd->src,
           cmd->srcXoff,
           cmd->srcYoff,
           cmd->srcMip,
           cmd->srcFace);
};

void rsp_AllocationCopy3DRange(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationCopy3DRange *cmd = static_cast<const RS_CMD_AllocationCopy3DRange *>(vp);
    rsi_AllocationCopy3DRange(con,
           cmd->dest,
           cmd->destXoff,
           cmd->destYoff,
           cmd->destZoff,
           cmd->destMip,
           cmd->width,
           cmd->height,
           cmd->depth,
           cmd->src,
           cmd->srcXoff,
           cmd->srcYoff,
           cmd->srcZoff,
           cmd->srcMip);
};

void rsp_ClosureSetArg(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ClosureSetArg *cmd = static_cast<const RS_CMD_ClosureSetArg *>(vp);
    rsi_ClosureSetArg(con,
           cmd->closureID,
           cmd->index,
           cmd->value,
           cmd->valueSize);
};

void rsp_ClosureSetGlobal(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ClosureSetGlobal *cmd = static_cast<const RS_CMD_ClosureSetGlobal *>(vp);
    rsi_ClosureSetGlobal(con,
           cmd->closureID,
           cmd->fieldID,
           cmd->value,
           cmd->valueSize);
};

void rsp_ScriptBindAllocation(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptBindAllocation *cmd = static_cast<const RS_CMD_ScriptBindAllocation *>(vp);
    rsi_ScriptBindAllocation(con,
           cmd->vtm,
           cmd->va,
           cmd->slot);
};

void rsp_ScriptSetTimeZone(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetTimeZone *cmd = static_cast<const RS_CMD_ScriptSetTimeZone *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ScriptSetTimeZone)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ScriptSetTimeZone(con,
           cmd->s,
           cmd->timeZone_length == 0 ? NULL : (const char *)&baseData[(intptr_t)cmd->timeZone],
           cmd->timeZone_length);
    size_t totalSize = 0;
    totalSize += cmd->timeZone_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ScriptSetTimeZone))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ScriptInvokeIDCreate(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptInvokeIDCreate *cmd = static_cast<const RS_CMD_ScriptInvokeIDCreate *>(vp);
    
    RsScriptInvokeID ret = rsi_ScriptInvokeIDCreate(con,
           cmd->s,
           cmd->slot);
    con->mIO.coreSetReturn(&ret, sizeof(ret));
};

void rsp_ScriptInvoke(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptInvoke *cmd = static_cast<const RS_CMD_ScriptInvoke *>(vp);
    rsi_ScriptInvoke(con,
           cmd->s,
           cmd->slot);
};

void rsp_ScriptInvokeV(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptInvokeV *cmd = static_cast<const RS_CMD_ScriptInvokeV *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ScriptInvokeV)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ScriptInvokeV(con,
           cmd->s,
           cmd->slot,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ScriptInvokeV))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ScriptForEach(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptForEach *cmd = static_cast<const RS_CMD_ScriptForEach *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ScriptForEach)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ScriptForEach(con,
           cmd->s,
           cmd->slot,
           cmd->ain,
           cmd->aout,
           cmd->usr_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->usr],
           cmd->usr_length,
           cmd->sc_length == 0 ? NULL : (const RsScriptCall *)&baseData[(intptr_t)cmd->sc],
           cmd->sc_length);
    size_t totalSize = 0;
    totalSize += cmd->usr_length;
    totalSize += cmd->sc_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ScriptForEach))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ScriptForEachMulti(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptForEachMulti *cmd = static_cast<const RS_CMD_ScriptForEachMulti *>(vp);
    rsi_ScriptForEachMulti(con,
           cmd->s,
           cmd->slot,
           cmd->ains,
           cmd->ains_length,
           cmd->aout,
           cmd->usr,
           cmd->usr_length,
           cmd->sc,
           cmd->sc_length);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_ScriptSetVarI(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarI *cmd = static_cast<const RS_CMD_ScriptSetVarI *>(vp);
    rsi_ScriptSetVarI(con,
           cmd->s,
           cmd->slot,
           cmd->value);
};

void rsp_ScriptSetVarObj(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarObj *cmd = static_cast<const RS_CMD_ScriptSetVarObj *>(vp);
    rsi_ScriptSetVarObj(con,
           cmd->s,
           cmd->slot,
           cmd->value);
};

void rsp_ScriptSetVarJ(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarJ *cmd = static_cast<const RS_CMD_ScriptSetVarJ *>(vp);
    rsi_ScriptSetVarJ(con,
           cmd->s,
           cmd->slot,
           cmd->value);
};

void rsp_ScriptSetVarF(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarF *cmd = static_cast<const RS_CMD_ScriptSetVarF *>(vp);
    rsi_ScriptSetVarF(con,
           cmd->s,
           cmd->slot,
           cmd->value);
};

void rsp_ScriptSetVarD(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarD *cmd = static_cast<const RS_CMD_ScriptSetVarD *>(vp);
    rsi_ScriptSetVarD(con,
           cmd->s,
           cmd->slot,
           cmd->value);
};

void rsp_ScriptSetVarV(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarV *cmd = static_cast<const RS_CMD_ScriptSetVarV *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ScriptSetVarV)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ScriptSetVarV(con,
           cmd->s,
           cmd->slot,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ScriptSetVarV))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ScriptGetVarV(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptGetVarV *cmd = static_cast<const RS_CMD_ScriptGetVarV *>(vp);
    rsi_ScriptGetVarV(con,
           cmd->s,
           cmd->slot,
           cmd->data,
           cmd->data_length);
    con->mIO.coreSetReturn(NULL, 0);
};

void rsp_ScriptSetVarVE(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptSetVarVE *cmd = static_cast<const RS_CMD_ScriptSetVarVE *>(vp);
    const uint8_t *baseData = 0;
    if (cmdSizeBytes != sizeof(RS_CMD_ScriptSetVarVE)) {
        baseData = &((const uint8_t *)vp)[sizeof(*cmd)];
    }
    rsi_ScriptSetVarVE(con,
           cmd->s,
           cmd->slot,
           cmd->data_length == 0 ? NULL : (const void *)&baseData[(intptr_t)cmd->data],
           cmd->data_length,
           cmd->e,
           cmd->dims_length == 0 ? NULL : (const uint32_t *)&baseData[(intptr_t)cmd->dims],
           cmd->dims_length);
    size_t totalSize = 0;
    totalSize += cmd->data_length;
    totalSize += cmd->dims_length;
    if ((totalSize != 0) && (cmdSizeBytes == sizeof(RS_CMD_ScriptSetVarVE))) {
        con->mIO.coreSetReturn(NULL, 0);
    }
};

void rsp_ScriptCCreate(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptCCreate *cmd = static_cast<const RS_CMD_ScriptCCreate *>(vp);
    
    RsScript ret = rsi_ScriptCCreate(con,
           cmd->resName,
           cmd->resName_length,
           cmd->cacheDir,
           cmd->cacheDir_length,
           cmd->text,
           cmd->text_length);
    con->mIO.coreSetReturn(&ret, sizeof(ret));
};

void rsp_ScriptIntrinsicCreate(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptIntrinsicCreate *cmd = static_cast<const RS_CMD_ScriptIntrinsicCreate *>(vp);
    
    RsScript ret = rsi_ScriptIntrinsicCreate(con,
           cmd->id,
           cmd->eid);
    con->mIO.coreSetReturn(&ret, sizeof(ret));
};

void rsp_ScriptGroupSetOutput(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptGroupSetOutput *cmd = static_cast<const RS_CMD_ScriptGroupSetOutput *>(vp);
    rsi_ScriptGroupSetOutput(con,
           cmd->group,
           cmd->kernel,
           cmd->alloc);
};

void rsp_ScriptGroupSetInput(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptGroupSetInput *cmd = static_cast<const RS_CMD_ScriptGroupSetInput *>(vp);
    rsi_ScriptGroupSetInput(con,
           cmd->group,
           cmd->kernel,
           cmd->alloc);
};

void rsp_ScriptGroupExecute(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_ScriptGroupExecute *cmd = static_cast<const RS_CMD_ScriptGroupExecute *>(vp);
    rsi_ScriptGroupExecute(con,
           cmd->group);
};

void rsp_AllocationIoSend(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationIoSend *cmd = static_cast<const RS_CMD_AllocationIoSend *>(vp);
    rsi_AllocationIoSend(con,
           cmd->alloc);
};

void rsp_AllocationIoReceive(Context *con, const void *vp, size_t cmdSizeBytes) {
    const RS_CMD_AllocationIoReceive *cmd = static_cast<const RS_CMD_AllocationIoReceive *>(vp);
    rsi_AllocationIoReceive(con,
           cmd->alloc);
};

void rspr_ContextDestroy(Context *con, ThreadIO *io) {
    RS_CMD_ContextDestroy cmd;



    rsi_ContextDestroy(con);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextGetMessage(Context *con, ThreadIO *io) {
    RS_CMD_ContextGetMessage cmd;
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.receiveLen_length, sizeof(cmd.receiveLen_length));
    io->coreRead(&cmd.usrID_length, sizeof(cmd.usrID_length));

    cmd.data = (void *)malloc(cmd.data_length);
    cmd.receiveLen = (size_t *)malloc(cmd.receiveLen_length);
    cmd.usrID = (uint32_t *)malloc(cmd.usrID_length);


    RsMessageToClientType ret =
    rsi_ContextGetMessage(con,
           cmd.data,
           cmd.data_length,
           cmd.receiveLen,
           cmd.receiveLen_length,
           cmd.usrID,
           cmd.usrID_length);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);
    io->coreSetReturn((void *)cmd.receiveLen, cmd.receiveLen_length);
    io->coreSetReturn((void *)cmd.usrID, cmd.usrID_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.data);
    free((void *)cmd.receiveLen);
    free((void *)cmd.usrID);
};

void rspr_ContextPeekMessage(Context *con, ThreadIO *io) {
    RS_CMD_ContextPeekMessage cmd;
    io->coreRead(&cmd.receiveLen_length, sizeof(cmd.receiveLen_length));
    io->coreRead(&cmd.usrID_length, sizeof(cmd.usrID_length));

    cmd.receiveLen = (size_t *)malloc(cmd.receiveLen_length);
    cmd.usrID = (uint32_t *)malloc(cmd.usrID_length);


    RsMessageToClientType ret =
    rsi_ContextPeekMessage(con,
           cmd.receiveLen,
           cmd.receiveLen_length,
           cmd.usrID,
           cmd.usrID_length);
    io->coreSetReturn((void *)cmd.receiveLen, cmd.receiveLen_length);
    io->coreSetReturn((void *)cmd.usrID, cmd.usrID_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.receiveLen);
    free((void *)cmd.usrID);
};

void rspr_ContextSendMessage(Context *con, ThreadIO *io) {
    RS_CMD_ContextSendMessage cmd;
    io->coreRead(&cmd.id, sizeof(cmd.id));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (const uint8_t *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_ContextSendMessage(con,
           cmd.id,
           cmd.data,
           cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_ContextInitToClient(Context *con, ThreadIO *io) {
    RS_CMD_ContextInitToClient cmd;



    rsi_ContextInitToClient(con);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextDeinitToClient(Context *con, ThreadIO *io) {
    RS_CMD_ContextDeinitToClient cmd;



    rsi_ContextDeinitToClient(con);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextSetCacheDir(Context *con, ThreadIO *io) {
    RS_CMD_ContextSetCacheDir cmd;
    io->coreRead(&cmd.cacheDir_length, sizeof(cmd.cacheDir_length));

    cmd.cacheDir = (const char *)malloc(cmd.cacheDir_length);
    if (cmd.cacheDir_length) io->coreRead((void *)cmd.cacheDir, cmd.cacheDir_length);


    rsi_ContextSetCacheDir(con,
           cmd.cacheDir,
           cmd.cacheDir_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.cacheDir);
};

void rspr_TypeCreate(Context *con, ThreadIO *io) {
    RS_CMD_TypeCreate cmd;
    io->coreRead(&cmd.e, sizeof(cmd.e));
    io->coreRead(&cmd.dimX, sizeof(cmd.dimX));
    io->coreRead(&cmd.dimY, sizeof(cmd.dimY));
    io->coreRead(&cmd.dimZ, sizeof(cmd.dimZ));
    io->coreRead(&cmd.mipmaps, sizeof(cmd.mipmaps));
    io->coreRead(&cmd.faces, sizeof(cmd.faces));
    io->coreRead(&cmd.yuv, sizeof(cmd.yuv));



    RsType ret =
    rsi_TypeCreate(con,
           cmd.e,
           cmd.dimX,
           cmd.dimY,
           cmd.dimZ,
           cmd.mipmaps,
           cmd.faces,
           cmd.yuv);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_TypeCreate2(Context *con, ThreadIO *io) {
    RS_CMD_TypeCreate2 cmd;
    io->coreRead(&cmd.dat_length, sizeof(cmd.dat_length));

    cmd.dat = (const RsTypeCreateParams *)malloc(cmd.dat_length);
    if (cmd.dat_length) io->coreRead((void *)cmd.dat, cmd.dat_length);


    RsType ret =
    rsi_TypeCreate2(con,
           cmd.dat,
           cmd.dat_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.dat);
};

void rspr_AllocationCreateTyped(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCreateTyped cmd;
    io->coreRead(&cmd.vtype, sizeof(cmd.vtype));
    io->coreRead(&cmd.mipmaps, sizeof(cmd.mipmaps));
    io->coreRead(&cmd.usages, sizeof(cmd.usages));
    io->coreRead(&cmd.ptr, sizeof(cmd.ptr));



    RsAllocation ret =
    rsi_AllocationCreateTyped(con,
           cmd.vtype,
           cmd.mipmaps,
           cmd.usages,
           cmd.ptr);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_AllocationCreateFromBitmap(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCreateFromBitmap cmd;
    io->coreRead(&cmd.vtype, sizeof(cmd.vtype));
    io->coreRead(&cmd.mipmaps, sizeof(cmd.mipmaps));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.usages, sizeof(cmd.usages));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    RsAllocation ret =
    rsi_AllocationCreateFromBitmap(con,
           cmd.vtype,
           cmd.mipmaps,
           cmd.data,
           cmd.data_length,
           cmd.usages);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.data);
};

void rspr_AllocationCubeCreateFromBitmap(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCubeCreateFromBitmap cmd;
    io->coreRead(&cmd.vtype, sizeof(cmd.vtype));
    io->coreRead(&cmd.mipmaps, sizeof(cmd.mipmaps));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.usages, sizeof(cmd.usages));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    RsAllocation ret =
    rsi_AllocationCubeCreateFromBitmap(con,
           cmd.vtype,
           cmd.mipmaps,
           cmd.data,
           cmd.data_length,
           cmd.usages);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.data);
};

void rspr_AllocationGetSurface(Context *con, ThreadIO *io) {
    RS_CMD_AllocationGetSurface cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));



    RsNativeWindow ret =
    rsi_AllocationGetSurface(con,
           cmd.alloc);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_AllocationSetSurface(Context *con, ThreadIO *io) {
    RS_CMD_AllocationSetSurface cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));
    io->coreRead(&cmd.sur, sizeof(cmd.sur));



    rsi_AllocationSetSurface(con,
           cmd.alloc,
           cmd.sur);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationAdapterCreate(Context *con, ThreadIO *io) {
    RS_CMD_AllocationAdapterCreate cmd;
    io->coreRead(&cmd.vtype, sizeof(cmd.vtype));
    io->coreRead(&cmd.baseAlloc, sizeof(cmd.baseAlloc));



    RsAllocation ret =
    rsi_AllocationAdapterCreate(con,
           cmd.vtype,
           cmd.baseAlloc);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_AllocationAdapterOffset(Context *con, ThreadIO *io) {
    RS_CMD_AllocationAdapterOffset cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));
    io->coreRead(&cmd.offsets_length, sizeof(cmd.offsets_length));

    cmd.offsets = (const uint32_t *)malloc(cmd.offsets_length);
    if (cmd.offsets_length) io->coreRead((void *)cmd.offsets, cmd.offsets_length);


    rsi_AllocationAdapterOffset(con,
           cmd.alloc,
           cmd.offsets,
           cmd.offsets_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.offsets);
};

void rspr_ContextFinish(Context *con, ThreadIO *io) {
    RS_CMD_ContextFinish cmd;



    rsi_ContextFinish(con);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextDump(Context *con, ThreadIO *io) {
    RS_CMD_ContextDump cmd;
    io->coreRead(&cmd.bits, sizeof(cmd.bits));



    rsi_ContextDump(con,
           cmd.bits);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextSetPriority(Context *con, ThreadIO *io) {
    RS_CMD_ContextSetPriority cmd;
    io->coreRead(&cmd.priority, sizeof(cmd.priority));



    rsi_ContextSetPriority(con,
           cmd.priority);

    io->coreSetReturn(NULL, 0);
};

void rspr_ContextDestroyWorker(Context *con, ThreadIO *io) {
    RS_CMD_ContextDestroyWorker cmd;



    rsi_ContextDestroyWorker(con);

    io->coreSetReturn(NULL, 0);
};

void rspr_AssignName(Context *con, ThreadIO *io) {
    RS_CMD_AssignName cmd;
    io->coreRead(&cmd.obj, sizeof(cmd.obj));
    io->coreRead(&cmd.name_length, sizeof(cmd.name_length));

    cmd.name = (const char *)malloc(cmd.name_length);
    if (cmd.name_length) io->coreRead((void *)cmd.name, cmd.name_length);


    rsi_AssignName(con,
           cmd.obj,
           cmd.name,
           cmd.name_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.name);
};

void rspr_ObjDestroy(Context *con, ThreadIO *io) {
    RS_CMD_ObjDestroy cmd;
    io->coreRead(&cmd.objPtr, sizeof(cmd.objPtr));



    rsi_ObjDestroy(con,
           cmd.objPtr);

    io->coreSetReturn(NULL, 0);
};

void rspr_ElementCreate(Context *con, ThreadIO *io) {
    RS_CMD_ElementCreate cmd;
    io->coreRead(&cmd.mType, sizeof(cmd.mType));
    io->coreRead(&cmd.mKind, sizeof(cmd.mKind));
    io->coreRead(&cmd.mNormalized, sizeof(cmd.mNormalized));
    io->coreRead(&cmd.mVectorSize, sizeof(cmd.mVectorSize));



    RsElement ret =
    rsi_ElementCreate(con,
           cmd.mType,
           cmd.mKind,
           cmd.mNormalized,
           cmd.mVectorSize);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ElementCreate2(Context *con, ThreadIO *io) {
    RS_CMD_ElementCreate2 cmd;
    io->coreRead(&cmd.elements_length, sizeof(cmd.elements_length));
    io->coreRead(&cmd.names_length_length, sizeof(cmd.names_length_length));
    io->coreRead(&cmd.arraySize_length, sizeof(cmd.arraySize_length));

    cmd.elements = (const RsElement *)malloc(cmd.elements_length);
    if (cmd.elements_length) io->coreRead((void *)cmd.elements, cmd.elements_length);
    cmd.names_length = (const size_t *)malloc(cmd.names_length_length);
    if (cmd.names_length_length) io->coreRead((void *)cmd.names_length, cmd.names_length_length);
    cmd.arraySize = (const uint32_t *)malloc(cmd.arraySize_length);
    if (cmd.arraySize_length) io->coreRead((void *)cmd.arraySize, cmd.arraySize_length);

    for (size_t ct = 0; ct < (cmd.names_length_length / sizeof(cmd.names_length)); ct++) {
        cmd.names = (const char **)malloc(cmd.names_length[ct]);
        io->coreRead(& cmd.names, cmd.names_length[ct]);
    }

    RsElement ret =
    rsi_ElementCreate2(con,
           cmd.elements,
           cmd.elements_length,
           cmd.names,
           cmd.names_length_length,
           cmd.names_length,
           cmd.arraySize,
           cmd.arraySize_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.elements);
    free((void *)cmd.names_length);
    free((void *)cmd.arraySize);
    for (size_t ct = 0; ct < (cmd.names_length_length / sizeof(cmd.names_length)); ct++) {
        free((void *)cmd.names);
    }
};

void rspr_AllocationCopyToBitmap(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCopyToBitmap cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_AllocationCopyToBitmap(con,
           cmd.alloc,
           cmd.data,
           cmd.data_length);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_AllocationGetPointer(Context *con, ThreadIO *io) {
    RS_CMD_AllocationGetPointer cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.face, sizeof(cmd.face));
    io->coreRead(&cmd.z, sizeof(cmd.z));
    io->coreRead(&cmd.array, sizeof(cmd.array));
    io->coreRead(&cmd.stride_length, sizeof(cmd.stride_length));

    cmd.stride = (size_t *)malloc(cmd.stride_length);


    void * ret =
    rsi_AllocationGetPointer(con,
           cmd.va,
           cmd.lod,
           cmd.face,
           cmd.z,
           cmd.array,
           cmd.stride,
           cmd.stride_length);
    io->coreSetReturn((void *)cmd.stride, cmd.stride_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.stride);
};

void rspr_Allocation1DData(Context *con, ThreadIO *io) {
    RS_CMD_Allocation1DData cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.count, sizeof(cmd.count));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_Allocation1DData(con,
           cmd.va,
           cmd.xoff,
           cmd.lod,
           cmd.count,
           cmd.data,
           cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation1DElementData(Context *con, ThreadIO *io) {
    RS_CMD_Allocation1DElementData cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.x, sizeof(cmd.x));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.comp_offset, sizeof(cmd.comp_offset));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_Allocation1DElementData(con,
           cmd.va,
           cmd.x,
           cmd.lod,
           cmd.data,
           cmd.data_length,
           cmd.comp_offset);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_AllocationElementData(Context *con, ThreadIO *io) {
    RS_CMD_AllocationElementData cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.x, sizeof(cmd.x));
    io->coreRead(&cmd.y, sizeof(cmd.y));
    io->coreRead(&cmd.z, sizeof(cmd.z));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.comp_offset, sizeof(cmd.comp_offset));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_AllocationElementData(con,
           cmd.va,
           cmd.x,
           cmd.y,
           cmd.z,
           cmd.lod,
           cmd.data,
           cmd.data_length,
           cmd.comp_offset);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation2DData(Context *con, ThreadIO *io) {
    RS_CMD_Allocation2DData cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.yoff, sizeof(cmd.yoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.face, sizeof(cmd.face));
    io->coreRead(&cmd.w, sizeof(cmd.w));
    io->coreRead(&cmd.h, sizeof(cmd.h));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.stride, sizeof(cmd.stride));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_Allocation2DData(con,
           cmd.va,
           cmd.xoff,
           cmd.yoff,
           cmd.lod,
           cmd.face,
           cmd.w,
           cmd.h,
           cmd.data,
           cmd.data_length,
           cmd.stride);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation3DData(Context *con, ThreadIO *io) {
    RS_CMD_Allocation3DData cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.yoff, sizeof(cmd.yoff));
    io->coreRead(&cmd.zoff, sizeof(cmd.zoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.w, sizeof(cmd.w));
    io->coreRead(&cmd.h, sizeof(cmd.h));
    io->coreRead(&cmd.d, sizeof(cmd.d));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.stride, sizeof(cmd.stride));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_Allocation3DData(con,
           cmd.va,
           cmd.xoff,
           cmd.yoff,
           cmd.zoff,
           cmd.lod,
           cmd.w,
           cmd.h,
           cmd.d,
           cmd.data,
           cmd.data_length,
           cmd.stride);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_AllocationGenerateMipmaps(Context *con, ThreadIO *io) {
    RS_CMD_AllocationGenerateMipmaps cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));



    rsi_AllocationGenerateMipmaps(con,
           cmd.va);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationRead(Context *con, ThreadIO *io) {
    RS_CMD_AllocationRead cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_AllocationRead(con,
           cmd.va,
           cmd.data,
           cmd.data_length);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation1DRead(Context *con, ThreadIO *io) {
    RS_CMD_Allocation1DRead cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.count, sizeof(cmd.count));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_Allocation1DRead(con,
           cmd.va,
           cmd.xoff,
           cmd.lod,
           cmd.count,
           cmd.data,
           cmd.data_length);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_AllocationElementRead(Context *con, ThreadIO *io) {
    RS_CMD_AllocationElementRead cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.x, sizeof(cmd.x));
    io->coreRead(&cmd.y, sizeof(cmd.y));
    io->coreRead(&cmd.z, sizeof(cmd.z));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.comp_offset, sizeof(cmd.comp_offset));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_AllocationElementRead(con,
           cmd.va,
           cmd.x,
           cmd.y,
           cmd.z,
           cmd.lod,
           cmd.data,
           cmd.data_length,
           cmd.comp_offset);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation2DRead(Context *con, ThreadIO *io) {
    RS_CMD_Allocation2DRead cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.yoff, sizeof(cmd.yoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.face, sizeof(cmd.face));
    io->coreRead(&cmd.w, sizeof(cmd.w));
    io->coreRead(&cmd.h, sizeof(cmd.h));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.stride, sizeof(cmd.stride));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_Allocation2DRead(con,
           cmd.va,
           cmd.xoff,
           cmd.yoff,
           cmd.lod,
           cmd.face,
           cmd.w,
           cmd.h,
           cmd.data,
           cmd.data_length,
           cmd.stride);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_Allocation3DRead(Context *con, ThreadIO *io) {
    RS_CMD_Allocation3DRead cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.xoff, sizeof(cmd.xoff));
    io->coreRead(&cmd.yoff, sizeof(cmd.yoff));
    io->coreRead(&cmd.zoff, sizeof(cmd.zoff));
    io->coreRead(&cmd.lod, sizeof(cmd.lod));
    io->coreRead(&cmd.w, sizeof(cmd.w));
    io->coreRead(&cmd.h, sizeof(cmd.h));
    io->coreRead(&cmd.d, sizeof(cmd.d));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.stride, sizeof(cmd.stride));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_Allocation3DRead(con,
           cmd.va,
           cmd.xoff,
           cmd.yoff,
           cmd.zoff,
           cmd.lod,
           cmd.w,
           cmd.h,
           cmd.d,
           cmd.data,
           cmd.data_length,
           cmd.stride);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_AllocationSyncAll(Context *con, ThreadIO *io) {
    RS_CMD_AllocationSyncAll cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.src, sizeof(cmd.src));



    rsi_AllocationSyncAll(con,
           cmd.va,
           cmd.src);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationResize1D(Context *con, ThreadIO *io) {
    RS_CMD_AllocationResize1D cmd;
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.dimX, sizeof(cmd.dimX));



    rsi_AllocationResize1D(con,
           cmd.va,
           cmd.dimX);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationCopy2DRange(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCopy2DRange cmd;
    io->coreRead(&cmd.dest, sizeof(cmd.dest));
    io->coreRead(&cmd.destXoff, sizeof(cmd.destXoff));
    io->coreRead(&cmd.destYoff, sizeof(cmd.destYoff));
    io->coreRead(&cmd.destMip, sizeof(cmd.destMip));
    io->coreRead(&cmd.destFace, sizeof(cmd.destFace));
    io->coreRead(&cmd.width, sizeof(cmd.width));
    io->coreRead(&cmd.height, sizeof(cmd.height));
    io->coreRead(&cmd.src, sizeof(cmd.src));
    io->coreRead(&cmd.srcXoff, sizeof(cmd.srcXoff));
    io->coreRead(&cmd.srcYoff, sizeof(cmd.srcYoff));
    io->coreRead(&cmd.srcMip, sizeof(cmd.srcMip));
    io->coreRead(&cmd.srcFace, sizeof(cmd.srcFace));



    rsi_AllocationCopy2DRange(con,
           cmd.dest,
           cmd.destXoff,
           cmd.destYoff,
           cmd.destMip,
           cmd.destFace,
           cmd.width,
           cmd.height,
           cmd.src,
           cmd.srcXoff,
           cmd.srcYoff,
           cmd.srcMip,
           cmd.srcFace);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationCopy3DRange(Context *con, ThreadIO *io) {
    RS_CMD_AllocationCopy3DRange cmd;
    io->coreRead(&cmd.dest, sizeof(cmd.dest));
    io->coreRead(&cmd.destXoff, sizeof(cmd.destXoff));
    io->coreRead(&cmd.destYoff, sizeof(cmd.destYoff));
    io->coreRead(&cmd.destZoff, sizeof(cmd.destZoff));
    io->coreRead(&cmd.destMip, sizeof(cmd.destMip));
    io->coreRead(&cmd.width, sizeof(cmd.width));
    io->coreRead(&cmd.height, sizeof(cmd.height));
    io->coreRead(&cmd.depth, sizeof(cmd.depth));
    io->coreRead(&cmd.src, sizeof(cmd.src));
    io->coreRead(&cmd.srcXoff, sizeof(cmd.srcXoff));
    io->coreRead(&cmd.srcYoff, sizeof(cmd.srcYoff));
    io->coreRead(&cmd.srcZoff, sizeof(cmd.srcZoff));
    io->coreRead(&cmd.srcMip, sizeof(cmd.srcMip));



    rsi_AllocationCopy3DRange(con,
           cmd.dest,
           cmd.destXoff,
           cmd.destYoff,
           cmd.destZoff,
           cmd.destMip,
           cmd.width,
           cmd.height,
           cmd.depth,
           cmd.src,
           cmd.srcXoff,
           cmd.srcYoff,
           cmd.srcZoff,
           cmd.srcMip);

    io->coreSetReturn(NULL, 0);
};

void rspr_ClosureCreate(Context *con, ThreadIO *io) {
    RS_CMD_ClosureCreate cmd;
    io->coreRead(&cmd.kernelID, sizeof(cmd.kernelID));
    io->coreRead(&cmd.returnValue, sizeof(cmd.returnValue));
    io->coreRead(&cmd.fieldIDs_length, sizeof(cmd.fieldIDs_length));
    io->coreRead(&cmd.values_length, sizeof(cmd.values_length));
    io->coreRead(&cmd.sizes_length, sizeof(cmd.sizes_length));
    io->coreRead(&cmd.depClosures_length, sizeof(cmd.depClosures_length));
    io->coreRead(&cmd.depFieldIDs_length, sizeof(cmd.depFieldIDs_length));

    cmd.fieldIDs = (RsScriptFieldID *)malloc(cmd.fieldIDs_length);
    cmd.values = (uintptr_t *)malloc(cmd.values_length);
    cmd.sizes = (int *)malloc(cmd.sizes_length);
    cmd.depClosures = (RsClosure *)malloc(cmd.depClosures_length);
    cmd.depFieldIDs = (RsScriptFieldID *)malloc(cmd.depFieldIDs_length);


    RsClosure ret =
    rsi_ClosureCreate(con,
           cmd.kernelID,
           cmd.returnValue,
           cmd.fieldIDs,
           cmd.fieldIDs_length,
           cmd.values,
           cmd.values_length,
           cmd.sizes,
           cmd.sizes_length,
           cmd.depClosures,
           cmd.depClosures_length,
           cmd.depFieldIDs,
           cmd.depFieldIDs_length);
    io->coreSetReturn((void *)cmd.fieldIDs, cmd.fieldIDs_length);
    io->coreSetReturn((void *)cmd.values, cmd.values_length);
    io->coreSetReturn((void *)cmd.sizes, cmd.sizes_length);
    io->coreSetReturn((void *)cmd.depClosures, cmd.depClosures_length);
    io->coreSetReturn((void *)cmd.depFieldIDs, cmd.depFieldIDs_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.fieldIDs);
    free((void *)cmd.values);
    free((void *)cmd.sizes);
    free((void *)cmd.depClosures);
    free((void *)cmd.depFieldIDs);
};

void rspr_InvokeClosureCreate(Context *con, ThreadIO *io) {
    RS_CMD_InvokeClosureCreate cmd;
    io->coreRead(&cmd.invokeID, sizeof(cmd.invokeID));
    io->coreRead(&cmd.params_length, sizeof(cmd.params_length));
    io->coreRead(&cmd.fieldIDs_length, sizeof(cmd.fieldIDs_length));
    io->coreRead(&cmd.values_length, sizeof(cmd.values_length));
    io->coreRead(&cmd.sizes_length, sizeof(cmd.sizes_length));

    cmd.params = (const void *)malloc(cmd.params_length);
    if (cmd.params_length) io->coreRead((void *)cmd.params, cmd.params_length);
    cmd.fieldIDs = (const RsScriptFieldID *)malloc(cmd.fieldIDs_length);
    if (cmd.fieldIDs_length) io->coreRead((void *)cmd.fieldIDs, cmd.fieldIDs_length);
    cmd.values = (const uintptr_t *)malloc(cmd.values_length);
    if (cmd.values_length) io->coreRead((void *)cmd.values, cmd.values_length);
    cmd.sizes = (const int *)malloc(cmd.sizes_length);
    if (cmd.sizes_length) io->coreRead((void *)cmd.sizes, cmd.sizes_length);


    RsClosure ret =
    rsi_InvokeClosureCreate(con,
           cmd.invokeID,
           cmd.params,
           cmd.params_length,
           cmd.fieldIDs,
           cmd.fieldIDs_length,
           cmd.values,
           cmd.values_length,
           cmd.sizes,
           cmd.sizes_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.params);
    free((void *)cmd.fieldIDs);
    free((void *)cmd.values);
    free((void *)cmd.sizes);
};

void rspr_ClosureSetArg(Context *con, ThreadIO *io) {
    RS_CMD_ClosureSetArg cmd;
    io->coreRead(&cmd.closureID, sizeof(cmd.closureID));
    io->coreRead(&cmd.index, sizeof(cmd.index));
    io->coreRead(&cmd.value, sizeof(cmd.value));
    io->coreRead(&cmd.valueSize, sizeof(cmd.valueSize));



    rsi_ClosureSetArg(con,
           cmd.closureID,
           cmd.index,
           cmd.value,
           cmd.valueSize);

    io->coreSetReturn(NULL, 0);
};

void rspr_ClosureSetGlobal(Context *con, ThreadIO *io) {
    RS_CMD_ClosureSetGlobal cmd;
    io->coreRead(&cmd.closureID, sizeof(cmd.closureID));
    io->coreRead(&cmd.fieldID, sizeof(cmd.fieldID));
    io->coreRead(&cmd.value, sizeof(cmd.value));
    io->coreRead(&cmd.valueSize, sizeof(cmd.valueSize));



    rsi_ClosureSetGlobal(con,
           cmd.closureID,
           cmd.fieldID,
           cmd.value,
           cmd.valueSize);

    io->coreSetReturn(NULL, 0);
};

void rspr_SamplerCreate(Context *con, ThreadIO *io) {
    RS_CMD_SamplerCreate cmd;
    io->coreRead(&cmd.magFilter, sizeof(cmd.magFilter));
    io->coreRead(&cmd.minFilter, sizeof(cmd.minFilter));
    io->coreRead(&cmd.wrapS, sizeof(cmd.wrapS));
    io->coreRead(&cmd.wrapT, sizeof(cmd.wrapT));
    io->coreRead(&cmd.wrapR, sizeof(cmd.wrapR));
    io->coreRead(&cmd.mAniso, sizeof(cmd.mAniso));



    RsSampler ret =
    rsi_SamplerCreate(con,
           cmd.magFilter,
           cmd.minFilter,
           cmd.wrapS,
           cmd.wrapT,
           cmd.wrapR,
           cmd.mAniso);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ScriptBindAllocation(Context *con, ThreadIO *io) {
    RS_CMD_ScriptBindAllocation cmd;
    io->coreRead(&cmd.vtm, sizeof(cmd.vtm));
    io->coreRead(&cmd.va, sizeof(cmd.va));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));



    rsi_ScriptBindAllocation(con,
           cmd.vtm,
           cmd.va,
           cmd.slot);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetTimeZone(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetTimeZone cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.timeZone_length, sizeof(cmd.timeZone_length));

    cmd.timeZone = (const char *)malloc(cmd.timeZone_length);
    if (cmd.timeZone_length) io->coreRead((void *)cmd.timeZone, cmd.timeZone_length);


    rsi_ScriptSetTimeZone(con,
           cmd.s,
           cmd.timeZone,
           cmd.timeZone_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.timeZone);
};

void rspr_ScriptInvokeIDCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptInvokeIDCreate cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));



    RsScriptInvokeID ret =
    rsi_ScriptInvokeIDCreate(con,
           cmd.s,
           cmd.slot);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ScriptInvoke(Context *con, ThreadIO *io) {
    RS_CMD_ScriptInvoke cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));



    rsi_ScriptInvoke(con,
           cmd.s,
           cmd.slot);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptInvokeV(Context *con, ThreadIO *io) {
    RS_CMD_ScriptInvokeV cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_ScriptInvokeV(con,
           cmd.s,
           cmd.slot,
           cmd.data,
           cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_ScriptForEach(Context *con, ThreadIO *io) {
    RS_CMD_ScriptForEach cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.ain, sizeof(cmd.ain));
    io->coreRead(&cmd.aout, sizeof(cmd.aout));
    io->coreRead(&cmd.usr_length, sizeof(cmd.usr_length));
    io->coreRead(&cmd.sc_length, sizeof(cmd.sc_length));

    cmd.usr = (const void *)malloc(cmd.usr_length);
    if (cmd.usr_length) io->coreRead((void *)cmd.usr, cmd.usr_length);
    cmd.sc = (const RsScriptCall *)malloc(cmd.sc_length);
    if (cmd.sc_length) io->coreRead((void *)cmd.sc, cmd.sc_length);


    rsi_ScriptForEach(con,
           cmd.s,
           cmd.slot,
           cmd.ain,
           cmd.aout,
           cmd.usr,
           cmd.usr_length,
           cmd.sc,
           cmd.sc_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.usr);
    free((void *)cmd.sc);
};

void rspr_ScriptForEachMulti(Context *con, ThreadIO *io) {
    RS_CMD_ScriptForEachMulti cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.ains_length, sizeof(cmd.ains_length));
    io->coreRead(&cmd.aout, sizeof(cmd.aout));
    io->coreRead(&cmd.usr_length, sizeof(cmd.usr_length));
    io->coreRead(&cmd.sc_length, sizeof(cmd.sc_length));

    cmd.ains = (RsAllocation *)malloc(cmd.ains_length);
    cmd.usr = (const void *)malloc(cmd.usr_length);
    if (cmd.usr_length) io->coreRead((void *)cmd.usr, cmd.usr_length);
    cmd.sc = (const RsScriptCall *)malloc(cmd.sc_length);
    if (cmd.sc_length) io->coreRead((void *)cmd.sc, cmd.sc_length);


    rsi_ScriptForEachMulti(con,
           cmd.s,
           cmd.slot,
           cmd.ains,
           cmd.ains_length,
           cmd.aout,
           cmd.usr,
           cmd.usr_length,
           cmd.sc,
           cmd.sc_length);
    io->coreSetReturn((void *)cmd.ains, cmd.ains_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.ains);
    free((void *)cmd.usr);
    free((void *)cmd.sc);
};

void rspr_ScriptSetVarI(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarI cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.value, sizeof(cmd.value));



    rsi_ScriptSetVarI(con,
           cmd.s,
           cmd.slot,
           cmd.value);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetVarObj(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarObj cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.value, sizeof(cmd.value));



    rsi_ScriptSetVarObj(con,
           cmd.s,
           cmd.slot,
           cmd.value);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetVarJ(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarJ cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.value, sizeof(cmd.value));



    rsi_ScriptSetVarJ(con,
           cmd.s,
           cmd.slot,
           cmd.value);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetVarF(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarF cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.value, sizeof(cmd.value));



    rsi_ScriptSetVarF(con,
           cmd.s,
           cmd.slot,
           cmd.value);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetVarD(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarD cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.value, sizeof(cmd.value));



    rsi_ScriptSetVarD(con,
           cmd.s,
           cmd.slot,
           cmd.value);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptSetVarV(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarV cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);


    rsi_ScriptSetVarV(con,
           cmd.s,
           cmd.slot,
           cmd.data,
           cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_ScriptGetVarV(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGetVarV cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));

    cmd.data = (void *)malloc(cmd.data_length);


    rsi_ScriptGetVarV(con,
           cmd.s,
           cmd.slot,
           cmd.data,
           cmd.data_length);
    io->coreSetReturn((void *)cmd.data, cmd.data_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
};

void rspr_ScriptSetVarVE(Context *con, ThreadIO *io) {
    RS_CMD_ScriptSetVarVE cmd;
    io->coreRead(&cmd.s, sizeof(cmd.s));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.data_length, sizeof(cmd.data_length));
    io->coreRead(&cmd.e, sizeof(cmd.e));
    io->coreRead(&cmd.dims_length, sizeof(cmd.dims_length));

    cmd.data = (const void *)malloc(cmd.data_length);
    if (cmd.data_length) io->coreRead((void *)cmd.data, cmd.data_length);
    cmd.dims = (const uint32_t *)malloc(cmd.dims_length);
    if (cmd.dims_length) io->coreRead((void *)cmd.dims, cmd.dims_length);


    rsi_ScriptSetVarVE(con,
           cmd.s,
           cmd.slot,
           cmd.data,
           cmd.data_length,
           cmd.e,
           cmd.dims,
           cmd.dims_length);

    io->coreSetReturn(NULL, 0);
    free((void *)cmd.data);
    free((void *)cmd.dims);
};

void rspr_ScriptCCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptCCreate cmd;
    io->coreRead(&cmd.resName_length, sizeof(cmd.resName_length));
    io->coreRead(&cmd.cacheDir_length, sizeof(cmd.cacheDir_length));
    io->coreRead(&cmd.text_length, sizeof(cmd.text_length));

    cmd.resName = (const char *)malloc(cmd.resName_length);
    if (cmd.resName_length) io->coreRead((void *)cmd.resName, cmd.resName_length);
    cmd.cacheDir = (const char *)malloc(cmd.cacheDir_length);
    if (cmd.cacheDir_length) io->coreRead((void *)cmd.cacheDir, cmd.cacheDir_length);
    cmd.text = (const char *)malloc(cmd.text_length);
    if (cmd.text_length) io->coreRead((void *)cmd.text, cmd.text_length);


    RsScript ret =
    rsi_ScriptCCreate(con,
           cmd.resName,
           cmd.resName_length,
           cmd.cacheDir,
           cmd.cacheDir_length,
           cmd.text,
           cmd.text_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.resName);
    free((void *)cmd.cacheDir);
    free((void *)cmd.text);
};

void rspr_ScriptIntrinsicCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptIntrinsicCreate cmd;
    io->coreRead(&cmd.id, sizeof(cmd.id));
    io->coreRead(&cmd.eid, sizeof(cmd.eid));



    RsScript ret =
    rsi_ScriptIntrinsicCreate(con,
           cmd.id,
           cmd.eid);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ScriptKernelIDCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptKernelIDCreate cmd;
    io->coreRead(&cmd.sid, sizeof(cmd.sid));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));
    io->coreRead(&cmd.sig, sizeof(cmd.sig));



    RsScriptKernelID ret =
    rsi_ScriptKernelIDCreate(con,
           cmd.sid,
           cmd.slot,
           cmd.sig);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ScriptFieldIDCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptFieldIDCreate cmd;
    io->coreRead(&cmd.sid, sizeof(cmd.sid));
    io->coreRead(&cmd.slot, sizeof(cmd.slot));



    RsScriptFieldID ret =
    rsi_ScriptFieldIDCreate(con,
           cmd.sid,
           cmd.slot);

    io->coreSetReturn(&ret, sizeof(ret));
};

void rspr_ScriptGroupCreate(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGroupCreate cmd;
    io->coreRead(&cmd.kernels_length, sizeof(cmd.kernels_length));
    io->coreRead(&cmd.src_length, sizeof(cmd.src_length));
    io->coreRead(&cmd.dstK_length, sizeof(cmd.dstK_length));
    io->coreRead(&cmd.dstF_length, sizeof(cmd.dstF_length));
    io->coreRead(&cmd.type_length, sizeof(cmd.type_length));

    cmd.kernels = (RsScriptKernelID *)malloc(cmd.kernels_length);
    cmd.src = (RsScriptKernelID *)malloc(cmd.src_length);
    cmd.dstK = (RsScriptKernelID *)malloc(cmd.dstK_length);
    cmd.dstF = (RsScriptFieldID *)malloc(cmd.dstF_length);
    cmd.type = (const RsType *)malloc(cmd.type_length);
    if (cmd.type_length) io->coreRead((void *)cmd.type, cmd.type_length);


    RsScriptGroup ret =
    rsi_ScriptGroupCreate(con,
           cmd.kernels,
           cmd.kernels_length,
           cmd.src,
           cmd.src_length,
           cmd.dstK,
           cmd.dstK_length,
           cmd.dstF,
           cmd.dstF_length,
           cmd.type,
           cmd.type_length);
    io->coreSetReturn((void *)cmd.kernels, cmd.kernels_length);
    io->coreSetReturn((void *)cmd.src, cmd.src_length);
    io->coreSetReturn((void *)cmd.dstK, cmd.dstK_length);
    io->coreSetReturn((void *)cmd.dstF, cmd.dstF_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.kernels);
    free((void *)cmd.src);
    free((void *)cmd.dstK);
    free((void *)cmd.dstF);
    free((void *)cmd.type);
};

void rspr_ScriptGroupSetOutput(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGroupSetOutput cmd;
    io->coreRead(&cmd.group, sizeof(cmd.group));
    io->coreRead(&cmd.kernel, sizeof(cmd.kernel));
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));



    rsi_ScriptGroupSetOutput(con,
           cmd.group,
           cmd.kernel,
           cmd.alloc);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptGroupSetInput(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGroupSetInput cmd;
    io->coreRead(&cmd.group, sizeof(cmd.group));
    io->coreRead(&cmd.kernel, sizeof(cmd.kernel));
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));



    rsi_ScriptGroupSetInput(con,
           cmd.group,
           cmd.kernel,
           cmd.alloc);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptGroupExecute(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGroupExecute cmd;
    io->coreRead(&cmd.group, sizeof(cmd.group));



    rsi_ScriptGroupExecute(con,
           cmd.group);

    io->coreSetReturn(NULL, 0);
};

void rspr_ScriptGroup2Create(Context *con, ThreadIO *io) {
    RS_CMD_ScriptGroup2Create cmd;
    io->coreRead(&cmd.name_length, sizeof(cmd.name_length));
    io->coreRead(&cmd.cacheDir_length, sizeof(cmd.cacheDir_length));
    io->coreRead(&cmd.closures_length, sizeof(cmd.closures_length));

    cmd.name = (const char *)malloc(cmd.name_length);
    if (cmd.name_length) io->coreRead((void *)cmd.name, cmd.name_length);
    cmd.cacheDir = (const char *)malloc(cmd.cacheDir_length);
    if (cmd.cacheDir_length) io->coreRead((void *)cmd.cacheDir, cmd.cacheDir_length);
    cmd.closures = (RsClosure *)malloc(cmd.closures_length);


    RsScriptGroup2 ret =
    rsi_ScriptGroup2Create(con,
           cmd.name,
           cmd.name_length,
           cmd.cacheDir,
           cmd.cacheDir_length,
           cmd.closures,
           cmd.closures_length);
    io->coreSetReturn((void *)cmd.closures, cmd.closures_length);

    io->coreSetReturn(&ret, sizeof(ret));
    free((void *)cmd.name);
    free((void *)cmd.cacheDir);
    free((void *)cmd.closures);
};

void rspr_AllocationIoSend(Context *con, ThreadIO *io) {
    RS_CMD_AllocationIoSend cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));



    rsi_AllocationIoSend(con,
           cmd.alloc);

    io->coreSetReturn(NULL, 0);
};

void rspr_AllocationIoReceive(Context *con, ThreadIO *io) {
    RS_CMD_AllocationIoReceive cmd;
    io->coreRead(&cmd.alloc, sizeof(cmd.alloc));



    rsi_AllocationIoReceive(con,
           cmd.alloc);

    io->coreSetReturn(NULL, 0);
};

RsPlaybackLocalFunc gPlaybackFuncs[73] = {
    NULL,
    NULL,
    NULL,
    NULL,
    rsp_ContextSendMessage,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    rsp_AllocationGetSurface,
    rsp_AllocationSetSurface,
    NULL,
    rsp_AllocationAdapterOffset,
    rsp_ContextFinish,
    rsp_ContextDump,
    rsp_ContextSetPriority,
    rsp_ContextDestroyWorker,
    rsp_AssignName,
    rsp_ObjDestroy,
    NULL,
    NULL,
    rsp_AllocationCopyToBitmap,
    rsp_AllocationGetPointer,
    rsp_Allocation1DData,
    rsp_Allocation1DElementData,
    rsp_AllocationElementData,
    rsp_Allocation2DData,
    rsp_Allocation3DData,
    rsp_AllocationGenerateMipmaps,
    rsp_AllocationRead,
    rsp_Allocation1DRead,
    rsp_AllocationElementRead,
    rsp_Allocation2DRead,
    rsp_Allocation3DRead,
    rsp_AllocationSyncAll,
    rsp_AllocationResize1D,
    rsp_AllocationCopy2DRange,
    rsp_AllocationCopy3DRange,
    NULL,
    NULL,
    rsp_ClosureSetArg,
    rsp_ClosureSetGlobal,
    NULL,
    rsp_ScriptBindAllocation,
    rsp_ScriptSetTimeZone,
    rsp_ScriptInvokeIDCreate,
    rsp_ScriptInvoke,
    rsp_ScriptInvokeV,
    rsp_ScriptForEach,
    rsp_ScriptForEachMulti,
    rsp_ScriptSetVarI,
    rsp_ScriptSetVarObj,
    rsp_ScriptSetVarJ,
    rsp_ScriptSetVarF,
    rsp_ScriptSetVarD,
    rsp_ScriptSetVarV,
    rsp_ScriptGetVarV,
    rsp_ScriptSetVarVE,
    rsp_ScriptCCreate,
    rsp_ScriptIntrinsicCreate,
    NULL,
    NULL,
    NULL,
    rsp_ScriptGroupSetOutput,
    rsp_ScriptGroupSetInput,
    rsp_ScriptGroupExecute,
    NULL,
    rsp_AllocationIoSend,
    rsp_AllocationIoReceive,
};
RsPlaybackRemoteFunc gPlaybackRemoteFuncs[73] = {
    NULL,
    rspr_ContextDestroy,
    rspr_ContextGetMessage,
    rspr_ContextPeekMessage,
    rspr_ContextSendMessage,
    rspr_ContextInitToClient,
    rspr_ContextDeinitToClient,
    rspr_ContextSetCacheDir,
    rspr_TypeCreate,
    rspr_TypeCreate2,
    rspr_AllocationCreateTyped,
    rspr_AllocationCreateFromBitmap,
    rspr_AllocationCubeCreateFromBitmap,
    rspr_AllocationGetSurface,
    rspr_AllocationSetSurface,
    rspr_AllocationAdapterCreate,
    rspr_AllocationAdapterOffset,
    rspr_ContextFinish,
    rspr_ContextDump,
    rspr_ContextSetPriority,
    rspr_ContextDestroyWorker,
    rspr_AssignName,
    rspr_ObjDestroy,
    rspr_ElementCreate,
    rspr_ElementCreate2,
    rspr_AllocationCopyToBitmap,
    rspr_AllocationGetPointer,
    rspr_Allocation1DData,
    rspr_Allocation1DElementData,
    rspr_AllocationElementData,
    rspr_Allocation2DData,
    rspr_Allocation3DData,
    rspr_AllocationGenerateMipmaps,
    rspr_AllocationRead,
    rspr_Allocation1DRead,
    rspr_AllocationElementRead,
    rspr_Allocation2DRead,
    rspr_Allocation3DRead,
    rspr_AllocationSyncAll,
    rspr_AllocationResize1D,
    rspr_AllocationCopy2DRange,
    rspr_AllocationCopy3DRange,
    rspr_ClosureCreate,
    rspr_InvokeClosureCreate,
    rspr_ClosureSetArg,
    rspr_ClosureSetGlobal,
    rspr_SamplerCreate,
    rspr_ScriptBindAllocation,
    rspr_ScriptSetTimeZone,
    rspr_ScriptInvokeIDCreate,
    rspr_ScriptInvoke,
    rspr_ScriptInvokeV,
    rspr_ScriptForEach,
    rspr_ScriptForEachMulti,
    rspr_ScriptSetVarI,
    rspr_ScriptSetVarObj,
    rspr_ScriptSetVarJ,
    rspr_ScriptSetVarF,
    rspr_ScriptSetVarD,
    rspr_ScriptSetVarV,
    rspr_ScriptGetVarV,
    rspr_ScriptSetVarVE,
    rspr_ScriptCCreate,
    rspr_ScriptIntrinsicCreate,
    rspr_ScriptKernelIDCreate,
    rspr_ScriptFieldIDCreate,
    rspr_ScriptGroupCreate,
    rspr_ScriptGroupSetOutput,
    rspr_ScriptGroupSetInput,
    rspr_ScriptGroupExecute,
    rspr_ScriptGroup2Create,
    rspr_AllocationIoSend,
    rspr_AllocationIoReceive,
};
};
};
