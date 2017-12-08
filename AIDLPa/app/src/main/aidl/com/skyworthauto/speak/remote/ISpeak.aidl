package com.skyworthauto.speak.remote;
import com.skyworthauto.speak.remote.CmdInfo;
import com.skyworthauto.speak.remote.IRemoteCmd;
interface ISpeak{
	void registerGlobalCmd(IRemoteCmd remoteCmd);
	void registerCustomCmd(IRemoteCmd remoteCmd);
	void unRegisterCmd(IRemoteCmd remoteCmd);
}