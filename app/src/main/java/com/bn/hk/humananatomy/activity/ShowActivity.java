package com.bn.hk.humananatomy.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.bn.hk.humananatomy.auto.ScreenScaleUtil;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.view.ShowView;

import static com.bn.hk.humananatomy.constant.Constant.updateCategory;

public class ShowActivity extends Activity {

	private ShowView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);         
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置为横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        Constant.SCREEN_WIDTH = outMetrics.widthPixels;                     //获取屏幕宽度
        Constant.SCREEN_HEIGHT= outMetrics.heightPixels;                    //获取屏幕宽度高度
        Constant.ssr= ScreenScaleUtil.calScale                              //计算屏幕自适应
                (Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);

                //初始化GLSurfaceView
        mGLSurfaceView = new ShowView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控

        updateCategory();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
        Constant.loadPosition=0;
        Constant.tempPosition=0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }    
}



