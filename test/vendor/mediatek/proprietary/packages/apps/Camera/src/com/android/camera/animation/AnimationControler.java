package com.android.camera.animation;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import com.android.camera.ComboPreferences;
import android.content.SharedPreferences;
import android.view.ViewGroup.LayoutParams;
import com.mediatek.camera.setting.SettingUtils;
import com.mediatek.camera.setting.SettingConstants;
import com.android.camera.bridge.CameraDeviceCtrl;

import android.os.Handler;
import android.os.Message;

public class AnimationControler {
	private static final int CAMERA_FRONT_ID = 0;
	private static final int CAMERA_BACK_ID = 1;

	private ImageView mSwitchImageView;
	private Bitmap mAnimBitmap;
	private View mOverlayView;

	private Context mContext;

	private Listener mListener;

	private boolean mAnimationEnable = false;

	//private int mScreenH = 0;
	//private int mScreenW = 0;
	//private int height4_3 = 0;
	//private int mNextCameraId = 0;

	private boolean mPhotoDecoded = false;
	private static final int MSG_PHOTO_DECODED = 1;
	private static final int MSG_SWITCH_CAMERA_END = 2;
	private boolean mSwitched = false;
	private long duration = 0;
	private Handler mHandler = null;
	private boolean mAnimationEnd = false;

