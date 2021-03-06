package android.content;
public final class SyncResult
  implements android.os.Parcelable
{
public  SyncResult() { throw new RuntimeException("Stub!"); }
public  boolean hasHardError() { throw new RuntimeException("Stub!"); }
public  boolean hasSoftError() { throw new RuntimeException("Stub!"); }
public  boolean hasError() { throw new RuntimeException("Stub!"); }
public  boolean madeSomeProgress() { throw new RuntimeException("Stub!"); }
public  void clear() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  java.lang.String toDebugString() { throw new RuntimeException("Stub!"); }
public static final android.content.SyncResult ALREADY_IN_PROGRESS;
public static final android.os.Parcelable.Creator<android.content.SyncResult> CREATOR;
public boolean databaseError;
public long delayUntil;
public boolean fullSyncRequested;
public boolean moreRecordsToGet;
public boolean partialSyncUnavailable;
public final android.content.SyncStats stats;
public final boolean syncAlreadyInProgress;
public boolean tooManyDeletions;
public boolean tooManyRetries;
static { ALREADY_IN_PROGRESS = null; CREATOR = null; }
}
