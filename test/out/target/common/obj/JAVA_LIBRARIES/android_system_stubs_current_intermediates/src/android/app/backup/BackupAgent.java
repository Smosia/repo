package android.app.backup;
public abstract class BackupAgent
  extends android.content.ContextWrapper
{
public  BackupAgent() { super((android.content.Context)null); throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public  void onDestroy() { throw new RuntimeException("Stub!"); }
public abstract  void onBackup(android.os.ParcelFileDescriptor oldState, android.app.backup.BackupDataOutput data, android.os.ParcelFileDescriptor newState) throws java.io.IOException;
public abstract  void onRestore(android.app.backup.BackupDataInput data, int appVersionCode, android.os.ParcelFileDescriptor newState) throws java.io.IOException;
public  void onFullBackup(android.app.backup.FullBackupDataOutput data) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final  void fullBackupFile(java.io.File file, android.app.backup.FullBackupDataOutput output) { throw new RuntimeException("Stub!"); }
public  void onRestoreFile(android.os.ParcelFileDescriptor data, long size, java.io.File destination, int type, long mode, long mtime) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void onRestoreFinished() { throw new RuntimeException("Stub!"); }
public static final int TYPE_DIRECTORY = 2;
public static final int TYPE_FILE = 1;
}
