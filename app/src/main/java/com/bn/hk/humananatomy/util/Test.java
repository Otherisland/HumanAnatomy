package com.bn.hk.humananatomy.util;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.bn.hk.humananatomy.activity.ShowActivity;
import com.bn.hk.humananatomy.alert.ProgressPurchase;
import com.bn.hk.humananatomy.constant.Constant;
import com.bn.hk.humananatomy.inteface.Category;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static com.bn.hk.humananatomy.constant.Constant.baseSDPath;
import static com.bn.hk.humananatomy.constant.Constant.categoryIndex;
import static com.bn.hk.humananatomy.constant.Constant.categorySever;
import static com.bn.hk.humananatomy.constant.Constant.currentModel;
import static com.bn.hk.humananatomy.constant.Constant.file_path;
import static com.bn.hk.humananatomy.constant.Constant.handler;
import static com.bn.hk.humananatomy.constant.Constant.isConnected;
import static com.bn.hk.humananatomy.constant.Constant.isExist;
import static com.bn.hk.humananatomy.constant.Constant.server_p;
import static com.bn.hk.humananatomy.constant.Constant.severPath;
import static com.bn.hk.humananatomy.constant.Constant.severPathIndex;


public class Test {

    public static ProgressPurchase pp;
    public Test() {


    }
    public Test(ProgressPurchase pp) {
        this.pp = pp;

    }
    public static void CopyAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    CopyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //将压缩包下载到本地并解压
    public void DownAndReadFile() {
        String filePath;
        String[] Path_Data = server_p.split("[/]");//服务器上的路径分割
        String Zip_Name = Path_Data[2];//资源包的名字
        String dir = baseSDPath[categoryIndex];//手机路径
        File savePath = new File(dir);//创建手机路径
        filePath=severPath[severPathIndex]+ server_p;//ip地址+服务器上的路径
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        /* //获取文件名*/
        if (isExist&&isConnected) {handler.post(
                new Runnable() {   //投给主线程处理
                    @Override
                    public void run(){pp.setProgressContext(0);}
                });
            try {
                downloadResource(savePath+"",filePath);
                int unZipFlag=1;
                //解压
                ZipUtil.unZipFiles(dir + Zip_Name, dir,unZipFlag);
                //解压完成后将原来压缩包删除
                File fileDelete = new File(dir + Zip_Name);
                if (fileDelete.exists()) {
                    fileDelete.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateTxt();
            jumpActivity();

        } else if(!isExist&&isConnected) {
            Looper.prepare();
            Toast mToast = Toast.makeText(pp.context, " ", Toast.LENGTH_SHORT);
            mToast.setText("该资源正在开发中，敬请期待！");
            mToast.show();//显示
            Looper.loop();
        }

    }
    public void downloadResource(String savePath,String filePath)
    {
        String[] urlname = filePath.split("/");
        int len = urlname.length - 1;
        String Zip_Name = urlname[len];

        try{
            File file = new File(savePath + "/" + Zip_Name);//
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream oputstream = new FileOutputStream(file);
            URL url = new URL(filePath);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            InputStream iputstream = uc.getInputStream();
            byte[] buff = new byte[4 * 1024];
            int byteRead;
            int currentRead=0;
            while((byteRead=(iputstream.read(buff)))!= -1) {
                currentRead+=byteRead;
                Constant.PROGRESS_RATIO=1.0*currentRead/uc.getContentLength()*60f;
                pp.setProgressRatio();
                oputstream.write(buff, 0, byteRead);
            }
            if (Constant.PROGRESS_RATIO <= 50) {
                Constant.PROGRESS_RATIO = 50;
            }
            //System.out.println("下载完成！！！" + Constant.PROGRESS_RATIO);
            oputstream.flush();
            iputstream.close();
            oputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void jumpActivity() {

        Intent intent = new Intent(pp.context, ShowActivity.class);
        pp.context.startActivity(intent);
        pp.builder.cancel();
        DynamicInterfaceUtil dynamicInterfaceUtil = new DynamicInterfaceUtil();
        dynamicInterfaceUtil.loadFromSDFile();
    }

    public static void updateTxt() {
        String filePath = baseSDPath[categoryIndex] + "main.txt";//本地配置文件路径
        try {
            String result;//
            File f = new File(filePath);//创建服务器上的地址
            int length = (int) f.length();//文件名对象的长度
            byte[] buff = new byte[length];//创建缓冲区//创建字节数组，准备将文件流中的数据传给字节数组
            FileInputStream fin = new FileInputStream(f);//创建文件输入流对象，指定要读取的文件对象
            fin.read(buff);//将字节流中的数据传递给字节数组
            fin.close();//关闭输入流
            result = new String(buff, "utf-8");//创建对象
            result = result.replaceAll("\\r\\n", "\n");//替换匹配项
            StringBuilder sb = new StringBuilder();// 创建字符串构建器
            sb.append(result);//字符串追加
            String finalResult = sb.toString().replace(file_path + "0", file_path + "1");//修改本地配置文件

            /* 写入Txt文件 */
            File writename = new File(filePath); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));//
            out.write(finalResult); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  boolean seeMain(Context context) {
        boolean isExist=false;
        URL url; HttpURLConnection uc;
        try {
            url = new URL(severPath[severPathIndex] + "main.txt");
            uc = (HttpURLConnection) url.openConnection();
            try{
                uc.setConnectTimeout(1000);
                uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
                uc.connect();
                isConnected=true;
              //  showToast("线路一连接成功");
            }catch (Exception e )
            {
              //  showToast("线路一连接失败，  正在尝试线路二下载");
                //severPathIndex++;
               // return false;
            }

            if(severPathIndex==1)
            {
                url = new URL(severPath[severPathIndex] + "main.txt");
                uc = (HttpURLConnection) url.openConnection();
                try{
                    uc.setConnectTimeout(1000);
                    uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
                    uc.connect();
                    isConnected=true;
                //    showToast("线路二连接成功");
                }catch (Exception e )
                {
                 //   showToast("线路二连接失败,  正在尝试线路三下载");
                    severPathIndex++;
                }
            }
            if(severPathIndex==2)
            {
                url = new URL(severPath[severPathIndex] + "main.txt");
                uc = (HttpURLConnection) url.openConnection();
                try{
                    uc.setConnectTimeout(1000);
                    uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
                    uc.connect();
                   isConnected=true;
                 //   showToast("线路三连接成功");
                }catch (Exception e )
                {
                   // showToast("线路三下载失败 ，请检查网络服务！！");
                    showToast("请检查网络服务！！",context);
                    severPathIndex=0;
                    return false;
                }
            }
            InputStream iputstream = uc.getInputStream();
            System.out.println("file size is:" + uc.getContentLength());//打印文件长度
            byte[] buffer = new byte[uc.getContentLength()];
            iputstream.read(buffer);
            String result = new String(buffer);
            result = new String(buffer, "utf-8");
            result = result.replaceAll("\\r\\n", "\n");
            System.out.println("server  :result:::::    结果：" + result);
            iputstream.close();

            categorySever.clear();
            DynamicInterfaceUtil dynamicInterfaceUtil=new DynamicInterfaceUtil();
            dynamicInterfaceUtil.tokenResult(result, 2);
          //  System.out.println("categorySever.   的大小" + categorySever.size());

            int count = 0;
            for (int i = 0; i < categorySever.size() && isExist == false; i++) {
                Category category = categorySever.get(i);
                count++;
                for (int j = 0; j < category.categoryChildPic.size(); j++) {

                   // System.out.println("currentModel   当前选中单元" + currentModel + " 与之匹配的单元：：" + category.categoryChildPic.get(j));
                    if (currentModel.equals(category.categoryChildPic.get(j))) {
                        System.out.println("匹配成功！！！！");
                        if (category.categoryChildLock.get(j).equals('0')) {
                            isExist = false;
                        } else {
                         //   System.out.println("有资源！！！！     " + category.categoryChildLock.get(j));
                            isExist = true;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接错误！！！ " );

        }
        System.out.println("testttttttttttttttttttttttt           "+isExist);
        return isExist;
    }
    public void showToast(String ss,Context context)
    {
        handler.post(new Runnable() {   //投给主线程处理
            @Override
            public void run() {
                Toast mToast = Toast.makeText(pp.context, " ", Toast.LENGTH_SHORT);
                mToast.setText(ss);
                mToast.show(); }
        });
    }
}
