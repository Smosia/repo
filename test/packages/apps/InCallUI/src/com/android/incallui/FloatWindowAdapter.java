package com.android.incallui;

import com.android.incallui.AudioModeProvider.AudioModeListener;
import android.telecom.AudioState;
import android.util.Log;

public class FloatWindowAdapter implements AudioModeListener {

	private final static String TAG = "FloatWindow";

	public FloatWindowAdapter() {
		AudioModeProvider.getInstance().addListener(this);
	}

	public static void hangup(String callId) {
		Log.d(TAG, "FloatWindowAdapter=>hangup: callId="+callId);
		TelecomAdapter.getInstance().disconnectCall(callId);
	}
	
	public static void toggleSpeakerphone() {
		int newMode = AudioState.ROUTE_SPEAKER;

        	// if speakerphone is already on, change to wired/earpiece
        	if (AudioModeProvider.getInstance().getAudioMode() == AudioState.ROUTE_SPEAKER) {
            		newMode = AudioState.ROUTE_WIRED_OR_EARPIECE;
        	}
		Log.d(TAG, "FloatWindowAdapter=>toggleSpeakerphone: newMode="+newMode);

        	TelecomAdapter.getInstance().setAudioRoute(newMode);
	}

	public static int getAudioMode() {
		return AudioModeProvider.getInstance().getAudioMode();
	}

	@Override
	public void onAudioMode(int newMode) {
		if (mListener != null) {
			mListener.onAudioMode(newMode);
		}

	}

	@Override
	public void onMute(boolean muted) {

	}

	@Override
	public void onSupportedAudioMode(int modeMask) {

	}
	
	public void setListener(AudioModeListener listener) {
		mListener = listener;
	}

	public void destroy() {
		AudioModeProvider.getInstance().removeListener(this);
	}

	private AudioModeListener mListener;
	
	public interface AudioModeListener {
		void onAudioMode(int newMode);
	}
}
