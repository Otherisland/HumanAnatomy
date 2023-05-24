package com.bn.hk.humananatomy.auto;

public class Main
{
	public static void main(String args[])
	{
		ScreenScaleResult ssr=ScreenScaleUtil.calScale(640,400);
		//System.out.println("1920,1080");
		//System.out.println(ssr);
		
		int[] after=ScreenScaleUtil.touchFromTargetToOrigin(640, 40, ssr);
		//System.out.println(after[0]+"|"+after[1]);
	}
}