package com.kayun.smartmotion.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kayun.smartmotion.R;
import com.kayun.smartmotion.beans.SmartMotionDialogInfo;
import com.kayun.smartmotion.beans.SmartMotionListObject;

public class SmartMotionFactory {

    public static final String TAG = "SmartMotionFactory";

    public static List<SmartMotionListObject> getListObjects(Context context, int id) {

        String[] titles = null;
        String[] summarys = null;
        ArrayList<SmartMotionListObject> objects = new ArrayList<>();

        switch (id) {
        case Constants.SCREEN_GROUP_ID:
            titles = context.getResources().getStringArray(R.array.screen_group_list_titles);
            summarys = context.getResources().getStringArray(R.array.screen_group_list_summarys);
            break;
        case Constants.CALL_GROUP_ID:
            titles = context.getResources().getStringArray(R.array.call_group_list_titles);
            summarys = context.getResources().getStringArray(R.array.call_group_list_summarys);
            break;
        case Constants.OTHERS_GROUP_ID:
            titles = context.getResources().getStringArray(R.array.others_group_list_titles);
            summarys = context.getResources().getStringArray(R.array.others_group_list_summarys);
            break;
        default:
            break;
        }

        for (int index = 0; index < titles.length; index++) {
            SmartMotionListObject object = new SmartMotionListObject(titles[index], summarys[index]);
            objects.add(object);
        }
        return objects;
    }

    /**
     * @category calculate the list height.
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
        if (null == adapter) {
            return;
        }
        int totalHeight = 0;
        for (int conunt = 0; conunt < adapter.getCount(); conunt++) {
            View listItem = adapter.getView(conunt, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));

        listView.setLayoutParams(params);
    }

    /**
     * @category get the function ID base on group ID and position ID.
     * @param group
     * @param position
     * @return
     */
    public static int getFuncId(int group, int position) {

        int id = Constants.INIT_INT_VALUE;

        if (group == Constants.SCREEN_GROUP_ID) {
            switch (position) {
            case 0:
                id = Constants.ACTION_UNLOCK;
                break;
            default:
                break;
            }
        } else if (group == Constants.CALL_GROUP_ID) {
            switch (position) {
            case 0:
                id = Constants.ANSWER_BY_SWING;
                break;
            case 1:
                id = Constants.SMART_ANSWER;
                break;
            case 2:
                id = Constants.SMART_CALL;
                break;
            case 3:
                id = Constants.SMART_SWITCH;
                break;
            case 4:
                id = Constants.TURN_TO_SILENCE;
            default:
                break;
            }
        } else if (group == Constants.OTHERS_GROUP_ID) {
            switch (position) {
            case 0:
                id = Constants.TURN_TO_SNOOZE;
                break;
            default:
                break;
            }
        } else {
            Log.d(TAG, "getFuncId - error group id.");
        }
        Log.d(TAG, "getFuncId - group = " + group + ", position = " + position + ", id = " + id);

        return id;
    }

    /**
     * @category get dialog drawable base on function ID.
     * @param funcID
     * @return
     */
    public static SmartMotionDialogInfo getTryDialogInfoById(int funcID) {

        Log.d(TAG, "getTryDialogInfoById - funcID = " + funcID);

        int dialogTitle = Constants.INIT_INT_VALUE;
        int dialogMessage = Constants.INIT_INT_VALUE;
        int dialogDrawable = Constants.INIT_INT_VALUE;

        switch (funcID) {
        case Constants.ACTION_UNLOCK:
            dialogTitle = R.string.action_unlock_dialog_title;
            dialogMessage = R.string.action_unlock_dialog_message;
            dialogDrawable = R.drawable.action_unlock_dialog_drawable;
            break;
        case Constants.ANSWER_BY_SWING:
            dialogTitle = R.string.answer_by_swing_dialog_title;
            dialogMessage = R.string.answer_by_swing_dialog_message;
            dialogDrawable = R.drawable.answer_by_swing_dialog_drawable;
            break;
        case Constants.SMART_ANSWER:
            dialogTitle = R.string.smart_answer_dialog_title;
            dialogMessage = R.string.smart_answer_dialog_message;
            dialogDrawable = R.drawable.smart_answer_dialog_drawable;
            break;
        case Constants.SMART_CALL:
            dialogTitle = R.string.smart_call_dialog_title;
            dialogMessage = R.string.smart_call_dialog_message;
            dialogDrawable = R.drawable.smart_call_dialog_drawable;
            break;
        case Constants.SMART_SWITCH:
            dialogTitle = R.string.smart_switch_dialog_title;
            dialogMessage = R.string.smart_switch_dialog_message;
            dialogDrawable = R.drawable.smart_switch_dialog_drawable;
            break;
        case Constants.TURN_TO_SILENCE:
            dialogTitle = R.string.turn_to_silence_dialog_title;
            dialogMessage = R.string.turn_to_silence_dialog_message;
            dialogDrawable = R.drawable.turn_to_silence_dialog_drawable;
            break;
        case Constants.TURN_TO_SNOOZE:
            dialogTitle = R.string.turn_to_snooze_dialog_title;
            dialogMessage = R.string.turn_to_snooze_dialog_message;
            dialogDrawable = R.drawable.turn_to_snooze_dialog_drawable;
            break;
        default:
            break;
        }
        Log.d(TAG, "getTryDialogInfoById - dialogTitle = " + dialogTitle + ", dialogMessage = "
                    + dialogMessage + ", dialogDrawable = " + dialogDrawable);

        return new SmartMotionDialogInfo(dialogTitle, dialogMessage, dialogDrawable);
    }

