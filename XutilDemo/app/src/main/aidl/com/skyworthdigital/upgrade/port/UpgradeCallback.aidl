// UpgradeCallback.aidl
package com.skyworthdigital.upgrade.port;

// Declare any non-default types here with import statements

interface UpgradeCallback {
   void downloadProgress(int progress);
   	void upgradeErrorCode(int errorCode);
   	void checkVersionResult(int hasNewVersion);
}
