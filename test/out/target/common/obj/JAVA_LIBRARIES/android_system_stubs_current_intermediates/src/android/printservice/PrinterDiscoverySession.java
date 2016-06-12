package android.printservice;
public abstract class PrinterDiscoverySession
{
public  PrinterDiscoverySession() { throw new RuntimeException("Stub!"); }
public final  java.util.List<android.print.PrinterInfo> getPrinters() { throw new RuntimeException("Stub!"); }
public final  void addPrinters(java.util.List<android.print.PrinterInfo> printers) { throw new RuntimeException("Stub!"); }
public final  void removePrinters(java.util.List<android.print.PrinterId> printerIds) { throw new RuntimeException("Stub!"); }
public abstract  void onStartPrinterDiscovery(java.util.List<android.print.PrinterId> priorityList);
public abstract  void onStopPrinterDiscovery();
public abstract  void onValidatePrinters(java.util.List<android.print.PrinterId> printerIds);
public abstract  void onStartPrinterStateTracking(android.print.PrinterId printerId);
public abstract  void onStopPrinterStateTracking(android.print.PrinterId printerId);
public final  java.util.List<android.print.PrinterId> getTrackedPrinters() { throw new RuntimeException("Stub!"); }
public abstract  void onDestroy();
public final  boolean isDestroyed() { throw new RuntimeException("Stub!"); }
public final  boolean isPrinterDiscoveryStarted() { throw new RuntimeException("Stub!"); }
}
