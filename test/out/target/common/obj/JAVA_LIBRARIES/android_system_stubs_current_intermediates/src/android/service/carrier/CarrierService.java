package android.service.carrier;
public abstract class CarrierService
  extends android.app.Service
{
public  CarrierService() { throw new RuntimeException("Stub!"); }
public abstract  android.os.PersistableBundle onLoadConfig(android.service.carrier.CarrierIdentifier id);
public final  void notifyCarrierNetworkChange(boolean active) { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String CARRIER_SERVICE_INTERFACE = "android.service.carrier.CarrierService";
}
