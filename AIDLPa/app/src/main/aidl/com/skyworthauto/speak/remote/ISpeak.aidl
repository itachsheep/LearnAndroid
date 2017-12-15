package com.skyworthauto.speak.remote;

import com.skyworthauto.speak.remote.IdInfo;
import com.skyworthauto.speak.remote.IRemoteCmd;
import com.skyworthauto.speak.remote.IRadioAM;
import com.skyworthauto.speak.remote.IRadioFM;
import com.skyworthauto.speak.remote.CmdInfo;
interface ISpeak{
	void registerGlobalCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void registerCustomCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void registerCmdForAM(int from, int to,IRadioAM radioCmd);
	void registerCmdForFM(float from, float to,IRadioFM radioCmd);
	void unRegisterGlobalCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void unRegisterCustomCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
}