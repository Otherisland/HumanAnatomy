package com.bn.hk.humananatomy.alert;//声明包

import android.app.ProgressDialog;
import android.content.Context;

import com.bn.hk.humananatomy.constant.Constant;

import static com.bn.hk.humananatomy.constant.Constant.ppCircle;


public class ProgressPurchase
{
    public ProgressDialog builder ;
    public String content;
    public String title;
    public Context context;

    public ProgressPurchase(Context context)
    {
        this.context=context;
        builder=new ProgressDialog(context);
    }

    public void setProgressContext(int index)
    {
        switch (index)
        {
            case 0:
                title="下载章节";
                content="下载章节内容中，请稍候";
                builder.setTitle(title);
                builder.setMessage(content);
                builder.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                builder.setIndeterminate(false);
                builder.setProgress(10);
                break;
            case 1:
                title="登录成功！";
                content="正在同步解锁信息，请稍候...";
                builder.setTitle(title);
                builder.setMessage(content);
                builder.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                builder.setIndeterminate(true);
                ppCircle=true;
                intentProgress();
                break;
        }
        builder.show();
    }

    public void setProgressRatio()
    {
        builder.setProgress((int) Constant.PROGRESS_RATIO);
    }
    public void intentProgress()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(ppCircle)
                {
                }
                builder.cancel();
            }
        }).start();
    }

}
