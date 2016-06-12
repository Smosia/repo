/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/search/ISearchEngineManagerService.aidl
 */
package com.mediatek.search;
/**
 * @hide
 */
public interface ISearchEngineManagerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.search.ISearchEngineManagerService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.search.ISearchEngineManagerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.search.ISearchEngineManagerService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.search.ISearchEngineManagerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.search.ISearchEngineManagerService))) {
return ((com.mediatek.search.ISearchEngineManagerService)iin);
}
return new com.mediatek.search.ISearchEngineManagerService.Stub.Proxy(obj);
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
case TRANSACTION_getAvailables:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.search.SearchEngine> _result = this.getAvailables();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getDefault:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.search.SearchEngine _result = this.getDefault();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getBestMatch:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
com.mediatek.common.search.SearchEngine _result = this.getBestMatch(_arg0, _arg1);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getSearchEngine:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
com.mediatek.common.search.SearchEngine _result = this.getSearchEngine(_arg0, _arg1);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_setDefault:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.search.SearchEngine _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.common.search.SearchEngine.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.setDefault(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.search.ISearchEngineManagerService
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
@Override public java.util.List<com.mediatek.common.search.SearchEngine> getAvailables() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.common.search.SearchEngine> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAvailables, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.common.search.SearchEngine.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.common.search.SearchEngine getDefault() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.common.search.SearchEngine _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDefault, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.common.search.SearchEngine.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.common.search.SearchEngine getBestMatch(java.lang.String name, java.lang.String favicon) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.common.search.SearchEngine _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(favicon);
mRemote.transact(Stub.TRANSACTION_getBestMatch, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.common.search.SearchEngine.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.common.search.SearchEngine getSearchEngine(int field, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.common.search.SearchEngine _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(field);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_getSearchEngine, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.common.search.SearchEngine.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setDefault(com.mediatek.common.search.SearchEngine engine) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((engine!=null)) {
_data.writeInt(1);
engine.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setDefault, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getAvailables = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getDefault = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getBestMatch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getSearchEngine = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setDefault = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public java.util.List<com.mediatek.common.search.SearchEngine> getAvailables() throws android.os.RemoteException;
public com.mediatek.common.search.SearchEngine getDefault() throws android.os.RemoteException;
public com.mediatek.common.search.SearchEngine getBestMatch(java.lang.String name, java.lang.String favicon) throws android.os.RemoteException;
public com.mediatek.common.search.SearchEngine getSearchEngine(int field, java.lang.String value) throws android.os.RemoteException;
public boolean setDefault(com.mediatek.common.search.SearchEngine engine) throws android.os.RemoteException;
}
