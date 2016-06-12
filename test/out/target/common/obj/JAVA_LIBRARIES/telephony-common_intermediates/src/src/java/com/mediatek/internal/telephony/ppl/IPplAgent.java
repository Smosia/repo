/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/telephony/src/java/com/mediatek/internal/telephony/ppl/IPplAgent.aidl
 */
package com.mediatek.internal.telephony.ppl;
public interface IPplAgent extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.internal.telephony.ppl.IPplAgent
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.internal.telephony.ppl.IPplAgent";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.internal.telephony.ppl.IPplAgent interface,
 * generating a proxy if needed.
 */
public static com.mediatek.internal.telephony.ppl.IPplAgent asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.internal.telephony.ppl.IPplAgent))) {
return ((com.mediatek.internal.telephony.ppl.IPplAgent)iin);
}
return new com.mediatek.internal.telephony.ppl.IPplAgent.Stub.Proxy(obj);
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
case TRANSACTION_readControlData:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readControlData();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_writeControlData:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _result = this.writeControlData(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_needLock:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.needLock();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.internal.telephony.ppl.IPplAgent
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
@Override public byte[] readControlData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readControlData, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int writeControlData(byte[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_writeControlData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int needLock() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_needLock, _data, _reply, 0);
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
static final int TRANSACTION_readControlData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_writeControlData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_needLock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public byte[] readControlData() throws android.os.RemoteException;
public int writeControlData(byte[] data) throws android.os.RemoteException;
public int needLock() throws android.os.RemoteException;
}
