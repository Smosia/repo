/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/android/nfc/INfcAdapterGsmaExtras.aidl
 */
package android.nfc;
//import org.si;
/**
 * GSMA interface which dependens on other module
 *//**
* @hide
*/
public interface INfcAdapterGsmaExtras extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.nfc.INfcAdapterGsmaExtras
{
private static final java.lang.String DESCRIPTOR = "android.nfc.INfcAdapterGsmaExtras";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.nfc.INfcAdapterGsmaExtras interface,
 * generating a proxy if needed.
 */
public static android.nfc.INfcAdapterGsmaExtras asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.nfc.INfcAdapterGsmaExtras))) {
return ((android.nfc.INfcAdapterGsmaExtras)iin);
}
return new android.nfc.INfcAdapterGsmaExtras.Stub.Proxy(obj);
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
case TRANSACTION_getActiveSeValue:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getActiveSeValue();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setActiveSeValue:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setActiveSeValue(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_commitRouting:
{
data.enforceInterface(DESCRIPTOR);
this.commitRouting();
reply.writeNoException();
return true;
}
case TRANSACTION_routeAids:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.routeAids(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isHceCapable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isHceCapable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableMultiEvtTransaction:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.enableMultiEvtTransaction();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isNFCEventAllowed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
java.lang.String[] _arg2;
_arg2 = data.createStringArray();
boolean[] _result = this.isNFCEventAllowed(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeBooleanArray(_result);
return true;
}
case TRANSACTION_setNfcSwpActive:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setNfcSwpActive(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.nfc.INfcAdapterGsmaExtras
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
     * Get the active SE.
     */
@Override public int getActiveSeValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getActiveSeValue, _data, _reply, 0);
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
     * Set the active SE.
     */
@Override public void setActiveSeValue(int seValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(seValue);
mRemote.transact(Stub.TRANSACTION_setActiveSeValue, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * CommitRouting.
     */
@Override public void commitRouting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_commitRouting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * route Aids.
     */
@Override public void routeAids(java.lang.String aid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(aid);
mRemote.transact(Stub.TRANSACTION_routeAids, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Query if system has HCE Feature
     */
@Override public boolean isHceCapable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isHceCapable, _data, _reply, 0);
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
     * Enable Multi Event Transaction Broadcast.
     */
@Override public boolean enableMultiEvtTransaction() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enableMultiEvtTransaction, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//SEAPI function
/**
     * is aid allow in the SIM applet
     */
@Override public boolean[] isNFCEventAllowed(java.lang.String reader, byte[] aid, java.lang.String[] packageNames) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(reader);
_data.writeByteArray(aid);
_data.writeStringArray(packageNames);
mRemote.transact(Stub.TRANSACTION_isNFCEventAllowed, _data, _reply, 0);
_reply.readException();
_result = _reply.createBooleanArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Enable SWP.
     */
@Override public boolean setNfcSwpActive(int simID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(simID);
mRemote.transact(Stub.TRANSACTION_setNfcSwpActive, _data, _reply, 0);
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
static final int TRANSACTION_getActiveSeValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setActiveSeValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_commitRouting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_routeAids = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_isHceCapable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_enableMultiEvtTransaction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isNFCEventAllowed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setNfcSwpActive = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
/**
     * Get the active SE.
     */
public int getActiveSeValue() throws android.os.RemoteException;
/**
     * Set the active SE.
     */
public void setActiveSeValue(int seValue) throws android.os.RemoteException;
/**
     * CommitRouting.
     */
public void commitRouting() throws android.os.RemoteException;
/**
     * route Aids.
     */
public void routeAids(java.lang.String aid) throws android.os.RemoteException;
/**
     * Query if system has HCE Feature
     */
public boolean isHceCapable() throws android.os.RemoteException;
/**
     * Enable Multi Event Transaction Broadcast.
     */
public boolean enableMultiEvtTransaction() throws android.os.RemoteException;
//SEAPI function
/**
     * is aid allow in the SIM applet
     */
public boolean[] isNFCEventAllowed(java.lang.String reader, byte[] aid, java.lang.String[] packageNames) throws android.os.RemoteException;
/**
     * Enable SWP.
     */
public boolean setNfcSwpActive(int simID) throws android.os.RemoteException;
}
