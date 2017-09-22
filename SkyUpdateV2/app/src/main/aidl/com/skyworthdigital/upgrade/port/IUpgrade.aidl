package com.skyworthdigital.upgrade.port;
import com.skyworthdigital.upgrade.port.UpgradeInfo;
import com.skyworthdigital.upgrade.port.UpgradeCallback;

interface IUpgrade{
   void registerCallback(UpgradeCallback cb);
   void unregisterCallback(UpgradeCallback cb);
   void startCheckUpgrade();
   UpgradeInfo getUpgradeInfo();
   void installPackage();
   int getDownloadProcess();
   boolean hasNewVersion();
}