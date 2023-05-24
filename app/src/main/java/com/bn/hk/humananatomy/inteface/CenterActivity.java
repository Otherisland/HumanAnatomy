package com.bn.hk.humananatomy.inteface;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.activity.MainActivity;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.database.LoginService;

import java.net.URLEncoder;
import java.util.StringTokenizer;

import static com.bn.hk.humananatomy.constant.Constant.showToast;
import static com.bn.hk.humananatomy.constant.Constant.userGlobal;

public class CenterActivity extends AppCompatActivity {

    private TextView uc_nickname;
    private TextView uc_usersex;
    private TextView uc_identify;
    private TextView uc_registerDate;
    private ImageView button_return;
    private Button button_csave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter);
        initUserCenter();
        //个人中心监听
        String action="getUserBasic";
        String data = "action="+URLEncoder.encode(action)
                +"&userGlobalid="+URLEncoder.encode(userGlobal);
        new Thread() {
            public void run() {

                String response = LoginService.postParameter(data);
                System.out.println(response);
                Constant.userInfo =response;

                System.out.println(Constant.userInfo);
                StringTokenizer st=new StringTokenizer(Constant.userInfo,",");

                uc_nickname.setText((String) st.nextElement());
                // /** 性别
                uc_usersex.setText((String) st.nextElement());
                // /** 身份信息
                uc_identify.setText((String) st.nextElement());
                uc_registerDate.setText((String) st.nextElement());


            }
        }.start();

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( CenterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        button_csave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // /** 昵称
                        String userNickname = (String) uc_nickname.getText().toString();
                        // /** 性别
                        String userSex =  (String) uc_usersex.getText().toString();
                        // /** 身份信息
                        String userIdentity = (String) uc_identify.getText().toString();


                        String action = "updateUserBasic";
                        String data = "action=" + URLEncoder.encode(action)
                                + "&userGlobalid=" + URLEncoder.encode(userGlobal)
                                + "&userNickname=" + URLEncoder.encode(userNickname)
                                + "&userSex=" + URLEncoder.encode(userSex)
                                + "&userIdentity=" + URLEncoder.encode(userIdentity);

                        new Thread() {
                            public void run() {

                                String response = LoginService.postParameter(data);
                                System.out.println(response);
                                if(response.equals("true"))
                                {
                                    showToast("修改成功!", CenterActivity.this);
                                    Intent intent=new Intent( CenterActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }
                            }
                        }.start();
                    }
                }
        );
    }
    public void initUserCenter()
    {
        button_return =findViewById(R.id.menuc_return);
        uc_nickname=findViewById(R.id.user_name_center);
        uc_usersex=findViewById(R.id.user_sex);
        uc_identify=findViewById(R.id.user_identify);
        uc_registerDate=findViewById(R.id.user_registerDate);
        button_csave =findViewById(R.id.button_save);

    }

}
