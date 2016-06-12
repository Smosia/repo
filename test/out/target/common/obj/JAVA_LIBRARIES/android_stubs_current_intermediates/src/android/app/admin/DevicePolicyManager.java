package android.app.admin;
public class DevicePolicyManager
{
DevicePolicyManager() { throw new RuntimeException("Stub!"); }
public  boolean isAdminActive(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.content.ComponentName> getActiveAdmins() { throw new RuntimeException("Stub!"); }
public  void removeActiveAdmin(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean hasGrantedPolicy(android.content.ComponentName admin, int usesPolicy) { throw new RuntimeException("Stub!"); }
public  void setPasswordQuality(android.content.ComponentName admin, int quality) { throw new RuntimeException("Stub!"); }
public  int getPasswordQuality(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumLength(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumLength(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumUpperCase(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumUpperCase(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumLowerCase(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumLowerCase(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumLetters(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumLetters(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumNumeric(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumNumeric(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumSymbols(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumSymbols(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordMinimumNonLetter(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  int getPasswordMinimumNonLetter(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setPasswordHistoryLength(android.content.ComponentName admin, int length) { throw new RuntimeException("Stub!"); }
public  void setPasswordExpirationTimeout(android.content.ComponentName admin, long timeout) { throw new RuntimeException("Stub!"); }
public  long getPasswordExpirationTimeout(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  long getPasswordExpiration(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  int getPasswordHistoryLength(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  int getPasswordMaximumLength(int quality) { throw new RuntimeException("Stub!"); }
public  boolean isActivePasswordSufficient() { throw new RuntimeException("Stub!"); }
public  int getCurrentFailedPasswordAttempts() { throw new RuntimeException("Stub!"); }
public  void setMaximumFailedPasswordsForWipe(android.content.ComponentName admin, int num) { throw new RuntimeException("Stub!"); }
public  int getMaximumFailedPasswordsForWipe(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean resetPassword(java.lang.String password, int flags) { throw new RuntimeException("Stub!"); }
public  void setMaximumTimeToLock(android.content.ComponentName admin, long timeMs) { throw new RuntimeException("Stub!"); }
public  long getMaximumTimeToLock(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void lockNow() { throw new RuntimeException("Stub!"); }
public  void wipeData(int flags) { throw new RuntimeException("Stub!"); }
public  void setRecommendedGlobalProxy(android.content.ComponentName admin, android.net.ProxyInfo proxyInfo) { throw new RuntimeException("Stub!"); }
public  int setStorageEncryption(android.content.ComponentName admin, boolean encrypt) { throw new RuntimeException("Stub!"); }
public  boolean getStorageEncryption(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  int getStorageEncryptionStatus() { throw new RuntimeException("Stub!"); }
public  boolean installCaCert(android.content.ComponentName admin, byte[] certBuffer) { throw new RuntimeException("Stub!"); }
public  void uninstallCaCert(android.content.ComponentName admin, byte[] certBuffer) { throw new RuntimeException("Stub!"); }
public  java.util.List<byte[]> getInstalledCaCerts(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void uninstallAllUserCaCerts(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean hasCaCertInstalled(android.content.ComponentName admin, byte[] certBuffer) { throw new RuntimeException("Stub!"); }
public  boolean installKeyPair(android.content.ComponentName admin, java.security.PrivateKey privKey, java.security.cert.Certificate cert, java.lang.String alias) { throw new RuntimeException("Stub!"); }
public  void setCertInstallerPackage(android.content.ComponentName admin, java.lang.String installerPackage) throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  java.lang.String getCertInstallerPackage(android.content.ComponentName admin) throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  void setCameraDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  boolean getCameraDisabled(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setScreenCaptureDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  boolean getScreenCaptureDisabled(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setAutoTimeRequired(android.content.ComponentName admin, boolean required) { throw new RuntimeException("Stub!"); }
public  boolean getAutoTimeRequired() { throw new RuntimeException("Stub!"); }
public  void setKeyguardDisabledFeatures(android.content.ComponentName admin, int which) { throw new RuntimeException("Stub!"); }
public  int getKeyguardDisabledFeatures(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean isDeviceOwnerApp(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void clearDeviceOwnerApp(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void setProfileEnabled(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setProfileName(android.content.ComponentName admin, java.lang.String profileName) { throw new RuntimeException("Stub!"); }
public  boolean isProfileOwnerApp(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void addPersistentPreferredActivity(android.content.ComponentName admin, android.content.IntentFilter filter, android.content.ComponentName activity) { throw new RuntimeException("Stub!"); }
public  void clearPackagePersistentPreferredActivities(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void setApplicationRestrictions(android.content.ComponentName admin, java.lang.String packageName, android.os.Bundle settings) { throw new RuntimeException("Stub!"); }
public  void setTrustAgentConfiguration(android.content.ComponentName admin, android.content.ComponentName target, android.os.PersistableBundle configuration) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName admin, android.content.ComponentName agent) { throw new RuntimeException("Stub!"); }
public  void setCrossProfileCallerIdDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  boolean getCrossProfileCallerIdDisabled(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setBluetoothContactSharingDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  boolean getBluetoothContactSharingDisabled(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void addCrossProfileIntentFilter(android.content.ComponentName admin, android.content.IntentFilter filter, int flags) { throw new RuntimeException("Stub!"); }
public  void clearCrossProfileIntentFilters(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean setPermittedAccessibilityServices(android.content.ComponentName admin, java.util.List<java.lang.String> packageNames) { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.String> getPermittedAccessibilityServices(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean setPermittedInputMethods(android.content.ComponentName admin, java.util.List<java.lang.String> packageNames) { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.String> getPermittedInputMethods(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.os.UserHandle createUser(android.content.ComponentName admin, java.lang.String name) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.os.UserHandle createAndInitializeUser(android.content.ComponentName admin, java.lang.String name, java.lang.String ownerName, android.content.ComponentName profileOwnerComponent, android.os.Bundle adminExtras) { throw new RuntimeException("Stub!"); }
public  boolean removeUser(android.content.ComponentName admin, android.os.UserHandle userHandle) { throw new RuntimeException("Stub!"); }
public  boolean switchUser(android.content.ComponentName admin, android.os.UserHandle userHandle) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getApplicationRestrictions(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void addUserRestriction(android.content.ComponentName admin, java.lang.String key) { throw new RuntimeException("Stub!"); }
public  void clearUserRestriction(android.content.ComponentName admin, java.lang.String key) { throw new RuntimeException("Stub!"); }
public  boolean setApplicationHidden(android.content.ComponentName admin, java.lang.String packageName, boolean hidden) { throw new RuntimeException("Stub!"); }
public  boolean isApplicationHidden(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  void enableSystemApp(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  int enableSystemApp(android.content.ComponentName admin, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void setAccountManagementDisabled(android.content.ComponentName admin, java.lang.String accountType, boolean disabled) { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getAccountTypesWithManagementDisabled() { throw new RuntimeException("Stub!"); }
public  void setLockTaskPackages(android.content.ComponentName admin, java.lang.String[] packages) throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  boolean isLockTaskPermitted(java.lang.String pkg) { throw new RuntimeException("Stub!"); }
public  void setGlobalSetting(android.content.ComponentName admin, java.lang.String setting, java.lang.String value) { throw new RuntimeException("Stub!"); }
public  void setSecureSetting(android.content.ComponentName admin, java.lang.String setting, java.lang.String value) { throw new RuntimeException("Stub!"); }
public  void setRestrictionsProvider(android.content.ComponentName admin, android.content.ComponentName provider) { throw new RuntimeException("Stub!"); }
public  void setMasterVolumeMuted(android.content.ComponentName admin, boolean on) { throw new RuntimeException("Stub!"); }
public  boolean isMasterVolumeMuted(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setUninstallBlocked(android.content.ComponentName admin, java.lang.String packageName, boolean uninstallBlocked) { throw new RuntimeException("Stub!"); }
public  boolean isUninstallBlocked(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  boolean addCrossProfileWidgetProvider(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  boolean removeCrossProfileWidgetProvider(android.content.ComponentName admin, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.String> getCrossProfileWidgetProviders(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  void setUserIcon(android.content.ComponentName admin, android.graphics.Bitmap icon) { throw new RuntimeException("Stub!"); }
public  void setSystemUpdatePolicy(android.content.ComponentName admin, android.app.admin.SystemUpdatePolicy policy) { throw new RuntimeException("Stub!"); }
public  android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() { throw new RuntimeException("Stub!"); }
public  boolean setKeyguardDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  boolean setStatusBarDisabled(android.content.ComponentName admin, boolean disabled) { throw new RuntimeException("Stub!"); }
public  void setPermissionPolicy(android.content.ComponentName admin, int policy) { throw new RuntimeException("Stub!"); }
public  int getPermissionPolicy(android.content.ComponentName admin) { throw new RuntimeException("Stub!"); }
public  boolean setPermissionGrantState(android.content.ComponentName admin, java.lang.String packageName, java.lang.String permission, int grantState) { throw new RuntimeException("Stub!"); }
public  int getPermissionGrantState(android.content.ComponentName admin, java.lang.String packageName, java.lang.String permission) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
public static final java.lang.String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
public static final java.lang.String ACTION_MANAGED_PROFILE_PROVISIONED = "android.app.action.MANAGED_PROFILE_PROVISIONED";
public static final java.lang.String ACTION_PROVISION_MANAGED_DEVICE = "android.app.action.PROVISION_MANAGED_DEVICE";
public static final java.lang.String ACTION_PROVISION_MANAGED_PROFILE = "android.app.action.PROVISION_MANAGED_PROFILE";
public static final java.lang.String ACTION_SET_NEW_PASSWORD = "android.app.action.SET_NEW_PASSWORD";
public static final java.lang.String ACTION_START_ENCRYPTION = "android.app.action.START_ENCRYPTION";
public static final java.lang.String ACTION_SYSTEM_UPDATE_POLICY_CHANGED = "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED";
public static final int ENCRYPTION_STATUS_ACTIVATING = 2;
public static final int ENCRYPTION_STATUS_ACTIVE = 3;
public static final int ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY = 4;
public static final int ENCRYPTION_STATUS_INACTIVE = 1;
public static final int ENCRYPTION_STATUS_UNSUPPORTED = 0;
public static final java.lang.String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
public static final java.lang.String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
public static final java.lang.String EXTRA_PROVISIONING_ACCOUNT_TO_MIGRATE = "android.app.extra.PROVISIONING_ACCOUNT_TO_MIGRATE";
public static final java.lang.String EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE = "android.app.extra.PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";
@java.lang.Deprecated()
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
public static final java.lang.String EXTRA_PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
public static final java.lang.String EXTRA_PROVISIONING_EMAIL_ADDRESS = "android.app.extra.PROVISIONING_EMAIL_ADDRESS";
public static final java.lang.String EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED = "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED";
public static final java.lang.String EXTRA_PROVISIONING_LOCALE = "android.app.extra.PROVISIONING_LOCALE";
public static final java.lang.String EXTRA_PROVISIONING_LOCAL_TIME = "android.app.extra.PROVISIONING_LOCAL_TIME";
public static final java.lang.String EXTRA_PROVISIONING_SKIP_ENCRYPTION = "android.app.extra.PROVISIONING_SKIP_ENCRYPTION";
public static final java.lang.String EXTRA_PROVISIONING_TIME_ZONE = "android.app.extra.PROVISIONING_TIME_ZONE";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_HIDDEN = "android.app.extra.PROVISIONING_WIFI_HIDDEN";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_PAC_URL = "android.app.extra.PROVISIONING_WIFI_PAC_URL";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_PASSWORD = "android.app.extra.PROVISIONING_WIFI_PASSWORD";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_BYPASS = "android.app.extra.PROVISIONING_WIFI_PROXY_BYPASS";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_HOST = "android.app.extra.PROVISIONING_WIFI_PROXY_HOST";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_PROXY_PORT = "android.app.extra.PROVISIONING_WIFI_PROXY_PORT";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_SECURITY_TYPE = "android.app.extra.PROVISIONING_WIFI_SECURITY_TYPE";
public static final java.lang.String EXTRA_PROVISIONING_WIFI_SSID = "android.app.extra.PROVISIONING_WIFI_SSID";
public static final int FLAG_MANAGED_CAN_ACCESS_PARENT = 2;
public static final int FLAG_PARENT_CAN_ACCESS_MANAGED = 1;
public static final int KEYGUARD_DISABLE_FEATURES_ALL = 2147483647;
public static final int KEYGUARD_DISABLE_FEATURES_NONE = 0;
public static final int KEYGUARD_DISABLE_FINGERPRINT = 32;
public static final int KEYGUARD_DISABLE_SECURE_CAMERA = 2;
public static final int KEYGUARD_DISABLE_SECURE_NOTIFICATIONS = 4;
public static final int KEYGUARD_DISABLE_TRUST_AGENTS = 16;
public static final int KEYGUARD_DISABLE_UNREDACTED_NOTIFICATIONS = 8;
public static final int KEYGUARD_DISABLE_WIDGETS_ALL = 1;
public static final java.lang.String MIME_TYPE_PROVISIONING_NFC = "application/com.android.managedprovisioning";
public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
public static final int PASSWORD_QUALITY_COMPLEX = 393216;
public static final int PASSWORD_QUALITY_NUMERIC = 131072;
public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
public static final int PASSWORD_QUALITY_SOMETHING = 65536;
public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
public static final int PERMISSION_GRANT_STATE_DEFAULT = 0;
public static final int PERMISSION_GRANT_STATE_DENIED = 2;
public static final int PERMISSION_GRANT_STATE_GRANTED = 1;
public static final int PERMISSION_POLICY_AUTO_DENY = 2;
public static final int PERMISSION_POLICY_AUTO_GRANT = 1;
public static final int PERMISSION_POLICY_PROMPT = 0;
public static final int RESET_PASSWORD_DO_NOT_ASK_CREDENTIALS_ON_BOOT = 2;
public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;
public static final int WIPE_EXTERNAL_STORAGE = 1;
public static final int WIPE_RESET_PROTECTION_DATA = 2;
}
