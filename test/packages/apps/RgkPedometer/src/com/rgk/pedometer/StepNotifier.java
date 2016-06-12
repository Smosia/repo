/*
 *  Pedometer - Android App
 *  Copyright (C) 2009 Levente Bagi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rgk.pedometer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Counts steps provided by StepDetector and passes the current step count to
 * the activity.
 */
public class StepNotifier implements StepPersister.Listener {

	private static final int NOTIFICATION_ID = 1;
	private static final String NOTIFICATION_TAG = "target_steps_finished";

	private Context mContext;
	private AsyncRingtonePlayer mPlayer;

	public StepNotifier(Context context) {
		mContext = context;
		mPlayer = new AsyncRingtonePlayer(context);
	}

	@Override
	public void stepsChanged(int steps, float calories, float distance) {
		if (SharePreferenceUtils.getTargetStepsValue(mContext) == steps) {
			Log.d(PedometerApplication.TAG,
					"StepNotifier: notify target steps finished.");

			mPlayer.play(null);
			showNotification();
		}
	}

	private void showNotification() {
		SharePreferenceUtils.setHasNotify(mContext, true);

		NotificationManager notifyManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);

		final Intent intent = new Intent(mContext, MainPedometerActivity.class);

		final Notification.Builder builder = new Notification.Builder(mContext);
		String title = mContext.getString(R.string.app_name);
		String contentText = mContext
				.getString(R.string.target_steps_finished_notify_content);
		builder.setAutoCancel(true)
				.setTicker(contentText)
				.setSmallIcon(R.drawable.ic_notification)
				.setContentTitle(title)
				.setContentText(contentText)
				.setContentIntent(
						PendingIntent.getActivity(mContext, 0, intent, 0));

		final Notification notification = builder.build();
		notifyManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification);
	}

	public static void cancelNotify(Context context) {
		NotificationManager notifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notifyManager.cancel(NOTIFICATION_TAG, NOTIFICATION_ID);

		SharePreferenceUtils.setHasNotify(context, false);
	}

}
