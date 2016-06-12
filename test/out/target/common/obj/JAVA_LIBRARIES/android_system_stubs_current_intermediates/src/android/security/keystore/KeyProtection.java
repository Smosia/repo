package android.security.keystore;
public final class KeyProtection
  implements java.security.KeyStore.ProtectionParameter
{
public static final class Builder
{
public  Builder(int purposes) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setKeyValidityStart(java.util.Date startDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setKeyValidityEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setKeyValidityForOriginationEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setKeyValidityForConsumptionEnd(java.util.Date endDate) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setEncryptionPaddings(java.lang.String... paddings) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setSignaturePaddings(java.lang.String... paddings) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setDigests(java.lang.String... digests) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setBlockModes(java.lang.String... blockModes) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setRandomizedEncryptionRequired(boolean required) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setUserAuthenticationRequired(boolean required) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection.Builder setUserAuthenticationValidityDurationSeconds(int seconds) { throw new RuntimeException("Stub!"); }
public  android.security.keystore.KeyProtection build() { throw new RuntimeException("Stub!"); }
}
KeyProtection() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityStart() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityForConsumptionEnd() { throw new RuntimeException("Stub!"); }
public  java.util.Date getKeyValidityForOriginationEnd() { throw new RuntimeException("Stub!"); }
public  int getPurposes() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getEncryptionPaddings() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getSignaturePaddings() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getDigests() { throw new RuntimeException("Stub!"); }
public  boolean isDigestsSpecified() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getBlockModes() { throw new RuntimeException("Stub!"); }
public  boolean isRandomizedEncryptionRequired() { throw new RuntimeException("Stub!"); }
public  boolean isUserAuthenticationRequired() { throw new RuntimeException("Stub!"); }
public  int getUserAuthenticationValidityDurationSeconds() { throw new RuntimeException("Stub!"); }
}
