package android.app.backup;
public class BackupTransport
{
public  BackupTransport() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder getBinder() { throw new RuntimeException("Stub!"); }
public  java.lang.String name() { throw new RuntimeException("Stub!"); }
public  android.content.Intent configurationIntent() { throw new RuntimeException("Stub!"); }
public  java.lang.String currentDestinationString() { throw new RuntimeException("Stub!"); }
public  android.content.Intent dataManagementIntent() { throw new RuntimeException("Stub!"); }
public  java.lang.String dataManagementLabel() { throw new RuntimeException("Stub!"); }
public  java.lang.String transportDirName() { throw new RuntimeException("Stub!"); }
public  int initializeDevice() { throw new RuntimeException("Stub!"); }
public  int clearBackupData(android.content.pm.PackageInfo packageInfo) { throw new RuntimeException("Stub!"); }
public  int finishBackup() { throw new RuntimeException("Stub!"); }
public  long requestBackupTime() { throw new RuntimeException("Stub!"); }
public  int performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor inFd) { throw new RuntimeException("Stub!"); }
public  android.app.backup.RestoreSet[] getAvailableRestoreSets() { throw new RuntimeException("Stub!"); }
public  long getCurrentRestoreSet() { throw new RuntimeException("Stub!"); }
public  int startRestore(long token, android.content.pm.PackageInfo[] packages) { throw new RuntimeException("Stub!"); }
public  android.app.backup.RestoreDescription nextRestorePackage() { throw new RuntimeException("Stub!"); }
public  int getRestoreData(android.os.ParcelFileDescriptor outFd) { throw new RuntimeException("Stub!"); }
public  void finishRestore() { throw new RuntimeException("Stub!"); }
public  long requestFullBackupTime() { throw new RuntimeException("Stub!"); }
public  int performFullBackup(android.content.pm.PackageInfo targetPackage, android.os.ParcelFileDescriptor socket) { throw new RuntimeException("Stub!"); }
public  int checkFullBackupSize(long size) { throw new RuntimeException("Stub!"); }
public  int sendBackupData(int numBytes) { throw new RuntimeException("Stub!"); }
public  void cancelFullBackup() { throw new RuntimeException("Stub!"); }
public  int getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor socket) { throw new RuntimeException("Stub!"); }
public  int abortFullRestore() { throw new RuntimeException("Stub!"); }
public static final int AGENT_ERROR = -1003;
public static final int AGENT_UNKNOWN = -1004;
public static final int NO_MORE_DATA = -1;
public static final int TRANSPORT_ERROR = -1000;
public static final int TRANSPORT_NOT_INITIALIZED = -1001;
public static final int TRANSPORT_OK = 0;
public static final int TRANSPORT_PACKAGE_REJECTED = -1002;
}
