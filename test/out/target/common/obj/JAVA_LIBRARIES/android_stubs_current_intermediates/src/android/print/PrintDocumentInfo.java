package android.print;
public final class PrintDocumentInfo
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  android.print.PrintDocumentInfo.Builder setPageCount(int pageCount) { throw new RuntimeException("Stub!"); }
public  android.print.PrintDocumentInfo.Builder setContentType(int type) { throw new RuntimeException("Stub!"); }
public  android.print.PrintDocumentInfo build() { throw new RuntimeException("Stub!"); }
}
PrintDocumentInfo() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public  int getPageCount() { throw new RuntimeException("Stub!"); }
public  int getContentType() { throw new RuntimeException("Stub!"); }
public  long getDataSize() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int CONTENT_TYPE_DOCUMENT = 0;
public static final int CONTENT_TYPE_PHOTO = 1;
public static final int CONTENT_TYPE_UNKNOWN = -1;
public static final android.os.Parcelable.Creator<android.print.PrintDocumentInfo> CREATOR;
public static final int PAGE_COUNT_UNKNOWN = -1;
static { CREATOR = null; }
}
