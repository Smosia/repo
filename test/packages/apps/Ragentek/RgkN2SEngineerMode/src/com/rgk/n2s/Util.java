package com.rgk.n2s;

import android.content.Context;
import android.content.res.Resources;

public class Util {
	public static String TAG = "Util";
	private Context mContext;
	public static int AP_CFG_REEB_PRODUCT_INFO_LID;
	public Util(Context context) {
		mContext = context;
	}
	public void initValue() {
		readResources(mContext);
	}

	public void readResources(Context ctx){
		Resources res = ctx.getResources();		
		
		AP_CFG_REEB_PRODUCT_INFO_LID = res.getInteger(R.integer.ap_cfg_reeb_product_info_lid);
	}

}
