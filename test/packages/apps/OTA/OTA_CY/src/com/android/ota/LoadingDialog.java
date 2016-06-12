package com.android.ota;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dexin.su on 2015/2/6.
 */
public class LoadingDialog extends Dialog {
    private Context context = null;
    private static LoadingDialog customProgressDialog = null;

    public LoadingDialog(Context context){
        super(context);
        this.context = context;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static LoadingDialog createDialog(Context context){
        customProgressDialog = new LoadingDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.loadingdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){
        Log.i("dexin","wocacaca");
        if (customProgressDialog == null){
            return;
        }
        Log.i("dexin","wocacacaaaaa");
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.setOneShot(false);
        if(animationDrawable.isRunning())//是否正在运行？
        {
            animationDrawable.stop();//停止
        }
        animationDrawable.start();
    }

    public void showLoading(){
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.setOneShot(false);
        if(animationDrawable.isRunning())//是否正在运行？
        {
            animationDrawable.stop();//停止
        }
        animationDrawable.start();
    }



    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public LoadingDialog setTitile(String strTitle){
        return customProgressDialog;
    }

    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public LoadingDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }
}