	public AnimationControler(ImageView switchImageView, View overView,
			Context context) {
		mSwitchImageView = switchImageView;
		mOverlayView = overView;
		mContext = context;

		//int[] size = getScreenSize(context);
		//mScreenH = size[1];
		//mScreenW = size[0];
		//height4_3 = getHeight4_3(mScreenW);

		if (mSwitchImageView == null || mOverlayView == null
				|| mContext == null) {
			throw new NullPointerException(
					"AnimationControler args: mSwitchImageView "
							+ "or mOverlayView or mContext can't be null");
		}

		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch(msg.what) {
				case MSG_PHOTO_DECODED:
					duration = System.currentTimeMillis() - duration;
					Log.d("sqm", "MSG_PHOTO_DECODED: duration---------" + duration);

					mPhotoDecoded = true;
					mSwitchImageView.setVisibility(View.VISIBLE);
					mOverlayView.setVisibility(View.VISIBLE);
					Bitmap bm = (Bitmap)msg.obj;
					mSwitchImageView.setImageBitmap(bm);
					
					//if (mSwitched) {
						startAnimation();
					//}
					break;

				case MSG_SWITCH_CAMERA_END:
					Log.d("sqm", "MSG_SWITCH_CAMERA_END---------------onSwitchCameraEnd");
					onSwitchCameraEnd();
					break;
				}
			}
			
		};
	}

	public void setCameraSwitchState(boolean switched) {
		Log.d("sqm", "setCameraSwitchState---------------switched="+switched);
		mSwitched = switched;
		if (mSwitched && mAnimationEnd) {
			//startAnimation();
			mHandler.sendEmptyMessageDelayed(MSG_SWITCH_CAMERA_END, 20);
			if (mListener != null) {
				mListener.onAnimationEnd();
			}
		}
		
	}

	public void setListener(Listener listener) {
		mListener = listener;
	}

	private int[] getScreenSize(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int[] wh = new int[2];
		wh[0] = dm.widthPixels;
		wh[1] = dm.heightPixels;
		Log.d("sqm", "getScreenSize() ----w = "+wh[0] + ", h=" + wh[1]);
		return wh;
	}

	private int getHeight4_3(int screenW) {
		return (int)(4.0f / 3 * screenW);
	}

	private void setAnimationViewSize(int width, int height) {
		/*ComboPreferences pref = new ComboPreferences(mContext);
		SharedPreferences curShare = pref.getSharedPreference(mContext, curCameraId);
		String ratioStr = curShare.getString(SettingConstants.KEY_PICTURE_RATIO, null);
		double ratio = Double.parseDouble(ratioStr);
		LayoutParams params = mSwitchImageView.getLayoutParams();
		if (SettingUtils.PICTURE_RATIO_4_3.equals(ratioStr)) {
			params.height = height4_3;
		} else {
			params.height = (int)Math.round(ratio * mScreenW);//mScreenH;
		}*/
		LayoutParams params = mSwitchImageView.getLayoutParams();
		params.width = width;
		params.height = height;
		mSwitchImageView.setLayoutParams(params);
	}

	public void prepareAnimationIcon(final Camera camera, 
			final boolean isFrontCamera, final int nextCameraId, 
				final CameraDeviceCtrl ctrl, int previewWidth, int previewHeight) {
		Log.d("sqm", "prepareAnimationIcon-------isFrontCamera=" + isFrontCamera
			+ ", nextCameraId=" + nextCameraId + ",previewWidth=" 
			+ previewWidth + ", previewHeight=" + previewHeight);

		duration = System.currentTimeMillis();

		//mNextCameraId = nextCameraId;
		//int curCameraId = isFrontCamera ? 1 : 0;
		//setAnimationViewHeight(curCameraId);
		setAnimationViewSize(previewWidth, previewHeight);

		mAnimationEnable = true;
		mAnimationEnd = false;
		mPhotoDecoded = false;
		mSwitched = false;
		//Camera.Parameters parameters = camera.getParameters();
		//final int picFormat = parameters.getPictureFormat();
		//parameters.setPictureFormat(ImageFormat.NV21);
		//parameters.setPreviewFormat(ImageFormat.NV21);
		//camera.setParameters(parameters);
		//camera.setPreviewCallback(new PreviewCallback() {
		camera.setOneShotPreviewCallback(new PreviewCallback() {

			@Override
			public void onPreviewFrame(byte[] data, Camera cam) {
				Log.d("sqm", "onPreviewFrame---------------");
				// saveBitmap(mContext, data);

				if (!mAnimationEnable) {
					return;
				}
				mAnimationEnable = false;

				//camera.setPreviewCallback(null);
				camera.setOneShotPreviewCallback(null);
				Size size = camera.getParameters().getPreviewSize();
				ctrl.closeCamera();
				
				//Camera.Parameters params = camera.getParameters();
				//params.setPictureFormat(picFormat);
				//camera.setParameters(params);
				
				// To switch camera
				if (mListener != null) {
					mListener.onAnimationStarted(nextCameraId);
				}
				//applyScaleInnerAnimation();

				new PhotoDecodeThread(data, size, isFrontCamera).start();
			}
		});
	}

	public void startAnimation() {
		Log.d("sqm", "startAnimation---------");
		applyScaleInnerAnimation();
	}

	private Bitmap asyncDecodePhoto(final boolean isFrontCamera, byte[] data, Size size) {
		Bitmap bm = null;
		if (isFrontCamera) {
			bm = decodeToBitMap(data, CAMERA_FRONT_ID, size);
			//Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			//mSwitchImageView.setImageBitmap(bm);
			//mAnimBitmap = BlurUtils.blurBitmap(scaleBitmap(bm, 80, 160), mContext);
			//mAnimBitmap = BlurUtils.blurBitmap(bm, mContext);
			mAnimBitmap = BlurUtils.doBlur(scaleBitmap(bm, 100, 200), 25, false);
			
			//applyRotation(CAMERA_FRONT_ID, 180, 270);
			//applyScaleInnerAnimation();
		} else {
			bm = decodeToBitMap(data, CAMERA_BACK_ID, size);
			//Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			//mSwitchImageView.setImageBitmap(bm);
			//mAnimBitmap = BlurUtils.blurBitmap(scaleBitmap(bm, 80, 160), mContext);
			//mAnimBitmap = BlurUtils.blurBitmap(bm, mContext);
			mAnimBitmap = BlurUtils.doBlur(scaleBitmap(bm, 100, 200), 25, false);
			//applyRotation(CAMERA_BACK_ID, 360, 270);
			//applyScaleInnerAnimation();
		}

		return bm;
	}

	private Bitmap decodeToBitMap(byte[] data, int cameraIndex, Size size) {
		//Size size = cam.getParameters().getPreviewSize();
		try {
			YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,
					size.height, null);
			Log.w("sqm", size.width + " ======== " + size.height);
			if (image != null) {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				image.compressToJpeg(new Rect(0, 0, size.width, size.height),
						100, stream);
				Bitmap bmp = BitmapFactory.decodeByteArray(
						stream.toByteArray(), 0, stream.size());
				Log.w("sqm", bmp.getWidth() + " " + bmp.getHeight());
				Log.w("sqm",
						(bmp.getPixel(100, 100) & 0xff) + "  "
								+ ((bmp.getPixel(100, 100) >> 8) & 0xff) + "  "
								+ ((bmp.getPixel(100, 100) >> 16) & 0xff));
				//saveBitmap(mContext, stream.toByteArray());// for test
				stream.close();

				// rotate bitmap
				Matrix m = new Matrix();
				android.graphics.Camera camera = new android.graphics.Camera();
				if (cameraIndex == 0) {
					//m.postRotate(-90);
					camera.save();
					camera.rotateZ(90);
					camera.rotateX(180);
			        camera.getMatrix(m);
			        camera.restore();
				} else if (cameraIndex == 1) {
					m.postRotate(90);
				}

				bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
						bmp.getHeight(), m, true);
				return bmp;
			}
		} catch (Exception ex) {
			Log.e("sqm", "Error:" + ex.getMessage());
		}

		return null;
	}
	
	private Bitmap scaleBitmap(Bitmap bm, int w, int h) {
		Bitmap b = bm;
		int width = b.getWidth();
		int height = b.getHeight();
		Log.d("sqm", "width="+width+", height=" + height);
		float scaleW = w * 1.0f / width;
		float scaleH = h * 1.0f / height;
		
		Matrix m = new Matrix();
		m.postScale(scaleW, scaleH);
		Bitmap bb = Bitmap.createBitmap(b, 0, 0, width, height, m, true);
		Log.d("sqm", "bb:width="+bb.getWidth()+", height=" + bb.getHeight());
		return bb;
	}

	private void applyRotation(int position, float start, float end) {
		// Find the center of the container
		final float centerX = mSwitchImageView.getWidth() / 2.0f;
		final float centerY = mSwitchImageView.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Animation rotation = AnimationFactory.createAnimationFor3DRotate(
				start, end, centerX, centerY, 510.0f, true);
		rotation.setDuration(300);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(position));

		mSwitchImageView.startAnimation(rotation);
	}

	private final class DisplayNextView implements Animation.AnimationListener {
		private final int mPosition;

		private DisplayNextView(int position) {
			mPosition = position;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			mSwitchImageView.setImageBitmap(mAnimBitmap);
			mSwitchImageView.post(new SwapViews(mPosition));
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private final class SwapViews implements Runnable {
		private final int mPosition;

		public SwapViews(int position) {
			mPosition = position;
		}

		public void run() {
			final float centerX = mSwitchImageView.getWidth() / 2.0f;
			final float centerY = mSwitchImageView.getHeight() / 2.0f;
			Animation rotation;

			if (mPosition == 0) {
				rotation = AnimationFactory.createAnimationFor3DRotate(270,
						360, centerX, centerY, 510.0f, false);
			} else {
				rotation = AnimationFactory.createAnimationFor3DRotate(270,
						180, centerX, centerY, 510.0f, false);
			}

			rotation.setDuration(200);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());
			rotation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					Log.d("sqm", "onAnimationEnd---------1");
					mSwitchImageView.setImageBitmap(null);
					mSwitchImageView.setVisibility(View.GONE);
					mOverlayView.setVisibility(View.GONE);
				}

				@Override
				public void onAnimationRepeat(Animation anim) {
				}

				@Override
				public void onAnimationStart(Animation anim) {
				}
			});

			mSwitchImageView.startAnimation(rotation);
		}
	}
	
	/** Apply the scale animation */
	private void applyScaleInnerAnimation() {
		Log.d("sqm", "applyScaleInnerAnimation---------");
		// The animation listener is used to trigger the next animation
		final Animation anim = AnimationFactory.createScaleInnerAnimation();
		anim.setDuration(300);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateInterpolator());
		anim.setAnimationListener(new DisplayNextAnimation());
		//anim.setStartOffset(500);

		mSwitchImageView.startAnimation(anim);
	}
	
	private final class DisplayNextAnimation implements Animation.AnimationListener {

		private DisplayNextAnimation() {
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			//setAnimationViewSize(mNextCameraId);
			mSwitchImageView.setImageBitmap(mAnimBitmap);
			mSwitchImageView.post(new SwapAnimation());
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	private final class SwapAnimation implements Runnable {

		public SwapAnimation() {
		}

		public void run() {
			Animation anim = AnimationFactory.createScaleOuterAnimation();
			anim.setDuration(300);
			anim.setFillAfter(true);
			anim.setInterpolator(new DecelerateInterpolator());
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					Log.d("sqm", "onAnimationEnd---------1");
					/*mSwitchImageView.setImageBitmap(null);
					mSwitchImageView.setVisibility(View.GONE);
					mOverlayView.setVisibility(View.GONE);*/
					if (mSwitched) {
						mHandler.sendEmptyMessageDelayed(MSG_SWITCH_CAMERA_END, 20);
						if (mListener != null) {
							mListener.onAnimationEnd();
						}
					}
					mAnimationEnd = true;
					
				}

				@Override
				public void onAnimationRepeat(Animation anim) {
				}

				@Override
				public void onAnimationStart(Animation anim) {
				}
			});

			mSwitchImageView.startAnimation(anim);
		}
	}

	public void onSwitchCameraEnd() {
		mSwitchImageView.setImageBitmap(null);
		mSwitchImageView.setVisibility(View.GONE);
		mOverlayView.setVisibility(View.GONE);
	}

	public interface Listener {
		void onAnimationStarted(int nextCameraId);
		void onAnimationEnd();
	}


	// For test
	private void saveBitmap(Context context, byte[] buffer) {
		java.io.FileOutputStream fos = null;
		try {
			fos = context.openFileOutput("112.png", Context.MODE_WORLD_WRITEABLE);
			fos.write(buffer);
			fos.flush();
		} catch (java.io.FileNotFoundException e) {
			Log.w("sqm", "saveBitmap:--------FileNotFoundException");
			e.printStackTrace();
		} catch (java.io.IOException e) {
			Log.w("sqm", "saveBitmap:--------IOException");
			e.printStackTrace();
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	class PhotoDecodeThread extends Thread {
		byte[] mData = null;
		Size mSize = null;
		boolean mIsFrontCamera;
		
		public PhotoDecodeThread(byte[] data, Size size, boolean isFrontCamera) {
			mData = data;
			mSize = size;
			mIsFrontCamera = isFrontCamera;
		}
		
		@Override
		public void run() {
			Log.d("sqm", "PhotoDecodeThread.run()---------");
			Bitmap bm = asyncDecodePhoto(mIsFrontCamera, mData, mSize);
			mHandler.obtainMessage(MSG_PHOTO_DECODED, bm).sendToTarget();
			
		}
		
	}

}
