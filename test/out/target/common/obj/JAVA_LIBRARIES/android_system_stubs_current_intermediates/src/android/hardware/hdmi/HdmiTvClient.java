package android.hardware.hdmi;
public final class HdmiTvClient
  extends android.hardware.hdmi.HdmiClient
{
public static interface SelectCallback
{
public abstract  void onComplete(int result);
}
public static interface InputChangeListener
{
public abstract  void onChanged(android.hardware.hdmi.HdmiDeviceInfo info);
}
public static interface HdmiMhlVendorCommandListener
{
public abstract  void onReceived(int portId, int offset, int length, byte[] data);
}
HdmiTvClient() { throw new RuntimeException("Stub!"); }
public  int getDeviceType() { throw new RuntimeException("Stub!"); }
public  void deviceSelect(int logicalAddress, android.hardware.hdmi.HdmiTvClient.SelectCallback callback) { throw new RuntimeException("Stub!"); }
public  void portSelect(int portId, android.hardware.hdmi.HdmiTvClient.SelectCallback callback) { throw new RuntimeException("Stub!"); }
public  void setInputChangeListener(android.hardware.hdmi.HdmiTvClient.InputChangeListener listener) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() { throw new RuntimeException("Stub!"); }
public  void setSystemAudioMode(boolean enabled, android.hardware.hdmi.HdmiTvClient.SelectCallback callback) { throw new RuntimeException("Stub!"); }
public  void setSystemAudioVolume(int oldIndex, int newIndex, int maxIndex) { throw new RuntimeException("Stub!"); }
public  void setSystemAudioMute(boolean mute) { throw new RuntimeException("Stub!"); }
public  void setRecordListener(android.hardware.hdmi.HdmiRecordListener listener) { throw new RuntimeException("Stub!"); }
public  void sendStandby(int deviceId) { throw new RuntimeException("Stub!"); }
public  void startOneTouchRecord(int recorderAddress, android.hardware.hdmi.HdmiRecordSources.RecordSource source) { throw new RuntimeException("Stub!"); }
public  void stopOneTouchRecord(int recorderAddress) { throw new RuntimeException("Stub!"); }
public  void startTimerRecording(int recorderAddress, int sourceType, android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource source) { throw new RuntimeException("Stub!"); }
public  void clearTimerRecording(int recorderAddress, int sourceType, android.hardware.hdmi.HdmiTimerRecordSources.TimerRecordSource source) { throw new RuntimeException("Stub!"); }
public  void setHdmiMhlVendorCommandListener(android.hardware.hdmi.HdmiTvClient.HdmiMhlVendorCommandListener listener) { throw new RuntimeException("Stub!"); }
public  void sendMhlVendorCommand(int portId, int offset, int length, byte[] data) { throw new RuntimeException("Stub!"); }
public static final int VENDOR_DATA_SIZE = 16;
}
