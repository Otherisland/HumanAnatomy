package com.bn.hk.humananatomy.thread;

import com.bn.hk.humananatomy.object.LoadedObjectVertexNormalTexture;

public class ObjectAlphaThread extends Thread {
    float uTime=1;
    int flag=1;
    LoadedObjectVertexNormalTexture obj;
    public ObjectAlphaThread(LoadedObjectVertexNormalTexture obj)
    {
        this.obj=obj;
    }
    public void run()
    {
        while(obj.isChosen)
        {
            //System.out.println(uTime);
            if(uTime>=1f){flag=1;}
            else if(uTime<=0.2f){flag=-1;}
            uTime-=flag*0.03f;
            obj.setuTime(uTime);
            try
            {
                Thread.sleep(30);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        uTime=1f;
        obj.setuTime(uTime);
    }
}
