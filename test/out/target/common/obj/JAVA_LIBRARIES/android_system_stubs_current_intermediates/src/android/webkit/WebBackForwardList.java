package android.webkit;
public abstract class WebBackForwardList
  implements java.lang.Cloneable, java.io.Serializable
{
public  WebBackForwardList() { throw new RuntimeException("Stub!"); }
public abstract  android.webkit.WebHistoryItem getCurrentItem();
public abstract  int getCurrentIndex();
public abstract  android.webkit.WebHistoryItem getItemAtIndex(int index);
public abstract  int getSize();
protected abstract  android.webkit.WebBackForwardList clone();
}
