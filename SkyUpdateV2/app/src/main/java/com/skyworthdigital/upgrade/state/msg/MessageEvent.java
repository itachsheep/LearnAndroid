package com.skyworthdigital.upgrade.state.msg;

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

    public MessageEvent(int what, int from) {
        this.what = what;
        this.from = from;
    }

    public static MessageEvent createMsg(int what, int from) {
        MessageEvent result = new MessageEvent(what, from);
        return result;
    }

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWhatMean(int what) {
        String result = "";
        switch (what) {
            case EVENT_UPGRADE_TRIGGER :
                result = "trigger";
                break;
            case EVENT_UPGRADE_NEXT :
                result = "next";
                break;
        }
        return result;
    }

    public String getFromMean(int what) {
        String result = "";
        switch (what) {
            case FROM_BOOT :
                result = "boot";
                break;
            case FROM_NET_CONNECT :
                result = "net connect";
                break;
            case FROM_USER_MANUAL :
                result = "user click";
                break;
        }
        return result;
    }

    
    
    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public String toString() {
        return what + " : " + getWhatMean(what) + " ; " + from + " : " + getFromMean(from) + " ; " + msg;
    }

}
