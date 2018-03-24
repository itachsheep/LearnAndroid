/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\as_workspace\\LearnAndroid\\SkyUpdateV2\\app\\src\\main\\aidl\\com\\skyworthdigital\\upgrade\\port\\UpgradeCallback.aidl
 */
package com.skyworthdigital.upgrade.port;
public interface UpgradeCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.skyworthdigital.upgrade.port.UpgradeCallback
{
private static final java.lang.String DESCRIPTOR = "com.skyworthdigital.upgrade.port.UpgradeCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.skyworthdigital.upgrade.port.UpgradeCallback interface,
 * generating a proxy if needed.
 */
public static com.skyworthdigital.upgrade.port.UpgradeCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.skyworthdigital.upgrade.port.UpgradeCallback))) {
return ((com.skyworthdigital.upgrade.port.UpgradeCallback)iin);
}
return new com.skyworthdigital.upgrade.port.UpgradeCallback.Stub.Proxy(obj);
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
case TRANSACTION_downloadProgress:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.downloadProgress(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_upgradeErrorCode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.upgradeErrorCode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_checkVersionResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.checkVersionResult(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.skyworthdigital.upgrade.port.UpgradeCallback
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
@Override public void downloadProgress(int progress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progress);
mRemote.transact(Stub.TRANSACTION_downloadProgress, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void upgradeErrorCode(int errorCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errorCode);
mRemote.transact(Stub.TRANSACTION_upgradeErrorCode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void checkVersionResult(int hasNewVersion) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(hasNewVersion);
mRemote.transact(Stub.TRANSACTION_checkVersionResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_downloadProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_upgradeErrorCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_checkVersionResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void downloadProgress(int progress) throws android.os.RemoteException;
public void upgradeErrorCode(int errorCode) throws android.os.RemoteException;
public void checkVersionResult(int hasNewVersion) throws android.os.RemoteException;
}
