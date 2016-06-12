package java.util;
public final class Locale
  implements java.lang.Cloneable, java.io.Serializable
{
public static final class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setLanguage(java.lang.String language) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setLanguageTag(java.lang.String languageTag) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setRegion(java.lang.String region) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setVariant(java.lang.String variant) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setScript(java.lang.String script) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setLocale(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder addUnicodeLocaleAttribute(java.lang.String attribute) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder removeUnicodeLocaleAttribute(java.lang.String attribute) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setExtension(char key, java.lang.String value) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder clearExtensions() { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder setUnicodeLocaleKeyword(java.lang.String key, java.lang.String type) { throw new RuntimeException("Stub!"); }
public  java.util.Locale.Builder clear() { throw new RuntimeException("Stub!"); }
public  java.util.Locale build() { throw new RuntimeException("Stub!"); }
}
public  Locale(java.lang.String language) { throw new RuntimeException("Stub!"); }
public  Locale(java.lang.String language, java.lang.String country) { throw new RuntimeException("Stub!"); }
public  Locale(java.lang.String language, java.lang.String country, java.lang.String variant) { throw new RuntimeException("Stub!"); }
public static  java.util.Locale forLanguageTag(java.lang.String languageTag) { throw new RuntimeException("Stub!"); }
public  java.lang.Object clone() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object object) { throw new RuntimeException("Stub!"); }
public static  java.util.Locale[] getAvailableLocales() { throw new RuntimeException("Stub!"); }
public  java.lang.String getCountry() { throw new RuntimeException("Stub!"); }
public static  java.util.Locale getDefault() { throw new RuntimeException("Stub!"); }
public final  java.lang.String getDisplayCountry() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayCountry(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public final  java.lang.String getDisplayLanguage() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayLanguage(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public final  java.lang.String getDisplayName() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayName(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public final  java.lang.String getDisplayVariant() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayVariant(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public  java.lang.String getISO3Country() { throw new RuntimeException("Stub!"); }
public  java.lang.String getISO3Language() { throw new RuntimeException("Stub!"); }
public static  java.lang.String[] getISOCountries() { throw new RuntimeException("Stub!"); }
public static  java.lang.String[] getISOLanguages() { throw new RuntimeException("Stub!"); }
public  java.lang.String getLanguage() { throw new RuntimeException("Stub!"); }
public  java.lang.String getVariant() { throw new RuntimeException("Stub!"); }
public  java.lang.String getScript() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayScript() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDisplayScript(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public  java.lang.String toLanguageTag() { throw new RuntimeException("Stub!"); }
public  java.util.Set<java.lang.Character> getExtensionKeys() { throw new RuntimeException("Stub!"); }
public  java.lang.String getExtension(char extensionKey) { throw new RuntimeException("Stub!"); }
public  java.lang.String getUnicodeLocaleType(java.lang.String keyWord) { throw new RuntimeException("Stub!"); }
public  java.util.Set<java.lang.String> getUnicodeLocaleAttributes() { throw new RuntimeException("Stub!"); }
public  java.util.Set<java.lang.String> getUnicodeLocaleKeys() { throw new RuntimeException("Stub!"); }
public synchronized  int hashCode() { throw new RuntimeException("Stub!"); }
public static synchronized  void setDefault(java.util.Locale locale) { throw new RuntimeException("Stub!"); }
public final  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final java.util.Locale CANADA;
public static final java.util.Locale CANADA_FRENCH;
public static final java.util.Locale CHINA;
public static final java.util.Locale CHINESE;
public static final java.util.Locale ENGLISH;
public static final java.util.Locale FRANCE;
public static final java.util.Locale FRENCH;
public static final java.util.Locale GERMAN;
public static final java.util.Locale GERMANY;
public static final java.util.Locale ITALIAN;
public static final java.util.Locale ITALY;
public static final java.util.Locale JAPAN;
public static final java.util.Locale JAPANESE;
public static final java.util.Locale KOREA;
public static final java.util.Locale KOREAN;
public static final java.util.Locale PRC;
public static final char PRIVATE_USE_EXTENSION = 120;
public static final java.util.Locale ROOT;
public static final java.util.Locale SIMPLIFIED_CHINESE;
public static final java.util.Locale TAIWAN;
public static final java.util.Locale TRADITIONAL_CHINESE;
public static final java.util.Locale UK;
public static final char UNICODE_LOCALE_EXTENSION = 117;
public static final java.util.Locale US;
static { CANADA = null; CANADA_FRENCH = null; CHINA = null; CHINESE = null; ENGLISH = null; FRANCE = null; FRENCH = null; GERMAN = null; GERMANY = null; ITALIAN = null; ITALY = null; JAPAN = null; JAPANESE = null; KOREA = null; KOREAN = null; PRC = null; ROOT = null; SIMPLIFIED_CHINESE = null; TAIWAN = null; TRADITIONAL_CHINESE = null; UK = null; US = null; }
}
