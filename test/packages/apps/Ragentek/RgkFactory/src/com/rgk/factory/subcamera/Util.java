/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.rgk.factory.subcamera;

import java.util.List;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Display;
/**
 * Collection of utility functions used in this package.
 */
public class Util {
    private static final String TAG = "Util";

    //hrl
    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
            int viewWidth, int viewHeight) {
        Log.d(TAG, "prepareMatrix mirror =" + mirror + " displayOrientation=" + displayOrientation
                + " viewWidth=" + viewWidth + " viewHeight=" + viewHeight);
        // Need mirror for front camera.
        matrix.setScale(mirror ? -1 : 1, 1);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate(displayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
        matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
    }
    
    public static int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }
    

    public static void rectFToRect(RectF rectF, Rect rect) {
        rect.left = Math.round(rectF.left);
        rect.top = Math.round(rectF.top);
        rect.right = Math.round(rectF.right);
        rect.bottom = Math.round(rectF.bottom);
    }
    
    public static int[] pointFToPoint(float[] point) {
        int[] pointF= new int[2];
        pointF[0] = Math.round(point[0]);
        pointF[1] = Math.round(point[1]);
        return pointF;
    }

    public static final double ASPECT_TOLERANCE = 0.07;
    public static final String PICTURE_RATIO_16_9 = "1.7778";
    public static final String PICTURE_RATIO_5_3 = "1.6667";
    public static final String PICTURE_RATIO_3_2 = "1.5";
    public static final String PICTURE_RATIO_4_3 = "1.3333";
    
    public static Size getOptimalPreviewSize(Activity currentActivity,
            List<Size> sizes, double targetRatio, boolean findMinalRatio, boolean needStandardPreview) {
        // Use a very small tolerance because we want an exact match.
        //final double EXACTLY_EQUAL = 0.001;
        if (sizes == null) {
            return null;
        }

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        double minDiffWidth = Double.MAX_VALUE;


        // Because of bugs of overlay and layout, we sometimes will try to
        // layout the viewfinder in the portrait orientation and thus get the
        // wrong size of preview surface. When we change the preview size, the
        // new overlay will be created before the old one closed, which causes
        // an exception. For now, just get the screen size.
        Display display = currentActivity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int targetHeight = Math.min(point.x, point.y);
        int targetWidth = Math.max(point.x, point.y);
        if (findMinalRatio) {
            //Find minimal aspect ratio for that: special video size maybe not have the mapping preview size.
            double minAspectio = Double.MAX_VALUE;
            for (Size size : sizes) {
                double aspectRatio = (double)size.width / size.height;
                if (Math.abs(aspectRatio - targetRatio)
                        <= Math.abs(minAspectio - targetRatio)) {
                    minAspectio = aspectRatio;
                }
            }
            Log.d(TAG, "getOptimalPreviewSize(" + targetRatio + ") minAspectio=" + minAspectio);
            targetRatio = minAspectio;
        }
        
        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
                minDiffWidth = Math.abs(size.width - targetWidth);
            } else if ((Math.abs(size.height - targetHeight) == minDiff)
                    && Math.abs(size.width - targetWidth) < minDiffWidth) {
                optimalSize = size;
                minDiffWidth = Math.abs(size.width - targetWidth);
            }
        }

        // Cannot find the one match the aspect ratio. This should not happen.
        // Ignore the requirement.
        /// M: This will happen when native return video size and wallpaper want to get specified ratio.
        if (optimalSize == null && needStandardPreview) {
            Log.w(TAG, "No preview size match the aspect ratio" + targetRatio + "," +
            		"then use the standard(4:3) preview size");
            minDiff = Double.MAX_VALUE;
            targetRatio = Double.parseDouble(PICTURE_RATIO_4_3);
            for (Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                    continue;
                }
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}