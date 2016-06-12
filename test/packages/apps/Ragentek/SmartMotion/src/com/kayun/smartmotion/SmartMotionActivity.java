package com.kayun.smartmotion;

import com.kayun.smartmotion.beans.SmartMotionDialogInfo;
import com.kayun.smartmotion.utils.Constants;
import com.kayun.smartmotion.utils.SmartMotionFactory;
import com.kayun.smartmotion.utils.SmartMotionListAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SmartMotionActivity extends Activity implements OnCheckedChangeListener{

    private static final String TAG = "SmartMotionActivity";

    //private CheckBox mAllCheckBox;

    private TextView mScreenGroupTitle;
    private ListView mScreenGroupList;

    private TextView mCallGroupTitle;
    private ListView mCallGroupList;

    private TextView mOthersGroupTitle;
    private ListView mOthersGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate - start");
        super.onCreate(savedInstanceState);
        ActionBar actionBar = SmartMotionActivity.this.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
        setContentView(R.layout.smart_motion_activity);
        findContentViews();
        Log.d(TAG,"onCreate - end");
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume - start");
        super.onResume();
        initContentViews();
        Log.d(TAG,"onResume - end");
    }

    private void findContentViews() {

        //View selectAllView = (View) findViewById(R.id.select_all_view);
        //mAllCheckBox = (CheckBox) selectAllView.findViewById(R.id.select_all_checkbox);
        //mAllCheckBox.setOnCheckedChangeListener(this);

        View screenGroupView = (View) findViewById(R.id.screen_group_view);
        mScreenGroupTitle = (TextView) screenGroupView.findViewById(R.id.group_title);
        mScreenGroupList = (ListView) screenGroupView.findViewById(R.id.group_list);

        View callGroupView = (View) findViewById(R.id.call_group_view);
        mCallGroupTitle = (TextView) callGroupView.findViewById(R.id.group_title);
        mCallGroupList = (ListView) callGroupView.findViewById(R.id.group_list);

        View othersGroupView = (View) findViewById(R.id.others_group_view);
        mOthersGroupTitle = (TextView) othersGroupView.findViewById(R.id.group_title);
        mOthersGroupList = (ListView) othersGroupView.findViewById(R.id.group_list);
    }

    private void initContentViews() {
        mScreenGroupTitle.setText(R.string.screen_group_name);
        //mScreenGroupList.setAdapter(new SmartMotionListAdapter(getApplicationContext(), Constants.SCREEN_GROUP_ID));
        mScreenGroupList.setAdapter(new SmartMotionListAdapter(this, Constants.SCREEN_GROUP_ID));
        SmartMotionFactory.setListViewHeightBasedOnChildren(mScreenGroupList);
        mScreenGroupList.setOnItemClickListener(new SmartMotionClickListener(this, Constants.SCREEN_GROUP_ID));

        mCallGroupTitle.setText(R.string.call_group_name);
        //mCallGroupList.setAdapter(new SmartMotionListAdapter(getApplicationContext(), Constants.CALL_GROUP_ID));
        mCallGroupList.setAdapter(new SmartMotionListAdapter(this, Constants.CALL_GROUP_ID));
        SmartMotionFactory.setListViewHeightBasedOnChildren(mCallGroupList);
        mCallGroupList.setOnItemClickListener(new SmartMotionClickListener(this, Constants.CALL_GROUP_ID));

        mOthersGroupTitle.setText(R.string.others_group_name);
        //mOthersGroupList.setAdapter(new SmartMotionListAdapter(getApplicationContext(), Constants.OTHERS_GROUP_ID));
        mOthersGroupList.setAdapter(new SmartMotionListAdapter(this, Constants.OTHERS_GROUP_ID));
        SmartMotionFactory.setListViewHeightBasedOnChildren(mOthersGroupList);
        mOthersGroupList.setOnItemClickListener(new SmartMotionClickListener(this, Constants.OTHERS_GROUP_ID));

        //mAllCheckBox.setChecked(isAllSwitchChecked());
        //mAllCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String ACTION_TRY_SMART_MOVTION = "android.intent.action.ACTION_TRY_SMART_MOVTION";

    class SmartMotionClickListener implements OnItemClickListener {

        private Context context;
        private int group;

        public SmartMotionClickListener(Context context, int group) {
            this.context = context;
            this.group = group;
        }

        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            final int funcId = SmartMotionFactory.getFuncId(group, position);
            Log.d(TAG,"onItemClick - group = " + group + ", position = " + position + ", funcId = " + funcId);

            final SmartMotionDialogInfo dialogInfo = SmartMotionFactory.getTryDialogInfoById(funcId);
            final ImageView image = (ImageView) new ImageView(context);
            image.setBackgroundResource(dialogInfo.getmDialogDrawable());

            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(dialogInfo.getmDialogTitle())
                    .setMessage(dialogInfo.getmDialogMessage())
                    .setView(image)
                    .setPositiveButton(R.string.dialog_cancel, null)
                    .setNegativeButton(R.string.dialog_try, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Start try activity.
                                    dialog.dismiss();
                                    Intent intent = new Intent(ACTION_TRY_SMART_MOVTION);
                                    intent.putExtra("ID", funcId);
                                    SmartMotionActivity.this.startActivity(intent);
                                }
                            }).create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    AnimationDrawable drawable = (AnimationDrawable) image.getBackground();
                    if (drawable != null) {
                        drawable.start();
                    }
                }
            });
            dialog.show();
        }
    }

    private static final String[] DB_NAMES = new String[]{Constants.DB_ACTION_UNLOCK,
                                                Constants.DB_ANSWER_BY_SWING,
                                                Constants.DB_SMART_ANSWER,
                                                Constants.DB_SMART_CALL,
                                                Constants.DB_SMART_SWITCH,
                                                Constants.DB_TURN_TO_SILENCE,
                                                Constants.DB_TURN_TO_SNOOZE};

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG,"onCheckedChanged - isChecked = " + isChecked);
        final int contentValue = isChecked ? 1 : 0;
        for(int i = 0; i < DB_NAMES.length;i++){
        Settings.System.putInt(getContentResolver(), DB_NAMES[i], contentValue);
        }
        Settings.System.putInt(getContentResolver(), Constants.DB_ALL_SWITCH, contentValue);
    }

    /*
    private boolean isAllSwitchChecked(){
        int value = Settings.System.getInt(getContentResolver(), Constants.DB_ALL_SWITCH, -1);
        if(value == -1){
            Settings.System.putInt(getContentResolver(), Constants.DB_ALL_SWITCH, 0);
        }
        Log.d(TAG, "isAllSwitchChecked - value = " + value);
        return value == 1;
    }
    */
}
