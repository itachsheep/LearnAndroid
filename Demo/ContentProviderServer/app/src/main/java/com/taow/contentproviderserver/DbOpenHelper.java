package com.taow.contentproviderserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taowei on 2017/8/7.
 * 2017-08-07 23:10
 * ContentProviderServer
 * com.taow.contentproviderserver
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static String dbName = "mysql.db";
    private static int dbVersion = 1;
    private String tableName = "book";
    public DbOpenHelper(Context context) {
        super(context, dbName, null, dbVersion);
        LogUtil.i("DbOpenHelper consturctor.." );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+tableName+"(_id INTEGER PRIMARY KEY, name TEXT)";
        LogUtil.i("DbOpenHelper.onCreate sql: "+sql );
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        if(oldVer == 1 && newVer == 2){
            //从版本1到版本2时，增加了一个字段 desc
            String sql = "alter table ["+tableName+"] add [desc] nvarchar(300)";
            sqLiteDatabase.execSQL(sql);
        }
    }
}
