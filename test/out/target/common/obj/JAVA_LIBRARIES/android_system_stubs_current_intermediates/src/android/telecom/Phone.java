package android.telecom;
@java.lang.Deprecated()
public final class Phone
{
public abstract static class Listener
{
public  Listener() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void onAudioStateChanged(android.telecom.Phone phone, android.telecom.AudioState audioState) { throw new RuntimeException("Stub!"); }
public  void onCallAudioStateChanged(android.telecom.Phone phone, android.telecom.CallAudioState callAudioState) { throw new RuntimeException("Stub!"); }
public  void onBringToForeground(android.telecom.Phone phone, boolean showDialpad) { throw new RuntimeException("Stub!"); }
public  void onCallAdded(android.telecom.Phone phone, android.telecom.Call call) { throw new RuntimeException("Stub!"); }
public  void onCallRemoved(android.telecom.Phone phone, android.telecom.Call call) { throw new RuntimeException("Stub!"); }
public  void onCanAddCallChanged(android.telecom.Phone phone, boolean canAddCall) { throw new RuntimeException("Stub!"); }
}
Phone() { throw new RuntimeException("Stub!"); }
public final  void addListener(android.telecom.Phone.Listener listener) { throw new RuntimeException("Stub!"); }
public final  void removeListener(android.telecom.Phone.Listener listener) { throw new RuntimeException("Stub!"); }
public final  java.util.List<android.telecom.Call> getCalls() { throw new RuntimeException("Stub!"); }
public final  boolean canAddCall() { throw new RuntimeException("Stub!"); }
public final  void setMuted(boolean state) { throw new RuntimeException("Stub!"); }
public final  void setAudioRoute(int route) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public final  android.telecom.AudioState getAudioState() { throw new RuntimeException("Stub!"); }
public final  android.telecom.CallAudioState getCallAudioState() { throw new RuntimeException("Stub!"); }
}
