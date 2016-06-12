package com.mediatek.oobe;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.IPackageDeleteObserver;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "OOBE";
	public static final int MSG_FINISH_ACTIVITY = 1001;
    public static final int MSG_START_LAUNCHER = 1002;

	private TextView title;
	private ListView mListView;
	private Button finishButton;

	private MyAdapter countryAdapter;

	private Typeface mTypeface;
	private String[] countryValue;
	private String[] deletePackageNames;

	private static int appsCountAll = 0;
	private static int appsCountDeleted = 0;
	private boolean needDelete = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");

		title = (TextView) findViewById(R.id.settings_title);
		title.setTypeface(mTypeface);
		mListView = (ListView) findViewById(R.id.list);
		String[] mItems = getResources().getStringArray(R.array.country_names);
		countryValue = getResources().getStringArray(R.array.country_values);
		countryAdapter = new MyAdapter(MainActivity.this, mItems);
		mListView.setAdapter(countryAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				countryAdapter.setSelectedPosition(position);
				countryAdapter.notifyDataSetInvalidated();
                finishButton.setEnabled(true);
                //finishButton.setAlpha(0xff);
			}
		});

		finishButton = (Button) findViewById(R.id.button_finish);
        finishButton.setEnabled(false);
        //finishButton.setAlpha(0x66);
		finishButton.setTypeface(mTypeface);
		finishButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int pos = countryAdapter.getSelectedPosition();
				finished(countryValue[pos]);
			}
		});
	}

	private void finished(String countryValue) {
        Log.d(TAG, "finished - " + countryValue);
		deleteAppsByCountryValue(countryValue);
	}

	private class MyAdapter extends BaseAdapter {
		Context mContext;
		private String[] list;

		private int selectedPostion = -1;

		public void setSelectedPosition(int pos) {
			selectedPostion = pos;
		}

		public int getSelectedPosition() {
			return selectedPostion;
		}

		public MyAdapter(Context context, String[] list) {
			this.list = list;
			mContext = context;
		}

		@Override
		public int getCount() {
			return list.length;
		}

		@Override
		public Object getItem(int position) {
			return list[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null || convertView.getTag() == null) {
				view = ((LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.list_item, null);
				holder = new ViewHolder(view);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) convertView.getTag();
			}

			String name = (String) getItem(position);
			holder.name.setText(name);
			holder.name.setTypeface(mTypeface);
			if (selectedPostion == position) {
				holder.name.setTextColor(mContext.getResources().getColor(
						R.color.list_item_text_color_selected));
				view.setBackgroundResource(R.drawable.list_selected_bg);
			} else {
				holder.name.setTextColor(mContext.getResources().getColor(
						R.color.list_item_text_color_normal));
				view.setBackgroundResource(R.drawable.list_bg);
			}
			return view;
		}
	}

	class ViewHolder {
		TextView name;

		public ViewHolder(View view) {
			this.name = (TextView) view.findViewById(R.id.text);
		}
	}

	public void deleteAppsByCountryValue(String country) {
        /*
		if ("Nigeria".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Nigeria);
		} else if ("Ghana".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Ghana);
		} else if ("Cote d'Ivoire".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Cote);
		} else if ("Kenya".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Kenya);
		} else if ("Egypt".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Egypt);
		} else if ("Morocco".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Morocco);
		} else if ("Arab emirates".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_UAE);
		} else if ("Saudi Arabia".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Saudi_Arabia);
		} else if ("Indonesia".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Indonesia);
		} else if ("Pakistan".equals(country)) {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Pakistan);
		} else {
			deletePackageNames = getResources().getStringArray(
					R.array.delete_apps_list_Others);
		}
        */
        
		if (deletePackageNames != null && deletePackageNames.length > 0) {   
			appsCountAll = deletePackageNames.length;
            Log.d(TAG, "deleteAppsByCountryValue, appsCountAll="+appsCountAll);
			needDelete = true;
			deleteApps(deletePackageNames);
		} else {
            Log.d(TAG, "No apps need delete");
            mHandler.sendEmptyMessage(MSG_FINISH_ACTIVITY);
        }
        Log.d(TAG, "deleteAppsByCountryValue exit");
	}

	
	private void deleteApps(String[] packageNames) { 
		if (null == packageNames) {
			return;
		} 
		PackageDeleteObserver mPackageDeleteObserver =new PackageDeleteObserver();
		for (String packageName : packageNames) {
			getPackageManager().deletePackage(packageName, mPackageDeleteObserver,0); 
		}
	}
	  
	class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
	  
		@Override
		public void packageDeleted(String packageName, int returnCode) {
          appsCountDeleted++;
          Log.d(TAG, "deleteAppsByCountryValue, packageDeleted="+appsCountDeleted);
		  if(appsCountDeleted >= appsCountAll) { 
			  needDelete = false;
			  mHandler.sendEmptyMessage(MSG_FINISH_ACTIVITY);
		  }
		}
	}
	 

	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_FINISH_ACTIVITY:
				if (!needDelete) {
					if (isCountryItemExist()) {
	                    updateCountryItem(countryValue[countryAdapter.getSelectedPosition()]);
	                } else {
	                    insertCountryItem(countryValue[countryAdapter.getSelectedPosition()]);
	                }
					
                    // Add a persistent setting to allow other apps to know the device has been provisioned.
                    Settings.Global.putInt(getContentResolver(), Settings.Global.DEVICE_PROVISIONED, 1);
                    Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 1);
                    
					PackageManager pm = getPackageManager();
			        ComponentName name = new ComponentName(MainActivity.this, WizardActivity.class);
			        int state = pm.getComponentEnabledSetting(name);
			        if (state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
			            pm.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
			                    PackageManager.DONT_KILL_APP);
			        }
                    finishButton.setEnabled(false);
                    mHandler.sendEmptyMessageDelayed(MSG_START_LAUNCHER, 1500);
                    //startLauncher();
                    //MainActivity.this.finish();
				}
				break;
            case MSG_START_LAUNCHER:
                startLauncher();
                MainActivity.this.finish();
                break;
			default:
				break;
			}

		};
	};
	
	
	private boolean isCountryItemExist() {
        ContentResolver contentResolver = getContentResolver();
        Uri selectUri = Uri.parse("content://com.gcl.provider.oobe/oobe");
        Cursor cursor = contentResolver.query(selectUri, null, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                if("country".equals(name)) {
                    return true;
                }
            }
            cursor.close();
        }
        return false;
    }
	
	private Uri insertCountryItem(String country) {
        ContentResolver contentResolver = getContentResolver();
        Uri insertUri = Uri.parse("content://com.gcl.provider.oobe/oobe");
        ContentValues values = new ContentValues();
        values.put("name", "country");
        values.put("value", "" + country);
        return contentResolver.insert(insertUri, values);
    }
	
	private void updateCountryItem(String country) {
        ContentResolver contentResolver = getContentResolver();
        Uri updateUri = Uri.parse("content://com.gcl.provider.oobe/oobe");
        ContentValues values = new ContentValues();
        values.put("name", "country");
        values.put("value", "" + country);
        contentResolver.update(updateUri, values, "name = ?", new String[]{"country"});
    }
    
    private void startLauncher() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.startActivity(i);
    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.d(TAG, "catch back key!");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}      
}
