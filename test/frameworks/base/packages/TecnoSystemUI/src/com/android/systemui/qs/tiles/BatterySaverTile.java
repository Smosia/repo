package com.android.systemui.qs.tiles;

import static android.os.PowerManager.ACTION_POWER_SAVE_MODE_CHANGING;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.android.systemui.qs.QSTile;
import com.android.systemui.R;
import com.android.internal.logging.MetricsLogger;

public class BatterySaverTile extends QSTile<QSTile.State> {

	private static final Intent INTENT_BATTERY_SAVER = new Intent(
			"android.settings.BATTERY_SAVER_SETTINGS");
	private PowerManager mPowerManager;

	public BatterySaverTile(Host host) {
		super(host);
		mPowerManager = (PowerManager) mContext
				.getSystemService(Context.POWER_SERVICE);
		mContext.registerReceiver(mReceiver, new IntentFilter(
				ACTION_POWER_SAVE_MODE_CHANGING));
	}
	public int getMetricsCategory() {
        return MetricsLogger.QS_SAVER;
	}

	@Override
	public void setListening(boolean listening) {

	}

	@Override
	protected void handleDestroy() {
		super.handleDestroy();
		mContext.unregisterReceiver(mReceiver);
	}

	@Override
	protected State newTileState() {
		return new State();
	}

	@Override
	protected void handleClick() {
		mHost.startActivityDismissingKeyguard(INTENT_BATTERY_SAVER);
	}

	@Override
	protected void handleUpdateState(State state, Object arg) {
		boolean isPowerSaverMode = mPowerManager.isPowerSaveMode();
		state.visible = true;
		state.icon = ResourceIcon.get(isPowerSaverMode ? R.drawable.ic_power_saver
						: R.drawable.ic_power_saver_off);
		state.label = mContext.getString(R.string.battery_saver);
		state.contentDescription = mContext.getString(R.string.battery_saver);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "action = " + intent.getAction());
			refreshState();
		}
	};

}
