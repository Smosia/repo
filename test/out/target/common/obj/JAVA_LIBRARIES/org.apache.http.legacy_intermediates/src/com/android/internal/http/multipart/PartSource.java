package com.android.internal.http.multipart;
public interface PartSource
{
public abstract  long getLength();
public abstract  java.lang.String getFileName();
public abstract  java.io.InputStream createInputStream() throws java.io.IOException;
}
