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
        likeAnimatorManager.play();
    }
}
