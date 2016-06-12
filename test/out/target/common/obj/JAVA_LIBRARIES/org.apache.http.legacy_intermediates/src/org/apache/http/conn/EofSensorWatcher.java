package org.apache.http.conn;
@java.lang.Deprecated()
public interface EofSensorWatcher
{
public abstract  boolean eofDetected(java.io.InputStream wrapped) throws java.io.IOException;
public abstract  boolean streamClosed(java.io.InputStream wrapped) throws java.io.IOException;
public abstract  boolean streamAbort(java.io.InputStream wrapped) throws java.io.IOException;
}
