package android.app.usage;
public final class UsageStatsManager
{
UsageStatsManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.app.usage.UsageStats> queryUsageStats(int intervalType, long beginTime, long endTime) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.app.usage.ConfigurationStats> queryConfigurations(int intervalType, long beginTime, long endTime) { throw new RuntimeException("Stub!"); }
public  android.app.usage.UsageEvents queryEvents(long beginTime, long endTime) { throw new RuntimeException("Stub!"); }
public  java.util.Map<java.lang.String, android.app.usage.UsageStats> queryAndAggregateUsageStats(long beginTime, long endTime) { throw new RuntimeException("Stub!"); }
public  boolean isAppInactive(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public static final int INTERVAL_BEST = 4;
public static final int INTERVAL_DAILY = 0;
public static final int INTERVAL_MONTHLY = 2;
public static final int INTERVAL_WEEKLY = 1;
public static final int INTERVAL_YEARLY = 3;
}
