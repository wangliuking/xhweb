package com.tcpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 图片文件的发送-客户端
 */
public class UploadPicClient {

    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);
        System.out.println("请输入图片路径：");
        String str = sc.next();*/

        String str = "F:\\31674132_21.jpg";
        File file = new File(str);
        if(!(file.exists()&& file.isFile())){
            JOptionPane.showMessageDialog(null, "文件不存在！");
            return ;
        }

        if(!(file.getName().endsWith(".jpg")||file.getName().endsWith(".gif"))){
            JOptionPane.showMessageDialog(null, "文件格式不对,文件扩展名必须是jpg或gif！");
            return ;
        }
        /*if( file.length()>=1024*1024*2){
            JOptionPane.showMessageDialog(null, "文件过大，不应超过2M，请重新上传！");
            return;
        }*/

        //上传
        try {
            Socket s = new Socket("localhost", 7799);

            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(str));

            OutputStream out = s.getOutputStream();

            byte buf[] = new byte[1024];
            int len=0;
            while((len=bin.read(buf))!=-1){
                out.write(buf, 0, len);
            }

            s.shutdownOutput();//告诉服务器，文件上传完毕

            //读取服务器的回馈信息
            InputStream in = s.getInputStream();
            byte buf2[] = new byte[1024];
            int len2 = in.read(buf2);
            System.out.println(new String(buf2, 0, len2));

            //关流
            out.close();
            bin.close();
            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
