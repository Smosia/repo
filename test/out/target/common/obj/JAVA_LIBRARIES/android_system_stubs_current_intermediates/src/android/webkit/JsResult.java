package android.webkit;
public class JsResult
{
public static interface ResultReceiver
{
public abstract  void onJsResultComplete(android.webkit.JsResult result);
}
public  JsResult(android.webkit.JsResult.ResultReceiver receiver) { throw new RuntimeException("Stub!"); }
public final  void cancel() { throw new RuntimeException("Stub!"); }
public final  void confirm() { throw new RuntimeException("Stub!"); }
public final  boolean getResult() { throw new RuntimeException("Stub!"); }
}
