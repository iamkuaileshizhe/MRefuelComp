package com.neocom.mobilerefueling.downloadeffects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by smartTop on 2016/12/23.
 */

public class SlideRight extends BaseEffects {
    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationX",300,0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

        );
    }
}
