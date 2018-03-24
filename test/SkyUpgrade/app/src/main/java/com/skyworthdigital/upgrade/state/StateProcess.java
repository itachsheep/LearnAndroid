package com.skyworthdigital.upgrade.state;

import com.skyworthdigital.upgrade.db.UpgradeTask;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public abstract class StateProcess implements Runnable{
    private UpgradeTask task = null;
    private int status = PROCESS_INIT;

    //each process status
    public static final int PROCESS_INIT = 1;
    public static final int PROCESS_RUNNING = 2;
    public static final int PROCESS_COMPLETE = 3;

    //upgrade status
    public static final int STATE_CHECK = 1;
    public static final int STATE_DOWNLOAD = 2;
    public static final int STATE_RECOVERY = 3;
    public static final int STATE_UPLOAD_UPGRADE = 4;
    public static final int STATE_COMPLETE = 5;

    public abstract void stopProcess();


    public StateProcess(UpgradeTask task) {
        this.task = task;
    }


    public void startProcess(){
        setStatus(PROCESS_RUNNING);
        try {
            run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isRunning() {
        return PROCESS_RUNNING == getStatus();
    }
    public int getStatus() {
        return status;
    }

    public void setTask(UpgradeTask task) {
        this.task = task;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public UpgradeTask getTask() {
        return task;
    }
}
