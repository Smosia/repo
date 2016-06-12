
package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.UserHandle;
import android.preference.Preference;

import com.android.settings.R;

public class CyStorageItemPreference extends Preference {
    public final int color;
    public  int userHandle;

    public CyStorageItemPreference(Context context, int titleRes, int colorRes) {
        this(context,  colorRes);
    }

    public CyStorageItemPreference(
            Context context, int colorRes) {
        super(context);

        if (colorRes != 0) {
        	
        	
            //this.color = context.getResources().getColor(colorRes);
        	this.color = colorRes;
            final Resources res = context.getResources();
            final int width = res.getDimensionPixelSize(R.dimen.device_memory_usage_button_width);
            final int height = res.getDimensionPixelSize(R.dimen.device_memory_usage_button_width);
            setIcon(createRectShape(width, height, this.color));
        } else {
            this.color = Color.MAGENTA;
        }

        //setTitle(title);
        //setSummary(R.string.memory_calculating_size);

        //this.userHandle = userHandle;
    }

    private static ShapeDrawable createRectShape(int width, int height, int color) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.setIntrinsicHeight(height);
        shape.setIntrinsicWidth(width);
        shape.getPaint().setColor(color);
        return shape;
    }
}
