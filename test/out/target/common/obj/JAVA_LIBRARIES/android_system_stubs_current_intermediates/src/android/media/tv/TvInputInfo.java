package android.media.tv;
public final class TvInputInfo
  implements android.os.Parcelable
{
public static final class TvInputSettings
{
TvInputSettings() { throw new RuntimeException("Stub!"); }
public static  java.util.Set<java.lang.String> getHiddenTvInputIds(android.content.Context context, int userId) { throw new RuntimeException("Stub!"); }
public static  java.util.Map<java.lang.String, java.lang.String> getCustomLabels(android.content.Context context, int userId) { throw new RuntimeException("Stub!"); }
public static  void putHiddenTvInputs(android.content.Context context, java.util.Set<java.lang.String> hiddenInputIds, int userId) { throw new RuntimeException("Stub!"); }
public static  void putCustomLabels(android.content.Context context, java.util.Map<java.lang.String, java.lang.String> customLabels, int userId) { throw new RuntimeException("Stub!"); }
}
TvInputInfo() { throw new RuntimeException("Stub!"); }
public static  android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo service, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, java.lang.String parentId, java.lang.String label, android.net.Uri iconUri) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo service, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, java.lang.String parentId, int labelRes, android.graphics.drawable.Icon icon) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo service, android.media.tv.TvInputHardwareInfo hardwareInfo, java.lang.String label, android.net.Uri iconUri) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.media.tv.TvInputInfo createTvInputInfo(android.content.Context context, android.content.pm.ResolveInfo service, android.media.tv.TvInputHardwareInfo hardwareInfo, int labelRes, android.graphics.drawable.Icon icon) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.lang.String getId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getParentId() { throw new RuntimeException("Stub!"); }
public  android.content.pm.ServiceInfo getServiceInfo() { throw new RuntimeException("Stub!"); }
public  android.content.Intent createSetupIntent() { throw new RuntimeException("Stub!"); }
public  android.content.Intent createSettingsIntent() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  android.hardware.hdmi.HdmiDeviceInfo getHdmiDeviceInfo() { throw new RuntimeException("Stub!"); }
public  boolean isPassthroughInput() { throw new RuntimeException("Stub!"); }
public  boolean isHardwareInput() { throw new RuntimeException("Stub!"); }
public  boolean isConnectedToHdmiSwitch() { throw new RuntimeException("Stub!"); }
public  boolean isHidden(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence loadLabel(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence loadCustomLabel(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Drawable loadIcon(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.tv.TvInputInfo> CREATOR;
public static final java.lang.String EXTRA_INPUT_ID = "android.media.tv.extra.INPUT_ID";
public static final int TYPE_COMPONENT = 1004;
public static final int TYPE_COMPOSITE = 1001;
public static final int TYPE_DISPLAY_PORT = 1008;
public static final int TYPE_DVI = 1006;
public static final int TYPE_HDMI = 1007;
public static final int TYPE_OTHER = 1000;
public static final int TYPE_SCART = 1003;
public static final int TYPE_SVIDEO = 1002;
public static final int TYPE_TUNER = 0;
public static final int TYPE_VGA = 1005;
static { CREATOR = null; }
}
