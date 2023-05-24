package com.bn.hk.humananatomy.inteface;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.activity.ShowActivity;
import com.bn.hk.humananatomy.alert.AlertPurchase;
import com.bn.hk.humananatomy.texture.TexManager;
import com.bn.hk.humananatomy.util.Test;

import java.util.ArrayList;

import static com.bn.hk.humananatomy.constant.Constant.baseSDPath;
import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_D;
import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_R;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.file_path;
import static com.bn.hk.humananatomy.constant.Constant.isDownload;
import static com.bn.hk.humananatomy.constant.Constant.isExist;
import static com.bn.hk.humananatomy.constant.Constant.isLogin;
import static com.bn.hk.humananatomy.constant.Constant.itemIndex;
import static com.bn.hk.humananatomy.constant.Constant.lock;
import static com.bn.hk.humananatomy.constant.Constant.menu_State;
import static com.bn.hk.humananatomy.constant.Constant.screenHeight;
import static com.bn.hk.humananatomy.constant.Constant.screenHeightC;
import static com.bn.hk.humananatomy.constant.Constant.screenWidth;
import static com.bn.hk.humananatomy.constant.Constant.screenWidthC;
import static com.bn.hk.humananatomy.constant.Constant.sever_path_basic;
import static com.bn.hk.humananatomy.constant.Constant.texturePath;
import static com.bn.hk.humananatomy.constant.Constant.server_p;
import static com.bn.hk.humananatomy.constant.Constant.currentPurchased;
import static com.bn.hk.humananatomy.constant.Constant.currentModel;
public class ItemType extends FrameLayout {
    private ArrayList<Character> userLock=new ArrayList<>(); //用户解锁信息
    AlertPurchase ap;
    private static final int DELAY = 3000;  //播放图片间隔时间
    private Context context;  //上下文，运行环境

    private int currentIndex;   //与baseAdapter条目的index绑定上


    private int itemView_ID[][]={     //88为默认 敬请期待开发系统id
            {88,1,2},
            {3,88,88},
            {88,88},
            {88},
            {88,88},
            {88}
    };


    public String iconN ="";

    private ArrayList<Character> isLock=new ArrayList<>();
    private ArrayList<String> iconGroupA=new ArrayList<>();
    private ArrayList<String> iconNameA=new ArrayList<>();
    private String systemName="";
    private ArrayList<Category> categoryGroup=new ArrayList();
    private String categoryName="";
    private ArrayList<Character> categoryChLock=new ArrayList<>();
    char systemLock;

    public ItemType(Context context) {
        this(context, null);
    }

