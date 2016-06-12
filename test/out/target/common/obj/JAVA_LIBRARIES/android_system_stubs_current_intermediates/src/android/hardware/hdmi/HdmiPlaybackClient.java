package android.hardware.hdmi;
public final class HdmiPlaybackClient
  extends android.hardware.hdmi.HdmiClient
{
public static interface OneTouchPlayCallback
{
public abstract  void onComplete(int result);
}
public static interface DisplayStatusCallback
{
public abstract  void onComplete(int status);
}
HdmiPlaybackClient() { throw new RuntimeException("Stub!"); }
public  void oneTouchPlay(android.hardware.hdmi.HdmiPlaybackClient.OneTouchPlayCallback callback) { throw new RuntimeException("Stub!"); }
public  int getDeviceType() { throw new RuntimeException("Stub!"); }
public  void queryDisplayStatus(android.hardware.hdmi.HdmiPlaybackClient.DisplayStatusCallback callback) { throw new RuntimeException("Stub!"); }
public  void sendStandby() { throw new RuntimeException("Stub!"); }
}
