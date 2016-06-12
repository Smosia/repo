package android.telephony;
public class IccOpenLogicalChannelResponse
  implements android.os.Parcelable
{
IccOpenLogicalChannelResponse() { throw new RuntimeException("Stub!"); }
public  int getChannel() { throw new RuntimeException("Stub!"); }
public  int getStatus() { throw new RuntimeException("Stub!"); }
public  byte[] getSelectResponse() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.telephony.IccOpenLogicalChannelResponse> CREATOR;
public static final int INVALID_CHANNEL = -1;
public static final int STATUS_MISSING_RESOURCE = 2;
public static final int STATUS_NO_ERROR = 1;
public static final int STATUS_NO_SUCH_ELEMENT = 3;
public static final int STATUS_UNKNOWN_ERROR = 4;
static { CREATOR = null; }
}
