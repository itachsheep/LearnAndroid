package com.skyworthauto.speak.cmd;

import java.util.Collection;

public interface ICmdProvider {


    int getId();


    Collection<? extends CmdSpeakable> getList();

                                                   
                                       
                                           

    ICommand getCommand(CmdSpeakable cmdData);





}
