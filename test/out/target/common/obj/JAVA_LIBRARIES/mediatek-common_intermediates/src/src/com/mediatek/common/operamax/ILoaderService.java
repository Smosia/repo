/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/operamax/ILoaderService.aidl
 */
package com.mediatek.common.operamax;
public interface ILoaderService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.operamax.ILoaderService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.operamax.ILoaderService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.operamax.ILoaderService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.operamax.ILoaderService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.operamax.ILoaderService))) {
return ((com.mediatek.common.operamax.ILoaderService)iin);
}
return new com.mediatek.common.operamax.ILoaderService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_startSaving:
{
data.enforceInterface(DESCRIPTOR);
this.startSaving();
reply.writeNoException();
return true;
}
case TRANSACTION_stopSaving:
{
data.enforceInterface(DESCRIPTOR);
this.stopSaving();
reply.writeNoException();
return true;
}
case TRANSACTION_getTunnelState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getTunnelState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getSavingState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getSavingState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_registerStateListener:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.operamax.ILoaderStateListener _arg0;
_arg0 = com.mediatek.common.operamax.ILoaderStateListener.Stub.asInterface(data.readStrongBinder());
this.registerStateListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterStateListener:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.operamax.ILoaderStateListener _arg0;
_arg0 = com.mediatek.common.operamax.ILoaderStateListener.Stub.asInterface(data.readStrongBinder());
this.unregisterStateListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_launchOperaMAX:
{
data.enforceInterface(DESCRIPTOR);
this.launchOperaMAX();
reply.writeNoException();
return true;
}
case TRANSACTION_addDirectedApp:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.addDirectedApp(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeDirectedApp:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeDirectedApp(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeAllDirectedApps:
{
data.enforceInterface(DESCRIPTOR);
this.removeAllDirectedApps();
reply.writeNoException();
return true;
}
case TRANSACTION_isAppDirected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isAppDirected(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDirectedAppList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getDirectedAppList();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_addDirectedHost:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.addDirectedHost(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeDirectedHost:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeDirectedHost(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeAllDirectedHosts:
{
data.enforceInterface(DESCRIPTOR);
this.removeAllDirectedHosts();
reply.writeNoException();
return true;
}
case TRANSACTION_isHostDirected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isHostDirected(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDirectedHostList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getDirectedHostList();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_addDirectedHeaderField:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.addDirectedHeaderField(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_removeDirectedHeaderField:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.removeDirectedHeaderField(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_removeAllDirectedHeaderFields:
{
data.enforceInterface(DESCRIPTOR);
this.removeAllDirectedHeaderFields();
reply.writeNoException();
return true;
}
case TRANSACTION_isHeaderFieldDirected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.isHeaderFieldDirected(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDirectedHeaderFieldList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getDirectedHeaderFieldList();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_setCompressLevel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setCompressLevel(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getCompressLevel:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCompressLevel();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.operamax.ILoaderService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Called to start saving. Saving service will be started in background. IStateListener should
     * be used to watch if saving is actually started or not.
     */
@Override public void startSaving() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startSaving, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Called to stop saving. Saving service will be stopped in background.
     */
@Override public void stopSaving() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSaving, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Get current tunnel state.
     *
     * @return value could be:
     *     1: opened
     *     2: closed
     */
@Override public int getTunnelState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTunnelState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get current saving state.
     *
     * @return value could be:
     *     1: started
     *     2: stopped
     *     3: paused
     *     4: service exception
     */
@Override public int getSavingState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSavingState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Register listener to watch tunnel and saving state changes.
     *
     * @see ILoaderStateListener
     */
@Override public void registerStateListener(com.mediatek.common.operamax.ILoaderStateListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerStateListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Unregister listener that watches tunnel and saving state changes.
     *
     * @see ILoaderStateListener
     */
@Override public void unregisterStateListener(com.mediatek.common.operamax.ILoaderStateListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterStateListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Launch OperaMax main UI
     */
@Override public void launchOperaMAX() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_launchOperaMAX, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Add app into directed connection list
     */
@Override public void addDirectedApp(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_addDirectedApp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove app from directed connection list
     */
@Override public void removeDirectedApp(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_removeDirectedApp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove all apps from directed connection list
     */
@Override public void removeAllDirectedApps() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_removeAllDirectedApps, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Check if app is in directed connection list
     */
@Override public boolean isAppDirected(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_isAppDirected, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get directed app list
     */
@Override public java.lang.String[] getDirectedAppList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDirectedAppList, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Add host into directed connection list
     */
@Override public void addDirectedHost(java.lang.String host) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(host);
mRemote.transact(Stub.TRANSACTION_addDirectedHost, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove host from directed connection list
     */
@Override public void removeDirectedHost(java.lang.String host) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(host);
mRemote.transact(Stub.TRANSACTION_removeDirectedHost, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove all hosts from directed connection list
     */
@Override public void removeAllDirectedHosts() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_removeAllDirectedHosts, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Check if host is in directed connection list
     */
@Override public boolean isHostDirected(java.lang.String host) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(host);
mRemote.transact(Stub.TRANSACTION_isHostDirected, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get directed host list
     */
@Override public java.lang.String[] getDirectedHostList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDirectedHostList, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Add header field into directed connection list
     */
@Override public void addDirectedHeaderField(java.lang.String key, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_addDirectedHeaderField, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove header field from directed connection list
     */
@Override public void removeDirectedHeaderField(java.lang.String key, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_removeDirectedHeaderField, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove all header fields from directed connection list
     */
@Override public void removeAllDirectedHeaderFields() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_removeAllDirectedHeaderFields, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Check if header field is in directed connection list
     */
@Override public boolean isHeaderFieldDirected(java.lang.String key, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_isHeaderFieldDirected, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get directed header field list. Each field is like 'key:value'
     */
@Override public java.lang.String[] getDirectedHeaderFieldList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDirectedHeaderFieldList, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set compress level.
     * @param level must be in [1, 3]. 1 -> low compress; 2 -> medium compress; 3 -> high compress,
     */
@Override public void setCompressLevel(int level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(level);
mRemote.transact(Stub.TRANSACTION_setCompressLevel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Get current compress level.
     */
@Override public int getCompressLevel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCompressLevel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_startSaving = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopSaving = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getTunnelState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getSavingState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_registerStateListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_unregisterStateListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_launchOperaMAX = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_addDirectedApp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_removeDirectedApp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_removeAllDirectedApps = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isAppDirected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getDirectedAppList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_addDirectedHost = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_removeDirectedHost = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_removeAllDirectedHosts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_isHostDirected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getDirectedHostList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_addDirectedHeaderField = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_removeDirectedHeaderField = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_removeAllDirectedHeaderFields = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_isHeaderFieldDirected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_getDirectedHeaderFieldList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_setCompressLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_getCompressLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
}
/**
     * Called to start saving. Saving service will be started in background. IStateListener should
     * be used to watch if saving is actually started or not.
     */
public void startSaving() throws android.os.RemoteException;
/**
     * Called to stop saving. Saving service will be stopped in background.
     */
public void stopSaving() throws android.os.RemoteException;
/**
     * Get current tunnel state.
     *
     * @return value could be:
     *     1: opened
     *     2: closed
     */
public int getTunnelState() throws android.os.RemoteException;
/**
     * Get current saving state.
     *
     * @return value could be:
     *     1: started
     *     2: stopped
     *     3: paused
     *     4: service exception
     */
public int getSavingState() throws android.os.RemoteException;
/**
     * Register listener to watch tunnel and saving state changes.
     *
     * @see ILoaderStateListener
     */
public void registerStateListener(com.mediatek.common.operamax.ILoaderStateListener listener) throws android.os.RemoteException;
/**
     * Unregister listener that watches tunnel and saving state changes.
     *
     * @see ILoaderStateListener
     */
public void unregisterStateListener(com.mediatek.common.operamax.ILoaderStateListener listener) throws android.os.RemoteException;
/**
     * Launch OperaMax main UI
     */
public void launchOperaMAX() throws android.os.RemoteException;
/**
     * Add app into directed connection list
     */
public void addDirectedApp(java.lang.String packageName) throws android.os.RemoteException;
/**
     * Remove app from directed connection list
     */
public void removeDirectedApp(java.lang.String packageName) throws android.os.RemoteException;
/**
     * Remove all apps from directed connection list
     */
public void removeAllDirectedApps() throws android.os.RemoteException;
/**
     * Check if app is in directed connection list
     */
public boolean isAppDirected(java.lang.String packageName) throws android.os.RemoteException;
/**
     * Get directed app list
     */
public java.lang.String[] getDirectedAppList() throws android.os.RemoteException;
/**
     * Add host into directed connection list
     */
public void addDirectedHost(java.lang.String host) throws android.os.RemoteException;
/**
     * Remove host from directed connection list
     */
public void removeDirectedHost(java.lang.String host) throws android.os.RemoteException;
/**
     * Remove all hosts from directed connection list
     */
public void removeAllDirectedHosts() throws android.os.RemoteException;
/**
     * Check if host is in directed connection list
     */
public boolean isHostDirected(java.lang.String host) throws android.os.RemoteException;
/**
     * Get directed host list
     */
public java.lang.String[] getDirectedHostList() throws android.os.RemoteException;
/**
     * Add header field into directed connection list
     */
public void addDirectedHeaderField(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
/**
     * Remove header field from directed connection list
     */
public void removeDirectedHeaderField(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
/**
     * Remove all header fields from directed connection list
     */
public void removeAllDirectedHeaderFields() throws android.os.RemoteException;
/**
     * Check if header field is in directed connection list
     */
public boolean isHeaderFieldDirected(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
/**
     * Get directed header field list. Each field is like 'key:value'
     */
public java.lang.String[] getDirectedHeaderFieldList() throws android.os.RemoteException;
/**
     * Set compress level.
     * @param level must be in [1, 3]. 1 -> low compress; 2 -> medium compress; 3 -> high compress,
     */
public void setCompressLevel(int level) throws android.os.RemoteException;
/**
     * Get current compress level.
     */
public int getCompressLevel() throws android.os.RemoteException;
}
