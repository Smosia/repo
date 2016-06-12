package com.mediatek.sensorhub;
public class DataCell
  implements android.os.Parcelable
{
DataCell() { throw new RuntimeException("Stub!"); }
public  boolean isPrevious() { throw new RuntimeException("Stub!"); }
public  int getIndex() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getIntValue() { throw new RuntimeException("Stub!"); }
public  float getFloatValue() { throw new RuntimeException("Stub!"); }
public  long getLongValue() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final int DATA_TYPE_FLOAT = 3;
public static final int DATA_TYPE_INT = 1;
public static final int DATA_TYPE_INVALID = 0;
public static final int DATA_TYPE_LONG = 2;
}
