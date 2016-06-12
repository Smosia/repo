package android.provider;
public abstract class SearchIndexablesProvider
  extends android.content.ContentProvider
{
public  SearchIndexablesProvider() { throw new RuntimeException("Stub!"); }
public  void attachInfo(android.content.Context context, android.content.pm.ProviderInfo info) { throw new RuntimeException("Stub!"); }
public  android.database.Cursor query(android.net.Uri uri, java.lang.String[] projection, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String sortOrder) { throw new RuntimeException("Stub!"); }
public abstract  android.database.Cursor queryXmlResources(java.lang.String[] projection);
public abstract  android.database.Cursor queryRawData(java.lang.String[] projection);
public abstract  android.database.Cursor queryNonIndexableKeys(java.lang.String[] projection);
public  java.lang.String getType(android.net.Uri uri) { throw new RuntimeException("Stub!"); }
public final  android.net.Uri insert(android.net.Uri uri, android.content.ContentValues values) { throw new RuntimeException("Stub!"); }
public final  int delete(android.net.Uri uri, java.lang.String selection, java.lang.String[] selectionArgs) { throw new RuntimeException("Stub!"); }
public final  int update(android.net.Uri uri, android.content.ContentValues values, java.lang.String selection, java.lang.String[] selectionArgs) { throw new RuntimeException("Stub!"); }
}
