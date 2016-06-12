/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/telecomm/java/com/android/internal/telecom/IConnectionService.aidl
 */
package com.android.internal.telecom;
/**
 * Internal remote interface for connection services.
 *
 * @see android.telecom.ConnectionService
 *
 * @hide
 */
public interface IConnectionService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IConnectionService
{
private static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IConnectionService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.android.internal.telecom.IConnectionService interface,
 * generating a proxy if needed.
 */
public static com.android.internal.telecom.IConnectionService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.android.internal.telecom.IConnectionService))) {
return ((com.android.internal.telecom.IConnectionService)iin);
}
return new com.android.internal.telecom.IConnectionService.Stub.Proxy(obj);
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
case TRANSACTION_addConnectionServiceAdapter:
{
data.enforceInterface(DESCRIPTOR);
com.android.internal.telecom.IConnectionServiceAdapter _arg0;
_arg0 = com.android.internal.telecom.IConnectionServiceAdapter.Stub.asInterface(data.readStrongBinder());
this.addConnectionServiceAdapter(_arg0);
return true;
}
case TRANSACTION_removeConnectionServiceAdapter:
{
data.enforceInterface(DESCRIPTOR);
com.android.internal.telecom.IConnectionServiceAdapter _arg0;
_arg0 = com.android.internal.telecom.IConnectionServiceAdapter.Stub.asInterface(data.readStrongBinder());
this.removeConnectionServiceAdapter(_arg0);
return true;
}
case TRANSACTION_createConnection:
{
data.enforceInterface(DESCRIPTOR);
android.telecom.PhoneAccountHandle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.telecom.PhoneAccountHandle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
android.telecom.ConnectionRequest _arg2;
if ((0!=data.readInt())) {
_arg2 = android.telecom.ConnectionRequest.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
boolean _arg3;
_arg3 = (0!=data.readInt());
boolean _arg4;
_arg4 = (0!=data.readInt());
this.createConnection(_arg0, _arg1, _arg2, _arg3, _arg4);
return true;
}
case TRANSACTION_abort:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.abort(_arg0);
return true;
}
case TRANSACTION_answerVideo:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.answerVideo(_arg0, _arg1);
return true;
}
case TRANSACTION_answer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.answer(_arg0);
return true;
}
case TRANSACTION_reject:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.reject(_arg0);
return true;
}
case TRANSACTION_disconnect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.disconnect(_arg0);
return true;
}
case TRANSACTION_hold:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.hold(_arg0);
return true;
}
case TRANSACTION_unhold:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unhold(_arg0);
return true;
}
case TRANSACTION_onCallAudioStateChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.telecom.CallAudioState _arg1;
if ((0!=data.readInt())) {
_arg1 = android.telecom.CallAudioState.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onCallAudioStateChanged(_arg0, _arg1);
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
case TRANSACTION_swapWithBackgroundCall:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.swapWithBackgroundCall(_arg0);
return true;
}
case TRANSACTION_holdWithPendingCallAction:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.holdWithPendingCallAction(_arg0, _arg1);
return true;
}
case TRANSACTION_disconnectWithPendingCallAction:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.disconnectWithPendingCallAction(_arg0, _arg1);
return true;
}
case TRANSACTION_rejectWithCause:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.rejectWithCause(_arg0, _arg1);
return true;
}
case TRANSACTION_hangupAll:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.hangupAll(_arg0);
return true;
}
case TRANSACTION_onPostDialContinue:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onPostDialContinue(_arg0, _arg1);
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
case TRANSACTION_createConference:
{
data.enforceInterface(DESCRIPTOR);
android.telecom.PhoneAccountHandle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.telecom.PhoneAccountHandle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
android.telecom.ConnectionRequest _arg2;
if ((0!=data.readInt())) {
_arg2 = android.telecom.ConnectionRequest.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
java.util.List<java.lang.String> _arg3;
_arg3 = data.createStringArrayList();
boolean _arg4;
_arg4 = (0!=data.readInt());
this.createConference(_arg0, _arg1, _arg2, _arg3, _arg4);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.android.internal.telecom.IConnectionService
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
@Override public void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter adapter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((adapter!=null))?(adapter.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addConnectionServiceAdapter, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter adapter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((adapter!=null))?(adapter.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeConnectionServiceAdapter, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void createConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, java.lang.String callId, android.telecom.ConnectionRequest request, boolean isIncoming, boolean isUnknown) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((connectionManagerPhoneAccount!=null)) {
_data.writeInt(1);
connectionManagerPhoneAccount.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(callId);
if ((request!=null)) {
_data.writeInt(1);
request.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((isIncoming)?(1):(0)));
_data.writeInt(((isUnknown)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_createConnection, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void abort(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_abort, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void answerVideo(java.lang.String callId, int videoState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(videoState);
mRemote.transact(Stub.TRANSACTION_answerVideo, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void answer(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_answer, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void reject(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_reject, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void disconnect(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_disconnect, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void hold(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_hold, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void unhold(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_unhold, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onCallAudioStateChanged(java.lang.String activeCallId, android.telecom.CallAudioState callAudioState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(activeCallId);
if ((callAudioState!=null)) {
_data.writeInt(1);
callAudioState.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onCallAudioStateChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
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
@Override public void conference(java.lang.String conferenceCallId, java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(conferenceCallId);
_data.writeString(callId);
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
@Override public void mergeConference(java.lang.String conferenceCallId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(conferenceCallId);
mRemote.transact(Stub.TRANSACTION_mergeConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void swapConference(java.lang.String conferenceCallId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(conferenceCallId);
mRemote.transact(Stub.TRANSACTION_swapConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/* M: call control part start */
@Override public void swapWithBackgroundCall(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_swapWithBackgroundCall, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/// M: CC078: For DSDS/DSDA Two-action operation @{

@Override public void holdWithPendingCallAction(java.lang.String callId, java.lang.String pendingCallAction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeString(pendingCallAction);
mRemote.transact(Stub.TRANSACTION_holdWithPendingCallAction, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void disconnectWithPendingCallAction(java.lang.String callId, java.lang.String pendingCallAction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeString(pendingCallAction);
mRemote.transact(Stub.TRANSACTION_disconnectWithPendingCallAction, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/// @}

@Override public void rejectWithCause(java.lang.String callId, int cause) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(cause);
mRemote.transact(Stub.TRANSACTION_rejectWithCause, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void hangupAll(java.lang.String callId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
mRemote.transact(Stub.TRANSACTION_hangupAll, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/* M: call control part end */
@Override public void onPostDialContinue(java.lang.String callId, boolean proceed) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(callId);
_data.writeInt(((proceed)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onPostDialContinue, _data, null, android.os.IBinder.FLAG_ONEWAY);
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
@Override public void createConference(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, java.lang.String conferenceCallId, android.telecom.ConnectionRequest request, java.util.List<java.lang.String> numbers, boolean isIncoming) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((connectionManagerPhoneAccount!=null)) {
_data.writeInt(1);
connectionManagerPhoneAccount.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(conferenceCallId);
if ((request!=null)) {
_data.writeInt(1);
request.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStringList(numbers);
_data.writeInt(((isIncoming)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_createConference, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_addConnectionServiceAdapter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_removeConnectionServiceAdapter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_createConnection = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_abort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_answerVideo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_answer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_reject = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_disconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_hold = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_unhold = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_onCallAudioStateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_playDtmfTone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_stopDtmfTone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_conference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_splitFromConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_mergeConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_swapConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_swapWithBackgroundCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_holdWithPendingCallAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_disconnectWithPendingCallAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_rejectWithCause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_hangupAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_onPostDialContinue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_explicitCallTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_inviteConferenceParticipants = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_createConference = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
}
public void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter adapter) throws android.os.RemoteException;
public void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter adapter) throws android.os.RemoteException;
public void createConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, java.lang.String callId, android.telecom.ConnectionRequest request, boolean isIncoming, boolean isUnknown) throws android.os.RemoteException;
public void abort(java.lang.String callId) throws android.os.RemoteException;
public void answerVideo(java.lang.String callId, int videoState) throws android.os.RemoteException;
public void answer(java.lang.String callId) throws android.os.RemoteException;
public void reject(java.lang.String callId) throws android.os.RemoteException;
public void disconnect(java.lang.String callId) throws android.os.RemoteException;
public void hold(java.lang.String callId) throws android.os.RemoteException;
public void unhold(java.lang.String callId) throws android.os.RemoteException;
public void onCallAudioStateChanged(java.lang.String activeCallId, android.telecom.CallAudioState callAudioState) throws android.os.RemoteException;
public void playDtmfTone(java.lang.String callId, char digit) throws android.os.RemoteException;
public void stopDtmfTone(java.lang.String callId) throws android.os.RemoteException;
public void conference(java.lang.String conferenceCallId, java.lang.String callId) throws android.os.RemoteException;
public void splitFromConference(java.lang.String callId) throws android.os.RemoteException;
public void mergeConference(java.lang.String conferenceCallId) throws android.os.RemoteException;
public void swapConference(java.lang.String conferenceCallId) throws android.os.RemoteException;
/* M: call control part start */
public void swapWithBackgroundCall(java.lang.String callId) throws android.os.RemoteException;
/// M: CC078: For DSDS/DSDA Two-action operation @{

public void holdWithPendingCallAction(java.lang.String callId, java.lang.String pendingCallAction) throws android.os.RemoteException;
public void disconnectWithPendingCallAction(java.lang.String callId, java.lang.String pendingCallAction) throws android.os.RemoteException;
/// @}

public void rejectWithCause(java.lang.String callId, int cause) throws android.os.RemoteException;
public void hangupAll(java.lang.String callId) throws android.os.RemoteException;
/* M: call control part end */
public void onPostDialContinue(java.lang.String callId, boolean proceed) throws android.os.RemoteException;
public void explicitCallTransfer(java.lang.String callId) throws android.os.RemoteException;
/// M: For VoLTE @{

public void inviteConferenceParticipants(java.lang.String conferenceCallId, java.util.List<java.lang.String> numbers) throws android.os.RemoteException;
public void createConference(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, java.lang.String conferenceCallId, android.telecom.ConnectionRequest request, java.util.List<java.lang.String> numbers, boolean isIncoming) throws android.os.RemoteException;
}
