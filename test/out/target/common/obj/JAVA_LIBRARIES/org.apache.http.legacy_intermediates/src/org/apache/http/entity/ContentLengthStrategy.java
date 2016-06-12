package org.apache.http.entity;
@java.lang.Deprecated()
public interface ContentLengthStrategy
{
public abstract  long determineLength(org.apache.http.HttpMessage message) throws org.apache.http.HttpException;
public static final int CHUNKED = -2;
public static final int IDENTITY = -1;
}
