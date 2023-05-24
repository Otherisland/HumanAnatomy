package com.bn.hk.humananatomy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.database.LoginService;
import com.bn.hk.humananatomy.object.UserAuths;
import com.bn.hk.humananatomy.util.SynchronizeUtil;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import static com.bn.hk.humananatomy.constant.Constant.updateCategory;
import static com.bn.hk.humananatomy.constant.Constant.userGlobal;
import static com.bn.hk.humananatomy.constant.Constant.showToast;
import static com.bn.hk.humananatomy.constant.Constant.isLogin;
public class RegisterActivity extends AppCompatActivity {

    private boolean mbDisplayFlg = false;
    private ImageView menu_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) { //隐藏标题栏
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_register);
        EditText user_name=findViewById(R.id.title_user_name);
        EditText user_password=findViewById(R.id.title_user_password);
        Button mBtnPasswordR=findViewById(R.id.passwdr_eye_btn);//是否显示密码
        Button register_button=findViewById(R.id.register_btn);
        menu_left=findViewById(R.id.menu_left);

        menu_left.setOnClickListener((v)->{
            Intent intent=new Intent( RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        });
        //眼睛监听
        mBtnPasswordR.setOnClickListener(
                (v)->{
                    if(!mbDisplayFlg)
                    {
                        user_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else
                    {
                        user_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    mbDisplayFlg=!mbDisplayFlg;
                    user_password.postInvalidate();
                    user_password.setSelection(user_password.getText().length());
                }
        );
        register_button.setOnClickListener(
                (v)->{

                    UserAuths userAuths=new UserAuths();
                    userAuths.setUserId(user_name.getText().toString());//用户名
                    userAuths.setUserCredential(user_password.getText().toString());//密码   登陆凭证
                    userAuths.setUserEmail("1589451265@qq.com");//邮箱
                    userAuths.setUserPhone("1008611");//手机号
                    userAuths.setUserIdentityType("手机");//登录类型
                    Constant.userName=user_name.getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ///** 用户全局序号
                            int userGlobalid=0;
                            // /** 用户账号
                            String userId=user_name.getText().toString() ;
                            ///** 登录类型
                            String userIdentityType="phone" ;
                            ///** 手机号
                            String userPhone="155555555" ;
                            ///** 邮箱
                            String userEmail="33@qq.com" ;
                            ///** 登陆凭证
                            String userCredential=user_password.getText().toString();
                            String action="getCount";
                            String data = "action="+ URLEncoder.encode(action);
                            String  response ="";
                            response= LoginService.postParameter(data);
                            int count=Integer.parseInt(response);
                            if(count!=-1)
                            {
                                userGlobalid=count+1;
                                userGlobal=userGlobalid+"";
                            }
                            action="register";
                            data = "action="+URLEncoder.encode(action)
                                    +"&userGlobalid="+URLEncoder.encode(userGlobalid+"")
                                    +"&userId="+URLEncoder.encode(userId)
                                    +"&userIdentityType="+URLEncoder.encode(userIdentityType)
                                    +"&userPhone="+URLEncoder.encode(userPhone)
                                    +"&userEmail="+URLEncoder.encode(userEmail)
                                    +"&userCredential="+URLEncoder.encode(userCredential);

                            response= LoginService.postParameter(data);
                            if(response.equals("true"))//
                            {
                                isLogin =true;
                                //showToast("注册成功",RegisterActivity.this);


                                String purchasedChapter="01000000000000000000" ; //初始
                                action="initPurchased";
                                data = "action="+URLEncoder.encode(action)
                                        +"&userGlobalid="+URLEncoder.encode(userGlobalid+"")
                                        +"&purchasedChapter="+URLEncoder.encode(purchasedChapter);
                                response= LoginService.postParameter(data);
                                System.out.println(response);

                                char unlock[]=purchasedChapter.toCharArray();

                                SynchronizeUtil su=new SynchronizeUtil();  //更新解锁信息
                                su.updateLockInfo(unlock);
                                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                                java.util.Date date = new java.util.Date();
                                System.out.println(sp.format(date));
                                java.sql.Date dateSql = new java.sql.Date(date.getTime());

                                // /** 昵称
                                String userNickname="随心" ;
                                // /** 性别
                                String userSex ="女";
                                // /** 身份信息
                                String userIdentity="学生" ;
                                // /** 注册时间
                                String userRegistrationDate=sp.format(dateSql) ;

                                action="initUserBasic";//用户信息
                                data = "action="+URLEncoder.encode(action)
                                        +"&userGlobalid="+URLEncoder.encode(userGlobalid+"")
                                        +"&userNickname="+URLEncoder.encode(userNickname)
                                        +"&userSex="+URLEncoder.encode(userSex)
                                        +"&userIdentity="+URLEncoder.encode(userIdentity)
                                        +"&userRegistrationDate="+URLEncoder.encode(userRegistrationDate);
                                response= LoginService.postParameter(data);
                                System.out.println(response);

                                showToast("注册成功",RegisterActivity.this);
                                updateCategory();//重新加载界面  刷新解锁信息
                                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                showToast("注册失败",RegisterActivity.this);
                            }
                        }
                    }).start();
                }
        );
    }
}
