
package com.skyworthauto.speak.cmd;

public class CustomCommand implements ICommand {

    private CmdSpeakable mData;

    public CustomCommand(CmdSpeakable data) {
        mData = data;
    }

    @Override
    public void execute() {
        runCmd(mData);
    }

    private void runCmd(final CmdSpeakable cmdData) {
        cmdData.onRunBefore();
        cmdData.run();
    }

}
