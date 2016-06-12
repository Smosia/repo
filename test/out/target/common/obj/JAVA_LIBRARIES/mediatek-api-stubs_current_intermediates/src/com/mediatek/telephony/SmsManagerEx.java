package com.mediatek.telephony;
public class SmsManagerEx
{
SmsManagerEx() { throw new RuntimeException("Stub!"); }
public  void sendTextMessage(java.lang.String destinationAddress, java.lang.String scAddress, java.lang.String text, android.app.PendingIntent sentIntent, android.app.PendingIntent deliveryIntent, int slotId) { throw new RuntimeException("Stub!"); }
public  java.util.ArrayList<java.lang.String> divideMessage(java.lang.String text) { throw new RuntimeException("Stub!"); }
public  void sendMultipartTextMessage(java.lang.String destinationAddress, java.lang.String scAddress, java.util.ArrayList<java.lang.String> parts, java.util.ArrayList<android.app.PendingIntent> sentIntents, java.util.ArrayList<android.app.PendingIntent> deliveryIntents, int slotId) { throw new RuntimeException("Stub!"); }
public  void sendDataMessage(java.lang.String destinationAddress, java.lang.String scAddress, short destinationPort, byte[] data, android.app.PendingIntent sentIntent, android.app.PendingIntent deliveryIntent, int slotId) { throw new RuntimeException("Stub!"); }
public static  com.mediatek.telephony.SmsManagerEx getDefault() { throw new RuntimeException("Stub!"); }
public  void sendDataMessage(java.lang.String destinationAddress, java.lang.String scAddress, short destinationPort, short originalPort, byte[] data, android.app.PendingIntent sentIntent, android.app.PendingIntent deliveryIntent, int slotId) { throw new RuntimeException("Stub!"); }
public  void sendTextMessageWithExtraParams(java.lang.String destAddr, java.lang.String scAddr, java.lang.String text, android.os.Bundle extraParams, android.app.PendingIntent sentIntent, android.app.PendingIntent deliveryIntent, int slotId) { throw new RuntimeException("Stub!"); }
public  void sendMultipartTextMessageWithExtraParams(java.lang.String destAddr, java.lang.String scAddr, java.util.ArrayList<java.lang.String> parts, android.os.Bundle extraParams, java.util.ArrayList<android.app.PendingIntent> sentIntents, java.util.ArrayList<android.app.PendingIntent> deliveryIntents, int slotId) { throw new RuntimeException("Stub!"); }
}
