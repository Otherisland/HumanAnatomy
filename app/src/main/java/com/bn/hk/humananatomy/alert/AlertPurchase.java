package com.bn.hk.humananatomy.alert;//声明包

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Toast;

import com.bn.hk.humananatomy.activity.RegisterActivity;
import com.bn.hk.humananatomy.database.LoginService;
import com.bn.hk.humananatomy.thread.NetThread;
import com.bn.hk.humananatomy.util.SynchronizeUtil;

import java.net.URLEncoder;

import static com.bn.hk.humananatomy.constant.Constant.currentPurchased;
import static com.bn.hk.humananatomy.constant.Constant.isExist;
import static com.bn.hk.humananatomy.constant.Constant.showToast;
import static com.bn.hk.humananatomy.constant.Constant.unChapterArray;
import static com.bn.hk.humananatomy.constant.Constant.unlockArray;
import static com.bn.hk.humananatomy.constant.Constant.userName;
import static com.bn.hk.humananatomy.constant.Constant.userPurchased;
import static com.bn.hk.humananatomy.constant.Constant.userGlobal;

public class AlertPurchase
{
    Builder builder;
    String content;
    String title;
    String icon;
    Context context;//上下文对象
    public AlertPurchase(Context context)
    {
        this.context=context;
        builder=new Builder(context);
    }

    public void setAlertContext(int index)
    {
        switch (index)
        {
            case 0:
                title="解锁章节";
                content="是否￥5购买并解锁该章节？";
                builder.setTitle(title);

                builder.setMessage(content);
                //添加一个确定按钮，打印log发现是 i=-1
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了确定按钮");
                        Log.d("Main","用户点击了确定按钮"+i);
                        synchronized (isExist)
                        {
                            if(isExist)
                            {
                                AlertPurchase apu=new AlertPurchase(context);
                                apu.setAlertContext(1);
                            }else {
                                Toast mToast = Toast.makeText(context, " ", Toast.LENGTH_SHORT);
                                mToast.setText("该资源正在开发中，敬请期待！");
                                mToast.show();
                            }
                        }

                    }
                });
                builder.setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了取消按钮");
                        Log.d("Main","用户点击了取消按钮"+i);
                    }
                });
                builder.setNeutralButton("返回", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了返回");
                        Log.d("Main","用户点击了返回"+i);
                    }
                });
                break;
            case 1:
                int choicenIndex=0;
                title="选择支付方式";
//                content="是否￥5购买并解锁该章节？";
                builder.setTitle(title);
                builder.setMessage(content);
                builder.setSingleChoiceItems(new String[]{"支付宝", "微信"}, 0, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        choicenIndex=which;
                    }
                });
                //添加一个确定按钮，打印log发现是 i=-1
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了确定按钮");
                        Log.d("Main","用户点击了确定按钮"+i);


                        if(isExist)
                        {
                            System.out.println(currentPurchased);
                            int charIndex=unChapterArray.get(currentPurchased);
                            unlockArray[charIndex]='1';
                            System.out.println("更新后的解锁字符串"+new String(unlockArray));
                        }
                        uploadUnlock();
                        SynchronizeUtil synchronizeUtil=new SynchronizeUtil();
                        synchronizeUtil.updateMaintxt();



                        AlertPurchase ap=new AlertPurchase(context);
                        ap.setAlertContext(2);

                    }
                });
                builder.setNeutralButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了取消");
                        Log.d("Main","用户点击了取消"+i);
                    }
                });
                break;
            case 2:
                title="购买成功！";
                content="您已购买成功，点击确定进入资源下载。";

                builder.setTitle(title);

                builder.setMessage(content);



                //添加一个确定按钮，打印log发现是 i=-1
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了确定按钮");
                        Log.d("Main","用户点击了确定按钮"+i);

                        showToast("当前user：   "+userName,context);
                        ProgressPurchase pp=new ProgressPurchase(context);
                        new NetThread(pp).start();
                    }
                });
                builder.setNeutralButton("返回", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了返回");
                        Log.d("Main","用户点击了返回"+i);
                    }
                });break;
            case 4:
                title="下载资源！";
                content="点击确定进入资源下载。";
                builder.setTitle(title);

                builder.setMessage(content);
                //添加一个确定按钮，打印log发现是 i=-1
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了确定按钮");
                        Log.d("Main","用户点击了确定按钮"+i);

                        ProgressPurchase pp=new ProgressPurchase(context);
                        new NetThread(pp).start();
                    }
                });
                builder.setNeutralButton("返回", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了返回");
                        Log.d("Main","用户点击了返回"+i);
                    }
                });break;
            case 5:
                title="感谢使用";
                content="如有问题，请加入QQ群776096278，为您解答。";
                builder.setTitle(title);

                builder.setMessage(content);
                //添加一个确定按钮，打印log发现是 i=-1
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了确定按钮");
                        Log.d("Main","用户点击了确定按钮"+i);
                    }
                });
                builder.setNeutralButton("返回", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Main","用户点击了返回");
                        Log.d("Main","用户点击了返回"+i);
                    }
                });break;

        }
        builder.show();
    }
    public void uploadUnlock()
    {
        new Thread()
        {
            public void run() {

                userPurchased=new String(unlockArray);
                ///* 更新解锁信息
                // /** 用户全局序号
                String userGlobalid=userGlobal ;
                // /** 章节解锁信息
                String purchasedChapter=userPurchased;
                String action="updatePurchased";
                String data = "action="+ URLEncoder.encode(action)
                        +"&userGlobalid="+URLEncoder.encode(userGlobalid)
                        +"&purchasedChapter="+URLEncoder.encode(purchasedChapter);
                String response= LoginService.postParameter(data);   //获取解锁信息
                System.out.println(data+"     "+response);
            }
        }.start();
    }
}