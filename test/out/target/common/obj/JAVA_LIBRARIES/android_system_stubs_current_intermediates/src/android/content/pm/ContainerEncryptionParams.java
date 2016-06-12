package android.content.pm;
@java.lang.Deprecated()
public class ContainerEncryptionParams
  implements android.os.Parcelable
{
public  ContainerEncryptionParams(java.lang.String encryptionAlgorithm, java.security.spec.AlgorithmParameterSpec encryptionSpec, javax.crypto.SecretKey encryptionKey) throws java.security.InvalidAlgorithmParameterException { throw new RuntimeException("Stub!"); }
public  ContainerEncryptionParams(java.lang.String encryptionAlgorithm, java.security.spec.AlgorithmParameterSpec encryptionSpec, javax.crypto.SecretKey encryptionKey, java.lang.String macAlgorithm, java.security.spec.AlgorithmParameterSpec macSpec, javax.crypto.SecretKey macKey, byte[] macTag, long authenticatedDataStart, long encryptedDataStart, long dataEnd) throws java.security.InvalidAlgorithmParameterException { throw new RuntimeException("Stub!"); }
public  java.lang.String getEncryptionAlgorithm() { throw new RuntimeException("Stub!"); }
public  java.security.spec.AlgorithmParameterSpec getEncryptionSpec() { throw new RuntimeException("Stub!"); }
public  javax.crypto.SecretKey getEncryptionKey() { throw new RuntimeException("Stub!"); }
public  java.lang.String getMacAlgorithm() { throw new RuntimeException("Stub!"); }
public  java.security.spec.AlgorithmParameterSpec getMacSpec() { throw new RuntimeException("Stub!"); }
public  javax.crypto.SecretKey getMacKey() { throw new RuntimeException("Stub!"); }
public  byte[] getMacTag() { throw new RuntimeException("Stub!"); }
public  long getAuthenticatedDataStart() { throw new RuntimeException("Stub!"); }
public  long getEncryptedDataStart() { throw new RuntimeException("Stub!"); }
public  long getDataEnd() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.ContainerEncryptionParams> CREATOR;
protected static final java.lang.String TAG = "ContainerEncryptionParams";
static { CREATOR = null; }
}
