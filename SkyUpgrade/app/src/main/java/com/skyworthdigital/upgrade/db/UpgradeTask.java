package com.skyworthdigital.upgrade.db;

import com.skyworthdigital.upgrade.port.UpgradeInfo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by SDT14324 on 2017/9/25.
 */

@Table(name = "upgradetask")
public class UpgradeTask {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "state")
    private int state;
    @Column(name = "downloadProcess")
    private int downloadProcess;
    @Column(name = "downloadSize")
    private long downloadSize;
    @Column(name = "isManual")
    private boolean isManual;
    @Column(name = "isDownloadComplete")
    private boolean isDownloadComplete;
    @Column(name = "isMD5right")
    private boolean isMD5right;
    @Column(name = "uploadDownloadComplete")
    private boolean uploadDownloadComplete;
    @Column(name = "isHintReboot")
    private boolean isHintReboot;
    @Column(name = "userInsureReboot")
    private boolean userInsureReboot;
    @Column(name = "isWriteRecoveryFlag")
    private boolean isWriteRecoveryFlag;
    @Column(name = "upgradeResult")
    private boolean upgradeResult;
    @Column(name = "uploadUpgradeResult")
    private boolean uploadUpgradeResult;
    @Column(name = "uploadUpgradeResultTimes")
    private int uploadUpgradeResultTimes;
    @Column(name = "taskstarttime")
    private long taskstarttime;
    @Column(name = "presoft")
    private String presoft;
    @Column(name = "uploaddownloadsize")
    private long uploaddownloadsize;

    // /UpgradeInfo
    @Column(name = "upgradeInfoId")
    private int upgradeInfoId;
    @Column(name = "devicetype")
    private String devicetype;
    @Column(name = "customerid")
    private String customerid;
    @Column(name = "hardversion")
    private String hardversion;
    @Column(name = "allsoft")
    private int allsoft;
    @Column(name = "isPublish")
    private int isPublish;
    @Column(name = "currnumber")
    private int currnumber;
    @Column(name = "maxnumber")
    private int maxnumber;
    @Column(name = "releasetime")
    private long releasetime;
    @Column(name = "storeFileName")
    private String storeFileName;
    @Column(name = "pkgurl")
    private String pkgurl;
    @Column(name = "pkgmd5")
    private String pkgmd5;
    @Column(name = "pkgversion")
    private String pkgversion;
    @Column(name = "pkgcndesc")
    private String pkgcndesc;
    @Column(name = "pkgendesc")
    private String pkgendesc;
    @Column(name = "forceupgrade")
    private int forceupgrade;
    @Column(name = "pkgtype")
    private int pkgtype;
    @Column(name = "pkgsize")
    private int pkgsize;
    @Column(name = "committime")
    private long committime;
    @Column(name = "modifytime")
    private long modifytime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getUploaddownloadsize() {
        return uploaddownloadsize;
    }

    public void setUploaddownloadsize(long uploaddownloadsize) {
        this.uploaddownloadsize = uploaddownloadsize;
    }

    public int getDownloadProcess() {
        return downloadProcess;
    }

    public void setDownloadProcess(int downloadProcess) {
        this.downloadProcess = downloadProcess;
    }

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }

    public boolean isDownloadComplete() {
        return isDownloadComplete;
    }

    public void setDownloadComplete(boolean isDownloadComplete) {
        this.isDownloadComplete = isDownloadComplete;
    }

    public boolean isMD5right() {
        return isMD5right;
    }

    public void setMD5right(boolean isMD5right) {
        this.isMD5right = isMD5right;
    }

    public boolean isUploadDownloadComplete() {
        return uploadDownloadComplete;
    }

    public void setUploadDownloadComplete(boolean uploadDownloadComplete) {
        this.uploadDownloadComplete = uploadDownloadComplete;
    }

    public boolean isHintReboot() {
        return isHintReboot;
    }

    public void setHintReboot(boolean isHintReboot) {
        this.isHintReboot = isHintReboot;
    }

    public boolean isUserInsureReboot() {
        return userInsureReboot;
    }

    public void setUserInsureReboot(boolean userInsureReboot) {
        this.userInsureReboot = userInsureReboot;
    }

    public boolean isWriteRecoveryFlag() {
        return isWriteRecoveryFlag;
    }

    public void setWriteRecoveryFlag(boolean isWriteRecoveryFlag) {
        this.isWriteRecoveryFlag = isWriteRecoveryFlag;
    }

    public boolean isUpgradeResult() {
        return upgradeResult;
    }

    public void setUpgradeResult(boolean upgradeResult) {
        this.upgradeResult = upgradeResult;
    }

    public boolean isUploadUpgradeResult() {
        return uploadUpgradeResult;
    }

    public void setUploadUpgradeResult(boolean uploadUpgradeResult) {
        this.uploadUpgradeResult = uploadUpgradeResult;
    }

    public int getUploadUpgradeResultTimes() {
        return uploadUpgradeResultTimes;
    }

    public void setUploadUpgradeResultTimes(int time) {
        this.uploadUpgradeResultTimes = time;
    }

    public int getUpgradeInfoId() {
        return upgradeInfoId;
    }

    public void setUpgradeInfoId(int upgradeInfoId) {
        this.upgradeInfoId = upgradeInfoId;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getHardversion() {
        return hardversion;
    }

    public void setHardversion(String hardversion) {
        this.hardversion = hardversion;
    }

    public int getAllsoft() {
        return allsoft;
    }

    public void setAllsoft(int allsoft) {
        this.allsoft = allsoft;
    }

    public int getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(int isPublish) {
        this.isPublish = isPublish;
    }

    public int getCurrnumber() {
        return currnumber;
    }

    public void setCurrnumber(int currnumber) {
        this.currnumber = currnumber;
    }

    public int getMaxnumber() {
        return maxnumber;
    }

    public void setMaxnumber(int maxnumber) {
        this.maxnumber = maxnumber;
    }

    public long getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(long releasetime) {
        this.releasetime = releasetime;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public String getPkgurl() {
        return pkgurl;
    }

    public void setPkgurl(String pkgurl) {
        this.pkgurl = pkgurl;
    }

    public String getPkgmd5() {
        return pkgmd5;
    }

    public void setPkgmd5(String pkgmd5) {
        this.pkgmd5 = pkgmd5;
    }

    public String getPkgversion() {
        return pkgversion;
    }

    public void setPkgversion(String pkgversion) {
        this.pkgversion = pkgversion;
    }

    public String getPkgcndesc() {
        return pkgcndesc;
    }

    public void setPkgcndesc(String pkgcndesc) {
        this.pkgcndesc = pkgcndesc;
    }

    public String getPkgendesc() {
        return pkgendesc;
    }

    public void setPkgendesc(String pkgendesc) {
        this.pkgendesc = pkgendesc;
    }

    public int getForceupgrade() {
        return forceupgrade;
    }

    public void setForceupgrade(int forceupgrade) {
        this.forceupgrade = forceupgrade;
    }

    public int getPkgtype() {
        return pkgtype;
    }

    public void setPkgtype(int pkgtype) {
        this.pkgtype = pkgtype;
    }

    public int getPkgsize() {
        return pkgsize;
    }

    public void setPkgsize(int pkgsize) {
        this.pkgsize = pkgsize;
    }

    public long getCommittime() {
        return committime;
    }

    public void setCommittime(long committime) {
        this.committime = committime;
    }

    public long getModifytime() {
        return modifytime;
    }

    public void setModifytime(long modifytime) {
        this.modifytime = modifytime;
    }

    public long getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(long taskstarttime) {
        this.taskstarttime = taskstarttime;
    }

    public String getPresoft() {
        return presoft;
    }

    public void setPresoft(String presoft) {
        this.presoft = presoft;
    }

    public void setUpgradeInfo(UpgradeInfo info) {
        setUpgradeInfoId(info.getId());
        setDevicetype(info.getDevicetype());
        setCustomerid(info.getCustomerid());
        setHardversion(info.getHardversion());
        setAllsoft(info.getAllsoft());
        setIsPublish(info.getIsPublish());
        setCurrnumber(info.getCurrnumber());
        setMaxnumber(info.getMaxnumber());
        setReleasetime(info.getReleasetime());
        setStoreFileName(info.getStoreFileName());
        setPkgurl(info.getPkgurl());
        setPkgmd5(info.getPkgmd5());
        setPkgversion(info.getPkgversion());
        setPkgcndesc(info.getPkgcndesc());
        setPkgendesc(info.getPkgendesc());
        setForceupgrade(info.getForceupgrade());
        setPkgtype(info.getPkgtype());
        setPkgsize(info.getPkgsize());
        setCommittime(info.getCommittime());
        setModifytime(info.getModifytime());
    }

    public UpgradeInfo getUpgradeInfo() {
        UpgradeInfo info = new UpgradeInfo();
        info.setId(getUpgradeInfoId());
        info.setDevicetype(getDevicetype());
        info.setCustomerid(getCustomerid());
        info.setHardversion(getHardversion());
        info.setAllsoft(getAllsoft());
        info.setIsPublish(getIsPublish());
        info.setCurrnumber(getCurrnumber());
        info.setMaxnumber(getMaxnumber());
        info.setReleasetime(getReleasetime());
        info.setStoreFileName(getStoreFileName());
        info.setPkgurl(getPkgurl());
        info.setPkgmd5(getPkgmd5());
        info.setPkgversion(getPkgversion());
        info.setPkgcndesc(getPkgcndesc());
        info.setPkgendesc(getPkgendesc());
        info.setForceupgrade(getForceupgrade());
        info.setPkgtype(getPkgtype());
        info.setPkgsize(getPkgsize());
        info.setCommittime(getCommittime());
        info.setModifytime(getModifytime());

        return info;
    }

    @Override
    public String toString() {
        return "UpgradeTask{" +
                "id=" + id +
                ", state=" + state +
                ", downloadProcess=" + downloadProcess +
                ", downloadSize=" + downloadSize +
                ", isManual=" + isManual +
                ", isDownloadComplete=" + isDownloadComplete +
                ", isMD5right=" + isMD5right +
                ", uploadDownloadComplete=" + uploadDownloadComplete +
                ", isHintReboot=" + isHintReboot +
                ", userInsureReboot=" + userInsureReboot +
                ", isWriteRecoveryFlag=" + isWriteRecoveryFlag +
                ", upgradeResult=" + upgradeResult +
                ", uploadUpgradeResult=" + uploadUpgradeResult +
                ", uploadUpgradeResultTimes=" + uploadUpgradeResultTimes +


                ", uploaddownloadsize=" + uploaddownloadsize +
                ", upgradeInfoId=" + upgradeInfoId +
                ", devicetype='" + devicetype + '\'' +
                ", customerid='" + customerid + '\'' +
                ", hardversion='" + hardversion + '\'' +

                ", storeFileName='" + storeFileName + '\'' +
                ", pkgurl='" + pkgurl + '\'' +
                ", pkgmd5='" + pkgmd5 + '\'' +
                ", pkgversion='" + pkgversion + '\'' +
                ", pkgcndesc='" + pkgcndesc + '\'' +
                ", pkgendesc='" + pkgendesc + '\'' +
                ", forceupgrade=" + forceupgrade +
                ", pkgtype=" + pkgtype +
                ", pkgsize=" + pkgsize +
                ", committime=" + committime +
                ", modifytime=" + modifytime +
                '}';
    }
}
