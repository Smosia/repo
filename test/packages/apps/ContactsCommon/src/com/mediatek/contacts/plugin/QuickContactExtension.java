package com.mediatek.contacts.plugin;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

import com.mediatek.common.PluginImpl;
import com.mediatek.contacts.ext.DefaultQuickContactExtension;
import com.android.contacts.common.R;
import android.widget.Toast;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.content.ContentResolver;

@PluginImpl(interfaceName="com.mediatek.contacts.ext.IQuickContactExtension")
public class QuickContactExtension extends DefaultQuickContactExtension {
    private static final String TAG = "QuickContactExtension";

    private static final Uri CONTACTS_URI = Data.CONTENT_URI;
    private static final String[] CONTACTS_PROJECTION = new String[] {
        Phone.NUMBER,
        Phone.DISPLAY_NAME};
		
    private static final int BLACK_LIST_MENU_ID = 10002;
    private static final Uri CONTENT_URI = Uri.parse("content://com.cmcc.ccs.black_list");
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String NAME = "NAME";
    private static final String[] BLACK_LIST_PROJECTION = {
        PHONE_NUMBER
    };

    /**
     * for op01
     * @param durationView the duration text
     */
    @Override
    public void setDurationViewVisibility(TextView durationView) {
        Log.d(TAG, "setDurationViewVisibility : GONE");
        durationView.setVisibility(View.GONE);
    }

