package com.bn.hk.humananatomy.object;//声明包

import android.opengl.GLES30;

import com.bn.hk.humananatomy.activity.ShowActivity;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.util.MatrixState;
import com.bn.hk.humananatomy.util.ShaderUtil;
import com.bn.hk.humananatomy.util.Vector3f;
import com.bn.hk.humananatomy.view.ShowView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.bn.hk.humananatomy.constant.Constant.hm_DBegin;
import static com.bn.hk.humananatomy.constant.Constant.hm_DLength;
import static com.bn.hk.humananatomy.constant.Constant.tData;

//加载后的物体——仅携带顶点信息，颜色随机
public class LoadedObjectVertexNormalTexture extends TouchableObject
{	
	int mProgram;//自定义渲染管线着色器程序id  
    int muMVPMatrixHandle;//总变换矩阵引用
    int muMMatrixHandle;//位置、旋转变换矩阵
    int maPositionHandle; //顶点位置属性引用  
    int maNormalHandle; //顶点法向量属性引用  
    int maLightLocationHandle;//光源位置属性引用  
    int maCameraHandle; //摄像机位置属性引用 
    int maTexCoorHandle; //顶点纹理坐标属性引用
    int muAlphaHandle;//半透明属性引用
    int muTimeHandle;//呼吸属性引用
    int vCount=0;
    int muColorHandle;//顶点颜色
    float uTime=1;//呼吸参数

    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
	FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	FloatBuffer mNormalBuffer;//顶点法向量数据缓冲
	FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲
    public LoadedObjectVertexNormalTexture(ShowView sv, float[] vertices, float[] normals, float texCoors[], String fname)
    {
    	//初始化顶点坐标、法向量、纹理坐标数据
    	initVertexData(vertices,normals,texCoors);
    	//初始化着色器      
    	initShader(sv);
        if(!fname.equals("body"))
        {
            createAABB(this,fname);//下面获得输入流时需要用到ShowActivity的对象sa
        }
    }

    public void setuTime(float uTime)
    {
        this.uTime=uTime;
    }
    public void createAABB(LoadedObjectVertexNormalTexture loadObj, String fname) {

        int length= hm_DLength.get(fname);
        int beginIndex= hm_DBegin.get(fname);
        float x=0,y=0,z=0,radius=0;
        for(int i=0;i<length;i+=5)
        {
            x=Float.parseFloat(tData[beginIndex+i+1]);
            y=Float.parseFloat(tData[beginIndex+i+2]);
            z=Float.parseFloat(tData[beginIndex+i+3]);
            radius=Float.parseFloat(tData[beginIndex+i+4]);
            Vector3f center=new Vector3f(x,y,z);
            loadObj.createAABBArray(center,radius);
        }
        setName(fname);
    }
    public void createAABBArray(Vector3f center,float radius)
    {
        //创建AABB包围盒
        float minX = center.x-radius;
        float maxX = center.x+radius;
        float minY = center.y-radius;
        float maxY = center.y+radius;
        float minZ = center.z-radius;
        float maxZ = center.z+radius;

        preBox.add(new AABB(minX, maxX, minY, maxY, minZ, maxZ,radius));
        curCount++;
    }
    
	//初始化顶点坐标、法向量、纹理坐标数据的方法
    public void initVertexData(float[] vertices,float[] normals,float texCoors[])
    {
    	//顶点坐标数据的初始化================begin============================
    	vCount=vertices.length/3;   
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================
        
        //顶点法向量数据的初始化================begin============================  
        ByteBuffer cbb = ByteBuffer.allocateDirect(normals.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mNormalBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mNormalBuffer.put(normals);//向缓冲区中放入顶点法向量数据
        mNormalBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
        
        //顶点纹理坐标数据的初始化================begin============================  
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = tbb.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer.put(texCoors);//向缓冲区中放入顶点纹理坐标数据
        mTexCoorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理坐标数据的初始化================end============================
    }

    //初始化shader
    public void initShader(ShowView sv)
    {
    	//加载顶点着色器的脚本内容
        mVertexShader=ShaderUtil.loadFromAssetsFile(Constant.shaders+"vertex.sh", sv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile(Constant.shaders+"frag.sh", sv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用  
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        //获取位置、旋转变换矩阵引用
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");
        //获取程序中光源位置引用
        maLightLocationHandle= GLES30.glGetUniformLocation(mProgram, "uLightLocation");
        //获取程序中顶点纹理坐标属性引用  
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //获取程序中摄像机位置引用
        maCameraHandle= GLES30.glGetUniformLocation(mProgram, "uCamera");
        muColorHandle=GLES30.glGetUniformLocation(mProgram, "aColor");
        //获取透明属性因子引用
        muAlphaHandle=GLES30.glGetUniformLocation(mProgram, "uAlpha");
        //获取呼吸属性因子引用
        muTimeHandle=GLES30.glGetUniformLocation(mProgram,"uTime");
    }
    //绘制加载物体的方法
    public void drawSelf(int texId)
    {
        copyM();
    	 //制定使用某套着色器程序
    	 GLES30.glUseProgram(mProgram);
         //将最终变换矩阵传入着色器程序
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
         //将位置、旋转变换矩阵传入着色器程序
         GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);
         //将光源位置传入着色器程序   
         GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         //将摄像机位置传入着色器程序   
         GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         //将半透明参数传入着色器程序
        GLES30.glUniform1i(muAlphaHandle,uAlpha);
        //将呼吸参数传入着色器程序
        GLES30.glUniform1f(muTimeHandle,uTime);
         // 将顶点位置数据传入渲染管线
         GLES30.glVertexAttribPointer
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT,
         		false,
                3*4,   
                mVertexBuffer
         );       
         //将顶点法向量数据传入渲染管线
         GLES30.glVertexAttribPointer
         (
        		maNormalHandle, 
         		3,   
         		GLES30.GL_FLOAT,
         		false,
                3*4,   
                mNormalBuffer
         );   
         //将顶点纹理坐标数据传入渲染管线
         GLES30.glVertexAttribPointer
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT,
         		false,
                2*4,   
                mTexCoorBuffer
         );
        //传入顶点颜色数据
        GLES30.glUniform4fv(muColorHandle, 1, color, 0);
         //启用顶点位置、法向量、纹理坐标数据数组
         GLES30.glEnableVertexAttribArray(maPositionHandle);
         GLES30.glEnableVertexAttribArray(maNormalHandle);
         GLES30.glEnableVertexAttribArray(maTexCoorHandle); //启用纹理坐标数据数组
         //绑定纹理
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//启用0号纹理
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//绑定纹理
         //绘制加载的物体
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
