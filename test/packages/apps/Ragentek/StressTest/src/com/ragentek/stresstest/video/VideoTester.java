package com.ragentek.stresstest.video;

/**
 * VideoTester
 * 
 * @author yangyang.liu
 * @date 2015/07/22
 */
import android.content.Context;
import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.utils.DebugUtil;

public class VideoTester extends BaseTester {

	@Override
	public boolean start(Context context) {
		DebugUtil.logd("VideoTester", "video tester start");

		startActivity("com.ragentek.stresstest",
				"com.ragentek.stresstest.video.VideoActivity", 1);
		return true;
	}

	@Override
	public boolean stop(Context context) {
		DebugUtil.logd("VideoTester", "video tester stop");
		stopActivity(1);
		return true;
	}

}
