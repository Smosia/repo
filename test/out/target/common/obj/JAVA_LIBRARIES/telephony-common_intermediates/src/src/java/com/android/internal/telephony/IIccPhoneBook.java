/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/telephony/src/java/com/android/internal/telephony/IIccPhoneBook.aidl
 */
package com.android.internal.telephony;
/** Interface for applications to access the ICC phone book.
 *
 * <p>The following code snippet demonstrates a static method to
 * retrieve the IIccPhoneBook interface from Android:</p>
 * <pre>private static IIccPhoneBook getSimPhoneBookInterface()
            throws DeadObjectException {
    IServiceManager sm = ServiceManagerNative.getDefault();
    IIccPhoneBook spb;
    spb = IIccPhoneBook.Stub.asInterface(sm.getService("iccphonebook"));
    return spb;
}
 * </pre>
 */
public interface IIccPhoneBook extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IIccPhoneBook
{
private static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IIccPhoneBook";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.android.internal.telephony.IIccPhoneBook interface,
 * generating a proxy if needed.
 */
public static com.android.internal.telephony.IIccPhoneBook asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.android.internal.telephony.IIccPhoneBook))) {
return ((com.android.internal.telephony.IIccPhoneBook)iin);
}
return new com.android.internal.telephony.IIccPhoneBook.Stub.Proxy(obj);
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
case TRANSACTION_getAdnRecordsInEf:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.util.List<com.android.internal.telephony.uicc.AdnRecord> _result = this.getAdnRecordsInEf(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getAdnRecordsInEfForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.util.List<com.android.internal.telephony.uicc.AdnRecord> _result = this.getAdnRecordsInEfForSubscriber(_arg0, _arg1);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_updateAdnRecordsInEfBySearch:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
boolean _result = this.updateAdnRecordsInEfBySearch(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateAdnRecordsInEfBySearchForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
java.lang.String _arg6;
_arg6 = data.readString();
boolean _result = this.updateAdnRecordsInEfBySearchForSubscriber(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateAdnRecordsInEfBySearchWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
java.lang.String _arg6;
_arg6 = data.readString();
int _result = this.updateAdnRecordsInEfBySearchWithError(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimPBRecordsInEfBySearchWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
java.lang.String[] _arg6;
_arg6 = data.createStringArray();
java.lang.String _arg7;
_arg7 = data.readString();
java.lang.String _arg8;
_arg8 = data.readString();
java.lang.String _arg9;
_arg9 = data.readString();
java.lang.String _arg10;
_arg10 = data.readString();
java.lang.String[] _arg11;
_arg11 = data.createStringArray();
int _result = this.updateUsimPBRecordsInEfBySearchWithError(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateAdnRecordsInEfByIndex:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _arg3;
_arg3 = data.readInt();
java.lang.String _arg4;
_arg4 = data.readString();
boolean _result = this.updateAdnRecordsInEfByIndex(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateAdnRecordsInEfByIndexForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
int _arg4;
_arg4 = data.readInt();
java.lang.String _arg5;
_arg5 = data.readString();
boolean _result = this.updateAdnRecordsInEfByIndexForSubscriber(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateAdnRecordsInEfByIndexWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
int _arg4;
_arg4 = data.readInt();
java.lang.String _arg5;
_arg5 = data.readString();
int _result = this.updateAdnRecordsInEfByIndexWithError(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimPBRecordsInEfByIndexWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
java.lang.String[] _arg6;
_arg6 = data.createStringArray();
int _arg7;
_arg7 = data.readInt();
int _result = this.updateUsimPBRecordsInEfByIndexWithError(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimPBRecordsByIndexWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
com.android.internal.telephony.uicc.AdnRecord _arg2;
if ((0!=data.readInt())) {
_arg2 = com.android.internal.telephony.uicc.AdnRecord.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _arg3;
_arg3 = data.readInt();
int _result = this.updateUsimPBRecordsByIndexWithError(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimPBRecordsBySearchWithError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
com.android.internal.telephony.uicc.AdnRecord _arg2;
if ((0!=data.readInt())) {
_arg2 = com.android.internal.telephony.uicc.AdnRecord.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.android.internal.telephony.uicc.AdnRecord _arg3;
if ((0!=data.readInt())) {
_arg3 = com.android.internal.telephony.uicc.AdnRecord.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
int _result = this.updateUsimPBRecordsBySearchWithError(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getAdnRecordsSize:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int[] _result = this.getAdnRecordsSize(_arg0);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_getAdnRecordsSizeForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int[] _result = this.getAdnRecordsSizeForSubscriber(_arg0, _arg1);
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
case TRANSACTION_getUsimGroups:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.util.List<com.mediatek.internal.telephony.uicc.UsimGroup> _result = this.getUsimGroups(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getUsimGroupById:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _result = this.getUsimGroupById(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_removeUsimGroupById:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.removeUsimGroupById(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_insertUsimGroup:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.insertUsimGroup(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimGroup:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.updateUsimGroup(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addContactToGroup:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.addContactToGroup(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_removeContactFromGroup:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.removeContactFromGroup(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateContactToGroups:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int[] _arg2;
_arg2 = data.createIntArray();
boolean _result = this.updateContactToGroups(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_moveContactFromGroupsToGroups:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int[] _arg2;
_arg2 = data.createIntArray();
int[] _arg3;
_arg3 = data.createIntArray();
boolean _result = this.moveContactFromGroupsToGroups(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_hasExistGroup:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.hasExistGroup(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsimGrpMaxNameLen:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getUsimGrpMaxNameLen(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsimGrpMaxCount:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getUsimGrpMaxCount(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsimAasList:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.util.List<com.mediatek.internal.telephony.uicc.AlphaTag> _result = this.getUsimAasList(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getUsimAasById:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _result = this.getUsimAasById(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_insertUsimAas:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.insertUsimAas(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getAnrCount:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getAnrCount(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getEmailCount:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getEmailCount(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsimAasMaxCount:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getUsimAasMaxCount(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsimAasMaxNameLen:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getUsimAasMaxNameLen(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updateUsimAas:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
java.lang.String _arg3;
_arg3 = data.readString();
boolean _result = this.updateUsimAas(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_removeUsimAasById:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.removeUsimAasById(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_hasSne:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.hasSne(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSneRecordLen:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getSneRecordLen(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isAdnAccessible:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isAdnAccessible(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPhonebookMemStorageExt:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.internal.telephony.uicc.UsimPBMemInfo[] _result = this.getPhonebookMemStorageExt(_arg0);
reply.writeNoException();
reply.writeTypedArray(_result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
return true;
}
case TRANSACTION_isUICCCard:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isUICCCard(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.android.internal.telephony.IIccPhoneBook
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
     * Loads the AdnRecords in efid and returns them as a
     * List of AdnRecords
     *
     * @param efid the EF id of a ADN-like SIM
     * @return List of AdnRecord
     */
@Override public java.util.List<com.android.internal.telephony.uicc.AdnRecord> getAdnRecordsInEf(int efid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.android.internal.telephony.uicc.AdnRecord> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(efid);
mRemote.transact(Stub.TRANSACTION_getAdnRecordsInEf, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.android.internal.telephony.uicc.AdnRecord.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Loads the AdnRecords in efid and returns them as a
     * List of AdnRecords
     *
     * @param efid the EF id of a ADN-like SIM
     * @param subId user preferred subId
     * @return List of AdnRecord
     */
@Override public java.util.List<com.android.internal.telephony.uicc.AdnRecord> getAdnRecordsInEfForSubscriber(int subId, int efid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.android.internal.telephony.uicc.AdnRecord> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
mRemote.transact(Stub.TRANSACTION_getAdnRecordsInEfForSubscriber, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.android.internal.telephony.uicc.AdnRecord.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return true for success
     */
@Override public boolean updateAdnRecordsInEfBySearch(int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(efid);
_data.writeString(oldTag);
_data.writeString(oldPhoneNumber);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfBySearch, _data, _reply, 0);
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
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @param subId user preferred subId
     * @return true for success
     */
@Override public boolean updateAdnRecordsInEfBySearchForSubscriber(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(oldTag);
_data.writeString(oldPhoneNumber);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfBySearchForSubscriber, _data, _reply, 0);
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
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned.
     *
     * This method will return why the error occurs.
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateAdnRecordsInEfBySearchWithError(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(oldTag);
_data.writeString(oldPhoneNumber);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfBySearchWithError, _data, _reply, 0);
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
     * Replace old USIM phonebook contacts with new USIM phonebook contacts 
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned.
     *
     * This method will return why the error occurs.
     *
     * @param efid must be EF_ADN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     * @param oldAnr additional number string for the adn record.
     * @param oldGrpIds group id list for the adn record.
     * @param oldEmails Emails for the adn record.     
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     * @param newAnr  new additional number string to be stored 
     * @param newGrpIds group id list for the adn record.
     * @param newEmails Emails for the adn record.  
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateUsimPBRecordsInEfBySearchWithError(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String oldAnr, java.lang.String oldGrpIds, java.lang.String[] oldEmails, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String newAnr, java.lang.String newGrpIds, java.lang.String[] newEmails) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(oldTag);
_data.writeString(oldPhoneNumber);
_data.writeString(oldAnr);
_data.writeString(oldGrpIds);
_data.writeStringArray(oldEmails);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeString(newAnr);
_data.writeString(newGrpIds);
_data.writeStringArray(newEmails);
mRemote.transact(Stub.TRANSACTION_updateUsimPBRecordsInEfBySearchWithError, _data, _reply, 0);
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
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return true for success
     */
@Override public boolean updateAdnRecordsInEfByIndex(int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(efid);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeInt(index);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfByIndex, _data, _reply, 0);
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
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @param subId user preferred subId
     * @return true for success
     */
@Override public boolean updateAdnRecordsInEfByIndexForSubscriber(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeInt(index);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfByIndexForSubscriber, _data, _reply, 0);
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
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateAdnRecordsInEfByIndexWithError(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeInt(index);
_data.writeString(pin2);
mRemote.transact(Stub.TRANSACTION_updateAdnRecordsInEfByIndexWithError, _data, _reply, 0);
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
     * Update an USIM phonebook contacts by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     * @param newAnr  new additional number string to be stored 
     * @param newGrpIds group id list for the adn record.
     * @param newEmails Emails for the adn record.     
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateUsimPBRecordsInEfByIndexWithError(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String newAnr, java.lang.String newGrpIds, java.lang.String[] newEmails, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
_data.writeString(newTag);
_data.writeString(newPhoneNumber);
_data.writeString(newAnr);
_data.writeString(newGrpIds);
_data.writeStringArray(newEmails);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_updateUsimPBRecordsInEfByIndexWithError, _data, _reply, 0);
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
     * Update an USIM phonebook contacts by record index
     *
     * This is useful for iteration the whole USIM ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param record adn record to be stored
     * @param index is 1-based adn record index to be updated
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateUsimPBRecordsByIndexWithError(int subId, int efid, com.android.internal.telephony.uicc.AdnRecord record, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
if ((record!=null)) {
_data.writeInt(1);
record.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_updateUsimPBRecordsByIndexWithError, _data, _reply, 0);
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
     * Update an USIM phonebook contacts by old record information
     *
     * This is useful for iteration the whole USIM ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldAdn adn record to be replaced
     * @param newAdn adn record to be stored
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
@Override public int updateUsimPBRecordsBySearchWithError(int subId, int efid, com.android.internal.telephony.uicc.AdnRecord oldAdn, com.android.internal.telephony.uicc.AdnRecord newAdn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
if ((oldAdn!=null)) {
_data.writeInt(1);
oldAdn.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((newAdn!=null)) {
_data.writeInt(1);
newAdn.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_updateUsimPBRecordsBySearchWithError, _data, _reply, 0);
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
     * Get the max munber of records in efid
     *
     * @param efid the EF id of a ADN-like SIM
     * @return  int[3] array
     *            recordSizes[0]  is the single record length
     *            recordSizes[1]  is the total length of the EF file
     *            recordSizes[2]  is the number of records in the EF file
     */
@Override public int[] getAdnRecordsSize(int efid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(efid);
mRemote.transact(Stub.TRANSACTION_getAdnRecordsSize, _data, _reply, 0);
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
     * Get the max munber of records in efid
     *
     * @param efid the EF id of a ADN-like SIM
     * @param subId user preferred subId
     * @return  int[3] array
     *            recordSizes[0]  is the single record length
     *            recordSizes[1]  is the total length of the EF file
     *            recordSizes[2]  is the number of records in the EF file
     */
@Override public int[] getAdnRecordsSizeForSubscriber(int subId, int efid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(efid);
mRemote.transact(Stub.TRANSACTION_getAdnRecordsSizeForSubscriber, _data, _reply, 0);
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
     * Judge if the PHB subsystem is ready or not
     *
     * @return  true for ready
     * @hide
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
     * Get groups list of USIM phonebook
     *
     * This is useful for getting groups list  of USIM phonebook 
     *
     * This method will return null if phonebook not ready
     *
     * @return UsimGroup list 
     *
     * @hide
     * @internal
     */
@Override public java.util.List<com.mediatek.internal.telephony.uicc.UsimGroup> getUsimGroups(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.internal.telephony.uicc.UsimGroup> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimGroups, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.internal.telephony.uicc.UsimGroup.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get indicated group name of USIM phonebook by group id
     *
     * This method will return null if phonebook not ready
     *
     * @param nGasId  given group id use to query group name
     * @return group name of indicated id 
     *
     * @hide
     * @internal
     */
@Override public java.lang.String getUsimGroupById(int subId, int nGasId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(nGasId);
mRemote.transact(Stub.TRANSACTION_getUsimGroupById, _data, _reply, 0);
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
     * Remove  indicated group from USIM phonebook by group id
     *
     * This method will return false if phonebook not ready
     *
     * @param nGasId  given group id use to query group name
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean removeUsimGroupById(int subId, int nGasId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(nGasId);
mRemote.transact(Stub.TRANSACTION_removeUsimGroupById, _data, _reply, 0);
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
     * Insert a new group to USIM phonebook by given name
     *
     * This method will return -1 if phonebook not ready
     *
     * @param grpName  new added group name
     * @return group id of inserted group
     *
     * @hide
     * @internal
     */
@Override public int insertUsimGroup(int subId, java.lang.String grpName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(grpName);
mRemote.transact(Stub.TRANSACTION_insertUsimGroup, _data, _reply, 0);
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
     * Update group name to USIM phonebook by id and new name
     *
     * This method will return -1 if phonebook not ready
     *
     * @param nGasId group id need to update     
     * @param grpName new group name
     * @return group id of inserted group
     *
     * @hide
     * @internal
     */
@Override public int updateUsimGroup(int subId, int nGasId, java.lang.String grpName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(nGasId);
_data.writeString(grpName);
mRemote.transact(Stub.TRANSACTION_updateUsimGroup, _data, _reply, 0);
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
     * Add contact to indicated group by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to add it to a group
     * @param grpIndex  group id 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean addContactToGroup(int subId, int adnIndex, int grpIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(adnIndex);
_data.writeInt(grpIndex);
mRemote.transact(Stub.TRANSACTION_addContactToGroup, _data, _reply, 0);
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
     * Remove contact from indicated group by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to remove it from a group
     * @param grpIndex  group id 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean removeContactFromGroup(int subId, int adnIndex, int grpIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(adnIndex);
_data.writeInt(grpIndex);
mRemote.transact(Stub.TRANSACTION_removeContactFromGroup, _data, _reply, 0);
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
     * Update contact to indicated group list by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to update it to groups
     * @param grpIdList  group id list
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean updateContactToGroups(int subId, int adnIndex, int[] grpIdList) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(adnIndex);
_data.writeIntArray(grpIdList);
mRemote.transact(Stub.TRANSACTION_updateContactToGroups, _data, _reply, 0);
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
     * Move contact from and to indicated group list by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex       contact id which you want to update it to groups
     * @param fromGrpIdList  group id list that want to remove
     * @param toGrpIdList    group id list that want to add
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean moveContactFromGroupsToGroups(int subId, int adnIndex, int[] fromGrpIdList, int[] toGrpIdList) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(adnIndex);
_data.writeIntArray(fromGrpIdList);
_data.writeIntArray(toGrpIdList);
mRemote.transact(Stub.TRANSACTION_moveContactFromGroupsToGroups, _data, _reply, 0);
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
     * Check if there is the same group in USIM phonebook
     *
     * This method will return -1  if phonebook not ready
     *
     * @param grpName group name
     * @return 1 if there is a same group name
     *
     * @hide
     * @internal
     */
@Override public int hasExistGroup(int subId, java.lang.String grpName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(grpName);
mRemote.transact(Stub.TRANSACTION_hasExistGroup, _data, _reply, 0);
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
     * Get the maximum group name limitation of USIM group
     *
     * This method will return -1  if phonebook not ready
     *
     * @return length of name limitation
     *
     * @hide
     * @internal
     */
@Override public int getUsimGrpMaxNameLen(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimGrpMaxNameLen, _data, _reply, 0);
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
     * Get the maximum group number limitation of USIM group
     *
     * This method will return -1  if phonebook not ready
     *
     * @return number of group limitation
     *
     * @hide
     * @internal
     */
@Override public int getUsimGrpMaxCount(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimGrpMaxCount, _data, _reply, 0);
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
     * Get name list of adtional number
     *
     * This method will return null  if phonebook not ready
     *
     * @return aditional number string list
     *
     * @hide
     * @internal
     */
@Override public java.util.List<com.mediatek.internal.telephony.uicc.AlphaTag> getUsimAasList(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.internal.telephony.uicc.AlphaTag> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimAasList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.internal.telephony.uicc.AlphaTag.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get name of indicated adtional number by given index
     *
     * This method will return null  if phonebook not ready
     *
     * @param index AAS index
     * @return indicated aditional number string
     *
     * @hide
     * @internal
     */
@Override public java.lang.String getUsimAasById(int subId, int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_getUsimAasById, _data, _reply, 0);
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
     * Insert name of adtional number
     *
     * This method will return -1  if phonebook not ready
     *
     * @param aasName AAS name
     * @return index of new added aditional number name
     *
     * @hide
     * @internal
     */
@Override public int insertUsimAas(int subId, java.lang.String aasName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeString(aasName);
mRemote.transact(Stub.TRANSACTION_insertUsimAas, _data, _reply, 0);
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
     * Get number of aditional number count
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number count
     *
     * @hide
     * @internal
     */
@Override public int getAnrCount(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getAnrCount, _data, _reply, 0);
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
     * Get number of email field count
     *
     * This method will return 0  if phonebook not ready
     *
     * @return email field
     *
     * @hide
     * @internal
     */
@Override public int getEmailCount(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getEmailCount, _data, _reply, 0);
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
     * Get count of aditional number name limitation
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number name count limitation
     *
     * @hide
     * @internal
     */
@Override public int getUsimAasMaxCount(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimAasMaxCount, _data, _reply, 0);
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
     * Get length of aditional number name limitation
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number name length limitaion
     *
     * @hide
     * @internal
     */
@Override public int getUsimAasMaxNameLen(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getUsimAasMaxNameLen, _data, _reply, 0);
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
     * Update aditional number name by given index and new name
     *
     * This method will return false  if phonebook not ready
     *
     * @param index  AAS index
     * @param pbrIndex  PBR index 
     * @param aasName  new AAS name
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean updateUsimAas(int subId, int index, int pbrIndex, java.lang.String aasName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(index);
_data.writeInt(pbrIndex);
_data.writeString(aasName);
mRemote.transact(Stub.TRANSACTION_updateUsimAas, _data, _reply, 0);
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
     * Remove aditional number name by given index 
     *
     * This method will return false  if phonebook not ready
     *
     * @param index  AAS index
     * @param pbrIndex  PBR index 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
@Override public boolean removeUsimAasById(int subId, int index, int pbrIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
_data.writeInt(index);
_data.writeInt(pbrIndex);
mRemote.transact(Stub.TRANSACTION_removeUsimAasById, _data, _reply, 0);
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
     * Check if support SNE functionility
     *
     * This method will return false  if phonebook not ready
     *
     * @return true if SNE is supported
     *
     * @hide
     * @internal
     */
@Override public boolean hasSne(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_hasSne, _data, _reply, 0);
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
     * Get SNE record length
     *
     * This method will return -1  if phonebook not ready
     *
     * @return SNE record length
     *
     * @hide
     * @internal
     */
@Override public int getSneRecordLen(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getSneRecordLen, _data, _reply, 0);
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
     * @hide
     * @internal
    */
@Override public boolean isAdnAccessible(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isAdnAccessible, _data, _reply, 0);
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
     * Get memory storage information of USIM phonebook
     *
     * This method will return null  if phonebook not ready
     *
     * @return UsimPBMemInfo array for memory storage
     *
     * @hide
     * @internal
     */
@Override public com.mediatek.internal.telephony.uicc.UsimPBMemInfo[] getPhonebookMemStorageExt(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.internal.telephony.uicc.UsimPBMemInfo[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getPhonebookMemStorageExt, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArray(com.mediatek.internal.telephony.uicc.UsimPBMemInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * This method will if the sim card is UICC card
     *
     * @return is Uicc Card
     *
     * @hide
     * @internal
     */
@Override public boolean isUICCCard(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isUICCCard, _data, _reply, 0);
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
static final int TRANSACTION_getAdnRecordsInEf = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getAdnRecordsInEfForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_updateAdnRecordsInEfBySearch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_updateAdnRecordsInEfBySearchForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_updateAdnRecordsInEfBySearchWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_updateUsimPBRecordsInEfBySearchWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_updateAdnRecordsInEfByIndex = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_updateAdnRecordsInEfByIndexForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_updateAdnRecordsInEfByIndexWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_updateUsimPBRecordsInEfByIndexWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_updateUsimPBRecordsByIndexWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_updateUsimPBRecordsBySearchWithError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getAdnRecordsSize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getAdnRecordsSizeForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_isPhbReady = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getUsimGroups = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getUsimGroupById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_removeUsimGroupById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_insertUsimGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_updateUsimGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_addContactToGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_removeContactFromGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_updateContactToGroups = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_moveContactFromGroupsToGroups = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_hasExistGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_getUsimGrpMaxNameLen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getUsimGrpMaxCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getUsimAasList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_getUsimAasById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_insertUsimAas = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_getAnrCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_getEmailCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getUsimAasMaxCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getUsimAasMaxNameLen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_updateUsimAas = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_removeUsimAasById = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_hasSne = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_getSneRecordLen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_isAdnAccessible = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_getPhonebookMemStorageExt = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_isUICCCard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
}
/**
     * Loads the AdnRecords in efid and returns them as a
     * List of AdnRecords
     *
     * @param efid the EF id of a ADN-like SIM
     * @return List of AdnRecord
     */
public java.util.List<com.android.internal.telephony.uicc.AdnRecord> getAdnRecordsInEf(int efid) throws android.os.RemoteException;
/**
     * Loads the AdnRecords in efid and returns them as a
     * List of AdnRecords
     *
     * @param efid the EF id of a ADN-like SIM
     * @param subId user preferred subId
     * @return List of AdnRecord
     */
public java.util.List<com.android.internal.telephony.uicc.AdnRecord> getAdnRecordsInEfForSubscriber(int subId, int efid) throws android.os.RemoteException;
/**
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return true for success
     */
public boolean updateAdnRecordsInEfBySearch(int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @param subId user preferred subId
     * @return true for success
     */
public boolean updateAdnRecordsInEfBySearchForSubscriber(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Replace oldAdn with newAdn in ADN-like record in EF
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned.
     *
     * This method will return why the error occurs.
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateAdnRecordsInEfBySearchWithError(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Replace old USIM phonebook contacts with new USIM phonebook contacts 
     *
     * getAdnRecordsInEf must be called at least once before this function,
     * otherwise an error will be returned.
     *
     * This method will return why the error occurs.
     *
     * @param efid must be EF_ADN
     * @param oldTag adn tag to be replaced
     * @param oldPhoneNumber adn number to be replaced
     * @param oldAnr additional number string for the adn record.
     * @param oldGrpIds group id list for the adn record.
     * @param oldEmails Emails for the adn record.     
     *        Set both oldTag and oldPhoneNubmer to "" means to replace an
     *        empty record, aka, insert new record
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number ot be stored
     * @param newAnr  new additional number string to be stored 
     * @param newGrpIds group id list for the adn record.
     * @param newEmails Emails for the adn record.  
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateUsimPBRecordsInEfBySearchWithError(int subId, int efid, java.lang.String oldTag, java.lang.String oldPhoneNumber, java.lang.String oldAnr, java.lang.String oldGrpIds, java.lang.String[] oldEmails, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String newAnr, java.lang.String newGrpIds, java.lang.String[] newEmails) throws android.os.RemoteException;
/**
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return true for success
     */
public boolean updateAdnRecordsInEfByIndex(int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @param subId user preferred subId
     * @return true for success
     */
public boolean updateAdnRecordsInEfByIndexForSubscriber(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Update an ADN-like EF record by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @param pin2 required to update EF_FDN, otherwise must be null
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateAdnRecordsInEfByIndexWithError(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, int index, java.lang.String pin2) throws android.os.RemoteException;
/**
     * Update an USIM phonebook contacts by record index
     *
     * This is useful for iteration the whole ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param newTag adn tag to be stored
     * @param newPhoneNumber adn number to be stored
     * @param newAnr  new additional number string to be stored 
     * @param newGrpIds group id list for the adn record.
     * @param newEmails Emails for the adn record.     
     *        Set both newTag and newPhoneNubmer to "" means to replace the old
     *        record with empty one, aka, delete old record
     * @param index is 1-based adn record index to be updated
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateUsimPBRecordsInEfByIndexWithError(int subId, int efid, java.lang.String newTag, java.lang.String newPhoneNumber, java.lang.String newAnr, java.lang.String newGrpIds, java.lang.String[] newEmails, int index) throws android.os.RemoteException;
/**
     * Update an USIM phonebook contacts by record index
     *
     * This is useful for iteration the whole USIM ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param record adn record to be stored
     * @param index is 1-based adn record index to be updated
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateUsimPBRecordsByIndexWithError(int subId, int efid, com.android.internal.telephony.uicc.AdnRecord record, int index) throws android.os.RemoteException;
/**
     * Update an USIM phonebook contacts by old record information
     *
     * This is useful for iteration the whole USIM ADN file, such as write the whole
     * phone book or erase/format the whole phonebook.
     *
     * This method will return why the error occurs
     *
     * @param efid must be one among EF_ADN, EF_FDN, and EF_SDN
     * @param oldAdn adn record to be replaced
     * @param newAdn adn record to be stored
     * @return ERROR_ICC_PROVIDER_* defined in the IccProvider
     *
     * @hide
     * @internal
     */
public int updateUsimPBRecordsBySearchWithError(int subId, int efid, com.android.internal.telephony.uicc.AdnRecord oldAdn, com.android.internal.telephony.uicc.AdnRecord newAdn) throws android.os.RemoteException;
/**
     * Get the max munber of records in efid
     *
     * @param efid the EF id of a ADN-like SIM
     * @return  int[3] array
     *            recordSizes[0]  is the single record length
     *            recordSizes[1]  is the total length of the EF file
     *            recordSizes[2]  is the number of records in the EF file
     */
public int[] getAdnRecordsSize(int efid) throws android.os.RemoteException;
/**
     * Get the max munber of records in efid
     *
     * @param efid the EF id of a ADN-like SIM
     * @param subId user preferred subId
     * @return  int[3] array
     *            recordSizes[0]  is the single record length
     *            recordSizes[1]  is the total length of the EF file
     *            recordSizes[2]  is the number of records in the EF file
     */
public int[] getAdnRecordsSizeForSubscriber(int subId, int efid) throws android.os.RemoteException;
/**
     * Judge if the PHB subsystem is ready or not
     *
     * @return  true for ready
     * @hide
     * @internal
     */
public boolean isPhbReady(int subId) throws android.os.RemoteException;
/**
     * Get groups list of USIM phonebook
     *
     * This is useful for getting groups list  of USIM phonebook 
     *
     * This method will return null if phonebook not ready
     *
     * @return UsimGroup list 
     *
     * @hide
     * @internal
     */
public java.util.List<com.mediatek.internal.telephony.uicc.UsimGroup> getUsimGroups(int subId) throws android.os.RemoteException;
/**
     * Get indicated group name of USIM phonebook by group id
     *
     * This method will return null if phonebook not ready
     *
     * @param nGasId  given group id use to query group name
     * @return group name of indicated id 
     *
     * @hide
     * @internal
     */
public java.lang.String getUsimGroupById(int subId, int nGasId) throws android.os.RemoteException;
/**
     * Remove  indicated group from USIM phonebook by group id
     *
     * This method will return false if phonebook not ready
     *
     * @param nGasId  given group id use to query group name
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean removeUsimGroupById(int subId, int nGasId) throws android.os.RemoteException;
/**
     * Insert a new group to USIM phonebook by given name
     *
     * This method will return -1 if phonebook not ready
     *
     * @param grpName  new added group name
     * @return group id of inserted group
     *
     * @hide
     * @internal
     */
public int insertUsimGroup(int subId, java.lang.String grpName) throws android.os.RemoteException;
/**
     * Update group name to USIM phonebook by id and new name
     *
     * This method will return -1 if phonebook not ready
     *
     * @param nGasId group id need to update     
     * @param grpName new group name
     * @return group id of inserted group
     *
     * @hide
     * @internal
     */
public int updateUsimGroup(int subId, int nGasId, java.lang.String grpName) throws android.os.RemoteException;
/**
     * Add contact to indicated group by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to add it to a group
     * @param grpIndex  group id 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean addContactToGroup(int subId, int adnIndex, int grpIndex) throws android.os.RemoteException;
/**
     * Remove contact from indicated group by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to remove it from a group
     * @param grpIndex  group id 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean removeContactFromGroup(int subId, int adnIndex, int grpIndex) throws android.os.RemoteException;
/**
     * Update contact to indicated group list by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex  contact id which you want to update it to groups
     * @param grpIdList  group id list
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean updateContactToGroups(int subId, int adnIndex, int[] grpIdList) throws android.os.RemoteException;
/**
     * Move contact from and to indicated group list by given contact index and group id
     *
     * This method will return false if phonebook not ready
     *
     * @param adnIndex       contact id which you want to update it to groups
     * @param fromGrpIdList  group id list that want to remove
     * @param toGrpIdList    group id list that want to add
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean moveContactFromGroupsToGroups(int subId, int adnIndex, int[] fromGrpIdList, int[] toGrpIdList) throws android.os.RemoteException;
/**
     * Check if there is the same group in USIM phonebook
     *
     * This method will return -1  if phonebook not ready
     *
     * @param grpName group name
     * @return 1 if there is a same group name
     *
     * @hide
     * @internal
     */
public int hasExistGroup(int subId, java.lang.String grpName) throws android.os.RemoteException;
/**
     * Get the maximum group name limitation of USIM group
     *
     * This method will return -1  if phonebook not ready
     *
     * @return length of name limitation
     *
     * @hide
     * @internal
     */
public int getUsimGrpMaxNameLen(int subId) throws android.os.RemoteException;
/**
     * Get the maximum group number limitation of USIM group
     *
     * This method will return -1  if phonebook not ready
     *
     * @return number of group limitation
     *
     * @hide
     * @internal
     */
public int getUsimGrpMaxCount(int subId) throws android.os.RemoteException;
/**
     * Get name list of adtional number
     *
     * This method will return null  if phonebook not ready
     *
     * @return aditional number string list
     *
     * @hide
     * @internal
     */
public java.util.List<com.mediatek.internal.telephony.uicc.AlphaTag> getUsimAasList(int subId) throws android.os.RemoteException;
/**
     * Get name of indicated adtional number by given index
     *
     * This method will return null  if phonebook not ready
     *
     * @param index AAS index
     * @return indicated aditional number string
     *
     * @hide
     * @internal
     */
public java.lang.String getUsimAasById(int subId, int index) throws android.os.RemoteException;
/**
     * Insert name of adtional number
     *
     * This method will return -1  if phonebook not ready
     *
     * @param aasName AAS name
     * @return index of new added aditional number name
     *
     * @hide
     * @internal
     */
public int insertUsimAas(int subId, java.lang.String aasName) throws android.os.RemoteException;
/**
     * Get number of aditional number count
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number count
     *
     * @hide
     * @internal
     */
public int getAnrCount(int subId) throws android.os.RemoteException;
/**
     * Get number of email field count
     *
     * This method will return 0  if phonebook not ready
     *
     * @return email field
     *
     * @hide
     * @internal
     */
public int getEmailCount(int subId) throws android.os.RemoteException;
/**
     * Get count of aditional number name limitation
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number name count limitation
     *
     * @hide
     * @internal
     */
public int getUsimAasMaxCount(int subId) throws android.os.RemoteException;
/**
     * Get length of aditional number name limitation
     *
     * This method will return -1  if phonebook not ready
     *
     * @return aditional number name length limitaion
     *
     * @hide
     * @internal
     */
public int getUsimAasMaxNameLen(int subId) throws android.os.RemoteException;
/**
     * Update aditional number name by given index and new name
     *
     * This method will return false  if phonebook not ready
     *
     * @param index  AAS index
     * @param pbrIndex  PBR index 
     * @param aasName  new AAS name
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean updateUsimAas(int subId, int index, int pbrIndex, java.lang.String aasName) throws android.os.RemoteException;
/**
     * Remove aditional number name by given index 
     *
     * This method will return false  if phonebook not ready
     *
     * @param index  AAS index
     * @param pbrIndex  PBR index 
     * @return true if the operation is successful
     *
     * @hide
     * @internal
     */
public boolean removeUsimAasById(int subId, int index, int pbrIndex) throws android.os.RemoteException;
/**
     * Check if support SNE functionility
     *
     * This method will return false  if phonebook not ready
     *
     * @return true if SNE is supported
     *
     * @hide
     * @internal
     */
public boolean hasSne(int subId) throws android.os.RemoteException;
/**
     * Get SNE record length
     *
     * This method will return -1  if phonebook not ready
     *
     * @return SNE record length
     *
     * @hide
     * @internal
     */
public int getSneRecordLen(int subId) throws android.os.RemoteException;
/**
     * @hide
     * @internal
    */
public boolean isAdnAccessible(int subId) throws android.os.RemoteException;
/**
     * Get memory storage information of USIM phonebook
     *
     * This method will return null  if phonebook not ready
     *
     * @return UsimPBMemInfo array for memory storage
     *
     * @hide
     * @internal
     */
public com.mediatek.internal.telephony.uicc.UsimPBMemInfo[] getPhonebookMemStorageExt(int subId) throws android.os.RemoteException;
/**
     * This method will if the sim card is UICC card
     *
     * @return is Uicc Card
     *
     * @hide
     * @internal
     */
public boolean isUICCCard(int subId) throws android.os.RemoteException;
}
