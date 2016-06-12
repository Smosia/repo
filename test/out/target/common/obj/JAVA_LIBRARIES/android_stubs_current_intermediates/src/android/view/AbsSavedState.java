package android.view;
public abstract class AbsSavedState
  implements android.os.Parcelable
{
protected  AbsSavedState(android.os.Parcelable superState) { throw new RuntimeException("Stub!"); }
protected  AbsSavedState(android.os.Parcel source) { throw new RuntimeException("Stub!"); }
public final  android.os.Parcelable getSuperState() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.view.AbsSavedState> CREATOR;
public static final android.view.AbsSavedState EMPTY_STATE;
static { CREATOR = null; EMPTY_STATE = null; }
}
