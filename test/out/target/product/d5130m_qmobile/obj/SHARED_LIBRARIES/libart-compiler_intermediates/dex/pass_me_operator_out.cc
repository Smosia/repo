#include <iostream>

#include "dex/pass_me.h"

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
std::ostream& operator<<(std::ostream& os, const OptimizationFlag& rhs) {
  switch (rhs) {
    case kOptimizationBasicBlockChange: os << "OptimizationBasicBlockChange"; break;
    case kOptimizationDefUsesChange: os << "OptimizationDefUsesChange"; break;
    case kLoopStructureChange: os << "LoopStructureChange"; break;
    default: os << "OptimizationFlag[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
std::ostream& operator<<(std::ostream& os, const DataFlowAnalysisMode& rhs) {
  switch (rhs) {
    case kAllNodes: os << "AllNodes"; break;
    case kPreOrderDFSTraversal: os << "PreOrderDFSTraversal"; break;
    case kRepeatingPreOrderDFSTraversal: os << "RepeatingPreOrderDFSTraversal"; break;
    case kReversePostOrderDFSTraversal: os << "ReversePostOrderDFSTraversal"; break;
    case kRepeatingPostOrderDFSTraversal: os << "RepeatingPostOrderDFSTraversal"; break;
    case kRepeatingReversePostOrderDFSTraversal: os << "RepeatingReversePostOrderDFSTraversal"; break;
    case kPostOrderDOMTraversal: os << "PostOrderDOMTraversal"; break;
    case kTopologicalSortTraversal: os << "TopologicalSortTraversal"; break;
    case kLoopRepeatingTopologicalSortTraversal: os << "LoopRepeatingTopologicalSortTraversal"; break;
    case kNoNodes: os << "NoNodes"; break;
    default: os << "DataFlowAnalysisMode[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace art
