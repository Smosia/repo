package android.printservice;
public abstract class PrintService
  extends android.app.Service
{
public  PrintService() { throw new RuntimeException("Stub!"); }
protected final  void attachBaseContext(android.content.Context base) { throw new RuntimeException("Stub!"); }
protected  void onConnected() { throw new RuntimeException("Stub!"); }
protected  void onDisconnected() { throw new RuntimeException("Stub!"); }
protected abstract  android.printservice.PrinterDiscoverySession onCreatePrinterDiscoverySession();
protected abstract  void onRequestCancelPrintJob(android.printservice.PrintJob printJob);
protected abstract  void onPrintJobQueued(android.printservice.PrintJob printJob);
public final  java.util.List<android.printservice.PrintJob> getActivePrintJobs() { throw new RuntimeException("Stub!"); }
public final  android.print.PrinterId generatePrinterId(java.lang.String localId) { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String EXTRA_PRINTER_INFO = "android.intent.extra.print.EXTRA_PRINTER_INFO";
public static final java.lang.String EXTRA_PRINT_DOCUMENT_INFO = "android.printservice.extra.PRINT_DOCUMENT_INFO";
public static final java.lang.String EXTRA_PRINT_JOB_INFO = "android.intent.extra.print.PRINT_JOB_INFO";
public static final java.lang.String SERVICE_INTERFACE = "android.printservice.PrintService";
public static final java.lang.String SERVICE_META_DATA = "android.printservice";
}
