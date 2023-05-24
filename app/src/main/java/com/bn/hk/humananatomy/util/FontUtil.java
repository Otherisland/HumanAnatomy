package com.bn.hk.humananatomy.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.bn.hk.humananatomy.object.LoadedObjectVertexNormalTexture;
import static com.bn.hk.humananatomy.constant.Constant.CONTENT_BG_WIDTH;

public class FontUtil 
{
	LoadedObjectVertexNormalTexture obj;
	public static float textSize=54;//字体的大小
	static String content="";
	public FontUtil(LoadedObjectVertexNormalTexture obj)
	{
		this.obj=obj;
		content=obj.getName();
	}

	public static Bitmap generateWLT(String str,int width,int height)
	{//生成文本纹理图的方法
		Paint paint=new Paint();//创建画笔对象
		String fontName="宋体";
		Typeface font=Typeface.create(fontName,Typeface.NORMAL);
		paint.setARGB(255, 255,255,255);//设置画笔颜色
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);//打开抗锯齿，使字体边缘光滑
		Bitmap bmTemp=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmTemp);
		if(str.length()>8)//读取到的为介绍文字
		{
			textSize=45;
			paint.setTextSize(textSize);//设置字体大小
			paint.setTypeface(font);
			int count=(int)((CONTENT_BG_WIDTH -96)/textSize)+1;//每行文字数量
			String strtemp[]=new String[str.length()/count+1];
			int j=0,i=0;
			while(j<str.length())
			{
				if((str.length()-j)<count)
				{
					strtemp[i]=str.substring(j,str.length());
				}else
				{
					strtemp[i]=str.substring(j,j+count);
				}
				j=j+count;
				i++;
			}
			for(int a=0;a<strtemp.length;a++)
			{
				if (strtemp[a]!=null)
				{
					canvasTemp.drawText(strtemp[a]+"", 0, (textSize+5)*(a+1)+a*5, paint);
				}
			}
		}else
		{
			textSize=54;
			paint.setTextSize(textSize);//设置字体大小
			paint.setTypeface(font);
			if(str!=null)
			{
				canvasTemp.drawText(str, 0, textSize, paint);
			}
		}
		return bmTemp;//返回绘制的作为纹理图的位图
	}
}