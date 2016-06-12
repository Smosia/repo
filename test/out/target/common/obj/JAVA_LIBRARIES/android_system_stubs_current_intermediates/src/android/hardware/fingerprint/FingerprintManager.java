package android.hardware.fingerprint;
public class FingerprintManager
{
public static final class CryptoObject
{
public  CryptoObject(java.security.Signature signature) { throw new RuntimeException("Stub!"); }
public  CryptoObject(javax.crypto.Cipher cipher) { throw new RuntimeException("Stub!"); }
public  CryptoObject(javax.crypto.Mac mac) { throw new RuntimeException("Stub!"); }
public  java.security.Signature getSignature() { throw new RuntimeException("Stub!"); }
public  javax.crypto.Cipher getCipher() { throw new RuntimeException("Stub!"); }
public  javax.crypto.Mac getMac() { throw new RuntimeException("Stub!"); }
}
public static class AuthenticationResult
{
AuthenticationResult() { throw new RuntimeException("Stub!"); }
public  android.hardware.fingerprint.FingerprintManager.CryptoObject getCryptoObject() { throw new RuntimeException("Stub!"); }
}
public abstract static class AuthenticationCallback
{
public  AuthenticationCallback() { throw new RuntimeException("Stub!"); }
public  void onAuthenticationError(int errorCode, java.lang.CharSequence errString) { throw new RuntimeException("Stub!"); }
public  void onAuthenticationHelp(int helpCode, java.lang.CharSequence helpString) { throw new RuntimeException("Stub!"); }
public  void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result) { throw new RuntimeException("Stub!"); }
public  void onAuthenticationFailed() { throw new RuntimeException("Stub!"); }
}
FingerprintManager() { throw new RuntimeException("Stub!"); }
public  void authenticate(android.hardware.fingerprint.FingerprintManager.CryptoObject crypto, android.os.CancellationSignal cancel, int flags, android.hardware.fingerprint.FingerprintManager.AuthenticationCallback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  boolean hasEnrolledFingerprints() { throw new RuntimeException("Stub!"); }
public  boolean isHardwareDetected() { throw new RuntimeException("Stub!"); }
public static final int FINGERPRINT_ACQUIRED_GOOD = 0;
public static final int FINGERPRINT_ACQUIRED_IMAGER_DIRTY = 3;
public static final int FINGERPRINT_ACQUIRED_INSUFFICIENT = 2;
public static final int FINGERPRINT_ACQUIRED_PARTIAL = 1;
public static final int FINGERPRINT_ACQUIRED_TOO_FAST = 5;
public static final int FINGERPRINT_ACQUIRED_TOO_SLOW = 4;
public static final int FINGERPRINT_ERROR_CANCELED = 5;
public static final int FINGERPRINT_ERROR_HW_UNAVAILABLE = 1;
public static final int FINGERPRINT_ERROR_LOCKOUT = 7;
public static final int FINGERPRINT_ERROR_NO_SPACE = 4;
public static final int FINGERPRINT_ERROR_TIMEOUT = 3;
public static final int FINGERPRINT_ERROR_UNABLE_TO_PROCESS = 2;
}
