package android.view.accessibility;
public final class AccessibilityWindowInfo
  implements android.os.Parcelable
{
AccessibilityWindowInfo() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getLayer() { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityNodeInfo getRoot() { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityWindowInfo getParent() { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  void getBoundsInScreen(android.graphics.Rect outBounds) { throw new RuntimeException("Stub!"); }
public  boolean isActive() { throw new RuntimeException("Stub!"); }
public  boolean isFocused() { throw new RuntimeException("Stub!"); }
public  boolean isAccessibilityFocused() { throw new RuntimeException("Stub!"); }
public  int getChildCount() { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityWindowInfo getChild(int index) { throw new RuntimeException("Stub!"); }
public static  android.view.accessibility.AccessibilityWindowInfo obtain() { throw new RuntimeException("Stub!"); }
public static  android.view.accessibility.AccessibilityWindowInfo obtain(android.view.accessibility.AccessibilityWindowInfo info) { throw new RuntimeException("Stub!"); }
public  void recycle() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.view.accessibility.AccessibilityWindowInfo> CREATOR;
public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
public static final int TYPE_APPLICATION = 1;
public static final int TYPE_INPUT_METHOD = 2;
public static final int TYPE_SYSTEM = 3;
static { CREATOR = null; }
}
