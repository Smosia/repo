#include <iostream>

#include "verifier/method_verifier.h"

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace verifier {
std::ostream& operator<<(std::ostream& os, const VerifyError& rhs) {
  switch (rhs) {
    case VERIFY_ERROR_BAD_CLASS_HARD: os << "VERIFY_ERROR_BAD_CLASS_HARD"; break;
    case VERIFY_ERROR_BAD_CLASS_SOFT: os << "VERIFY_ERROR_BAD_CLASS_SOFT"; break;
    case VERIFY_ERROR_NO_CLASS: os << "VERIFY_ERROR_NO_CLASS"; break;
    case VERIFY_ERROR_NO_FIELD: os << "VERIFY_ERROR_NO_FIELD"; break;
    case VERIFY_ERROR_NO_METHOD: os << "VERIFY_ERROR_NO_METHOD"; break;
    case VERIFY_ERROR_ACCESS_CLASS: os << "VERIFY_ERROR_ACCESS_CLASS"; break;
    case VERIFY_ERROR_ACCESS_FIELD: os << "VERIFY_ERROR_ACCESS_FIELD"; break;
    case VERIFY_ERROR_ACCESS_METHOD: os << "VERIFY_ERROR_ACCESS_METHOD"; break;
    case VERIFY_ERROR_CLASS_CHANGE: os << "VERIFY_ERROR_CLASS_CHANGE"; break;
    case VERIFY_ERROR_INSTANTIATION: os << "VERIFY_ERROR_INSTANTIATION"; break;
    default: os << "VerifyError[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace verifier
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace verifier {
std::ostream& operator<<(std::ostream& os, const MethodType& rhs) {
  switch (rhs) {
    case METHOD_UNKNOWN: os << "METHOD_UNKNOWN"; break;
    case METHOD_DIRECT: os << "METHOD_DIRECT"; break;
    case METHOD_STATIC: os << "METHOD_STATIC"; break;
    case METHOD_VIRTUAL: os << "METHOD_VIRTUAL"; break;
    case METHOD_INTERFACE: os << "METHOD_INTERFACE"; break;
    default: os << "MethodType[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace verifier
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace verifier {
std::ostream& operator<<(std::ostream& os, const MethodVerifier::FailureKind& rhs) {
  switch (rhs) {
    case MethodVerifier::kNoFailure: os << "NoFailure"; break;
    case MethodVerifier::kSoftFailure: os << "SoftFailure"; break;
    case MethodVerifier::kHardFailure: os << "HardFailure"; break;
    default: os << "MethodVerifier::FailureKind[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace verifier
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace verifier {
std::ostream& operator<<(std::ostream& os, const RegisterTrackingMode& rhs) {
  switch (rhs) {
    case kTrackRegsBranches: os << "TrackRegsBranches"; break;
    case kTrackCompilerInterestPoints: os << "TrackCompilerInterestPoints"; break;
    case kTrackRegsAll: os << "TrackRegsAll"; break;
    default: os << "RegisterTrackingMode[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace verifier
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace verifier {
std::ostream& operator<<(std::ostream& os, const VerifyErrorRefType& rhs) {
  switch (rhs) {
    case VERIFY_ERROR_REF_CLASS: os << "VERIFY_ERROR_REF_CLASS"; break;
    case VERIFY_ERROR_REF_FIELD: os << "VERIFY_ERROR_REF_FIELD"; break;
    case VERIFY_ERROR_REF_METHOD: os << "VERIFY_ERROR_REF_METHOD"; break;
    default: os << "VerifyErrorRefType[" << static_cast<int>(rhs) << "]"; break;
  }
  return os;
}
}  // namespace verifier
}  // namespace art

