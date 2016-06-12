package com.mediatek.sensorhub;
public class SensorHubManager
{
SensorHubManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.Integer> getContextList() { throw new RuntimeException("Stub!"); }
public  boolean isContextSupported(int type) { throw new RuntimeException("Stub!"); }
public  int requestAction(com.mediatek.sensorhub.Condition condition, com.mediatek.sensorhub.Action action) { throw new RuntimeException("Stub!"); }
public  boolean updateCondition(int requestId, com.mediatek.sensorhub.Condition condition) { throw new RuntimeException("Stub!"); }
public  boolean cancelAction(int requestId) { throw new RuntimeException("Stub!"); }
public static java.lang.String EXTRA_REQUEST_ID;
public static final int REQUEST_ERROR_CONTEXT_INVALID = -3;
public static final int REQUEST_ERROR_NO_RESOURCE = -2;
public static final int REQUEST_ERROR_UNKNOWN = -1;
public static final java.lang.String SENSORHUB_SERVICE = "sensorhubservice";
}
