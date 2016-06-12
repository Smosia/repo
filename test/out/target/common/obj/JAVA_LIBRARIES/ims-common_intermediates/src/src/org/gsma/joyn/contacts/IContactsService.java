/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/contacts/IContactsService.aidl
 */
package org.gsma.joyn.contacts;
/**
 * Contacts service API
 */
public interface IContactsService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.contacts.IContactsService
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.contacts.IContactsService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.contacts.IContactsService interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.contacts.IContactsService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.contacts.IContactsService))) {
return ((org.gsma.joyn.contacts.IContactsService)iin);
}
return new org.gsma.joyn.contacts.IContactsService.Stub.Proxy(obj);
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
case TRANSACTION_getJoynContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
org.gsma.joyn.contacts.JoynContact _result = this.getJoynContact(_arg0);
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
case TRANSACTION_getJoynContacts:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<org.gsma.joyn.contacts.JoynContact> _result = this.getJoynContacts();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getJoynContactsOnline:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<org.gsma.joyn.contacts.JoynContact> _result = this.getJoynContactsOnline();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getJoynContactsSupporting:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<org.gsma.joyn.contacts.JoynContact> _result = this.getJoynContactsSupporting(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getServiceVersion:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getServiceVersion();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getImBlockedContactsFromLocal:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getImBlockedContactsFromLocal();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_isImBlockedForContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isImBlockedForContact(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getImBlockedContacts:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getImBlockedContacts();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_getTimeStampForBlockedContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getTimeStampForBlockedContact(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setImBlockedForContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setImBlockedForContact(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setFtBlockedForContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setFtBlockedForContact(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isRcsValidNumber:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.isRcsValidNumber(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getRegistrationState:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getRegistrationState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_loadImBlockedContactsToLocal:
{
data.enforceInterface(DESCRIPTOR);
this.loadImBlockedContactsToLocal();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.contacts.IContactsService
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
@Override public org.gsma.joyn.contacts.JoynContact getJoynContact(java.lang.String contactId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.contacts.JoynContact _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contactId);
mRemote.transact(Stub.TRANSACTION_getJoynContact, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = org.gsma.joyn.contacts.JoynContact.CREATOR.createFromParcel(_reply);
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
@Override public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContacts() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<org.gsma.joyn.contacts.JoynContact> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getJoynContacts, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(org.gsma.joyn.contacts.JoynContact.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContactsOnline() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<org.gsma.joyn.contacts.JoynContact> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getJoynContactsOnline, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(org.gsma.joyn.contacts.JoynContact.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContactsSupporting(java.lang.String tag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<org.gsma.joyn.contacts.JoynContact> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(tag);
mRemote.transact(Stub.TRANSACTION_getJoynContactsSupporting, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(org.gsma.joyn.contacts.JoynContact.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getServiceVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getImBlockedContactsFromLocal() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getImBlockedContactsFromLocal, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isImBlockedForContact(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_isImBlockedForContact, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getImBlockedContacts() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getImBlockedContacts, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getTimeStampForBlockedContact(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_getTimeStampForBlockedContact, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setImBlockedForContact(java.lang.String contact, boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setImBlockedForContact, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setFtBlockedForContact(java.lang.String contact, boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setFtBlockedForContact, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isRcsValidNumber(java.lang.String number) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(number);
mRemote.transact(Stub.TRANSACTION_isRcsValidNumber, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getRegistrationState(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_getRegistrationState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void loadImBlockedContactsToLocal() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_loadImBlockedContactsToLocal, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getJoynContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getJoynContacts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getJoynContactsOnline = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getJoynContactsSupporting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getImBlockedContactsFromLocal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isImBlockedForContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getImBlockedContacts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getTimeStampForBlockedContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setImBlockedForContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_setFtBlockedForContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_isRcsValidNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getRegistrationState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_loadImBlockedContactsToLocal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public org.gsma.joyn.contacts.JoynContact getJoynContact(java.lang.String contactId) throws android.os.RemoteException;
public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContacts() throws android.os.RemoteException;
public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContactsOnline() throws android.os.RemoteException;
public java.util.List<org.gsma.joyn.contacts.JoynContact> getJoynContactsSupporting(java.lang.String tag) throws android.os.RemoteException;
public int getServiceVersion() throws android.os.RemoteException;
public java.util.List<java.lang.String> getImBlockedContactsFromLocal() throws android.os.RemoteException;
public boolean isImBlockedForContact(java.lang.String contact) throws android.os.RemoteException;
public java.util.List<java.lang.String> getImBlockedContacts() throws android.os.RemoteException;
public java.lang.String getTimeStampForBlockedContact(java.lang.String contact) throws android.os.RemoteException;
public void setImBlockedForContact(java.lang.String contact, boolean flag) throws android.os.RemoteException;
public void setFtBlockedForContact(java.lang.String contact, boolean flag) throws android.os.RemoteException;
public boolean isRcsValidNumber(java.lang.String number) throws android.os.RemoteException;
public int getRegistrationState(java.lang.String contact) throws android.os.RemoteException;
public void loadImBlockedContactsToLocal() throws android.os.RemoteException;
}
