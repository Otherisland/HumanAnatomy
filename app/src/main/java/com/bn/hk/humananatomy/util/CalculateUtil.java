package com.bn.hk.humananatomy.util;

import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.object.AABB;
import com.bn.hk.humananatomy.object.LoadedObjectVertexNormalTexture;
import com.bn.hk.humananatomy.thread.ObjectAlphaThread;
import com.bn.hk.humananatomy.view.ShowView;

import java.util.ArrayList;

import static com.bn.hk.humananatomy.constant.Constant.bubbleDownId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleHeight;
import static com.bn.hk.humananatomy.constant.Constant.bubbleLeftId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleRightId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleUpId;
import static com.bn.hk.humananatomy.constant.Constant.bubbleWidth;
import static com.bn.hk.humananatomy.constant.Constant.bubbleX;
import static com.bn.hk.humananatomy.constant.Constant.bubbleY;
import static com.bn.hk.humananatomy.constant.Constant.checkedIndex;
import static com.bn.hk.humananatomy.constant.Constant.far;
import static com.bn.hk.humananatomy.constant.Constant.isHyaline;
import static com.bn.hk.humananatomy.constant.Constant.isMutitouch;
import static com.bn.hk.humananatomy.constant.Constant.left;
import static com.bn.hk.humananatomy.constant.Constant.lovnList;
import static com.bn.hk.humananatomy.constant.Constant.nameHeight;
import static com.bn.hk.humananatomy.constant.Constant.nameWidth;
import static com.bn.hk.humananatomy.constant.Constant.top;
import static com.bn.hk.humananatomy.constant.Constant.vertics;
import static com.bn.hk.humananatomy.constant.Constant.wlHeight;
import static com.bn.hk.humananatomy.constant.Constant.wlWidth;
import static com.bn.hk.humananatomy.constant.Constant.nameX;
import static com.bn.hk.humananatomy.constant.Constant.nameY;
import static com.bn.hk.humananatomy.constant.Constant.bubbleTextureId;
import static com.bn.hk.humananatomy.constant.Constant.yAngle;
import static com.bn.hk.humananatomy.constant.Constant.near;
public class CalculateUtil {

