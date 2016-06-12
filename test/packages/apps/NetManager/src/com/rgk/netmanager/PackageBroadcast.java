package com.rgk.netmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(NetApplication.TAG, "PackageBroadcast,onReceive action ="+intent.getAction());
		// this broadcast from PackageManagerService.java
		if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
			// Ignore application updates
			final boolean replacing = intent.getBooleanExtra(
					Intent.EXTRA_REPLACING, false);
			if (!replacing) {
				final int uid = intent.getIntExtra(Intent.EXTRA_UID, -123);
				FirewallDatabaseHelper databaseHelper = new FirewallDatabaseHelper(
						context);
				if (databaseHelper.hasUidWithMobile(uid)) {
					try {
						NetAdapter.getInstance(context)
								.setFirewallUidChainRule(uid, 0, false);
						databaseHelper.deleteDenyUidWithMobile(uid);
					} catch (NetRemoteException e) {
						e.printStackTrace();
					}
				}
				if (databaseHelper.hasUidWithWifi(uid)) {
					try {
						NetAdapter.getInstance(context)
								.setFirewallUidChainRule(uid, 1, false);
						databaseHelper.deleteDenyUidWithWifi(uid);
					} catch (NetRemoteException e) {
						e.printStackTrace();
					}
				}
				AppUtils.reset();
			}
		} else if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
			AppUtils.reset();
		}

	}

}
