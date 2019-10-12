package com.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {

    public static boolean ping(String ipAddress) throws Exception {
        int timeOut = 1000;  //超时应该在3钞以上
        // 当返回值是true时，说明host是可用的，false则不可。
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        return status;
    }

    public static void ping02(String ipAddress) throws Exception {
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
            while ((line = buf.readLine()) != null)
                System.out.println(line);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        try {   // 执行命令并获取输出
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    /**
     *      * Purpose:ping host或者 port
     *      * @author Hermanwang
     *      * @param ipAddress:ip
     *      * @param port:port/host
     *      * @throws Exception
     *      * @return boolean
     *     
     */
    public static boolean pingHost(String ipAddress, int port) throws Exception {
        boolean isReachable = false;
        Socket connect = new Socket();
        try {
            InetSocketAddress endpointSocketAddr = new InetSocketAddress(ipAddress, port);
            //此处3000是超时时间,单位 毫秒
            connect.connect(endpointSocketAddr, 2000);
            isReachable = connect.isConnected();
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", ip = " + ipAddress + ", port = " + port);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage() + ", ip = " + ipAddress + ", port = " + port);
                }
            }
        }
        return isReachable;
    }

    public static void main(String[] args) throws Exception {

    }
}
