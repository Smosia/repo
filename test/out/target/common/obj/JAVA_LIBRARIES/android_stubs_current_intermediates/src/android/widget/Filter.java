package android.widget;
public abstract class Filter
{
protected static class FilterResults
{
public  FilterResults() { throw new RuntimeException("Stub!"); }
public int count;
public java.lang.Object values;
}
public static interface FilterListener
{
public abstract  void onFilterComplete(int count);
}
public  Filter() { throw new RuntimeException("Stub!"); }
public final  void filter(java.lang.CharSequence constraint) { throw new RuntimeException("Stub!"); }
public final  void filter(java.lang.CharSequence constraint, android.widget.Filter.FilterListener listener) { throw new RuntimeException("Stub!"); }
protected abstract  android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence constraint);
protected abstract  void publishResults(java.lang.CharSequence constraint, android.widget.Filter.FilterResults results);
public  java.lang.CharSequence convertResultToString(java.lang.Object resultValue) { throw new RuntimeException("Stub!"); }
}
