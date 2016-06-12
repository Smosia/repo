package android.provider;
public final class DocumentsContract
{
public static final class Document
{
Document() { throw new RuntimeException("Stub!"); }
public static final java.lang.String COLUMN_DISPLAY_NAME = "_display_name";
public static final java.lang.String COLUMN_DOCUMENT_ID = "document_id";
public static final java.lang.String COLUMN_FLAGS = "flags";
public static final java.lang.String COLUMN_ICON = "icon";
public static final java.lang.String COLUMN_LAST_MODIFIED = "last_modified";
public static final java.lang.String COLUMN_MIME_TYPE = "mime_type";
public static final java.lang.String COLUMN_SIZE = "_size";
public static final java.lang.String COLUMN_SUMMARY = "summary";
public static final int FLAG_DIR_PREFERS_GRID = 16;
public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
public static final int FLAG_SUPPORTS_DELETE = 4;
public static final int FLAG_SUPPORTS_RENAME = 64;
public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
public static final int FLAG_SUPPORTS_WRITE = 2;
public static final java.lang.String MIME_TYPE_DIR = "vnd.android.document/directory";
}
public static final class Root
{
Root() { throw new RuntimeException("Stub!"); }
public static final java.lang.String COLUMN_AVAILABLE_BYTES = "available_bytes";
public static final java.lang.String COLUMN_DOCUMENT_ID = "document_id";
public static final java.lang.String COLUMN_FLAGS = "flags";
public static final java.lang.String COLUMN_ICON = "icon";
public static final java.lang.String COLUMN_MIME_TYPES = "mime_types";
public static final java.lang.String COLUMN_ROOT_ID = "root_id";
public static final java.lang.String COLUMN_SUMMARY = "summary";
public static final java.lang.String COLUMN_TITLE = "title";
public static final int FLAG_LOCAL_ONLY = 2;
public static final int FLAG_SUPPORTS_CREATE = 1;
public static final int FLAG_SUPPORTS_IS_CHILD = 16;
public static final int FLAG_SUPPORTS_RECENTS = 4;
public static final int FLAG_SUPPORTS_SEARCH = 8;
}
DocumentsContract() { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildRootsUri(java.lang.String authority) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildRootUri(java.lang.String authority, java.lang.String rootId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildRecentDocumentsUri(java.lang.String authority, java.lang.String rootId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildTreeDocumentUri(java.lang.String authority, java.lang.String documentId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildDocumentUri(java.lang.String authority, java.lang.String documentId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildDocumentUriUsingTree(android.net.Uri treeUri, java.lang.String documentId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildChildDocumentsUri(java.lang.String authority, java.lang.String parentDocumentId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildChildDocumentsUriUsingTree(android.net.Uri treeUri, java.lang.String parentDocumentId) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri buildSearchDocumentsUri(java.lang.String authority, java.lang.String rootId, java.lang.String query) { throw new RuntimeException("Stub!"); }
public static  boolean isDocumentUri(android.content.Context context, android.net.Uri uri) { throw new RuntimeException("Stub!"); }
public static  java.lang.String getRootId(android.net.Uri rootUri) { throw new RuntimeException("Stub!"); }
public static  java.lang.String getDocumentId(android.net.Uri documentUri) { throw new RuntimeException("Stub!"); }
public static  java.lang.String getTreeDocumentId(android.net.Uri documentUri) { throw new RuntimeException("Stub!"); }
public static  java.lang.String getSearchDocumentsQuery(android.net.Uri searchDocumentsUri) { throw new RuntimeException("Stub!"); }
public static  android.graphics.Bitmap getDocumentThumbnail(android.content.ContentResolver resolver, android.net.Uri documentUri, android.graphics.Point size, android.os.CancellationSignal signal) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri createDocument(android.content.ContentResolver resolver, android.net.Uri parentDocumentUri, java.lang.String mimeType, java.lang.String displayName) { throw new RuntimeException("Stub!"); }
public static  android.net.Uri renameDocument(android.content.ContentResolver resolver, android.net.Uri documentUri, java.lang.String displayName) { throw new RuntimeException("Stub!"); }
public static  boolean deleteDocument(android.content.ContentResolver resolver, android.net.Uri documentUri) { throw new RuntimeException("Stub!"); }
public static final java.lang.String EXTRA_ERROR = "error";
public static final java.lang.String EXTRA_EXCLUDE_SELF = "android.provider.extra.EXCLUDE_SELF";
public static final java.lang.String EXTRA_INFO = "info";
public static final java.lang.String EXTRA_LOADING = "loading";
public static final java.lang.String EXTRA_PROMPT = "android.provider.extra.PROMPT";
public static final java.lang.String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
}
