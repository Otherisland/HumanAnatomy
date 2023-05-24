package com.bn.hk.humananatomy.auto;

//缩放计算的结果
public class ScreenScaleResult
{
	public int lucX;//左上角X坐标
	public int lucY;//左上角y坐标
	public float ratio;//缩放比例
	public ScreenOrien so;//横竖屏情况
	public int skWidth;//视口宽度
	public int skHeight;//视口高度
	public float vpRatio;//视口宽高比

	public ScreenScaleResult(int lucX,int lucY,float ratio,ScreenOrien so,int skWidth,int skHeight)
	{
		this.lucX=lucX;
		this.lucY=lucY;
		this.ratio=ratio;
		this.so=so;
		this.skHeight=skHeight;
		this.skWidth=skWidth;
		this.vpRatio=1.0f*skWidth/skHeight;
	}

	public String toString()
	{
		return "lucX="+lucX+", lucY="+lucY+", ratio="+ratio+", "+so;
	}
}