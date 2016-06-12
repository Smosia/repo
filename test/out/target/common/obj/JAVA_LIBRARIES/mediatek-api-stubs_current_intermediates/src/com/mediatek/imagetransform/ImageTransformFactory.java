package com.mediatek.imagetransform;
public class ImageTransformFactory
{
public class Options
{
public  Options() { throw new RuntimeException("Stub!"); }
public  void setCropRoi(android.graphics.Rect cropRoi) { throw new RuntimeException("Stub!"); }
public  android.graphics.Rect getCropRoi() { throw new RuntimeException("Stub!"); }
public  void setFlip(java.lang.String flip) { throw new RuntimeException("Stub!"); }
public  java.lang.String getFlip() { throw new RuntimeException("Stub!"); }
public  void setRotation(int rotation) { throw new RuntimeException("Stub!"); }
public  int getRotation() { throw new RuntimeException("Stub!"); }
public  void setEncodingQuality(int encodingQuality) { throw new RuntimeException("Stub!"); }
public  int getEncodingQuality() { throw new RuntimeException("Stub!"); }
public  void setDither(boolean dither) { throw new RuntimeException("Stub!"); }
public  boolean isDither() { throw new RuntimeException("Stub!"); }
public  void setSharpness(int sharpness) { throw new RuntimeException("Stub!"); }
public  int getSharpness() { throw new RuntimeException("Stub!"); }
}
ImageTransformFactory() { throw new RuntimeException("Stub!"); }
public static  com.mediatek.imagetransform.ImageTransformFactory createImageTransformFactory() { throw new RuntimeException("Stub!"); }
public  boolean applyTransform(android.media.Image srcImage, android.media.Image targetImage, com.mediatek.imagetransform.ImageTransformFactory.Options options) { throw new RuntimeException("Stub!"); }
public static final java.lang.String FLIP_H = "horizontally";
public static final java.lang.String FLIP_V = "vertically";
public static final int ROT_0 = 0;
public static final int ROT_180 = 180;
public static final int ROT_270 = 270;
public static final int ROT_90 = 90;
}
