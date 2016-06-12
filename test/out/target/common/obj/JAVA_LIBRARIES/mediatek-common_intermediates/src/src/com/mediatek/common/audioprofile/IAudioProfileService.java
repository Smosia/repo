/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/audioprofile/IAudioProfileService.aidl
 */
package com.mediatek.common.audioprofile;
/**
 * {@hide}
 */
public interface IAudioProfileService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.audioprofile.IAudioProfileService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.audioprofile.IAudioProfileService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.audioprofile.IAudioProfileService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.audioprofile.IAudioProfileService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.audioprofile.IAudioProfileService))) {
return ((com.mediatek.common.audioprofile.IAudioProfileService)iin);
}
return new com.mediatek.common.audioprofile.IAudioProfileService.Stub.Proxy(obj);
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
case TRANSACTION_setActiveProfile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setActiveProfile(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addProfile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.addProfile();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_deleteProfile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.deleteProfile(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_reset:
{
data.enforceInterface(DESCRIPTOR);
this.reset();
reply.writeNoException();
return true;
}
case TRANSACTION_getProfileCount:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getProfileCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getAllProfileKeys:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getAllProfileKeys();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getPredefinedProfileKeys:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getPredefinedProfileKeys();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getCustomizedProfileKeys:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getCustomizedProfileKeys();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_isNameExist:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isNameExist(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getActiveProfileKey:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getActiveProfileKey();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getLastActiveProfileKey:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getLastActiveProfileKey();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getRingtoneUri:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
android.net.Uri _result = this.getRingtoneUri(_arg0, _arg1);
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
case TRANSACTION_getRingtoneUriWithSIM:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
long _arg2;
_arg2 = data.readLong();
android.net.Uri _result = this.getRingtoneUriWithSIM(_arg0, _arg1, _arg2);
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
case TRANSACTION_getStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.getStreamVolume(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isVibrationEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isVibrationEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isDtmfToneEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isDtmfToneEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isSoundEffectEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isSoundEffectEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isLockScreenEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isLockScreenEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isVibrateOnTouchEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isVibrateOnTouchEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getProfileStateString:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _result = this.getProfileStateString(_arg0);
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getProfileName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getProfileName(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setRingtoneUri:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
long _arg2;
_arg2 = data.readLong();
android.net.Uri _arg3;
if ((0!=data.readInt())) {
_arg3 = android.net.Uri.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
this.setRingtoneUri(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_setStreamVolume:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setStreamVolume(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setVibrationEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setVibrationEnabled(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setDtmfToneEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setDtmfToneEnabled(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setSoundEffectEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setSoundEffectEnabled(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setLockScreenEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setLockScreenEnabled(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setVibrateOnTouchEnabled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setVibrateOnTouchEnabled(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setProfileName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.setProfileName(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setUserId:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setUserId(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isActiveProfile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isActiveProfile(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isRingtoneExist:
{
data.enforceInterface(DESCRIPTOR);
android.net.Uri _arg0;
if ((0!=data.readInt())) {
_arg0 = android.net.Uri.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.isRingtoneExist(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getStreamMaxVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getStreamMaxVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDefaultRingtone:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.net.Uri _result = this.getDefaultRingtone(_arg0);
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
case TRANSACTION_getBesSurroundState:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getBesSurroundState();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBesSurroundState:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
int _result = this.setBesSurroundState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getBesSurroundMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBesSurroundMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBesSurroundMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.setBesSurroundMode(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_listenAudioProfie:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.audioprofile.IAudioProfileListener _arg0;
_arg0 = com.mediatek.common.audioprofile.IAudioProfileListener.Stub.asInterface(data.readStrongBinder());
int _arg1;
_arg1 = data.readInt();
this.listenAudioProfie(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.audioprofile.IAudioProfileService
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
@Override public void setActiveProfile(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_setActiveProfile, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String addProfile() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_addProfile, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean deleteProfile(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_deleteProfile, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void reset() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_reset, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getProfileCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getProfileCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getAllProfileKeys() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAllProfileKeys, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getPredefinedProfileKeys() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPredefinedProfileKeys, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getCustomizedProfileKeys() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCustomizedProfileKeys, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isNameExist(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_isNameExist, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getActiveProfileKey() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getActiveProfileKey, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getLastActiveProfileKey() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastActiveProfileKey, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.net.Uri getRingtoneUri(java.lang.String profileKey, int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.Uri _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_getRingtoneUri, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.net.Uri.CREATOR.createFromParcel(_reply);
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
@Override public android.net.Uri getRingtoneUriWithSIM(java.lang.String profileKey, int type, long simId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.Uri _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(type);
_data.writeLong(simId);
mRemote.transact(Stub.TRANSACTION_getRingtoneUriWithSIM, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.net.Uri.CREATOR.createFromParcel(_reply);
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
@Override public int getStreamVolume(java.lang.String profileKey, int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getStreamVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isVibrationEnabled(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isVibrationEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isDtmfToneEnabled(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isDtmfToneEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isSoundEffectEnabled(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isSoundEffectEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isLockScreenEnabled(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isLockScreenEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isVibrateOnTouchEnabled(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isVibrateOnTouchEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getProfileStateString(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_getProfileStateString, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getProfileName(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_getProfileName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setRingtoneUri(java.lang.String profileKey, int type, long simId, android.net.Uri ringtoneUri) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(type);
_data.writeLong(simId);
if ((ringtoneUri!=null)) {
_data.writeInt(1);
ringtoneUri.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setRingtoneUri, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setStreamVolume(java.lang.String profileKey, int streamType, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(streamType);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_setStreamVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setVibrationEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setVibrationEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setDtmfToneEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setDtmfToneEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSoundEffectEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setSoundEffectEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setLockScreenEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setLockScreenEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setVibrateOnTouchEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setVibrateOnTouchEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setProfileName(java.lang.String profileKey, java.lang.String newName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
_data.writeString(newName);
mRemote.transact(Stub.TRANSACTION_setProfileName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUserId(int userId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(userId);
mRemote.transact(Stub.TRANSACTION_setUserId, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isActiveProfile(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_isActiveProfile, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isRingtoneExist(android.net.Uri uri) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((uri!=null)) {
_data.writeInt(1);
uri.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_isRingtoneExist, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getStreamMaxVolume(int streamType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(streamType);
mRemote.transact(Stub.TRANSACTION_getStreamMaxVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.net.Uri getDefaultRingtone(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.Uri _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_getDefaultRingtone, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.net.Uri.CREATOR.createFromParcel(_reply);
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
@Override public boolean getBesSurroundState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBesSurroundState, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setBesSurroundState(boolean isEnableBes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isEnableBes)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBesSurroundState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getBesSurroundMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBesSurroundMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setBesSurroundMode(int besSurroundMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(besSurroundMode);
mRemote.transact(Stub.TRANSACTION_setBesSurroundMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void listenAudioProfie(com.mediatek.common.audioprofile.IAudioProfileListener callback, int event) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
_data.writeInt(event);
mRemote.transact(Stub.TRANSACTION_listenAudioProfie, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_setActiveProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_addProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_deleteProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_reset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getProfileCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getAllProfileKeys = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getPredefinedProfileKeys = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getCustomizedProfileKeys = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_isNameExist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getActiveProfileKey = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getLastActiveProfileKey = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getRingtoneUri = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getRingtoneUriWithSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_isVibrationEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_isDtmfToneEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_isSoundEffectEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_isLockScreenEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_isVibrateOnTouchEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getProfileStateString = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getProfileName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setRingtoneUri = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_setStreamVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setVibrationEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_setDtmfToneEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_setSoundEffectEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_setLockScreenEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_setVibrateOnTouchEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_setProfileName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_setUserId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_isActiveProfile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_isRingtoneExist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getStreamMaxVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getDefaultRingtone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getBesSurroundState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_setBesSurroundState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_getBesSurroundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_setBesSurroundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_listenAudioProfie = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
}
public void setActiveProfile(java.lang.String profileKey) throws android.os.RemoteException;
public java.lang.String addProfile() throws android.os.RemoteException;
public boolean deleteProfile(java.lang.String profileKey) throws android.os.RemoteException;
public void reset() throws android.os.RemoteException;
public int getProfileCount() throws android.os.RemoteException;
public java.util.List<java.lang.String> getAllProfileKeys() throws android.os.RemoteException;
public java.util.List<java.lang.String> getPredefinedProfileKeys() throws android.os.RemoteException;
public java.util.List<java.lang.String> getCustomizedProfileKeys() throws android.os.RemoteException;
public boolean isNameExist(java.lang.String name) throws android.os.RemoteException;
public java.lang.String getActiveProfileKey() throws android.os.RemoteException;
public java.lang.String getLastActiveProfileKey() throws android.os.RemoteException;
public android.net.Uri getRingtoneUri(java.lang.String profileKey, int type) throws android.os.RemoteException;
public android.net.Uri getRingtoneUriWithSIM(java.lang.String profileKey, int type, long simId) throws android.os.RemoteException;
public int getStreamVolume(java.lang.String profileKey, int streamType) throws android.os.RemoteException;
public boolean isVibrationEnabled(java.lang.String profileKey) throws android.os.RemoteException;
public boolean isDtmfToneEnabled(java.lang.String profileKey) throws android.os.RemoteException;
public boolean isSoundEffectEnabled(java.lang.String profileKey) throws android.os.RemoteException;
public boolean isLockScreenEnabled(java.lang.String profileKey) throws android.os.RemoteException;
public boolean isVibrateOnTouchEnabled(java.lang.String profileKey) throws android.os.RemoteException;
public java.util.List<java.lang.String> getProfileStateString(java.lang.String profileKey) throws android.os.RemoteException;
public java.lang.String getProfileName(java.lang.String profileKey) throws android.os.RemoteException;
public void setRingtoneUri(java.lang.String profileKey, int type, long simId, android.net.Uri ringtoneUri) throws android.os.RemoteException;
public void setStreamVolume(java.lang.String profileKey, int streamType, int index) throws android.os.RemoteException;
public void setVibrationEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException;
public void setDtmfToneEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException;
public void setSoundEffectEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException;
public void setLockScreenEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException;
public void setVibrateOnTouchEnabled(java.lang.String profileKey, boolean enabled) throws android.os.RemoteException;
public void setProfileName(java.lang.String profileKey, java.lang.String newName) throws android.os.RemoteException;
public void setUserId(int userId) throws android.os.RemoteException;
public boolean isActiveProfile(java.lang.String profileKey) throws android.os.RemoteException;
public boolean isRingtoneExist(android.net.Uri uri) throws android.os.RemoteException;
public int getStreamMaxVolume(int streamType) throws android.os.RemoteException;
public android.net.Uri getDefaultRingtone(int type) throws android.os.RemoteException;
public boolean getBesSurroundState() throws android.os.RemoteException;
public int setBesSurroundState(boolean isEnableBes) throws android.os.RemoteException;
public int getBesSurroundMode() throws android.os.RemoteException;
public int setBesSurroundMode(int besSurroundMode) throws android.os.RemoteException;
public void listenAudioProfie(com.mediatek.common.audioprofile.IAudioProfileListener callback, int event) throws android.os.RemoteException;
}
