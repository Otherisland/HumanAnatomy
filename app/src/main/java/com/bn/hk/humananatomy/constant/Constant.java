package com.bn.hk.humananatomy.constant;

import android.content.Context;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bn.hk.humananatomy.R;
import com.bn.hk.humananatomy.activity.WelcomeActivity;
import com.bn.hk.humananatomy.auto.ScreenScaleResult;
import com.bn.hk.humananatomy.inteface.Category;
import com.bn.hk.humananatomy.object.TouchableObject;
import com.bn.hk.humananatomy.texture.TexManager;
import com.bn.hk.humananatomy.util.DynamicInterfaceUtil;
import java.util.ArrayList;
import java.util.HashMap;


public class Constant {

    public static String severPath[]={
            "http://192.168.43.135:8080/anatomic/",
    };
    //140.143.14.37
    public static int bubbleTextureId;           //气泡纹理ID
    public static int nameX;                    //名称绘制x坐标
    public static int nameY;                    //名称绘制y坐标
    public static int nameWidth;                //名称纹理图宽度
    public static int nameHeight;               //名称纹理图高度
    public static int bubbleX;                  //气泡绘制x坐标
    public static int bubbleY;                  //气泡绘制y坐标
    public static int bubbleWidth;              //气泡宽度
    public static int bubbleHeight;             //气泡高度
    public static final int HIDE_NOW_X=17;      //隐藏选中物体x方向位置
    public static final int HIDE_NOW_Y=1827;    //隐藏选中物体y方向位置
    public static final int HYALINE_NOW_X=226;  //透明选中物体x方向位置
    public static final int HYALINE_NOW_Y=1827; //透明选中物体y方向位置
    public static final int HIDE_OTHER_X=650;   //隐藏其他物体x方向位置
    public static final int HIDE_OTHER_Y=1827;     //隐藏其他物体y方向位置
    public static final int HYALINE_OTHER_X=438;    //透明其他物体x方向位置
    public static final int HYALINE_OTHER_Y=1827;   //透明其他物体y方向位置
    public static String baseSDPath[]={             //数据包下载完成存放位置
            WelcomeActivity.RootPath+"/HumanAnatomy/data/descriptive_anatomy/",
            WelcomeActivity.RootPath+"/HumanAnatomy/data/regional_anatomy/",
    };
    public static int itemIndex=0;  //条项索引
    public static Object lock=new Object();  //itemIndex的锁
    public static int categoryIndex=0;  //目录索引 系统解剖0，局部解剖1
    public static ArrayList<Category> categoryGroup_D =new ArrayList<>(); //系统解剖目录信息
    public static ArrayList<Category> categoryGroup_R =new ArrayList<>(); //局部解剖目录信息
    public static ArrayList<Category> categorySever =new ArrayList<>(); //服务器配置信息
    public static String currentModel ="";  //目录索引 系统解剖0，局部解剖1
    public static int[] categoryItemCount={0,0}; //目录条目项数量
    public static BaseAdapter baa;
    public static double screenWidthC =720.0;
    public static double screenHeightC=1440.0;
    public static int screenWidth;
    public static int screenHeight;
    public static boolean menu_State=false;
    // /** 昵称
    public static String userInfo = "";
    public static boolean isFirst=true;
    public static boolean isLogin;
    public static String userName="请登录";
    public static String userGlobal="";
    public static String userPurchased="";
    public static String currentPurchased="";
    //链表结构，(chapter, index),chapter——>unlockArray[index],  update unlockChapter (章节的解锁信息)
    public static HashMap<String,Integer> unChapterArray=new HashMap<>();
    //存储章节的解锁信息(chapter, unlockInfor)
    public static HashMap<String,Character> unlockChapter=new HashMap<>();
    public static char []unlockArray=new char[20];
    public static boolean isConnected=false;
    public static Boolean isExist=false;
    public static boolean ppCircle=false;
    public static boolean LOADING_OVER =false;//加载完成的标志
    public static Handler handler=new Handler();
    public static String texturePath="";
    public static double PROGRESS_RATIO=0;
    public static ScreenScaleResult ssr;//屏幕自适应结果对象
    public static int SCREEN_WIDTH;//实际屏幕宽度
    public static int SCREEN_HEIGHT;//实际屏幕高度
    public static int SCREEN_TARGET_WIDTH=1080;
    public static int SCREEN_TARGET_HEIGHT=1920;
    public static String file_path;
    //不同界面对应的文件路径
    public static String server_p;
    public static char isDownload;

