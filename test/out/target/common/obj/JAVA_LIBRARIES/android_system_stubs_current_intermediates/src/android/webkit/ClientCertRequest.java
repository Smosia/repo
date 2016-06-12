package android.webkit;
public abstract class ClientCertRequest
{
public  ClientCertRequest() { throw new RuntimeException("Stub!"); }
public abstract  java.lang.String[] getKeyTypes();
public abstract  java.security.Principal[] getPrincipals();
public abstract  java.lang.String getHost();
public abstract  int getPort();
public abstract  void proceed(java.security.PrivateKey privateKey, java.security.cert.X509Certificate[] chain);
public abstract  void ignore();
public abstract  void cancel();
}
