package android.media.tv;
public final class TvContract
{
public static interface BaseTvColumns
  extends android.provider.BaseColumns
{
public static final java.lang.String COLUMN_PACKAGE_NAME = "package_name";
}
public static final class Channels
  implements android.media.tv.TvContract.BaseTvColumns
{
public static final class Logo
{
Logo() { throw new RuntimeException("Stub!"); }
public static final java.lang.String CONTENT_DIRECTORY = "logo";
}
Channels() { throw new RuntimeException("Stub!"); }
public static final  java.lang.String getVideoResolution(java.lang.String videoFormat) { throw new RuntimeException("Stub!"); }
public static final java.lang.String COLUMN_APP_LINK_COLOR = "app_link_color";
public static final java.lang.String COLUMN_APP_LINK_ICON_URI = "app_link_icon_uri";
public static final java.lang.String COLUMN_APP_LINK_INTENT_URI = "app_link_intent_uri";
public static final java.lang.String COLUMN_APP_LINK_POSTER_ART_URI = "app_link_poster_art_uri";
public static final java.lang.String COLUMN_APP_LINK_TEXT = "app_link_text";
public static final java.lang.String COLUMN_DESCRIPTION = "description";
public static final java.lang.String COLUMN_DISPLAY_NAME = "display_name";
public static final java.lang.String COLUMN_DISPLAY_NUMBER = "display_number";
public static final java.lang.String COLUMN_INPUT_ID = "input_id";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
public static final java.lang.String COLUMN_NETWORK_AFFILIATION = "network_affiliation";
public static final java.lang.String COLUMN_ORIGINAL_NETWORK_ID = "original_network_id";
public static final java.lang.String COLUMN_SEARCHABLE = "searchable";
public static final java.lang.String COLUMN_SERVICE_ID = "service_id";
public static final java.lang.String COLUMN_SERVICE_TYPE = "service_type";
public static final java.lang.String COLUMN_TRANSPORT_STREAM_ID = "transport_stream_id";
public static final java.lang.String COLUMN_TYPE = "type";
public static final java.lang.String COLUMN_VERSION_NUMBER = "version_number";
public static final java.lang.String COLUMN_VIDEO_FORMAT = "video_format";
public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/channel";
public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/channel";
public static final android.net.Uri CONTENT_URI;
public static final java.lang.String SERVICE_TYPE_AUDIO = "SERVICE_TYPE_AUDIO";
public static final java.lang.String SERVICE_TYPE_AUDIO_VIDEO = "SERVICE_TYPE_AUDIO_VIDEO";
public static final java.lang.String SERVICE_TYPE_OTHER = "SERVICE_TYPE_OTHER";
public static final java.lang.String TYPE_1SEG = "TYPE_1SEG";
public static final java.lang.String TYPE_ATSC_C = "TYPE_ATSC_C";
public static final java.lang.String TYPE_ATSC_M_H = "TYPE_ATSC_M_H";
public static final java.lang.String TYPE_ATSC_T = "TYPE_ATSC_T";
public static final java.lang.String TYPE_CMMB = "TYPE_CMMB";
public static final java.lang.String TYPE_DTMB = "TYPE_DTMB";
public static final java.lang.String TYPE_DVB_C = "TYPE_DVB_C";
public static final java.lang.String TYPE_DVB_C2 = "TYPE_DVB_C2";
public static final java.lang.String TYPE_DVB_H = "TYPE_DVB_H";
public static final java.lang.String TYPE_DVB_S = "TYPE_DVB_S";
public static final java.lang.String TYPE_DVB_S2 = "TYPE_DVB_S2";
public static final java.lang.String TYPE_DVB_SH = "TYPE_DVB_SH";
public static final java.lang.String TYPE_DVB_T = "TYPE_DVB_T";
public static final java.lang.String TYPE_DVB_T2 = "TYPE_DVB_T2";
public static final java.lang.String TYPE_ISDB_C = "TYPE_ISDB_C";
public static final java.lang.String TYPE_ISDB_S = "TYPE_ISDB_S";
public static final java.lang.String TYPE_ISDB_T = "TYPE_ISDB_T";
public static final java.lang.String TYPE_ISDB_TB = "TYPE_ISDB_TB";
public static final java.lang.String TYPE_NTSC = "TYPE_NTSC";
public static final java.lang.String TYPE_OTHER = "TYPE_OTHER";
public static final java.lang.String TYPE_PAL = "TYPE_PAL";
public static final java.lang.String TYPE_SECAM = "TYPE_SECAM";
public static final java.lang.String TYPE_S_DMB = "TYPE_S_DMB";
public static final java.lang.String TYPE_T_DMB = "TYPE_T_DMB";
public static final java.lang.String VIDEO_FORMAT_1080I = "VIDEO_FORMAT_1080I";
public static final java.lang.String VIDEO_FORMAT_1080P = "VIDEO_FORMAT_1080P";
public static final java.lang.String VIDEO_FORMAT_2160P = "VIDEO_FORMAT_2160P";
public static final java.lang.String VIDEO_FORMAT_240P = "VIDEO_FORMAT_240P";
public static final java.lang.String VIDEO_FORMAT_360P = "VIDEO_FORMAT_360P";
public static final java.lang.String VIDEO_FORMAT_4320P = "VIDEO_FORMAT_4320P";
public static final java.lang.String VIDEO_FORMAT_480I = "VIDEO_FORMAT_480I";
public static final java.lang.String VIDEO_FORMAT_480P = "VIDEO_FORMAT_480P";
public static final java.lang.String VIDEO_FORMAT_576I = "VIDEO_FORMAT_576I";
public static final java.lang.String VIDEO_FORMAT_576P = "VIDEO_FORMAT_576P";
public static final java.lang.String VIDEO_FORMAT_720P = "VIDEO_FORMAT_720P";
public static final java.lang.String VIDEO_RESOLUTION_ED = "VIDEO_RESOLUTION_ED";
public static final java.lang.String VIDEO_RESOLUTION_FHD = "VIDEO_RESOLUTION_FHD";
public static final java.lang.String VIDEO_RESOLUTION_HD = "VIDEO_RESOLUTION_HD";
public static final java.lang.String VIDEO_RESOLUTION_SD = "VIDEO_RESOLUTION_SD";
public static final java.lang.String VIDEO_RESOLUTION_UHD = "VIDEO_RESOLUTION_UHD";
static { CONTENT_URI = null; }
}
public static final class Programs
  implements android.media.tv.TvContract.BaseTvColumns
{
public static final class Genres
{
Genres() { throw new RuntimeException("Stub!"); }
public static  java.lang.String encode(java.lang.String... genres) { throw new RuntimeException("Stub!"); }
public static  java.lang.String[] decode(java.lang.String genres) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ANIMAL_WILDLIFE = "ANIMAL_WILDLIFE";
public static final java.lang.String ARTS = "ARTS";
public static final java.lang.String COMEDY = "COMEDY";
public static final java.lang.String DRAMA = "DRAMA";
public static final java.lang.String EDUCATION = "EDUCATION";
public static final java.lang.String ENTERTAINMENT = "ENTERTAINMENT";
public static final java.lang.String FAMILY_KIDS = "FAMILY_KIDS";
public static final java.lang.String GAMING = "GAMING";
public static final java.lang.String LIFE_STYLE = "LIFE_STYLE";
public static final java.lang.String MOVIES = "MOVIES";
public static final java.lang.String MUSIC = "MUSIC";
public static final java.lang.String NEWS = "NEWS";
public static final java.lang.String PREMIER = "PREMIER";
public static final java.lang.String SHOPPING = "SHOPPING";
public static final java.lang.String SPORTS = "SPORTS";
public static final java.lang.String TECH_SCIENCE = "TECH_SCIENCE";
public static final java.lang.String TRAVEL = "TRAVEL";
}
Programs() { throw new RuntimeException("Stub!"); }
public static final java.lang.String COLUMN_AUDIO_LANGUAGE = "audio_language";
public static final java.lang.String COLUMN_BROADCAST_GENRE = "broadcast_genre";
public static final java.lang.String COLUMN_CANONICAL_GENRE = "canonical_genre";
public static final java.lang.String COLUMN_CHANNEL_ID = "channel_id";
public static final java.lang.String COLUMN_CONTENT_RATING = "content_rating";
public static final java.lang.String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
public static final java.lang.String COLUMN_EPISODE_NUMBER = "episode_number";
public static final java.lang.String COLUMN_EPISODE_TITLE = "episode_title";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
public static final java.lang.String COLUMN_LONG_DESCRIPTION = "long_description";
public static final java.lang.String COLUMN_POSTER_ART_URI = "poster_art_uri";
public static final java.lang.String COLUMN_SEARCHABLE = "searchable";
public static final java.lang.String COLUMN_SEASON_NUMBER = "season_number";
public static final java.lang.String COLUMN_SHORT_DESCRIPTION = "short_description";
public static final java.lang.String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
public static final java.lang.String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
public static final java.lang.String COLUMN_TITLE = "title";
public static final java.lang.String COLUMN_VERSION_NUMBER = "version_number";
public static final java.lang.String COLUMN_VIDEO_HEIGHT = "video_height";
public static final java.lang.String COLUMN_VIDEO_WIDTH = "video_width";
public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/program";
public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/program";
public static final android.net.Uri CONTENT_URI;
static { CONTENT_URI = null; }
}
TvContract() { throw new RuntimeException("Stub!"); }
public static final  java.lang.String buildInputId(android.content.ComponentName name) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildChannelUri(long channelId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildChannelUriForPassthroughInput(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildChannelLogoUri(long channelId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildChannelLogoUri(android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildChannelsUriForInput(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildProgramUri(long programId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildProgramsUriForChannel(long channelId) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildProgramsUriForChannel(android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildProgramsUriForChannel(long channelId, long startTime, long endTime) { throw new RuntimeException("Stub!"); }
public static final  android.net.Uri buildProgramsUriForChannel(android.net.Uri channelUri, long startTime, long endTime) { throw new RuntimeException("Stub!"); }
public static final java.lang.String AUTHORITY = "android.media.tv";
}
