package com.skyworthdigital.upgrade.port;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class UpgradeInfo implements Parcelable {
    private int id;
    private String devicetype;
    private String customerid;
    private String hardversion;
    private int allsoft;
    private int isPublish;
    private int currnumber;
    private int maxnumber;
    private long releasetime;
    private String storeFileName;
    private String pkgurl;
    private String pkgmd5;
    private String pkgversion;
    private String pkgcndesc;
    private String pkgendesc;
    private int forceupgrade;
    private int pkgtype;
    private int pkgsize;
    private long committime;
    private long modifytime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(long releasetime) {
        this.releasetime = releasetime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(devicetype);
        dest.writeString(customerid);
        dest.writeString(hardversion);
        dest.writeInt(allsoft);
        dest.writeInt(isPublish);
        dest.writeInt(currnumber);
        dest.writeInt(maxnumber);
        dest.writeLong(releasetime);
        dest.writeString(storeFileName);
        dest.writeString(pkgurl);
        dest.writeString(pkgmd5);
        dest.writeString(pkgversion);
        dest.writeString(pkgcndesc);
        dest.writeString(pkgendesc);
        dest.writeInt(forceupgrade);
        dest.writeInt(pkgtype);
        dest.writeInt(pkgsize);
        dest.writeLong(committime);
        dest.writeLong(modifytime);
    }
    public static final Parcelable.Creator<UpgradeInfo> CREATOR = new Parcelable.Creator<UpgradeInfo>() {
        @Override
        public UpgradeInfo createFromParcel(Parcel in) {
            UpgradeInfo upgradeInfo = new UpgradeInfo();
            upgradeInfo.setId(in.readInt());
            upgradeInfo.setDevicetype(in.readString());
            upgradeInfo.setCustomerid(in.readString());
            upgradeInfo.setHardversion(in.readString());
            upgradeInfo.setAllsoft(in.readInt());
            upgradeInfo.setIsPublish(in.readInt());
            upgradeInfo.setCurrnumber(in.readInt());
            upgradeInfo.setMaxnumber(in.readInt());
            upgradeInfo.setReleasetime(in.readLong());
            upgradeInfo.setStoreFileName(in.readString());
            upgradeInfo.setPkgurl(in.readString());
            upgradeInfo.setPkgmd5(in.readString());
            upgradeInfo.setPkgversion(in.readString());
            upgradeInfo.setPkgcndesc(in.readString());
            upgradeInfo.setPkgendesc(in.readString());
            upgradeInfo.setForceupgrade(in.readInt());
            upgradeInfo.setPkgtype(in.readInt());
            upgradeInfo.setPkgsize(in.readInt());
            upgradeInfo.setCommittime(in.readLong());
            upgradeInfo.setModifytime(in.readLong());
            return upgradeInfo;
        }

        @Override
        public UpgradeInfo[] newArray(int size) {
            return new UpgradeInfo[size];
        }
    };

    public boolean equals(Object o) {
        if (o instanceof UpgradeInfo) {
            UpgradeInfo info = (UpgradeInfo) o;
            boolean isMd5Equal = isEqual(info.getPkgmd5(), getPkgmd5());
            boolean isUrlEqual = isEqual(info.getPkgurl(), getPkgurl());
            boolean isForceFlagEqual = info.getForceupgrade() == getForceupgrade();
            if (isMd5Equal && isUrlEqual && isForceFlagEqual) {
                return true;
            }
        }
        return false;
    }

    public boolean isIDEqual(Object o) {
        if (o instanceof UpgradeInfo) {
            UpgradeInfo info = (UpgradeInfo) o;
            boolean isIdEqual = info.getId() == getId();
            if (isIdEqual) {
                return true;
            }
        }
        return false;
    }

    public boolean isJustFroceNotEqual(Object o) {
        if (o instanceof UpgradeInfo) {
            UpgradeInfo info = (UpgradeInfo) o;
            boolean isMd5Equal = isEqual(info.getPkgmd5(), getPkgmd5());
            boolean isUrlEqual = isEqual(info.getPkgurl(), getPkgurl());
            boolean isForceFlagEqual = info.getForceupgrade() == getForceupgrade();
            if (isMd5Equal && isUrlEqual && !isForceFlagEqual) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEqual(String str1, String str2) {
        if (str1 != null && str1.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

}
