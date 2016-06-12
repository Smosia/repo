package com.rgk.pedometer;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

/**
 * Plays the default ringtone. Uses {@link Ringtone} in a separate thread so
 * that this class can be used from the main thread.
 */
class AsyncRingtonePlayer {
	// Message codes used with the ringtone thread.
	private static final int EVENT_PLAY = 1;
	private static final int EVENT_STOP = 2;
	private static final int EVENT_REPEAT = 3;

	public static final String KEY_DEFAULT_NOTIFICATION = "mtk_audioprofile_default_notification";

	// The interval in which to restart the ringer.
	// private static final int RESTART_RINGER_MILLIS = 3000;

	/** Handler running on the ringtone thread. */
	private Handler mHandler;

	/** The current ringtone. Only used by the ringtone thread. */
	private Ringtone mRingtone;

	/**
	 * The context.
	 */
	private final Context mContext;

	AsyncRingtonePlayer(Context context) {
		mContext = context;
	}

	/** Plays the ringtone. */
	public void play(Uri ringtone) {
		Log.d(PedometerApplication.TAG, "Posting play.");
		postMessage(EVENT_PLAY, true /* shouldCreateHandler */, ringtone);
	}

	/** Stops playing the ringtone. */
	public void stop() {
		Log.d(PedometerApplication.TAG, "Posting stop.");
		postMessage(EVENT_STOP, false /* shouldCreateHandler */, null);
	}

	/**
	 * Posts a message to the ringtone-thread handler. Creates the handler if
	 * specified by the parameter shouldCreateHandler.
	 * 
	 * @param messageCode
	 *            The message to post.
	 * @param shouldCreateHandler
	 *            True when a handler should be created to handle this message.
	 */
	private void postMessage(int messageCode, boolean shouldCreateHandler,
			Uri ringtone) {
		synchronized (this) {
			if (mHandler == null && shouldCreateHandler) {
				mHandler = getNewHandler();
			}

			if (mHandler == null) {
				Log.d(PedometerApplication.TAG, "postMessage()------------messageCode=" + messageCode);
			} else {
				mHandler.obtainMessage(messageCode, ringtone).sendToTarget();
			}
		}
	}

	/**
	 * Creates a new ringtone Handler running in its own thread.
	 */
	private Handler getNewHandler() {
		HandlerThread thread = new HandlerThread("ringtone-player");
		thread.start();

		return new Handler(thread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case EVENT_PLAY:
					handlePlay((Uri) msg.obj);
					break;
				case EVENT_REPEAT:
					handleRepeat();
					break;
				case EVENT_STOP:
					handleStop();
					break;
				}
			}
		};
	}

	/**
	 * Starts the actual playback of the ringtone. Executes on ringtone-thread.
	 */
	private void handlePlay(Uri ringtoneUri) {
		// don't bother with any of this if there is an EVENT_STOP waiting.
		if (mHandler.hasMessages(EVENT_STOP)) {
			return;
		}

		Log.i(PedometerApplication.TAG, "Play ringtone.");

		if (mRingtone == null) {
			mRingtone = getRingtone(ringtoneUri);

			// Cancel everything if there is no ringtone.
			if (mRingtone == null) {
				handleStop();
				return;
			}
		}

		handleRepeat();
	}

	private void handleRepeat() {
		if (mRingtone == null) {
			return;
		}

		if (mRingtone.isPlaying()) {
			Log.d(PedometerApplication.TAG, "Ringtone already playing.");
		} else {
			mRingtone.play();
			Log.i(PedometerApplication.TAG, "Repeat ringtone.");
		}

		// Repost event to restart ringer in {@link RESTART_RINGER_MILLIS}.
		/*
		 * synchronized(this) { if (!mHandler.hasMessages(EVENT_REPEAT)) {
		 * mHandler.sendEmptyMessageDelayed(EVENT_REPEAT,
		 * RESTART_RINGER_MILLIS); } }
		 */
	}

	/**
	 * Stops the playback of the ringtone. Executes on the ringtone-thread.
	 */
	private void handleStop() {
		Log.i(PedometerApplication.TAG, "Stop ringtone.");

		if (mRingtone != null) {
			Log.d(PedometerApplication.TAG, "Ringtone.stop() invoked.");
			mRingtone.stop();
			mRingtone = null;
		}

		synchronized (this) {
			// At the time that STOP is handled, there should be no need for
			// repeat messages in the
			// queue.
			mHandler.removeMessages(EVENT_REPEAT);

			if (mHandler.hasMessages(EVENT_PLAY)) {
				Log.v(PedometerApplication.TAG, "Keeping alive ringtone thread for subsequent play request.");
			} else {
				mHandler.removeMessages(EVENT_STOP);
				mHandler.getLooper().quitSafely();
				mHandler = null;
				Log.v(PedometerApplication.TAG, "Handler cleared.");
			}
		}
	}

	private Ringtone getRingtone(Uri ringtoneUri) {
		if (ringtoneUri == null) {
			// ringtoneUri = Settings.System.DEFAULT_NOTIFICATION_URI;
			ringtoneUri = Uri.parse(Settings.System.getString(
					mContext.getContentResolver(), KEY_DEFAULT_NOTIFICATION));
		}
		Log.d(PedometerApplication.TAG, "getRingtone ringtoneUri=" + ringtoneUri);

		Ringtone ringtone = RingtoneManager.getRingtone(mContext, ringtoneUri);
		// ALPS01820873 ringtone maybe null
		if (ringtone != null) {
			ringtone.setStreamType(AudioManager.STREAM_NOTIFICATION);
		}
		return ringtone;
	}
}
