package com.ragentek.wakegesture.sqlite;

import java.util.ArrayList;

import com.ragentek.wakegesture.R;
import com.ragentek.wakegesture.bean.KeyGestureFunction;
import com.ragentek.wakegesture.constant.KEY_EVENT_WAKE;
import com.ragentek.wakegesture.util.Util;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.util.Log;

public class SqliteManager {
	
    private  Context  mContext;
    private  GestureSqliteOpenHelper  gestureSqliteOpenHelper;
    private  int  key_int[]=new int[]{KEY_EVENT_WAKE.KEYCODE_C,KEY_EVENT_WAKE.KEYCODE_E,
    		                          KEY_EVENT_WAKE.KEYCODE_M,KEY_EVENT_WAKE.KEYCODE_O,KEY_EVENT_WAKE.KEYCODE_W};
    
	public SqliteManager(Context context)
	{
		mContext=context;
		gestureSqliteOpenHelper=new GestureSqliteOpenHelper(mContext);
		//Log.e("xieshanlin","SqliteManger===");
		if(queryAll()==null || queryAll().size()==0){
			initAllKey();
		}
	}
	
	
	public  void  addKEY(int key,String key_description, int type,String infor_one,String infor_second,String information_three){
		SQLiteDatabase  db=gestureSqliteOpenHelper.getWritableDatabase();
		String sql="insert into  gestureSettings(key,key_description,action_type,information_one,information_second,information_three) "
				+ "values(?,?,?,?,?,?)";
		db.execSQL(sql,new Object[]{key,key_description,type,infor_one,infor_second,information_three});
		db.close();
	}
	
	
	public String[]  queryKey(int key) {
		
		String str_key[]=null;
		SQLiteDatabase  db=gestureSqliteOpenHelper.getWritableDatabase();
		String sql="select * from  gestureSettings where key=?";
		Cursor cursor=db.rawQuery(sql, new String[]{key+""});
		if(cursor!=null){
			if(cursor.moveToFirst()){
			str_key=new String[3];	
			str_key[0]=cursor.getInt(cursor.getColumnIndex("action_type"))+"";
			//Log.e("xieshanlin","type===="+str_key[0]);
			str_key[1]=cursor.getString(cursor.getColumnIndex("information_one"));
			str_key[2]=cursor.getString(cursor.getColumnIndex("information_second"));
			 }
			cursor.close();
			db.close();
	        return  str_key;		
		}
		else{
		cursor.close();
		db.close();
		return str_key;
		}
	}
	
	public  void  updateKey(int key,int type,String infor_one,String infor_second,String infor_three){
		String sql="update gestureSettings set action_type=?, information_one=?,information_second=?,information_three=?  where key=?";
		SQLiteDatabase  db=gestureSqliteOpenHelper.getWritableDatabase();
		db.execSQL(sql,new Object[]{type,infor_one,infor_second,infor_three,key});
		if(TextUtils.isEmpty(infor_one)){
		Util.putIsConfigurationApplication(mContext, key, 0);	
		}else{
		Util.putIsConfigurationApplication(mContext, key, 1);
		}
		db.close();
	}
	
	public  ArrayList<KeyGestureFunction> queryAll(){
		
		SQLiteDatabase  db=gestureSqliteOpenHelper.getWritableDatabase();
		String sql="select * from  gestureSettings ";
		Cursor cursor=db.rawQuery(sql, new String[]{});
		ArrayList<KeyGestureFunction>  keyGestureFunctions=new ArrayList<KeyGestureFunction>();
		//Log.e("xieshanlin","cursor--count===="+cursor.getCount());
		if(cursor!=null){
			//Log.e("xieshanlin","-------");
			while(cursor.moveToNext()){
				//Log.e("xieshanlin","-------11111");
				int key=cursor.getInt(cursor.getColumnIndex("key"));
				String  key_description=cursor.getString(cursor.getColumnIndex("key_description"));
				int action_type=cursor.getInt(cursor.getColumnIndex("action_type"));
				String information_one=cursor.getString(cursor.getColumnIndex("information_one"));
				String information_second=cursor.getString(cursor.getColumnIndex("information_second"));
				String information_three=cursor.getString(cursor.getColumnIndex("information_three"));
				KeyGestureFunction  keyFunction=new KeyGestureFunction(key,key_description, action_type, information_one, information_second,information_three);
				//Log.e("xieshanlin","-------22222");
				keyGestureFunctions.add(keyFunction);
			}
			cursor.close();
			db.close();
	    return  keyGestureFunctions;		
		}
		else{
		cursor.close();
		db.close();
		return null;
		}
		
	}
	/*********
	 * init all key  null  to  key  order
	 */
	public void  initAllKey(){
		Resources  resources=mContext.getResources();
		boolean  is_default_application=resources.getBoolean(R.bool.is_configuration_default_application);
		String key_decription[]=resources.getStringArray(R.array.array_gesture_letter);
		if(is_default_application){
		String default_packageName[]=resources.getStringArray(R.array.default_gesture_launch_application_package);
		String default_mainActivityName[]=resources.getStringArray(R.array.default_gesture_launch_application_activity);
		for(int i=0;i<key_int.length;i++){
			addKEY(key_int[i],key_decription[i],2,default_packageName[i],default_mainActivityName[i],null);
			Util.putIsConfigurationApplication(mContext, key_int[i], 1);
		 }
		}else{
			for(int i=0;i<key_int.length;i++){
				addKEY(key_int[i],key_decription[i],-1,null,null,null);
				Util.putIsConfigurationApplication(mContext, key_int[i], 0);
			 }
		}
		
	}
	
	/********
	 * update  database   when  remove  package
	 * 
	 ********/
	public  void  updateDatabaseWhenRemove(String packageName){
		
		String  packageName_update=packageName.substring(packageName.lastIndexOf(":")+1);
		SQLiteDatabase  db=gestureSqliteOpenHelper.getWritableDatabase();
		Log.e("wakegesture","packageName-----"+packageName_update);
		String sql="select * from  gestureSettings where information_one=?";
		Cursor cursor=db.rawQuery(sql, new String[]{packageName_update+""});
		if(cursor!=null){
	    //modify   BUG_ID:JLLB-851  xieshanlin  20140625(start)
	    Log.e("wakegesture","cursor!=null");
	    while(cursor.moveToNext()){
	    Log.e("wakegesture","updateKey--------");
		int key=cursor.getInt(cursor.getColumnIndex("key"));
		updateKey(key, -1, null, null, null);
		//Util.putIsConfigurationApplication(mContext, key, 0);
		  }
		}
		cursor.close();
		db.close();
		//modify   BUG_ID:JLLB-851  xieshanlin  20140625(end)
	}
	
}
