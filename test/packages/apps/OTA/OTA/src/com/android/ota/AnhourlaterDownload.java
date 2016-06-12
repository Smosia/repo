package com.android.ota;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
/***
 *
 * 1. yulong.liang@ragentek 20120530 BUG_ID:QYLE-578
 *  Description: click cancel button,Exception occurred 
 *
 */
public class AnhourlaterDownload extends Activity {
    private String TAG="AnhourlaterDownload";
    private String downloadURL;
    private int downloadByte;
    private String packageType;//包类型
    //add BUG_ID:QYLE-578 liangyulong 20120530(start)
    private Button anhour_download_sys,anhour_download_sys_cancel;
    //add BUG_ID:QYLE-578 liangyulong 20120530(end)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.anhour_download);
        stopService(new Intent(this, AnHourLaterNotificationService.class));
        downloadURL= this.getIntent().getExtras().getString("downloadUrl");
        downloadByte=this.getIntent().getExtras().getInt("downloadByte");
        packageType= this.getIntent().getExtras().getString("packageType");
        //  Log.d(TAG, downloadByte+"==downloadByte==");
        //add BUG_ID:QYLE-578 liangyulong 20120530(start)
        anhour_download_sys=(Button)this.findViewById(R.id.anhour_download_sys);
        anhour_download_sys.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try{
                    if(checkPhoneInfo(AnhourlaterDownload.this)){
                        stopService(new Intent(AnhourlaterDownload.this,NotificationDownloadService.class));
                        Intent intent = new Intent(AnhourlaterDownload.this, NotificationDownloadService.class);
                        intent.putExtra("downloadUrl",downloadURL);
                        intent.putExtra("downloadByte", downloadByte);
                        intent.putExtra("packageType", packageType);
                        startService(intent);
                    }
                    AnhourlaterDownload.this.finish();
                }
                catch(Exception e){
                    Log.d(TAG, e.toString());
                }
            }
        });
        anhour_download_sys_cancel=(Button)this.findViewById(R.id.anhour_download_sys_cancel);
        anhour_download_sys_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AnhourlaterDownload.this.finish();
            }
        });
        //add BUG_ID:QYLE-578 liangyulong 20120530(end)
    }
    //delete BUG_ID:QYLE-578 liangyulong 20120530(start)
    /**
     public void onClick(View v){
     switch (v.getId()) {

     case R.id.anhour_download_sys://下载
     try{
     if(checkPhoneInfo(this)){
     stopService(new Intent(this,NotificationDownloadService.class));
     Intent intent = new Intent(this, NotificationDownloadService.class);
     intent.putExtra("downloadUrl",downloadURL);
     startService(intent);
     }
     this.finish();
     }
     catch(Exception e){
     Log.d(TAG, e.toString());
     }
     break;

     case R.id.anhour_download_sys_cancel://取消
     this.finish();
     break;
     }
     }*/
    //delete BUG_ID:QYLE-578 liangyulong 20120530(end)
    private boolean checkPhoneInfo(Context context){
        if(!ActivityUtils.haveInternet(context)){//网络是否通畅
            Toast.makeText(this,R.string.wap_network_fail, 1).show();
            return false;
        }
        if(ActivityUtils.isExistSdcard()){//T卡
            long lo=ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
            double size=lo/1024/1024;
            if(size<300){
                Toast.makeText(this,R.string.sdcard_space_not_enough, 1).show();
                return false;
            }
            else{
                return true ;
            }
        }
        else{
            Toast.makeText(this,R.string.sdcard_not_exist, 1).show();
            return false;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
