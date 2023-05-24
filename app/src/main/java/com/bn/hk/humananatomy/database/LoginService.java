package com.bn.hk.humananatomy.database;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;

/*该类演示了四种方法提交数据到服务器，并将服务器解析后的数据以字符串的形式返回*/
public class LoginService {

    public static String postParameter(String data){
        return sendPost("http://192.168.43.135:8080/HumanServer/UserServlet",data);
    }
     //140.143.14.37
    //网络请求的方式之一，用Post请求获取数据
    public static String sendPost(String url,String param)
    {
        String result="";
        String text="";
        try{
            URL httpurl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            // 获取URLConnection对象对应的输出流
            PrintWriter out = new PrintWriter(httpConn.getOutputStream());
            out.print(param);//发送请求参数
            out.flush();
            int code = httpConn.getResponseCode();//检查是否正常返回数据
            System.out.println(code);
            if (code == 200) {

                System.out.println("yy   "+text);
                InputStream is = httpConn.getInputStream();
                byte[] buffer = new byte[httpConn.getContentLength()];
                is.read(buffer);
                result = new String(buffer);
                result = new String(buffer, "utf-8");
                result = result.replaceAll("\\r\\n", "\n");
                is.close();
            }

            out.close();
        }catch(Exception e){
            System.out.println("没有结果！"+e);
            e.printStackTrace();
        }

        return result;
    }
    public static void main(String args[])
    {
        String ss="67";
        //     更新用户基本信息
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        java.util.Date date = new java.util.Date();
        System.out.println(sp.format(date));
        Date dateSql = new Date(date.getTime());

        // /** 用户全局序号
        String userGlobalid=ss ;
        // /** 昵称
        String userNickname="s55s" ;
        // /** 性别
        String userSex ="woman";
        // /** 身份信息
        String userIdentity="student" ;
        String action="updateUserBasic";
        String data = "action="+URLEncoder.encode(action)
                +"&userGlobalid="+URLEncoder.encode(userGlobalid)
                +"&userNickname="+URLEncoder.encode(userNickname)
                +"&userSex="+URLEncoder.encode(userSex)
                +"&userIdentity="+URLEncoder.encode(userIdentity);
        System.out.println(postParameter(data));

    }
}
