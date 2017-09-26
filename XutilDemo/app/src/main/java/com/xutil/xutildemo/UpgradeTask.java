package com.xutil.xutildemo;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by SDT14324 on 2017/9/25.
 */

@Table(name = "UpgradeTask")
public class UpgradeTask {


    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "state")
    private int state;
    @Column(name = "downloadProcess")
    private int downloadProcess;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDownloadProcess() {
        return downloadProcess;
    }

    public void setDownloadProcess(int downloadProcess) {
        this.downloadProcess = downloadProcess;
    }

    @Override
    public String toString() {
        return "UpgradeTask{" +
                "id=" + id +
                ", state=" + state +
                ", downloadProcess=" + downloadProcess +
                '}'+"\n";
    }
}
