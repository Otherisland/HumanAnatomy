package com.bn.hk.humananatomy.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.ETC1Util;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bn.hk.humananatomy.constant.Constant.picPath;


public class TexManager
{
    private static Map<String,Bitmap> tex=new HashMap<String, Bitmap>();
    public static List<String> texName=new ArrayList<String>();

    private static Bitmap initTexture( String texPath)
    {
        Bitmap bitmapTmp=null;
        try
        {
            File f=new File(texPath);
            int length=(int)f.length();
            byte[] buff=new byte[length];
            FileInputStream fin=new FileInputStream(f);
            fin.read(buff);
            fin.close();
            bitmapTmp =BitmapFactory.decodeByteArray(buff,0,buff.length);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return bitmapTmp;
    }
    public static void addTexArray(String tna[])
    {
        for(String tn:tna) {
            texName.add(tn);
        }
    }
    public static void loadTextures()
    {
        for(String tn:texName)
        {
            tex.put(tn,initTexture(picPath+tn));
        }
    }
    public static void loadSingleTexture(String tn)
    {
        tex.put(tn,initTexture(picPath+tn));
    }

    public static Bitmap getTex(String tn)
    {
        return tex.get(tn);
    }
    //生成纹理的id
    public static int createTexture(Bitmap bitmap)
    {
        //生成纹理ID
        int[] textures = new int[1];
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        int textureId=textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT);
        //实际加载纹理
        GLUtils.texImage2D
                (
                        GLES30.GL_TEXTURE_2D,   //纹理类型
                        0, 					  //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmap, 			  //纹理图像
                        0					  //纹理边框尺寸
                );
        bitmap.recycle(); 		  //纹理加载成功后释放图片
        return textureId;
    }
    public static int  initSDTextureETC1(String filePath) //textureId 压缩纹理
    {
        //生成纹理ID
        int[] textures = new int[1];
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        int textureId=textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        try
        {
            //通过输入流加载图片===============begin===================
            FileInputStream fin=new FileInputStream(filePath);
            ETC1Util.loadTexture//将纹理数据加载进纹理缓冲
                    (
                            GLES30.GL_TEXTURE_2D, //纹理类型
                            0, //纹理层次
                            0,//纹理边框尺寸
                            GLES30.GL_RGB,//色彩通道格式
                            GLES30.GL_UNSIGNED_BYTE, //每像素数据数
                            fin//压缩纹理数据输入流
                    );
            fin.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return textureId;
    }
}
