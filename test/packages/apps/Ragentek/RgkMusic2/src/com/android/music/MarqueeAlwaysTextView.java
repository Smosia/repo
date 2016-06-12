/***********************************************
 **add Bug_id:DWYEL-74 gaoxueyan 20141015
***********************************************/

package com.android.music;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;


@RemoteView
public class MarqueeAlwaysTextView extends TextView {
    public MarqueeAlwaysTextView(Context context) {
        super(context);
    }

    public MarqueeAlwaysTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeAlwaysTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
