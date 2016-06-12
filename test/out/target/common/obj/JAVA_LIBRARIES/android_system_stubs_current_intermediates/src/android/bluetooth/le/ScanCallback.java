package android.bluetooth.le;
public abstract class ScanCallback
{
public  ScanCallback() { throw new RuntimeException("Stub!"); }
public  void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) { throw new RuntimeException("Stub!"); }
public  void onBatchScanResults(java.util.List<android.bluetooth.le.ScanResult> results) { throw new RuntimeException("Stub!"); }
public  void onScanFailed(int errorCode) { throw new RuntimeException("Stub!"); }
public static final int SCAN_FAILED_ALREADY_STARTED = 1;
public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;
public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;
public static final int SCAN_FAILED_INTERNAL_ERROR = 3;
}