    public static void calSQ(float x,float y)
    {
        //计算仿射变换后A、B两点在世界坐标系中的坐标
        float[] AB= IntersectantUtil.calculateABPosition
                (
                        x, y, //触控点x、y坐标
                        Constant.SCREEN_WIDTH, //屏幕宽度
                        Constant.SCREEN_HEIGHT, //屏幕高度
                        //近平面参数
                        left, //视角left、top值
                        top,
                        near, //视角near、far值
                        far
                );
        //射线AB
        Vector3f start = new Vector3f(AB[0], AB[1], AB[2]);//拾取射线线段起点A坐标
        Vector3f end = new Vector3f(AB[3], AB[4], AB[5]);//拾取射线线段起点B坐标
        /*
         * 计算AB线段与每个物体包围盒的最佳交点(与A点最近的交点)，
         * 并记录有最佳交点的物体在列表中的索引值
         */
        //记录列表中时间最小的索引值
        checkedIndex = -1;//设置选中物体索引(-1表示没有选中物体)
        int tmpIndex=-1;//用于记录距离A点最近物体的索引值
        float minT=1;//用于记录列表中各个物体AABB包围盒与拾取射线段最近交点的t值
        int minj=-1;
        float []circle=new float[3];
        ArrayList<AABB> boxArray_current=new ArrayList<AABB>();;
        for(int i = lovnList.size() - 1; i >= 0; i--)//遍历列表中的物体
        {
            //将射线start->end的起点与终点变换到物体坐标系
            Vector3f rayStart= MatrixState.fromGToO(start,lovnList.get(i).m);
            Vector3f rayEnd=MatrixState.fromGToO(end,lovnList.get(i).m);
            //获得当前物体的AABB包围盒
            ArrayList<AABB> boxArray=new ArrayList<AABB>();
            for(int j=0;j<lovnList.get(i).curCount;j++)
            {
                boxArray.add(j,lovnList.get(i).getCurrBox().get(j));
                //计算当前物体AABB包围盒最近的有效交点的t值
                float t = boxArray.get(j).rayIntersect(rayStart, rayEnd);
                if (t <= minT) {		//若小于原最小t
                    minT = t;			//更新最小t
                    tmpIndex = i;		//更新最近物体索引
                    minj=j;
                    boxArray_current=boxArray;
                }
            }
        }
        if(minj>=0)//计算摸中的球的球心坐标
        {
            circle[0]=boxArray_current.get(minj).minX+boxArray_current.get(minj).radius;
            circle[1]=boxArray_current.get(minj).minY+boxArray_current.get(minj).radius;
            circle[2]=boxArray_current.get(minj).minZ+boxArray_current.get(minj).radius;
        }
        if(tmpIndex!=-1&&!lovnList.get(tmpIndex).isHide)
        {
           lovnList.get(tmpIndex).setVerticsBubble(circle[0],circle[1],circle[2]);
        }else{
            tmpIndex=-1;
        }
        checkedIndex=tmpIndex;		//记录最近(选中)物体索引
        changeObj(checkedIndex);	//改变被选中物体
    }
    public static void changeObj(int index)
    {
        if (index != -1) {//如果有物体被选中
            for (int i = 0; i < lovnList.size(); i++) {//遍历所有可拾取物体列表
                if(!isMutitouch&&!(isHyaline&&lovnList.get(i).isHyaline))
                {
                    lovnList.get(i).changeOnTouch(false);//改变物体颜色及放大率到选中状态
                    lovnList.get(i).isChosen=false;
                }
                if (i == index) {
                    if (!isMutitouch) { //非多选
                        lovnList.get(i).isChosen = true;
                    } else {//多选
                        lovnList.get(i).isChosen = !lovnList.get(i).isChosen;
                    }
                }
                if(!lovnList.get(i).isChosen)
                {
                    lovnList.get(i).canBreathe=false;
                }else{
                    if(!lovnList.get(i).canBreathe)
                    {
                        ObjectAlphaThread obt=new ObjectAlphaThread
                                ((LoadedObjectVertexNormalTexture) lovnList.get(i));
                        obt.start();
                        lovnList.get(i).canBreathe=true;
                    }
                    lovnList.get(i).changeOnTouch(true);//改变物体颜色及放大率到选中状态
                }
            }
        }
        else
        {//如果没有物体被选中
            for (int i = 0; i < lovnList.size(); i++) {//遍历所有可拾取物体列表
                if(isHyaline&&lovnList.get(i).isHyaline)
                {
                    lovnList.get(i).isChosen=false;
                    lovnList.get(i).canBreathe=false;
                    lovnList.get(i).changeOnTouch(false);//改变物体颜色及放大率到未选中状态
                }
                else {
                    lovnList.get(i).isChosen=false;
                    lovnList.get(i).isHyaline = false;
                    lovnList.get(i).canBreathe=false;
                    lovnList.get(i).uAlpha = 0;
                    lovnList.get(i).changeOnTouch(false);//改变物体颜色及放大率到未选中状态
                }
            }
        }
    }
    public static void changeBubbleWHXY()
    {
        bubbleTextureId=bubbleUpId;
        nameWidth= lovnList.get(checkedIndex).getName().length()*wlWidth;
        nameHeight=wlHeight;
        int bubblePlusWidth= Constant.BUBBLE_PLUSWID;
        int bubblePlusHeight=Constant.BUBBLE_PLUSHEI;
        int bOriX=Constant.BUBBLE_X;
        int bOriY=Constant.BUBBLE_Y;
        int bubbleOriWidth=Constant.BUBBLE_WIDTH;
        int bubbleOriHeight=Constant.BUBBLE_HEIGHT;
        int bubbleFlag=2;

        bubbleWidth=bubblePlusWidth+nameWidth;
        bubbleHeight=bubblePlusHeight+nameHeight;
        float ratio_x=1f;
        float ratio_y=1f;
        int bX=bOriX*bubbleWidth/bubbleOriWidth;
        int bY=bOriY*bubbleHeight/bubbleOriHeight;
        int judgeRange=100;
        bubbleX=(int)(Constant.SCREEN_TARGET_WIDTH/2*vertics[0]+Constant.SCREEN_TARGET_WIDTH/2+bX);
        bubbleY=(int)(-Constant.SCREEN_TARGET_HEIGHT/2*vertics[1]+Constant.SCREEN_TARGET_HEIGHT/2+bY);
        boolean A=bubbleX<judgeRange;//右
        boolean B=bubbleX+bubbleWidth-Constant.SCREEN_WIDTH>-1*judgeRange;//左
        boolean C=bubbleY<judgeRange;//下
        boolean D=bubbleY+bubbleHeight-Constant.SCREEN_HEIGHT>-1*judgeRange;//上

        if(!B&&C&&!D)//C 下
        {
            bOriX=-bubbleOriWidth-bOriX;
            bOriY=-bubbleOriHeight-bOriY;
            bubbleTextureId=bubbleDownId;
            bubbleFlag=3;
        }else if(!A&&!C&&D)//D 上
        {
            bubbleTextureId=bubbleUpId;
            bubbleFlag=2;
        }else if(A&&!B&&!C)//A 右
        {
            int tempOriXY=bOriX;
            bOriX=-bubbleOriHeight-bOriY;
            bOriY=tempOriXY;
            int tempOriHW=bubbleOriHeight;
            bubbleOriHeight=bubbleOriWidth;
            bubbleOriWidth=tempOriHW;
            bubbleTextureId=bubbleRightId;
            bubbleFlag=0;
        }else if(!A&&B&&!D)//B 左
        {
            int tempOriXY=bOriY;
            bOriY=-bubbleOriWidth-bOriX;
            bOriX=tempOriXY;
            int tempOriHW=bubbleOriHeight;
            bubbleOriHeight=bubbleOriWidth;
            bubbleOriWidth=tempOriHW;
            bubbleTextureId=bubbleLeftId;
            bubbleFlag=1;
        }
        bubbleWidth=bubblePlusWidth+nameWidth;
        bubbleHeight=bubblePlusHeight+nameHeight;
        bX=bOriX*bubbleWidth/bubbleOriWidth;
        bY=bOriY*bubbleHeight/bubbleOriHeight;
        bubbleX=(int)(Constant.SCREEN_TARGET_WIDTH/2*vertics[0]+Constant.SCREEN_TARGET_WIDTH/2+bX);
        bubbleY=(int)(-Constant.SCREEN_TARGET_HEIGHT/2*vertics[1]+Constant.SCREEN_TARGET_HEIGHT/2+bY);
        if(bubbleFlag==0)//右
        {
            ratio_y=1f;
            ratio_x=(float) Constant.BUBBLE_TAIL_XY/(float) bubbleWidth;
            ratio_x=1+ratio_x;
        }else if(bubbleFlag==1)//左
        {
            ratio_y=1f;
            ratio_x=(float) Constant.BUBBLE_TAIL_XY/(float) bubbleWidth;
            ratio_x=1-ratio_x;
        } else if(bubbleFlag==2)//上
        {
            ratio_x=1f;
            ratio_y=(float) Constant.BUBBLE_TAIL_XY/(float) bubbleHeight;
            ratio_y=1-ratio_y;
        }else if(bubbleFlag==3)//下
        {
            ratio_x=1f;
            ratio_y=(float) Constant.BUBBLE_TAIL_XY/(float) bubbleHeight;
            ratio_y=1+ratio_y;
        }
        nameX=(int)((float)bubbleX+(((float)bubbleWidth*ratio_x)-(float)nameWidth)/2);
        nameY=(int)((float)bubbleY+(((float)bubbleHeight*ratio_y)-(float)nameHeight)/2);
    }
    public static void limitYAngle() //限制yAngle的范围-180~180之间
    {
        if ( yAngle <0&& Math.abs(yAngle)%360>=180)
        {
            yAngle=360-Math.abs(yAngle);
        }else if( yAngle>0&& yAngle%360>=180){
            yAngle=-(360- yAngle);
        }
    }
}
