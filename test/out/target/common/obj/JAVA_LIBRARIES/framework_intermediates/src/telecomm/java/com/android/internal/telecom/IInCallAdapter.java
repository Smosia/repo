/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/telecomm/java/com/android/internal/telecom/IInCallAdapter.aidl
 */
package com.android.internal.telecom;
/**
 * Internal remote callback interface for in-call services.
 *
 * @see android.telecom.InCallAdapter
 *
 * {@hide}
 */
public interface IInCallAdapter extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IInCallAdapter
{
private static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IInCallAdapter";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.android.internal.telecom.IInCallAdapter interface,
 * generating a proxy if needed.
 */
public static com.android.internal.telecom.IInCallAdapter asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.android.internal.telecom.IInCallAdapter))) {
return ((com.android.internal.telecom.IInCallAdapter)iin);
}
return new com.android.internal.telecom.IInCallAdapter.Stub.Proxy(obj);
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
case TRANSACTION_answerCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.answerCall(_arg0, _arg1);
return true;
}
case TRANSACTION_rejectCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
java.lang.String _arg2;
_arg2 = data.readString();
this.rejectCall(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_disconnectCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.disconnectCall(_arg0);
return true;
}
case TRANSACTION_holdCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.holdCall(_arg0);
return true;
}
case TRANSACTION_unholdCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unholdCall(_arg0);
return true;
}
case TRANSACTION_mute:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.mute(_arg0);
return true;
}
case TRANSACTION_setAudioRoute:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setAudioRoute(_arg0);
return true;
}
case TRANSACTION_playDtmfTone:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
char _arg1;
_arg1 = (char)data.readInt();
this.playDtmfTone(_arg0, _arg1);
return true;
}
case TRANSACTION_stopDtmfTone:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.stopDtmfTone(_arg0);
return true;
}
case TRANSACTION_postDialContinue:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.postDialContinue(_arg0, _arg1);
return true;
}
case TRANSACTION_phoneAccountSelected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.telecom.PhoneAccountHandle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.telecom.PhoneAccountHandle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _arg2;
_arg2 = (0!=data.readInt());
this.phoneAccountSelected(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_conference:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.conference(_arg0, _arg1);
return true;
}
case TRANSACTION_splitFromConference:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.splitFromConference(_arg0);
return true;
}
case TRANSACTION_mergeConference:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.mergeConference(_arg0);
return true;
}
case TRANSACTION_swapConference:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.swapConference(_arg0);
return true;
}
case TRANSACTION_turnOnProximitySensor:
{
data.enforceInterface(DESCRIPTOR);
this.turnOnProximitySensor();
return true;
}
case TRANSACTION_turnOffProximitySensor:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.turnOffProximitySensor(_arg0);
return true;
}
case TRANSACTION_startVoiceRecording:
{
data.enforceInterface(DESCRIPTOR);
this.startVoiceRecording();
return true;
}
case TRANSACTION_startVoiceRecordingCustom:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _arg2;
_arg2 = (0!=data.readInt());
this.startVoiceRecordingCustom(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_stopVoiceRecording:
{
data.enforceInterface(DESCRIPTOR);
this.stopVoiceRecording();
return true;
}
case TRANSACTION_setSortedBackgroudCallList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.setSortedBackgroudCallList(_arg0);
return true;
}
case TRANSACTION_setSortedIncomingCallList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.setSortedIncomingCallList(_arg0);
return true;
}
case TRANSACTION_explicitCallTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.explicitCallTransfer(_arg0);
return true;
}
case TRANSACTION_hangupAll:
{
data.enforceInterface(DESCRIPTOR);
this.hangupAll();
return true;
}
case TRANSACTION_hangupAllHoldCalls:
{
data.enforceInterface(DESCRIPTOR);
this.hangupAllHoldCalls();
return true;
}
case TRANSACTION_hangupActiveAndAnswerWaiting:
{
data.enforceInterface(DESCRIPTOR);
this.hangupActiveAndAnswerWaiting();
return true;
}
case TRANSACTION_updatePowerForSmartBook:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.updatePowerForSmartBook(_arg0);
return true;
}
case TRANSACTION_inviteConferenceParticipants:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _arg1;
_arg1 = data.createStringArrayList();
this.inviteConferenceParticipants(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.android.internal.telecom.IInCallAdapter
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
@Override public void answerCall(java.lang.String callId, int videoState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(videoState);
mRemote.transact(Stub.TRANSACTION_answerCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void rejectCall(java.lang.String callId, boolean rejectWithMessage, java.lang.String textMessage) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(((rejectWithMessage)?(1):(0)));
_data.writeString(textMessage);
mRemote.transact(Stub.TRANSACTION_rejectCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void disconnectCall(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_disconnectCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void holdCall(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_holdCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void unholdCall(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_unholdCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void mute(boolean shouldMute) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((shouldMute)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_mute, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void setAudioRoute(int route) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(route);
mRemote.transact(Stub.TRANSACTION_setAudioRoute, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void playDtmfTone(java.lang.String callId, char digit) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(((int)digit));
mRemote.transact(Stub.TRANSACTION_playDtmfTone, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void stopDtmfTone(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_stopDtmfTone, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void postDialContinue(java.lang.String callId, boolean proceed) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(((proceed)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_postDialContinue, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void phoneAccountSelected(java.lang.String callId, android.telecom.PhoneAccountHandle accountHandle, boolean setDefault) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
if ((accountHandle!=null)) {
_data.writeInt(1);
accountHandle.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((setDefault)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_phoneAccountSelected, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void conference(java.lang.String callId, java.lang.String otherCallId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeString(otherCallId);
mRemote.transact(Stub.TRANSACTION_conference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void splitFromConference(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_splitFromConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void mergeConference(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_mergeConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void swapConference(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_swapConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void turnOnProximitySensor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_turnOnProximitySensor, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void turnOffProximitySensor(boolean screenOnImmediately) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((screenOnImmediately)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_turnOffProximitySensor, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void startVoiceRecording() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startVoiceRecording, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
//add DWYQLSSMY-70 songqingming 20160324 (start)

@Override public void startVoiceRecordingCustom(int slot, java.lang.String number, boolean isConference) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slot);
_data.writeString(number);
_data.writeInt(((isConference)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_startVoiceRecordingCustom, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
//add DWYQLSSMY-70 songqingming 20160324 (end)

@Override public void stopVoiceRecording() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopVoiceRecording, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void setSortedBackgroudCallList(java.util.List<java.lang.String> list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(list);
mRemote.transact(Stub.TRANSACTION_setSortedBackgroudCallList, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void setSortedIncomingCallList(java.util.List<java.lang.String> list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(list);
mRemote.transact(Stub.TRANSACTION_setSortedIncomingCallList, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void explicitCallTransfer(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_explicitCallTransfer, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void hangupAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hangupAll, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void hangupAllHoldCalls() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hangupAllHoldCalls, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void hangupActiveAndAnswerWaiting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hangupActiveAndAnswerWaiting, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void updatePowerForSmartBook(boolean onOff) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((onOff)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_updatePowerForSmartBook, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/// M: For VoLTE @{

@Override public void inviteConferenceParticipants(java.lang.String conferenceCallId, java.util.List<java.lang.String> numbers) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(conferenceCallId);
_data.writeStringList(numbers);
mRemote.transact(Stub.TRANSACTION_inviteConferenceParticipants, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_answerCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_rejectCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_disconnectCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_holdCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_unholdCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_mute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setAudioRoute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_playDtmfTone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_stopDtmfTone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_postDialContinue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_phoneAccountSelected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_conference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_splitFromConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_mergeConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_swapConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_turnOnProximitySensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_turnOffProximitySensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_startVoiceRecording = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_startVoiceRecordingCustom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_stopVoiceRecording = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_setSortedBackgroudCallList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setSortedIncomingCallList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_explicitCallTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_hangupAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_hangupAllHoldCalls = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_hangupActiveAndAnswerWaiting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_updatePowerForSmartBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_inviteConferenceParticipants = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
}
public void answerCall(java.lang.String callId, int videoState) throws android.os.RemoteException;
public void rejectCall(java.lang.String callId, boolean rejectWithMessage, java.lang.String textMessage) throws android.os.RemoteException;
public void disconnectCall(java.lang.String callId) throws android.os.RemoteException;
public void holdCall(java.lang.String callId) throws android.os.RemoteException;
public void unholdCall(java.lang.String callId) throws android.os.RemoteException;
public void mute(boolean shouldMute) throws android.os.RemoteException;
public void setAudioRoute(int route) throws android.os.RemoteException;
public void playDtmfTone(java.lang.String callId, char digit) throws android.os.RemoteException;
public void stopDtmfTone(java.lang.String callId) throws android.os.RemoteException;
public void postDialContinue(java.lang.String callId, boolean proceed) throws android.os.RemoteException;
public void phoneAccountSelected(java.lang.String callId, android.telecom.PhoneAccountHandle accountHandle, boolean setDefault) throws android.os.RemoteException;
public void conference(java.lang.String callId, java.lang.String otherCallId) throws android.os.RemoteException;
public void splitFromConference(java.lang.String callId) throws android.os.RemoteException;
public void mergeConference(java.lang.String callId) throws android.os.RemoteException;
public void swapConference(java.lang.String callId) throws android.os.RemoteException;
public void turnOnProximitySensor() throws android.os.RemoteException;
public void turnOffProximitySensor(boolean screenOnImmediately) throws android.os.RemoteException;
public void startVoiceRecording() throws android.os.RemoteException;
//add DWYQLSSMY-70 songqingming 20160324 (start)

public void startVoiceRecordingCustom(int slot, java.lang.String number, boolean isConference) throws android.os.RemoteException;
//add DWYQLSSMY-70 songqingming 20160324 (end)

public void stopVoiceRecording() throws android.os.RemoteException;
public void setSortedBackgroudCallList(java.util.List<java.lang.String> list) throws android.os.RemoteException;
public void setSortedIncomingCallList(java.util.List<java.lang.String> list) throws android.os.RemoteException;
public void explicitCallTransfer(java.lang.String callId) throws android.os.RemoteException;
public void hangupAll() throws android.os.RemoteException;
public void hangupAllHoldCalls() throws android.os.RemoteException;
public void hangupActiveAndAnswerWaiting() throws android.os.RemoteException;
public void updatePowerForSmartBook(boolean onOff) throws android.os.RemoteException;
/// M: For VoLTE @{

public void inviteConferenceParticipants(java.lang.String conferenceCallId, java.util.List<java.lang.String> numbers) throws android.os.RemoteException;
}