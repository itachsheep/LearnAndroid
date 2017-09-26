package com.xutil.xutildemo;


import org.xutils.ex.DbException;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.List;

public class TaskManager {
    public static final String DB_NAME = "upgrade.db";
    public static final int DB_VERSION = 3;
    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;


    public TaskManager() {
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName(DB_NAME);
        //db file dir :/data/user/0/com.xutil.xutildemo/files
        LogUtil.i("db file dir :" + App.getInstance().getFilesDir());
        daoConfig.setDbDir(App.getInstance().getFilesDir());
        daoConfig.setDbVersion(DB_VERSION);
        daoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                LogUtil.i("oldVersion:" + oldVersion + " newVersion:" + newVersion);


               /* try {
                    LogUtil.i("oldVersion:" + oldVersion + " newVersion:" + newVersion);



                    if (oldVersion == 1) {
                        db.addColumn(DeviceInfo.class, "deviceTypeNameDesc");
                        db.addColumn(UpgradeTask.class, "uploaddownloadsize");
                    }

                    if (oldVersion == 2) {
                        db.addColumn(UpgradeTask.class, "uploaddownloadsize");
                    }
                }
                catch (DbException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
            }
        });
        dbManager = x.getDb(daoConfig);
    }

    public static TaskManager getTaskManager() {
        return App.getInstance().getTaskManager();
    }

    public UpgradeTask obtainLastTask() {
        UpgradeTask task = null;
        try {
            task = dbManager.selector(UpgradeTask.class).orderBy("id", true).findFirst();
        }
        catch (DbException e) {
            e.printStackTrace();
        }
        return task;
    }



    public void saveObj(Object obj) {
        try {
            // dbManager.saveOrUpdate(obj);
            dbManager.save(obj);
        }
        catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<UpgradeTask> findAll(){
        List<UpgradeTask> list = null;
        try {
            list = dbManager.findAll(UpgradeTask.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateObj(Object obj) {
        try {
            dbManager.update(obj);
        }
        catch (DbException e) {
            e.printStackTrace();
        }
    }
}