    /**
     * for op01,add for "blacklist" in call detail.
     * @param menu blacklist menu.
     * @param number phone number.
     * @param name contact name.
     */
    @Override
    public void onPrepareOptionsMenu(Context context, Menu menu, long id) {
        /* feature options    
        if (!SystemProperties.get("ro.mtk_op01_rcs").equals("1") ) {
            return;
        }*/

        if (id <= 0) {
            return;
        }
		
        StringBuilder selection = new StringBuilder(Phone.RAW_CONTACT_ID + "=" + id + " AND mimetype_id=5");

        log(selection.toString());
		ContentResolver resolver = context.getContentResolver();
        //Cursor cursorContact = resolver.query(CONTACTS_URI, CONTACTS_PROJECTION,
        //                                selection.toString(), null, null);
        Cursor cursorContact = resolver.query(CONTACTS_URI, CONTACTS_PROJECTION, selection.toString(), null, null);
		Log.i(TAG, "cursor cursorContact");
        if (cursorContact == null) {
            return;
        }
		Log.i(TAG, "cursor cursorContact cursorContact.getCount()="+cursorContact.getCount());
		 String strName = new String();
		 String[] strNumbers = new String[cursorContact.getCount()];
        int i = 0;
        try {
            cursorContact.moveToFirst();
            while (!cursorContact.isAfterLast()) {
                strNumbers[i] = cursorContact
                        .getString(cursorContact.getColumnIndexOrThrow(CONTACTS_PROJECTION[0]));
				Log.i(TAG, "cursor number="+strNumbers[i]);
                if (i == 0) {
                    strName = cursorContact
                        .getString(cursorContact.getColumnIndexOrThrow(CONTACTS_PROJECTION[1]));
				    Log.i(TAG, "cursor Name="+strName);
                }
                if (strNumbers[i] == null || strNumbers[i].isEmpty()) {
                    log("cursor is null or empty !");
                }
                cursorContact.moveToNext();
				i++;
           }
        } finally {
            cursorContact.close();
        }
		
        final String name = strName;
        final String[] numbers = strNumbers;
		
        MenuItem blackListMenu = menu.findItem(BLACK_LIST_MENU_ID);
        boolean isAutoRejectNumber = autoReject(context, numbers);
        if (blackListMenu != null) {
            menu.removeItem(BLACK_LIST_MENU_ID);
        }
		
        int menuIndex = menu.size();
        final Context fnContext = context;
        try {
            if (isAutoRejectNumber) {
                blackListMenu = menu.add(Menu.NONE, BLACK_LIST_MENU_ID, menuIndex,
                        fnContext.getText(R.string.remove_black_list));
                blackListMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (removeblackNumber(fnContext, numbers)) {
						    Toast.makeText(fnContext, R.string.remove_black_list_complete_toast,
                                Toast.LENGTH_SHORT).show();
						} else {
					        Toast.makeText(fnContext, R.string.remove_black_list_failed_toast,
                                Toast.LENGTH_SHORT).show();
						}
                        return true;
                    }
                });
            } else {
                blackListMenu = menu.add(Menu.NONE, BLACK_LIST_MENU_ID, menuIndex,
                        fnContext.getText(R.string.add_black_list));
                blackListMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                    	if (numbers.length > 0) {
	                        if (addblackNumber(fnContext, numbers, name)) {
							    Toast.makeText(fnContext, R.string.add_black_list_complete_toast,
	                                Toast.LENGTH_SHORT).show();
							} else {
						        Toast.makeText(fnContext, R.string.add_black_list_failed_toast,
	                                Toast.LENGTH_SHORT).show();
							}
                    	} else {
                    		Toast.makeText(fnContext, R.string.add_black_list_no_numbers,
	                                Toast.LENGTH_SHORT).show();
                    	}
                        return true;
                    }
                });
            }
        }  catch (Exception e) {
            Log.d(TAG, "no com.mediatek.op01.plugin packages");
        }
    }

    /**
     * check if the call should be rejected.
     * @param number the incoming call number.
     * @return the result that the current number should be auto reject.
     */
    public boolean autoReject(Context context, String[] numbers) {
        Log.d(TAG, "auto Reject");
        boolean result = false;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                    BLACK_LIST_PROJECTION, null, null, null);
            if (cursor == null) {
                Log.d(TAG, "cursor is null...");
                return false;
            }
            try {
			    boolean hasNumber;
			    for (String number : numbers) {
                    String blockNumber;
                    cursor.moveToFirst();
					hasNumber = false;
                    while (!cursor.isAfterLast()) {
                        blockNumber = cursor.getString(0);
                        if (PhoneNumberUtils.compare(number, blockNumber)) {
                            hasNumber = true;
                            break;
                        }
                        cursor.moveToNext();
                    }
					if (hasNumber == false) {
					    result = false;
						break;
					} else {
					    result = true;
					}
				}
            }
            finally {
                cursor.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "autoReject cursor exception");
        }
        return result;
    }

    /**
     * Add a pair of number and name to device's blacklist.
     * @param number the phone number, it's mandatory.
     * @param name the display name, it's optional.
     * @return ture if the data was added successfully, otherwise false.
     */
    public boolean addblackNumber(Context context, String[] numbers, String name) {

        Log.d(TAG, "add black Number");
		boolean result = true;
        for (String number : numbers) {
			ContentValues values = new ContentValues();
			values.put(PHONE_NUMBER, number);
			values.put(NAME, name);

			Log.d(TAG, "add number="+number+" name="+name);
			Uri resultUri = context.getContentResolver().insert(CONTENT_URI, values);

			if (resultUri == null) {
				result = false;
			}
        }
        return result;
    }

    /**
     * Remove a number from device's blacklist.
     * @param number the phone number, it's mandatory.
     * @return ture if the data was removed successfully, otherwise false.
     */
    public boolean removeblackNumber(Context context, String[] numbers) {
        Log.d(TAG, "remove black Number");
		boolean result = true;
		for (String number : numbers) {
            Uri uri = Uri.withAppendedPath(CONTENT_URI, Uri.encode(number));
            int retCount = context.getContentResolver().delete(uri, null, null);
            if (retCount <= 0) {
                result = false;
            }
		}

        return result;
    }

    /**
     * @param string.
     * @return true if string is number string.
     */
    public boolean isNumberString(String string) {
        boolean isNumber = false;
        String strCopy = new String(string);
        /*char firstChar = strCopy.charAt(0);
        if (firstChar == '+') {
            strCopy = strCopy.substring(1);
        }*/
        int index = strCopy.indexOf('+');
        Log.d(TAG, "isNumberString index: " + index);
        if(index == 0) {
            strCopy = strCopy.substring(index+1);
        }
        if (strCopy.length() > 0) {
            isNumber = strCopy.matches("[0-9]+");
        }
        Log.d(TAG, "isNumberString strCopy: " + strCopy);
        return isNumber;
    }
}