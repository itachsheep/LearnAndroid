package com.skyworthdigital.upgrade.state;

import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.state.process.CheckProcess;
import com.skyworthdigital.upgrade.state.process.DownloadProcess;
import com.skyworthdigital.upgrade.state.process.RecoveryProcess;
import com.skyworthdigital.upgrade.state.process.UploadUpgradeProcess;
import com.skyworthdigital.upgrade.utils.LogUtil;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class StateProcessFactory {
    public static StateProcess createCheckProcess() {
        StateProcess result = new CheckProcess();
        return result;
    }


    public static StateProcess createProcess(UpgradeTask task){
        StateProcess result = null;
        LogUtil.i("StateProcess.createProcess state: "+task.getState());
        switch (task.getState()){
            case StateProcess.STATE_CHECK:
                result =  new CheckProcess();
                result.setTask(new UpgradeTask());
                break;
            case StateProcess.STATE_DOWNLOAD:
                result = new DownloadProcess(task);
                break;
            case StateProcess.STATE_RECOVERY :
                result = new RecoveryProcess(task);
                break;
            case StateProcess.STATE_UPLOAD_UPGRADE :
                result = new UploadUpgradeProcess(task);
                break;
            default :
                result = new CheckProcess();
                result.setTask(new UpgradeTask());
                break;
        }
        return result;
    }
}
