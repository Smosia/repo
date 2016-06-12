package com.ragentek.wakegesture.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ragentek.wakegesture.R;
import com.ragentek.wakegesture.bean.KeyGestureFunction;
import com.ragentek.wakegesture.constant.Constants;
import com.ragentek.wakegesture.util.Util;

import android.R.integer;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GestureListAdapter extends BaseAdapter {

	private Context mContext;
	private String str_array[];
	private LayoutInflater mLayoutInflater;
	private ArrayList<KeyGestureFunction> keyGestureFunctions;
	private int int_gesture[] = new int[] { R.drawable.touch, R.drawable.up,
			R.drawable.down, R.drawable.left, R.drawable.right,
			R.drawable.c_icon, R.drawable.e_icon, R.drawable.m_icon,
			R.drawable.o_icon, R.drawable.w_icon };
	private String action_array[];
	private PackageManager packageManager;
	private List<ResolveInfo> packageInfos;
	private ContentResolver res;

	public ArrayList<KeyGestureFunction> getKeyGestureFunctions() {
		return keyGestureFunctions;
	}

	public void setKeyGestureFunctions(
			ArrayList<KeyGestureFunction> keyGestureFunctions) {
		this.keyGestureFunctions = keyGestureFunctions;
		notifyDataSetChanged();
	}

	public GestureListAdapter(Context context,
			ArrayList<KeyGestureFunction> keyGestureFunctions) {
		this.mContext = context;
		str_array = mContext.getResources().getStringArray(
				R.array.array_gesture);
		action_array = mContext.getResources().getStringArray(
				R.array.array_gesture_action);
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.keyGestureFunctions = keyGestureFunctions;
		packageManager = this.mContext.getPackageManager();
		packageInfos = getApplicationInfoList(packageManager);
		res = mContext.getContentResolver();
	}

	private List<ResolveInfo> getApplicationInfoList(
			PackageManager packageManager) {
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		return packageManager.queryIntentActivities(intent,
				packageManager.GET_ACTIVITIES);
	}

	private String getLabel(String packageName, String activityName,
			PackageManager packageManager) {
		String label = "";
		int length = packageInfos.size();
		for (int i = 0; i < length; i++) {
			ResolveInfo info = packageInfos.get(i);
			if (info.activityInfo.packageName.equals(packageName)
					&& info.activityInfo.name.equals(activityName)) {
				label = info.loadLabel(packageManager).toString();
				break;
			}
		}
		return label;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int listSize = 0;
		if (keyGestureFunctions != null && keyGestureFunctions.size() > 0) {
			listSize = keyGestureFunctions.size();
		}
		return str_array.length + listSize;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return keyGestureFunctions.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		/******
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_gesture_list,
					null);
			holder.iv_item_gesture = (ImageView) convertView
					.findViewById(R.id.iv_item_gesture);
			holder.name_gesture = (TextView) convertView
					.findViewById(R.id.name_gesture);
			holder.action_gesture = (TextView) convertView
					.findViewById(R.id.action_gesture);
			holder.iv_item_next = (ImageView) convertView
					.findViewById(R.id.iv_item_next);
			holder.toggle_gesture_list = (ToggleButton) convertView
					.findViewById(R.id.toggle_gesture_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        ***********/
		convertView = mLayoutInflater.inflate(R.layout.item_gesture_list,
				null);
		ImageView iv_item_gesture = (ImageView) convertView
				.findViewById(R.id.iv_item_gesture);
		TextView name_gesture = (TextView) convertView
				.findViewById(R.id.name_gesture);
		TextView action_gesture = (TextView) convertView
				.findViewById(R.id.action_gesture);
		ImageView iv_item_next = (ImageView) convertView
				.findViewById(R.id.iv_item_next);
		Switch item_switch = (Switch) convertView
				.findViewById(R.id.switch_gesture_list);
		if (arg0 <= 4) {
			item_switch.setVisibility(View.VISIBLE);
			if (arg0 != 2) {
				name_gesture.setText(str_array[arg0]);
				action_gesture.setText(action_array[arg0]);
			} else {
				name_gesture.setText(str_array[arg0]);
				// holder.action_gesture.setText(Util.getProgramApplicationName(mContext,
				// packageManager, Constants.DIALER_PACKAGE_STRING));
				action_gesture.setText(getLabel(
						Constants.DIALER_PACKAGE_STRING,
						Constants.DIALER_ACTIVITY_NAME, packageManager));
			}
			iv_item_next.setVisibility(View.GONE);
			item_switch.setChecked(getToggleStatus(arg0));
			item_switch
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton position,
								boolean arg1) {
							// TODO Auto-generated method stub
							setToggleStatus(arg0, arg1);
						}
					});
		convertView.setBackground(null);     			

		} else {
			item_switch.setVisibility(View.GONE);
			KeyGestureFunction keyAction = keyGestureFunctions.get(arg0 - 5);
			// holder.action_gesture.setText(Util.getProgramApplicationName(mContext,
			// packageManager, keyAction.getInformation_one()));
			action_gesture.setText(getLabel(
					keyAction.getInformation_one(),
					keyAction.getInformation_second(), packageManager));
			name_gesture.setText(keyAction.getKey_description() + " ");
			iv_item_next.setVisibility(View.VISIBLE);
			
		}
		iv_item_gesture.setBackgroundResource(int_gesture[arg0]);
		return convertView;
	}

	private boolean getToggleStatus(int position) {
		int flag = 0;

		if (position == 0) {

			flag = Settings.Global.getInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_F, 1);

		} else if (position == 1) {

			flag = Settings.Global.getInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_UP, 1);

		} else if (position == 2) {

			flag = Settings.Global.getInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_DOWN, 1);

		} else if (position == 3) {

			flag = Settings.Global.getInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_LEFT, 1);

		} else if (position == 4) {

			flag = Settings.Global.getInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_RIGHT, 1);

		}
		return flag == 1;
	}

	private void setToggleStatus(int position, boolean status) {
		int  status_int=status?1:0;
		if (position == 0) {
			Settings.Global.putInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_F, status_int);
		} else if (position == 1) {
			Settings.Global.putInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_UP,status_int);
		} else if (position == 2) {
			Settings.Global.putInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_DOWN, status_int);
		} else if (position == 3) {
			Settings.Global.putInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_LEFT, status_int);
		} else if (position == 4) {
			Settings.Global.putInt(res,
					Settings.Global.BLACK_GESTURE_WAKE_DPAD_RIGHT, status_int);
		}

	}

	class ViewHolder {
		ImageView iv_item_gesture;
		TextView name_gesture;
		TextView action_gesture;
		ImageView iv_item_next;
		ToggleButton toggle_gesture_list;
	}

}
