package android.provider;
public abstract class DocumentsProvider
  extends android.content.ContentProvider
{
public  DocumentsProvider() { throw new RuntimeException("Stub!"); }
public  void attachInfo(android.content.Context context, android.content.pm.ProviderInfo info) { throw new RuntimeException("Stub!"); }
public  boolean isChildDocument(java.lang.String parentDocumentId, java.lang.String documentId) { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"unused"})
public  java.lang.String createDocument(java.lang.String parentDocumentId, java.lang.String mimeType, java.lang.String displayName) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"unused"})
public  java.lang.String renameDocument(java.lang.String documentId, java.lang.String displayName) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"unused"})
public  void deleteDocument(java.lang.String documentId) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public abstract  android.database.Cursor queryRoots(java.lang.String[] projection) throws java.io.FileNotFoundException;
@java.lang.SuppressWarnings(value={"unused"})
public  android.database.Cursor queryRecentDocuments(java.lang.String rootId, java.lang.String[] projection) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public abstract  android.database.Cursor queryDocument(java.lang.String documentId, java.lang.String[] projection) throws java.io.FileNotFoundException;
public abstract  android.database.Cursor queryChildDocuments(java.lang.String parentDocumentId, java.lang.String[] projection, java.lang.String sortOrder) throws java.io.FileNotFoundException;
@java.lang.SuppressWarnings(value={"unused"})
public  android.database.Cursor querySearchDocuments(java.lang.String rootId, java.lang.String query, java.lang.String[] projection) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public  java.lang.String getDocumentType(java.lang.String documentId) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public abstract  android.os.ParcelFileDescriptor openDocument(java.lang.String documentId, java.lang.String mode, android.os.CancellationSignal signal) throws java.io.FileNotFoundException;
@java.lang.SuppressWarnings(value={"unused"})
public  android.content.res.AssetFileDescriptor openDocumentThumbnail(java.lang.String documentId, android.graphics.Point sizeHint, android.os.CancellationSignal signal) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public final  android.database.Cursor query(android.net.Uri uri, java.lang.String[] projection, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String sortOrder) { throw new RuntimeException("Stub!"); }
public final  java.lang.String getType(android.net.Uri uri) { throw new RuntimeException("Stub!"); }
public  android.net.Uri canonicalize(android.net.Uri uri) { throw new RuntimeException("Stub!"); }
public final  android.net.Uri insert(android.net.Uri uri, android.content.ContentValues values) { throw new RuntimeException("Stub!"); }
public final  int delete(android.net.Uri uri, java.lang.String selection, java.lang.String[] selectionArgs) { throw new RuntimeException("Stub!"); }
public final  int update(android.net.Uri uri, android.content.ContentValues values, java.lang.String selection, java.lang.String[] selectionArgs) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle call(java.lang.String method, java.lang.String arg, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public final  void revokeDocumentPermission(java.lang.String documentId) { throw new RuntimeException("Stub!"); }
public final  android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String mode) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public final  android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String mode, android.os.CancellationSignal signal) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"resource"})
public final  android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String mode) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"resource"})
public final  android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String mode, android.os.CancellationSignal signal) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public final  android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String mimeTypeFilter, android.os.Bundle opts) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
public final  android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String mimeTypeFilter, android.os.Bundle opts, android.os.CancellationSignal signal) throws java.io.FileNotFoundException { throw new RuntimeException("Stub!"); }
}
