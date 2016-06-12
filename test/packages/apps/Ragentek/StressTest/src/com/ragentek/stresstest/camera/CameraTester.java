package com.ragentek.stresstest.camera;

/**
 * CameraTester
 * 
 * @author yangyang.liu
 * @date 2015/07/22
 */
import android.content.Context;
import com.ragentek.stresstest.BaseTester;

public class CameraTester extends BaseTester {

	private final static String PKGNAME = "com.ragentek.stresstest";
	private final static String CLASSNAME = "com.ragentek.stresstest.camera.CameraBack";
	private final static int CAMERA_REQUEST_CODE = 1000;

	@Override
	public boolean start(Context context) {

		return startActivity(PKGNAME, CLASSNAME, CAMERA_REQUEST_CODE);
	}

	@Override
	public boolean stop(Context context) {

		return stopActivity(CAMERA_REQUEST_CODE);
	}
}
