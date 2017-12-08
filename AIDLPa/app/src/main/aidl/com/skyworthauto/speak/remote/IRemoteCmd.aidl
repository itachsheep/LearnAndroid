package com.skyworthauto.speak.remote;
import com.skyworthauto.speak.remote.CmdInfo;
interface IRemoteCmd {
	List<CmdInfo> getList();
	String getId();
	void onCommand(String cmdKey,String cmdData);
}