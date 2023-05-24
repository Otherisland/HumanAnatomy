package com.bn.hk.humananatomy.util;

import com.bn.hk.humananatomy.constant.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {

    public static void unZipFiles(String zipPath, String descDir,int unZipFlag) throws IOException {
        unZipFiles(new File(zipPath), descDir,unZipFlag);
    }

    /**
     * 解压文件到指定目录
     *
     * @param zipFile
     * @param descDir
     * @author isea533
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir,int unZipFlag) throws IOException {
        File pathFile = new File(descDir);//创建手机路径文件
        long length = zipFile.length();//目标文件长度
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);//创建本地路径的zip文件
        for (
                Enumeration entries = zip.entries();//得到zip文件集合
                entries.hasMoreElements(); )//
        {
            ZipEntry entry = (ZipEntry) entries.nextElement();//得到一个一个的元素
            String zipEntryName = entry.getName();//得到文件名称
            InputStream in = zip.getInputStream(entry);//创建文件的输入流对象
            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");//用/替换\\*
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));//
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            //输出文件路径信息
            OutputStream out = new FileOutputStream(outPath);//创建目标文件的输出流
            byte[] buf1 = new byte[4096];//创建字节数组
            int len;
            int current_len=0;
            while((len=in.read(buf1))>0)
            {
                current_len+=len;
                out.write(buf1,0,len);
                if(unZipFlag==1)
                {
                    Constant.PROGRESS_RATIO+=1.0*current_len/length*100*0.4;
                    Test.pp.setProgressRatio();
                }
            }
            in.close();
            out.close();

        }
        System.out.println("******************解压完毕********************");
    }
}
