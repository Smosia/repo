package android.hardware.hdmi;
public class HdmiTimerRecordSources
{
 static class TimeUnit
{
TimeUnit() { throw new RuntimeException("Stub!"); }
}
public static final class Time
  extends android.hardware.hdmi.HdmiTimerRecordSources.TimeUnit
{
Time() { throw new RuntimeException("Stub!"); }
}
public static final class Duration
  extends android.hardware.hdmi.HdmiTimerRecordSources.TimeUnit
{
Duration() { throw new RuntimeException("Stub!"); }
}
public static final class TimerInfo
{
TimerInfo() { throw new RuntimeException("Stub!"); }
}
public static final class TimerRecordSource
{
TimerRecordSource() { throw new RuntimeException("Stub!"); }
}
HdmiTimerRecordSources() { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofDigitalSource(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.DigitalServiceSource source) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofAnalogueSource(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.AnalogueServiceSource source) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofExternalPlug(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.ExternalPlugData source) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource ofExternalPhysicalAddress(android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfo, android.hardware.hdmi.HdmiRecordSources.ExternalPhysicalAddress source) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.Time timeOf(int hour, int minute) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.Duration durationOf(int hour, int minute) { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiTimerRecordSources.TimerInfo timerInfoOf(int dayOfMonth, int monthOfYear, android.hardware.hdmi.HdmiTimerRecordSources.Time startTime, android.hardware.hdmi.HdmiTimerRecordSources.Duration duration, int recordingSequence) { throw new RuntimeException("Stub!"); }
public static  boolean checkTimerRecordSource(int sourcetype, byte[] recordSource) { throw new RuntimeException("Stub!"); }
public static final int RECORDING_SEQUENCE_REPEAT_FRIDAY = 32;
public static final int RECORDING_SEQUENCE_REPEAT_MONDAY = 2;
public static final int RECORDING_SEQUENCE_REPEAT_ONCE_ONLY = 0;
public static final int RECORDING_SEQUENCE_REPEAT_SATUREDAY = 64;
public static final int RECORDING_SEQUENCE_REPEAT_SUNDAY = 1;
public static final int RECORDING_SEQUENCE_REPEAT_THURSDAY = 16;
public static final int RECORDING_SEQUENCE_REPEAT_TUESDAY = 4;
public static final int RECORDING_SEQUENCE_REPEAT_WEDNESDAY = 8;
}
