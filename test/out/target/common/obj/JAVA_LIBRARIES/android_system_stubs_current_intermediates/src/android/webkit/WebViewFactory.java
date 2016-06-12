package android.webkit;
public final class WebViewFactory
{
public  WebViewFactory() { throw new RuntimeException("Stub!"); }
public static  java.lang.String getWebViewPackageName() { throw new RuntimeException("Stub!"); }
public static  android.content.pm.PackageInfo getLoadedPackageInfo() { throw new RuntimeException("Stub!"); }
public static  int loadWebViewNativeLibraryFromPackage(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public static  void prepareWebViewInZygote() { throw new RuntimeException("Stub!"); }
public static  void prepareWebViewInSystemServer() { throw new RuntimeException("Stub!"); }
public static  void onWebViewUpdateInstalled() { throw new RuntimeException("Stub!"); }
public static final java.lang.String CHROMIUM_WEBVIEW_VMSIZE_SIZE_PROPERTY = "persist.sys.webview.vmsize";
public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
public static final int LIBLOAD_FAILED_JNI_CALL = 7;
public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
public static final int LIBLOAD_FAILED_TO_LOAD_LIBRARY = 6;
public static final int LIBLOAD_FAILED_TO_OPEN_RELRO_FILE = 5;
public static final int LIBLOAD_FAILED_WAITING_FOR_RELRO = 3;
public static final int LIBLOAD_SUCCESS = 0;
public static final int LIBLOAD_WRONG_PACKAGE_NAME = 1;
}
