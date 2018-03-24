package net.bither.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.util.Log;

public class NativeUtil {
	private static String TAG = "BitmapCompressActivity.NativeUtil";
	
	static {
		System.loadLibrary("jpegbither");
		System.loadLibrary("bitherjni");
	}
	
	
	public static void compressBitmap(Bitmap bitmap,String filePath){
		Log.i(TAG, "jpegCompressImage" );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 20;
     // JNI调用保存图片到SD卡 这个关键
        NativeUtil.saveBitmap(bitmap, options, filePath, true);
	}
	
	/**
     * 调用native方法
     *
     * @param bit
     * @param quality
     * @param fileName
     * @param optimize
     * @Description:函数描述
     */
    public static void saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
    }
    
    /**
     * 调用底层 bitherlibjni.c中的方法
     *
     * @param bit
     * @param w
     * @param h
     * @param quality
     * @param fileNameBytes
     * @param optimize
     * @return
     * @Description:函数描述
     */
    public static native String compressBitmap(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes,
                                                boolean optimize);


}
