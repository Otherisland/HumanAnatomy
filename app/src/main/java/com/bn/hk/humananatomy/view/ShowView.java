package com.bn.hk.humananatomy.view;//声明包

import static com.bn.hk.humananatomy.constant.Constant.isHyaline;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import com.bn.hk.humananatomy.activity.ShowActivity;
import com.bn.hk.humananatomy.auto.ScreenScaleUtil;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.object.BNPoint;
import com.bn.hk.humananatomy.object.LoadedObjectVertexNormalTexture;
import com.bn.hk.humananatomy.object.ReturnButtonStack;
import com.bn.hk.humananatomy.object.SplitRecord;
import com.bn.hk.humananatomy.object.TexDrawer;
import com.bn.hk.humananatomy.object.TexDrawert;
import com.bn.hk.humananatomy.object.TouchableObject;
import com.bn.hk.humananatomy.texture.TexManager;
import com.bn.hk.humananatomy.util.CalculateUtil;
import com.bn.hk.humananatomy.util.FontUtil;
import com.bn.hk.humananatomy.util.LoadUtil;
import com.bn.hk.humananatomy.util.MatrixState;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.bn.hk.humananatomy.constant.Constant.CONTENT_BG_BLANK;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_BG_WIDTH;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_BG_X;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_BG_Y;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_X;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_Y;
import static com.bn.hk.humananatomy.constant.Constant.DETAIL_HEIGHT;
import static com.bn.hk.humananatomy.constant.Constant.DETAIL_WIDTH;
import static com.bn.hk.humananatomy.constant.Constant.DETAIL_X;
import static com.bn.hk.humananatomy.constant.Constant.DETAIL_Y;
import static com.bn.hk.humananatomy.constant.Constant.LOADING_OVER;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_BG_HEIGHT;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_BG_WIDTH;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_BG_X;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_BG_Y;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_HEIGHT;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_RESET_HEIGHT;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_RESET_WIDTH;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_RESET_X;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_RESET_Y;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_WIDTH;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_X;
import static com.bn.hk.humananatomy.constant.Constant.SPLIT_Y;
import static com.bn.hk.humananatomy.constant.Constant.baseSDPath;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.clearData;
import static com.bn.hk.humananatomy.constant.Constant.far;
import static com.bn.hk.humananatomy.constant.Constant.file_path;
import static com.bn.hk.humananatomy.constant.Constant.hm_DBegin;
import static com.bn.hk.humananatomy.constant.Constant.hm_DLength;
import static com.bn.hk.humananatomy.constant.Constant.left;
import static com.bn.hk.humananatomy.constant.Constant.loadBackgroundHeight;
import static com.bn.hk.humananatomy.constant.Constant.loadBackgroundWidth;
import static com.bn.hk.humananatomy.constant.Constant.loadBackgroundX;
import static com.bn.hk.humananatomy.constant.Constant.loadBackgroundY;
import static com.bn.hk.humananatomy.constant.Constant.loadBarHeight;
import static com.bn.hk.humananatomy.constant.Constant.loadBarWidth;
import static com.bn.hk.humananatomy.constant.Constant.loadBarX;
import static com.bn.hk.humananatomy.constant.Constant.loadBarY;
import static com.bn.hk.humananatomy.constant.Constant.lovnList;
import static com.bn.hk.humananatomy.constant.Constant.near;
import static com.bn.hk.humananatomy.constant.Constant.objChinese;
import static com.bn.hk.humananatomy.constant.Constant.objDetailId;
import static com.bn.hk.humananatomy.constant.Constant.objName;
import static com.bn.hk.humananatomy.constant.Constant.right;
import static com.bn.hk.humananatomy.constant.Constant.tDLineCount;
import static com.bn.hk.humananatomy.constant.Constant.tData;
import static com.bn.hk.humananatomy.constant.Constant.tempPosition;
import static com.bn.hk.humananatomy.constant.Constant.texturePath;
import static com.bn.hk.humananatomy.constant.Constant.loadPosition;
import static com.bn.hk.humananatomy.constant.Constant.detailData;
import static com.bn.hk.humananatomy.constant.Constant.bubbleDownId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleHeight;
import static com.bn.hk.humananatomy.constant.Constant.bubbleLeftId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleRightId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleUpId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleWidth;
import static com.bn.hk.humananatomy.constant.Constant.bubbleX;
import static com.bn.hk.humananatomy.constant.Constant.bubbleY;
import static com.bn.hk.humananatomy.constant.Constant.checkedIndex;
import static com.bn.hk.humananatomy.constant.Constant.nameHeight;
import static com.bn.hk.humananatomy.constant.Constant.nameWidth;
import static com.bn.hk.humananatomy.constant.Constant.top;
import static com.bn.hk.humananatomy.constant.Constant.bottom;
import static com.bn.hk.humananatomy.constant.Constant.vertics;
import static com.bn.hk.humananatomy.constant.Constant.wlHeight;
import static com.bn.hk.humananatomy.constant.Constant.wlWidth;
import static com.bn.hk.humananatomy.constant.Constant.nameX;
import static com.bn.hk.humananatomy.constant.Constant.nameY;
import static com.bn.hk.humananatomy.constant.Constant.bubbleTextureId;
import static com.bn.hk.humananatomy.constant.Constant.xAngle;
import static com.bn.hk.humananatomy.constant.Constant.yAngle;
import static com.bn.hk.humananatomy.texture.TexManager.createTexture;
import static com.bn.hk.humananatomy.texture.TexManager.initSDTextureETC1;
import static com.bn.hk.humananatomy.util.FontUtil.textSize;

