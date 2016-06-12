package org.apache.commons.logging;
@java.lang.Deprecated()
public interface Log
{
public abstract  boolean isDebugEnabled();
public abstract  boolean isErrorEnabled();
public abstract  boolean isFatalEnabled();
public abstract  boolean isInfoEnabled();
public abstract  boolean isTraceEnabled();
public abstract  boolean isWarnEnabled();
public abstract  void trace(java.lang.Object message);
public abstract  void trace(java.lang.Object message, java.lang.Throwable t);
public abstract  void debug(java.lang.Object message);
public abstract  void debug(java.lang.Object message, java.lang.Throwable t);
public abstract  void info(java.lang.Object message);
public abstract  void info(java.lang.Object message, java.lang.Throwable t);
public abstract  void warn(java.lang.Object message);
public abstract  void warn(java.lang.Object message, java.lang.Throwable t);
public abstract  void error(java.lang.Object message);
public abstract  void error(java.lang.Object message, java.lang.Throwable t);
public abstract  void fatal(java.lang.Object message);
public abstract  void fatal(java.lang.Object message, java.lang.Throwable t);
}
