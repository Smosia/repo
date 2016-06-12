package org.apache.http.params;
public interface HttpParams
{
public abstract  java.lang.Object getParameter(java.lang.String arg0);
public abstract  org.apache.http.params.HttpParams setParameter(java.lang.String arg0, java.lang.Object arg1);
public abstract  org.apache.http.params.HttpParams copy();
public abstract  boolean removeParameter(java.lang.String arg0);
public abstract  long getLongParameter(java.lang.String arg0, long arg1);
public abstract  org.apache.http.params.HttpParams setLongParameter(java.lang.String arg0, long arg1);
public abstract  int getIntParameter(java.lang.String arg0, int arg1);
public abstract  org.apache.http.params.HttpParams setIntParameter(java.lang.String arg0, int arg1);
public abstract  double getDoubleParameter(java.lang.String arg0, double arg1);
public abstract  org.apache.http.params.HttpParams setDoubleParameter(java.lang.String arg0, double arg1);
public abstract  boolean getBooleanParameter(java.lang.String arg0, boolean arg1);
public abstract  org.apache.http.params.HttpParams setBooleanParameter(java.lang.String arg0, boolean arg1);
public abstract  boolean isParameterTrue(java.lang.String arg0);
public abstract  boolean isParameterFalse(java.lang.String arg0);
}
