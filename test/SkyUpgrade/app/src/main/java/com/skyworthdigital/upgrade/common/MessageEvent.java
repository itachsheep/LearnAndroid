package com.skyworthdigital.upgrade.common;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class MessageEvent {


    public static final int EVENT_UPGRADE_TRIGGER = 1;
    public static final int EVENT_UPGRADE_NEXT = 2;
    public static final int EVENT_STOP_CURR_TASK = 3;
    public static final int EVENT_USER_INSURE_RECOVERY = 4;
    public static final int EVENT_UPLOAD_DEVICE_INFO = 5;
    public static final int MSG_DOWNLOAD_PROGRESS = 6;
    public static final int MSG_DOWNLOAD_FAILED = 7;
    public static final int MSG_CHECK_NEW_VERSION = 8;

    public static final int FROM_NORMAL = 0;
    public static final int FROM_BOOT = 1;
    public static final int FROM_NET_CONNECT = 2;
    public static final int FROM_USER_MANUAL = 3;
    public static final int FROM_AUTO_CHECK = 4;
    public static final int FROM_WAKE_UP = 5;


    private int what = 0;
    private int from = 0;
    private int param=0;
    private String msg;

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageEvent(int what, int from){
        this.what = what;
        this.from = from;
    }

    public static MessageEvent createMsg(int what, int from) {
        MessageEvent result = new MessageEvent(what, from);
        return result;
    }
}
