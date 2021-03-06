/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mediatek.common.view.tests.animation;

import com.mediatek.common.view.tests.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutAnimationActivity extends Activity {
    private int mCount = 1;
    Button mButton;
    private Button mLastButton;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.animation_two);
        mButton = (Button) findViewById(R.id.button1);

        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Button newButton = new Button(LayoutAnimationActivity.this);
                newButton.setText("Button:" + LayoutAnimationActivity.this.mCount);
                LinearLayout layout = (LinearLayout) findViewById(R.id.container);
                layout.addView(newButton);
                LayoutAnimationActivity.this.mCount++;
                mLastButton = newButton;
            }
        });
    }

    public Button getLastButton() {
        return mLastButton;
    }
}
