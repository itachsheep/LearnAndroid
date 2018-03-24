package com.skyworthdigital.upgrade.state.process;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Handler;
import android.os.RecoverySystem;
import de.greenrobot.event.EventBus;

import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgradeBracast;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;

public class RecoveryProcess extends StateProcess {

    public RecoveryProcess(UpgradeTask task) {
        super(task);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        LogUtil.log("run recovery process");

        if (getTask().isWriteRecoveryFlag() && !CommonUtil.isRecoveryMode()) {

            /* 升级之后上报一次下载完成 */
            UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
            upload.uploadDownloadResult();

            getTask().setState(StateProcess.STATE_UPLOAD_UPGRADE);
            UpgradeApp.getInstance().getTaskManager().updateObj(getTask());

            EventBus.getDefault().post(
                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, MessageEvent.FROM_NORMAL));
        }
        else {
            if (getTask().getForceupgrade() == 1) {
                forceUpdate();
            }
            else {
                normalUpdate();
            }
        }

        this.setStatus(StateProcess.PROCESS_COMPLETE);
        LogUtil.log("RecoveryProcess finished");
    }

    @Override
    public void stopProcess() {
        // TODO Auto-generated method stub

    }

    private void postHintRecovery() {
        Intent in = new Intent(IUpgradeBracast.ACTION_HINT_RECOVERY);
        in.putExtra(CommonUtil.DESCRIPTION, getTask().getPkgcndesc());
        in.putExtra(CommonUtil.ISFORCEUPGRADE, getTask().getForceupgrade());
        in.putExtra(CommonUtil.PKGVERSION, getTask().getPkgversion());
        try {
            UpgradeApp.getInstance().sendStickyBroadcast(in);
            postBroadcastToView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postBroadcastToView() {
        Intent it = new Intent("android.action.skyworth.sys.update.toast");
        try {
            UpgradeApp.getInstance().sendBroadcast(it);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rebootInstallPackage() {

        String updateFile = CommonUtil.getSaveFilePath();
        final File mFile = new File(updateFile);
        if (mFile.exists()) {
            try {
                RecoverySystem.installPackage(UpgradeApp.getInstance(), mFile);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void forceUpdate() {

        postHintRecovery();

        getTask().setUserInsureReboot(true);
        updateTask();

        new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

            public void run() {
                rebootInstallPackage();
            }

        }, 15000);
    }

    private void normalUpdate() {
        if (!getTask().isUserInsureReboot()) {
            postHintRecovery();
        }
        else {
            updateTask();

            new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                public void run() {
                    rebootInstallPackage();
                }

            }, 1000);
        }
    }

    private void updateTask() {
        getTask().setWriteRecoveryFlag(true);
        CommonUtil.setRecoveryMode(true);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
    }
}
