package com.bn.hk.humananatomy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import static com.bn.hk.humananatomy.constant.Constant.baseSDPath;
import static com.bn.hk.humananatomy.constant.Constant.unlockArray;

public class SynchronizeUtil {
    String userId;
    public SynchronizeUtil(String userId)
    {
        this.userId=userId;
    }
    public SynchronizeUtil(){}
    public void updateMaintxt()
    {
        char []descriptive_unlock=new char[12];
        char []regional_unlock=new char[8];
        for(int i=0;i<unlockArray.length;i++)
        {
            if(i<12)
            {
                descriptive_unlock[i]=unlockArray[i];
            }else{
                regional_unlock[i-12]=unlockArray[i];
            }
        }
        updateTxt(0,descriptive_unlock);
        updateTxt(1,regional_unlock);
    }
    public void updateLockInfo(char []unlock)
    {
        char []descriptive_unlock=new char[12];
        char []regional_unlock=new char[8];
        for(int i=0;i<unlock.length;i++)
        {
            if(i<12)
            {
                descriptive_unlock[i]=unlock[i];
            }else{
                regional_unlock[i-12]=unlock[i];
            }
        }

        updateTxt(0,descriptive_unlock);
        updateTxt(1,regional_unlock);
    }
    public static void updateTxt(int index,char []unlock) {
        String filePath = baseSDPath[index] + "main.txt";
        try {
            String result = null;
            // System.out.println(filePath);
            File f = new File(filePath);
            int length = (int) f.length();
            byte[] buff = new byte[length];
            FileInputStream fin = new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result = new String(buff, "utf-8");
            result = result.replaceAll("\\r\\n", "\n");
            System.out.println(result);
            String finalResult="";
            for(int i=0,j=0;i<result.length();i++)
            {
                if(result.charAt(i)=='#'||result.charAt(i)=='|')
                {
                    finalResult+=result.charAt(i);
                    finalResult+=unlock[j];
                    i++;
                    j++;

                }else {
                    finalResult+=result.charAt(i);
                }
            }
            System.out.println(finalResult);

          /* 写入Txt文件 */
            File writename = new File(filePath); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(finalResult); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
