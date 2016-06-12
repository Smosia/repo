package android.content;
public class SyncRequest
  implements android.os.Parcelable
{
public static class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder syncOnce() { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder syncPeriodic(long pollFrequency, long beforeSeconds) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setDisallowMetered(boolean disallow) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setSyncAdapter(android.accounts.Account account, java.lang.String authority) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setExtras(android.os.Bundle bundle) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setNoRetry(boolean noRetry) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setIgnoreSettings(boolean ignoreSettings) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setIgnoreBackoff(boolean ignoreBackoff) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setManual(boolean isManual) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest.Builder setExpedited(boolean expedited) { throw new RuntimeException("Stub!"); }
public  android.content.SyncRequest build() { throw new RuntimeException("Stub!"); }
}
SyncRequest() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.SyncRequest> CREATOR;
static { CREATOR = null; }
}
