package com.skyworthauto.speak.remote;

import com.skyworthauto.speak.remote.IdInfo;
import com.skyworthauto.speak.remote.IRemoteCmd;
import com.skyworthauto.speak.remote.CmdInfo;
interface ISpeak{
	void registerGlobalCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void registerCustomCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void unRegisterGlobalCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
	void unRegisterCustomCmd(IRemoteCmd remoteCmd,in IdInfo idInfo);
}