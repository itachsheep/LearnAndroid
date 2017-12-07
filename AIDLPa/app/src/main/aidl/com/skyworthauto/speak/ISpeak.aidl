package com.skyworthauto.speak;
import com.skyworthauto.speak.CmdInfo;
interface ISpeak{
	void registerGlobalCmd(in CmdInfo cmdInfo);
	void registerCustomCmd(in CmdInfo cmdInfo);
	void unRegisterCmd(inout CmdInfo cmdInfo);
}