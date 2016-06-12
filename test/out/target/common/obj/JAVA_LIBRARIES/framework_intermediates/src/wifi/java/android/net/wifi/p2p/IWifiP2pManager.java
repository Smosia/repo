/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/wifi/java/android/net/wifi/p2p/IWifiP2pManager.aidl
 */
package android.net.wifi.p2p;
/**
 * Interface that WifiP2pService implements
 *
 * {@hide}
 */
public interface IWifiP2pManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.net.wifi.p2p.IWifiP2pManager
{
private static final java.lang.String DESCRIPTOR = "android.net.wifi.p2p.IWifiP2pManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.net.wifi.p2p.IWifiP2pManager interface,
 * generating a proxy if needed.
 */
public static android.net.wifi.p2p.IWifiP2pManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.net.wifi.p2p.IWifiP2pManager))) {
return ((android.net.wifi.p2p.IWifiP2pManager)iin);
}
return new android.net.wifi.p2p.IWifiP2pManager.Stub.Proxy(obj);
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
case TRANSACTION_getMessenger:
{
data.enforceInterface(DESCRIPTOR);
android.os.Messenger _result = this.getMessenger();
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
case TRANSACTION_getP2pStateMachineMessenger:
{
data.enforceInterface(DESCRIPTOR);
android.os.Messenger _result = this.getP2pStateMachineMessenger();
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
case TRANSACTION_setMiracastMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setMiracastMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setMiracastModeEx:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setMiracastModeEx(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_fastConnectAsGo:
{
data.enforceInterface(DESCRIPTOR);
android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo _result = this.fastConnectAsGo(_arg0);
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
case TRANSACTION_getMacAddress:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getMacAddress();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getPeerIpAddress:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getPeerIpAddress(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getP2pHandoverSelectToken:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getP2pHandoverSelectToken();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.net.wifi.p2p.IWifiP2pManager
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
@Override public android.os.Messenger getMessenger() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Messenger _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMessenger, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Messenger.CREATOR.createFromParcel(_reply);
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
@Override public android.os.Messenger getP2pStateMachineMessenger() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Messenger _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getP2pStateMachineMessenger, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Messenger.CREATOR.createFromParcel(_reply);
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
@Override public void setMiracastMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setMiracastMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
///M: Add by MTK  @{

@Override public void setMiracastModeEx(int mode, int freq) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
_data.writeInt(freq);
mRemote.transact(Stub.TRANSACTION_setMiracastModeEx, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo fastConnectAsGo(android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((info!=null)) {
_data.writeInt(1);
info.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_fastConnectAsGo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo.CREATOR.createFromParcel(_reply);
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
@Override public java.lang.String getMacAddress() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMacAddress, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getPeerIpAddress(java.lang.String peerMacAddress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(peerMacAddress);
mRemote.transact(Stub.TRANSACTION_getPeerIpAddress, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public byte[] getP2pHandoverSelectToken() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getP2pHandoverSelectToken, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getMessenger = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getP2pStateMachineMessenger = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setMiracastMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setMiracastModeEx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_fastConnectAsGo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getMacAddress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getPeerIpAddress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getP2pHandoverSelectToken = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
public android.os.Messenger getMessenger() throws android.os.RemoteException;
public android.os.Messenger getP2pStateMachineMessenger() throws android.os.RemoteException;
public void setMiracastMode(int mode) throws android.os.RemoteException;
///M: Add by MTK  @{

public void setMiracastModeEx(int mode, int freq) throws android.os.RemoteException;
public android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo fastConnectAsGo(android.net.wifi.p2p.fastconnect.WifiP2pFastConnectInfo info) throws android.os.RemoteException;
public java.lang.String getMacAddress() throws android.os.RemoteException;
public java.lang.String getPeerIpAddress(java.lang.String peerMacAddress) throws android.os.RemoteException;
public byte[] getP2pHandoverSelectToken() throws android.os.RemoteException;
}
