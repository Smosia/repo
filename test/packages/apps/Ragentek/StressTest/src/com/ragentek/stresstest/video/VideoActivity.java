package com.ragentek.stresstest.video;

/**
 * Video Test
 * 
 * @author yangyang.liu
 * @date 2015/07/21
 */
import com.ragentek.stresstest.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {

	private VideoView videoView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().getAttributes().privateFlags |=
		 WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.video_view);

		videoView = (VideoView) findViewById(R.id.myVideoView);
		videoView.setMediaController(new MediaController(this));
		videoView.setVideoPath("android.resource://com.ragentek.stresstest/"
				+ R.raw.test_video);
		videoView.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				videoView.start();
			}
		});
		videoView.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (videoView != null) {
			videoView.stopPlayback();
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
}
