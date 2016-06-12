package android.view.inputmethod;
public final class InputMethodSubtype
  implements android.os.Parcelable
{
public static class InputMethodSubtypeBuilder
{
public  InputMethodSubtypeBuilder() { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setIsAuxiliary(boolean isAuxiliary) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setOverridesImplicitlyEnabledSubtype(boolean overridesImplicitlyEnabledSubtype) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setIsAsciiCapable(boolean isAsciiCapable) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeIconResId(int subtypeIconResId) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeNameResId(int subtypeNameResId) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeId(int subtypeId) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeLocale(java.lang.String subtypeLocale) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeMode(java.lang.String subtypeMode) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder setSubtypeExtraValue(java.lang.String subtypeExtraValue) { throw new RuntimeException("Stub!"); }
public  android.view.inputmethod.InputMethodSubtype build() { throw new RuntimeException("Stub!"); }
}
@Deprecated
public  InputMethodSubtype(int nameId, int iconId, java.lang.String locale, java.lang.String mode, java.lang.String extraValue, boolean isAuxiliary, boolean overridesImplicitlyEnabledSubtype) { throw new RuntimeException("Stub!"); }
@Deprecated
public  InputMethodSubtype(int nameId, int iconId, java.lang.String locale, java.lang.String mode, java.lang.String extraValue, boolean isAuxiliary, boolean overridesImplicitlyEnabledSubtype, int id) { throw new RuntimeException("Stub!"); }
public  int getNameResId() { throw new RuntimeException("Stub!"); }
public  int getIconResId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getLocale() { throw new RuntimeException("Stub!"); }
public  java.lang.String getMode() { throw new RuntimeException("Stub!"); }
public  java.lang.String getExtraValue() { throw new RuntimeException("Stub!"); }
public  boolean isAuxiliary() { throw new RuntimeException("Stub!"); }
public  boolean overridesImplicitlyEnabledSubtype() { throw new RuntimeException("Stub!"); }
public  boolean isAsciiCapable() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getDisplayName(android.content.Context context, java.lang.String packageName, android.content.pm.ApplicationInfo appInfo) { throw new RuntimeException("Stub!"); }
public  boolean containsExtraValueKey(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  java.lang.String getExtraValueOf(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.view.inputmethod.InputMethodSubtype> CREATOR;
static { CREATOR = null; }
}
