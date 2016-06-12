package com.mediatek.sensorhub;
public class Condition
  implements android.os.Parcelable
{
public static class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, int operation, int value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, int operation, long value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, int operation, float value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, boolean isPrevious, int operation, int value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, boolean isPrevious, int operation, long value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition createCondition(int index, boolean isPrevious, int operation, float value) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition combineWithAnd(com.mediatek.sensorhub.Condition c1, com.mediatek.sensorhub.Condition c2) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition combineWithOr(com.mediatek.sensorhub.Condition c1, com.mediatek.sensorhub.Condition c2) { throw new RuntimeException("Stub!"); }
public  com.mediatek.sensorhub.Condition combineWithBracket(com.mediatek.sensorhub.Condition c) { throw new RuntimeException("Stub!"); }
}
Condition() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final int OP_ANY = 255;
public static final int OP_EQUALS = 5;
public static final int OP_GREATER_THAN = 1;
public static final int OP_GREATER_THAN_OR_EQUALS = 2;
public static final int OP_LESS_THAN = 3;
public static final int OP_LESS_THAN_OR_EQUALS = 4;
public static final int OP_MOD = 7;
public static final int OP_NOT_EQUALS = 6;
}
