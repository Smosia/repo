package android.app.backup;
public class BackupManager
{
public  BackupManager(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  void dataChanged() { throw new RuntimeException("Stub!"); }
public static  void dataChanged(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  int requestRestore(android.app.backup.RestoreObserver observer) { throw new RuntimeException("Stub!"); }
public  android.app.backup.RestoreSession beginRestoreSession() { throw new RuntimeException("Stub!"); }
public  void setBackupEnabled(boolean isEnabled) { throw new RuntimeException("Stub!"); }
public  boolean isBackupEnabled() { throw new RuntimeException("Stub!"); }
public  void setAutoRestore(boolean isEnabled) { throw new RuntimeException("Stub!"); }
public  java.lang.String getCurrentTransport() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] listAllTransports() { throw new RuntimeException("Stub!"); }
public  java.lang.String selectBackupTransport(java.lang.String transport) { throw new RuntimeException("Stub!"); }
public  void backupNow() { throw new RuntimeException("Stub!"); }
public  long getAvailableRestoreToken(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
}