    public ItemType(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ItemType(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();   //构造方法中初始化View
    }

    private void initView() {   //初始化View
        synchronized (lock)
        {
            currentIndex=itemIndex;   //防止baseAdapter刷帧造成的不同步
        }
        if(categoryIndex==0)
        {
            categoryGroup=categoryGroup_D;
        }else {
            categoryGroup=categoryGroup_R;
        }

        if(categoryGroup.get(currentIndex)!=null)
        {
            Category category=new Category();
            category=categoryGroup.get(currentIndex);
            for(int i=0;i<category.categoryChildName.size();i++)
            {
                userLock.add(category.categoryChildName.get(i).charAt(0));  //用户解锁信息
            }

            categoryChLock=userLock;
            categoryName=category.categoryName;
            systemLock=category.categoryLock;
           //System.out.println("currentIndex    :"+currentIndex);
           systemName=category.categoryPic;
            if(category.categoryChildName!=null)
            {
                iconGroupA=category.categoryChildPic;
                iconNameA=category.categoryChildName;
                isLock=category.categoryChildLock;
            }else{
                iconGroupA.clear();
                iconNameA.clear();
            }
        }

        initContent();//初始化界面内容
    }

    private void initContent() {
        double widthRatioR=screenWidth/screenWidthC;
        double heightRatioR=screenHeight/screenHeightC;
        String texPath_logo=systemName+".png";


        //对应界面的xml,找到其中的圆点，图片id
        View view = LayoutInflater.from(context).inflate(
                R.layout.item, this, true); //必须有


        ImageView typeImage=new ImageView(context);
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams((int)( 60.0*widthRatioR),(int)(60.0*heightRatioR));
        params1.leftMargin =params1.topMargin=20;
        LinearLayout lin1Layout=view.findViewById(R.id.lin1);
        lin1Layout.addView(typeImage,params1);
        typeImage.setImageBitmap(TexManager.getTex(texPath_logo));


        TextView tType=new TextView(context);
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.leftMargin=20;

        params2.topMargin=20;
        lin1Layout.addView(tType,params2);
        tType.setTextSize(20);
        tType.setText(categoryName);
        tType.setTextColor(Color.BLACK);




        ImageView icon11 = new ImageView(context);   //初始化图标对象
        RelativeLayout.LayoutParams paramspp = new RelativeLayout.LayoutParams(
                (int)(200.0*widthRatioR),
                (int)(200.0*heightRatioR));   //用代码设置LinearLayout格式 LayoutParams
        paramspp.bottomMargin= paramspp.rightMargin = 20;   //代码设置LinerLayout左右距离（格式）
        paramspp.leftMargin =20;

        RelativeLayout relativeLayoutm = (RelativeLayout) view.findViewById(R.id.rela); //对应界面的圆点LInerLayout
        relativeLayoutm.addView(icon11, paramspp); //将图片和（图片在LinearLayout中的）格式加载进界面LinerLayout中

        String texPath1="expect.png";
        if(systemLock=='0')
        {
            icon11.setImageBitmap(TexManager.getTex(texPath1));
        }
    if(iconNameA!=null)
    {
        for(int i=0;i<iconNameA.size();i++)
        {
            ImageView icon1 = new ImageView(context);   //初始化图标对象
            ImageView icon_Dark=new ImageView(context);
            ImageView icon_Expect=new ImageView(context);

            RelativeLayout.LayoutParams paramsp = new RelativeLayout.LayoutParams(
                    (int)(200.0*widthRatioR),
                    (int)(200.0*heightRatioR));   //用代码设置LinearLayout格式 LayoutParams
            paramsp.bottomMargin= paramsp.rightMargin = 20;   //代码设置LinerLayout左右距离（格式）
            paramsp.leftMargin =(int) (20.0*widthRatioR+i*240*widthRatioR);

            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rela); //对应界面的圆点LInerLayout

            RelativeLayout.LayoutParams paramspd = new RelativeLayout.LayoutParams(
                    (int)(160.0*widthRatioR),
                    (int)(160.0*heightRatioR));   //用代码设置LinearLayout格式 LayoutParams
            paramspd.bottomMargin= paramspd.rightMargin = 20;   //代码设置LinerLayout左右距离（格式）
            paramspd.leftMargin =(int) (40.0*widthRatioR+i*240*widthRatioR);

            RelativeLayout relativeLayoutd = (RelativeLayout) view.findViewById(R.id.rela); //对应界面的圆点LInerLayout
            relativeLayout.addView(icon1, paramsp); //将图片和（图片在LinearLayout中的）格式加载进界面LinerLayout中
            relativeLayoutd.addView(icon_Dark, paramspd);
            relativeLayout.addView(icon_Expect,paramsp);
            String texPath=iconGroupA.get(i)+".png";
            String texPathD="dark.png";
            //String texPathD="mm.png";

            if(!TexManager.texName.contains(texPath))
            {
                TexManager.loadSingleTexture(texPath);

            }

            icon1.setImageBitmap(TexManager.getTex(texPath));
            if(categoryChLock.get(i)=='0')
            {
                icon_Dark.setImageBitmap(TexManager.getTex(texPathD));
            }

            int ii=i;
            icon1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categoryChLock.get(ii)=='0'&& isLogin ==false)
                {
                    Toast.makeText(context,"请先登录！",Toast.LENGTH_SHORT).show();
                    return;
                }


                System.out.println("menu_State="+menu_State);
                if(menu_State==false)
                {
                    iconN =iconNameA.get(ii);
                    server_p=sever_path_basic[categoryIndex]+systemName+"/"+iconGroupA.get(ii)+".zip";
                    isDownload=isLock.get(ii);
                    //System.out.println("常量路径：：："+server_p);

                    file_path=iconGroupA.get(ii);

                    if(isDownload=='1'&&categoryChLock.get(ii)=='1')
                    {

                        texturePath=baseSDPath[categoryIndex]+file_path+"/texture/"+file_path+".pkm";
                        //System.out.println("纹理的地址：                             "+texturePath);
                        jumpActivity(itemView_ID[currentIndex][ii]);   //跳转到3D界面
                    }else {


                        texturePath=baseSDPath[categoryIndex]+file_path+"/texture/"+file_path+".pkm";
                        currentModel =file_path;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Test test=new Test();
                                synchronized (isExist){
                                    isExist=test.seeMain(context);
                                }
                            }
                        }).start();
                        ap=new AlertPurchase(context);
                        if(userLock.get(ii)=='1')
                        { ap.setAlertContext(4);}
                        else {
                            currentPurchased=iconGroupA.get(ii);
                            ap.setAlertContext(0);
                        }
                    }

                }
            }
        });
        }
    }

    }
    public void jumpActivity(int viewID)
    {
        //System.out.println("现在的ViewID是：    "+viewID);
        Intent intent = new Intent(context,ShowActivity.class);
        intent.putExtra("viewId",""+viewID);
        context.startActivity(intent);

    }
}