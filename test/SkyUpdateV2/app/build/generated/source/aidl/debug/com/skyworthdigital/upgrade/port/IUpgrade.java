/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\as_workspace\\LearnAndroid\\SkyUpdateV2\\app\\src\\main\\aidl\\com\\skyworthdigital\\upgrade\\port\\IUpgrade.aidl
 */
package com.skyworthdigital.upgrade.port;
public interface IUpgrade extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.skyworthdigital.upgrade.port.IUpgrade
{
private static final java.lang.String DESCRIPTOR = "com.skyworthdigital.upgrade.port.IUpgrade";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.skyworthdigital.upgrade.port.IUpgrade interface,
 * generating a proxy if needed.
 */
public static com.skyworthdigital.upgrade.port.IUpgrade asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.skyworthdigital.upgrade.port.IUpgrade))) {
return ((com.skyworthdigital.upgrade.port.IUpgrade)iin);
}
return new com.skyworthdigital.upgrade.port.IUpgrade.Stub.Proxy(obj);
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
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.skyworthdigital.upgrade.port.UpgradeCallback _arg0;
_arg0 = com.skyworthdigital.upgrade.port.UpgradeCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.skyworthdigital.upgrade.port.UpgradeCallback _arg0;
_arg0 = com.skyworthdigital.upgrade.port.UpgradeCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_startCheckUpgrade:
{
data.enforceInterface(DESCRIPTOR);
this.startCheckUpgrade();
reply.writeNoException();
return true;
}
case TRANSACTION_getUpgradeInfo:
{
data.enforceInterface(DESCRIPTOR);
com.skyworthdigital.upgrade.port.UpgradeInfo _result = this.getUpgradeInfo();
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
case TRANSACTION_installPackage:
{
data.enforceInterface(DESCRIPTOR);
this.installPackage();
reply.writeNoException();
return true;
}
case TRANSACTION_getDownloadProcess:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDownloadProcess();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_hasNewVersion:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.hasNewVersion();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.skyworthdigital.upgrade.port.IUpgrade
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
@Override public void registerCallback(com.skyworthdigital.upgrade.port.UpgradeCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterCallback(com.skyworthdigital.upgrade.port.UpgradeCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startCheckUpgrade() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startCheckUpgrade, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public com.skyworthdigital.upgrade.port.UpgradeInfo getUpgradeInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.skyworthdigital.upgrade.port.UpgradeInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUpgradeInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.skyworthdigital.upgrade.port.UpgradeInfo.CREATOR.createFromParcel(_reply);
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
@Override public void installPackage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_installPackage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getDownloadProcess() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDownloadProcess, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean hasNewVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_hasNewVersion, _data, _reply, 0);
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
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_startCheckUpgrade = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getUpgradeInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_installPackage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDownloadProcess = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_hasNewVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public void registerCallback(com.skyworthdigital.upgrade.port.UpgradeCallback cb) throws android.os.RemoteException;
public void unregisterCallback(com.skyworthdigital.upgrade.port.UpgradeCallback cb) throws android.os.RemoteException;
public void startCheckUpgrade() throws android.os.RemoteException;
public com.skyworthdigital.upgrade.port.UpgradeInfo getUpgradeInfo() throws android.os.RemoteException;
public void installPackage() throws android.os.RemoteException;
public int getDownloadProcess() throws android.os.RemoteException;
public boolean hasNewVersion() throws android.os.RemoteException;
}
