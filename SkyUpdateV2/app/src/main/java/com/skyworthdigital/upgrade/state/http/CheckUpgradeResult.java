package com.skyworthdigital.upgrade.state.http;

import com.skyworthdigital.upgrade.port.UpgradeInfo;

public class CheckUpgradeResult {
    private int code;
    private String msg;
    private DataResult data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataResult getData() {
        return data;
    }

    public void setData(DataResult data) {
        this.data = data;
    }

    public class DataResult {
        private UpgradeInfo upgradeinfo;

        public UpgradeInfo getUpgradeinfo() {
            return upgradeinfo;
        }

        public void setUpgradeinfo(UpgradeInfo upgradeinfo) {
            this.upgradeinfo = upgradeinfo;
        }

    }

}
