package android.hardware;
public final class Sensor
{
Sensor() { throw new RuntimeException("Stub!"); }
public  int getReportingMode() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public  java.lang.String getVendor() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getVersion() { throw new RuntimeException("Stub!"); }
public  float getMaximumRange() { throw new RuntimeException("Stub!"); }
public  float getResolution() { throw new RuntimeException("Stub!"); }
public  float getPower() { throw new RuntimeException("Stub!"); }
public  int getMinDelay() { throw new RuntimeException("Stub!"); }
public  int getFifoReservedEventCount() { throw new RuntimeException("Stub!"); }
public  int getFifoMaxEventCount() { throw new RuntimeException("Stub!"); }
public  java.lang.String getStringType() { throw new RuntimeException("Stub!"); }
public  int getMaxDelay() { throw new RuntimeException("Stub!"); }
public  boolean isWakeUpSensor() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int REPORTING_MODE_CONTINUOUS = 0;
public static final int REPORTING_MODE_ONE_SHOT = 2;
public static final int REPORTING_MODE_ON_CHANGE = 1;
public static final int REPORTING_MODE_SPECIAL_TRIGGER = 3;
public static final java.lang.String STRING_TYPE_ACCELEROMETER = "android.sensor.accelerometer";
public static final java.lang.String STRING_TYPE_AMBIENT_TEMPERATURE = "android.sensor.ambient_temperature";
public static final java.lang.String STRING_TYPE_GAME_ROTATION_VECTOR = "android.sensor.game_rotation_vector";
public static final java.lang.String STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR = "android.sensor.geomagnetic_rotation_vector";
public static final java.lang.String STRING_TYPE_GRAVITY = "android.sensor.gravity";
public static final java.lang.String STRING_TYPE_GYROSCOPE = "android.sensor.gyroscope";
public static final java.lang.String STRING_TYPE_GYROSCOPE_UNCALIBRATED = "android.sensor.gyroscope_uncalibrated";
public static final java.lang.String STRING_TYPE_HEART_RATE = "android.sensor.heart_rate";
public static final java.lang.String STRING_TYPE_LIGHT = "android.sensor.light";
public static final java.lang.String STRING_TYPE_LINEAR_ACCELERATION = "android.sensor.linear_acceleration";
public static final java.lang.String STRING_TYPE_MAGNETIC_FIELD = "android.sensor.magnetic_field";
public static final java.lang.String STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED = "android.sensor.magnetic_field_uncalibrated";
@java.lang.Deprecated()
public static final java.lang.String STRING_TYPE_ORIENTATION = "android.sensor.orientation";
public static final java.lang.String STRING_TYPE_PRESSURE = "android.sensor.pressure";
public static final java.lang.String STRING_TYPE_PROXIMITY = "android.sensor.proximity";
public static final java.lang.String STRING_TYPE_RELATIVE_HUMIDITY = "android.sensor.relative_humidity";
public static final java.lang.String STRING_TYPE_ROTATION_VECTOR = "android.sensor.rotation_vector";
public static final java.lang.String STRING_TYPE_SIGNIFICANT_MOTION = "android.sensor.significant_motion";
public static final java.lang.String STRING_TYPE_STEP_COUNTER = "android.sensor.step_counter";
public static final java.lang.String STRING_TYPE_STEP_DETECTOR = "android.sensor.step_detector";
@java.lang.Deprecated()
public static final java.lang.String STRING_TYPE_TEMPERATURE = "android.sensor.temperature";
public static final int TYPE_ACCELEROMETER = 1;
public static final int TYPE_ALL = -1;
public static final int TYPE_AMBIENT_TEMPERATURE = 13;
public static final int TYPE_GAME_ROTATION_VECTOR = 15;
public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 20;
public static final int TYPE_GRAVITY = 9;
public static final int TYPE_GYROSCOPE = 4;
public static final int TYPE_GYROSCOPE_UNCALIBRATED = 16;
public static final int TYPE_HEART_RATE = 21;
public static final int TYPE_LIGHT = 5;
public static final int TYPE_LINEAR_ACCELERATION = 10;
public static final int TYPE_MAGNETIC_FIELD = 2;
public static final int TYPE_MAGNETIC_FIELD_UNCALIBRATED = 14;
@java.lang.Deprecated()
public static final int TYPE_ORIENTATION = 3;
public static final int TYPE_PRESSURE = 6;
public static final int TYPE_PROXIMITY = 8;
public static final int TYPE_RELATIVE_HUMIDITY = 12;
public static final int TYPE_ROTATION_VECTOR = 11;
public static final int TYPE_SIGNIFICANT_MOTION = 17;
public static final int TYPE_STEP_COUNTER = 19;
public static final int TYPE_STEP_DETECTOR = 18;
@java.lang.Deprecated()
public static final int TYPE_TEMPERATURE = 7;
}
