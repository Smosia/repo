package android.widget;
public class SimpleAdapter
  extends android.widget.BaseAdapter
  implements android.widget.Filterable, android.widget.ThemedSpinnerAdapter
{
public static interface ViewBinder
{
public abstract  boolean setViewValue(android.view.View view, java.lang.Object data, java.lang.String textRepresentation);
}
public  SimpleAdapter(android.content.Context context, java.util.List<? extends java.util.Map<java.lang.String, ?>> data, int resource, java.lang.String[] from, int[] to) { throw new RuntimeException("Stub!"); }
public  int getCount() { throw new RuntimeException("Stub!"); }
public  java.lang.Object getItem(int position) { throw new RuntimeException("Stub!"); }
public  long getItemId(int position) { throw new RuntimeException("Stub!"); }
public  android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) { throw new RuntimeException("Stub!"); }
public  void setDropDownViewResource(int resource) { throw new RuntimeException("Stub!"); }
public  void setDropDownViewTheme(android.content.res.Resources.Theme theme) { throw new RuntimeException("Stub!"); }
public  android.content.res.Resources.Theme getDropDownViewTheme() { throw new RuntimeException("Stub!"); }
public  android.view.View getDropDownView(int position, android.view.View convertView, android.view.ViewGroup parent) { throw new RuntimeException("Stub!"); }
public  android.widget.SimpleAdapter.ViewBinder getViewBinder() { throw new RuntimeException("Stub!"); }
public  void setViewBinder(android.widget.SimpleAdapter.ViewBinder viewBinder) { throw new RuntimeException("Stub!"); }
public  void setViewImage(android.widget.ImageView v, int value) { throw new RuntimeException("Stub!"); }
public  void setViewImage(android.widget.ImageView v, java.lang.String value) { throw new RuntimeException("Stub!"); }
public  void setViewText(android.widget.TextView v, java.lang.String text) { throw new RuntimeException("Stub!"); }
public  android.widget.Filter getFilter() { throw new RuntimeException("Stub!"); }
}
