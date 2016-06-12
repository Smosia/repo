package android.hardware.hdmi;
public abstract class HdmiRecordListener
{
public static class TimerStatusData
{
TimerStatusData() { throw new RuntimeException("Stub!"); }
public  boolean isOverlapped() { throw new RuntimeException("Stub!"); }
public  int getMediaInfo() { throw new RuntimeException("Stub!"); }
public  boolean isProgrammed() { throw new RuntimeException("Stub!"); }
public  int getProgrammedInfo() { throw new RuntimeException("Stub!"); }
public  int getNotProgammedError() { throw new RuntimeException("Stub!"); }
public  int getDurationHour() { throw new RuntimeException("Stub!"); }
public  int getDurationMinute() { throw new RuntimeException("Stub!"); }
public  int getExtraError() { throw new RuntimeException("Stub!"); }
}
public  HdmiRecordListener() { throw new RuntimeException("Stub!"); }
public abstract  android.hardware.hdmi.HdmiRecordSources.RecordSource onOneTouchRecordSourceRequested(int recorderAddress);
public  void onOneTouchRecordResult(int recorderAddress, int result) { throw new RuntimeException("Stub!"); }
public  void onTimerRecordingResult(int recorderAddress, android.hardware.hdmi.HdmiRecordListener.TimerStatusData data) { throw new RuntimeException("Stub!"); }
public  void onClearTimerRecordingResult(int recorderAddress, int result) { throw new RuntimeException("Stub!"); }
}
