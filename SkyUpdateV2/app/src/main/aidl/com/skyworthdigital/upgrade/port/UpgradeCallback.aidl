package com.skyworthdigital.upgrade.port;

interface UpgradeCallback {
	void downloadProgress(int progress);
	void upgradeErrorCode(int errorCode);
	void checkVersionResult(int hasNewVersion);
}