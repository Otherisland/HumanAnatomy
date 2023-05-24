package com.bn.hk.humananatomy.thread;

import com.bn.hk.humananatomy.activity.RegisterActivity;
import com.bn.hk.humananatomy.alert.ProgressPurchase;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.util.Test;
import static com.bn.hk.humananatomy.constant.Constant.baseSDPath;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.file_path;
import static com.bn.hk.humananatomy.constant.Constant.handler;
import static com.bn.hk.humananatomy.constant.Constant.isDownload;
import static com.bn.hk.humananatomy.constant.Constant.showToast;
import static com.bn.hk.humananatomy.constant.Constant.texturePath;

public class NetThread extends Thread {

    ProgressPurchase pp;
    Test test;

    public NetThread(ProgressPurchase pp)
    {
        this.pp=pp;
        test=new Test(pp);
    }
    public void run()
    {
        if(isDownload=='1')//要加载的模型已经存在sd卡中 直接加载即可
        {
            handler.post(new Runnable() {   //设置进度条alert  投给主线程处理
                @Override
                public void run() {
                    pp.setProgressContext(0);
                }
            });

            for(int i=0;i<=100;i++)
            {
                Constant.PROGRESS_RATIO=i;
                pp.setProgressRatio();
                try{Thread.sleep(50);}catch (Exception e){
                    e.printStackTrace();
                }
            }
            test.jumpActivity();
        }else//未下载未解压 则进行下载解压
        {
            showToast("下载 ：：："+isDownload,pp.context);
            System.out.println("该文件不存在，将进行下载解压");
            test.DownAndReadFile();
        }
    }

}
