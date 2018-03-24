package com.skyworthdigital.upgrade.upload;

import com.skyworthdigital.upgrade.db.DeviceInfo;

public class DeviceInfoResult {
    private int code;
    private String msg;
    private DeviceInfo data;

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

    public DeviceInfo getData() {
        return data;
    }

    public void setData(DeviceInfo data) {
        this.data = data;
    }

}
