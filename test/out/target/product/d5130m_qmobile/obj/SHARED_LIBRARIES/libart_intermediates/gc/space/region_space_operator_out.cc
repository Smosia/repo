#include <iostream>

#include "gc/space/region_space.h"

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace gc {
namespace space {
std::ostream& operator<<(std::ostream& os, const RegionSpace::RegionType& rhs) {
  switch (rhs) {
    case RegionSpace::RegionType::kRegionTypeAll: os << "RegionTypeAll"; break;
    case RegionSpace::RegionType::kRegionTypeFromSpace: os << "RegionTypeFromSpace"; break;
    case RegionSpace::RegionType::kRegionTypeUnevacFromSpace: os << "RegionTypeUnevacFromSpace"; break;
    case RegionSpace::RegionType::kRegionTypeToSpace: os << "RegionTypeToSpace"; break;
    case RegionSpace::RegionType::kRegionTypeNone: os << "RegionTypeNone"; break;
  }
  return os;
}
}  // namespace space
}  // namespace gc
}  // namespace art

// This was automatically generated by art/tools/generate-operator-out.py --- do not edit!
namespace art {
namespace gc {
namespace space {
std::ostream& operator<<(std::ostream& os, const RegionSpace::RegionState& rhs) {
  switch (rhs) {
    case RegionSpace::RegionState::kRegionStateFree: os << "RegionStateFree"; break;
    case RegionSpace::RegionState::kRegionStateAllocated: os << "RegionStateAllocated"; break;
    case RegionSpace::RegionState::kRegionStateLarge: os << "RegionStateLarge"; break;
    case RegionSpace::RegionState::kRegionStateLargeTail: os << "RegionStateLargeTail"; break;
  }
  return os;
}
}  // namespace space
}  // namespace gc
}  // namespace art
