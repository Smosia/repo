package com.mediatek.systemui.qs.tiles;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSTile;

public class TimeoutTile extends QSTile<QSTile.BooleanState> {
    public static final int MINIMUM_TIMEOUT = 15000;
    public static final int MEDIUM_TIMEOUT = 30000;
    public static final int MAXIMUM_TIMEOUT = 60000;
    private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 30000;
    private ContentResolver mResolver;
    private int mIconResId;
    public int getMetricsCategory() {
        return MetricsLogger.QS_TIMEOUT;
	}
    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean selfChange) {
            refreshState();
        }
    };

    public TimeoutTile(Host host) {
        super(host);
        mResolver = mContext.getContentResolver();
        mResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_OFF_TIMEOUT),true, mObserver);
    }

    @Override
    public void setListening(boolean listening) {

    }

    @Override
    protected BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    protected void handleClick() {
        handleTimeoutChange();
        refreshState();
    }

    @Override
    protected void handleLongClick() {
        Intent intent = new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS);
        mHost.startActivityDismissingKeyguard(intent);
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        updateTimeoutState();
        state.label = mContext.getString(R.string.timeout);
        state.icon = ResourceIcon.get(mIconResId);
        state.visible = true;
    }

    @Override
    protected void handleDestroy() {
        super.handleDestroy();
        mResolver.unregisterContentObserver(mObserver);
    }

    private void handleTimeoutChange() {
        try {
            int timeout = Settings.System.getInt(mResolver,Settings.System.SCREEN_OFF_TIMEOUT,FALLBACK_SCREEN_TIMEOUT_VALUE);
            Log.d(TAG, "handleTimeoutChange : timeout = " + timeout);
            if (timeout <= MINIMUM_TIMEOUT) {
                timeout = MEDIUM_TIMEOUT;
            } else if (timeout <= MEDIUM_TIMEOUT) {
                timeout = MAXIMUM_TIMEOUT;
            } else {
                timeout = MINIMUM_TIMEOUT;
            }
            Settings.System.putInt(mResolver,Settings.System.SCREEN_OFF_TIMEOUT, timeout);
            Log.d(TAG, "handleTimeoutChange : system put timeout = " + timeout);

        } catch (Exception e) {
            Log.e(TAG, "handleTimeoutChange error " + e);
        }
    }

    private void updateTimeoutState() {
        int timeout = getTimeout();
        switch (timeout) {
        case MINIMUM_TIMEOUT:
            mIconResId = R.drawable.ic_qs_timeout_min;
            break;
        case MEDIUM_TIMEOUT:
            mIconResId = R.drawable.ic_qs_timeout_med;
            break;
        case MAXIMUM_TIMEOUT:
            mIconResId = R.drawable.ic_qs_timeout_max;
            break;

        default:
            break;
        }
    }

    private int getTimeout() {
        try {
            int timeout = Settings.System.getInt(mResolver,Settings.System.SCREEN_OFF_TIMEOUT,FALLBACK_SCREEN_TIMEOUT_VALUE);
            if (timeout <= MINIMUM_TIMEOUT) {
                timeout = MINIMUM_TIMEOUT;
            } else if (timeout <= MEDIUM_TIMEOUT) {
                timeout = MEDIUM_TIMEOUT;
            } else {
                timeout = MAXIMUM_TIMEOUT;
            }
            return timeout;
        } catch (Exception e) {
            Log.e(TAG, "getTimeout error " + e);
        }
        return MEDIUM_TIMEOUT;
    }

}