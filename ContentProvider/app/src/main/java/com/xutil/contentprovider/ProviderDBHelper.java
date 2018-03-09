package com.xutil.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProviderDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "upgrade_provider.db";
	private static final int DATABASE_VERSION = 1; 
	public static final String DEVICEINFO_TABLE_NAME = "deviceinfo";

	public ProviderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		LogUtil.log("ProviderDBHelper Creating...");

		db.execSQL("CREATE TABLE " + DEVICEINFO_TABLE_NAME + " ("
				+ CommonUtil.KEY + "TEXT PRIMARY KEY,"
				+ CommonUtil.VALUE + " TEXT" + ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		LogUtil.log("ProviderDBHelper onUpgrade database from version " + oldVersion + " to "
				+ newVersion + ".");
	}

}
