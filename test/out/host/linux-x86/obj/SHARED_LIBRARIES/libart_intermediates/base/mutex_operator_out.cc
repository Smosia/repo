#include <iostream>

#include "base/mutex.h"

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
std::ostream& operator<<(std::ostream& os, const LockLevel& rhs) {
  switch (rhs) {
    case kLoggingLock: os << "LoggingLock"; break;
    case kMemMapsLock: os << "MemMapsLock"; break;
    case kSwapMutexesLock: os << "SwapMutexesLock"; break;
    case kUnexpectedSignalLock: os << "UnexpectedSignalLock"; break;
    case kThreadSuspendCountLock: os << "ThreadSuspendCountLock"; break;
    case kAbortLock: os << "AbortLock"; break;
    case kJdwpSocketLock: os << "JdwpSocketLock"; break;
    case kRegionSpaceRegionLock: os << "RegionSpaceRegionLock"; break;
    case kTransactionLogLock: os << "TransactionLogLock"; break;
    case kReferenceQueueSoftReferencesLock: os << "ReferenceQueueSoftReferencesLock"; break;
    case kReferenceQueuePhantomReferencesLock: os << "ReferenceQueuePhantomReferencesLock"; break;
    case kReferenceQueueFinalizerReferencesLock: os << "ReferenceQueueFinalizerReferencesLock"; break;
    case kReferenceQueueWeakReferencesLock: os << "ReferenceQueueWeakReferencesLock"; break;
    case kReferenceQueueClearedReferencesLock: os << "ReferenceQueueClearedReferencesLock"; break;
    case kReferenceProcessorLock: os << "ReferenceProcessorLock"; break;
    case kJitCodeCacheLock: os << "JitCodeCacheLock"; break;
    case kRosAllocGlobalLock: os << "RosAllocGlobalLock"; break;
    case kRosAllocBracketLock: os << "RosAllocBracketLock"; break;
    case kRosAllocBulkFreeLock: os << "RosAllocBulkFreeLock"; break;
    case kAllocSpaceLock: os << "AllocSpaceLock"; break;
    case kBumpPointerSpaceBlockLock: os << "BumpPointerSpaceBlockLock"; break;
    case kArenaPoolLock: os << "ArenaPoolLock"; break;
    case kDexFileMethodInlinerLock: os << "DexFileMethodInlinerLock"; break;
    case kDexFileToMethodInlinerMapLock: os << "DexFileToMethodInlinerMapLock"; break;
    case kMarkSweepMarkStackLock: os << "MarkSweepMarkStackLock"; break;
    case kInternTableLock: os << "InternTableLock"; break;
    case kOatFileSecondaryLookupLock: os << "OatFileSecondaryLookupLock"; break;
    case kTracingUniqueMethodsLock: os << "TracingUniqueMethodsLock"; break;
    case kTracingStreamingLock: os << "TracingStreamingLock"; break;
    case kDefaultMutexLevel: os << "DefaultMutexLevel"; break;
    case kMarkSweepLargeObjectLock: os << "MarkSweepLargeObjectLock"; break;
    case kPinTableLock: os << "PinTableLock"; break;
    case kJdwpObjectRegistryLock: os << "JdwpObjectRegistryLock"; break;
    case kModifyLdtLock: os << "ModifyLdtLock"; break;
    case kAllocatedThreadIdsLock: os << "AllocatedThreadIdsLock"; break;
    case kMonitorPoolLock: os << "MonitorPoolLock"; break;
    case kMethodVerifiersLock: os << "MethodVerifiersLock"; break;
    case kClassLinkerClassesLock: os << "ClassLinkerClassesLock"; break;
    case kBreakpointLock: os << "BreakpointLock"; break;
    case kMonitorLock: os << "MonitorLock"; break;
    case kMonitorListLock: os << "MonitorListLock"; break;
    case kJniLoadLibraryLock: os << "JniLoadLibraryLock"; break;
    case kThreadListLock: os << "ThreadListLock"; break;
    case kInterpreterStringInitMapLock: os << "InterpreterStringInitMapLock"; break;
    case kAllocTrackerLock: os << "AllocTrackerLock"; break;
    case kDeoptimizationLock: os << "DeoptimizationLock"; break;
    case kProfilerLock: os << "ProfilerLock"; break;
    case kJdwpShutdownLock: os << "JdwpShutdownLock"; break;
    case kJdwpEventListLock: os << "JdwpEventListLock"; break;
    case kJdwpAttachLock: os << "JdwpAttachLock"; break;
    case kJdwpStartLock: os << "JdwpStartLock"; break;
    case kRuntimeShutdownLock: os << "RuntimeShutdownLock"; break;
    case kTraceLock: os << "TraceLock"; break;
    case kHeapBitmapLock: os << "HeapBitmapLock"; break;
    case kMutatorLock: os << "MutatorLock"; break;
    case kInstrumentEntrypointsLock: os << "InstrumentEntrypointsLock"; break;
    case kZygoteCreationLock: os << "ZygoteCreationLock"; break;
    case kLockLevelCount: os << "LockLevelCount"; break;
    default: os << "LockLevel[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace art
