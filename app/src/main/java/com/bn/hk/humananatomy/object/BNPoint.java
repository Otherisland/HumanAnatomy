package com.bn.hk.humananatomy.object;

public class BNPoint {
    /*float oldX;
    float oldY;*/
    boolean hasOld=false;
    public float x,y;
    public BNPoint(float x, float y)
    {
        this.x=x;
        this.y=y;
    }
    public void setLocation(float x,float y)//设置触控点的新位置
    {
       /* oldX=this.x;
        oldY=this.y;*/
        hasOld=true;
        this.x=x;
        this.y=y;
    }
    public static float calDistance(BNPoint a,BNPoint b)
    {
        float result=0;
        result=(float)Math.sqrt((a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y));
        return result;
    }
}
