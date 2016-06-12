package android.drm;
public class DrmInfoStatus
{
public  DrmInfoStatus(int statusCode, int infoType, android.drm.ProcessedData data, java.lang.String mimeType) { throw new RuntimeException("Stub!"); }
public static final int STATUS_ERROR = 2;
public static final int STATUS_OK = 1;
public final android.drm.ProcessedData data;
public final int infoType;
public final java.lang.String mimeType;
public final int statusCode;
}
