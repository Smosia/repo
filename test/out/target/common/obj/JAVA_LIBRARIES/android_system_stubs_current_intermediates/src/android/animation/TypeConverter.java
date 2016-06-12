package android.animation;
public abstract class TypeConverter<T, V>
{
public  TypeConverter(java.lang.Class<T> fromClass, java.lang.Class<V> toClass) { throw new RuntimeException("Stub!"); }
public abstract  V convert(T value);
}
