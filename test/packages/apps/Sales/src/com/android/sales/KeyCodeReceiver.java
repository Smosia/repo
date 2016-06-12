package com.android.sales;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class KeyCodeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
		Uri mUri = Uri.parse("android_secret_code://72537");
		if (intent.getAction().equals(SECRET_CODE_ACTION)) {
			Uri uri = intent.getData();
			if (uri.equals(mUri)) {
				intent = new Intent(context, SalesActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		}
	}
}
