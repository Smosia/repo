package android.graphics.pdf;
public class PdfDocument
{
public static final class PageInfo
{
public static final class Builder
{
public  Builder(int pageWidth, int pageHeight, int pageNumber) { throw new RuntimeException("Stub!"); }
public  android.graphics.pdf.PdfDocument.PageInfo.Builder setContentRect(android.graphics.Rect contentRect) { throw new RuntimeException("Stub!"); }
public  android.graphics.pdf.PdfDocument.PageInfo create() { throw new RuntimeException("Stub!"); }
}
PageInfo() { throw new RuntimeException("Stub!"); }
public  int getPageWidth() { throw new RuntimeException("Stub!"); }
public  int getPageHeight() { throw new RuntimeException("Stub!"); }
public  android.graphics.Rect getContentRect() { throw new RuntimeException("Stub!"); }
public  int getPageNumber() { throw new RuntimeException("Stub!"); }
}
public static final class Page
{
Page() { throw new RuntimeException("Stub!"); }
public  android.graphics.Canvas getCanvas() { throw new RuntimeException("Stub!"); }
public  android.graphics.pdf.PdfDocument.PageInfo getInfo() { throw new RuntimeException("Stub!"); }
}
public  PdfDocument() { throw new RuntimeException("Stub!"); }
public  android.graphics.pdf.PdfDocument.Page startPage(android.graphics.pdf.PdfDocument.PageInfo pageInfo) { throw new RuntimeException("Stub!"); }
public  void finishPage(android.graphics.pdf.PdfDocument.Page page) { throw new RuntimeException("Stub!"); }
public  void writeTo(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.util.List<android.graphics.pdf.PdfDocument.PageInfo> getPages() { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
