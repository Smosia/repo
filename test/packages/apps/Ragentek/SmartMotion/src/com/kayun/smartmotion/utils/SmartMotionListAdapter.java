package com.kayun.smartmotion.utils;

import java.util.List;

import com.kayun.smartmotion.R;
import com.kayun.smartmotion.beans.SmartMotionListObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class SmartMotionListAdapter extends BaseAdapter{

    private static final String TAG = "SmartMotionListAdapter";

    private Context context;
    private int group;
    private List<SmartMotionListObject> list;

    public SmartMotionListAdapter(Context context,int group) {
        this.context = context;
        this.group = group;
        this.list = SmartMotionFactory.getListObjects(context, group);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Wrapper wrapper;

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.xml.smart_motion_item, null);
            wrapper = new Wrapper(convertView);
            convertView.setTag(wrapper);
        } else {
            wrapper = (Wrapper) convertView.getTag();
        }

        TextView titleView = wrapper.getTitleView();
        TextView summaryView = wrapper.getSummaryView();
        Switch controllerSwitch = wrapper.getControllerSwitch();

        titleView.setText(list.get(position).getTitle());
        summaryView.setText(list.get(position).getSummary());
        controllerSwitch.setChecked(isChecked(position));
        listening(controllerSwitch, position);

        return convertView;
    }

    private void listening(Switch switch_, final int position){
        switch_.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                String DBName = SmartMotionFactory.DBName(SmartMotionFactory.getFuncId(group, position));
                Settings.System.putInt(context.getContentResolver(), DBName, isChecked? 1:0);
            }
        });
    }

    class Wrapper {

        private TextView titleView;
        private TextView summaryView;
        private Switch controllerSwitch;
        private View rowView;

        public Wrapper(View rowView) {
            this.rowView = rowView;
        }

        public TextView getTitleView() {
            if (null == titleView) {
                titleView = (TextView) rowView.findViewById(R.id.smart_motion_title);
            }
            return titleView;
        }

        public TextView getSummaryView() {
            if (null == summaryView) {
                summaryView = (TextView) rowView.findViewById(R.id.smart_motion_summary);
            }
            return summaryView;
        }

        public Switch getControllerSwitch() {
            if (null == controllerSwitch) {
                controllerSwitch = (Switch) rowView.findViewById(R.id.smart_motion_switch);
            }
            return controllerSwitch;
        }
    }

    private boolean isChecked(int position){
        Log.d(TAG, "isChecked - position = " + position + ", group = " + group);
        String DBName = SmartMotionFactory.DBName(SmartMotionFactory.getFuncId(group, position));
        int value = Settings.System.getInt(context.getContentResolver(), DBName, -1);
        Log.d(TAG, "isChecked - DBName = " + DBName + ", value = " + value);
        if(value == -1){
            Settings.System.putInt(context.getContentResolver(), DBName, 0);
        }
        return value == 1;
    }
}
