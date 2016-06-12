package android.view.animation;
public class Transformation
{
public  Transformation() { throw new RuntimeException("Stub!"); }
public  void clear() { throw new RuntimeException("Stub!"); }
public  int getTransformationType() { throw new RuntimeException("Stub!"); }
public  void setTransformationType(int transformationType) { throw new RuntimeException("Stub!"); }
public  void set(android.view.animation.Transformation t) { throw new RuntimeException("Stub!"); }
public  void compose(android.view.animation.Transformation t) { throw new RuntimeException("Stub!"); }
public  android.graphics.Matrix getMatrix() { throw new RuntimeException("Stub!"); }
public  void setAlpha(float alpha) { throw new RuntimeException("Stub!"); }
public  float getAlpha() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  java.lang.String toShortString() { throw new RuntimeException("Stub!"); }
public static final int TYPE_ALPHA = 1;
public static final int TYPE_BOTH = 3;
public static final int TYPE_IDENTITY = 0;
public static final int TYPE_MATRIX = 2;
protected float mAlpha;
protected android.graphics.Matrix mMatrix;
protected int mTransformationType;
}
