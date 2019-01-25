package com.tcpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 图片文件接收-服务器端
 */
public class ServerFTP {

    public static void main(String[] args) {
        String str = ServerFTP.class.getResource("").toString();
        String[] temp = str.split("WEB-INF");
        String[] temp1 = temp[0].split("file:/");
        String path = temp1[1]+"pic";
        //path.replace("\\","\\/\\/");
        String finalPath = path.replace("/","\\\\");
        System.out.println(finalPath);
        /*try {
            ServerSocket server = new ServerSocket(7800);
            while(true){
                Socket s = server.accept();//阻塞方法
                //只负责和客户端进行握手
                new Thread(new  UploadThread(s) ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void start(){
        try {
            ServerSocket server = new ServerSocket(7799);
            System.out.println("FTP上传服务器已就绪！！！:");
            while(true){
                Socket s = server.accept();//阻塞方法
                //只负责和客户端进行握手
                new Thread(new  UploadThread(s) ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class UploadThread implements Runnable{
    private Socket s;
    public UploadThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {

        String ip = s.getInetAddress().getHostAddress();
        System.out.println(ip+"...get！！！");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String data = reader.readLine();
            //String data = "test.jpg";
            /*String[] tempData = d.split("jpg");
            String data = tempData[0]+"jpg";
            System.out.println(data);
            Thread.sleep(1000 * 10);*/

            BufferedInputStream bin = new BufferedInputStream(s.getInputStream());

            String str = ServerFTP.class.getResource("").toString();
            String[] temp = str.split("WEB-INF");
            String[] temp1 = temp[0].split("file:/");
            String path = temp1[1]+"pic";
            String finalPath = path.replace("/","\\\\");
            //String finalPath = "F:\\myPic";
            System.out.println(finalPath);

            File dir = new File(finalPath);
            System.out.println(dir);
            if(!dir.exists()){
                dir.mkdir();//文件夹不存在,创建mypic文件夹
            }

            //int count=1;
            File file = new File(dir, data.trim());

            while(file.exists()){
                file = new File(dir,data.trim()); //带号的文件名
            }

            FileOutputStream fout = new FileOutputStream(file);

            //从socket流中读取数据，存储到本地文件。相当于对拷
            byte buf[] = new byte[1024];
            int len=0;
            int count1 = 0;
            while( (len=bin.read(buf))!=-1){
                fout.write(buf, 0, len);
                //System.out.println("loading！！！"+count1+++"==="+data);
            }
            System.out.println("传输完成！！！");
            //图片接收完毕

            //向客户端发送回馈信息
            OutputStream out = s.getOutputStream();
            out.write( "success!!!".getBytes() );
            Thread.sleep(300);
            fout.close();
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
