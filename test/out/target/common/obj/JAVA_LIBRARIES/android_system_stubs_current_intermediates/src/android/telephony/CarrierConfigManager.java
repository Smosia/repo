package android.telephony;
public class CarrierConfigManager
{
CarrierConfigManager() { throw new RuntimeException("Stub!"); }
public  android.os.PersistableBundle getConfigForSubId(int subId) { throw new RuntimeException("Stub!"); }
public  android.os.PersistableBundle getConfig() { throw new RuntimeException("Stub!"); }
public  void notifyConfigChangedForSubId(int subId) { throw new RuntimeException("Stub!"); }
public  void updateConfigForPhoneId(int phoneId, java.lang.String simState) { throw new RuntimeException("Stub!"); }
public static  android.os.PersistableBundle getDefaultConfig() { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_CARRIER_CONFIG_CHANGED = "android.telephony.action.CARRIER_CONFIG_CHANGED";
public static final java.lang.String KEY_ADDITIONAL_CALL_SETTING_BOOL = "additional_call_setting_bool";
public static final java.lang.String KEY_ALLOW_EMERGENCY_NUMBERS_IN_CALL_LOG_BOOL = "allow_emergency_numbers_in_call_log_bool";
public static final java.lang.String KEY_ALLOW_LOCAL_DTMF_TONES_BOOL = "allow_local_dtmf_tones_bool";
public static final java.lang.String KEY_APN_EXPAND_BOOL = "apn_expand_bool";
public static final java.lang.String KEY_AUTO_RETRY_ENABLED_BOOL = "auto_retry_enabled_bool";
public static final java.lang.String KEY_CARRIER_ALLOW_TURNOFF_IMS_BOOL = "carrier_allow_turnoff_ims_bool";
public static final java.lang.String KEY_CARRIER_SETTINGS_ENABLE_BOOL = "carrier_settings_enable_bool";
public static final java.lang.String KEY_CARRIER_VOLTE_AVAILABLE_BOOL = "carrier_volte_available_bool";
public static final java.lang.String KEY_CARRIER_VOLTE_PROVISIONING_REQUIRED_BOOL = "carrier_volte_provisioning_required_bool";
public static final java.lang.String KEY_CARRIER_VOLTE_TTY_SUPPORTED_BOOL = "carrier_volte_tty_supported_bool";
public static final java.lang.String KEY_CARRIER_VT_AVAILABLE_BOOL = "carrier_vt_available_bool";
public static final java.lang.String KEY_CARRIER_VVM_PACKAGE_NAME_STRING = "carrier_vvm_package_name_string";
public static final java.lang.String KEY_CARRIER_WFC_IMS_AVAILABLE_BOOL = "carrier_wfc_ims_available_bool";
public static final java.lang.String KEY_CDMA_NONROAMING_NETWORKS_STRING_ARRAY = "cdma_nonroaming_networks_string_array";
public static final java.lang.String KEY_CDMA_ROAMING_NETWORKS_STRING_ARRAY = "cdma_roaming_networks_string_array";
public static final java.lang.String KEY_CSP_ENABLED_BOOL = "csp_enabled_bool";
public static final java.lang.String KEY_DEFAULT_SIM_CALL_MANAGER_STRING = "default_sim_call_manager_string";
public static final java.lang.String KEY_DISABLE_CDMA_ACTIVATION_CODE_BOOL = "disable_cdma_activation_code_bool";
public static final java.lang.String KEY_DTMF_TYPE_ENABLED_BOOL = "dtmf_type_enabled_bool";
public static final java.lang.String KEY_ENABLE_DIALER_KEY_VIBRATION_BOOL = "enable_dialer_key_vibration_bool";
public static final java.lang.String KEY_FORCE_HOME_NETWORK_BOOL = "force_home_network_bool";
public static final java.lang.String KEY_GSM_NONROAMING_NETWORKS_STRING_ARRAY = "gsm_nonroaming_networks_string_array";
public static final java.lang.String KEY_GSM_ROAMING_NETWORKS_STRING_ARRAY = "gsm_roaming_networks_string_array";
public static final java.lang.String KEY_HAS_IN_CALL_NOISE_SUPPRESSION_BOOL = "has_in_call_noise_suppression_bool";
public static final java.lang.String KEY_HIDE_CARRIER_NETWORK_SETTINGS_BOOL = "hide_carrier_network_settings_bool";
public static final java.lang.String KEY_HIDE_SIM_LOCK_SETTINGS_BOOL = "hide_sim_lock_settings_bool";
public static final java.lang.String KEY_IGNORE_SIM_NETWORK_LOCKED_EVENTS_BOOL = "ignore_sim_network_locked_events_bool";
public static final java.lang.String KEY_MMS_ALIAS_ENABLED_BOOL = "aliasEnabled";
public static final java.lang.String KEY_MMS_ALIAS_MAX_CHARS_INT = "aliasMaxChars";
public static final java.lang.String KEY_MMS_ALIAS_MIN_CHARS_INT = "aliasMinChars";
public static final java.lang.String KEY_MMS_ALLOW_ATTACH_AUDIO_BOOL = "allowAttachAudio";
public static final java.lang.String KEY_MMS_APPEND_TRANSACTION_ID_BOOL = "enabledTransID";
public static final java.lang.String KEY_MMS_EMAIL_GATEWAY_NUMBER_STRING = "emailGatewayNumber";
public static final java.lang.String KEY_MMS_GROUP_MMS_ENABLED_BOOL = "enableGroupMms";
public static final java.lang.String KEY_MMS_HTTP_PARAMS_STRING = "httpParams";
public static final java.lang.String KEY_MMS_HTTP_SOCKET_TIMEOUT_INT = "httpSocketTimeout";
public static final java.lang.String KEY_MMS_MAX_IMAGE_HEIGHT_INT = "maxImageHeight";
public static final java.lang.String KEY_MMS_MAX_IMAGE_WIDTH_INT = "maxImageWidth";
public static final java.lang.String KEY_MMS_MAX_MESSAGE_SIZE_INT = "maxMessageSize";
public static final java.lang.String KEY_MMS_MESSAGE_TEXT_MAX_SIZE_INT = "maxMessageTextSize";
public static final java.lang.String KEY_MMS_MMS_DELIVERY_REPORT_ENABLED_BOOL = "enableMMSDeliveryReports";
public static final java.lang.String KEY_MMS_MMS_ENABLED_BOOL = "enabledMMS";
public static final java.lang.String KEY_MMS_MMS_READ_REPORT_ENABLED_BOOL = "enableMMSReadReports";
public static final java.lang.String KEY_MMS_MULTIPART_SMS_ENABLED_BOOL = "enableMultipartSMS";
public static final java.lang.String KEY_MMS_NAI_SUFFIX_STRING = "naiSuffix";
public static final java.lang.String KEY_MMS_NOTIFY_WAP_MMSC_ENABLED_BOOL = "enabledNotifyWapMMSC";
public static final java.lang.String KEY_MMS_RECIPIENT_LIMIT_INT = "recipientLimit";
public static final java.lang.String KEY_MMS_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES_BOOL = "sendMultipartSmsAsSeparateMessages";
public static final java.lang.String KEY_MMS_SHOW_CELL_BROADCAST_APP_LINKS_BOOL = "config_cellBroadcastAppLinks";
public static final java.lang.String KEY_MMS_SMS_DELIVERY_REPORT_ENABLED_BOOL = "enableSMSDeliveryReports";
public static final java.lang.String KEY_MMS_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD_INT = "smsToMmsTextLengthThreshold";
public static final java.lang.String KEY_MMS_SMS_TO_MMS_TEXT_THRESHOLD_INT = "smsToMmsTextThreshold";
public static final java.lang.String KEY_MMS_SUBJECT_MAX_LENGTH_INT = "maxSubjectLength";
public static final java.lang.String KEY_MMS_SUPPORT_HTTP_CHARSET_HEADER_BOOL = "supportHttpCharsetHeader";
public static final java.lang.String KEY_MMS_SUPPORT_MMS_CONTENT_DISPOSITION_BOOL = "supportMmsContentDisposition";
public static final java.lang.String KEY_MMS_UA_PROF_TAG_NAME_STRING = "uaProfTagName";
public static final java.lang.String KEY_MMS_UA_PROF_URL_STRING = "uaProfUrl";
public static final java.lang.String KEY_MMS_USER_AGENT_STRING = "userAgent";
public static final java.lang.String KEY_OPERATOR_SELECTION_EXPAND_BOOL = "operator_selection_expand_bool";
public static final java.lang.String KEY_PREFER_2G_BOOL = "prefer_2g_bool";
public static final java.lang.String KEY_SHOW_APN_SETTING_CDMA_BOOL = "show_apn_setting_cdma_bool";
public static final java.lang.String KEY_SHOW_CDMA_CHOICES_BOOL = "show_cdma_choices_bool";
public static final java.lang.String KEY_SHOW_ONSCREEN_DIAL_BUTTON_BOOL = "show_onscreen_dial_button_bool";
public static final java.lang.String KEY_SIM_NETWORK_UNLOCK_ALLOW_DISMISS_BOOL = "sim_network_unlock_allow_dismiss_bool";
public static final java.lang.String KEY_SUPPORT_PAUSE_IMS_VIDEO_CALLS_BOOL = "support_pause_ims_video_calls_bool";
public static final java.lang.String KEY_SUPPORT_SWAP_AFTER_MERGE_BOOL = "support_swap_after_merge_bool";
public static final java.lang.String KEY_USE_HFA_FOR_PROVISIONING_BOOL = "use_hfa_for_provisioning_bool";
public static final java.lang.String KEY_USE_OTASP_FOR_PROVISIONING_BOOL = "use_otasp_for_provisioning_bool";
public static final java.lang.String KEY_VOICEMAIL_NOTIFICATION_PERSISTENT_BOOL = "voicemail_notification_persistent_bool";
public static final java.lang.String KEY_VOICE_PRIVACY_DISABLE_UI_BOOL = "voice_privacy_disable_ui_bool";
public static final java.lang.String KEY_VOLTE_REPLACEMENT_RAT_INT = "volte_replacement_rat_int";
public static final java.lang.String KEY_VVM_DESTINATION_NUMBER_STRING = "vvm_destination_number_string";
public static final java.lang.String KEY_VVM_PORT_NUMBER_INT = "vvm_port_number_int";
public static final java.lang.String KEY_VVM_TYPE_STRING = "vvm_type_string";
public static final java.lang.String KEY_WORLD_PHONE_BOOL = "world_phone_bool";
}
