package com.ragentek.stresstest.camerafront;

/**
 * CameraTest1
 * 
 * @author yangyang.liu
 * @date 2015/07/20
 */
import android.content.Context;
import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.utils.DebugUtil;

public class CameraTester1 extends BaseTester {

	private final static String PKGNAME = "com.ragentek.stresstest";
	private final static String CLASSNAME = "com.ragentek.stresstest.camerafront.CameraFront";
	private final static int CAMERA_REQUEST_CODE = 2001;
	public static String TAG = "CameraFrontTester";

	@Override
	public boolean start(Context context) {
		DebugUtil.logd("TAG", "CameraFront Tester start");

		startActivity(PKGNAME, CLASSNAME, CAMERA_REQUEST_CODE);
		return true;
	}

	@Override
	public boolean stop(Context context) {
		DebugUtil.logd("TAG", "CameraFront Tester stop");

		stopActivity(CAMERA_REQUEST_CODE);
		return true;
	}

}
