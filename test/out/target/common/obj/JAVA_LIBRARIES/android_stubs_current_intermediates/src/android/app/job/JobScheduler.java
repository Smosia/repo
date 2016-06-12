package android.app.job;
public abstract class JobScheduler
{
public  JobScheduler() { throw new RuntimeException("Stub!"); }
public abstract  int schedule(android.app.job.JobInfo job);
public abstract  void cancel(int jobId);
public abstract  void cancelAll();
public abstract  java.util.List<android.app.job.JobInfo> getAllPendingJobs();
public static final int RESULT_FAILURE = 0;
public static final int RESULT_SUCCESS = 1;
}