    public static int getComingAnimById(int id){

        Log.d(TAG, "getComingAnimById - id = " + id);
        int anim = Constants.INIT_INT_VALUE;
        switch (id) {
        case Constants.ACTION_UNLOCK:
            anim = R.drawable.action_unlock_incoming;
            break;
        case Constants.ANSWER_BY_SWING:
            anim = R.drawable.answer_by_swing_incoming;
            break;
        case Constants.SMART_ANSWER:
            anim = R.drawable.smart_answer_incoming;
            break;
        case Constants.SMART_CALL:
            anim = R.drawable.smart_call_incoming;
            break;
        case Constants.SMART_SWITCH:
            anim = R.drawable.smart_switch_incoming;
            break;
        case Constants.TURN_TO_SILENCE:
            anim = R.drawable.turn_to_silence_incoming;
            break;
        case Constants.TURN_TO_SNOOZE:
            anim = R.drawable.turn_to_snooze_incoming;
            break;
        default:
            break;
        }
        return anim;
    }

    public static int getGoingAnimById(int id){

        Log.d(TAG, "getGoingAnimById - id = " + id);
        int anim = Constants.INIT_INT_VALUE;
        switch (id) {
        case Constants.ACTION_UNLOCK:
            anim = R.drawable.action_unlock;
            break;
        case Constants.ANSWER_BY_SWING:
            anim = R.drawable.answer_by_swing_conversing;
            break;
        case Constants.SMART_ANSWER:
            anim = R.drawable.smart_answer_conversing;
            break;
        case Constants.SMART_CALL:
            anim = R.drawable.smart_call_dialing;
            break;
        case Constants.SMART_SWITCH:
            anim = R.drawable.smart_switch_closed_speaker;
            break;
        case Constants.TURN_TO_SILENCE:
            anim = R.drawable.turn_to_silence_going;
            break;
        case Constants.TURN_TO_SNOOZE:
            anim = R.drawable.turn_to_snoozed;
            break;
        default:
            break;
        }
        return anim;
    }

    public static String DBName(int id){
        Log.d(TAG, "DBName - id = " + id);
        String name = null;
        switch (id) {
        case Constants.ACTION_UNLOCK:
            name = Constants.DB_ACTION_UNLOCK;
            break;
        case Constants.ANSWER_BY_SWING:
            name = Constants.DB_ANSWER_BY_SWING;
            break;
        case Constants.SMART_ANSWER:
            name = Constants.DB_SMART_ANSWER;
            break;
        case Constants.SMART_CALL:
            name = Constants.DB_SMART_CALL;
            break;
        case Constants.SMART_SWITCH:
            name = Constants.DB_SMART_SWITCH;
            break;
        case Constants.TURN_TO_SILENCE:
            name = Constants.DB_TURN_TO_SILENCE;
            break;
        case Constants.TURN_TO_SNOOZE:
            name = Constants.DB_TURN_TO_SNOOZE;
            break;
        default:
            break;
        }
        Log.d(TAG, "DBName - name = " + name);
        return name;
    }
}
