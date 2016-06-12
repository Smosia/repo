package com.ragentek.wakegesture.receiver;
import com.ragentek.wakegesture.sqlite.SqliteManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class PackageRemoveReceiver extends BroadcastReceiver {
	
	private final String REMOVE_APP ="android.intent.action.PACKAGE_REMOVED";
	private SqliteManager  sqliteManager;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action=intent.getAction();
		sqliteManager=new SqliteManager(context);
		if(REMOVE_APP.equals(action)) {
			String  packageName=intent.getDataString();
			sqliteManager.updateDatabaseWhenRemove(packageName);
		}

	}

}
