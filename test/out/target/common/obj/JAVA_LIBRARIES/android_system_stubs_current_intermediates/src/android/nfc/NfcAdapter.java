package android.nfc;
public final class NfcAdapter
{
public static interface ReaderCallback
{
public abstract  void onTagDiscovered(android.nfc.Tag tag);
}
public static interface OnNdefPushCompleteCallback
{
public abstract  void onNdefPushComplete(android.nfc.NfcEvent event);
}
public static interface CreateNdefMessageCallback
{
public abstract  android.nfc.NdefMessage createNdefMessage(android.nfc.NfcEvent event);
}
public static interface CreateBeamUrisCallback
{
public abstract  android.net.Uri[] createBeamUris(android.nfc.NfcEvent event);
}
public static interface NfcUnlockHandler
{
public abstract  boolean onUnlockAttempted(android.nfc.Tag tag);
}
NfcAdapter() { throw new RuntimeException("Stub!"); }
public static  android.nfc.NfcAdapter getDefaultAdapter(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  boolean isEnabled() { throw new RuntimeException("Stub!"); }
public  boolean enable() { throw new RuntimeException("Stub!"); }
public  boolean disable() { throw new RuntimeException("Stub!"); }
public  boolean disable(boolean persist) { throw new RuntimeException("Stub!"); }
public  void setBeamPushUris(android.net.Uri[] uris, android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  void setBeamPushUrisCallback(android.nfc.NfcAdapter.CreateBeamUrisCallback callback, android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  void setNdefPushMessage(android.nfc.NdefMessage message, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void setNdefPushMessage(android.nfc.NdefMessage message, android.app.Activity activity, int flags) { throw new RuntimeException("Stub!"); }
public  void setNdefPushMessageCallback(android.nfc.NfcAdapter.CreateNdefMessageCallback callback, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void setOnNdefPushCompleteCallback(android.nfc.NfcAdapter.OnNdefPushCompleteCallback callback, android.app.Activity activity, android.app.Activity... activities) { throw new RuntimeException("Stub!"); }
public  void enableForegroundDispatch(android.app.Activity activity, android.app.PendingIntent intent, android.content.IntentFilter[] filters, java.lang.String[][] techLists) { throw new RuntimeException("Stub!"); }
public  void disableForegroundDispatch(android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  void enableReaderMode(android.app.Activity activity, android.nfc.NfcAdapter.ReaderCallback callback, int flags, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  void disableReaderMode(android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  boolean invokeBeam(android.app.Activity activity) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void enableForegroundNdefPush(android.app.Activity activity, android.nfc.NdefMessage message) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void disableForegroundNdefPush(android.app.Activity activity) { throw new RuntimeException("Stub!"); }
public  boolean enableNdefPush() { throw new RuntimeException("Stub!"); }
public  boolean disableNdefPush() { throw new RuntimeException("Stub!"); }
public  boolean isNdefPushEnabled() { throw new RuntimeException("Stub!"); }
public  boolean addNfcUnlockHandler(android.nfc.NfcAdapter.NfcUnlockHandler unlockHandler, java.lang.String[] tagTechnologies) { throw new RuntimeException("Stub!"); }
public  boolean removeNfcUnlockHandler(android.nfc.NfcAdapter.NfcUnlockHandler unlockHandler) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_ADAPTER_STATE_CHANGED = "android.nfc.action.ADAPTER_STATE_CHANGED";
public static final java.lang.String ACTION_NDEF_DISCOVERED = "android.nfc.action.NDEF_DISCOVERED";
public static final java.lang.String ACTION_TAG_DISCOVERED = "android.nfc.action.TAG_DISCOVERED";
public static final java.lang.String ACTION_TECH_DISCOVERED = "android.nfc.action.TECH_DISCOVERED";
public static final java.lang.String EXTRA_ADAPTER_STATE = "android.nfc.extra.ADAPTER_STATE";
public static final java.lang.String EXTRA_ID = "android.nfc.extra.ID";
public static final java.lang.String EXTRA_NDEF_MESSAGES = "android.nfc.extra.NDEF_MESSAGES";
public static final java.lang.String EXTRA_READER_PRESENCE_CHECK_DELAY = "presence";
public static final java.lang.String EXTRA_TAG = "android.nfc.extra.TAG";
public static final int FLAG_NDEF_PUSH_NO_CONFIRM = 1;
public static final int FLAG_READER_NFC_A = 1;
public static final int FLAG_READER_NFC_B = 2;
public static final int FLAG_READER_NFC_BARCODE = 16;
public static final int FLAG_READER_NFC_F = 4;
public static final int FLAG_READER_NFC_V = 8;
public static final int FLAG_READER_NO_PLATFORM_SOUNDS = 256;
public static final int FLAG_READER_SKIP_NDEF_CHECK = 128;
public static final int STATE_OFF = 1;
public static final int STATE_ON = 3;
public static final int STATE_TURNING_OFF = 4;
public static final int STATE_TURNING_ON = 2;
}
