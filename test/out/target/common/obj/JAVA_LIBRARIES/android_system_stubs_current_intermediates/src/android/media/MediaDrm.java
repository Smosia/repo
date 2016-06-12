package android.media;
public final class MediaDrm
{
public static final class MediaDrmStateException
  extends java.lang.IllegalStateException
{
MediaDrmStateException() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDiagnosticInfo() { throw new RuntimeException("Stub!"); }
}
public static interface OnExpirationUpdateListener
{
public abstract  void onExpirationUpdate(android.media.MediaDrm md, byte[] sessionId, long expirationTime);
}
public static interface OnKeyStatusChangeListener
{
public abstract  void onKeyStatusChange(android.media.MediaDrm md, byte[] sessionId, java.util.List<android.media.MediaDrm.KeyStatus> keyInformation, boolean hasNewUsableKey);
}
public static final class KeyStatus
{
KeyStatus() { throw new RuntimeException("Stub!"); }
public  int getStatusCode() { throw new RuntimeException("Stub!"); }
public  byte[] getKeyId() { throw new RuntimeException("Stub!"); }
public static final int STATUS_EXPIRED = 1;
public static final int STATUS_INTERNAL_ERROR = 4;
public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
public static final int STATUS_PENDING = 3;
public static final int STATUS_USABLE = 0;
}
public static interface OnEventListener
{
public abstract  void onEvent(android.media.MediaDrm md, byte[] sessionId, int event, int extra, byte[] data);
}
public static final class KeyRequest
{
KeyRequest() { throw new RuntimeException("Stub!"); }
public  byte[] getData() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDefaultUrl() { throw new RuntimeException("Stub!"); }
public  int getRequestType() { throw new RuntimeException("Stub!"); }
public static final int REQUEST_TYPE_INITIAL = 0;
public static final int REQUEST_TYPE_RELEASE = 2;
public static final int REQUEST_TYPE_RENEWAL = 1;
}
public static final class ProvisionRequest
{
ProvisionRequest() { throw new RuntimeException("Stub!"); }
public  byte[] getData() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDefaultUrl() { throw new RuntimeException("Stub!"); }
}
public final class CryptoSession
{
CryptoSession() { throw new RuntimeException("Stub!"); }
public  byte[] encrypt(byte[] keyid, byte[] input, byte[] iv) { throw new RuntimeException("Stub!"); }
public  byte[] decrypt(byte[] keyid, byte[] input, byte[] iv) { throw new RuntimeException("Stub!"); }
public  byte[] sign(byte[] keyid, byte[] message) { throw new RuntimeException("Stub!"); }
public  boolean verify(byte[] keyid, byte[] message, byte[] signature) { throw new RuntimeException("Stub!"); }
}
public  MediaDrm(java.util.UUID uuid) throws android.media.UnsupportedSchemeException { throw new RuntimeException("Stub!"); }
public static final  boolean isCryptoSchemeSupported(java.util.UUID uuid) { throw new RuntimeException("Stub!"); }
public static final  boolean isCryptoSchemeSupported(java.util.UUID uuid, java.lang.String mimeType) { throw new RuntimeException("Stub!"); }
public  void setOnExpirationUpdateListener(android.media.MediaDrm.OnExpirationUpdateListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setOnKeyStatusChangeListener(android.media.MediaDrm.OnKeyStatusChangeListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setOnEventListener(android.media.MediaDrm.OnEventListener listener) { throw new RuntimeException("Stub!"); }
public native  byte[] openSession() throws android.media.NotProvisionedException, android.media.ResourceBusyException;
public native  void closeSession(byte[] sessionId);
public native  android.media.MediaDrm.KeyRequest getKeyRequest(byte[] scope, byte[] init, java.lang.String mimeType, int keyType, java.util.HashMap<java.lang.String, java.lang.String> optionalParameters) throws android.media.NotProvisionedException;
public native  byte[] provideKeyResponse(byte[] scope, byte[] response) throws android.media.NotProvisionedException, android.media.DeniedByServerException;
public native  void restoreKeys(byte[] sessionId, byte[] keySetId);
public native  void removeKeys(byte[] sessionId);
public native  java.util.HashMap<java.lang.String, java.lang.String> queryKeyStatus(byte[] sessionId);
public  android.media.MediaDrm.ProvisionRequest getProvisionRequest() { throw new RuntimeException("Stub!"); }
public  void provideProvisionResponse(byte[] response) throws android.media.DeniedByServerException { throw new RuntimeException("Stub!"); }
public native  void unprovisionDevice();
public native  java.util.List<byte[]> getSecureStops();
public native  byte[] getSecureStop(byte[] ssid);
public native  void releaseSecureStops(byte[] ssRelease);
public native  void releaseAllSecureStops();
public native  java.lang.String getPropertyString(java.lang.String propertyName);
public native  byte[] getPropertyByteArray(java.lang.String propertyName);
public native  void setPropertyString(java.lang.String propertyName, java.lang.String value);
public native  void setPropertyByteArray(java.lang.String propertyName, byte[] value);
public  android.media.MediaDrm.CryptoSession getCryptoSession(byte[] sessionId, java.lang.String cipherAlgorithm, java.lang.String macAlgorithm) { throw new RuntimeException("Stub!"); }
protected  void finalize() { throw new RuntimeException("Stub!"); }
public final native  void release();
public static final int EVENT_KEY_EXPIRED = 3;
public static final int EVENT_KEY_REQUIRED = 2;
@Deprecated
public static final int EVENT_PROVISION_REQUIRED = 1;
public static final int EVENT_SESSION_RECLAIMED = 5;
public static final int EVENT_VENDOR_DEFINED = 4;
public static final int KEY_TYPE_OFFLINE = 2;
public static final int KEY_TYPE_RELEASE = 3;
public static final int KEY_TYPE_STREAMING = 1;
public static final java.lang.String PROPERTY_ALGORITHMS = "algorithms";
public static final java.lang.String PROPERTY_DESCRIPTION = "description";
public static final java.lang.String PROPERTY_DEVICE_UNIQUE_ID = "deviceUniqueId";
public static final java.lang.String PROPERTY_VENDOR = "vendor";
public static final java.lang.String PROPERTY_VERSION = "version";
}
