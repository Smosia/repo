package com.ragentek.stresstest.vibrate;

/**
 * VibrateTester
 * 
 * @author yangyang.liu
 * @date 2015/07/21
 */
import android.content.Context;
import android.os.Vibrator;
import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.utils.DebugUtil;

public class VibrateTester extends BaseTester {

	private Vibrator vibrator;

	@Override
	public boolean start(Context context) {
		DebugUtil.logd("VibrateTester", "vibrate tester start");
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(new long[] { 1000, 2000 }, 0);
		return true;
	}

	@Override
	public boolean stop(Context context) {
		DebugUtil.logd("VibrateTester", "vibrate tester stop");
		if (vibrator != null) {
			vibrator.cancel();
		}
		return true;
	}

}
