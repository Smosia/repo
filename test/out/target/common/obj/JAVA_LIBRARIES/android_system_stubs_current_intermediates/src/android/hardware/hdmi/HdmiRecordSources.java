package android.hardware.hdmi;
public final class HdmiRecordSources
{
public abstract static class RecordSource
{
RecordSource() { throw new RuntimeException("Stub!"); }
}
public static final class OwnSource
  extends android.hardware.hdmi.HdmiRecordSources.RecordSource
{
OwnSource() { throw new RuntimeException("Stub!"); }
}
public static final class DigitalServiceSource
  extends android.hardware.hdmi.HdmiRecordSources.RecordSource
{
DigitalServiceSource() { throw new RuntimeException("Stub!"); }
}
public static final class AnalogueServiceSource
  extends android.hardware.hdmi.HdmiRecordSources.RecordSource
{
AnalogueServiceSource() { throw new RuntimeException("Stub!"); }
}
public static final class ExternalPlugData
  extends android.hardware.hdmi.HdmiRecordSources.RecordSource
{
ExternalPlugData() { throw new RuntimeException("Stub!"); }
}
public static final class ExternalPhysicalAddress
  extends android.hardware.hdmi.HdmiRecordSources.RecordSource
{
ExternalPhysicalAddress() { throw new RuntimeException("Stub!"); }
}
HdmiRecordSources() { throw new RuntimeException("Stub!"); }
public static  android.hardware.hdmi.HdmiRecordSources.OwnSource ofOwnSource() { throw new RuntimeException("Stub!"); }
public static  boolean checkRecordSource(byte[] recordSource) { throw new RuntimeException("Stub!"); }
}
