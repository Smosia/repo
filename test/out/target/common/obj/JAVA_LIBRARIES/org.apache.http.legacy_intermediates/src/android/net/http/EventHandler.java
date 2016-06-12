package android.net.http;
public interface EventHandler
{
public abstract  void status(int major_version, int minor_version, int code, java.lang.String reason_phrase);
public abstract  void headers(android.net.http.Headers headers);
public abstract  void data(byte[] data, int len);
public abstract  void endData();
public abstract  void certificate(android.net.http.SslCertificate certificate);
public abstract  void error(int id, java.lang.String description);
public abstract  boolean handleSslErrorRequest(android.net.http.SslError error);
public static final int ERROR = -1;
public static final int ERROR_AUTH = -4;
public static final int ERROR_BAD_URL = -12;
public static final int ERROR_CONNECT = -6;
public static final int ERROR_FAILED_SSL_HANDSHAKE = -11;
public static final int ERROR_IO = -7;
public static final int ERROR_LOOKUP = -2;
public static final int ERROR_PROXYAUTH = -5;
public static final int ERROR_REDIRECT_LOOP = -9;
public static final int ERROR_TIMEOUT = -8;
public static final int ERROR_UNSUPPORTED_AUTH_SCHEME = -3;
public static final int ERROR_UNSUPPORTED_SCHEME = -10;
public static final int FILE_ERROR = -13;
public static final int FILE_NOT_FOUND_ERROR = -14;
public static final int OK = 0;
public static final int TOO_MANY_REQUESTS_ERROR = -15;
}