/**
* add this file : JEYS-57 zhangzixiao 20131226
*/
package com.android.mms.ui;




import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.mms.R;

public class ScheduledMessage extends Activity {
	public static final int YEAR_MIN = 1970;
	public static final int YEAR_MAX = 2037;

	private Calendar mCalendar;
	private DatePickerDialog mDatePickerDialog;
	private TimePickerDialog mTimePickerDialog;

	private Button dateText;
	private Button timeText;
	private Button comfirmBtn;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled_message);
        context = this;
        
        initActionBar();
        
        mCalendar = Calendar.getInstance();
        long milliseconds = ScheduledMessageUtils.getScheduledTime(context);
        if(milliseconds > 0) {
            mCalendar.setTimeInMillis(milliseconds);
        }
        
        long timeMillis = mCalendar.getTimeInMillis();

        dateText = (Button) findViewById(R.id.date_text);
        dateText.setOnClickListener(mClickListener);
        timeText = (Button) findViewById(R.id.time_text);
        timeText.setOnClickListener(mClickListener);
        comfirmBtn = (Button) findViewById(R.id.confirm_btn);
        comfirmBtn.setOnClickListener(mClickListener);

        setDate(dateText, timeMillis);
        setTime(timeText, timeMillis);
    }
	
    private void initActionBar() {
        ActionBar actionBar = this.getActionBar();
        actionBar.setCustomView(R.layout.scheduled_message_action_bar);
        View customView = actionBar.getCustomView();
        Button okBut = (Button) customView.findViewById(R.id.ok);
        //M: JWYYL-732  guochenlong  20141224 (START) @{
        /*
         * do this at btn_finish
         */
        okBut.setVisibility(View.GONE);
        /*
        okBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                calendar.set(Calendar.YEAR, datePicker.getYear());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());			
                calendar.set(Calendar.SECOND, 0);
                long timeInMillis = calendar.getTimeInMillis();
                ScheduledMessageUtils.setScheduledTime(context, timeInMillis);
            Toast.makeText(context, R.string.done, Toast.LENGTH_SHORT).show();
            finish();
            }
        });
        */
        //M: JWYYL-732  (END) }@

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
	
    

    private void configureDatePicker(DatePicker datePicker) {
        // The system clock can't represent dates outside this range.
        Calendar t = Calendar.getInstance();
        t.clear();
        t.set(YEAR_MIN, Calendar.JANUARY, 1);
        datePicker.setMinDate(t.getTimeInMillis());
        t.clear();
		t.set(YEAR_MAX, Calendar.DECEMBER, 31);
        datePicker.setMaxDate(t.getTimeInMillis());
    }

    private void setTime(TextView view, long millis) {
        int flags = DateUtils.FORMAT_SHOW_TIME;
        flags |= DateUtils.FORMAT_CAP_NOON_MIDNIGHT;
        if (DateFormat.is24HourFormat(this)) {
            flags |= DateUtils.FORMAT_24HOUR;
        }

        String timeString;

        timeString = DateUtils.formatDateTime(this, millis, flags);

        view.setText(timeString);
    }

	private void setDate(TextView view, long millis) {
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH
				| DateUtils.FORMAT_ABBREV_WEEKDAY;

		String dateString = null;
		dateString = DateUtils.formatDateTime(this, millis, flags);
		view.setText(dateString);
	}

    private void showDatePickerDialog() {
		if (mDatePickerDialog != null) {
			mDatePickerDialog.dismiss();
		}

		mDatePickerDialog = new DatePickerDialog(context, R.style.SmsAlertDialog, dateCallBack,
				mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH));
		DatePicker datePicker = mDatePickerDialog.getDatePicker();
		configureDatePicker(datePicker);
		mDatePickerDialog.show();
	}

	private void showTimePickerDialog() {
		if (mTimePickerDialog != null) {
			mTimePickerDialog.dismiss();
		}
		mTimePickerDialog = new TimePickerDialog(context,
				timeCallBack, mCalendar.get(Calendar.HOUR_OF_DAY),
				mCalendar.get(Calendar.MINUTE),
				DateFormat.is24HourFormat(context));
		mTimePickerDialog.show();
	}
    
	OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.date_text:
				showDatePickerDialog();
				break;
			case R.id.time_text:
				showTimePickerDialog();
				break;

			case R.id.confirm_btn:
				Log.d("guocl", "year=" + mCalendar.get(Calendar.YEAR)
						+ ", month=" + mCalendar.get(Calendar.MONTH)
						+ ", monthDay=" + mCalendar.get(Calendar.DAY_OF_MONTH));
				Log.d("guocl", "hour=" + mCalendar.get(Calendar.HOUR_OF_DAY)
						+ ", minute=" + mCalendar.get(Calendar.MINUTE)
						+ ", second=" + mCalendar.get(Calendar.SECOND));
                ScheduledMessageUtils.setScheduledTime(context,  mCalendar.getTimeInMillis());
                finish();
				break;

			default:
				break;
			}
		}
	};

	OnDateSetListener dateCallBack = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int monthDay) {
			Log.d("guocl", "year=" + year + ", month=" + month + ", monthDay="
					+ monthDay);

			mCalendar.set(Calendar.YEAR, year);
			mCalendar.set(Calendar.MONTH, month);
			mCalendar.set(Calendar.DAY_OF_MONTH, monthDay);

			long timeMillis = mCalendar.getTimeInMillis();
			setDate(dateText, timeMillis);
		}
	};

	OnTimeSetListener timeCallBack = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
			mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendar.set(Calendar.MINUTE, minute);
			mCalendar.set(Calendar.SECOND, 0);

			long timeMillis = mCalendar.getTimeInMillis();
			setTime(timeText, timeMillis);
		}
	};    
    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mDatePickerDialog != null && mDatePickerDialog.isShowing()) {
			DatePicker datePicker = mDatePickerDialog.getDatePicker();
			if (datePicker != null) {
				mCalendar.set(Calendar.YEAR, datePicker.getYear());
				mCalendar.set(Calendar.MONTH, datePicker.getMonth());
				mCalendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
			}
			showDatePickerDialog();
		} else if (mTimePickerDialog != null && mTimePickerDialog.isShowing()){
			showTimePickerDialog();
		}
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
	int itemId = item.getItemId();
	switch(itemId) {
	case android.R.id.home:
	    finish();
	    return true;
	}
	return super.onMenuItemSelected(featureId, item);
    }
}
