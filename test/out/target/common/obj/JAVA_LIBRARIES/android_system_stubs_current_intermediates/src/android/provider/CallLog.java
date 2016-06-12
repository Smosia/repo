package android.provider;
public class CallLog
{
public static class Calls
  implements android.provider.BaseColumns
{
public  Calls() { throw new RuntimeException("Stub!"); }
public static  java.lang.String getLastOutgoingCall(android.content.Context context) { throw new RuntimeException("Stub!"); }
public static final java.lang.String CACHED_FORMATTED_NUMBER = "formatted_number";
public static final java.lang.String CACHED_LOOKUP_URI = "lookup_uri";
public static final java.lang.String CACHED_MATCHED_NUMBER = "matched_number";
public static final java.lang.String CACHED_NAME = "name";
public static final java.lang.String CACHED_NORMALIZED_NUMBER = "normalized_number";
public static final java.lang.String CACHED_NUMBER_LABEL = "numberlabel";
public static final java.lang.String CACHED_NUMBER_TYPE = "numbertype";
public static final java.lang.String CACHED_PHOTO_ID = "photo_id";
public static final java.lang.String CACHED_PHOTO_URI = "photo_uri";
public static final android.net.Uri CONTENT_FILTER_URI;
public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";
public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/calls";
public static final android.net.Uri CONTENT_URI;
public static final android.net.Uri CONTENT_URI_WITH_VOICEMAIL;
public static final java.lang.String COUNTRY_ISO = "countryiso";
public static final java.lang.String DATA_USAGE = "data_usage";
public static final java.lang.String DATE = "date";
public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";
public static final java.lang.String DURATION = "duration";
public static final java.lang.String EXTRA_CALL_TYPE_FILTER = "android.provider.extra.CALL_TYPE_FILTER";
public static final java.lang.String FEATURES = "features";
public static final int FEATURES_VIDEO = 1;
public static final java.lang.String GEOCODED_LOCATION = "geocoded_location";
public static final int INCOMING_TYPE = 1;
public static final java.lang.String IS_READ = "is_read";
public static final java.lang.String LIMIT_PARAM_KEY = "limit";
public static final int MISSED_TYPE = 3;
public static final java.lang.String NEW = "new";
public static final java.lang.String NUMBER = "number";
public static final java.lang.String NUMBER_PRESENTATION = "presentation";
public static final java.lang.String OFFSET_PARAM_KEY = "offset";
public static final int OUTGOING_TYPE = 2;
public static final java.lang.String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
public static final java.lang.String PHONE_ACCOUNT_ID = "subscription_id";
public static final int PRESENTATION_ALLOWED = 1;
public static final int PRESENTATION_PAYPHONE = 4;
public static final int PRESENTATION_RESTRICTED = 2;
public static final int PRESENTATION_UNKNOWN = 3;
public static final java.lang.String TRANSCRIPTION = "transcription";
public static final java.lang.String TYPE = "type";
public static final int VOICEMAIL_TYPE = 4;
public static final java.lang.String VOICEMAIL_URI = "voicemail_uri";
static { CONTENT_FILTER_URI = null; CONTENT_URI = null; CONTENT_URI_WITH_VOICEMAIL = null; }
}
public  CallLog() { throw new RuntimeException("Stub!"); }
public static final java.lang.String AUTHORITY = "call_log";
public static final android.net.Uri CONTENT_URI;
static { CONTENT_URI = null; }
}
