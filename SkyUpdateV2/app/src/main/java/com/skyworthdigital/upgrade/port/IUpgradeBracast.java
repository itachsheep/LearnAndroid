package com.skyworthdigital.upgrade.port;

public interface IUpgradeBracast {
    public static final String ACTION_CHECKUPDATE = "android.action.skyworthdigital.ota.checkupdate";
    public static final String ACTION_HINT_RECOVERY = "android.action.skyworthdigital.ota.hintreadyrecovery";
    public static final String ACTION_DOWNLOAD_PROCESS = "android.action.skyworthdigital.ota.downloadprocess";
    public static final String ACTION_DOWNLOAD_FAILED = "android.action.skyworthdigital.ota.downloadfailed";
	public static final String ACTION_UPGRADE_COMPLETE = "android.action.skyworthdigital.ota.upgradecomplete";
}
