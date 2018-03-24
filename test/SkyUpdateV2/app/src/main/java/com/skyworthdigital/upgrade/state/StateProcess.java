package com.skyworthdigital.upgrade.state;

import com.skyworthdigital.upgrade.model.UpgradeTask;

public abstract class StateProcess implements Runnable {
    public static final int STATE_CHECK = 1;
    public static final int STATE_DOWNLOAD = 2;
    public static final int STATE_RECOVERY = 3;
    public static final int STATE_UPLOAD_UPGRADE = 4;
    public static final int STATE_COMPLETE = 5;

    public static final int PROCESS_INIT = 1;
    public static final int PROCESS_RUNNING = 2;
    public static final int PROCESS_COMPLETE = 3;

    private UpgradeTask task = null;
    private int status = PROCESS_INIT;

    public StateProcess(UpgradeTask task) {
        this.task = task;
    }

    public void startProcess() {
        setStatus(PROCESS_RUNNING);
        try {
            run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void stopProcess();

    public boolean isRunning() {
        return PROCESS_RUNNING == getStatus();
    }

    public UpgradeTask getTask() {
        return task;
    }

    public void setTask(UpgradeTask task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString() {
        return "status : " + getStatusString();
    }

    private String getStatusString() {
        String result = "";
        switch (status) {
            case PROCESS_INIT :
                result = "init";
                break;
            case PROCESS_RUNNING :
                result = "running";
                break;
            case PROCESS_COMPLETE :
                result = "complete";
                break;
        }
        return result;
    }

}
