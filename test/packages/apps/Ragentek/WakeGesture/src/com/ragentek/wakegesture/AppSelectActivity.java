package com.ragentek.wakegesture;


import java.util.List;

import com.ragentek.wakegesture.sqlite.SqliteManager;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class AppSelectActivity extends BaseActivity {
    
	private  ListView  listView_select_app;
	private  PackageManager   packageManager;
	private  List<ResolveInfo>  packageInfos;
	private  int  keyCode;
	private  String  keyDescription;
	private  SqliteManager  sqliteManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getApplicationInfoList();
		initControl();
	}

	private void  getApplicationInfoList(){
		packageManager=getPackageManager();
		Intent intent=new Intent(Intent.ACTION_MAIN,null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		packageInfos=packageManager.queryIntentActivities(intent, packageManager.GET_ACTIVITIES);
	}
	
	private  void  initControl(){
		Intent intent=getIntent();
		keyCode=intent.getIntExtra("keyCode", 0);
		keyDescription=intent.getStringExtra("keyDescription");
		sqliteManager=new SqliteManager(this);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//setTitleText(R.string.app_choice);
		//getToggleButton().setVisibility(View.GONE);
		listView_select_app=(ListView) findViewById(R.id.ll_listview);
		listView_select_app.setAdapter(new AppSelectAdapter(packageInfos));
		listView_select_app.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,  int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==0){
					sqliteManager.updateKey(keyCode, -1, null, null,null);
					finish();	
				}
				else{
				final ResolveInfo  info=packageInfos.get(arg2-1);
				String message=getString(R.string.confirm_application_configuration, keyDescription,info.loadLabel(packageManager));
				Builder dialog=new AlertDialog.Builder(AppSelectActivity.this);
				dialog.setIcon(R.drawable.ic_settings_gesture_wake);
				dialog.setTitle(R.string.gesture_wake_configuration).setMessage(message).setPositiveButton(R.string.ok, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						sqliteManager.updateKey(keyCode, 2, info.activityInfo.packageName, info.activityInfo.name,info.loadLabel(packageManager).toString());
						finish();
					}
				});
				dialog.setNegativeButton(R.string.cancel, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create().show();
				}
			}
		});
	}
	@Override
	   public boolean onOptionsItemSelected(MenuItem item) {
		   if (item.getItemId() == android.R.id.home) {
			   super.onBackPressed();
			   return true;
		   }
		   return super.onOptionsItemSelected(item);
	   }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	@Override
	public View getBelowView() {
		// TODO Auto-generated method stub
	  View  view=mLayoutInflater.inflate(R.layout.ll_listview, null);
	  return  view;
	}
	
	
    public class   AppSelectAdapter  extends  BaseAdapter{
    	
        private List<ResolveInfo> mResolveInfos;
    	public  AppSelectAdapter(List<ResolveInfo> resolveInfos){
    		this.mResolveInfos=resolveInfos;
    	}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(mResolveInfos!=null && mResolveInfos.size()>0){
			return mResolveInfos.size()+1;}
			else{
			return 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mResolveInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			
			if(convertView==null){
				holder=new ViewHolder();
				convertView=mLayoutInflater.inflate(R.layout.item_app_select,null);
				holder.icon_app_select=(ImageView) convertView.findViewById(R.id.icon_app_select);
				holder.tv_item_app_label=(TextView) convertView.findViewById(R.id.tv_item_app_label);
				//holder.tv_item_app_package=(TextView) convertView.findViewById(R.id.tv_item_app_package);
				convertView.setTag(holder);
			}
			else{
				holder=(ViewHolder) convertView.getTag();
			}
			if(arg0==0){
				//holder.icon_app_select.setVisibility(View.GONE);
				holder.icon_app_select.setImageDrawable(null);
				holder.tv_item_app_label.setText(R.string.app_select_no);
			}
			else{
			ResolveInfo resolveInfo=mResolveInfos.get(arg0-1);
			holder.icon_app_select.setImageDrawable(resolveInfo.loadIcon(packageManager));
			holder.tv_item_app_label.setText(resolveInfo.loadLabel(packageManager));
			//holder.tv_item_app_package.setText(resolveInfo.activityInfo.packageName);
			//holder.tv_item_app_package.setText(resolveInfo.activityInfo.name);
			}
			return convertView;
		}
    	
		class  ViewHolder{
			ImageView  icon_app_select;
			TextView   tv_item_app_label;
			TextView   tv_item_app_package;
		}
    	
    }
     
}
