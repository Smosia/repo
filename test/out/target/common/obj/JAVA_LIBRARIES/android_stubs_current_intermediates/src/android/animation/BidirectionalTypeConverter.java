package android.animation;
public abstract class BidirectionalTypeConverter<T, V>
  extends android.animation.TypeConverter<T, V>
{
public  BidirectionalTypeConverter(java.lang.Class<T> fromClass, java.lang.Class<V> toClass) { super((java.lang.Class)null,(java.lang.Class)null); throw new RuntimeException("Stub!"); }
public abstract  T convertBack(V value);
public  android.animation.BidirectionalTypeConverter<V, T> invert() { throw new RuntimeException("Stub!"); }
}
