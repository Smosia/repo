package com.android.settings.emergencyassistant;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.settings.R;

/**
 * Created by maoxunlei on 13-7-25.
 */
public class EmergencyAddContactTitleLayout extends RelativeLayout {

    private ImageView mDeleteContact;
    private TextView mContactCountView;
    private String currentContactStr;

    private TextView mCancelDeleteButton, mDeleteContactButton;
    private View view;
    private Context mContext;
    private OnClickListener mListener;

    public EmergencyAddContactTitleLayout(Context context, int contactCount, OnClickListener listener, boolean deleteContact) {
        super(context);
       
        mContext = context;
        mListener = listener;
        updateTitleLayout(deleteContact, contactCount);
       
    }

    public void updateView(int contactCount) {
        if (mContactCountView != null) {
            currentContactStr = getResources().getString(R.string.current_contact_added, contactCount);
            if (currentContactStr != null) {
                mContactCountView.setText(currentContactStr);
            }
        }
    }

    public void updateTitleLayout(boolean deleteContact, int currentContact) {
        removeView(view);
        if (deleteContact) {
            view = View.inflate(mContext, R.layout.emergency_delete_contact_title_layout, null);
            mCancelDeleteButton = (TextView) view.findViewById(R.id.delete_contact_button);
            mDeleteContactButton = (TextView) view.findViewById(R.id.cancel_delete_contact);
            //mContactToFormer = (ImageView) view.findViewById(R.id.contact_to_former_page);
            mCancelDeleteButton.setOnClickListener(mListener);  
            mDeleteContactButton.setOnClickListener(mListener);
            //mContactToFormer.setOnClickListener(mListener);
        } else {
            view = View.inflate(mContext, R.layout.emergency_add_contact_title_layout, null);
            mContactCountView = (TextView) view.findViewById(R.id.contact_count);
            //mContactToFormer = (ImageView) view.findViewById(R.id.contact_to_former_page);
            mDeleteContact = (ImageView) view.findViewById(R.id.delete_contact);
            currentContactStr = getResources().getString(R.string.current_contact_added, currentContact);
            if (currentContactStr != null) {
                mContactCountView.setText(currentContactStr);
            }

            //mContactToFormer.setOnClickListener(mListener);
            mDeleteContact.setOnClickListener(mListener);
        }
        addView(view);
    }
}
