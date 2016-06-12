#include <iostream>

#include "dex_instruction_utils.h"

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
std::ostream& operator<<(std::ostream& os, const DexMemAccessType& rhs) {
  switch (rhs) {
    case kDexMemAccessWord: os << "DexMemAccessWord"; break;
    case kDexMemAccessWide: os << "DexMemAccessWide"; break;
    case kDexMemAccessObject: os << "DexMemAccessObject"; break;
    case kDexMemAccessBoolean: os << "DexMemAccessBoolean"; break;
    case kDexMemAccessByte: os << "DexMemAccessByte"; break;
    case kDexMemAccessChar: os << "DexMemAccessChar"; break;
    case kDexMemAccessShort: os << "DexMemAccessShort"; break;
    case kDexMemAccessTypeCount: os << "DexMemAccessTypeCount"; break;
    default: os << "DexMemAccessType[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
std::ostream& operator<<(std::ostream& os, const DexInvokeType& rhs) {
  switch (rhs) {
    case kDexInvokeVirtual: os << "DexInvokeVirtual"; break;
    case kDexInvokeSuper: os << "DexInvokeSuper"; break;
    case kDexInvokeDirect: os << "DexInvokeDirect"; break;
    case kDexInvokeStatic: os << "DexInvokeStatic"; break;
    case kDexInvokeInterface: os << "DexInvokeInterface"; break;
    case kDexInvokeTypeCount: os << "DexInvokeTypeCount"; break;
    default: os << "DexInvokeType[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace art
