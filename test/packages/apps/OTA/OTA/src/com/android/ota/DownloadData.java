package com.android.ota;




import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadData extends Activity {

    private TextView progress;
    private ProgressBar progressBar;
    private Button downButton;
    private Button cancelButton;
    private MyReceiver receiver;
    private static final String TAG="DownloadData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.downloaddata);
        initData();
        Log.d(TAG,"onCreate:");

    }

    private void initData(){
        progress = (TextView) this.findViewById(R.id.tv_progress);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar_down_sys);
        downButton = (Button) this.findViewById(R.id.download_pause);
        cancelButton = (Button) this.findViewById(R.id.download_cancel);
        downButton.setOnClickListener(new DownloadButton());
        cancelButton.setOnClickListener(new CancelButton());
		
		/*if(!ActivityUtils.getParameter(getApplicationContext(), "ota_priority").equals("Optional")){
			cancelButton.setVisibility(View.GONE);
		}*/

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        //注册
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy:");
        //不要忘了这一步
        unregisterReceiver(receiver);
    }
    /**
     * 广播接收器
     * @author user
     *
     */
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive:");
            Bundle bundle = intent.getExtras();
            int progressSize = bundle.getInt("progress");
            int progressMax=bundle.getInt("progressMax");
            int result = (int) ((float) progressSize
                    / progressMax * 100);
            progressBar.setMax(progressMax);
            progressBar.setProgress(progressSize);
            progress.setText(getSizeString(progressSize) + "/"+getSizeString(progressMax));
            //progress.setText( result + "%");
            //Toast.makeText(context, progressSize+">>>"+progressMax, 1).show();
            if(result>=100){
                DownloadData.this.finish();
            }
        }
    }

    private final class DownloadButton implements View.OnClickListener {

        public void onClick(View v) {

            DownloadData.this.finish();
        }
    }
    public class CancelButton implements OnClickListener {

        public void onClick(View v) {
            try{
                stopService(new Intent(DownloadData.this, NotificationDownloadService.class));
                Toast.makeText(DownloadData.this, R.string.download_data_cancel, 1).show();
                DownloadData.this.finish();
            }
            catch(Exception e){
                Log.d(TAG,"Error:"+e.toString());

            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private  String getSizeString(long size) {
        if (size < 1024) {
            return String.valueOf(size) + "B";
        }
        else {
            size = size / 1024;
        }
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        }
        else {
            size = size * 100 / 1024;
        }

        return String.valueOf((size / 100)) + "." + ((size % 100) < 10 ? "0" : "")
                + String.valueOf((size % 100)) + "MB";
    }



}
