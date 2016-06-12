package android.app.job;
public class JobInfo
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder(int jobId, android.content.ComponentName jobService) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setExtras(android.os.PersistableBundle extras) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setRequiredNetworkType(int networkType) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setRequiresCharging(boolean requiresCharging) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setRequiresDeviceIdle(boolean requiresDeviceIdle) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setPeriodic(long intervalMillis) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setMinimumLatency(long minLatencyMillis) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setOverrideDeadline(long maxExecutionDelayMillis) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setBackoffCriteria(long initialBackoffMillis, int backoffPolicy) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo.Builder setPersisted(boolean isPersisted) { throw new RuntimeException("Stub!"); }
public  android.app.job.JobInfo build() { throw new RuntimeException("Stub!"); }
}
JobInfo() { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  android.os.PersistableBundle getExtras() { throw new RuntimeException("Stub!"); }
public  android.content.ComponentName getService() { throw new RuntimeException("Stub!"); }
public  boolean isRequireCharging() { throw new RuntimeException("Stub!"); }
public  boolean isRequireDeviceIdle() { throw new RuntimeException("Stub!"); }
public  int getNetworkType() { throw new RuntimeException("Stub!"); }
public  long getMinLatencyMillis() { throw new RuntimeException("Stub!"); }
public  long getMaxExecutionDelayMillis() { throw new RuntimeException("Stub!"); }
public  boolean isPeriodic() { throw new RuntimeException("Stub!"); }
public  boolean isPersisted() { throw new RuntimeException("Stub!"); }
public  long getIntervalMillis() { throw new RuntimeException("Stub!"); }
public  long getInitialBackoffMillis() { throw new RuntimeException("Stub!"); }
public  int getBackoffPolicy() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int BACKOFF_POLICY_EXPONENTIAL = 1;
public static final int BACKOFF_POLICY_LINEAR = 0;
public static final android.os.Parcelable.Creator<android.app.job.JobInfo> CREATOR;
public static final long DEFAULT_INITIAL_BACKOFF_MILLIS = 30000L;
public static final long MAX_BACKOFF_DELAY_MILLIS = 18000000L;
public static final int NETWORK_TYPE_ANY = 1;
public static final int NETWORK_TYPE_NONE = 0;
public static final int NETWORK_TYPE_UNMETERED = 2;
static { CREATOR = null; }
}
