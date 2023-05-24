package com.bn.hk.humananatomy.object;
//一般纹理绘制类

import android.opengl.GLES30;
import com.bn.hk.humananatomy.auto.ScreenScaleUtil;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.util.MatrixState;
import com.bn.hk.humananatomy.util.ShaderUtil;
import com.bn.hk.humananatomy.view.ShowView;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//纹理三角形
public class TexDrawer
{
    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maTexCoorHandle; //顶点纹理坐标属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer   mTexCoorBuffer;//顶点纹理坐标数据缓冲
    int vCount=0;

    public TexDrawer(ShowView mv)
    {
        //初始化顶点数据的方法
        initVertexData();
        //初始化着色器的方法
        initShader(mv);
    }

    //初始化顶点数据的方法
    public void initVertexData()
    {
        //顶点坐标数据的初始化================begin============================
        vCount=6;

        float [] vertices=new float[]
        {
             0,0,0,  0,-1,0,
             1,-1,0, 0,0,0,
             1,-1,0, 1,0,0
         };
        //顶点纹理坐标数据的初始化================begin============================
        float [] texCoor=new float[]
        {
                0,0, 0,1, 1,1,
                0,0, 1,1, 1,0
        };

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

        //顶点纹理坐标数据的初始化================begin============================
        //创建顶点纹理坐标数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer.put(texCoor);//向缓冲区中放入顶点纹理数据
        mTexCoorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理坐标数据的初始化================end============================

    }

    //初始化着色器
    public void initShader(ShowView mv)
    {
        //加载顶点着色器的脚本内容
        mVertexShader= ShaderUtil.loadFromAssetsFile(Constant.shaders+"vertex_tex_at.sh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader=ShaderUtil.loadFromAssetsFile(Constant.shaders+"frag_tex_at.sh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf(int texId,int x,int y,int width,int height)
    {
        //开启混合
        GLES30.glEnable(GLES30.GL_BLEND);			//开启混合
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,	//设置混合因子
              			   GLES30.GL_ONE_MINUS_SRC_ALPHA);

        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);

        float wScale=ScreenScaleUtil.fromPixSizeToScreenSize(width, Constant.ssr);
        float hScale= ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw=ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw=ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();
        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.scale(wScale,hScale,1.0f);

        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //将顶点纹理坐标数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2*4,
                        mTexCoorBuffer
                );
        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);//启用顶点纹理坐标数据
        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//设置使用的纹理编号
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//绑定指定的纹理id
        //以三角形的方式填充
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);			//关闭混合
    }
}
