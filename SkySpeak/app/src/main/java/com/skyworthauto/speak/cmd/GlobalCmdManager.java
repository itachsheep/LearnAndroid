package com.skyworthauto.speak.cmd;

import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZAsrManager;
import com.txznet.sdk.TXZAsrManager.AsrComplexSelectCallback;

public class GlobalCmdManager {

    private static final String TAG = GlobalCmdManager.class.getSimpleName();

    private static volatile GlobalCmdManager sInstance;

    private GlobalCmdManager() {

    }

    public static GlobalCmdManager getInstance() {
        if (null == sInstance) {
            synchronized (CustomCmdManager.class) {
                if (null == sInstance) {
                    sInstance = new GlobalCmdManager();
                }
            }
        }

        return sInstance;
    }

    public void registerCmd(int id) {
        registerCmd(CmdProviderFactory.getProvider(id));
    }

    private void registerCmd(ICmdProvider cmdProvider) {
        L.d(TAG, "registerCmd : " + cmdProvider);
        TXZAsrManager.getInstance().useWakeupAsAsr(new GloableCommandListener(cmdProvider));
    }

    public void unregisterCmd(int id) {
        L.d(TAG, "unregisterCmd : " + id);
        TXZAsrManager.getInstance().recoverWakeupFromAsr(createTastId(id));
    }

    private void unregisterCmd(ICmdProvider cmdProvider) {
        L.d(TAG, "unregisterCmd : " + cmdProvider);
        TXZAsrManager.getInstance().recoverWakeupFromAsr(createTastId(cmdProvider));
    }

    private static String createTastId(ICmdProvider cmdProvider) {
        return createTastId(cmdProvider.getId());
    }

    private static String createTastId(int id) {
        return "sky_control_" + String.valueOf(id);
    }

    private static class GloableCommandListener extends AsrComplexSelectCallback {

        private ICmdProvider mCmdProvider;

        public GloableCommandListener(ICmdProvider provider) {
            mCmdProvider = provider;
            for (Cmd cmd : mCmdProvider.getList()) {
                addCommand(cmd.mCmdKey, cmd.mCmdArray);
            }
        }

        @Override
        public String getTaskId() {
            return createTastId(mCmdProvider);
        }

        @Override
        public boolean needAsrState() {
            return false;
        }

        @Override
        public void onCommandSelected(String cmdId, String data) {
            L.d(TAG, "onCommandSelected cmdId=" + cmdId + ", command=" + data);
            for (CmdSpeakable cmdData : mCmdProvider.getList()) {
                L.d(TAG, "CommandListener.cmdData.mCmdKey==" + cmdData.mCmdKey);
                if (cmdData.mCmdKey.equals(cmdId)) {
                    ICommand command = mCmdProvider.getCommand(cmdData);
                    if (command != null) {
                        command.execute();
                    }
                    break;
                }
            }
        }

    }
}
