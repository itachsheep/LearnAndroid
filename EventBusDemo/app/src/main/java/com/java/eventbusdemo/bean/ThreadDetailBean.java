package com.java.eventbusdemo.bean;

/**
 * Created by SDT14324 on 2017/9/19.
 */

public class ThreadDetailBean {
    private String threadName;
    private long threadId;


    public ThreadDetailBean(String name,long id){
        threadName = name;
        threadId = id;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }
}
