package android.nfc.cardemulation;
public abstract class HostApduService
  extends android.app.Service
{
public  HostApduService() { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public final  void sendResponseApdu(byte[] responseApdu) { throw new RuntimeException("Stub!"); }
public final  void notifyUnhandled() { throw new RuntimeException("Stub!"); }
public abstract  byte[] processCommandApdu(byte[] commandApdu, android.os.Bundle extras);
public abstract  void onDeactivated(int reason);
public static final int DEACTIVATION_DESELECTED = 1;
public static final int DEACTIVATION_LINK_LOSS = 0;
public static final java.lang.String SERVICE_INTERFACE = "android.nfc.cardemulation.action.HOST_APDU_SERVICE";
public static final java.lang.String SERVICE_META_DATA = "android.nfc.cardemulation.host_apdu_service";
}
