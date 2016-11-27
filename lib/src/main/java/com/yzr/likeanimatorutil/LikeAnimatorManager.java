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
    //���޶�������
    private AnimatorSet animatorSet;

    //�켣����
    private AnimGenerator animGenerator;

    //Activity��������
    private Context context;

    //��Ӷ�����Ŀ��View
    private View view;

    //��Ӷ���������(Ŀ��View + ״̬���߶�)
    private int vX, vY;

    //Ŀ��View�Ŀ��
    private int vWidth, vHeight;

    //Ŀ��View�ĸ�����
    private ViewGroup vRootViewGroup;

    private Random random = new Random();

    //���޶�����ͼƬ��Դ����
    private int[] drawableResourceIds;

    //���޶������ֵ�������Ĭ��20
    private int AnimCount = 20;

    //���޶������ֵ��������
    private final int MAXANIMCOUNT = 200;

    //���޶����ļ��ʱ�䣬Ĭ��200ms
    private int AnimDelay = 200;

    //���޶����ĵ��β���ʱ�䣬Ĭ��2000ms
    private int AnimDuration = 2000;

    //���޶����Ļ�߶�
    private int AnimPathHeight = 600;

    //���޶����ĺ�����Χ
    private int AnimHorizontalScope = 200;

    //�Ƿ�ִ�����Ŷ���
    private boolean ScaleAble = false;

    //���Ŷ��������ŷ�Χ ֧�ָ��� ��С֧�ֵ�-1 Ĭ��0.5
    private float scaleScope = 0.5f;

    //�Ƿ�ִ��͸���ȶ���
    private boolean AlphaAble = false;

    //͸���ȶ�������ʼ����ֵֹ Ĭ�ϴӲ�͸����͸�� 1��0
    private float startAlpha = 1f;
    private float endAlpha = 0f;

    //ѭ�����ű�־λ
    private boolean loop;

    public LikeAnimatorManager(Context context,View target,int[] drawableResourceIds) {
        this.context = context;
        this.view = target;
        this.vRootViewGroup = LayoutUtil.getRootView(context);
        //Ĭ������ΪĿ��view������
        vX = LayoutUtil.getScreenX(target);
        vY = LayoutUtil.getScreenY(target);//-(LayoutUtil.isShowStatus(context)?LayoutUtil.getStatusHeight(context):0);
        //Ĭ�ϴ�СΪĿ��view�Ĵ�С
        vWidth = target.getWidth();
        vHeight = target.getHeight();
        this.drawableResourceIds = drawableResourceIds;
        animGenerator = new BezierAnimGenerator();
    }

    /**
     *�Զ���켣����������
     * @param animGenerator �켣����������
     */
    public void setAnimGenerator(AnimGenerator animGenerator) {
        this.animGenerator = animGenerator;
    }

    /**
     * ���ö������ֵ�λ��
     * @param vX ��������λ������Ļ�ϵ�X����
     * @param vY ��������λ������Ļ�ϵ�Y����
     */
    public void setLocation(int vX,int vY) {
        this.vX = vX;
        this.vY = vY;
    }

    public int getvX() {
        return vX;
    }

    public void setvX(int vX) {
        this.vX = vX;
    }

    public int getvY() {
        return vY;
    }

    public void setvY(int vY) {
        this.vY = vY;
    }

    /**
     * ���ö���ͼƬ�Ĵ�С
     * @param vWidth �������޶���ͼ��Ŀ�
     * @param vHeight �������޶���ͼ��ĸ�
     */
    public void setSize(int vWidth,int vHeight) {
        this.vWidth = Math.abs(vWidth);
        this.vHeight = Math.abs(vHeight);
    }

    /**
     * ���õ��޶����ĵ��β���ʱ��
     * @param animDuration ���޶����ĵ��β���ʱ��
     */
    public void setAnimDuration(int animDuration) {
        if(animDuration < 0){
            return;
        }
        AnimDuration = animDuration;
    }

    /**
     * ���õ��޶����Ļ�߶�
     * @param animPathHeight ���޶����Ļ�߶�
     */
    public void setAnimPathHeight(int animPathHeight) {
        AnimPathHeight = Math.abs(animPathHeight);
    }

    /**
     * ���õ��޶����ĺ�����Χ
     * @param animHorizontalScope ���޶����ĺ�����Χ
     */
    public void setAnimHorizontalScope(int animHorizontalScope) {
        AnimHorizontalScope = animHorizontalScope;
    }

    /**
     * ���ö������ֵ�����
     * @param animCount �������ֵ�����
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
     * ���ö������ֵļ��
     * @param animDelay �������ֵļ��
     */
    public void setAnimDelay(int animDelay) {
        if(animDelay < 0){
            return;
        }
        AnimDelay = animDelay;
    }

    /**
     * ����Ƿ�ִ�����Ŷ���
     * @return �Ƿ�ִ�����Ŷ���
     */
    public boolean isScaleAble() {
        return ScaleAble;
    }

    /**
     * �����Ƿ�ִ�����Ŷ���
     * @param scaleAble �Ƿ�ִ�����Ŷ���
     */
    public void setScaleAble(boolean scaleAble) {
        ScaleAble = scaleAble;
    }

    /**
     * ����Ƿ�ִ��͸������
     * @return �Ƿ�ִ��͸������
     */
    public boolean isAlphaAble() {
        return AlphaAble;
    }

    /**
     * �����Ƿ�ִ��͸������
     * @param alphaAble �Ƿ�ִ��͸������
     */
    public void setAlphaAble(boolean alphaAble) {
        AlphaAble = alphaAble;
    }

    /**
     * �������Ŷ���������ֵ
     * @param scaleScope ���Ŷ���������ֵ 1����1+scaleScope��
     */
    public void setScaleScope(float scaleScope) {
        if(scaleScope < -1){
            scaleScope = -1;
        }
        this.scaleScope = scaleScope;
    }

    /**
     * ����͸���ȶ�������ʼ͸����
     * @param startAlpha ͸���ȶ�������ʼ͸����
     */
    public void setStartAlpha(float startAlpha) {
        this.startAlpha = startAlpha;
    }
    /**
     * ����͸���ȶ�������ֹ͸����
     * @param endAlpha ͸���ȶ�������ֹ͸����
     */
    public void setEndAlpha(float endAlpha) {
        this.endAlpha = endAlpha;
    }

    /**
     * ͨ�����Զ��� ʵ��ͼƬ�����Ŷ���Ч��
     * @param target ��Ӷ���Ч����Ŀ��View
     * @param animDelay �����ļ��
     */
    private AnimatorSet getScaleAnimtor(View target,int animDelay){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1f, 1f+scaleScope);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1f, 1f+scaleScope);
        AnimatorSet scaleAnimtor = new AnimatorSet();
        scaleAnimtor.setDuration(AnimDuration);
        scaleAnimtor.setStartDelay(animDelay);
        scaleAnimtor.setInterpolator(new LinearInterpolator());//���Ա仯
        scaleAnimtor.playTogether(scaleX,scaleY);
        scaleAnimtor.setTarget(target);
        return scaleAnimtor;
    }

    /**
     * ͨ�����Զ��� ʵ��ͼƬ��͸���ȱ仯�Ķ���Ч��
     * @param target ��Ӷ���Ч����Ŀ��View
     * @param animDelay �����ļ��
     */
    private AnimatorSet getAlphaAnimtor(View target,int animDelay){

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, startAlpha, endAlpha);
        AnimatorSet alphaAnimtor = new AnimatorSet();
        alphaAnimtor.setDuration(AnimDuration);
        alphaAnimtor.setStartDelay(animDelay);
        alphaAnimtor.setInterpolator(new LinearInterpolator());//���Ա仯
        alphaAnimtor.playTogether(alpha);
        alphaAnimtor.setTarget(target);
        return alphaAnimtor;
    }

    /**
     * ����������������remove��ӵĶ���View
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
            vRootViewGroup.removeView((target));
        }
    }

    /**
     * ���ŵ��޶���
     */
    public void play() {
        animatorSet = new AnimatorSet();
        ViewGroup.LayoutParams likelayoutParams = new ViewGroup.LayoutParams(vWidth, vHeight);
        animGenerator.setDuration(AnimDuration);
        animGenerator.setAnimPathHeight(AnimPathHeight);
        animGenerator.setHorizontalScope(AnimHorizontalScope);
        animGenerator.setStartLocation(vX,vY);
        for(int i = 0 ; i<AnimCount ; i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(drawableResourceIds[random.nextInt(drawableResourceIds.length)]);
            imageView.setVisibility(View.GONE);
            vRootViewGroup.addView(imageView, likelayoutParams);
            Animator set = animGenerator.animgenerate(imageView);
            set.addListener(new AnimEndListener(imageView));
            set.setStartDelay(AnimDelay*i);
            animatorSet.play(set);
            if(ScaleAble) animatorSet.play(getScaleAnimtor(imageView,AnimDelay*i));
            if(AlphaAble) animatorSet.play(getAlphaAnimtor(imageView,AnimDelay*i));
        }
        animatorSet.start();
    }

    /**
     * ȡ�����ŵ��޶���
     */
    public void cancel(){
        if(animatorSet != null && animatorSet.isRunning()){
            animatorSet.cancel();
        }
    }
}
