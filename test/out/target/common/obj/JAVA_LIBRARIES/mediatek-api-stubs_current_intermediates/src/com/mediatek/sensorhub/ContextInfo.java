package com.mediatek.sensorhub;
public class ContextInfo
{
public static class Type
{
Type() { throw new RuntimeException("Stub!"); }
public static final int CARRY = 1001;
public static final int FACING = 1002;
public static final int PEDOMETER = 1003;
public static final int PICK_UP = 1004;
public static final int SHAKE = 1005;
public static final int USER_ACTIVITY = 1006;
}
public static class Pedometer
{
Pedometer() { throw new RuntimeException("Stub!"); }
public static final int STEP_FREQUENCY = 15;
public static final int STEP_LENGTH = 14;
public static final int TOTAL_COUNT = 16;
public static final int TOTAL_DISTANCE = 17;
}
public static class UserActivity
{
public static class State
{
State() { throw new RuntimeException("Stub!"); }
public static final int IN_VEHICLE = 20;
public static final int ON_BICYCLE = 21;
public static final int ON_FOOT = 22;
public static final int STILL = 23;
public static final int TILTING = 25;
public static final int UNKNOWN = 24;
}
UserActivity() { throw new RuntimeException("Stub!"); }
public static final int CONFIDENCE = 32;
public static final int CURRENT_STATE = 31;
public static final int DURATION = 34;
}
public static class Carry
{
Carry() { throw new RuntimeException("Stub!"); }
public static final int IN_POCKET = 28;
}
public static class Pickup
{
Pickup() { throw new RuntimeException("Stub!"); }
public static final int VALUE = 38;
}
public static class Facing
{
Facing() { throw new RuntimeException("Stub!"); }
public static final int FACE_DOWN = 41;
}
public static class Shake
{
Shake() { throw new RuntimeException("Stub!"); }
public static final int VALUE = 44;
}
ContextInfo() { throw new RuntimeException("Stub!"); }
}
