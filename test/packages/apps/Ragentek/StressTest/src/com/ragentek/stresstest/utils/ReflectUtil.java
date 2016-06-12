package com.ragentek.stresstest.utils;

import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.audio.ReceiverTester;
import com.ragentek.stresstest.backled.BackLedTester;
import com.ragentek.stresstest.camera.CameraTester;
import com.ragentek.stresstest.camerafront.CameraTester1;
import com.ragentek.stresstest.vibrate.VibrateTester;
import com.ragentek.stresstest.video.VideoTester;

/**
 * util for reflect
 * 
 * @author yangyang.liu
 * @date 2015/07/23
 */
public class ReflectUtil {

	/**
	 * instance an object by className
	 * 
	 * @param className
	 * @return
	 */
	public static Object getClassInstance(String className) {
		BaseTester tester = null;

		if (className.equals("com.ragentek.stresstest.audio.ReceiverTester")) {
			tester = new ReceiverTester();
		} else if (className
				.equals("com.ragentek.stresstest.camera.CameraTester")) {

			tester = new CameraTester();
		} else if (className
				.equals("com.ragentek.stresstest.camerafront.CameraTester1")) {

			tester = new CameraTester1();
		} else if (className
				.equals("com.ragentek.stresstest.vibrate.VibrateTester")) {

			tester = new VibrateTester();
		} else if (className
				.equals("com.ragentek.stresstest.video.VideoTester")) {

			tester = new VideoTester();
		} else if (className
				.equals("com.ragentek.stresstest.backled.BackLedTester")) {

			tester = new BackLedTester();
		}
		return tester;
	}

}
