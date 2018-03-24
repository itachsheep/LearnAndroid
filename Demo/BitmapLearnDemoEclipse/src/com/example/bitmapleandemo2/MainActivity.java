package com.example.bitmapleandemo2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import net.bither.util.NativeUtil;

public class MainActivity extends Activity {
	private File sdFile;
    private String TAG = "BitmapCompressActivity";
    private File imageFile;
    
    public static final int REQUEST_PICK_IMAGE = 10011;
    public static final int REQUEST_KITKAT_PICK_IMAGE = 10012;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sdFile = Environment.getExternalStorageDirectory();
        imageFile = new File(sdFile,"target.jpg");
	}
	
	/**
	 * 1.����ѹ��
	 * ԭ��ͨ���㷨�ٵ�(ͬ��)��ͼƬ�е�һЩĳ��Щ�㸽����������أ��ﵽ�������������ļ���С��Ŀ�ġ�
	 * ��С��ͼƬ����
	 * ע�⣺����ʵֻ��ʵ�ֶ�file��Ӱ�죬�Լ������ͼƬ������bitmap�ڴ����޷���ʡ�ģ�������ô��
	 * ��Ϊbitmap���ڴ��еĴ�С�ǰ������ؼ���ģ�Ҳ����width*height����������ѹ����������ı�ͼƬ����ʵ�����أ����ش�С����䣩��
	 * 
	 * ʹ�ó�����
	 * 			��ͼƬѹ���󱣴浽���أ����߽�ͼƬ�ϴ���������������ʵ����������
	 */
	public void qualityCompress(View view){
		Log.i(TAG, "qualityCompress clicked !! ");
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		//ѹ��ͼƬ
		compressImageToFile(bitmap, new File(sdFile,"qualityCompress.jpg"));
	}
	
	private void compressImageToFile(Bitmap bitmap,File file){
		//0~100
		int quality = 50;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality , baos );
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sizeCompress(View view){
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		compressBitmapToFileBySize(bitmap, new File(sdFile,"sizeCompress.jpg"));
	}
	
	/**
	 * 2.�ߴ�ѹ��
	 * ͨ�����ٵ�λ�ߴ������ֵ�����������ϵĽ������ء�1020*8880--
	 * ʹ�ó�������������ͼ��ʱ��ͷ����
	 * 
	 * @param bmp
	 * @param file
	 */
	private void compressBitmapToFileBySize(Bitmap bitmap,File file){
		//ѹ���ߴ籶����ֵԽ��ͼƬ�ĳߴ��ԽС
		int ratio = 4;
		Bitmap result = Bitmap.createBitmap(bitmap.getWidth()/ratio, 
				bitmap.getHeight()/ratio, Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(result);
		RectF rect = new RectF(0, 0, bitmap.getWidth()/ratio, bitmap.getHeight()/ratio);
		canvas.drawBitmap(bitmap, null, rect , null);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void jpegCompress(View view){
		Log.i(TAG, "jpegCompress click");
		/*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_KITKAT_PICK_IMAGE);
        }*/
		BitmapFactory.Options opts = new BitmapFactory.Options(); 
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), opts);
		NativeUtil.compressBitmap(bitmap, new File(imageFile,"jpegCompress.jpg").getAbsolutePath());
	}
	
	
	
	public static Uri ensureUriPermission(Context context, Intent intent) {
        Uri uri = intent.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int takeFlags = intent.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
            context.getContentResolver().takePersistableUriPermission(uri, takeFlags);
        }
        return uri;
    }
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_IMAGE:
				//4.4 ����
				if (data != null) {
                    Uri uri = data.getData();
//                    compressImage(uri);
                } else {
                    Log.e(TAG, "========ͼƬΪ��======");
                }
				break;

			case REQUEST_KITKAT_PICK_IMAGE:
				//4.4 ������
				if (data != null) {
                    Uri uri = ensureUriPermission(this, data);
//                    compressImage(uri);
                } else {
                    Log.e(TAG, "====-----==ͼƬΪ��======");
                }
				break;
			}
		}
	}
}
