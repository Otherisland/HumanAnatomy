package com.bn.hk.humananatomy.activity;//声明包

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.database.LoginService;
import com.bn.hk.humananatomy.util.SharedPreferencesManage;
import com.bn.hk.humananatomy.util.SynchronizeUtil;

import java.net.URLEncoder;

import static com.bn.hk.humananatomy.constant.Constant.showToast;
import static com.bn.hk.humananatomy.constant.Constant.updateCategory;
import static com.bn.hk.humananatomy.constant.Constant.userGlobal;
import static com.bn.hk.humananatomy.constant.Constant.userName;
import static com.bn.hk.humananatomy.constant.Constant.ppCircle;
import static com.bn.hk.humananatomy.constant.Constant.isLogin;
public class LoginActivity extends AppCompatActivity {

    public static EditText mEtUser;
    public static EditText mEtPassword;
    private SharedPreferencesManage spref;
    SharedPreferences.Editor editor;
    private CheckBox rember_pwd;
    private boolean mbDisplayFlg = false;
    String action;
    String data;
    String  response;
    Button mBtnPassword;
    Button mBtnLogin;
    TextView registerTextView;
    boolean isRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) { //隐藏标题栏
            getSupportActionBar().hide();
        }
        setContentView(R.layout.complex_activity_main);
        init();//初始化
        isRemember();//判断是否选了记住密码
        //是否显示密码监听
        eyeOnClick(mBtnPassword);
        //登录按钮监听
        loginOnClick();
        //注册
        registerTextView.setOnClickListener(
                (v)->{
                    Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
        );

    }
    public void init()
    {
        mBtnPassword=findViewById(R.id.passwd_eye_btn);//是否显示密码
        mBtnLogin=findViewById(R.id.login_btn);//登录按钮
        registerTextView=findViewById(R.id.register_textView);//注册
        mEtUser=findViewById(R.id.login_input_username);//用户EditText
        mEtPassword=findViewById(R.id.login_input_password);//密码EditText
        // 记住密码
        rember_pwd=findViewById(R.id.remember_pwd);
        spref =new SharedPreferencesManage(this);
        isRemember= spref.getBoolean("remember_password",false);
    }
    public void isRemember()
    {
        if(isRemember)
        {
            String user_name= spref.getString("user_name","");
            String user_password= spref.getString("user_password","");
            mEtUser.setText(user_name);
            mEtPassword.setText(user_password);
            rember_pwd.setChecked(true);
        }
    }
    public void eyeOnClick(Button mBtnPassword)
    {
        //眼睛监听
        mBtnPassword.setOnClickListener(
                (v)->{
                    System.out.println("      mbDisplayFlg=     "+mbDisplayFlg);
                    if(!mbDisplayFlg)
                    {
                        mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else
                    {
                        mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    mbDisplayFlg=!mbDisplayFlg;
                    mEtPassword.postInvalidate();
                    mEtPassword.setSelection(mEtPassword.getText().length());
                }
        );
    }
    public void loginOnClick()
    {
        mBtnLogin.setOnClickListener(
                (v)->{
                    Toast.makeText(this,"登陆中，请稍候...", Toast.LENGTH_SHORT).show();
                    String user_name=mEtUser.getText().toString();
                    String user_password=mEtPassword.getText().toString();
                    editor=spref.edit();
                    spref.setEditor(editor);
                    if(rember_pwd.isChecked()){
                        spref.setBoolean("remember_password",true);
                        spref.setString("user_name",user_name);
                        spref.setString("user_password",user_password);
                    }else{
                        spref.clear();
                    }
                    spref.commit();
                    login(user_name,user_password);//线程登录方法
                    ppCircle=false;
                }
        );
    }
    public void login(String user_name,String user_password)
    {
        new Thread()
        {
            public void run() {

                action="login";
                data = "action="+URLEncoder.encode(action)+"&username="+URLEncoder.encode(user_name)+"&password="
                        + URLEncoder.encode(user_password);
                response= LoginService.postParameter(data);
                if(response.equals("true"))
                {
                    action="getGlobalID";
                    data = "action="+URLEncoder.encode(action)
                            +"&userId="+URLEncoder.encode(user_name);
                    response= LoginService.postParameter(data);
                    userGlobal=response;
                    System.out.println("用户全局编号为"+userGlobal);   //获取global id

                    action="getPurchased";
                    data = "action="+URLEncoder.encode(action)
                            +"&userGlobalid="+URLEncoder.encode(userGlobal);
                    response= LoginService.postParameter(data);   //获取解锁信息
                    System.out.println(userGlobal+"解锁信息为"+response);

                    char unlock[]=response.toCharArray();
                    SynchronizeUtil su=new SynchronizeUtil(userGlobal);  //更新解锁信息
                    su.updateLockInfo(unlock);
                    isLogin =true;
                    userName =user_name;//更新头像下面的显示
                    //showToast("登录成功",LoginActivity.this);
                    updateCategory();//重新加载界面  刷新解锁信息
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    showToast("用户名或密码错误",LoginActivity.this);
                }

            }
        }.start();
    }
    public void request()
    {
        action="getGlobalID";
        data = "action="+URLEncoder.encode(action)
                +"&userId="+URLEncoder.encode(userName);
        response= LoginService.postParameter(data);
        userGlobal=response;
        System.out.println(userGlobal);   //获取global id

        action="getPurchased";
        data = "action="+URLEncoder.encode(action)
                +"&userGlobalid="+URLEncoder.encode(userGlobal);
        response= LoginService.postParameter(data);   //获取解锁信息
    }

}
