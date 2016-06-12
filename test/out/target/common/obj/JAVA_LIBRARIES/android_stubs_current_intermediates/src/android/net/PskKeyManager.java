package android.net;
public abstract class PskKeyManager
{
public  PskKeyManager() { throw new RuntimeException("Stub!"); }
public  java.lang.String chooseServerKeyIdentityHint(java.net.Socket socket) { throw new RuntimeException("Stub!"); }
public  java.lang.String chooseServerKeyIdentityHint(javax.net.ssl.SSLEngine engine) { throw new RuntimeException("Stub!"); }
public  java.lang.String chooseClientKeyIdentity(java.lang.String identityHint, java.net.Socket socket) { throw new RuntimeException("Stub!"); }
public  java.lang.String chooseClientKeyIdentity(java.lang.String identityHint, javax.net.ssl.SSLEngine engine) { throw new RuntimeException("Stub!"); }
public  javax.crypto.SecretKey getKey(java.lang.String identityHint, java.lang.String identity, java.net.Socket socket) { throw new RuntimeException("Stub!"); }
public  javax.crypto.SecretKey getKey(java.lang.String identityHint, java.lang.String identity, javax.net.ssl.SSLEngine engine) { throw new RuntimeException("Stub!"); }
public static final int MAX_IDENTITY_HINT_LENGTH_BYTES = 128;
public static final int MAX_IDENTITY_LENGTH_BYTES = 128;
public static final int MAX_KEY_LENGTH_BYTES = 256;
}
