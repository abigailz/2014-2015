package com.tsv.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tsv.R;
import com.tsv.biz.QuestionService;
import com.tsv.biz.UserService;
import com.tsv.biz.impl.QuestionServiceImpl;
import com.tsv.biz.impl.UserServiceImpl;
import com.tsv.closure.Constant;
import com.tsv.util.GPS;

public class MainActivity extends Activity {

	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_ZOOM = 2; // 缩放
	private static final int PHOTO_RESOULT = 3;// 结果
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private final String photoPath = "/photo/";
	private ImageView imageView = null;
	/*** 相册 **/
	private Button btnTakePicaure;
	/*** 拍照 ***/
	private Button btnCamera;
	private Button btnSelectImage;

	private final String tag = "camer";

	private QuestionService fileServiceImpl;
	private UserService userServiceImpl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d(tag, "onCreate start camer");
		imageView = (ImageView) findViewById(R.id.imageView);

		btnTakePicaure = (Button) findViewById(R.id.btnTakePicture);
		btnTakePicaure.setOnClickListener(onClickListener);

		btnCamera = (Button) findViewById(R.id.btnCamera);
		btnCamera.setOnClickListener(onClickListener);

		btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		btnSelectImage.setOnClickListener(onClickListener);
		
		initData();
	}

	private void initData() {
		Constant.MobileInfo.Build_MODEL = android.os.Build.MODEL;
		Constant.MobileInfo.VERSION_RELEASE = android.os.Build.VERSION.RELEASE;
		
		fileServiceImpl = new QuestionServiceImpl();
		userServiceImpl = new UserServiceImpl();
	}

	private final View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.d(tag, "onClick " + v);
			// 相册
			if (v == btnTakePicaure) {
				// Action为Intent.ACTION_GET_CONTENT
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTO_ZOOM);
				return;
			} 
			
			// 拍照
			if (v == btnCamera) {
				final String photoName = System.currentTimeMillis() + ".jpg";
				File f = Environment.getExternalStorageDirectory();
//				String type = Environment.DIRECTORY_DCIM;
//				File path = Environment.getExternalStoragePublicDirectory(type);
				File file = new File(f + photoPath);
				if (!file.exists()) {
					file.mkdirs();
				}

				final String name = f + photoPath + photoName;
				Uri uri = Uri.fromFile(new File(name));
				Log.d(tag, "filePath " + name);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
				startActivityForResult(intent, PHOTO_GRAPH);
				return;
			}

			// 选择图片
			if (v == btnSelectImage) {
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				//intent.putExtra("result", "请求参数错误");
				startActivity(intent);
				
				/*** * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的 */
//				Intent intent = new Intent();
//				intent.setType("image/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(intent, 1);
				return;
			}

			return;
		}
	};

	/**
	 * 对回调函数进行处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(tag, "onActivityResult :resultCode=" + resultCode);
		if(Activity.RESULT_OK != resultCode){
			Log.d(tag,"request failed.");
			return;
		}
		
		switch (requestCode) {
		case PHOTO_GRAPH:// 拍照
			process_graph(data);
			break;
		case PHOTO_ZOOM:// 相册
			process_zoom(data);
			break;
		case PHOTO_RESOULT:// 处理结果
			if (data == null) {
				return;
			}

			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
				// 此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
				imageView.setImageBitmap(photo); // 把图片显示在ImageView控件上
			}
			break;
		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void process_graph(Intent data){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		final String fileName = format.format(new Date()) + ".jpg";
		
		// 设置文件保存路径 /storage/emulated/0/temp.jpg
		//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换  
		File file = new File(Environment.getExternalStorageDirectory(),"image.jpg");
		Uri uri = Uri.fromFile(file);
		
		Log.d(tag, "picPath = " + uri);
		
		if(data != null){
			//这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片  
			Bundle extras = data.getExtras();  
			Bitmap bitmap = extras.getParcelable("data");  
            if (bitmap != null) {  
                //  deal image
            	fileServiceImpl.savePic(bitmap, file.getAbsolutePath(),fileName);
            } 
		}
		 
		// file:///storage/emulated/0/temp.jpg
		cropImage(uri,200,200,PHOTO_RESOULT);
		return;
	}
	
	
	private void process_zoom(Intent data){
		if (data == null) {
			return;
		}
		// TODO 相册多文件选择
		// content://media/external/images/media/2287
		Uri uri = data.getData();
		if(uri == null){
			Log.d(tag, "picture is null.");
			return;
		}

		String image = uriChangeFile(uri);
		// 图片上传
//		fileServiceImpl.upload(image);
		
		// 登录
		String context = "{\"loc\":\""+new GPS(this).getGPS()+"\"}";
		String str = userServiceImpl.login(context);
		Log.d(tag, str);
		if(str == null){
			
		}
		// 相册缩放图片
		cropImage(uri, 200, 200, PHOTO_RESOULT);
	}
	
	private String uriChangeFile(Uri uri){
		if ( null == uri ){ 
			return null;
		}

		//final String scheme = uri.getScheme();
		String[] proj = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor.getString(actual_image_column_index);
		
		return img_path;
	}
	
	/**
	 * 截取图片  
	 * @param uri
	 * @param outputX
	 * @param outputY
	 * @param requestCode
	 */
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode){  
    	Log.d(tag, "picture uri:" + uri.getPath());
        //裁剪图片意图  
        Intent intent = new Intent("com.android.camera.action.CROP");    
        intent.setDataAndType(uri, "image/*");    
        intent.putExtra("crop", "true");  
        //裁剪框的比例，1：1  
        intent.putExtra("aspectX", 1);    
        intent.putExtra("aspectY", 1);  
        //裁剪后输出图片的尺寸大小  
        intent.putExtra("outputX", outputX);     
        intent.putExtra("outputY", outputY);  
        //图片格式  
        intent.putExtra("outputFormat", "JPEG");  
        intent.putExtra("noFaceDetection", true);  
        intent.putExtra("return-data", true);    
        startActivityForResult(intent, requestCode);  
        return;
    } 
	/**
	 * 显示提示文字
	 * 
	 * @param mess
	 */
	private void showDialog(String mess) {
		new AlertDialog.Builder(MainActivity.this).setTitle("Message")
				.setMessage(mess)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}
}