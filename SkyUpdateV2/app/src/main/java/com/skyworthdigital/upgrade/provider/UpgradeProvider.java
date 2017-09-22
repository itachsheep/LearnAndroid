package com.skyworthdigital.upgrade.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.skyworthdigital.upgrade.util.CommonUtil;

public class UpgradeProvider extends ContentProvider{

	private ProviderDBHelper mOpenHelper;
	
	private static final int DEVICEINFO = 1;
	private static final int DEVICEINFO_ID = 2;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static HashMap<String, String> sDeviceInfoProjectionMap;
	
	static {
		sURIMatcher.addURI(CommonUtil.AUTHORITY, CommonUtil.CONTENT_PATH, DEVICEINFO);
		sURIMatcher.addURI(CommonUtil.AUTHORITY, CommonUtil.CONTENT_PATH + "/#", DEVICEINFO_ID);
		
		sDeviceInfoProjectionMap = new HashMap<String, String>();
		sDeviceInfoProjectionMap.put(CommonUtil.KEY, CommonUtil.KEY);
		sDeviceInfoProjectionMap.put(CommonUtil.VALUE, CommonUtil.VALUE);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mOpenHelper = new ProviderDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		String orderBy = sortOrder;;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sURIMatcher.match(uri)) {
		case DEVICEINFO:
			qb.setTables(ProviderDBHelper.DEVICEINFO_TABLE_NAME);
			qb.setProjectionMap(sDeviceInfoProjectionMap);
			break;

		case DEVICEINFO_ID:
			qb.setTables(ProviderDBHelper.DEVICEINFO_TABLE_NAME);
			qb.setProjectionMap(sDeviceInfoProjectionMap);
			qb.appendWhere(CommonUtil.KEY + "='" 
					+ uri.getPathSegments().get(1) + "'");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
	     switch (sURIMatcher.match(uri)) {
         case DEVICEINFO:
                 return CommonUtil.CONTENT_TYPE;
         case DEVICEINFO_ID:
             return CommonUtil.CONTENT_ITEM_TYPE;
         default:
                 throw new IllegalArgumentException("Unknown URI"+uri);
         }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        
        long rowId = 0;
        switch (sURIMatcher.match(uri)) {
		case DEVICEINFO:
			rowId = db.insert(ProviderDBHelper.DEVICEINFO_TABLE_NAME, null, values);
	        if (rowId > 0) {
	            Uri noteUri = ContentUris.withAppendedId(CommonUtil.CONTENT_URI, rowId);
	            getContext().getContentResolver().notifyChange(noteUri, null);
	            return noteUri;
	        }
			break;
			
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

        throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sURIMatcher.match(uri)) {
        case DEVICEINFO:
            count = db.delete(ProviderDBHelper.DEVICEINFO_TABLE_NAME, selection, selectionArgs);
            break;

        case DEVICEINFO_ID:
            String key = uri.getPathSegments().get(1);
            count = db.delete(ProviderDBHelper.DEVICEINFO_TABLE_NAME, 
            		CommonUtil.KEY + "=" + key
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            break;
            
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sURIMatcher.match(uri)) {
        case DEVICEINFO:
            count = db.update(ProviderDBHelper.DEVICEINFO_TABLE_NAME, values, 
            		selection, selectionArgs);
            break;

        case DEVICEINFO_ID:
            String title = uri.getPathSegments().get(1);
            count = db.update(ProviderDBHelper.DEVICEINFO_TABLE_NAME, 
            		values, CommonUtil.KEY + "=" + title
                    + (!TextUtils.isEmpty(selection) ? 
                    		" AND (" + selection + ')' : ""), selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}
	
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        
        long rowId = 0;
        int count = 0;
        switch (sURIMatcher.match(uri)) {
		case DEVICEINFO:
			db.beginTransaction();
			
			for (ContentValues value : values) {
				rowId = db.insert(ProviderDBHelper.DEVICEINFO_TABLE_NAME, null, value);
				if (rowId > 0) {
					count++;
				}
			}
	        
	        db.setTransactionSuccessful();
        	db.endTransaction();
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

        return count;
	}

}
