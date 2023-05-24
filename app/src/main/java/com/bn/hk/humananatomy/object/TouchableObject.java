package com.bn.hk.humananatomy.object;//声明包

import com.bn.hk.humananatomy.util.MatrixState;

import java.util.ArrayList;

import static com.bn.hk.humananatomy.constant.Constant.objChinese;
import static com.bn.hk.humananatomy.constant.Constant.objName;

/*
 * 可以被触控到的抽象类，
 * 物体继承了该类可以被触控到
 */
public abstract class TouchableObject {
	public float objectTransX =0;
	public float objectTransY =0;
	public float objectTransZ =0;
	ArrayList<AABB> preBox=new ArrayList<AABB>();
	private String tempObjName;
	public boolean checkToMove=false;
	public boolean isHide=false;
	public boolean isHyaline=false;
	public boolean isChosen=false;
	public boolean canBreathe=false;
	public int curCount;
	public int uAlpha;
    public float[] m = new float[16];//基本变换矩阵
	float[] color=new float[]{1,1,1,1};//顶点颜色
	private float[] verticsBubble;
	//获得物体OBB包围盒的方法
    public ArrayList<AABB> getCurrBox(){
    	return preBox;//获取物体包围盒
    }
    //根据选中状态改变物体颜色、尺寸的方法
	public void changeOnTouch(boolean flag){
		if (flag) {//若选中
			color = new float[] { 0.553f, 0.871f, 0.922f, 1 };//改变颜色为绿色
		} else {//若没有选中
			color = new float[] { 1, 1, 1, 1 };	//恢复颜色为白色
		}	
	}
	//复制变换矩阵
    public void copyM(){
    	for(int i=0;i<16;i++){
    		m[i]= MatrixState.getMMatrix()[i];
    	}
    }
    //画当前被点击物体的名称
	public String getName()
	{
		return tempObjName;
	}
	public void setName(String fname)
	{
		int index=0;
		for(int i=0;i<objName.length;i++)
		{
			if(objName[i].equals(fname))
				index=i;
		}
		tempObjName = objChinese[index];
	}

	abstract public void drawSelf(int texId);

	public void setVerticsBubble(float x,float y,float z)//设置定位点向量
	{
		verticsBubble=new float[]{x,y,z,1};
	}

	public float[] getVerticsBubble()//获取定位点向量
	{
		return verticsBubble;
	}

}