    //3D物体操作变量
    public static ArrayList<TouchableObject> lovnList=new ArrayList<TouchableObject>();
    public static float yAngle;//绕Y轴旋转的角度
    public static float xAngle; //绕X轴旋转的角度
    public static float left;
    public static float right;
    public static float top;
    public static float near;
    public static float far;
    public static float bottom;
    public static boolean isHyaline=false;
    public static boolean isMutitouch=false;
    //对智能气泡位置的计算

    //纹理图ID
    public static int bubbleUpId;//气泡上
    public static int bubbleDownId;//气泡下
    public static int bubbleLeftId;//气泡左
    public static int bubbleRightId;//气泡右
    public static int wlWidth=60;//文字纹理图的宽度
    public static int wlHeight=60;//文字纹理图的高度
    public static float[] vertics=new float[]{0,0,0,1};
    public static int checkedIndex=-1;

    //功能选择位置量

    public static final int BUTTON_WIDTH=202;
    public static final int BUTTON_HEIGHT=58;
    public static final int RETURN_X=870;
    public static final int RETURN_Y=1830;
    public static final int RETURN_WIDTH=170;
    public static final int RETURN_HEIGHT=50;
    public static final int FUNCTION_HIDE=0;
    public static final int FUNCTION_HYALINE=1;
    public static final int CONTENT_BG_X=0;
    public static final int CONTENT_BG_Y=0;
    public static final int CONTENT_BG_WIDTH=1080;
    public static final int CONTENT_BG_HEIGHT=300;
    public static final int CONTENT_BG_BLANK=80;//?
    public static final int CONTENT_X=40;
    public static final int CONTENT_Y=25;
    public static final int DETAIL_X=980;
    public static final int DETAIL_Y=1718;
    public static final int DETAIL_WIDTH=67;
    public static final int DETAIL_HEIGHT=93;
    public static final int SPLIT_X=14;
    public static final int SPLIT_Y=1707;
    public static final int SPLIT_WIDTH=93;
    public static final int SPLIT_HEIGHT=93;
    public static final int BUTTON_WIDTH_TWO=93;
    public static final int BUTTON_HEIGHT_TWO=93;
    public static final int SPLIT_BG_X=7;
    public static final int SPLIT_BG_Y=1568;
    public static final int SPLIT_BG_WIDTH=108;
    public static final int SPLIT_BG_HEIGHT=240;
    public static final int SPLIT_RESET_X=19;
    public static final int SPLIT_RESET_Y=1581;
    public static final int SPLIT_RESET_WIDTH=83;
    public static final int SPLIT_RESET_HEIGHT=89;

    //2D纹理绘制大小、位置
    public static int BUBBLE_WIDTH=500;
    public static int BUBBLE_HEIGHT=360;
    public static int BUBBLE_PLUSWID=200;
    public static int BUBBLE_PLUSHEI=100;
    public static int BUBBLE_X=-337;
    public static int BUBBLE_Y=-360;
    public static int BUBBLE_TAIL_XY=75;

    public static String[] objChinese=new String[]{};
    public static String[] objName=new String[]{};
    public static String[] objDetailId=new String[]{};

    //加载进度条
    public static float loadPosition=0;
    public static float tempPosition=0;
    public static int loadBackgroundX=0;
    public static int loadBackgroundY=0;
    public static int loadBackgroundWidth=1080;
    public static int loadBackgroundHeight=1920;
    public static int loadBarX=242;
    public static int loadBarY=1321;
    public static int loadBarWidth=630;
    public static int loadBarHeight=24;
    public static HashMap<String,String> detailData=new HashMap<>();//从txt预先读入介绍内容
    //优化加载速度，数据的匹配
    public static String tDataResult =new String();//读取的数据结果
    public static int tDLineCount;//读取的数据结果
    public static String tData[];//读取的数据结果
    public static HashMap<String,Integer> hm_DBegin =new HashMap<>();
    public static HashMap<String,Integer> hm_DLength =new HashMap<>();

