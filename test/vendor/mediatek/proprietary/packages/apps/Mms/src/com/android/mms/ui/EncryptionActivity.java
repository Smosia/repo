/**
 * add this file : JWLWKK-1209 zhangzixiao 20140728
 */
package com.android.mms.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.android.mms.R;
import com.android.mms.MmsApp;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.os.SystemProperties;
import android.view.KeyEvent;//add bug id:jllb-4895 chenshu
//Modify chenshu 20151016 start no bug id, upgrade mms encryption start
import android.content.ContentUris;
import android.content.ContentValues;
import android.provider.Telephony.Threads;
import java.util.HashSet;
import java.util.Iterator;
import com.mediatek.setting.GeneralPreferenceActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Modify chenshu 20151016 start no bug id, upgrade mms encryption end

public class EncryptionActivity extends Activity implements View.OnClickListener {
 	private static final String TAG = "EncryptionActivity";
	public static final int TYPE_NEW = 1;
	public static final int TYPE_EDIT = 2;
	public static final int TYPE_GET = 3;
	public static final String TYPE = "type";
	public static final String URI = "uri";
	public static final String PREF_ENCRYPTED_PWD = GeneralPreferenceActivity.KEY_ENCRYPTION;
	private int type = 1;
	private int step = 1;
	private String password1;
	private String password2;
	private String password3;
	private TextView pwdTv;
	private EditText pwd;
	private Button cancel;
	private Button next;
	private Context context;
    //Modify chenshu 20151016 start no bug id, upgrade mms encryption start
    public static final String SELECTED_THREADS = "selectedThreads";
    public static final String ENABLE_ENCRYPTION = GeneralPreferenceActivity.ENABLE_ENCRYPTION;
    public static final Uri mAllThreadUri = Uri.parse("content://mms-sms/all_threads");
    //Modify chenshu 20151016 start no bug id, upgrade mms encryption end

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption);
        //GradientDrawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] {Color.RED, Color.GREEN, Color.BLUE});
        //getWindow().setBackgroundDrawable(gradientDrawable);
        context = this;
        initResource();
    }
    
    private void initResource() {
    	pwdTv = (TextView) findViewById(R.id.pwdTv);
    	pwd = (EditText) findViewById(R.id.pwd);
    	cancel = (Button) findViewById(R.id.cancel);
    	next = (Button) findViewById(R.id.next);
    	next.setEnabled(false);
    	step = 1;
    	password1 = null;
    	password2 = null;
    	password3 = null;
    	Intent intent = getIntent();
    	if(intent != null) {
    		type = intent.getIntExtra(TYPE, TYPE_NEW);
    	}
    	int titleRes = 0;
		int pwdTvRes = 0;
		switch(type) {
		case TYPE_NEW:
			titleRes = R.string.new_pwd;
			pwdTvRes = R.string.input_new_pwd;
			break;
		case TYPE_EDIT:
			titleRes = R.string.edit_pwd;
			pwdTvRes = R.string.input_old_pwd;
			break;
		case TYPE_GET:
			titleRes = R.string.input_pwd;
			pwdTvRes = R.string.input_pwd;
			break;
		}
		getWindow().setTitle(getString(titleRes));
		pwdTv.setText(pwdTvRes);
    	cancel.setOnClickListener(this);
    	next.setOnClickListener(this);
    	pwd.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable editable) {
				if(editable.toString().trim().length()==0) {
					next.setEnabled(false);
				} else {
					if(!next.isEnabled()) {
						next.setEnabled(true);
					}
				}
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
    	});
    }
    
    private void updateUI() {
    	int pwdTvRes = 0;
    	switch(type) {
    	case TYPE_NEW:
			if(step==1) {
				pwdTvRes = R.string.input_new_pwd;
			} else {
				pwdTvRes = R.string.input_confirm_pwd;
			}
			break;
		case TYPE_EDIT:
			if(step==1) {
				pwdTvRes = R.string.input_old_pwd;
			} else if(step==2) {
				pwdTvRes = R.string.input_new_pwd;
			} else if(step==3) {
				pwdTvRes = R.string.input_confirm_pwd;
			}
			break;
		case TYPE_GET:
			if(step==1) {
				pwdTvRes = R.string.input_pwd;
			}
			break;
    	}
    	pwdTv.setText(pwdTvRes);
    	pwd.setText("");
    }

	@Override
	public void onClick(View v) {
		if(v == cancel) {
			setResult(Activity.RESULT_CANCELED);
			finish();
		} else if(v == next) {
			switch(type) {
			case TYPE_NEW:
				if(step==1) {
					password1 = pwd.getText().toString().trim();
                    //Modify chenshu 20160407 start
                    if (!isContainAlphabet(password1)) {
                        Toast.makeText(context, R.string.encrypt_has_alphabet, Toast.LENGTH_LONG).show();
                        break;
                    }
                    //Modify chenshu 20160407 end
					step++;
					updateUI();
				} else if(step==2) {
					password2 = pwd.getText().toString().trim();
					if(password2.equals(password1)) {
						savePwd(password1);
                        //Modify chenshu 20151016 start no bug id, upgrade mms encryption start
                        HashSet<Long> selectedThreads = (HashSet<Long>)getIntent().getSerializableExtra(SELECTED_THREADS);
                        /*if (selectedThreads != null) {
                            android.util.Log.e("chenshu","selectedThreads:"+selectedThreads);
                            for (Iterator it= selectedThreads.iterator(); it.hasNext();){
                                long tempThread = (long)it.next();
                                if (tempThread > 0) {
                                    android.util.Log.e("chenshu","it.next():"+tempThread);
                                    Uri threadUri = ContentUris.withAppendedId(Threads.CONTENT_URI, tempThread).buildUpon().appendQueryParameter("isRcse", "true").build();
                                    if(threadUri != null) {
						                ContentValues ct = new ContentValues(1);
	        				            ct.put("encrypted", 1);
	        				            getContentResolver().update(threadUri, ct, null, null);
						            }
                                }
                            }*/
                        //update sql chenshu 20160405 -------------------------start
                        if (selectedThreads != null) {
                            String selection = null;
                            android.util.Log.e("chenshu","selectedThreads:"+selectedThreads);
                            StringBuilder buf = new StringBuilder();
                            buf.append("_id in ( ");
                            int i=0;
                            long tempThread;
                            for (Iterator it= selectedThreads.iterator(); it.hasNext();){
                                tempThread = (long)it.next();
                                if (tempThread > 0) {
                                    android.util.Log.e("chenshu","it.next():"+tempThread);
                                    if (i++ > 0) {
                                        buf.append(",");
                                    }
                                    buf.append(Long.toString(tempThread));
                                }
                            }
                            buf.append(")");
                            selection = buf.toString();
                            android.util.Log.e("chenshu","selection:"+selection);
                            ContentValues ct = new ContentValues(1);
	        				ct.put("encrypted", 1);
	        				getContentResolver().update(mAllThreadUri, ct, selection, null);
                        //update sql chenshu 20160405 -------------------------end
                            setEncryptionPreferenceChecked();
                        }
                        //Modify chenshu 20151016 start no bug id, upgrade mms encryption end
						hideSoftInput();
						setResult(Activity.RESULT_OK);
						finish();
					} else {
						Toast.makeText(context, R.string.tip_not_same_pwd, Toast.LENGTH_SHORT).show();
						pwd.setText("");
					}
				}
				break;
			case TYPE_EDIT:
				if(step==1) {
					password1 = pwd.getText().toString().trim();
					String encryptedPwd = getPwd();
					if(password1.equals(encryptedPwd)) {
						step++;
						updateUI();
					} else {
						Toast.makeText(context, R.string.tip_old_pwd_incorrect, Toast.LENGTH_SHORT).show();
						pwd.setText("");
					}
				} else if(step==2) {
					password2 = pwd.getText().toString().trim();
					if(password1.equals(password2)) {
						Toast.makeText(context, R.string.tip_same_pwd, Toast.LENGTH_SHORT).show();
						pwd.setText("");
					} 
                    //Modify chenshu 20160407 start
                    else if (!isContainAlphabet(password2)) {
                        Toast.makeText(context, R.string.encrypt_has_alphabet, Toast.LENGTH_LONG).show();
                    }
                    //Modify chenshu 20160407 end
                    else {
						step++;
						updateUI();
					}
				} else if(step==3) {
					password3 = pwd.getText().toString().trim();
					if(password3.equals(password2)) {
						savePwd(password2);
						Toast.makeText(context, R.string.tip_pwd_change_success, Toast.LENGTH_SHORT).show();
						hideSoftInput();
						setResult(Activity.RESULT_OK);
						finish();
					} else {
						Toast.makeText(context, R.string.tip_not_same_pwd, Toast.LENGTH_SHORT).show();
						pwd.setText("");
					}
				}
				break;
			case TYPE_GET:
				if(step==1) {
					password1 = pwd.getText().toString().trim();
					String encryptedPwd = getPwd();
					if(password1.equals(encryptedPwd)) {
						hideSoftInput();
						Uri uri = getIntent().getParcelableExtra(URI);
						if(uri != null) {
						    ContentValues ct = new ContentValues(1);
	        				    ct.put("encrypted", 0);
	        				    getContentResolver().update(uri, ct, null, null);
						}
                        //Modify DWYQLSSMY-64 chenshu 20160405 start upgrade mms encryption start
                        HashSet<Long> selectedThreads = (HashSet<Long>)getIntent().getSerializableExtra(SELECTED_THREADS);
                        if (selectedThreads != null) {
                            String selection = null;
                            android.util.Log.e("chenshu","selectedThreads:"+selectedThreads);
                            StringBuilder buf = new StringBuilder();
                            buf.append("_id in ( ");
                            int i=0;
                            long tempThread;
                            for (Iterator it= selectedThreads.iterator(); it.hasNext();){
                                tempThread = (long)it.next();
                                if (tempThread > 0) {
                                    android.util.Log.e("chenshu","it.next():"+tempThread);
                                    if (i++ > 0) {
                                        buf.append(",");
                                    }
                                    buf.append(Long.toString(tempThread));
                                }
                            }
                            buf.append(")");
                            selection = buf.toString();
                            android.util.Log.e("chenshu","selection:"+selection);
                            ContentValues ct = new ContentValues(1);
	        				ct.put("encrypted", 0);
	        				getContentResolver().update(mAllThreadUri, ct, selection, null);
                        }
                        //Modify DWYQLSSMY-64 chenshu 20160405 start upgrade mms encryption end
						setResult(Activity.RESULT_OK);
						finish();
					} else {
						Toast.makeText(context, R.string.tip_pwd_incorrect, Toast.LENGTH_SHORT).show();
						pwd.setText("");
					}
				}
				break;
			}
		}
	}
	
	private void savePwd(String pwd) {
		Log.e(TAG, "savePwd : pwd="+pwd);
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_ENCRYPTED_PWD, pwd).commit();
	}
	
	private String getPwd() {
		String pwd = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_ENCRYPTED_PWD, null);
		Log.e(TAG, "getPwd : pwd="+pwd);
		return pwd;
	}

	private void hideSoftInput() {
	  	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(pwd.getWindowToken(), 0);
 	}

	public static boolean supportEncryptedMessage() {
		return "1".equals(SystemProperties.get("ro.rgk_mms_encryption_message"));
    	}

	public static boolean encryptionEnabled() {
	  	if(supportEncryptedMessage() && 
			PreferenceManager.getDefaultSharedPreferences(MmsApp.getApplication())
				.getBoolean(GeneralPreferenceActivity.ENABLE_ENCRYPTION, false)) {
		    return true;
		}
		return false;
	}

    //add bug id:jllb-4895 chenshu start
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
 		if(keyCode==KeyEvent.KEYCODE_BACK){
 			setResult(Activity.RESULT_CANCELED);
			finish();
 		}
		return super.onKeyDown(keyCode, event);
    }
    //add bug id:jllb-4895 chenshu end
	
	    //Modify chenshu 20151016 start no bug id, upgrade mms encryption start
    public static boolean encryptionPreferenceEnabled() {
        if (PreferenceManager.getDefaultSharedPreferences(MmsApp.getApplication())
				.getBoolean(GeneralPreferenceActivity.ENABLE_ENCRYPTION, false)) {
		    return true;
        }
        return false;
    }

    public static boolean encryptionHasPassword() {
        if (android.text.TextUtils.isEmpty(
                PreferenceManager.getDefaultSharedPreferences(MmsApp.getApplication())
				.getString(GeneralPreferenceActivity.KEY_ENCRYPTION, null) )) {
		    return false;
        }
        return true;
    }

    public void setEncryptionPreferenceChecked(){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(ENABLE_ENCRYPTION, true).commit();
    }
    //Modify chenshu 20151016 start no bug id, upgrade mms encryption end
    //Modify DWYSLM-486 chenshu 20160407 start
    //private static final String mRegex = "[0-9]*[a-zA-Z]+";
    private static final String mRegex = "[a-zA-Z]+";
    public static boolean isContainAlphabet(String psw){
        android.util.Log.e("chenshu","psw:"+psw);
        android.util.Log.e("chenshu","mRegex:"+mRegex);
        Pattern mPattern = Pattern.compile(mRegex);
		Matcher matcher = mPattern.matcher(psw);
        boolean containAlphabet = matcher.find();
        android.util.Log.e("chenshu","a:"+containAlphabet);
        return containAlphabet;
    }
    //Modify DWYSLM-486 chenshu 20160407 end
}
