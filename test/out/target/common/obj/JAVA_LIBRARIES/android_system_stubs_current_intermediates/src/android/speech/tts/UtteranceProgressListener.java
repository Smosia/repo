package android.speech.tts;
public abstract class UtteranceProgressListener
{
public  UtteranceProgressListener() { throw new RuntimeException("Stub!"); }
public abstract  void onStart(java.lang.String utteranceId);
public abstract  void onDone(java.lang.String utteranceId);
@java.lang.Deprecated()
public abstract  void onError(java.lang.String utteranceId);
public  void onError(java.lang.String utteranceId, int errorCode) { throw new RuntimeException("Stub!"); }
public  void onStop(java.lang.String utteranceId, boolean interrupted) { throw new RuntimeException("Stub!"); }
}
