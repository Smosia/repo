package android.media.audiopolicy;
public class AudioPolicy
{
public static class Builder
{
public  Builder(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioPolicy.Builder addMix(android.media.audiopolicy.AudioMix mix) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioPolicy.Builder setLooper(android.os.Looper looper) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  void setAudioPolicyFocusListener(android.media.audiopolicy.AudioPolicy.AudioPolicyFocusListener l) { throw new RuntimeException("Stub!"); }
public  void setAudioPolicyStatusListener(android.media.audiopolicy.AudioPolicy.AudioPolicyStatusListener l) { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioPolicy build() { throw new RuntimeException("Stub!"); }
}
public abstract static class AudioPolicyStatusListener
{
public  AudioPolicyStatusListener() { throw new RuntimeException("Stub!"); }
public  void onStatusChange() { throw new RuntimeException("Stub!"); }
public  void onMixStateUpdate(android.media.audiopolicy.AudioMix mix) { throw new RuntimeException("Stub!"); }
}
public abstract static class AudioPolicyFocusListener
{
public  AudioPolicyFocusListener() { throw new RuntimeException("Stub!"); }
public  void onAudioFocusGrant(android.media.AudioFocusInfo afi, int requestResult) { throw new RuntimeException("Stub!"); }
public  void onAudioFocusLoss(android.media.AudioFocusInfo afi, boolean wasNotified) { throw new RuntimeException("Stub!"); }
}
AudioPolicy() { throw new RuntimeException("Stub!"); }
public  void setRegistration(java.lang.String regId) { throw new RuntimeException("Stub!"); }
public  int getFocusDuckingBehavior() { throw new RuntimeException("Stub!"); }
public  int setFocusDuckingBehavior(int behavior) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException { throw new RuntimeException("Stub!"); }
public  android.media.AudioRecord createAudioRecordSink(android.media.audiopolicy.AudioMix mix) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack createAudioTrackSource(android.media.audiopolicy.AudioMix mix) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  int getStatus() { throw new RuntimeException("Stub!"); }
public  java.lang.String toLogFriendlyString() { throw new RuntimeException("Stub!"); }
public static final int FOCUS_POLICY_DUCKING_DEFAULT = 0;
public static final int FOCUS_POLICY_DUCKING_IN_APP = 0;
public static final int FOCUS_POLICY_DUCKING_IN_POLICY = 1;
public static final int POLICY_STATUS_REGISTERED = 2;
public static final int POLICY_STATUS_UNREGISTERED = 1;
}
