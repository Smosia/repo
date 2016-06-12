package android.media.midi;
public abstract class MidiDeviceService
  extends android.app.Service
{
public  MidiDeviceService() { throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public abstract  android.media.midi.MidiReceiver[] onGetInputPortReceivers();
public final  android.media.midi.MidiReceiver[] getOutputPortReceivers() { throw new RuntimeException("Stub!"); }
public final  android.media.midi.MidiDeviceInfo getDeviceInfo() { throw new RuntimeException("Stub!"); }
public  void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus status) { throw new RuntimeException("Stub!"); }
public  void onClose() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String SERVICE_INTERFACE = "android.media.midi.MidiDeviceService";
}
