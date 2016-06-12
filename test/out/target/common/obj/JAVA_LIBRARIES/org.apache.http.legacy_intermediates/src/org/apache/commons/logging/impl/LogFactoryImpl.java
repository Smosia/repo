package org.apache.commons.logging.impl;
@java.lang.Deprecated()
public class LogFactoryImpl
  extends org.apache.commons.logging.LogFactory
{
public  LogFactoryImpl() { throw new RuntimeException("Stub!"); }
public  java.lang.Object getAttribute(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getAttributeNames() { throw new RuntimeException("Stub!"); }
public  org.apache.commons.logging.Log getInstance(java.lang.Class clazz) throws org.apache.commons.logging.LogConfigurationException { throw new RuntimeException("Stub!"); }
public  org.apache.commons.logging.Log getInstance(java.lang.String name) throws org.apache.commons.logging.LogConfigurationException { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
public  void removeAttribute(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  void setAttribute(java.lang.String name, java.lang.Object value) { throw new RuntimeException("Stub!"); }
protected static  java.lang.ClassLoader getContextClassLoader() throws org.apache.commons.logging.LogConfigurationException { throw new RuntimeException("Stub!"); }
protected static  boolean isDiagnosticsEnabled() { throw new RuntimeException("Stub!"); }
protected static  java.lang.ClassLoader getClassLoader(java.lang.Class clazz) { throw new RuntimeException("Stub!"); }
protected  void logDiagnostic(java.lang.String msg) { throw new RuntimeException("Stub!"); }
@Deprecated
protected  java.lang.String getLogClassName() { throw new RuntimeException("Stub!"); }
@Deprecated
protected  java.lang.reflect.Constructor getLogConstructor() throws org.apache.commons.logging.LogConfigurationException { throw new RuntimeException("Stub!"); }
@Deprecated
protected  boolean isJdk13LumberjackAvailable() { throw new RuntimeException("Stub!"); }
@Deprecated
protected  boolean isJdk14Available() { throw new RuntimeException("Stub!"); }
@Deprecated
protected  boolean isLog4JAvailable() { throw new RuntimeException("Stub!"); }
protected  org.apache.commons.logging.Log newInstance(java.lang.String name) throws org.apache.commons.logging.LogConfigurationException { throw new RuntimeException("Stub!"); }
public static final java.lang.String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
public static final java.lang.String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
public static final java.lang.String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
public static final java.lang.String LOG_PROPERTY = "org.apache.commons.logging.Log";
protected static final java.lang.String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
protected java.util.Hashtable attributes;
protected java.util.Hashtable instances;
protected java.lang.reflect.Constructor logConstructor;
protected java.lang.Class[] logConstructorSignature = null;
protected java.lang.reflect.Method logMethod;
protected java.lang.Class[] logMethodSignature = null;
}
