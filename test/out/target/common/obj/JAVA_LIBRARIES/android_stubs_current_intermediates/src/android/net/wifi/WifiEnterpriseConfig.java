package android.net.wifi;
public class WifiEnterpriseConfig
  implements android.os.Parcelable
{
public static final class Eap
{
Eap() { throw new RuntimeException("Stub!"); }
public static final int AKA = 5;
public static final int AKA_PRIME = 6;
public static final int NONE = -1;
public static final int PEAP = 0;
public static final int PWD = 3;
public static final int SIM = 4;
public static final int TLS = 1;
public static final int TTLS = 2;
}
public static final class Phase2
{
Phase2() { throw new RuntimeException("Stub!"); }
public static final int GTC = 4;
public static final int MSCHAP = 2;
public static final int MSCHAPV2 = 3;
public static final int NONE = 0;
public static final int PAP = 1;
}
public  WifiEnterpriseConfig() { throw new RuntimeException("Stub!"); }
public  WifiEnterpriseConfig(android.net.wifi.WifiEnterpriseConfig source) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  void setEapMethod(int eapMethod) { throw new RuntimeException("Stub!"); }
public  int getEapMethod() { throw new RuntimeException("Stub!"); }
public  void setPhase2Method(int phase2Method) { throw new RuntimeException("Stub!"); }
public  int getPhase2Method() { throw new RuntimeException("Stub!"); }
public  void setIdentity(java.lang.String identity) { throw new RuntimeException("Stub!"); }
public  java.lang.String getIdentity() { throw new RuntimeException("Stub!"); }
public  void setAnonymousIdentity(java.lang.String anonymousIdentity) { throw new RuntimeException("Stub!"); }
public  java.lang.String getAnonymousIdentity() { throw new RuntimeException("Stub!"); }
public  void setPassword(java.lang.String password) { throw new RuntimeException("Stub!"); }
public  java.lang.String getPassword() { throw new RuntimeException("Stub!"); }
public  void setCaCertificate(java.security.cert.X509Certificate cert) { throw new RuntimeException("Stub!"); }
public  java.security.cert.X509Certificate getCaCertificate() { throw new RuntimeException("Stub!"); }
public  void setClientKeyEntry(java.security.PrivateKey privateKey, java.security.cert.X509Certificate clientCertificate) { throw new RuntimeException("Stub!"); }
public  java.security.cert.X509Certificate getClientCertificate() { throw new RuntimeException("Stub!"); }
@Deprecated
public  void setSubjectMatch(java.lang.String subjectMatch) { throw new RuntimeException("Stub!"); }
@Deprecated
public  java.lang.String getSubjectMatch() { throw new RuntimeException("Stub!"); }
public  void setAltSubjectMatch(java.lang.String altSubjectMatch) { throw new RuntimeException("Stub!"); }
public  java.lang.String getAltSubjectMatch() { throw new RuntimeException("Stub!"); }
public  void setDomainSuffixMatch(java.lang.String domain) { throw new RuntimeException("Stub!"); }
public  java.lang.String getDomainSuffixMatch() { throw new RuntimeException("Stub!"); }
public  void setRealm(java.lang.String realm) { throw new RuntimeException("Stub!"); }
public  java.lang.String getRealm() { throw new RuntimeException("Stub!"); }
public  void setPlmn(java.lang.String plmn) { throw new RuntimeException("Stub!"); }
public  java.lang.String getPlmn() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.wifi.WifiEnterpriseConfig> CREATOR;
static { CREATOR = null; }
}
