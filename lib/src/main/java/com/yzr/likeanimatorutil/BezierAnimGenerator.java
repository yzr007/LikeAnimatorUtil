package com.yzr.likeanimatorutil;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

import java.util.Random;

/**
 * Created by Yangzr on 2016/11/23.
 */

public class BezierAnimGenerator extends AnimGenerator{
    private BezierAnimEvaluator bezierAnimEvaluator;
    private Random random;

    public BezierAnimGenerator(){
        random = new Random();
    }

    private void initAnimEvaluator(){
        bezierAnimEvaluator = new BezierAnimEvaluator(getPointF(0),getPointF(1));

    }
    @Override
    public ValueAnimator animgenerate(View target) {
        initAnimEvaluator();
        ValueAnimator animator = ValueAnimator.ofObject(bezierAnimEvaluator, new PointF(startX, startY), new PointF(startX - horizontalScope/2 + random.nextInt(horizontalScope), startY - animPathHeight));
        AnimListener bezierAnimListener = new AnimListener(target);
        animator.addUpdateListener(bezierAnimListener);
        animator.setTarget(target);
        animator.setDuration(2000);
        return animator;
    }


    /**
     * 获取中间的两个点
     */
    private PointF getPointF(int index) {
        PointF pointF = new PointF();
        pointF.x = startX - horizontalScope/2 + random.nextInt(horizontalScope);
        int halfHeight = animPathHeight/2;
        pointF.y = startY - halfHeight*index - random.nextInt(halfHeight);
        return pointF;
    }
}
