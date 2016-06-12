package com.ragentek.stresstest.audio;

/**
 * ReceiverTester
 * @author yangyang.liu
 * @date 2015/07/24
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.ragentek.stresstest.BaseTester;
import com.ragentek.stresstest.R;

public class ReceiverTester extends BaseTester {

	private MediaPlayer mp = null;
	private AudioManager am = null;

	@Override
	public boolean start(Context context) {
		am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		if (am != null) {
			am.setMode(AudioManager.MODE_IN_CALL);
		}
		mp = MediaPlayer.create(context, R.raw.test_audio);
		mp.setLooping(true);
		mp.start();
		return true;
	}

	@Override
	public boolean stop(Context context) {
		if (mp != null && mp.isPlaying()) {
			mp.stop();
			mp.release();
		}
		if (am != null) {
			am.setMode(AudioManager.MODE_NORMAL);
		}
		return true;
	}
}
