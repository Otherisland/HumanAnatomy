package com.bn.hk.humananatomy.object;//声明包

import com.bn.hk.humananatomy.util.Vector3f;

//物体的AABB包围盒
public class AABB
{
	public float minX;
	public float maxX;
	public float minY;
	public float maxY;
	public float minZ;
	public float maxZ;
	public float radius;

	public AABB(float minXIn, float maxXIn, float minYIn, float maxYIn, float minZIn, float maxZIn, float radius)
	{
		minX=minXIn;
		maxX=maxXIn;
		minY=minYIn;
		maxY=maxYIn;
		minZ=minZIn;
		maxZ=maxZIn;
		this.radius=radius;
	}

	//和参数射线的相交性测试，如果不相交则返回值是一个非常大的数(大于1)
	//如果相交，返回相交时间t
	//t为0-1之间的值
	public float rayIntersect(
			Vector3f rayStart,    //射线起点
			Vector3f rayEnd        //射线终点
	){

		//使用参数方程 P=P起点+t*Pd  (其中Pd为终点减去起点得到的差值)
		//解开成三个轴的参数方程则为
		//X=X起点+t*Xd  (Xd=X终点-X起点)
		//Y=Y起点+t*Yd  (Yd=Y终点-Y起点)
		//Z=Z起点+t*Zd  (Zd=Z终点-Z起点)
		//有效的t应该在（0～1之间）

		//如果未相交则返回这个大数
		float t = Float.POSITIVE_INFINITY;

		//计算射线段是否与 X=min.x 平面相交
		//计算射线段与X=min.x 平面相交的交点t值
		float tempT=(minX-rayStart.x)/(rayEnd.x-rayStart.x);
		//若t值在0～1的范围内
		if(tempT>=0&&tempT<=1)
		{
			//计算出射线段与X=min.x 平面的交点Y、Z坐标
			float y=rayStart.y+tempT*(rayEnd.y-rayStart.y);
			float z=rayStart.z+tempT*(rayEnd.z-rayStart.z);
			//考察交点Y、Z坐标是否在包围盒此面的范围内
			if(y>=minY&&y<=maxY&&z>=minZ&&z<=maxZ)
			{
				//若交点在包围盒此面的范围内则考察当前交点值是否小于原先t值
				//若小于则说明此交点离摄像机更近，则更新t值
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}

		//下面几个分支的注释 我没有详细写 和上面的分支相同，写书稿时可以补足 并省略一些分支 因为套路相同

		//计算射线段是否与 X=max.x 平面相交
		tempT=(maxX-rayStart.x)/(rayEnd.x-rayStart.x);
		if(tempT>=0&&tempT<=1)
		{
			float y=rayStart.y+tempT*(rayEnd.y-rayStart.y);
			float z=rayStart.z+tempT*(rayEnd.z-rayStart.z);
			if(y>=minY&&y<=maxY&&z>=minZ&&z<=maxZ)
			{
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}

		//计算射线段是否与 Y=min.y 平面相交
		tempT=(minY-rayStart.y)/(rayEnd.y-rayStart.y);
		if(tempT>=0&&tempT<=1)
		{
			float x=rayStart.x+tempT*(rayEnd.x-rayStart.x);
			float z=rayStart.z+tempT*(rayEnd.z-rayStart.z);
			if(x>=minX&&x<=maxX&&z>=minZ&&z<=maxZ)
			{
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}

		//计算射线段是否与 Y=max.y 平面相交
		tempT=(maxY-rayStart.y)/(rayEnd.y-rayStart.y);
		if(tempT>=0&&tempT<=1)
		{
			float x=rayStart.x+tempT*(rayEnd.x-rayStart.x);
			float z=rayStart.z+tempT*(rayEnd.z-rayStart.z);
			if(x>=minX&&x<=maxX&&z>=minZ&&z<=maxZ)
			{
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}

		//计算射线段是否与 Z=min.z 平面相交
		tempT=(minZ-rayStart.z)/(rayEnd.z-rayStart.z);
		if(tempT>=0&&tempT<=1)
		{
			float x=rayStart.x+tempT*(rayEnd.x-rayStart.x);
			float y=rayStart.y+tempT*(rayEnd.y-rayStart.y);
			if(x>=minX&&x<=maxX&&y>=minY&&y<=maxY)
			{
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}

		//计算射线段是否与 Z=max.z 平面相交
		tempT=(maxZ-rayStart.z)/(rayEnd.z-rayStart.z);
		if(tempT>=0&&tempT<=1)
		{
			float x=rayStart.x+tempT*(rayEnd.x-rayStart.x);
			float y=rayStart.y+tempT*(rayEnd.y-rayStart.y);
			if(x>=minX&&x<=maxX&&y>=minY&&y<=maxY)
			{
				if(tempT<t)
				{
					t=tempT;
				}
			}
		}
		return t;//返回相交点参数值
	}
}
