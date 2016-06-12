/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/telephony/java/com/mediatek/internal/telephony/ITelephonyEx.aidl
 */
package com.mediatek.internal.telephony;
/**
 * Interface used to interact with the phone.  Mostly this is used by the
 * TelephonyManager class.  A few places are still using this directly.
 * Please clean them up if possible and use TelephonyManager insteadl.
 *
 * {@hide}
 */
public interface ITelephonyEx extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.internal.telephony.ITelephonyEx
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.internal.telephony.ITelephonyEx";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.internal.telephony.ITelephonyEx interface,
 * generating a proxy if needed.
 */
public static com.mediatek.internal.telephony.ITelephonyEx asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.internal.telephony.ITelephonyEx))) {
return ((com.mediatek.internal.telephony.ITelephonyEx)iin);
}
return new com.mediatek.internal.telephony.ITelephonyEx.Stub.Proxy(obj);
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
case TRANSACTION_queryNetworkLock:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.os.Bundle _result = this.queryNetworkLock(_arg0, _arg1);
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
case TRANSACTION_supplyNetworkDepersonalization:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.supplyNetworkDepersonalization(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_repollIccStateForNetworkLock:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.repollIccStateForNetworkLock(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setLine1Number:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.setLine1Number(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isFdnEnabled:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isFdnEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getIccCardType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getIccCardType(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getSvlteCardType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getSvlteCardType(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isAppTypeSupported:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.isAppTypeSupported(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isTestIccCard:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isTestIccCard(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getMvnoMatchType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getMvnoMatchType(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getMvnoPattern:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _result = this.getMvnoPattern(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getNetworkOperatorNameGemini:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getNetworkOperatorNameGemini(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getNetworkOperatorNameUsingSub:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getNetworkOperatorNameUsingSub(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getNetworkOperatorGemini:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getNetworkOperatorGemini(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getNetworkOperatorUsingSub:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getNetworkOperatorUsingSub(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_btSimapConnectSIM:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.internal.telephony.BtSimapOperResponse _arg1;
_arg1 = new com.mediatek.internal.telephony.BtSimapOperResponse();
int _result = this.btSimapConnectSIM(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg1!=null)) {
reply.writeInt(1);
_arg1.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_btSimapDisconnectSIM:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.btSimapDisconnectSIM();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_btSimapApduRequest:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
com.mediatek.internal.telephony.BtSimapOperResponse _arg2;
_arg2 = new com.mediatek.internal.telephony.BtSimapOperResponse();
int _result = this.btSimapApduRequest(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg2!=null)) {
reply.writeInt(1);
_arg2.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_btSimapResetSIM:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.internal.telephony.BtSimapOperResponse _arg1;
_arg1 = new com.mediatek.internal.telephony.BtSimapOperResponse();
int _result = this.btSimapResetSIM(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg1!=null)) {
reply.writeInt(1);
_arg1.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_btSimapPowerOnSIM:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.internal.telephony.BtSimapOperResponse _arg1;
_arg1 = new com.mediatek.internal.telephony.BtSimapOperResponse();
int _result = this.btSimapPowerOnSIM(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg1!=null)) {
reply.writeInt(1);
_arg1.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_btSimapPowerOffSIM:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.btSimapPowerOffSIM();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_simAkaAuthentication:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
byte[] _arg2;
_arg2 = data.createByteArray();
byte[] _arg3;
_arg3 = data.createByteArray();
byte[] _result = this.simAkaAuthentication(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_simGbaAuthBootStrapMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
byte[] _arg2;
_arg2 = data.createByteArray();
byte[] _arg3;
_arg3 = data.createByteArray();
byte[] _result = this.simGbaAuthBootStrapMode(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_simGbaAuthNafMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
byte[] _arg2;
_arg2 = data.createByteArray();
byte[] _arg3;
_arg3 = data.createByteArray();
byte[] _result = this.simGbaAuthNafMode(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_broadcastIccUnlockIntent:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.broadcastIccUnlockIntent(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isRadioOffBySimManagement:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isRadioOffBySimManagement(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPhoneCapability:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getPhoneCapability(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPhoneCapability:
{
data.enforceInterface(DESCRIPTOR);
int[] _arg0;
_arg0 = data.createIntArray();
int[] _arg1;
_arg1 = data.createIntArray();
this.setPhoneCapability(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_configSimSwap:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.configSimSwap(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isSimSwapped:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSimSwapped();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isCapSwitchManualEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isCapSwitchManualEnabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getCapSwitchManualList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getCapSwitchManualList();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getLocatedPlmn:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getLocatedPlmn(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getNetworkHideState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getNetworkHideState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getServiceState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.Bundle _result = this.getServiceState(_arg0);
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
case TRANSACTION_getAdnStorageInfo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int[] _result = this.getAdnStorageInfo(_arg0);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_isPhbReady:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isPhbReady(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getScAddressUsingSubId:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.Bundle _result = this.getScAddressUsingSubId(_arg0);
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
case TRANSACTION_setScAddressUsingSubId:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.setScAddressUsingSubId(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isAirplanemodeAvailableNow:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isAirplanemodeAvailableNow();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getLastDataConnectionFailCause:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.getLastDataConnectionFailCause(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getLinkProperties:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
android.net.LinkProperties _result = this.getLinkProperties(_arg0, _arg1);
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
case TRANSACTION_setRadioCapability:
{
data.enforceInterface(DESCRIPTOR);
android.telephony.RadioAccessFamily[] _arg0;
_arg0 = data.createTypedArray(android.telephony.RadioAccessFamily.CREATOR);
boolean _result = this.setRadioCapability(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isCapabilitySwitching:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isCapabilitySwitching();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchSvlteRatMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.switchSvlteRatMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSvlteRatMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setSvlteRatMode(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getSvlteServiceState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.Bundle _result = this.getSvlteServiceState(_arg0);
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
case TRANSACTION_switchRadioTechnology:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.switchRadioTechnology(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setRadioTechnology:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setRadioTechnology(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setTrmForPhone:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setTrmForPhone(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getSubscriberIdForLteDcPhone:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getSubscriberIdForLteDcPhone(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getSvlteImei:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getSvlteImei(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getMainCapabilityPhoneId:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMainCapabilityPhoneId();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isAllowAirplaneModeChange:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isAllowAirplaneModeChange();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_initializeService:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.initializeService(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_finalizeService:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.finalizeService(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isInHomeNetwork:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isInHomeNetwork(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setLteAccessStratumReport:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setLteAccessStratumReport(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setLteUplinkDataTransfer:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
int _arg1;
_arg1 = data.readInt();
boolean _result = this.setLteUplinkDataTransfer(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getLteAccessStratumState:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getLteAccessStratumState();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_isSharedDefaultApn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSharedDefaultApn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSvlteMeid:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _result = this.getSvlteMeid(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_isImsRegistered:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isImsRegistered(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isVolteEnabled:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isVolteEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isWifiCallingEnabled:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isWifiCallingEnabled(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getMeid:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getMeid();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setEccInProgress:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setEccInProgress(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isEccInProgress:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isEccInProgress();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.internal.telephony.ITelephonyEx
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
@Override public android.os.Bundle queryNetworkLock(int subId, int category) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(category);
mRemote.transact(Stub.TRANSACTION_queryNetworkLock, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
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
@Override public int supplyNetworkDepersonalization(int subId, java.lang.String strPasswd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(strPasswd);
mRemote.transact(Stub.TRANSACTION_supplyNetworkDepersonalization, _data, _reply, 0);
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
     * Modem SML change feature.
     * This function will query the SIM state of the given slot. And broadcast
     * ACTION_UNLOCK_SIM_LOCK if the SIM state is in network lock.
     *
     * @param subId: Indicate which sub to query
     * @param needIntent: The caller can deside to broadcast ACTION_UNLOCK_SIM_LOCK or not
     *                    in this time, because some APs will receive this intent (eg. Keyguard).
     *                    That can avoid this intent to effect other AP.
     */
@Override public void repollIccStateForNetworkLock(int subId, boolean needIntent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(((needIntent)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_repollIccStateForNetworkLock, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int setLine1Number(int subId, java.lang.String alphaTag, java.lang.String number) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(alphaTag);
_data.writeString(number);
mRemote.transact(Stub.TRANSACTION_setLine1Number, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isFdnEnabled(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isFdnEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getIccCardType(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getIccCardType, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Request to get UICC card type.
     *
     * @param slotId indicated sim id
     *
     * @return index for UICC card type
     *
     */
@Override public int getSvlteCardType(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_getSvlteCardType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isAppTypeSupported(int slotId, int appType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
_data.writeInt(appType);
mRemote.transact(Stub.TRANSACTION_isAppTypeSupported, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isTestIccCard(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_isTestIccCard, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getMvnoMatchType(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getMvnoMatchType, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getMvnoPattern(int subId, java.lang.String type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(type);
mRemote.transact(Stub.TRANSACTION_getMvnoPattern, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getNetworkOperatorNameGemini(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_getNetworkOperatorNameGemini, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getNetworkOperatorNameUsingSub(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getNetworkOperatorNameUsingSub, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getNetworkOperatorGemini(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_getNetworkOperatorGemini, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getNetworkOperatorUsingSub(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getNetworkOperatorUsingSub, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     *send BT SIM profile of Connect SIM
     * @param simId specify which SIM to connect
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
@Override public int btSimapConnectSIM(int simId, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(simId);
mRemote.transact(Stub.TRANSACTION_btSimapConnectSIM, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
btRsp.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     *send BT SIM profile of Disconnect SIM
     * @param null
     * @return success or error code.
     */
@Override public int btSimapDisconnectSIM() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_btSimapDisconnectSIM, _data, _reply, 0);
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
     *Transfer APDU data through BT SAP
     * @param type Indicate which transport protocol is the preferred one
     * @param cmdAPDU APDU data to transfer in hex character format
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
@Override public int btSimapApduRequest(int type, java.lang.String cmdAPDU, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeString(cmdAPDU);
mRemote.transact(Stub.TRANSACTION_btSimapApduRequest, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
btRsp.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     *send BT SIM profile of Reset SIM
     * @param type Indicate which transport protocol is the preferred one
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
@Override public int btSimapResetSIM(int type, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_btSimapResetSIM, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
btRsp.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     *send BT SIM profile of Power On SIM
     * @param type Indicate which transport protocol is the preferred onet
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
@Override public int btSimapPowerOnSIM(int type, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_btSimapPowerOnSIM, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
btRsp.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     *send BT SIM profile of PowerOff SIM
     * @return success or error code.
     */
@Override public int btSimapPowerOffSIM() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_btSimapPowerOffSIM, _data, _reply, 0);
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
     * Request to run AKA authenitcation on UICC card by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteRand random challenge in byte array
     * @param byteAutn authenication token in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
@Override public byte[] simAkaAuthentication(int slotId, int family, byte[] byteRand, byte[] byteAutn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
_data.writeInt(family);
_data.writeByteArray(byteRand);
_data.writeByteArray(byteAutn);
mRemote.transact(Stub.TRANSACTION_simAkaAuthentication, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Request to run GBA authenitcation (Bootstrapping Mode)on UICC card
     * by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteRand random challenge in byte array
     * @param byteAutn authenication token in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
@Override public byte[] simGbaAuthBootStrapMode(int slotId, int family, byte[] byteRand, byte[] byteAutn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
_data.writeInt(family);
_data.writeByteArray(byteRand);
_data.writeByteArray(byteAutn);
mRemote.transact(Stub.TRANSACTION_simGbaAuthBootStrapMode, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Request to run GBA authenitcation (NAF Derivation Mode)on UICC card
     * by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteNafId network application function id in byte array
     * @param byteImpi IMS private user identity in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
@Override public byte[] simGbaAuthNafMode(int slotId, int family, byte[] byteNafId, byte[] byteImpi) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
_data.writeInt(family);
_data.writeByteArray(byteNafId);
_data.writeByteArray(byteImpi);
mRemote.transact(Stub.TRANSACTION_simGbaAuthNafMode, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Since MTK keyguard has dismiss feature, we need to retrigger unlock event
     * when user try to access the SIM card.
     *
     * @param subId inidicated subscription
     *
     * @return true represent broadcast a unlock intent to notify keyguard
     *         false represent current state is not LOCKED state. No need to retrigger.
     *
     */
@Override public boolean broadcastIccUnlockIntent(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_broadcastIccUnlockIntent, _data, _reply, 0);
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
     * Query if the radio is turned off by user.
     *
     * @param subId inidicated subscription
     *
     * @return true radio is turned off by user.
     *         false radio isn't turned off by user.
     *
     */
@Override public boolean isRadioOffBySimManagement(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isRadioOffBySimManagement, _data, _reply, 0);
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
     * Get current phone capability
     *
     * @return the capability of phone. (@see PhoneConstants)
     * @internal
     */
@Override public int getPhoneCapability(int phoneId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(phoneId);
mRemote.transact(Stub.TRANSACTION_getPhoneCapability, _data, _reply, 0);
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
     * Set capability to phones
     *
     * @param phoneId phones want to change capability
     * @param capability new capability for each phone
     * @internal
     */
@Override public void setPhoneCapability(int[] phoneId, int[] capability) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeIntArray(phoneId);
_data.writeIntArray(capability);
mRemote.transact(Stub.TRANSACTION_setPhoneCapability, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * To config SIM swap mode(for dsda).
     *
     * @return true if config SIM Swap mode successful, or return false
     * @internal
     */
@Override public boolean configSimSwap(boolean toSwapped) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((toSwapped)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_configSimSwap, _data, _reply, 0);
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
     * To check SIM is swapped or not(for dsda).
     *
     * @return true if swapped, or return false
     * @internal
     */
@Override public boolean isSimSwapped() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSimSwapped, _data, _reply, 0);
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
     * To Check if Capability Switch Manual Control Mode Enabled.
     *
     * @return true if Capability Switch manual control mode is enabled, else false;
     * @internal
     */
@Override public boolean isCapSwitchManualEnabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isCapSwitchManualEnabled, _data, _reply, 0);
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
     * Get item list that will be displayed on manual switch setting
     *
     * @return String[] contains items
     * @internal
     */
@Override public java.lang.String[] getCapSwitchManualList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCapSwitchManualList, _data, _reply, 0);
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
     * To get located PLMN from sepcified SIM modem  protocol
     * Returns current located PLMN string(ex: "46000") or null if not availble (ex: in flight mode or no signal area or this SIM is turned off)
     * @param subId Indicate which SIM subscription to query
     * @internal
     */
@Override public java.lang.String getLocatedPlmn(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getLocatedPlmn, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Check if phone is hiding network temporary out of service state.
     * @param subId Indicate which SIM subscription to query
     * @return if phone is hiding network temporary out of service state.
     * @internal
    */
@Override public int getNetworkHideState(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getNetworkHideState, _data, _reply, 0);
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
     * get the network service state for specified SIM
     * @param subId Indicate which SIM subscription to query
     * @return service state.
     * @internal
    */
@Override public android.os.Bundle getServiceState(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getServiceState, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
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
/**
     * This function is used to get SIM phonebook storage information
     * by sim id.
     *
     * @param simId Indicate which sim(slot) to query
     * @return int[] which incated the storage info
     *         int[0]; // # of remaining entries
     *         int[1]; // # of total entries
     *         int[2]; // # max length of number
     *         int[3]; // # max length of alpha id
     *
     * @internal
     */
@Override public int[] getAdnStorageInfo(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getAdnStorageInfo, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * This function is used to check if the SIM phonebook is ready
     * by sim id.
     *
     * @param simId Indicate which sim(slot) to query
     * @return true if phone book is ready.
     * @internal
     */
@Override public boolean isPhbReady(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isPhbReady, _data, _reply, 0);
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
     * Get service center address
     *
     * @param subId subscription identity
     *
     * @return bundle value with error code and service message center address
     */
@Override public android.os.Bundle getScAddressUsingSubId(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getScAddressUsingSubId, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
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
/**
     * Set service message center address
     *
     * @param subId subscription identity
     * @param service message center addressto be set
     *
     * @return true for success, false for failure
     */
@Override public boolean setScAddressUsingSubId(int subId, java.lang.String address) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(address);
mRemote.transact(Stub.TRANSACTION_setScAddressUsingSubId, _data, _reply, 0);
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
     * This function will check if phone can enter airplane mode right now
     *
     * @return boolean: return phone can enter flight mode
     *                true: phone can enter flight mode
     *                false: phone cannot enter flight mode
     */
@Override public boolean isAirplanemodeAvailableNow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isAirplanemodeAvailableNow, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// VoLTE
/**
     * This function will get DcFailCause with int format.
     *
     * @return int: return int failCause value
     */
@Override public int getLastDataConnectionFailCause(java.lang.String apnType, int phoneId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(apnType);
_data.writeInt(phoneId);
mRemote.transact(Stub.TRANSACTION_getLastDataConnectionFailCause, _data, _reply, 0);
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
     * This function will get link properties of input apn type.
     *
     * @param apnType input apn type for geting link properties
     * @return LinkProperties: return correspondent link properties with input apn type
     */
@Override public android.net.LinkProperties getLinkProperties(java.lang.String apnType, int phoneId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.LinkProperties _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(apnType);
_data.writeInt(phoneId);
mRemote.transact(Stub.TRANSACTION_getLinkProperties, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.net.LinkProperties.CREATOR.createFromParcel(_reply);
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
/**
     * Set phone radio type and access technology.
     *
     * @param rafs an RadioAccessFamily array to indicate all phone's
     *        new radio access family. The length of RadioAccessFamily
     *        must equal to phone count.
     * @return true if start setPhoneRat successfully.
     */
@Override public boolean setRadioCapability(android.telephony.RadioAccessFamily[] rafs) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedArray(rafs, 0);
mRemote.transact(Stub.TRANSACTION_setRadioCapability, _data, _reply, 0);
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
     * Check if under capability switching.
     *
     * @return true if switching
     */
@Override public boolean isCapabilitySwitching() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isCapabilitySwitching, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/// M: [C2K][SVLTE] Switch SVLTE RAT mode. @{
/**
     * Switch SVLTE RAT mode.
     * @param mode the RAT mode.
     * @return void
     */
@Override public void switchSvlteRatMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_switchSvlteRatMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Set SVLTE RAT mode.
     * @param mode the RAT mode.
     * @param subId subscription ID to be queried
     * @return void
     */
@Override public void setSvlteRatMode(int mode, int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_setSvlteRatMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Get the ServiceState for Svlte.
     * @param subId for getting the current ServiceState for Svlte.
     */
@Override public android.os.Bundle getSvlteServiceState(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getSvlteServiceState, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
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
/**
     * Switch SVLTE RAT mode.
     * @param mode the RAT mode.
     */
@Override public void switchRadioTechnology(int networkType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(networkType);
mRemote.transact(Stub.TRANSACTION_switchRadioTechnology, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Set SVLTE Radio Technology.
     * @param networkType the networktype want to switch.
     * @param subId subscription ID to be queried
     */
@Override public void setRadioTechnology(int networkType, int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(networkType);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_setRadioTechnology, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/// @}
/**
    * Set TRM
    *
    * @param mode indicate which case want to set to modem
    */
@Override public void setTrmForPhone(int phoneId, int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(phoneId);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setTrmForPhone, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Get subscriber Id of LTE phone.
     * @param subId the subId of CDMAPhone
     * @return The subscriber Id of LTE phone.
     */
@Override public java.lang.String getSubscriberIdForLteDcPhone(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getSubscriberIdForLteDcPhone, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get Svlte imei.
     * @param slotId slot id.
     * @return String: imei.
     */
@Override public java.lang.String getSvlteImei(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_getSvlteImei, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get main capability phone id.
     * @return The phone id with highest capability.
     */
@Override public int getMainCapabilityPhoneId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMainCapabilityPhoneId, _data, _reply, 0);
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
     * Return true if allow the airplane mode change.
     */
@Override public boolean isAllowAirplaneModeChange() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isAllowAirplaneModeChange, _data, _reply, 0);
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
     * Initialze external SIM service on phone process.
     *
     * @hide
     */
@Override public void initializeService(java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_initializeService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Finalize external SIM service on phone process.
     *
     * @hide
     */
@Override public void finalizeService(java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_finalizeService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Return the sim card if in home network.
     *
     * @param subId subscription ID to be queried
     * @return true if in home network
     */
@Override public boolean isInHomeNetwork(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isInHomeNetwork, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// M: [LTE][Low Power][UL traffic shaping] Start
/**
     * Set LTE access stratum urc report
     * @param enabled the LTE AS state URC report is enable or disable
     * @return true if enabled/disable urc report successfully.
     */
@Override public boolean setLteAccessStratumReport(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setLteAccessStratumReport, _data, _reply, 0);
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
     * Set LTE uplink data transfer
     * @param isOn the LTE uplink data transfer is on or off
     * @param timeMillis the close timer
     * @return true if enabled/disable uplink data transfer successfully.
     */
@Override public boolean setLteUplinkDataTransfer(boolean isOn, int timeMillis) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isOn)?(1):(0)));
_data.writeInt(timeMillis);
mRemote.transact(Stub.TRANSACTION_setLteUplinkDataTransfer, _data, _reply, 0);
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
     * Get LTE access stratum state
     * @return unknown/idle/connected if abnormal mode/power saving mode candidate/normal power mode.
     */
@Override public java.lang.String getLteAccessStratumState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLteAccessStratumState, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get if shared default type apn
     * @return true if is shared default type apn occurred.
     */
@Override public boolean isSharedDefaultApn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSharedDefaultApn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// M: [LTE][Low Power][UL traffic shaping] End
/**
     * Get Svlte meid.
     * @param slotId slot id.
     * @return String: meid.
     */
@Override public java.lang.String getSvlteMeid(int slotId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(slotId);
mRemote.transact(Stub.TRANSACTION_getSvlteMeid, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get IMS registration state by given sub-id.
     * @param subId The subId for query
     * @return true if IMS is registered, or false
     * @hide
     */
@Override public boolean isImsRegistered(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isImsRegistered, _data, _reply, 0);
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
     * Get Volte registration state by given sub-id.
     * @param subId The subId for query
     * @return true if volte is registered, or false
     * @hide
     */
@Override public boolean isVolteEnabled(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isVolteEnabled, _data, _reply, 0);
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
     * Get WFC registration state by given sub-id.
     * @param subId The subId for query
     * @return true if wfc is registered, or false
     * @hide
     */
@Override public boolean isWifiCallingEnabled(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isWifiCallingEnabled, _data, _reply, 0);
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
     * Get Svlte meid.
     * @return String: meid.
     */
@Override public java.lang.String getMeid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMeid, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set ECC in progress.
     * @param state ECC in pregress or not.
     */
@Override public void setEccInProgress(boolean state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((state)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setEccInProgress, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Return if ECC in progress.
     * @return true if ECC in progress, otherwise false.
     */
@Override public boolean isEccInProgress() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isEccInProgress, _data, _reply, 0);
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
static final int TRANSACTION_queryNetworkLock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_supplyNetworkDepersonalization = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_repollIccStateForNetworkLock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setLine1Number = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_isFdnEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getIccCardType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getSvlteCardType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_isAppTypeSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_isTestIccCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getMvnoMatchType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getMvnoPattern = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getNetworkOperatorNameGemini = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getNetworkOperatorNameUsingSub = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getNetworkOperatorGemini = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getNetworkOperatorUsingSub = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_btSimapConnectSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_btSimapDisconnectSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_btSimapApduRequest = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_btSimapResetSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_btSimapPowerOnSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_btSimapPowerOffSIM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_simAkaAuthentication = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_simGbaAuthBootStrapMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_simGbaAuthNafMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_broadcastIccUnlockIntent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_isRadioOffBySimManagement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getPhoneCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_setPhoneCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_configSimSwap = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_isSimSwapped = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_isCapSwitchManualEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_getCapSwitchManualList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getLocatedPlmn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getNetworkHideState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getServiceState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_getAdnStorageInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_isPhbReady = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_getScAddressUsingSubId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_setScAddressUsingSubId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_isAirplanemodeAvailableNow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_getLastDataConnectionFailCause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_getLinkProperties = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_setRadioCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_isCapabilitySwitching = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_switchSvlteRatMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_setSvlteRatMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_getSvlteServiceState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_switchRadioTechnology = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_setRadioTechnology = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_setTrmForPhone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_getSubscriberIdForLteDcPhone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_getSvlteImei = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_getMainCapabilityPhoneId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_isAllowAirplaneModeChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_initializeService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_finalizeService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
static final int TRANSACTION_isInHomeNetwork = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
static final int TRANSACTION_setLteAccessStratumReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 57);
static final int TRANSACTION_setLteUplinkDataTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 58);
static final int TRANSACTION_getLteAccessStratumState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 59);
static final int TRANSACTION_isSharedDefaultApn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 60);
static final int TRANSACTION_getSvlteMeid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 61);
static final int TRANSACTION_isImsRegistered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 62);
static final int TRANSACTION_isVolteEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 63);
static final int TRANSACTION_isWifiCallingEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 64);
static final int TRANSACTION_getMeid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 65);
static final int TRANSACTION_setEccInProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 66);
static final int TRANSACTION_isEccInProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 67);
}
public android.os.Bundle queryNetworkLock(int subId, int category) throws android.os.RemoteException;
public int supplyNetworkDepersonalization(int subId, java.lang.String strPasswd) throws android.os.RemoteException;
/**
     * Modem SML change feature.
     * This function will query the SIM state of the given slot. And broadcast
     * ACTION_UNLOCK_SIM_LOCK if the SIM state is in network lock.
     *
     * @param subId: Indicate which sub to query
     * @param needIntent: The caller can deside to broadcast ACTION_UNLOCK_SIM_LOCK or not
     *                    in this time, because some APs will receive this intent (eg. Keyguard).
     *                    That can avoid this intent to effect other AP.
     */
public void repollIccStateForNetworkLock(int subId, boolean needIntent) throws android.os.RemoteException;
public int setLine1Number(int subId, java.lang.String alphaTag, java.lang.String number) throws android.os.RemoteException;
public boolean isFdnEnabled(int subId) throws android.os.RemoteException;
public java.lang.String getIccCardType(int subId) throws android.os.RemoteException;
/**
     * Request to get UICC card type.
     *
     * @param slotId indicated sim id
     *
     * @return index for UICC card type
     *
     */
public int getSvlteCardType(int slotId) throws android.os.RemoteException;
public boolean isAppTypeSupported(int slotId, int appType) throws android.os.RemoteException;
public boolean isTestIccCard(int slotId) throws android.os.RemoteException;
public java.lang.String getMvnoMatchType(int subId) throws android.os.RemoteException;
public java.lang.String getMvnoPattern(int subId, java.lang.String type) throws android.os.RemoteException;
public java.lang.String getNetworkOperatorNameGemini(int slotId) throws android.os.RemoteException;
public java.lang.String getNetworkOperatorNameUsingSub(int subId) throws android.os.RemoteException;
public java.lang.String getNetworkOperatorGemini(int slotId) throws android.os.RemoteException;
public java.lang.String getNetworkOperatorUsingSub(int subId) throws android.os.RemoteException;
/**
     *send BT SIM profile of Connect SIM
     * @param simId specify which SIM to connect
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
public int btSimapConnectSIM(int simId, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException;
/**
     *send BT SIM profile of Disconnect SIM
     * @param null
     * @return success or error code.
     */
public int btSimapDisconnectSIM() throws android.os.RemoteException;
/**
     *Transfer APDU data through BT SAP
     * @param type Indicate which transport protocol is the preferred one
     * @param cmdAPDU APDU data to transfer in hex character format
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
public int btSimapApduRequest(int type, java.lang.String cmdAPDU, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException;
/**
     *send BT SIM profile of Reset SIM
     * @param type Indicate which transport protocol is the preferred one
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
public int btSimapResetSIM(int type, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException;
/**
     *send BT SIM profile of Power On SIM
     * @param type Indicate which transport protocol is the preferred onet
     * @param btRsp fetch the response data.
     * @return success or error code.
     */
public int btSimapPowerOnSIM(int type, com.mediatek.internal.telephony.BtSimapOperResponse btRsp) throws android.os.RemoteException;
/**
     *send BT SIM profile of PowerOff SIM
     * @return success or error code.
     */
public int btSimapPowerOffSIM() throws android.os.RemoteException;
/**
     * Request to run AKA authenitcation on UICC card by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteRand random challenge in byte array
     * @param byteAutn authenication token in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
public byte[] simAkaAuthentication(int slotId, int family, byte[] byteRand, byte[] byteAutn) throws android.os.RemoteException;
/**
     * Request to run GBA authenitcation (Bootstrapping Mode)on UICC card
     * by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteRand random challenge in byte array
     * @param byteAutn authenication token in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
public byte[] simGbaAuthBootStrapMode(int slotId, int family, byte[] byteRand, byte[] byteAutn) throws android.os.RemoteException;
/**
     * Request to run GBA authenitcation (NAF Derivation Mode)on UICC card
     * by indicated family.
     *
     * @param slotId indicated sim id
     * @param family indiacted family category
     *        UiccController.APP_FAM_3GPP =  1; //SIM/USIM
     *        UiccController.APP_FAM_3GPP2 = 2; //RUIM/CSIM
     *        UiccController.APP_FAM_IMS   = 3; //ISIM
     * @param byteNafId network application function id in byte array
     * @param byteImpi IMS private user identity in byte array
     *
     * @return reponse paramenters/data from UICC
     *
     */
public byte[] simGbaAuthNafMode(int slotId, int family, byte[] byteNafId, byte[] byteImpi) throws android.os.RemoteException;
/**
     * Since MTK keyguard has dismiss feature, we need to retrigger unlock event
     * when user try to access the SIM card.
     *
     * @param subId inidicated subscription
     *
     * @return true represent broadcast a unlock intent to notify keyguard
     *         false represent current state is not LOCKED state. No need to retrigger.
     *
     */
public boolean broadcastIccUnlockIntent(int subId) throws android.os.RemoteException;
/**
     * Query if the radio is turned off by user.
     *
     * @param subId inidicated subscription
     *
     * @return true radio is turned off by user.
     *         false radio isn't turned off by user.
     *
     */
public boolean isRadioOffBySimManagement(int subId) throws android.os.RemoteException;
/**
     * Get current phone capability
     *
     * @return the capability of phone. (@see PhoneConstants)
     * @internal
     */
public int getPhoneCapability(int phoneId) throws android.os.RemoteException;
/**
     * Set capability to phones
     *
     * @param phoneId phones want to change capability
     * @param capability new capability for each phone
     * @internal
     */
public void setPhoneCapability(int[] phoneId, int[] capability) throws android.os.RemoteException;
/**
     * To config SIM swap mode(for dsda).
     *
     * @return true if config SIM Swap mode successful, or return false
     * @internal
     */
public boolean configSimSwap(boolean toSwapped) throws android.os.RemoteException;
/**
     * To check SIM is swapped or not(for dsda).
     *
     * @return true if swapped, or return false
     * @internal
     */
public boolean isSimSwapped() throws android.os.RemoteException;
/**
     * To Check if Capability Switch Manual Control Mode Enabled.
     *
     * @return true if Capability Switch manual control mode is enabled, else false;
     * @internal
     */
public boolean isCapSwitchManualEnabled() throws android.os.RemoteException;
/**
     * Get item list that will be displayed on manual switch setting
     *
     * @return String[] contains items
     * @internal
     */
public java.lang.String[] getCapSwitchManualList() throws android.os.RemoteException;
/**
     * To get located PLMN from sepcified SIM modem  protocol
     * Returns current located PLMN string(ex: "46000") or null if not availble (ex: in flight mode or no signal area or this SIM is turned off)
     * @param subId Indicate which SIM subscription to query
     * @internal
     */
public java.lang.String getLocatedPlmn(int subId) throws android.os.RemoteException;
/**
     * Check if phone is hiding network temporary out of service state.
     * @param subId Indicate which SIM subscription to query
     * @return if phone is hiding network temporary out of service state.
     * @internal
    */
public int getNetworkHideState(int subId) throws android.os.RemoteException;
/**
     * get the network service state for specified SIM
     * @param subId Indicate which SIM subscription to query
     * @return service state.
     * @internal
    */
public android.os.Bundle getServiceState(int subId) throws android.os.RemoteException;
/**
     * This function is used to get SIM phonebook storage information
     * by sim id.
     *
     * @param simId Indicate which sim(slot) to query
     * @return int[] which incated the storage info
     *         int[0]; // # of remaining entries
     *         int[1]; // # of total entries
     *         int[2]; // # max length of number
     *         int[3]; // # max length of alpha id
     *
     * @internal
     */
public int[] getAdnStorageInfo(int subId) throws android.os.RemoteException;
/**
     * This function is used to check if the SIM phonebook is ready
     * by sim id.
     *
     * @param simId Indicate which sim(slot) to query
     * @return true if phone book is ready.
     * @internal
     */
public boolean isPhbReady(int subId) throws android.os.RemoteException;
/**
     * Get service center address
     *
     * @param subId subscription identity
     *
     * @return bundle value with error code and service message center address
     */
public android.os.Bundle getScAddressUsingSubId(int subId) throws android.os.RemoteException;
/**
     * Set service message center address
     *
     * @param subId subscription identity
     * @param service message center addressto be set
     *
     * @return true for success, false for failure
     */
public boolean setScAddressUsingSubId(int subId, java.lang.String address) throws android.os.RemoteException;
/**
     * This function will check if phone can enter airplane mode right now
     *
     * @return boolean: return phone can enter flight mode
     *                true: phone can enter flight mode
     *                false: phone cannot enter flight mode
     */
public boolean isAirplanemodeAvailableNow() throws android.os.RemoteException;
// VoLTE
/**
     * This function will get DcFailCause with int format.
     *
     * @return int: return int failCause value
     */
public int getLastDataConnectionFailCause(java.lang.String apnType, int phoneId) throws android.os.RemoteException;
/**
     * This function will get link properties of input apn type.
     *
     * @param apnType input apn type for geting link properties
     * @return LinkProperties: return correspondent link properties with input apn type
     */
public android.net.LinkProperties getLinkProperties(java.lang.String apnType, int phoneId) throws android.os.RemoteException;
/**
     * Set phone radio type and access technology.
     *
     * @param rafs an RadioAccessFamily array to indicate all phone's
     *        new radio access family. The length of RadioAccessFamily
     *        must equal to phone count.
     * @return true if start setPhoneRat successfully.
     */
public boolean setRadioCapability(android.telephony.RadioAccessFamily[] rafs) throws android.os.RemoteException;
/**
     * Check if under capability switching.
     *
     * @return true if switching
     */
public boolean isCapabilitySwitching() throws android.os.RemoteException;
/// M: [C2K][SVLTE] Switch SVLTE RAT mode. @{
/**
     * Switch SVLTE RAT mode.
     * @param mode the RAT mode.
     * @return void
     */
public void switchSvlteRatMode(int mode) throws android.os.RemoteException;
/**
     * Set SVLTE RAT mode.
     * @param mode the RAT mode.
     * @param subId subscription ID to be queried
     * @return void
     */
public void setSvlteRatMode(int mode, int subId) throws android.os.RemoteException;
/**
     * Get the ServiceState for Svlte.
     * @param subId for getting the current ServiceState for Svlte.
     */
public android.os.Bundle getSvlteServiceState(int subId) throws android.os.RemoteException;
/**
     * Switch SVLTE RAT mode.
     * @param mode the RAT mode.
     */
public void switchRadioTechnology(int networkType) throws android.os.RemoteException;
/**
     * Set SVLTE Radio Technology.
     * @param networkType the networktype want to switch.
     * @param subId subscription ID to be queried
     */
public void setRadioTechnology(int networkType, int subId) throws android.os.RemoteException;
/// @}
/**
    * Set TRM
    *
    * @param mode indicate which case want to set to modem
    */
public void setTrmForPhone(int phoneId, int mode) throws android.os.RemoteException;
/**
     * Get subscriber Id of LTE phone.
     * @param subId the subId of CDMAPhone
     * @return The subscriber Id of LTE phone.
     */
public java.lang.String getSubscriberIdForLteDcPhone(int subId) throws android.os.RemoteException;
/**
     * Get Svlte imei.
     * @param slotId slot id.
     * @return String: imei.
     */
public java.lang.String getSvlteImei(int slotId) throws android.os.RemoteException;
/**
     * Get main capability phone id.
     * @return The phone id with highest capability.
     */
public int getMainCapabilityPhoneId() throws android.os.RemoteException;
/**
     * Return true if allow the airplane mode change.
     */
public boolean isAllowAirplaneModeChange() throws android.os.RemoteException;
/**
     * Initialze external SIM service on phone process.
     *
     * @hide
     */
public void initializeService(java.lang.String serviceName) throws android.os.RemoteException;
/**
     * Finalize external SIM service on phone process.
     *
     * @hide
     */
public void finalizeService(java.lang.String serviceName) throws android.os.RemoteException;
/**
     * Return the sim card if in home network.
     *
     * @param subId subscription ID to be queried
     * @return true if in home network
     */
public boolean isInHomeNetwork(int subId) throws android.os.RemoteException;
// M: [LTE][Low Power][UL traffic shaping] Start
/**
     * Set LTE access stratum urc report
     * @param enabled the LTE AS state URC report is enable or disable
     * @return true if enabled/disable urc report successfully.
     */
public boolean setLteAccessStratumReport(boolean enabled) throws android.os.RemoteException;
/**
     * Set LTE uplink data transfer
     * @param isOn the LTE uplink data transfer is on or off
     * @param timeMillis the close timer
     * @return true if enabled/disable uplink data transfer successfully.
     */
public boolean setLteUplinkDataTransfer(boolean isOn, int timeMillis) throws android.os.RemoteException;
/**
     * Get LTE access stratum state
     * @return unknown/idle/connected if abnormal mode/power saving mode candidate/normal power mode.
     */
public java.lang.String getLteAccessStratumState() throws android.os.RemoteException;
/**
     * Get if shared default type apn
     * @return true if is shared default type apn occurred.
     */
public boolean isSharedDefaultApn() throws android.os.RemoteException;
// M: [LTE][Low Power][UL traffic shaping] End
/**
     * Get Svlte meid.
     * @param slotId slot id.
     * @return String: meid.
     */
public java.lang.String getSvlteMeid(int slotId) throws android.os.RemoteException;
/**
     * Get IMS registration state by given sub-id.
     * @param subId The subId for query
     * @return true if IMS is registered, or false
     * @hide
     */
public boolean isImsRegistered(int subId) throws android.os.RemoteException;
/**
     * Get Volte registration state by given sub-id.
     * @param subId The subId for query
     * @return true if volte is registered, or false
     * @hide
     */
public boolean isVolteEnabled(int subId) throws android.os.RemoteException;
/**
     * Get WFC registration state by given sub-id.
     * @param subId The subId for query
     * @return true if wfc is registered, or false
     * @hide
     */
public boolean isWifiCallingEnabled(int subId) throws android.os.RemoteException;
/**
     * Get Svlte meid.
     * @return String: meid.
     */
public java.lang.String getMeid() throws android.os.RemoteException;
/**
     * Set ECC in progress.
     * @param state ECC in pregress or not.
     */
public void setEccInProgress(boolean state) throws android.os.RemoteException;
/**
     * Return if ECC in progress.
     * @return true if ECC in progress, otherwise false.
     */
public boolean isEccInProgress() throws android.os.RemoteException;
}
