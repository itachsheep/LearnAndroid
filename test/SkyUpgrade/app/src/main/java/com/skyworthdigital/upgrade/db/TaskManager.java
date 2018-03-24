package com.skyworthdigital.upgrade.db;

import com.skyworthdigital.upgrade.common.UpgradeApp;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by SDT14324 on 2017/9/25.
 */

public class TaskManager {
    public static final String DB_NAME = "upgrade.db";
    public static final int DB_VERSION = 3;
    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;

    public TaskManager() {
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName(DB_NAME);
        daoConfig.setDbDir(UpgradeApp.getInstance().getFilesDir());
        daoConfig.setDbVersion(DB_VERSION);
        daoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {

            @Override
            public void onUpgrade(DbManager dbManager, int oldVersion, int newVersion) {
                // TODO: 2017/9/25  避免更新时候删除，先设置listener
                /*try {
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
                    e.printStackTrace();
                }*/
            }
        });
        dbManager = x.getDb(daoConfig);
    }

    public static TaskManager getTaskManager() {
        return UpgradeApp.getInstance().getTaskManager();
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

    public void updateObj(Object obj) {
        try {
            dbManager.update(obj);
        }
        catch (DbException e) {
            e.printStackTrace();
        }
    }


    public DeviceInfo obtainDeviceInfo() {
        DeviceInfo task = null;
        try {
            task = dbManager.selector(DeviceInfo.class).orderBy("id", true).findFirst();
        }
        catch (DbException e) {
            e.printStackTrace();
        }
        return task;
    }
}
