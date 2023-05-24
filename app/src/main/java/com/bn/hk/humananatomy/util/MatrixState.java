package com.bn.hk.humananatomy.util;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Stack;

//存储系统矩阵状态的类
public class MatrixState 
{
	private static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    private static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵   
    private static float[] currMatrix;//当前变换矩阵
    public static float[] lightLocation=new float[]{0,0,0};//定位光光源位置
    public static FloatBuffer cameraFB;
    public static FloatBuffer lightPositionFB;
    
    public static Stack<float[]> mStack=new Stack<float[]>();//保护变换矩阵的栈
    
    public static void setInitStack()//获取不变换初始矩阵
    {
    	currMatrix=new float[16];
    	Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }
    
    public static void pushMatrix()//保护变换矩阵
    {
    	mStack.push(currMatrix.clone());
    }
    
    public static void popMatrix()//恢复变换矩阵
    {
    	currMatrix=mStack.pop();
    }
    
    public static void translate(float x,float y,float z)//设置沿xyz轴移动
    {
    	Matrix.translateM(currMatrix, 0, x, y, z);
    }
    
    public static void rotate(float angle,float x,float y,float z)//设置绕xyz轴移动
    {
    	Matrix.rotateM(currMatrix,0,angle,x,y,z);
    }
    
    public static void scale(float x,float y,float z)//缩放
    {
    	Matrix.scaleM(currMatrix,0, x, y, z);
    }
    
    
    //设置摄像机
    public static void setCamera
    (
    		float cx,	//摄像机位置x
    		float cy,   //摄像机位置y
    		float cz,   //摄像机位置z
    		float tx,   //摄像机目标点x
    		float ty,   //摄像机目标点y
    		float tz,   //摄像机目标点z
    		float upx,  //摄像机UP向量X分量
    		float upy,  //摄像机UP向量Y分量
    		float upz   //摄像机UP向量Z分量		
    )
    {
    	Matrix.setLookAtM
        (
        		mVMatrix, 
        		0, 
        		cx,
        		cy,
        		cz,
        		tx,
        		ty,
        		tz,
        		upx,
        		upy,
        		upz
        );
    	
    	float[] cameraLocation=new float[3];//摄像机位置
    	cameraLocation[0]=cx;
    	cameraLocation[1]=cy;
    	cameraLocation[2]=cz;
    	
    	ByteBuffer llbb = ByteBuffer.allocateDirect(3*4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        cameraFB=llbb.asFloatBuffer();
        cameraFB.put(cameraLocation);
        cameraFB.position(0);  
    }
    
    //设置透视投影参数
    public static void setProjectFrustum
    (
    	float left,		//near面的left
    	float right,    //near面的right
    	float bottom,   //near面的bottom
    	float top,      //near面的top
    	float near,		//near面距离
    	float far       //far面距离
    )
    {
    	Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    
    //设置正交投影参数
    public static void setProjectOrtho
    (
    	float left,		//near面的left
    	float right,    //near面的right
    	float bottom,   //near面的bottom
    	float top,      //near面的top
    	float near,		//near面距离
    	float far       //far面距离
    )
    {    	
    	Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }   
   
    //获取具体物体的总变换矩阵
    public static float[] getFinalMatrix()
    {
    	float[] mMVPMatrix=new float[16];
    	Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
    
    //获取具体物体的变换矩阵
    public static float[] getMMatrix()
    {       
        return currMatrix;
    }
    
    //设置灯光位置的方法
    public static void setLightLocation(float x,float y,float z)
    {
    	lightLocation[0]=x;
    	lightLocation[1]=y;
    	lightLocation[2]=z;
    	ByteBuffer llbb = ByteBuffer.allocateDirect(3*4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFB=llbb.asFloatBuffer();
        lightPositionFB.put(lightLocation);
        lightPositionFB.position(0);
    }
    //获取摄像机朝向的矩阵
    public static float[] getCaMatrix()
    {
		return mVMatrix;
    } 
    //获取投影矩阵
    public static float[] getProjMatrix()
    {
		return mProjMatrix;
    }
    //获取摄像机矩阵的逆矩阵的方法
    public static float[] getInvertMvMatrix(){
        float[] invM = new float[16];
        Matrix.invertM(invM, 0, mVMatrix, 0);//求摄像机矩阵的逆矩阵
        return invM;//返回摄像机矩阵的逆矩阵
    }
    //将摄像机坐标系中坐标变换为世界坐标系中坐标
    public static float[] fromPtoPreP(float[] p){
        //通过逆变换，得到变换之前的点
        float[] inverM = getInvertMvMatrix();//获取摄像机矩阵的逆矩阵
        float[] preP = new float[4];//用于存储结果坐标的数组
        Matrix.multiplyMV(preP, 0, inverM, 0, new float[]{p[0],p[1],p[2],1}, 0);//求世界坐标系中的坐标
        return new float[]{preP[0],preP[1],preP[2]};//返回世界坐标系中的坐标
    }

    //将世界坐标系中坐标变换为物体坐标系中坐标
    public static Vector3f fromGToO(Vector3f v,float[] m){
        //通过逆变换，得到变换之前的点
        float[] invM = new float[16];//用于存放矩阵元素的数组
        Matrix.invertM(invM, 0, m, 0);//求基本变换矩阵的逆矩阵
        float[] preP = new float[4];//用于存储结果坐标的数组
        Matrix.multiplyMV(preP, 0, invM, 0, new float[]{v.x,v.y,v.z,1}, 0);//求物体坐标系中的坐标
        return new Vector3f(preP[0],preP[1],preP[2]);//返回物体坐标系中的坐标
    }

    //求气泡坐标
    public static float []getBubblePoint(float [] verticsBubble)
    {
        float[] mMVPMatrix=MatrixState.getFinalMatrix();
        float [] vertics=new float[4];
        Matrix.multiplyMV(vertics,0,mMVPMatrix,0,verticsBubble,0);
        for(int i=0;i<4;i++)
        {
            vertics[i]/=vertics[3];
        }
//        System.out.println("vertics[0]="+vertics[0]+"   vertics[1]="+vertics[1]+"   vertics[2]="+vertics[2]+"   vertics[3]="+vertics[3]);
        return vertics;
    }
}
