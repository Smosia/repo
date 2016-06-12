package android.media.audiopolicy;
public class AudioMix
{
public static class Builder
{
public  Builder(android.media.audiopolicy.AudioMixingRule rule) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioMix.Builder setFormat(android.media.AudioFormat format) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioMix.Builder setRouteFlags(int routeFlags) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.audiopolicy.AudioMix build() throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
}
AudioMix() { throw new RuntimeException("Stub!"); }
public  int getMixState() { throw new RuntimeException("Stub!"); }
public static final int MIX_STATE_DISABLED = -1;
public static final int MIX_STATE_IDLE = 0;
public static final int MIX_STATE_MIXING = 1;
public static final int ROUTE_FLAG_LOOP_BACK = 2;
public static final int ROUTE_FLAG_RENDER = 1;
}
