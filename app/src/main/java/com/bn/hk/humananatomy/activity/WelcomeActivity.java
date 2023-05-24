package com.bn.hk.humananatomy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.util.SharedPreferencesManage;
import com.bn.hk.humananatomy.util.ZipUtil;
import static com.bn.hk.humananatomy.constant.Constant.userName;
import static com.bn.hk.humananatomy.util.PermissionUtils.verifyStoragePermissions;
import static com.bn.hk.humananatomy.util.Test.CopyAssets;

public class WelcomeActivity extends AppCompatActivity {

    public static String RootPath;
    private SharedPreferencesManage spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){ //隐藏标题栏
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RootPath=this.getExternalFilesDir(null)+"";
        verifyStoragePermissions(this);//询问权限问题
        spref =new SharedPreferencesManage(this);
        userName=spref.getString("user_name","");//设置头像下面名称
        if(userName.equals(""))
        {
            userName="请登录";
        }
    }
    public void jump()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                //自定义activity出入方式
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
                finish();
            }
        }, 2200);
    }
    public  void record() {

        String rootPath = RootPath + "/";
        System.out.println("下载及解压到目标文件夹  ：：" + rootPath);
        String zipName = "HumanAnatomy.zip";
        CopyAssets(this, zipName, rootPath + zipName);
        try
        {
            ZipUtil.unZipFiles(rootPath+zipName,rootPath,2);
        }catch(Exception e)
        {
            System.out.println("解压assets出错");
            e.printStackTrace();
        }
        this.jump();
    }

}
