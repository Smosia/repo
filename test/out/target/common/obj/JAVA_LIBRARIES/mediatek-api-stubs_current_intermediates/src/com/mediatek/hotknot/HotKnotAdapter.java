package com.mediatek.hotknot;
public final class HotKnotAdapter
{
public static interface OnHotKnotCompleteCallback
{
public abstract  void onHotKnotComplete(int reason);
}
public static interface CreateHotKnotMessageCallback
{
public abstract  com.mediatek.hotknot.HotKnotMessage createHotKnotMessage();
}
public static interface CreateHotKnotBeamUrisCallback
{
public abstract  android.net.Uri[] createHotKnotBeamUris();
}
HotKnotAdapter() { throw new RuntimeException("Stub!"); }
public static  com.mediatek.hotknot.HotKnotAdapter getDefaultAdapter(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  boolean isEnabled() { throw new RuntimeException("Stub!"); }
public  void setHotKnotMessage(com.mediatek.hotknot.HotKnotMessage message, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void setHotKnotMessageCallback(com.mediatek.hotknot.HotKnotAdapter.CreateHotKnotMessageCallback callback, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void setOnHotKnotCompleteCallback(com.mediatek.hotknot.HotKnotAdapter.OnHotKnotCompleteCallback callback, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void setHotKnotBeamUris(android.net.Uri[] uris, android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  void setHotKnotBeamUrisCallback(com.mediatek.hotknot.HotKnotAdapter.CreateHotKnotBeamUrisCallback callback, android.app.Activity activity) { throw new RuntimeException("Stub!"); }
@android.annotation.SdkConstant(value=android.annotation.SdkConstant.SdkConstantType.BROADCAST_INTENT_ACTION)
public static final java.lang.String ACTION_ADAPTER_STATE_CHANGED = "com.mediatek.hotknot.action.ADAPTER_STATE_CHANGED";
public static final java.lang.String ACTION_HOTKNOT_SETTINGS = "mediatek.settings.HOTKNOT_SETTINGS";
@android.annotation.SdkConstant(value=android.annotation.SdkConstant.SdkConstantType.ACTIVITY_INTENT_ACTION)
public static final java.lang.String ACTION_MESSAGE_DISCOVERED = "com.mediatek.hotknot.action.MESSAGE_DISCOVERED";
public static final int ERROR_DATA_TOO_LARGE = 1;
public static final int ERROR_SUCCESS = 0;
public static final java.lang.String EXTRA_ADAPTER_STATE = "com.mediatek.hotknot.extra.ADAPTER_STATE";
public static final java.lang.String EXTRA_DATA = "com.mediatek.hotknot.extra.DATA";
public static final int STATE_DISABLED = 1;
public static final int STATE_ENABLED = 2;
}
