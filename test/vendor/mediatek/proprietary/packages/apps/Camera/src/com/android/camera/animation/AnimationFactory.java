package com.android.camera.animation;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class AnimationFactory {

	public static Animation createAnimationFor3DRotate(float fromDegrees,
			float toDegrees, float centerX, float centerY, float depthZ,
			boolean reverse) {
		return new Rotate3dAnimation(fromDegrees, toDegrees, centerX, centerY,
				depthZ, reverse);
	}
	
	public static Animation createScaleInnerAnimation() {
    	ScaleAnimation anim = new ScaleAnimation(1, 0.006f, 1, 0.8f,  
    			Animation.RELATIVE_TO_PARENT, 0.5f,  Animation.RELATIVE_TO_PARENT, 0.5f);
    	return anim;
    }
    
    public static Animation createScaleOuterAnimation() {
    	ScaleAnimation anim = new ScaleAnimation(0.006f, 1, 0.8f, 1,  
    			Animation.RELATIVE_TO_PARENT, 0.5f,  Animation.RELATIVE_TO_PARENT, 0.5f);
    	return anim;
    }

}
