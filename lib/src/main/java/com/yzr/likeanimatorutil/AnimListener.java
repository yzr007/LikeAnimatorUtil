package com.yzr.likeanimatorutil;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by Yangzr on 2016/11/23.
 */

public class AnimListener implements ValueAnimator.AnimatorUpdateListener {
    private View target;

    public AnimListener(View target) {
        this.target = target;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        PointF pointF = (PointF) animation.getAnimatedValue();
        target.setX(pointF.x);
        target.setY(pointF.y);
    }


}
