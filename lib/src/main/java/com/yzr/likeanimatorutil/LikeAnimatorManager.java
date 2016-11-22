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
    Context context;

    //添加动画的目标View
    View view;

    //添加动画的坐标(目标View + 状态栏高度)
    int vX, vY;

    //目标View的宽高
    int vWidth, vHeight;

    //目标View的根布局
    ViewGroup vRootViewGroup;

    //点赞view的LayoutParams
    ViewGroup.LayoutParams likelayoutParams;

    Random random = new Random();

    //点赞动画的图片资源集合
    int[] drawableResourceIds;

    //点赞动画出现的数量，默认20
    int AnimCount = 20;

    //点赞动画的间隔时间，默认200ms
    int AnimDelay = 200;

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
     * @param vX
     * @param vY
     */
    public void setLocation(int vX,int vY) {
        this.vX = vX;
        this.vX = vY;
    }

    /**
     * 设置动画图片的大小
     * @param vWidth
     * @param vHeight
     */
    public void setSize(int vWidth,int vHeight) {
        this.vWidth = vWidth;
        this.vHeight = vHeight;
    }

    /**
     * 设置动画出现的数量
     * @param animCount
     */
    public void setAnimCount(int animCount) {
        AnimCount = animCount;
    }

    /**
     * 设置动画出现的间隔
     * @param animDelay
     */
    public void setAnimDelay(int animDelay) {
        AnimDelay = animDelay;
    }


    /**
     * 我们自定义一个BezierEvaluator 实现 TypeEvaluator
     * 由于我们view的移动需要控制x y 所以就传入PointF 作为参数,是不是感觉完全契合
     */
    public class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF pointF1;
        private PointF pointF2;

        public BezierEvaluator(PointF pointF1,PointF pointF2){
            this.pointF1 = pointF1;
            this.pointF2 = pointF2;
        }

        @Override
        public PointF evaluate(float time, PointF startValue,
                               PointF endValue) {

            float timeLeft = 1.0f - time;
            PointF point = new PointF();//结果

            point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
                    + 3 * timeLeft * timeLeft * time * (pointF1.x)
                    + 3 * timeLeft * time * time * (pointF2.x)
                    + time * time * time * (endValue.x);

            point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
                    + 3 * timeLeft * timeLeft * time * (pointF1.y)
                    + 3 * timeLeft * time * time * (pointF2.y)
                    + time * time * time * (endValue.y);
            return point;
        }
    }

    /**
     * 贝塞尔曲线的动画实现
     */

    private ValueAnimator getBezierValueAnimator(View target) {
        //初始化一个BezierEvaluator
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(0), getPointF(1));

        //第一个PointF传入的是初始点的位置
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(vX, vY), new PointF(vX - 50 + random.nextInt(100), vY - 600));//随机
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(2000);
        return animator;
    }
    /**
     * 获取中间的两个点
     */
    private PointF getPointF(int scale) {

        PointF pointF = new PointF();
        pointF.x = vX -100 + random.nextInt(200);
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = vY - 300*scale - random.nextInt(300);
        return pointF;
    }

    /**
     * 只有在回调里使用了计算的值,才能真正做到曲线运动
     */
    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {
        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // alpha动画
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
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
        likelayoutParams = new ViewGroup.LayoutParams(vWidth,vHeight);
        for(int i = 0 ; i<AnimCount ; i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(drawableResourceIds[random.nextInt(drawableResourceIds.length)]);
            imageView.setVisibility(View.GONE);
            vRootViewGroup.addView(imageView,likelayoutParams);
            Animator set = getBezierValueAnimator(imageView);
            set.addListener(new AnimEndListener(imageView));
            set.setStartDelay(AnimDelay*i);
            animatorSet.play(set);
        }
        animatorSet.start();
    }

}
