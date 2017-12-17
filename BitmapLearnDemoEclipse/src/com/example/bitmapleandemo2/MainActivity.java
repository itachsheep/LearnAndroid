package com.example.bitmapleandemo2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
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
	 * 1.质量压缩
	 * 原理：通过算法抠掉(同化)了图片中的一些某个些点附近相近的像素，达到降低质量介绍文件大小的目的。
	 * 减小了图片质量
	 * 注意：它其实只能实现对file的影响，对加载这个图片出来的bitmap内存是无法节省的，还是那么大。
	 * 因为bitmap在内存中的大小是按照像素计算的，也就是width*height，对于质量压缩，并不会改变图片的真实的像素（像素大小不会变）。
	 * 
	 * 使用场景：
	 * 			将图片压缩后保存到本地，或者将图片上传到服务器。根据实际需求来。
	 */
	public void qualityCompress(View view){
		Log.i(TAG, "qualityCompress clicked !! ");
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
		//压缩图片
		compressImageToFile(bitmap, new File(sdFile,"qualityCompress_5.jpeg"));
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
		compressBitmapToFileBySize(bitmap, new File(sdFile,"sizeCompress.jpeg"));
	}
	
	/**
	 * 2.尺寸压缩
	 * 通过减少单位尺寸的像素值，正真意义上的降低像素。1020*8880--
	 * 使用场景：缓存缩略图的时候（头像处理）
	 * 
	 * @param bmp
	 * @param file
	 */
	private void compressBitmapToFileBySize(Bitmap bitmap,File file){
		//压缩尺寸倍数，值越大，图片的尺寸就越小
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
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_KITKAT_PICK_IMAGE);
        }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_IMAGE:
				//4.4 以下
				if (data != null) {
                    Uri uri = data.getData();
                    compressImage(uri);
                } else {
                    Log.e("======", "========图片为空======");
                }
				break;

			case REQUEST_KITKAT_PICK_IMAGE:
				//4.4 含以上
				if (data != null) {
                    Uri uri = ensureUriPermission(this, data);
                    compressImage(uri);
                } else {
                    Log.e("======", "====-----==图片为空======");
                }
				break;
			}
		}
	}
}
