package com.ragentek.stresstest.backled;

import android.content.Context;

import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.utils.DebugUtil;

public class BackLedTester extends BaseTester{

	@Override
	public boolean start(Context context) {
		DebugUtil.logd("BackLedTester", "BackLed tester start");

		startActivity("com.ragentek.stresstest",
				"com.ragentek.stresstest.backled.BackLedActivity", 1);
		return true;
	}

	@Override
	public boolean stop(Context context) {
		DebugUtil.logd("BackLedTester", "BackLed tester stop");
		stopActivity(1);
		return true;
	}
}