    //shader路径
    public static String shaders="shaders/";
    public static String picPath= WelcomeActivity.RootPath+"/HumanAnatomy/pic/";

    public static int severPathIndex=0;

    public static String sever_path_basic[]=
            {
                    "descriptive_anatomy/",
                    "regional_anatomy/"
            };
    //菜单界面资源字符串id的数组
    public static final int[][] nameIds={
            {R.string.xt_guanjie, R.string.xt_neizang, R.string.xt_maiguan,
                    R.string.xt_ganjueqi, R.string.xt_shenjing, R.string.xt_neifenmi},
            {R.string.bf_toubu, R.string.bf_jingbu, R.string.bf_xiongbu, R.string.bf_fubu,
                    R.string.bf_penbu, R.string.bf_jizhuqu, R.string.bf_shangzhi, R.string.bf_xiazhi},
    };
    public static final int[][] name_Frag_Ids={
            {R.string.gj_guanjiexue, R.string.gj_guxue, R.string.gj_jixue},
            {R.string.nz_huxi, R.string.nz_xiaohua, R.string.nz_miniao},
            {R.string.mg_xinxueguan, R.string.mg_linba},
            {R.string.gjq_shiqi, R.string.gjq_qtwq},
            {R.string.sj_zwsj, R.string.sj_zssj, R.string.sj_nhjs},
            {R.string.nfm_chuiti, R.string.nfm_jiazhuangxian, R.string.nfm_shenshangxian}
    };
    public static String[] picName= new String[]{
            "abdomen.png", "articular.png", "chest.png","endocrine.png", "head.png", "lowerlimbs.png","neck.png",
            "nervous.png","pelvina.png","sensory.png","splanchnology.png","upperlimbs.png","vasculature.png","vertebralis.png",
            "bubble_down.png","bubble_left.png","bubble_right.png","bubble_up.png",//"","","","","","","","","","","",
            "backgd_2d1.png","detailbg.png","content.png","content_no.png","split.png","split_no.png","split_bg.png","split_reset.png",
            "dark.png","expect.png","dot0.png","dot1.png","hx_introduction.png","xh_introduction.png","yd_introduction.png","menu.png",
            "loadbackground.png","lu.png",
            "myology.png"
    };
    public static void clearData()
    {
        detailData.clear();
        tDataResult=null;
        tDLineCount=0;
        for(int i=0;i<tData.length;i++)
        {
            tData[i]=null;
        }
        hm_DBegin.clear();
        hm_DLength.clear();
    }
    public static void loadAndroidOriPic()
    {//初始化界面图片资源
        TexManager.addTexArray(Constant.picName);
        TexManager.loadTextures();
    }
    public static void showToast(String ss,Context context)
    {

        handler.post(new Runnable() {   //投给主线程处理
            @Override
            public void run() {
                Toast mToast = Toast.makeText(context, " ", Toast.LENGTH_SHORT);
                mToast.setText(ss);
                mToast.show(); }
        });
    }
    public static void updateCategory()
    {
        categoryGroup_D.clear();
        categoryGroup_R.clear();
        DynamicInterfaceUtil dynamicInterfaceUtil=new DynamicInterfaceUtil();
        dynamicInterfaceUtil.loadFromSDFile();
    }
    public static void initunChapterArray()
    {
        unChapterArray.put("arthrology",0);
        unChapterArray.put("osteology",1);
        unChapterArray.put("myology",2);
        unChapterArray.put("respiratory",3);
        unChapterArray.put("alimentary",4);
        unChapterArray.put("urinary",5);
        unChapterArray.put("cardiovascular",6);
        unChapterArray.put("lymphatic",7);
        unChapterArray.put("visualizer",8);
        unChapterArray.put("peripheralnervous",9);
        unChapterArray.put("centralnervous",10);
        unChapterArray.put("adrenalgland",11);
        unChapterArray.put("headZ",12);
        unChapterArray.put("neckZ",13);
        unChapterArray.put("chestZ",14);
        unChapterArray.put("abdomenZ",15);
        unChapterArray.put("pelvinaZ",16);
        unChapterArray.put("vertebralisZ",17);
        unChapterArray.put("upperlimbsZ",18);
        unChapterArray.put("lowerlimbsZ",19);
    }

}
