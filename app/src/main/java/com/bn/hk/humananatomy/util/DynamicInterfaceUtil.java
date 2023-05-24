package com.bn.hk.humananatomy.util;

import com.bn.hk.humananatomy.activity.WelcomeActivity;
import com.bn.hk.humananatomy.inteface.Category;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_D;
import static com.bn.hk.humananatomy.constant.Constant.categoryGroup_R;
import static com.bn.hk.humananatomy.constant.Constant.categorySever;
import static com.bn.hk.humananatomy.constant.Constant.unlockArray;
import static com.bn.hk.humananatomy.constant.Constant.unlockChapter;

public class DynamicInterfaceUtil {

    public void loadFromSDFile() {
            String filePath;
            filePath = WelcomeActivity.RootPath + "/HumanAnatomy/data/descriptive_anatomy/main.txt";
            token(filePath,0);
            filePath = WelcomeActivity.RootPath + "/HumanAnatomy/data/regional_anatomy/main.txt";
            token(filePath,1);
            getunLockInfor();

    }
    public void getunLockInfor()
    {
        for(int j=0;j<categoryGroup_D.size();j++)
        {
            Category categoryp=categoryGroup_D.get(j);

            for(int i=0;i<categoryp.categoryChildPic.size();i++)
            {
                unlockChapter.put(categoryp.categoryChildPic.get(i),categoryp.categoryChildName.get(i).charAt(0));
            }
        }
        for(int j=0;j<categoryGroup_R.size();j++)
        {
            Category categoryp=categoryGroup_R.get(j);

            for(int i=0;i<categoryp.categoryChildPic.size();i++)
            {
                unlockChapter.put(categoryp.categoryChildPic.get(i),categoryp.categoryChildName.get(i).charAt(0));
            }
        }
        Iterator iter = unlockChapter.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
           // System.out.println("key  :::"+key+"    val   "+val);
        }
        groupUnlockInfor();

    }
    public void groupUnlockInfor()
    {
        unlockArray[0]=unlockChapter.get("arthrology");
        unlockArray[1]=unlockChapter.get("osteology");
        unlockArray[2]=unlockChapter.get("myology");
        unlockArray[3]=unlockChapter.get("respiratory");
        unlockArray[4]=unlockChapter.get("alimentary");
        unlockArray[5]=unlockChapter.get("urinary");
        unlockArray[6]=unlockChapter.get("cardiovascular");
        unlockArray[7]=unlockChapter.get("lymphatic");
        unlockArray[8]=unlockChapter.get("visualizer");
        unlockArray[9]=unlockChapter.get("peripheralnervous");
        unlockArray[10]=unlockChapter.get("centralnervous");
        unlockArray[11]=unlockChapter.get("adrenalgland");
        unlockArray[12]=unlockChapter.get("headZ");
        unlockArray[13]=unlockChapter.get("neckZ");
        unlockArray[14]=unlockChapter.get("chestZ");
        unlockArray[15]=unlockChapter.get("abdomenZ");
        unlockArray[16]=unlockChapter.get("pelvinaZ");
        unlockArray[17]=unlockChapter.get("vertebralisZ");
        unlockArray[18]=unlockChapter.get("upperlimbsZ");
        unlockArray[19]=unlockChapter.get("lowerlimbsZ");
        System.out.println();
        for(int i=0;i<unlockArray.length;i++)
        {

            System.out.print(unlockArray[i]);

        }
        System.out.println();
    }
    public void token(String filePath,int index) {
        try {
            String result = null;
            //System.out.println(filePath);
            File f = new File(filePath);
            int length = (int) f.length();
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, "utf-8");
            result = result.replaceAll("\\r\\n", "\n");
            System.out.println(result);
            tokenResult(result,index);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void tokenResult(String result,int index)
    {
        StringTokenizer st = new StringTokenizer(result, "\n"); //切成逐行系统和子类串
        while (st.hasMoreElements()) {
            Category category = new Category();
            String ss = (String) st.nextElement();
            StringTokenizer st1 = new StringTokenizer(ss, "#"); //切成系统和子类两个类别

            String ss1 = (String) st1.nextElement();

            String categoryInfor = ss1;                     //得到该系统的信息串

            StringTokenizer st3 = new StringTokenizer(categoryInfor, "_"); //切成名字和图片、解锁两个类别
            String categoryName = (String) st3.nextElement();
            //System.out.println(categoryName);
            category.categoryName = categoryName;
            String categoryPicL = (String) st3.nextElement();
            //System.out.println(categoryPicL);
            category.categoryPic = categoryPicL.substring(0, categoryPicL.length() - 1);
            //System.out.println(category.categoryPic);
            category.categoryLock = categoryPicL.charAt(categoryPicL.length() - 1);
            //System.out.println(category.categoryLock);


            if (st1.hasMoreElements()) {
                String categoryChildGroup = (String) st1.nextElement();
                StringTokenizer st2 = new StringTokenizer(categoryChildGroup, "|"); //切该系统的子类信息串
                while (st2.hasMoreElements()) {
                    String categoryChildInfor = (String) st2.nextElement(); //得到该系统的子类信息串
                    //      System.out.println(categoryChild);
                    StringTokenizer st4 = new StringTokenizer(categoryChildInfor, "_"); //切成名字和图片、解锁两个类别
                    String categoryChildName = (String) st4.nextElement();
                    //System.out.println(categoryChildName);
                    category.categoryChildName.add(categoryChildName);

                    String categoryChildPicL = (String) st4.nextElement();
                    //System.out.println(categoryChildPicL);
                    String categoryChildPic = categoryChildPicL.substring(0, categoryChildPicL.length() - 1);
                    char categoryChildLock = categoryChildPicL.charAt(categoryChildPicL.length() - 1);
                    category.categoryChildPic.add(categoryChildPic);
                    category.categoryChildLock.add(categoryChildLock);
                }
            }
            if(index==0){
                categoryGroup_D.add(category);
            } else if(index==1) {
                categoryGroup_R.add(category);
            }else if(index==2)
            {
                categorySever.add(category);
            }
        }
    }
}
