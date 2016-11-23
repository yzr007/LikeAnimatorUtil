package com.yzr.likeanimatorutil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Yangzr on 2016/11/22.
 */

public class LikeAnimatorManager {
    //Activity的上下文
    private Context context;

    //添加动画的目标View
    private View view;

    //添加动画的坐标(目标View + 状态栏高度)
    private int vX, vY;

    //目标View的宽高
    private int vWidth, vHeight;

    //目标View的根布局
    private ViewGroup vRootViewGroup;

    private Random random = new Random();

    //点赞动画的图片资源集合
    private int[] drawableResourceIds;

    //点赞动画出现的数量，默认20
    private int AnimCount = 20;

    //点赞动画的间隔时间，默认200ms
    private int AnimDelay = 200;

    //点赞动画的单次播放时间，默认2000ms
    private int AnimDuration = 2000;

    //点赞动画的活动高度
    private int AnimPathHeight = 600;

    //点赞动画的横向活动范围
    private int AnimHorizontalScope = 200;

    public LikeAnimatorManager(Context context,View target,int[] drawableResourceIds) {
        this.context = context;
        this.view = target;
        this.vRootViewGroup = LayoutUtil.getRootView(context);
        //默认坐标为目标view的坐标
        vX = LayoutUtil.getScreenX(target);
        vY = LayoutUtil.getScreenY(target)+(LayoutUtil.isShowStatus(context)?LayoutUtil.getStatusHeight(context):0);
        //默认大小为目标view的大小
        vWidth = target.getWidth();
        vHeight = target.getHeight();
        this.drawableResourceIds = drawableResourceIds;
    }

    /**
     * 设置动画出现的位置
     */
    public void setLocation(int vX,int vY) {
        this.vX = vX;
        this.vY = vY;
    }

    /**
     * 设置动画图片的大小
     */
    public void setSize(int vWidth,int vHeight) {
        this.vWidth = vWidth;
        this.vHeight = vHeight;
    }

    /**
     * 设置动画出现的数量
     */
    public void setAnimCount(int animCount) {
        AnimCount = animCount;
    }

    /**
     * 设置动画出现的间隔
     */
    public void setAnimDelay(int animDelay) {
        AnimDelay = animDelay;
    }



    //TODO 拆分缩放和透明度变化 设置参数
    /**
     * 通过属性动画 实现爱心图片的缩放和透明度变化的动画效果
     * target 就是爱心图片的view
     */
    private AnimatorSet getEnterAnimtor(final View target){


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);

        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());//线性变化
        enter.playTogether(scaleX,scaleY);
        enter.setTarget(target);

        return enter;
    }

    /**
     * 动画结束后，remove
     */
    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            target.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            vRootViewGroup.removeView((target));
        }
    }

    public void addFavor() {
        AnimatorSet animatorSet = new AnimatorSet();
        ViewGroup.LayoutParams likelayoutParams = new ViewGroup.LayoutParams(vWidth, vHeight);
        BezierAnimGenerator bezierAnimGenerator = new BezierAnimGenerator();
        bezierAnimGenerator.setDuration(AnimDuration);
        bezierAnimGenerator.setAnimPathHeight(AnimPathHeight);
        bezierAnimGenerator.setHorizontalScope(AnimHorizontalScope);
        bezierAnimGenerator.setStartLocation(vX,vY);
        for(int i = 0 ; i<AnimCount ; i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(drawableResourceIds[random.nextInt(drawableResourceIds.length)]);
            imageView.setVisibility(View.GONE);
            vRootViewGroup.addView(imageView, likelayoutParams);
            Animator set = bezierAnimGenerator.animgenerate(imageView);
            set.addListener(new AnimEndListener(imageView));
            set.setStartDelay(AnimDelay*i);
            animatorSet.play(set);
        }
        animatorSet.start();
    }

}
