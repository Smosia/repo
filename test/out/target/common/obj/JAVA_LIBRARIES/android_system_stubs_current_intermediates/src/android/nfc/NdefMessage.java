package android.nfc;
public final class NdefMessage
  implements android.os.Parcelable
{
public  NdefMessage(byte[] data) throws android.nfc.FormatException { throw new RuntimeException("Stub!"); }
public  NdefMessage(android.nfc.NdefRecord record, android.nfc.NdefRecord... records) { throw new RuntimeException("Stub!"); }
public  NdefMessage(android.nfc.NdefRecord[] records) { throw new RuntimeException("Stub!"); }
public  android.nfc.NdefRecord[] getRecords() { throw new RuntimeException("Stub!"); }
public  int getByteArrayLength() { throw new RuntimeException("Stub!"); }
public  byte[] toByteArray() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.nfc.NdefMessage> CREATOR;
static { CREATOR = null; }
}
