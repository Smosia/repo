package android.service.voice;
public class VoiceInteractionService
  extends android.app.Service
{
public  VoiceInteractionService() { throw new RuntimeException("Stub!"); }
public  void onLaunchVoiceAssistFromKeyguard() { throw new RuntimeException("Stub!"); }
public static  boolean isActiveService(android.content.Context context, android.content.ComponentName service) { throw new RuntimeException("Stub!"); }
public  void setDisabledShowContext(int flags) { throw new RuntimeException("Stub!"); }
public  int getDisabledShowContext() { throw new RuntimeException("Stub!"); }
public  void showSession(android.os.Bundle args, int flags) { throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onReady() { throw new RuntimeException("Stub!"); }
public  void onShutdown() { throw new RuntimeException("Stub!"); }
public final  android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(java.lang.String keyphrase, java.util.Locale locale, android.service.voice.AlwaysOnHotwordDetector.Callback callback) { throw new RuntimeException("Stub!"); }
protected  void dump(java.io.FileDescriptor fd, java.io.PrintWriter pw, java.lang.String[] args) { throw new RuntimeException("Stub!"); }
public static final java.lang.String SERVICE_INTERFACE = "android.service.voice.VoiceInteractionService";
public static final java.lang.String SERVICE_META_DATA = "android.voice_interaction";
}
