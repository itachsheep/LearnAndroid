package com.xutil.contentprovider;

import android.net.Uri;

/**
 * Created by SDT14324 on 2017/10/11.
 */

public class CommonUtil {
    public static final String KEY = "key";

    public static final String VALUE = "value";

    public static final String CONTENT_PATH = "deviceinfo";
    public static final String AUTHORITY = "com.skyworthdigital.upgrade";

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.skyworthdigital.ott.deviceinfo";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.skyworthdigital.ott.deviceinfo";

}
