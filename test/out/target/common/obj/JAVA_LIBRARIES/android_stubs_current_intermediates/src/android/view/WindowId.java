package android.view;
public class WindowId
  implements android.os.Parcelable
{
public abstract static class FocusObserver
{
public  FocusObserver() { throw new RuntimeException("Stub!"); }
public abstract  void onFocusGained(android.view.WindowId token);
public abstract  void onFocusLost(android.view.WindowId token);
}
WindowId() { throw new RuntimeException("Stub!"); }
public  boolean isFocused() { throw new RuntimeException("Stub!"); }
public  void registerFocusObserver(android.view.WindowId.FocusObserver observer) { throw new RuntimeException("Stub!"); }
public  void unregisterFocusObserver(android.view.WindowId.FocusObserver observer) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object otherObj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.view.WindowId> CREATOR;
static { CREATOR = null; }
}
