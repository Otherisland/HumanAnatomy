package com.bn.hk.humananatomy.auto;

//计算缩放情况的工具类
public class ScreenScaleUtil
{
	static final float sHpWidth=1920;//原始横屏的宽度
	static final float sHpHeight=1080;//原始横屏的高度
	static final float whHpRatio=sHpWidth/sHpHeight;//原始横屏的宽高比


	static final float sSpWidth=1080;//原始竖屏的宽度
	static final float sSpHeight=1920;//原始竖屏的高度
	static final float whSpRatio=sSpWidth/sSpHeight;//原始竖屏的宽高比


	public static ScreenScaleResult calScale
			(
				 float targetWidth,	//目标宽度
					float targetHeight	//目标高度
			)
	{
		ScreenScaleResult result=null;
		ScreenOrien so=null;

		//首先判断目标是横屏还是竖屏
		if(targetWidth>targetHeight)
		{
			so=ScreenOrien.HP;
		}
		else
		{
			so=ScreenOrien.SP;
		}

		//System.out.println(so);


		//进行横屏结果的计算
		if(so==ScreenOrien.HP)
		{
			//计算目标的宽高比
			float targetRatio=targetWidth/targetHeight;

			if(targetRatio>whHpRatio)
			{
				//若目标宽高比大于原始宽高比则以目标的高度计算结果
				float ratio=targetHeight/sHpHeight;
				float realTargetWidth=sHpWidth*ratio;
				float lcuX=(targetWidth-realTargetWidth)/2.0f;
				float lcuY=0;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so,(int)(sHpWidth*ratio),(int)(sHpHeight*ratio));
			}
			else
			{
				//若目标宽高比小于原始宽高比则以目标的宽度计算结果
				float ratio=targetWidth/sHpWidth;
				float realTargetHeight=sHpHeight*ratio;
				float lcuX=0;
				float lcuY=(targetHeight-realTargetHeight)/2.0f;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so,(int)(sHpWidth*ratio),(int)(sHpHeight*ratio));
			}
		}

		//进行竖屏结果的计算
		if(so==ScreenOrien.SP)
		{
			//计算目标的宽高比
			float targetRatio=targetWidth/targetHeight;

			if(targetRatio>whSpRatio)
			{
				//若目标宽高比大于原始宽高比则以目标的高度计算结果
				float ratio=targetHeight/sSpHeight;
				float realTargetWidth=sSpWidth*ratio;
				float lcuX=(targetWidth-realTargetWidth)/2.0f;
				float lcuY=0;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so,(int)(sSpWidth*ratio),(int)(sSpHeight*ratio));
			}
			else
			{
				//若目标宽高比小于原始宽高比则以目标的宽度计算结果
				float ratio=targetWidth/sSpWidth;
				float realTargetHeight=sSpHeight*ratio;
				float lcuX=0;
				float lcuY=(targetHeight-realTargetHeight)/2.0f;
				result=new ScreenScaleResult((int)lcuX,(int)lcuY,ratio,so,(int)(sSpWidth*ratio),(int)(sSpHeight*ratio));
			}
		}
		return result;
	}

	//目标屏幕的触控点转为原始屏幕触控点的方法
	public static int[] touchFromTargetToOrigin(int x,int y,ScreenScaleResult ssr)
	{
		int[] result=new int[2];

		result[0]=(int) ((x-ssr.lucX)/ssr.ratio);
		result[1]=(int) ((y-ssr.lucY)/ssr.ratio);

		return result;
	}

	public static float fromPixSizeToScreenSize(float screenSize,ScreenScaleResult ssr)
	{
		if(ssr.so==ScreenOrien.HP)
		{
			return screenSize/(sHpHeight/2);
		}
		else
		{
			return screenSize/(sSpHeight/2);
		}
	}

	public static float from2DZBTo3DZBX(float x,ScreenScaleResult ssr)
	{
		if(ssr.so==ScreenOrien.HP)
		{
			return (x-(sHpWidth/2))/(sHpWidth/2)*(sHpWidth/sHpHeight);
		}
		else
		{
			return (x-(sSpWidth/2))/(sSpWidth/2)*(sSpWidth/sSpHeight);
		}

	}

	public static float from2DZBTo3DZBY(float y,ScreenScaleResult ssr)
	{
		if(ssr.so==ScreenOrien.HP)
		{
			return 	((sHpHeight/2)-y)/(sHpHeight/2);
		}
		else
		{
			return 	((sSpHeight/2)-y)/(sSpHeight/2);
		}
	}
}