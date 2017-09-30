package com.skyworthdigital.upgrade.state.process;

import android.content.Intent;
import android.os.Handler;
import android.os.RecoverySystem;

import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.upload.UploadDownloadComplete;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;

import java.io.File;
import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class RecoveryProcess extends StateProcess{
    public RecoveryProcess(UpgradeTask task) {
        super(task);
    }

    @Override
    public void stopProcess() {

    }

    @Override
    public void run() {
        LogUtil.i("run recovery process");
        if (getTask().isWriteRecoveryFlag() && !CommonUtil.isRecoveryMode()) {

            //if install the update.zip, then report install sucess
            LogUtil.i("RecoveryProcess upload");
            UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
            upload.uploadDownloadResult();

            getTask().setState(StateProcess.STATE_UPLOAD_UPGRADE);
            UpgradeApp.getInstance().getTaskManager().updateObj(getTask());

            EventBus.getDefault().post(
                    MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, MessageEvent.FROM_NORMAL));
        } else {
            if (getTask().getForceupgrade() == 1) {
                forceUpdate();
            }
            else {
                normalUpdate();
            }
        }

        RecoveryProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
        LogUtil.i("RecoveryProcess finished");

    }


    private void normalUpdate() {
        LogUtil.i("RecoveryProcess normal update : "+getTask().isUserInsureReboot());
        if (!getTask().isUserInsureReboot()) {
            postHintRecovery();
        } else {
            updateTask();

            new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                public void run() {
                    rebootInstallPackage();
                }

            }, IConstants.SECONDS_1);
        }
    }


    private void forceUpdate() {
        LogUtil.i("RecoveryProcess force update");
        postHintRecovery();

        getTask().setUserInsureReboot(true);
        updateTask();

        new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

            public void run() {
                rebootInstallPackage();
            }

        }, IConstants.SECONDS_15);
    }

    private void rebootInstallPackage() {
        LogUtil.i("RecoveryProcess rebootInstallPackage ");
        String updateFile = CommonUtil.getSaveFilePath();
        final File mFile = new File(updateFile);
        if (mFile.exists()) {
            try {
                LogUtil.i("RecoveryProcess mFile "+mFile.getAbsolutePath());
                RecoverySystem.installPackage(UpgradeApp.getInstance(), mFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTask() {
        getTask().setWriteRecoveryFlag(true);
        CommonUtil.setRecoveryMode(true);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
    }

    private void postHintRecovery() {
        Intent in = new Intent(IConstants.ACTION_HINT_RECOVERY);
        in.putExtra(IConstants.DESCRIPTION, getTask().getPkgcndesc());
        in.putExtra(IConstants.ISFORCEUPGRADE, getTask().getForceupgrade());
        in.putExtra(IConstants.PKGVERSION, getTask().getPkgversion());
        try {
            UpgradeApp.getInstance().sendStickyBroadcast(in);
            // TODO: 2017/9/26 remove
            /*postBroadcastToView();*/
        }
        catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("RecoveryProcess postHintRecovery error :"+e.getMessage());
        }
    }


}
