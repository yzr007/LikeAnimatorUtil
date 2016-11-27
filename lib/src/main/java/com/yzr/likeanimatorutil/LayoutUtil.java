package com.yzr.likeanimatorutil;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Yangzr on 2016/11/22.
 */

public class LayoutUtil {
    /**
     * ��ȡ������
     * @param context Activity��������
     * @return Activity�ĸ�����
     */
    public static ViewGroup getRootView(Context context) {
        return ((ViewGroup) ((Activity)context).getWindow().getDecorView());
    }

    /**
     * �Ƿ���ʾ״̬��
     * @param context Activity��������
     * @return �Ƿ���ʾ״̬��
     */
    public static boolean isShowStatus(Context context){
        WindowManager.LayoutParams attrs = ((Activity)context).getWindow().getAttributes();
        if((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN){
            return false;
        }else{
            return true;
        }
    }
    /**
     * ���״̬���ĸ߶�
     * @param context Activity��������
     * @return ״̬���ĸ߶�
     */
    public static int getStatusHeight(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getScreenX(View v){
        int location[] = new int[2];
        v.getLocationOnScreen(location);
        return location[0];
    }
    public static int getScreenY(View v){
        int location[] = new int[2];
        v.getLocationOnScreen(location);
        return location[1];
    }
}
