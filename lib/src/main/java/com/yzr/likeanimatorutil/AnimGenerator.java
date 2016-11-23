package com.yzr.likeanimatorutil;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Yangzr on 2016/11/23.
 */

public abstract class AnimGenerator {
    protected int duration = 2000;
    protected int animPathHeight = 600;
    protected int startX = 100,startY = 1000;
    protected int horizontalScope = 200;


    public abstract ValueAnimator animgenerate(View target);

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAnimPathHeight(int height) {
        this.animPathHeight = height;
    }

    public void setStartLocation(int startX,int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public void setHorizontalScope(int horizontalScope) {
        this.horizontalScope = horizontalScope;
    }
}
