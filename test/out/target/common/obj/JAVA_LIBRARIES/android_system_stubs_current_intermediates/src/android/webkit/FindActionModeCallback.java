package android.webkit;
public class FindActionModeCallback
  implements android.view.ActionMode.Callback, android.text.TextWatcher, android.view.View.OnClickListener, android.webkit.WebView.FindListener
{
public static class NoAction
  implements android.view.ActionMode.Callback
{
public  NoAction() { throw new RuntimeException("Stub!"); }
public  boolean onCreateActionMode(android.view.ActionMode mode, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onPrepareActionMode(android.view.ActionMode mode, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onActionItemClicked(android.view.ActionMode mode, android.view.MenuItem item) { throw new RuntimeException("Stub!"); }
public  void onDestroyActionMode(android.view.ActionMode mode) { throw new RuntimeException("Stub!"); }
}
public  FindActionModeCallback(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  void finish() { throw new RuntimeException("Stub!"); }
public  void setText(java.lang.String text) { throw new RuntimeException("Stub!"); }
public  void setWebView(android.webkit.WebView webView) { throw new RuntimeException("Stub!"); }
public  void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) { throw new RuntimeException("Stub!"); }
public  void findAll() { throw new RuntimeException("Stub!"); }
public  void showSoftInput() { throw new RuntimeException("Stub!"); }
public  void updateMatchCount(int matchIndex, int matchCount, boolean isEmptyFind) { throw new RuntimeException("Stub!"); }
public  void onClick(android.view.View v) { throw new RuntimeException("Stub!"); }
public  boolean onCreateActionMode(android.view.ActionMode mode, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  void onDestroyActionMode(android.view.ActionMode mode) { throw new RuntimeException("Stub!"); }
public  boolean onPrepareActionMode(android.view.ActionMode mode, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onActionItemClicked(android.view.ActionMode mode, android.view.MenuItem item) { throw new RuntimeException("Stub!"); }
public  void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) { throw new RuntimeException("Stub!"); }
public  void onTextChanged(java.lang.CharSequence s, int start, int before, int count) { throw new RuntimeException("Stub!"); }
public  void afterTextChanged(android.text.Editable s) { throw new RuntimeException("Stub!"); }
public  int getActionModeGlobalBottom() { throw new RuntimeException("Stub!"); }
}
