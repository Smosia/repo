package com.ragentek.stresstest;

/**
 * the control center,implement the main logic,like as an Adapter
 * 
 * @author yangyang.liu
 * @date 2015/07/24
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.internal.widget.LockPatternUtils;
import com.ragentek.stresstest.utils.DebugUtil;
import com.ragentek.stresstest.utils.ReflectUtil;

//import com.ragentek.confirm.RGKConfirm;

public class ControlCenter extends ListActivity {
	private final static String TAG = "StressTest";
	private final static boolean DEBUG = true;

	/* SharedPreferences file name */
	public final static String SP_FILENAME = "stress_test";
	/* count reboot times in sp */
	public final static String SKEY_REBOOT_COUNT = "REBOOT_COUNT";
	public final static String SKEY_REBOOT_MODE = "SKEY_REBOOT_MODE";
	public final static String SKEY_REBOOT_TIMES = "SKEY_REBOOT_TIMES";
	public final static String SKEY_TEST_DURATION = "SKEY_TEST_DURATION";
	public final static String SKEY_REBOOT_TEST = "SKEY_REBOOT_TEST";

	/* Dose it start from the boot receiver */
	public final static String START_FROM_BOOTRECEIVER = "START_FROM_BOOTRECEIVER";

	private final static String LIST_ITEM_TITLE = "title";
	private final static String LIST_ITEM_RESULT = "result";
	private final static int LIST_ITEM_RESULT_STATE_INVALID = -1;
	private final static int LIST_ITEM_RESULT_STATE_PASS = 1;
	private final static int LIST_ITEM_RESULT_STATE_FAILED = 2;

	private final static int MSG_START_CURR_TEST = 1;
	private final static int MSG_STOP_CURR_TEST = MSG_START_CURR_TEST + 1;
	private final static int MSG_START_NEXT_TEST = MSG_START_CURR_TEST + 2;

	private final static int MSG_REFRESH_BTN = 10;
	private final static int MSG_REFRESH_LIST = MSG_REFRESH_BTN + 1;
	private final static int MSG_SHOW_DIALOG = MSG_REFRESH_BTN + 2;

	private final static int DG_TEST_OVER = 20;
	private final static int DG_TEST_EXIT = DG_TEST_OVER + 1;

	public static boolean isTestMode = false;

	Map<String, FunctionItem> mFunctionItem = new HashMap<String, FunctionItem>();

	List<FunctionItem> mFunctionList = new ArrayList<ControlCenter.FunctionItem>();

	List<Map<String, Object>> mItemList = new ArrayList<Map<String, Object>>();

	List<BaseTester> mTesterList = new ArrayList<BaseTester>();

	Setting setting = new Setting();

	private BaseTester currTester;

	private int currTestIndex = 0;

	private int Testindex = 1;

	private int spTestDuration;

	private int spRebootTimes;

	private String spRebootTest;

	private Context mContext;

	private LayoutInflater mInflater;

	private LockPatternUtils mLockPatternUtils;

	private SharedPreferences sp;

	private PowerManager pm;

	private Bitmap PASS_ICON;
	private Bitmap FAIL_ICON;

	private Button btnStart;
	private Button btnSettings;
	private Button btnStop;

	private LinearLayout llListView;
	private LinearLayout llSettingView;

	private EditText etTestDur;
	private EditText etRebootTime;
	private EditText etRebootTest;

	private HandlerThread handlerThread;
	private Handler handler;

	/**
	 * a handler in UI thread , so that can update the view
	 */
	Handler uiHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REFRESH_BTN:
				refreshBTN();
				break;

			case MSG_REFRESH_LIST:
				refreshList();
				break;

			case MSG_SHOW_DIALOG:
				onDialogShow(msg.arg1);
				break;

			default:
				break;
			}
		};
	};

	/*private void checkAPP() {
		RGKConfirm instance = RGKConfirm.getInstance();
		String pkgName = getPackageName();
		if(!instance.encrypt(pkgName+RGKConfirm.RGKSTR).equals(instance.getConfirmString())){
		 throw new RuntimeException("confirm failed!");
		 }
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// app protect
		//checkAPP();
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// shield home key ,only use for MTK
		// 0x80000000 is the value of FLAG_HOMEKEY_DISPATCHED
		getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;

		setContentView(R.layout.main);

		init();

		Intent intent = getIntent();
		boolean startFromBootreceiver = intent.getBooleanExtra(
				START_FROM_BOOTRECEIVER, false);

		// if startFromBootreceiver = true,then enter reboot mode
		// otherwise normal create
		if (startFromBootreceiver) {
			int rebootCount = sp.getInt(SKEY_REBOOT_COUNT, 0);
			rebootCount++;
			Editor editor = sp.edit();
			if (DEBUG) {
				DebugUtil.logd(TAG, "rebootCount:" + rebootCount
						+ ", rebootTimes:" + setting.rebootTimes);
			}
			if (rebootCount > 0 && rebootCount < setting.rebootTimes) {
				editor.putInt(SKEY_REBOOT_COUNT, rebootCount);
				editor.putInt(""+mItemList.size(),LIST_ITEM_RESULT_STATE_INVALID);
				//Modify JWBLB-1197 by xujie 20150811 (start)
				editor.commit();
				//startTest(null);
				enterReboot();
				//Modify JWBLB-1197 by xujie 20150811 (end)
			} else {
				editor.putInt(SKEY_REBOOT_COUNT, 0);
				editor.putBoolean(SKEY_REBOOT_MODE, false);
				editor.putInt(""+mItemList.size(),LIST_ITEM_RESULT_STATE_PASS);
				editor.commit();//Add JWBLB-1197 by xujie 20150811
				mLockPatternUtils.setLockScreenDisabled(false);
				showMyDialog(DG_TEST_OVER);
			}
			//editor.commit();//Rm JWBLB-1197 by xujie 20150811
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		stopCurrTest();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// shield home key
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// shield home key
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	/**
	 * init
	 */
	private void init() {
		initRes();
		initSettingConfig();
		initItemConfig();
		initItemList();
		initHanlder();
	}

	/**
	 * init resources
	 */
	private void initRes() {
		mContext = this;
		mInflater = LayoutInflater.from(mContext);
		 mLockPatternUtils = new LockPatternUtils(mContext);
		pm = (PowerManager) getSystemService(POWER_SERVICE);
		PASS_ICON = BitmapFactory.decodeResource(getResources(),
				R.drawable.test_pass);
		FAIL_ICON = BitmapFactory.decodeResource(getResources(),
				R.drawable.test_fail);
		btnStart = (Button) findViewById(R.id.btn_start);
		btnSettings = (Button) findViewById(R.id.btn_setting);
		btnStop = (Button) findViewById(R.id.btn_stop);
		llListView = (LinearLayout) findViewById(R.id.ll_list_view);
		llSettingView = (LinearLayout) findViewById(R.id.ll_setting_view);
		etTestDur = (EditText) findViewById(R.id.et_test_dur);
		etRebootTime = (EditText) findViewById(R.id.et_rb_times);
		etRebootTest = (EditText) findViewById(R.id.et_rb_test);
		sp = getSharedPreferences(SP_FILENAME, MODE_PRIVATE);

	}

	/**
	 * Init and Thread other with UI Thread to start test
	 */
	private void initHanlder() {
		handlerThread = new HandlerThread("Tester-thread");

		handlerThread.start();

		handler = new Handler(handlerThread.getLooper(), new Callback() {

			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {
				case MSG_START_CURR_TEST:
					handleStartTest();
					break;

				case MSG_START_NEXT_TEST:
					handleStartNext();
					break;

				case MSG_STOP_CURR_TEST:
					handleStopTest();
					break;

				default:
					break;
				}

				return false;
			}
		});
	}

	/**
	 * init setting configs
	 */
	private void initSettingConfig() {
		// Get setting configs
		try {
			XmlPullParser mXmlPullParser = null;
			mXmlPullParser = getResources().getXml(
					R.xml.stresstest_setting_config);

			int mEventType = mXmlPullParser.getEventType();
			while (mEventType != XmlPullParser.END_DOCUMENT) {
				if (mEventType == XmlPullParser.START_DOCUMENT) {

				} else if (mEventType == XmlPullParser.END_DOCUMENT) {

				}

				else if (mEventType == XmlPullParser.START_TAG) {
					String name = mXmlPullParser.getName();

					if (name.equals("Setting")) {
						String autoTest = mXmlPullParser.getAttributeValue(
								null, "autoTestAll");
						setting.autoTestAll = autoTest == null ? true : "true"
								.equals(autoTest) ? true : false;

						Editor editor = sp.edit();

						// Init testDuration start
						String testDuration = mXmlPullParser.getAttributeValue(
								null, "testDuration");
						try {
							if (testDuration != null) {
								spTestDuration = sp.getInt(SKEY_TEST_DURATION,
										-1);
								if (spTestDuration < 0) {
									spTestDuration = Integer
											.parseInt(testDuration);
									setting.testDuration = spTestDuration * 60 * 1000;
									editor.putInt(SKEY_TEST_DURATION,
											spTestDuration);
								} else {
									setting.testDuration = spTestDuration * 60 * 1000;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						// end

						// Init rebootTest start
						String rebootTest = mXmlPullParser.getAttributeValue(
								null, "rebootTest");
						spRebootTest = sp.getString(SKEY_REBOOT_TEST, "NULL");
						if ("NULL".equalsIgnoreCase(spRebootTest)) {
							spRebootTest = rebootTest;
							setting.rebootTest = rebootTest == null ? false
									: "Y".equalsIgnoreCase(rebootTest) ? true
											: false;
							editor.putString(SKEY_REBOOT_TEST, spRebootTest);
						} else {
							setting.rebootTest = "Y"
									.equalsIgnoreCase(spRebootTest) ? true
									: false;
						}
						// end

						// Init rebootTimes start
						String rebootTimes = mXmlPullParser.getAttributeValue(
								null, "rebootTimes");
						try {
							if (rebootTimes != null) {
								spRebootTimes = sp
										.getInt(SKEY_REBOOT_TIMES, -1);
								if (spRebootTimes < 0) {
									spRebootTimes = Integer
											.parseInt(rebootTimes);
									setting.rebootTimes = spRebootTimes;
									editor.putInt(SKEY_REBOOT_TIMES,
											spRebootTimes);
								} else {
									setting.rebootTimes = spRebootTimes;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						// end

						editor.commit();
						break;
					}
				} else if (mEventType == XmlPullParser.END_TAG) {

				} else if (mEventType == XmlPullParser.TEXT) {

				}
				mEventType = mXmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtil.loge(TAG, e);
		}
	}

	/**
	 * init items config
	 */
	private void initItemConfig() {
		FunctionItem functionItem = null;
		// Get Test Item
		try {
			XmlPullParser mXmlPullParser = null;
			mXmlPullParser = getResources()
					.getXml(R.xml.stresstest_item_config);

			int mEventType = mXmlPullParser.getEventType();
			while (mEventType != XmlPullParser.END_DOCUMENT) {
				if (mEventType == XmlPullParser.START_DOCUMENT) {

				} else if (mEventType == XmlPullParser.END_DOCUMENT) {

				}

				else if (mEventType == XmlPullParser.START_TAG) {
					String name = mXmlPullParser.getName();

					if (name.equals("FunctionItem")) {
						functionItem = null;
						String enable = mXmlPullParser.getAttributeValue(null,
								"enable");

						if (enable != null && enable.equals("true")) {
							functionItem = new FunctionItem();
							functionItem.name = getStringByName(mXmlPullParser
									.getAttributeValue(null, "name"));
							functionItem.calssName = mXmlPullParser
									.getAttributeValue(null, "calssName");
						}
					}
				} else if (mEventType == XmlPullParser.END_TAG) {
					String tagName = mXmlPullParser.getName();

					if (functionItem != null && tagName.equals("FunctionItem")) {
						mFunctionList.add(functionItem);
					}
				} else if (mEventType == XmlPullParser.TEXT) {

				}
				mEventType = mXmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtil.loge(TAG, e);
		}
	}

	private String getStringByName(String name) {

		name = name.substring(name.lastIndexOf("/") + 1, name.length());

		if (DEBUG) {
			DebugUtil.logd(TAG, name);
		}

		Resources res = getResources();
		int resId = res
				.getIdentifier(name, "string", "com.ragentek.stresstest");
		return getString(resId);
	}

	/**
	 * init item list
	 */
	private void initItemList() {

		// add items into mTesterList & mItemList
		for (int i = 0; i < mFunctionList.size(); i++) {

			FunctionItem mItem = mFunctionList.get(i);
			BaseTester iTester = (BaseTester) ReflectUtil
					.getClassInstance(mItem.calssName);
			Log.i(TAG, "iTester=" + iTester);
			if (iTester != null) {
				iTester.setContext(this);
				mTesterList.add(iTester);
				Log.i(TAG, "mTesterList=" + mTesterList);
				addItem(mItemList, mItem.name);
			}
		}

		// add reboot test item at the end
		addRebootItem();

		setListAdapter(mBaseAdapter);
	}

	private void addItem(List<Map<String, Object>> data, String name) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(LIST_ITEM_TITLE, Testindex + "  " + name);
		temp.put(LIST_ITEM_RESULT, "NULL");
		data.add(temp);
		Testindex++;
	}

	private void addRebootItem() {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(LIST_ITEM_TITLE, Testindex + "  "
				+ getString(R.string.name_reboot_test));
		temp.put(LIST_ITEM_RESULT, "NULL");
		mItemList.add(temp);
		Testindex++;
	}

	BaseAdapter mBaseAdapter = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.list_item, null);
			ImageView image = (ImageView) convertView
					.findViewById(R.id.icon_center);
			TextView text = (TextView) convertView
					.findViewById(R.id.text_center);
			text.setText((String) (mItemList.get(position).get(LIST_ITEM_TITLE)));
			//modify BUG_ID:JWBLB-1197 xuemingwei 20150902(start)
			Log.i(TAG, "mBaseAdapter > getView=" + position);
			int result = sp.getInt(""+(position+1),LIST_ITEM_RESULT_STATE_INVALID);
			Log.i(TAG, "mBaseAdapter > result=" + result);
			switch(result){
			case LIST_ITEM_RESULT_STATE_PASS:
				image.setImageBitmap(PASS_ICON);
				break;
			case LIST_ITEM_RESULT_STATE_FAILED:
				image.setImageBitmap(FAIL_ICON);
				break;
			case LIST_ITEM_RESULT_STATE_INVALID:
			    image.setImageBitmap(null);
				break;
			}	
			/*String result = (String) (String) (mItemList.get(position)
					.get(LIST_ITEM_RESULT));
			if (result != null && !result.equals("NULL"))
				image.setImageBitmap(result.equals("OK") ? PASS_ICON : result
						.equals("FAIL") ? FAIL_ICON : null);
			else
				image.setImageBitmap(null);*/
			//modify BUG_ID:JWBLB-1197 xuemingwei 20150902(end)
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return mItemList.get(position);
		}

		@Override
		public int getCount() {
			return mItemList.size();
		}
	};

	/**
	 * start into auto test
	 * 
	 * @param view
	 */
	public void startTest(View view) {
		if (setting.autoTestAll) {
		int len = mItemList.size();
		for (int i = 0; i < len; i++){
			Editor editor = sp.edit();
			editor.putInt(""+(i+1),LIST_ITEM_RESULT_STATE_INVALID);
			editor.commit();
		}
			handler.sendEmptyMessage(MSG_START_CURR_TEST);
			isTestMode = true;
			refreshBTN();
			DebugUtil.shortToast(this, getString(R.string.msg_start_test));
		} else {
			DebugUtil.shortToast(this, getString(R.string.msg_set_config));
		}
	}

	/**
	 * stop all test
	 * 
	 * @param view
	 */
	public void stopTest(View view) {
		if (handler.hasMessages(MSG_STOP_CURR_TEST)) {
			handler.removeMessages(MSG_STOP_CURR_TEST);
		}
		if (currTester != null) {
			currTester.stop(mContext);
		}
		mItemList.get(currTestIndex).put(LIST_ITEM_RESULT, "FAIL");
		Editor editor = sp.edit();
		editor.putInt(""+(currTestIndex+1),LIST_ITEM_RESULT_STATE_FAILED);
		editor.commit();
		resetVars();
		refreshBTN();
		resetRebootCounts();
		DebugUtil.shortToast(this, getString(R.string.msg_test_stop));
	}

	/**
	 * enter setting page,set aside as a reference
	 * 
	 * @param view
	 */
	public void enterSettings(View view) {
		llListView.setVisibility(View.GONE);
		llSettingView.setVisibility(View.VISIBLE);
		DebugUtil.logd(TAG, "" + llSettingView.isActivated());
		etTestDur.setText(spTestDuration + "");
		etRebootTest.setText(spRebootTest + "");
		etRebootTime.setText(spRebootTimes + "");
	}

	/**
	 * save the user config
	 * 
	 * @param view
	 */
	public void saveConfig(View view) {
		llListView.setVisibility(View.VISIBLE);
		llSettingView.setVisibility(View.GONE);

		try {
			spTestDuration = Integer.parseInt(etTestDur.getText().toString());
			setting.testDuration = spTestDuration * 60 * 1000;

			String rtTmp = etRebootTest.getText().toString();
			;

			if ("Y".equalsIgnoreCase(rtTmp) || "N".equalsIgnoreCase(rtTmp)) {
				spRebootTest = rtTmp;
				setting.rebootTest = "Y".equalsIgnoreCase(spRebootTest) ? true
						: false;
			} else {
				DebugUtil.shortToast(mContext,
						getString(R.string.msg_save_fail_rt));
			}

			setting.rebootTimes = spRebootTimes = Integer.parseInt(etRebootTime
					.getText().toString());
		} catch (NumberFormatException e) {
			DebugUtil.shortToast(mContext,
					getString(R.string.msg_save_fail_int));
		}
		Editor editor = sp.edit();
		editor.putInt(SKEY_TEST_DURATION, spTestDuration);
		editor.putString(SKEY_REBOOT_TEST, spRebootTest);
		editor.putInt(SKEY_REBOOT_TIMES, spRebootTimes);
		editor.commit();
		DebugUtil.shortToast(mContext, getString(R.string.msg_save_success));
	}

	/**
	 * cancel save
	 * 
	 * @param view
	 */
	public void cancelConfig(View view) {
		llListView.setVisibility(View.VISIBLE);
		llSettingView.setVisibility(View.GONE);
	}

	private void stopCurrTestAtTime(long duration) {
		handler.sendEmptyMessageAtTime(MSG_STOP_CURR_TEST,
				SystemClock.uptimeMillis() + duration);
	}

	private void handleStartTest() {
		if (DEBUG) {
			DebugUtil.logd(TAG, "start test");
		}
		if (mTesterList != null && mTesterList.size() > 0) {
			currTester = mTesterList.get(currTestIndex);
			boolean isStarted = currTester.start(mContext);
			if (isStarted) {
				stopCurrTestAtTime(setting.testDuration);
			} else {
				mItemList.get(currTestIndex).put(LIST_ITEM_RESULT, "FAIL");
				Editor editor = sp.edit();
				editor.putInt(""+(currTestIndex+1),LIST_ITEM_RESULT_STATE_FAILED);
				editor.commit();
				uiHandler.sendEmptyMessage(MSG_REFRESH_LIST);
				handler.sendEmptyMessageDelayed(MSG_START_NEXT_TEST, 1000);
			}
		} else {
			enterReboot();
		}
	}

	private void handleStartNext() {
		if (DEBUG) {
			DebugUtil.logd(TAG, "start next test");
		}
		currTestIndex++;
		if (currTestIndex >= mTesterList.size()) {
			enterReboot();
			resetVars();
			uiHandler.sendEmptyMessage(MSG_REFRESH_BTN);
		} else {
			currTester = mTesterList.get(currTestIndex);
			boolean isStarted = currTester.start(mContext);
			if (isStarted) {
				stopCurrTestAtTime(setting.testDuration);
			} else {
				mItemList.get(currTestIndex).put(LIST_ITEM_RESULT, "FAIL");
				Editor editor = sp.edit();
				editor.putInt(""+(currTestIndex+1),LIST_ITEM_RESULT_STATE_FAILED);
				editor.commit();
				uiHandler.sendEmptyMessage(MSG_REFRESH_LIST);
				handler.sendEmptyMessageDelayed(MSG_START_NEXT_TEST, 1000);
			}
		}

	}

	private void handleStopTest() {
		if (DEBUG) {
			DebugUtil.logd(TAG, "stop current test");
		}
		boolean result = currTester.stop(mContext);
		mItemList.get(currTestIndex).put(LIST_ITEM_RESULT,
				result ? "OK" : "FAIL");
				if(result){
					Editor editor = sp.edit();
					editor.putInt(""+(currTestIndex+1),LIST_ITEM_RESULT_STATE_PASS);
					editor.commit();
					Log.i(TAG, "OK, "+currTestIndex+": LIST_ITEM_RESULT_STATE_PASS");
				}else{
					Editor editor = sp.edit();
					editor.putInt(""+(currTestIndex+1),LIST_ITEM_RESULT_STATE_FAILED);
					editor.commit();
					Log.i(TAG, "FAIL, "+currTestIndex+": LIST_ITEM_RESULT_STATE_FAILED");
				}	
		uiHandler.sendEmptyMessage(MSG_REFRESH_LIST);
		handler.sendEmptyMessageDelayed(MSG_START_NEXT_TEST, 1000);
	}

	private void stopCurrTest() {
		// It's abnormally stop if has message MSG_STOP_CURR_TEST
		if (handler.hasMessages(MSG_STOP_CURR_TEST)) {
			handler.removeMessages(MSG_STOP_CURR_TEST);
			handler.sendEmptyMessage(MSG_STOP_CURR_TEST);
		}
	}

	private void enterReboot() {
		if (setting.rebootTest) {
			Editor editor = sp.edit();
			editor.putBoolean(SKEY_REBOOT_MODE, true);
			editor.commit();
			mLockPatternUtils.setLockScreenDisabled(true);
			pm.reboot(null);
		} else {
			isTestMode = false;
			uiHandler.sendEmptyMessage(MSG_REFRESH_BTN);
			showMyDialog(DG_TEST_OVER);
		}
	}

	private void resetVars() {
		currTestIndex = 0;
		isTestMode = false;
	}

	private void resetRebootCounts() {
		Editor editor = sp.edit();
		editor.putInt(SKEY_REBOOT_COUNT, 0);
		editor.commit();
	}

	private void refreshBTN() {
		if (DEBUG) {
			DebugUtil.logd(TAG, "isTestMode:" + isTestMode);
		}
		if (isTestMode) {
			btnStart.setVisibility(View.GONE);
			btnSettings.setVisibility(View.GONE);
			btnStop.setVisibility(View.VISIBLE);
		} else {
			btnStart.setVisibility(View.VISIBLE);
			btnSettings.setVisibility(View.VISIBLE);
			btnStop.setVisibility(View.GONE);
		}
	}

	private void refreshList() {
		Log.i(TAG, "mBaseAdapter.notifyDataSetInvalidated");
		mBaseAdapter.notifyDataSetInvalidated();
	}

	private void onDialogShow(int dialog) {
		if (DEBUG) {
			DebugUtil.logd(TAG, "dialog Id:" + dialog);
		}
		switch (dialog) {
		case DG_TEST_OVER: {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(getString(R.string.msg_test_finish))
					.setPositiveButton(R.string.btn_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
			break;
		}
		case DG_TEST_EXIT: {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(getString(R.string.msg_test_exit))
					.setPositiveButton(R.string.btn_yes, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					})
					.setNegativeButton(R.string.btn_no, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
			break;
		}
		default:
			break;
		}
	}

	private void showMyDialog(int dialog) {
		if (DEBUG) {
			DebugUtil.logd(TAG, "showMyDialog");
		}
		Message msg = Message.obtain();
		msg.arg1 = dialog;
		msg.what = MSG_SHOW_DIALOG;
		uiHandler.sendMessage(msg);
	}
	/**
	 * items
	 * 
	 * @author yangyang.liu
	 */
	private class FunctionItem {
		String name;
		String calssName;
	}

	/**
	 * settings
	 * 
	 * @author yangyang.liu
	 */
	private class Setting {
		boolean autoTestAll;
		long testDuration;
		boolean rebootTest;
		int rebootTimes;
	}

	@Override
	public void onBackPressed() {
		if (llSettingView != null
				&& llSettingView.getVisibility() == View.VISIBLE) {
			cancelConfig(null);
		} else if (isTestMode) {
			return;
		} else {
			showMyDialog(DG_TEST_EXIT);
		}
	}
}
