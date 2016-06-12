package android.security.keystore;
public final class KeyGenParameterSpec
  implements java.security.spec.AlgorithmParameterSpec
{
public static final class Builder
{
public  Builder(java.lang.String keystoreAlias, int purposes) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setKeySize(int keySize) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setAlgorithmParameterSpec(java.security.spec.AlgorithmParameterSpec spec) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setCertificateSubject(javax.security.auth.x500.X500Principal subject) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setCertificateSerialNumber(java.math.BigInteger serialNumber) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setCertificateNotBefore(java.util.Date date) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setCertificateNotAfter(java.util.Date date) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityStart(java.util.Date startDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityForOriginationEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setKeyValidityForConsumptionEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setDigests(java.lang.String... digests) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setEncryptionPaddings(java.lang.String... paddings) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setSignaturePaddings(java.lang.String... paddings) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setBlockModes(java.lang.String... blockModes) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setRandomizedEncryptionRequired(boolean required) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationRequired(boolean required) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec.Builder setUserAuthenticationValidityDurationSeconds(int seconds) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyGenParameterSpec build() { throw new RuntimeException("Stub!"); }
}
KeyGenParameterSpec() { throw new RuntimeException("Stub!"); }
public  java.lang.String getKeystoreAlias() { throw new RuntimeException("Stub!"); }
public  int getKeySize() { throw new RuntimeException("Stub!"); }
public  java.security.spec.AlgorithmParameterSpec getAlgorithmParameterSpec() { throw new RuntimeException("Stub!"); }
public  javax.security.auth.x500.X500Principal getCertificateSubject() { throw new RuntimeException("Stub!"); }
public  java.math.BigInteger getCertificateSerialNumber() { throw new RuntimeException("Stub!"); }
public  java.util.Date getCertificateNotBefore() { throw new RuntimeException("Stub!"); }
public  java.util.Date getCertificateNotAfter() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityStart() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityForConsumptionEnd() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityForOriginationEnd() { throw new RuntimeException("Stub!"); }
public  int getPurposes() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getDigests() { throw new RuntimeException("Stub!"); }
public  boolean isDigestsSpecified() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getEncryptionPaddings() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getSignaturePaddings() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getBlockModes() { throw new RuntimeException("Stub!"); }
public  boolean isRandomizedEncryptionRequired() { throw new RuntimeException("Stub!"); }
public  boolean isUserAuthenticationRequired() { throw new RuntimeException("Stub!"); }
public  int getUserAuthenticationValidityDurationSeconds() { throw new RuntimeException("Stub!"); }
}
