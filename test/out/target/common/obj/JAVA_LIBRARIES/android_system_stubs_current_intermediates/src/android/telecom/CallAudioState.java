package android.telecom;
public final class CallAudioState
  implements android.os.Parcelable
{
public  CallAudioState(boolean muted, int route, int supportedRouteMask) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean isMuted() { throw new RuntimeException("Stub!"); }
public  int getRoute() { throw new RuntimeException("Stub!"); }
public  int getSupportedRouteMask() { throw new RuntimeException("Stub!"); }
public static  java.lang.String audioRouteToString(int route) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel destination, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.telecom.CallAudioState> CREATOR;
public static final int ROUTE_BLUETOOTH = 2;
public static final int ROUTE_EARPIECE = 1;
public static final int ROUTE_SPEAKER = 8;
public static final int ROUTE_WIRED_HEADSET = 4;
public static final int ROUTE_WIRED_OR_EARPIECE = 5;
static { CREATOR = null; }
}