public class ShowView extends GLSurfaceView
{
    private SceneRenderer mRenderer;//场景渲染器
    boolean moveFlag=false;//触控是否是移动事件标志
    boolean clickedRefer=false;
    boolean clickedBubble=false;
    boolean splitMode=false;
    boolean detailedIntroduction=false;
    float oldX;float oldY;
    float modelTranslationX =0;float modelTranslationY =0;float modelTranslationZ =0;
    float currScale=1;  //初始缩放比例

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private final float transSpeedSpan=12.7f;
    private final float scaleSpeedSpan=220;//转换比例
    
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    private float touchDistance;     //手指落下时两指之间距离

    HashMap<Integer,BNPoint> hm=new HashMap<Integer,BNPoint>();//存放触控点
    //可拾取物体列表

    //栈的物体列表
    List<TouchableObject> stackList;
    ReturnButtonStack rbs;
    Stack<ReturnButtonStack> returnStack;
    Stack<SplitRecord> splitStack;
    SplitRecord touchableNew;
    SplitRecord touchableOld;
    ReturnButtonStack rbsPOP;

    int wenliId=-1;
    int maskTextureId;//工具栏板
    // 触控得到的xy数组
    int xy[] = new int[]{};
    int x2D;
    int y2D;
    int previousIndex=-1;
    int stackFunction;
    //被选中物体的索引值，即id，没有被选中时索引值为-1
    int detailButtonId=-1;
    int ndetailButtonId=-1;
    int splitButtonId=-1;
    int splitButtonBGId=-1;
    int splitResetId=-1;
    int nsplitButtonId=-1;
    int detailBgId;
    int detailId=-1;
	public ShowView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer(this,(ShowActivity) context);	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
        lovnList.clear();//每次进showView清空上次的加载模型
        stackList=new CopyOnWriteArrayList<TouchableObject>();
        returnStack=new Stack();
        splitStack=new Stack<>();//拆分时所用栈结构
        LOADING_OVER=false;
    }
    public void actionDown(MotionEvent e,int index,int id,float x,float y)
    {
        if(!clickedRefer&&!clickedBubble)
        {
            hm.put(id,new BNPoint(e.getX(index),e.getY(index)));
            oldX=x ;
            oldY=y;
        }
        moveFlag=false;
    }
    public void actionPointerDown(MotionEvent e,int index,int id)
    {
        moveFlag=false;
        if(!clickedBubble&&!clickedRefer)
        {
            hm.put(id,new BNPoint(e.getX(index),e.getY(index)));
            if(hm.size()==2)
            {
                BNPoint bpTempA=hm.get(e.getPointerId(0));
                BNPoint bpTempB=hm.get(e.getPointerId(1));
                touchDistance =BNPoint.calDistance(bpTempA,bpTempB);
                oldX=bpTempA.x;
                oldY=bpTempA.y;
            }
        }
    }
    public void actionMovePoint2(MotionEvent e)
    {
        BNPoint bpTempA=hm.get(e.getPointerId(0));
        BNPoint bpTempB=hm.get(e.getPointerId(1));
        float currDis=BNPoint.calDistance(bpTempA,bpTempB);
        currScale+=(currDis- touchDistance)/scaleSpeedSpan;
        if(currScale>=3||currScale<0.2f)//缩放范围
        {
            currScale=currScale-(currDis- touchDistance)/scaleSpeedSpan;
        }
        touchDistance =currDis;
        //手指操控物体移动
        double pai=3.14159265359;
        double  yAngleH=2.0/360*yAngle*pai;  //yAngle 为底面角度
        double xAngleH=2.0/360*xAngle*pai;  //xAngle 为侧面角度
        modelTranslationX += (float) Math.cos(yAngleH)*(bpTempA.x-oldX)/transSpeedSpan-(float) Math.sin(yAngleH)*Math.sin(xAngleH)*(bpTempA.y-oldY)/transSpeedSpan;;
        modelTranslationY -=(float) Math.cos(xAngleH)*(bpTempA.y-oldY)/transSpeedSpan;
        modelTranslationZ += (float) Math.sin(yAngleH)*(bpTempA.x-oldX)/transSpeedSpan+(float) Math.cos(yAngleH)*Math.sin(xAngleH)*(bpTempA.y-oldY)/transSpeedSpan;
        oldX=bpTempA.x;
        oldY=bpTempA.y;
    }
    public void actionMove(MotionEvent e,float x,float y)
    {
        float dx = x - oldX;//计算触控点X位移
        float dy = y - oldY;//计算触控点Y位移
        //若大于阈值则是移动
        if(Math.abs(dx)>20f || Math.abs(dy)>20f)
        {
            moveFlag=true;
        }
        if(!clickedRefer&&!clickedBubble&&moveFlag)
        {
            int count=e.getPointerCount();
            for(int i=0;i<count;i++)
            {
                int tempId=e.getPointerId(i);
                hm.get(tempId).setLocation(e.getX(i),e.getY(i));
            }
            if(hm.size()==2)
            {
                actionMovePoint2(e);
            }
            if(hm.size()==1)//只有一个触控点时移动
            {
                dy = y - mPreviousY;//计算触控笔Y位移
                dx = x - mPreviousX;//计算触控笔X位移
                if(splitMode&&checkedIndex>=0)
                {
                    float transX_temp=0;
                    float transY_temp=0;
                    float transZ_temp=0;
                    double pai=3.14159265359;
                    double  yAngleH=2.0/360*yAngle*pai;  //yAngle 为底面角度
                    double xAngleH=2.0/360*xAngle*pai;  //xAngle 为侧面角度
                    transX_temp= (float) ((float) Math.cos(yAngleH)*dx/transSpeedSpan-(float) Math.sin(yAngleH)*Math.sin(xAngleH)*dy/transSpeedSpan);;
                    transY_temp=(float) Math.cos(xAngleH)*dy/transSpeedSpan;
                    transZ_temp= (float) ((float) Math.sin(yAngleH)*dx/transSpeedSpan+(float) Math.cos(yAngleH)*Math.sin(xAngleH)*dy/transSpeedSpan);
                    lovnList.get(checkedIndex).objectTransX +=transX_temp;
                    lovnList.get(checkedIndex).objectTransY -=transY_temp;
                    lovnList.get(checkedIndex).objectTransZ +=transZ_temp;
                }
                if(!splitMode||checkedIndex==-1)
                {
                    yAngle += dx * TOUCH_SCALE_FACTOR*0.5;//设置沿y轴旋转角度
                    xAngle+= dy * TOUCH_SCALE_FACTOR*0.3;//设置沿x轴旋转角度
                    if(Math.abs(xAngle)>=90)
                    {
                        xAngle=((xAngle>0)?1:-1)*90;
                    }
                    CalculateUtil.limitYAngle();
                }
            }
        }
    }

    public boolean judgeFunctionOne(int x,int y)//判断当前功能,底层功能栏
    {
        if(x2D>=x && x2D<=x+Constant.BUTTON_WIDTH && y2D>=y&&y2D<=y+Constant.BUTTON_HEIGHT)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean judgeFunctionTwo(int x,int y)//判断当前功能,底层功能栏
    {
        if(x2D>=x && x2D<=x+Constant.BUTTON_WIDTH_TWO && y2D>=y&&y2D<=y+Constant.BUTTON_HEIGHT_TWO)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void actionUpFunction(float x,float y)//功能栏的几个功能
    {
        if(checkedIndex!=-1&&judgeFunctionOne(Constant.HIDE_NOW_X,Constant.HIDE_NOW_Y))
        {
            stackFunction=Constant.FUNCTION_HIDE;
            lovnList.get(checkedIndex).isHide=true;
            stackList.add(lovnList.get(checkedIndex));
        }
        if(checkedIndex!=-1&&judgeFunctionOne(Constant.HIDE_OTHER_X,Constant.HIDE_OTHER_Y))
        {
            stackFunction=Constant.FUNCTION_HIDE;
            for(int j=0;j<lovnList.size();j++)
            {
                if(j!=checkedIndex&&!lovnList.get(j).isHide)
                {
                    lovnList.get(j).isHide=true;
                    stackList.add(lovnList.get(j));
                }
            }
        }
        //透明当前模型
        if(checkedIndex!=-1&&judgeFunctionOne(Constant.HYALINE_NOW_X,Constant.HYALINE_NOW_Y))
        {
            stackFunction=Constant.FUNCTION_HYALINE;
            isHyaline=true;
            lovnList.get(checkedIndex).isHyaline=true;
            lovnList.get(checkedIndex).uAlpha=1;
            stackList.add(lovnList.get(checkedIndex));
        }
        if(checkedIndex!=-1&&judgeFunctionOne(Constant.HYALINE_OTHER_X,Constant.HYALINE_OTHER_Y)) {
            isHyaline = true;
            stackFunction=Constant.FUNCTION_HYALINE;
            for (int j = 0; j < lovnList.size(); j++) {
                if (j != checkedIndex&&!lovnList.get(j).isHyaline&&!lovnList.get(j).isHide) {
                    lovnList.get(j).isHyaline = true;
                    lovnList.get(j).uAlpha = 1;
                    stackList.add(lovnList.get(j));
                }
            }
        }
    }
    public void actionUpReturn()//撤销
    {
        if(!returnStack.empty()&&judgeFunctionOne(Constant.RETURN_X,Constant.RETURN_Y))//撤销
        {
            rbsPOP=returnStack.pop();
            int functionIndex=rbsPOP.getFunction(),i=0;
            List<TouchableObject> alist=rbsPOP.getAlist();
            switch (functionIndex)
            {
                case Constant.FUNCTION_HIDE:
                    for(i=0;i<alist.size();i++)
                    {
                        alist.get(i).isHide=false;
                    }
                    break;
                case Constant.FUNCTION_HYALINE:
                    for(i=0;i<alist.size();i++)
                    {
                        alist.get(i).isHyaline=false;
                        alist.get(i).uAlpha = 0;
                    }
                    break;
            }
        }
    }
    public void actionUpSplit(float x,float y)
    {
        if(!(x2D>=DETAIL_X&&judgeFunctionTwo(DETAIL_X,DETAIL_Y)))//没有点击介绍按钮
        {
            CalculateUtil.calSQ(x,y);
        }
        if(judgeFunctionTwo(SPLIT_RESET_X,SPLIT_RESET_Y))//重置
        {
            for(int i=0;i<lovnList.size();i++)
            {
                lovnList.get(i).objectTransY =0;
                lovnList.get(i).objectTransX =0;
                lovnList.get(i).objectTransZ =0;
            }
        }
        if(checkedIndex>=0)
        {
            if(previousIndex>=0)
            {
                lovnList.get(previousIndex).checkToMove=false;
            }
            lovnList.get(checkedIndex).checkToMove=true;
            touchableOld=new SplitRecord();
            touchableOld.mytransX=lovnList.get(checkedIndex).objectTransX;
            touchableOld.mytransY=lovnList.get(checkedIndex).objectTransY;
            touchableOld.lovnListId=checkedIndex;
            splitStack.push(touchableOld);
            previousIndex=checkedIndex;
        }
        if(!splitStack.empty()&&judgeFunctionOne(Constant.RETURN_X,Constant.RETURN_Y))//撤销
        {
            touchableNew =splitStack.pop();
            for(int i=0;i<lovnList.size();i++)
            {
                if(i==touchableNew.lovnListId)
                {
                    lovnList.get(i).objectTransX = touchableNew.mytransX;
                    lovnList.get(i).objectTransY = touchableNew.mytransY;
                }
            }
        }
    }
    public void actionUp(float x,float y)
    {
        if(checkedIndex>=0)
        {
            lovnList.get(checkedIndex).checkToMove=false;
        }
        if(!moveFlag&&checkedIndex!=-1&&x2D>=bubbleX&&x2D<=bubbleX+bubbleWidth&&
                y2D>=bubbleY&&y2D<=bubbleY+bubbleHeight)
        {
            clickedBubble=true;
        }else
        {
            clickedBubble=false;
        }
        if(!clickedRefer&&!clickedBubble&&!moveFlag)
        {
            stackList=new CopyOnWriteArrayList<TouchableObject>();
            actionUpFunction(x,y);//功能栏的几个功能实现
            if(stackList.size()!=0&&stackList!=null)
            {
                rbs=new ReturnButtonStack(stackFunction,stackList);
                returnStack.push(rbs);
            }
            actionUpReturn();//撤销方法
        }
        if(splitMode)//拆分
        {
             actionUpSplit(x,y);
        }
        if(!clickedRefer&&checkedIndex!=-1&&x2D>=DETAIL_X&&x2D<=DETAIL_X+DETAIL_WIDTH&&
                y2D>=DETAIL_Y&&y2D<=DETAIL_Y+DETAIL_HEIGHT)//内容按钮监听
        {
            clickedRefer=true;
            detailedIntroduction=true;
        }else
        {
            clickedRefer=false;
            detailedIntroduction=false;
            if(!moveFlag)
            {
                CalculateUtil.calSQ(x,y);
            }
        }
        if(x2D>=SPLIT_X&&x2D<=SPLIT_X+SPLIT_WIDTH&&y2D>=SPLIT_Y&&
                y2D<=SPLIT_Y+SPLIT_HEIGHT)//点击拆分按钮
        {
            splitMode=!splitMode;//拆分模式
        }
        hm.clear();
        moveFlag=false;
    }
    public void actionPointerUp(int id,float x,float y)
    {
        if(!clickedRefer&&!clickedBubble&&!moveFlag)
        {
            CalculateUtil.calSQ(x,y);
        }hm.remove(id);
        moveFlag=false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if(LOADING_OVER)//加载界面触屏无效
        {
            float y = e.getY();
            float x = e.getX();
            //转换为标准屏触控坐标
            xy = ScreenScaleUtil.touchFromTargetToOrigin((int)x, (int)y, Constant.ssr);
            x2D = xy[0];y2D = xy[1];     //用于2D触控的位置判断        /*转换为标准屏触控坐标*/
            int action=e.getAction()&MotionEvent.ACTION_MASK;
            //获取主、辅点id（down时主辅点id皆正确，up时辅点id正确，主点id要查询Map中剩下的一个点的id）
            int index=(e.getAction()&MotionEvent.ACTION_POINTER_INDEX_MASK)
                    >>>MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int id=e.getPointerId(index);
            switch(action)
            {
                case MotionEvent.ACTION_DOWN:
                    actionDown(e,index,id,x,y);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionPointerDown(e,index,id);
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionMove(e,x,y);
                    break;
                case MotionEvent.ACTION_UP:
                    actionUp(x,y);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionPointerUp(id,x,y);
                    break;
            }
            mPreviousY = y;//记录触控笔位置
            mPreviousX = x;//记录触控笔位置
        }
        return true;
    }

    private class SceneRenderer implements Renderer
    {
        ShowActivity sa;
        ShowView sv;
        float ratio;//视口宽高比例
        int loadFlag=0;
        int nameId=-1;//当前的纹理id
        int loadBackgroundId;
        int loadBarId;
        int loadIndex =0;
        int workFlag=-4;//加载任务分步的标志
        int showDetailLineCount=0;
        int workDataLineCount=0;
        TexDrawert load;//3D绘制2D
        TexDrawer nRect;//文字对象
        TexDrawer texRect;//纹理三角形对象引用
        TexDrawer tr;//工具栏的对象
        TexDrawer tBubble;//气泡
        TexDrawer rRect;//展示具体介绍板
        LoadedObjectVertexNormalTexture lodvnt;
        String strSingle;

        public SceneRenderer(ShowView sv,ShowActivity sa)
        {
            this.sv=sv;
            this.sa=sa;
        }
        public String[] readFile(String filePath,int use_flag)
        {
            int ch=0,index=0;
            int lineCount=0;
            String result=null;
            String[] data=new String[]{};
            try {
                File f=new File(filePath);
                FileInputStream fin=new FileInputStream(f);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while((ch=fin.read())!=-1)
                {
                    if(index<3) { index++; }
                    else{ baos.write(ch); if(ch=='\n') { lineCount++; }}
                }
                lineCount++;
                byte[] buff=baos.toByteArray();
                fin.read(buff);
                baos.close();
                fin.close();
                result=new String(buff,"UTF-8");
                result=result.replaceAll("\\r\\n","\n");
                data=result.split("[\t\n]");//字符串的拆分方法
                switch (use_flag)
                {
                    case 1:
                        showDetailLineCount=lineCount;
                        break;
                    case 2:
                        tDLineCount=lineCount;
                        break;
                    case 3:
                        workDataLineCount=lineCount;
                        break;
                }
            }
            catch(Exception e)
            {
                System.out.println("     任务1出错出错     ");
                e.printStackTrace();
            }
            return data;
        }
        public void showDetail()
        {
            int use_flag=1;
            String fileName="details.txt";
            String filePath=null;
            String data[];
            filePath=baseSDPath[categoryIndex]+file_path+"/instruction/"+fileName;
            data=readFile(filePath,use_flag);
            //System.out.println("行数！！！！！"+showDetailLineCount);
            for(int i=0;i<showDetailLineCount*2;i+=2)
            {
                detailData.put(data[i],data[i+1]);
            }
        }
        public void getData( ShowActivity sa)
        {
            int use_flag=2;
            int preIndex=0;
            int nowIndex=0;
            int length=0;
            String fileName= "touch_data.txt";
            String filePath=null;
            String objName="";  //存放obj名字

            filePath=baseSDPath[categoryIndex]+file_path+"/data/"+fileName;
            tData=readFile(filePath,use_flag);
            for(int i = 0; i< tDLineCount *5; i+=5)
            {
                String na= tData[i].substring(0, tData[i].length()-4);//obj名称
                if(!objName.equals(na))
                {
                    if(i!=0)
                    {
                        nowIndex=i;
                        length=nowIndex-preIndex;
                        hm_DLength.put(objName,length);
                    }
                    preIndex=i;
                    objName=na;
                    hm_DBegin.put(objName,i);
                }
                if(i==tDLineCount *5-5) //最后一个obj的小球数据长度
                {
                    nowIndex=tDLineCount *5-1;
                    length=nowIndex-preIndex+1;
                    //System.out.println("最后一个数据 "+objName+"   "+length);
                    hm_DLength.put(objName,length);
                }
            }
        }
        public void workReadData()
        {
            int use_flag=3;
            String fileName= "costal_data.txt";
            String filePath=null;
            String[] data;

            if(!hm_DBegin.isEmpty())
            {
                clearData();
            }
            filePath=baseSDPath[categoryIndex]+file_path+"/data/"+fileName;
            data=readFile(filePath,use_flag);
            objName=new String[workDataLineCount];
            objChinese=new String[workDataLineCount];
            objDetailId=new String[workDataLineCount];
            for(int i=0,j=0;i<workDataLineCount*3;i+=3,j++)
            {
                objName[j]=data[i];     //对应部位的obj名字
                objChinese[j]=data[i+1];//对应部位的中文名字用于气泡上内容的显示
                objDetailId[j]=data[i+2];
            }
        }
        public void workLoadObj()
        {
            if((loadIndex<objName.length))
            {
                System.out.println(objName.length+"长第"+ loadIndex +"块肉"+objName[loadIndex]);
                strSingle=objName[loadIndex];
                lodvnt= LoadUtil.loadFromFile(baseSDPath[categoryIndex]+file_path+"/obj/"+strSingle + ".obj", ShowView.this.getResources(), ShowView.this,strSingle,sa);
                if(lodvnt!=null)
                {
                    lovnList.add(lodvnt);
                }
                float xprint=((float)(loadIndex +1)/(float)objName.length);
                System.out.println("比例为======="+xprint);
                loadPosition=tempPosition+510*xprint;
                loadIndex++;
            }
            else if(loadIndex>=objName.length)
            {
                LOADING_OVER =true;
                loadFlag=0;
                loadIndex =0;
            }
        }
        public void draw2DScene()
        {
            MatrixState.pushMatrix();
            MatrixState.setProjectOrtho                                 //调用此方法计算产生平行投影矩阵
                    (-Constant.ssr.vpRatio, Constant.ssr.vpRatio, -1, 1, 1, 10);
            //调用此方法产生摄像机观察矩阵
            MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            tr.drawSelf(maskTextureId,0,0,1080,1920);//绘制隐藏，撤销功能板
            if(splitMode)
            {
                tr.drawSelf(splitButtonId,SPLIT_X,SPLIT_Y,SPLIT_WIDTH,SPLIT_HEIGHT);//绘制拆分按钮
                tr.drawSelf(splitButtonBGId,SPLIT_BG_X,SPLIT_BG_Y,SPLIT_BG_WIDTH,SPLIT_BG_HEIGHT);
                tr.drawSelf(splitResetId,SPLIT_RESET_X,SPLIT_RESET_Y,SPLIT_RESET_WIDTH,SPLIT_RESET_HEIGHT);
            }else
            {
                tr.drawSelf(nsplitButtonId,SPLIT_X,SPLIT_Y,SPLIT_WIDTH,SPLIT_HEIGHT);//绘制拆分按钮
            }
            if(!clickedRefer)
            {
                tr.drawSelf(ndetailButtonId,DETAIL_X,DETAIL_Y,DETAIL_WIDTH,DETAIL_HEIGHT);//绘制内容按钮
            }
            else
            {
                tr.drawSelf(detailButtonId,DETAIL_X,DETAIL_Y,DETAIL_WIDTH,DETAIL_HEIGHT);//绘制内容按钮
            }
            if(checkedIndex>=0)
            {
                MatrixState.pushMatrix();
                //改变宽度高度XY位置量,灵活气泡
                CalculateUtil.changeBubbleWHXY();
                if(clickedRefer)//展示内容
                {
                    if(detailId!=-1)
                    {//若当前纹理存在，则删除
                        GLES30.glDeleteTextures(1, new int[]{detailId}, 0);
                    }
                    //重新生成文本纹理图
                    if(checkedIndex<detailData.size()*2)
                    {
                        String detail="";
                        detail=detailData.get(objDetailId[checkedIndex]);
                        int count=(int)((CONTENT_BG_WIDTH-96)/45)+1;
                        int column=detail.length()/count+1;//行数
                        int height=(int)(column*textSize);
                        Bitmap bm= FontUtil.generateWLT(detail, CONTENT_BG_WIDTH, height+10);
                        detailId=createTexture(bm);//将生成的纹理图加载
                        nRect.drawSelf(detailId,CONTENT_X,CONTENT_Y,CONTENT_BG_WIDTH,height+10);//显示介绍文字
                        rRect.drawSelf(detailBgId,CONTENT_BG_X,CONTENT_BG_Y,
                                CONTENT_BG_WIDTH,height+CONTENT_BG_BLANK);//绘制展示细节背景
                    }
                    clickedRefer=true;
                }
                if(nRect!=null)
                {
                    nRect.drawSelf(nameId,nameX,nameY,nameWidth,nameHeight);
                }
                if(tBubble!=null)
                {
                    tBubble.drawSelf(bubbleTextureId,bubbleX,bubbleY,
                            bubbleWidth,bubbleHeight);
                }
                MatrixState.popMatrix();
            }
            MatrixState.popMatrix();
        }
        public void drawScene()
        {
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -400f);
            MatrixState.translate(0,-10,-60);
            if(!splitMode)
            {
                MatrixState.translate(modelTranslationX, modelTranslationY, modelTranslationZ);    //认真思考translate和rotate这两个函数在3D空间作用及顺序
            }
            MatrixState.rotate(yAngle, 0, 1, 0);
            if(yAngle<0){
                if(yAngle>-90)
                {
                    MatrixState.rotate(xAngle,(90-Math.abs( yAngle)), 0,-Math.abs(yAngle) );
                }else{
                    MatrixState.rotate(xAngle, -(Math.abs( yAngle)-90), 0,-(180-Math.abs(yAngle)));
                }

            }
            else{
                if (yAngle<90)
                {
                    MatrixState.rotate(xAngle, 90-yAngle, 0,yAngle);
                }else{
                    MatrixState.rotate(xAngle, -(yAngle-90), 0,(180-yAngle) );
                }
            }
            MatrixState.scale(currScale,currScale,currScale);//控制物体变大与变小

            if(splitMode)//拆分模式
            {
                // MatrixState.rotate(yAngle, 0, 1, 0);
                for(int i=0;i<lovnList.size();i++)
                {
                    if(lovnList.get(i)!=null)
                    {
                        MatrixState.pushMatrix();
                        MatrixState.translate(lovnList.get(i).objectTransX,lovnList.get(i).objectTransY,lovnList.get(i).objectTransZ);
                        if(i==checkedIndex)  //拆分模式 气泡跟着模型走
                        {
                            vertics=MatrixState.getBubblePoint(lovnList.get(checkedIndex).getVerticsBubble());
                        }
                        //MatrixState.scale(currScale,currScale,currScale);//控制物体变大与变小
                        lovnList.get(i).drawSelf(wenliId);
                        MatrixState.popMatrix();
                    }
                }
            }else
            {

                if(checkedIndex>=0)
                {
                    vertics=MatrixState.getBubblePoint(lovnList.get(checkedIndex).getVerticsBubble());
                }
                for(int i=0;i<lovnList.size();i++)
                {
                    //若加载的物体不为空则绘制物体
                    if(lovnList.get(i)!=null&&!lovnList.get(i).isHide)
                    {
                        // System.out.println(i+"       ！！@@当前绘制的是");
                        lovnList.get(i).drawSelf(wenliId);
                    }
                }
            }
            MatrixState.popMatrix();
            //关闭混合
            GLES30.glDisable(GLES30.GL_BLEND);
            if(checkedIndex>=0)
            {
                if(nameId!=-1)
                {//若当前纹理存在，则删除
                    GLES30.glDeleteTextures(1, new int[]{nameId}, 0);
                }
                //重新生成文本纹理图
                Bitmap bm=FontUtil.generateWLT(lovnList.get(checkedIndex).getName(), lovnList.get(checkedIndex).getName().length()*wlWidth, wlHeight);
                nameId=createTexture(bm);//将生成的纹理图加载
            }

            //清除深度缓冲
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT);
            draw2DScene();

        }
        public void drawLoading()
        {
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT);
            MatrixState.pushMatrix();
            //平行投影
            MatrixState.setProjectOrtho(-Constant.ssr.vpRatio, Constant.ssr.vpRatio, -1, 1, 2, 10);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,3,0f,0f,0f,0f,1.0f,0.0f);
            MatrixState.translate(0,0,1);
            load.drawSelf(loadBarId,loadBarX,loadBarY,loadBarWidth,loadBarHeight);
            texRect.drawSelf(loadBackgroundId,loadBackgroundX,loadBackgroundY, loadBackgroundWidth, loadBackgroundHeight);//画加载界面
            MatrixState.popMatrix();
        }
        public void workLoadTexture()
        {
            tr=new TexDrawer(ShowView.this);
            nRect=new TexDrawer(ShowView.this);
            tBubble=new TexDrawer(ShowView.this);
            rRect=new TexDrawer(ShowView.this);

            maskTextureId=createTexture(TexManager.getTex("backgd_2d1.png"));
            bubbleUpId= createTexture(TexManager.getTex("bubble_up.png"));
            bubbleDownId=createTexture(TexManager.getTex("bubble_down.png"));
            bubbleLeftId=createTexture(TexManager.getTex("bubble_left.png"));
            bubbleRightId=createTexture(TexManager.getTex("bubble_right.png"));
            detailBgId =createTexture(TexManager.getTex("detailbg.png"));
            detailButtonId=createTexture(TexManager.getTex("content.png"));
            ndetailButtonId=createTexture(TexManager.getTex("content_no.png"));
            splitButtonId=createTexture(TexManager.getTex("split.png"));
            nsplitButtonId=createTexture(TexManager.getTex("split_no.png"));
            splitButtonBGId=createTexture(TexManager.getTex("split_bg.png"));
            splitResetId=createTexture(TexManager.getTex("split_reset.png"));
            wenliId=initSDTextureETC1(texturePath);
        }
        public void onDrawFrame(GL10 gl)
        {
            if(!LOADING_OVER &&loadFlag==0)
            {
                loadFlag++;
            }
        	//清除深度缓冲与颜色缓冲
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-left, right, -bottom, top, near, far);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,0f,0f,0f,-1f,0f,1.0f,0.0f);
            //开启混合
            GLES30.glEnable(GLES30.GL_BLEND);
            //设置混合因子
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
            if(!LOADING_OVER)//当解压完成并且还没有加载完成后执行
            {
                drawLoading();
                switch(workFlag)
                {
                    case 0:
                        workLoadTexture();
                        loadPosition+=20;
                        break;
                    case 6:
                        workReadData();//读数据，obj名称
                        showDetail();//读内容
                        loadPosition+=20;
                        break;
                    case 9:
                        getData(sa);//读球的数据
                        loadPosition+=30;
                        tempPosition=loadPosition;
                        break;
                    case 12 :
                        workLoadObj();//加载肌肉obj进集合框架
                        break;
                }
                if(workFlag==12)
                {
                    workFlag=12;
                }else
                {
                    workFlag++;
                }
            }else //加载完毕进行绘制
            {
                drawScene();//绘制主要3D场景
                workFlag=-1;
            }
        }
        public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            GLES30.glViewport(Constant.ssr.lucX,                        //设置视口大小及位置
                    Constant.ssr.lucY, Constant.ssr.skWidth, Constant.ssr.skHeight);
            //计算GLSurfaceView的宽高比
            ratio = (float) width / height;
            left=right=ratio;
            top=bottom=1;
            near=8;
            far=800;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-left, right, -bottom, top, near, far);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,0f,0f,0f,-1f,0f,1.0f,0.0f);
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0f,0f,0f,1.0f);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
            //初始化光源位置
            MatrixState.setLightLocation(40, 10, 20);
            //加载要绘制的物体
            texRect=new TexDrawer(ShowView.this);
            load=new TexDrawert(ShowView.this);
            TexManager.loadTextures();
            loadBackgroundId = createTexture(TexManager.getTex("loadbackground.png"));
            loadBarId= createTexture(TexManager.getTex("lu.png"));
        }
    }
}
