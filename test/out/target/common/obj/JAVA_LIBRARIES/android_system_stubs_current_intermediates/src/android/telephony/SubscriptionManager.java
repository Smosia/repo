package android.telephony;
public class SubscriptionManager
{
public static class OnSubscriptionsChangedListener
{
public  OnSubscriptionsChangedListener() { throw new RuntimeException("Stub!"); }
public  void onSubscriptionsChanged() { throw new RuntimeException("Stub!"); }
}
SubscriptionManager() { throw new RuntimeException("Stub!"); }
public static  android.telephony.SubscriptionManager from(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  void addOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener listener) { throw new RuntimeException("Stub!"); }
public  void removeOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener listener) { throw new RuntimeException("Stub!"); }
public  android.telephony.SubscriptionInfo getActiveSubscriptionInfo(int subId) { throw new RuntimeException("Stub!"); }
public  android.telephony.SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int slotIdx) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList() { throw new RuntimeException("Stub!"); }
public  int getActiveSubscriptionInfoCount() { throw new RuntimeException("Stub!"); }
public  int getActiveSubscriptionInfoCountMax() { throw new RuntimeException("Stub!"); }
public  boolean isNetworkRoaming(int subId) { throw new RuntimeException("Stub!"); }
public static final int DATA_ROAMING_DISABLE = 0;
public static final int DATA_ROAMING_ENABLE = 1;
}
