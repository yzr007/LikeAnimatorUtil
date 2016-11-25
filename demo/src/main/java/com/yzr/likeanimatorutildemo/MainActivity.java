package com.yzr.likeanimatorutildemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yzr.likeanimatorutil.LikeAnimatorManager;

/**
 * Created by Yangzr on 2016/11/21.
 */

public class MainActivity extends AppCompatActivity{
    TextView txt;
    LikeAnimatorManager likeAnimatorManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnim();
            }
        });
    }

    private void showAnim(){
        int[] imgs = {R.mipmap.heart_01,R.mipmap.heart_02,R.mipmap.heart_03,R.mipmap.heart_04,R.mipmap.heart_05};
        likeAnimatorManager = new LikeAnimatorManager(this,txt,imgs);
        //动画视图的大小和位置默认为target的大小和位置，可以通过setLocation()和setSize()自由配置
        likeAnimatorManager.setAnimDelay(50);//单个动画的间隔，默认200ms
        likeAnimatorManager.setAnimCount(50);//点赞动画的数量，默认20
        likeAnimatorManager.setAnimDuration(1000);//单个动画的Duration，默认2000ms
        likeAnimatorManager.setAnimPathHeight(1000);//动画轨迹的高度，默认600
        likeAnimatorManager.setAnimHorizontalScope(600);//动画轨迹的横向最大范围，默认200
        likeAnimatorManager.setAlphaAble(true);//是否开启透明度变化，默认false
        likeAnimatorManager.setStartAlpha(0.2f);//起始透明度
        likeAnimatorManager.setEndAlpha(1f);//结束透明度
        likeAnimatorManager.setScaleAble(true);//是否开启缩放，默认false
        likeAnimatorManager.setScaleScope(1.2f);//缩放动画的缩放范围：1-（1+scope），默认0.5,最小值 -1
        likeAnimatorManager.play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        likeAnimatorManager.cancel();
    }
}
