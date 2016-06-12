package com.ragentek.stresstest.camerafront;

/**
 * CameraFront Test
 * 
 * @author yangyang.liu
 * @date 2015/07/21
 */
import java.io.File;
import java.io.IOException;
import java.util.List;
import com.ragentek.stresstest.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraFront extends Activity implements SurfaceHolder.Callback {

	private final static int MSG_TAKE_PICTURE = 10;
	private Camera mCamera = null;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	final static String TAG = "CameraFront";
	private Context mContext = null;
	private final String ErrorMsg = " can not save picture!";
	private int pictureNum = 1;

	@Override
	public void finish() {

		stopCamera();
		clean();
		super.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (myHandler.hasMessages(MSG_TAKE_PICTURE)) {
			myHandler.removeMessages(MSG_TAKE_PICTURE);
		}
	}

	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_TAKE_PICTURE:
				post(myRunnable);
				break;

			default:
				break;
			}
		};
	};

	MyRunnable myRunnable = new MyRunnable();

	class MyRunnable implements Runnable {

		@Override
		public void run() {
			takePicture();
			myHandler.sendEmptyMessageDelayed(MSG_TAKE_PICTURE, 5000);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		 getWindow().getAttributes().privateFlags |=
		 WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camera_back);

		/* SurfaceHolder set */
		mSurfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(CameraFront.this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mContext = this;

		myHandler.postDelayed(myRunnable, 2000);
	}

	public void surfaceCreated(SurfaceHolder surfaceholder) {

		logd("surfaceCreated");
		try {
			mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
		} catch (Exception exception) {
			showToast(getString(R.string.msg_camera_fail_open));
			mCamera = null;
		}

		if (mCamera == null) {
			finish();
		} else {
			try {
				mCamera.setPreviewDisplay(mSurfaceHolder);
			} catch (IOException exception) {
				mCamera.release();
				mCamera = null;
				finish();
			}
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int format, int w,
			int h) {

		logd("surfaceChanged");
		startCamera();
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {

		logd("surfaceDestroyed");
		stopCamera();
	}

	private void takePicture() {

		logd("takePicture");
		if (mCamera != null) {
			try {
				mCamera.takePicture(mShutterCallback, rawPictureCallback,
						jpegCallback);
				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
			} catch (Exception e) {
				e.printStackTrace();
				finish();
			}
		} else {
			finish();
		}
	}

	private ShutterCallback mShutterCallback = new ShutterCallback() {

		public void onShutter() {

			logd("onShutter");
		}
	};

	private PictureCallback rawPictureCallback = new PictureCallback() {

		public void onPictureTaken(byte[] _data, Camera _camera) {

		}
	};

	private PictureCallback jpegCallback = new PictureCallback() {

		public void onPictureTaken(byte[] _data, Camera _camera) {

			try {
				_camera.stopPreview();

				Thread.sleep(1000);
				_camera.startPreview();
			} catch (Exception e) {
				Log.e(TAG, e.toString());
				showToast(ErrorMsg);
			}
		}
	};

	private String getStoreName() {
		String name = TAG + "_" + pictureNum + ".jpg";
		pictureNum++;
		File file = new File(Environment.getExternalStorageDirectory(), name);
		if (file.exists()) {
			file.delete();
		}
		return name;
	}

	private void clean() {
		String cmd = "rm -rf /mnt/sdcard/CameraBack*.jpg ";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
			System.out.println("delete over");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final class AutoFocusCallback implements
			android.hardware.Camera.AutoFocusCallback {

		public void onAutoFocus(boolean focused, Camera camera) {

			if (focused) {
				takePicture();
			}
		}
	};

	private void startCamera() {

		if (mCamera != null) {
			try {
				Camera.Parameters parameters = mCamera.getParameters();
				List<Camera.Size> allSize = parameters
						.getSupportedPictureSizes();
				int max = 0, maxHeight = 0, maxWidth = 0;
				for (int count = 0; count < allSize.size(); count++) {
					System.out.println("maxHeight" + maxHeight + "maxWidth"
							+ maxWidth);
					if (max < (allSize.get(count).height * allSize.get(count).width)) {
						maxHeight = allSize.get(count).height;
						maxWidth = allSize.get(count).width;
						max = maxHeight * maxWidth;
						System.out.println("maxHeight =" + maxHeight
								+ "maxWidth" + maxWidth);
					}
				}
				parameters.setPictureSize(maxWidth, maxHeight);
				Camera.Size size = parameters.getPictureSize();
				System.out.println("at last picture heitht" + size.height
						+ "height" + size.width);
				parameters.setPictureFormat(PixelFormat.JPEG);
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
				parameters.setRotation(CameraInfo.CAMERA_FACING_BACK);
				mCamera.setParameters(parameters);
				mCamera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
				loge(e);
			}
		}

	}

	private void stopCamera() {

		if (mCamera != null) {
			try {
				mCamera.stopPreview();
				mCamera.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void logd(Object d) {

		Log.d(TAG, d + "");
	}

	private void loge(Object e) {

		Log.e(TAG, e + "");
	}

	private void showToast(String str) {

		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
