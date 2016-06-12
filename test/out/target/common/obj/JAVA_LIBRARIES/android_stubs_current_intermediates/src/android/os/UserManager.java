package android.os;
public class UserManager
{
UserManager() { throw new RuntimeException("Stub!"); }
public  java.lang.String getUserName() { throw new RuntimeException("Stub!"); }
public  boolean isUserAGoat() { throw new RuntimeException("Stub!"); }
public  boolean isSystemUser() { throw new RuntimeException("Stub!"); }
public  boolean isUserRunning(android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  boolean isUserRunningOrStopping(android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getUserRestrictions() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getUserRestrictions(android.os.UserHandle userHandle) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setUserRestrictions(android.os.Bundle restrictions) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setUserRestrictions(android.os.Bundle restrictions, android.os.UserHandle userHandle) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setUserRestriction(java.lang.String key, boolean value) { throw new RuntimeException("Stub!"); }
public  boolean hasUserRestriction(java.lang.String restrictionKey) { throw new RuntimeException("Stub!"); }
public  long getSerialNumberForUser(android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  android.os.UserHandle getUserForSerialNumber(long serialNumber) { throw new RuntimeException("Stub!"); }
public  int getUserCount() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.os.UserHandle> getUserProfiles() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getApplicationRestrictions(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
@Deprecated
public  boolean setRestrictionsChallenge(java.lang.String newPin) { throw new RuntimeException("Stub!"); }
public  long getUserCreationTime(android.os.UserHandle userHandle) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ALLOW_PARENT_PROFILE_APP_LINKING = "allow_parent_profile_app_linking";
public static final java.lang.String DISALLOW_ADD_USER = "no_add_user";
public static final java.lang.String DISALLOW_ADJUST_VOLUME = "no_adjust_volume";
public static final java.lang.String DISALLOW_APPS_CONTROL = "no_control_apps";
public static final java.lang.String DISALLOW_CONFIG_BLUETOOTH = "no_config_bluetooth";
public static final java.lang.String DISALLOW_CONFIG_CELL_BROADCASTS = "no_config_cell_broadcasts";
public static final java.lang.String DISALLOW_CONFIG_CREDENTIALS = "no_config_credentials";
public static final java.lang.String DISALLOW_CONFIG_MOBILE_NETWORKS = "no_config_mobile_networks";
public static final java.lang.String DISALLOW_CONFIG_TETHERING = "no_config_tethering";
public static final java.lang.String DISALLOW_CONFIG_VPN = "no_config_vpn";
public static final java.lang.String DISALLOW_CONFIG_WIFI = "no_config_wifi";
public static final java.lang.String DISALLOW_CREATE_WINDOWS = "no_create_windows";
public static final java.lang.String DISALLOW_CROSS_PROFILE_COPY_PASTE = "no_cross_profile_copy_paste";
public static final java.lang.String DISALLOW_DEBUGGING_FEATURES = "no_debugging_features";
public static final java.lang.String DISALLOW_FACTORY_RESET = "no_factory_reset";
public static final java.lang.String DISALLOW_FUN = "no_fun";
public static final java.lang.String DISALLOW_INSTALL_APPS = "no_install_apps";
public static final java.lang.String DISALLOW_INSTALL_UNKNOWN_SOURCES = "no_install_unknown_sources";
public static final java.lang.String DISALLOW_MODIFY_ACCOUNTS = "no_modify_accounts";
public static final java.lang.String DISALLOW_MOUNT_PHYSICAL_MEDIA = "no_physical_media";
public static final java.lang.String DISALLOW_NETWORK_RESET = "no_network_reset";
public static final java.lang.String DISALLOW_OUTGOING_BEAM = "no_outgoing_beam";
public static final java.lang.String DISALLOW_OUTGOING_CALLS = "no_outgoing_calls";
public static final java.lang.String DISALLOW_REMOVE_USER = "no_remove_user";
public static final java.lang.String DISALLOW_SAFE_BOOT = "no_safe_boot";
public static final java.lang.String DISALLOW_SHARE_LOCATION = "no_share_location";
public static final java.lang.String DISALLOW_SMS = "no_sms";
public static final java.lang.String DISALLOW_UNINSTALL_APPS = "no_uninstall_apps";
public static final java.lang.String DISALLOW_UNMUTE_MICROPHONE = "no_unmute_microphone";
public static final java.lang.String DISALLOW_USB_FILE_TRANSFER = "no_usb_file_transfer";
public static final java.lang.String ENSURE_VERIFY_APPS = "ensure_verify_apps";
public static final java.lang.String KEY_RESTRICTIONS_PENDING = "restrictions_pending";
}
