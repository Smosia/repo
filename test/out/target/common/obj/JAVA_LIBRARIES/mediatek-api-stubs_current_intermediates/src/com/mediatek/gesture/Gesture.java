package com.mediatek.gesture;
public class Gesture
{
public static interface HandDetectionListener
{
public abstract  void onHandDetected(com.mediatek.gesture.Gesture.HandDetectionEvent event);
}
public class HandDetectionEvent
{
public  HandDetectionEvent() { throw new RuntimeException("Stub!"); }
public  void setBoundBox(android.graphics.Rect boundBox) { throw new RuntimeException("Stub!"); }
public  android.graphics.Rect getBoundBox() { throw new RuntimeException("Stub!"); }
public  void setConfidence(float confidence) { throw new RuntimeException("Stub!"); }
public  float getConfidence() { throw new RuntimeException("Stub!"); }
public  void setId(int id) { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  void setPose(com.mediatek.gesture.Gesture.HandPose pose) { throw new RuntimeException("Stub!"); }
public  com.mediatek.gesture.Gesture.HandPose getPose() { throw new RuntimeException("Stub!"); }
}
public static enum HandPose
{
POSE_OPENPLAM(),
POSE_VICTORY();
}
Gesture() { throw new RuntimeException("Stub!"); }
public static  com.mediatek.gesture.Gesture createGesture() { throw new RuntimeException("Stub!"); }
public  void addHandDetectionListener(com.mediatek.gesture.Gesture.HandDetectionListener listener, com.mediatek.gesture.Gesture.HandPose pose) { throw new RuntimeException("Stub!"); }
public  void removeHandDetectionListener(com.mediatek.gesture.Gesture.HandDetectionListener listener, com.mediatek.gesture.Gesture.HandPose pose) { throw new RuntimeException("Stub!"); }
}
