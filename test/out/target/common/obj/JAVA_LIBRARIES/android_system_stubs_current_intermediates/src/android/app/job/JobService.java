package android.app.job;
public abstract class JobService
  extends android.app.Service
{
public  JobService() { throw new RuntimeException("Stub!"); }
public abstract  boolean onStartJob(android.app.job.JobParameters params);
public abstract  boolean onStopJob(android.app.job.JobParameters params);
public final  void jobFinished(android.app.job.JobParameters params, boolean needsReschedule) { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String PERMISSION_BIND = "android.permission.BIND_JOB_SERVICE";
}
