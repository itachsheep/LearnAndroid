package com.skyworthauto.speak.remote;
import com.skyworthauto.speak.remote.IdInfo;
import com.skyworthauto.speak.remote.CmdInfo;
interface IRemoteCmd {
	List<CmdInfo> getList();
	int  getId();
	void onCommand(String cmdKey,String cmdData);
}