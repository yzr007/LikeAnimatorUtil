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

    //点赞动画出现的最大数量
    private final int MAXANIMCOUNT = 200;

    //点赞动画的间隔时间，默认200ms
    private int AnimDelay = 200;

    //点赞动画的单次播放时间，默认2000ms
    private int AnimDuration = 2000;

    //点赞动画的活动高度
    private int AnimPathHeight = 600;

    //点赞动画的横向活动范围
    private int AnimHorizontalScope = 200;

    //是否执行缩放动画
    private boolean ScaleAble = false;

    //缩放动画的缩放范围 支持负数 最小支持到-1 默认0.5
    private float scaleScope = 0.5f;

    //是否执行透明度动画
    private boolean AlphaAble = false;

    //透明度动画的起始于终止值 默认从不透明到透明 1到0
    private float startAlpha = 1f;
    private float endAlpha = 0f;

    //循环播放标志位
    private boolean loop;

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
        this.vWidth = Math.abs(vWidth);
        this.vHeight = Math.abs(vHeight);
    }

    /**
     * 设置点赞动画的单次播放时间
     */
    public void setAnimDuration(int animDuration) {
        if(animDuration < 0){
            return;
        }
        AnimDuration = animDuration;
    }

    /**
     * 设置点赞动画的活动高度
     */
    public void setAnimPathHeight(int animPathHeight) {
        AnimPathHeight = Math.abs(animPathHeight);
    }

    /**
     * 设置点赞动画的横向活动范围
     */
    public void setAnimHorizontalScope(int animHorizontalScope) {
        AnimHorizontalScope = animHorizontalScope;
    }

    /**
     * 设置动画出现的数量
     */
    public void setAnimCount(int animCount) {
        if(animCount < 0){
            return;
        }
        if(animCount > MAXANIMCOUNT){
            AnimCount = MAXANIMCOUNT;
            return;
        }
        AnimCount = animCount;
    }

    /**
     * 设置动画出现的间隔
     */
    public void setAnimDelay(int animDelay) {
        if(animDelay < 0){
            return;
        }
        AnimDelay = animDelay;
    }

    public boolean isScaleAble() {
        return ScaleAble;
    }

    /**
     * 设置是否执行缩放动画
     */
    public void setScaleAble(boolean scaleAble) {
        ScaleAble = scaleAble;
    }

    public boolean isAlphaAble() {
        return AlphaAble;
    }

    /**
     * 设置是否执行透明动画
     */
    public void setAlphaAble(boolean alphaAble) {
        AlphaAble = alphaAble;
    }

    /**
     * 设置缩放动画的缩放值
     */
    public void setScaleScope(float scaleScope) {
        if(scaleScope < -1){
            scaleScope = -1;
        }
        this.scaleScope = scaleScope;
    }

    /**
     * 设置透明度动画的起始透明度
     */
    public void setStartAlpha(float startAlpha) {
        this.startAlpha = startAlpha;
    }
    /**
     * 设置透明度动画的终止透明度
     */
    public void setEndAlpha(float endAlpha) {
        this.endAlpha = endAlpha;
    }

    /**
     * 通过属性动画 实现图片的缩放动画效果
     */
    private AnimatorSet getScaleAnimtor(View target,int animDelay){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1f, 1f+scaleScope);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1f, 1f+scaleScope);
        AnimatorSet scaleAnimtor = new AnimatorSet();
        scaleAnimtor.setDuration(AnimDuration);
        scaleAnimtor.setStartDelay(animDelay);
        scaleAnimtor.setInterpolator(new LinearInterpolator());//线性变化
        scaleAnimtor.playTogether(scaleX,scaleY);
        scaleAnimtor.setTarget(target);
        return scaleAnimtor;
    }

    /**
     * 通过属性动画 实现图片的透明度变化的动画效果
     */
    private AnimatorSet getAlphaAnimtor(View target,int animDelay){

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, startAlpha, endAlpha);
        AnimatorSet alphaAnimtor = new AnimatorSet();
        alphaAnimtor.setDuration(AnimDuration);
        alphaAnimtor.setStartDelay(animDelay);
        alphaAnimtor.setInterpolator(new LinearInterpolator());//线性变化
        alphaAnimtor.playTogether(alpha);
        alphaAnimtor.setTarget(target);
        return alphaAnimtor;
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

    public void play() {
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
            if(ScaleAble) animatorSet.play(getScaleAnimtor(imageView,AnimDelay*i));
            if(AlphaAble) animatorSet.play(getAlphaAnimtor(imageView,AnimDelay*i));
        }
        animatorSet.start();
    }

}
