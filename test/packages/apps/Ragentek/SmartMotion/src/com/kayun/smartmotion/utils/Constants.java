package com.kayun.smartmotion.utils;

public class Constants {

    public static final int INIT_INT_VALUE = -1;

    public static final int SCREEN_GROUP_ID = 1 << 0;
    public static final int CALL_GROUP_ID 	= 1 << 1;
    public static final int OTHERS_GROUP_ID = 1 << 2;

    public static final int ACTION_UNLOCK 	= 1 << 0;
    public static final int ANSWER_BY_SWING = 1 << 1;
    public static final int SMART_ANSWER 	= 1 << 2;
    public static final int SMART_CALL 		= 1 << 3;
    public static final int SMART_SWITCH 	= 1 << 4;
    public static final int TURN_TO_SILENCE = 1 << 5;
    public static final int TURN_TO_SNOOZE 	= 1 << 6;

    public static final String DB_ALL_SWITCH = "all_switch";

    public static final String DB_ACTION_UNLOCK 	= "action_unlock";
    public static final String DB_ANSWER_BY_SWING 	= "answer_by_swing";
    public static final String DB_SMART_ANSWER 		= "smart_answer";
    public static final String DB_SMART_CALL 		= "smart_call";
    public static final String DB_SMART_SWITCH 		= "smart_switch";
    public static final String DB_TURN_TO_SILENCE 	= "turn_to_silence";
    public static final String DB_TURN_TO_SNOOZE 	= "turn_to_snooze";
}
