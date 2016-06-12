package com.mediatek.effect;
public abstract class Effect
{
public static interface EffectUpdateListener
{
public abstract  void onEffectUpdateds(com.mediatek.effect.Effect effect, java.lang.Object info);
}
Effect() { throw new RuntimeException("Stub!"); }
public abstract  void apply(android.media.Image srcImage, android.media.Image targetImage);
public abstract  java.lang.String getName();
public abstract  void setParameter(java.lang.String parameterKey, java.lang.Object value);
public  void setUpdateListener(com.mediatek.effect.Effect.EffectUpdateListener listener) { throw new RuntimeException("Stub!"); }
public abstract  void release();
}
