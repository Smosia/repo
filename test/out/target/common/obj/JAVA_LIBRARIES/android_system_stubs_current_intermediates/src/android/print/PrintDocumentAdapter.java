package android.print;
public abstract class PrintDocumentAdapter
{
public abstract static class WriteResultCallback
{
WriteResultCallback() { throw new RuntimeException("Stub!"); }
public  void onWriteFinished(android.print.PageRange[] pages) { throw new RuntimeException("Stub!"); }
public  void onWriteFailed(java.lang.CharSequence error) { throw new RuntimeException("Stub!"); }
public  void onWriteCancelled() { throw new RuntimeException("Stub!"); }
}
public abstract static class LayoutResultCallback
{
LayoutResultCallback() { throw new RuntimeException("Stub!"); }
public  void onLayoutFinished(android.print.PrintDocumentInfo info, boolean changed) { throw new RuntimeException("Stub!"); }
public  void onLayoutFailed(java.lang.CharSequence error) { throw new RuntimeException("Stub!"); }
public  void onLayoutCancelled() { throw new RuntimeException("Stub!"); }
}
public  PrintDocumentAdapter() { throw new RuntimeException("Stub!"); }
public  void onStart() { throw new RuntimeException("Stub!"); }
public abstract  void onLayout(android.print.PrintAttributes oldAttributes, android.print.PrintAttributes newAttributes, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.LayoutResultCallback callback, android.os.Bundle extras);
public abstract  void onWrite(android.print.PageRange[] pages, android.os.ParcelFileDescriptor destination, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.WriteResultCallback callback);
public  void onFinish() { throw new RuntimeException("Stub!"); }
public static final java.lang.String EXTRA_PRINT_PREVIEW = "EXTRA_PRINT_PREVIEW";
}
