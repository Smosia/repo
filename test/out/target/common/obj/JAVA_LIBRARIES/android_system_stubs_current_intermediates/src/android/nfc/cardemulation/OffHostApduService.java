package android.nfc.cardemulation;
public abstract class OffHostApduService
  extends android.app.Service
{
public  OffHostApduService() { throw new RuntimeException("Stub!"); }
public abstract  android.os.IBinder onBind(android.content.Intent intent);
public static final java.lang.String SERVICE_INTERFACE = "android.nfc.cardemulation.action.OFF_HOST_APDU_SERVICE";
public static final java.lang.String SERVICE_META_DATA = "android.nfc.cardemulation.off_host_apdu_service";
}
