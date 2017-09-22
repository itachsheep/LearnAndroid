package com.skyworthauto.speak.cmd;

import android.util.SparseArray;

import com.skyworthauto.speak.util.L;
import com.txznet.sdk.TXZAsrManager;
import com.txznet.sdk.TXZAsrManager.CommandListener;
import com.txznet.sdk.TXZResourceManager;

public class CustomCmdManager {

    private static final String TAG = CustomCmdManager.class.getSimpleName();

    private static volatile CustomCmdManager sInstance;

    private SparseArray<CustomCommandListener> mListenerList =
            new SparseArray<CustomCommandListener>();

    private CustomCmdManager() {

    }

    public static CustomCmdManager getInstance() {
        if (null == sInstance) {
            synchronized (CustomCmdManager.class) {
                if (null == sInstance) {
                    sInstance = new CustomCmdManager();
                }
            }
        }

        return sInstance;
    }

    public void registerCmd(int id) {
        registerCmd(CmdProviderFactory.getProvider(id));
    }

    private void registerCmd(ICmdProvider cmdProvider) {
        if (hasRegistered(cmdProvider)) {
            return;
        }
        L.d(TAG, "registerCmd : " + cmdProvider);
        CustomCommandListener listener = new CustomCommandListener(cmdProvider);
        mListenerList.put(cmdProvider.getId(), listener);

        TXZAsrManager.getInstance().addCommandListener(listener);
        for (Cmd cmd : cmdProvider.getList()) {
            TXZAsrManager.getInstance().regCommand(cmd.mCmdArray, cmd.mCmdKey);
        }
    }

    private boolean hasRegistered(ICmdProvider cmdProvider) {
        return null != mListenerList.get(cmdProvider.getId());
    }

    public void unregisterCmd(int id) {
        unregisterCmd(CmdProviderFactory.getProvider(id));
    }

    private void unregisterCmd(ICmdProvider cmdProvider) {
        L.d(TAG, "unregisterCmd : " + cmdProvider);
        for (Cmd cmd : cmdProvider.getList()) {
            TXZAsrManager.getInstance().unregCommand(cmd.mCmdArray);
        }
        TXZAsrManager.getInstance().removeCommandListener(getListener(cmdProvider.getId()));
        mListenerList.delete(cmdProvider.getId());
    }

    private CommandListener getListener(int id) {
        return mListenerList.get(id);
    }

    private static class CustomCommandListener implements CommandListener {

        private ICmdProvider mCmdProvider;

        public CustomCommandListener(ICmdProvider provider) {
            mCmdProvider = provider;
        }

        @Override
        public void onCommand(String cmd, String data) {
            L.d(TAG, "CommandListener" + this + ", data==" + data);
            for (CmdSpeakable cmdData : mCmdProvider.getList()) {
                L.d(TAG, "CommandListener.cmdData.mCmdKey==" + cmdData.mCmdKey);
                if (cmdData.mCmdKey.equals(data)) {
                    runCmd(cmdData);
                    break;
                }
            }
        }

        private void runCmd(final CmdSpeakable cmdData) {
            TXZResourceManager.getInstance()
                    .speakTextOnRecordWin(cmdData.mSpeakStr, true, new Runnable() {

                        @Override
                        public void run() {
                            ICommand command = mCmdProvider.getCommand(cmdData);
                            if (command != null) {
                                command.execute();
                            }
                        }
                    });
        }
    }

}
