package com.xutil.xutildemo.db;

import com.xutil.xutildemo.utils.App;
import com.xutil.xutildemo.utils.LogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by SDT14324 on 2017/10/11.
 */

public class StuManager {

    private DbManager dbManager;
    private DbManager.DaoConfig daoConfig;

    public StuManager(){
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("student.db");
        daoConfig.setDbDir(App.getInstance().getFilesDir());
        daoConfig.setDbVersion(1);
        daoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                LogUtil.i("oldVersion : "+oldVersion+", newVersion: "+newVersion);
                try {
                    LogUtil.i("add xxxx ");
                    db.addColumn(Student.class,"haha");
                    db.addColumn(Student.class,"sex");
                } catch (DbException e) {
                    LogUtil.i("update db error: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        dbManager = x.getDb(daoConfig);
    }

    public void updateDb(){
        daoConfig.setDbVersion(2);
        dbManager = x.getDb(daoConfig);
    }

    public void addColumn(){
        try {
            LogUtil.i("addColumn  ");
            dbManager.addColumn(Student.class,"sex");
        } catch (DbException e) {
            LogUtil.i("addColumn error: "+e.getMessage());
            e.printStackTrace();
        }

    }

    public Student obtainLastStudent(){
//        dbManager.findFirst(Student.class);
        Student student = null;
        try {
            student = dbManager.selector(Student.class).orderBy("_id").findFirst();
        } catch (DbException e) {
            LogUtil.i("obtainLastStudent db error: "+e.getMessage());
            e.printStackTrace();
        }
        return student;
    }

    public UpgradeTask obtainLastUpgradeTask(){
        UpgradeTask task = null;
        try {
            task = dbManager.selector(UpgradeTask.class).orderBy("_id").findFirst();
        } catch (DbException e) {
            LogUtil.i("obtainLastStudent db error: "+e.getMessage());
            e.printStackTrace();
        }
        return task;
    }

    public void saveObj(Object obj){
        try {
            dbManager.save(obj);
        } catch (DbException e) {
            LogUtil.i("saveObj db error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Student> findAll(){
        List<Student> list = null;
        try {
            list = dbManager.findAll(Student.class);
        } catch (DbException e) {
            LogUtil.i("findAll db error: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void updateObj(Object obj){
        try {
            dbManager.update(obj);
        } catch (DbException e) {
            LogUtil.i("updateObj db error: "+e.getMessage());
            e.printStackTrace();
        }
    }



}
