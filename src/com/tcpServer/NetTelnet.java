package com.tcpServer;  
import java.io.InputStream;  
import java.io.PrintStream; 
import org.apache.commons.net.telnet.TelnetClient;  
 
public class NetTelnet {  
    private TelnetClient telnet = new TelnetClient();  
    private InputStream in;  
    private PrintStream out;  
    private char prompt = '$';  

    // 普通用户结束  
    public NetTelnet(String ip, int port, String user, String password) {  
        try {  
            telnet.connect(ip, port);  
            in = telnet.getInputStream();  
            out = new PrintStream(telnet.getOutputStream());  
            // 根据root用户设置结束符  
            this.prompt = user.equals("root") ? '#' : '$';  
            login(user, password);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    /** * 登录 * * @param user * @param password */  
    public void login(String user, String password) {  
        readUntil("login:");  
        write(user);  
        readUntil("Password:");  
        write(password);  
        readUntil(prompt + " ");  
    }  

    /** * 读取分析结果 * * @param pattern * @return */  
    public String readUntil(String pattern) {  
        try {  
            char lastChar = pattern.charAt(pattern.length() - 1);  
            StringBuffer sb = new StringBuffer();  
            char ch = (char) in.read();  
            while (true) {  
                sb.append(ch);  
                if (ch == lastChar) {  
                    if (sb.toString().endsWith(pattern)) {  
                        return sb.toString();  
                    }  
                }  
                ch = (char) in.read();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  

    /** * 写操作 * * @param value */  
    public void write(String value) {  
        try {  
            out.println(value);  
            out.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    /** * 向目标发送命令字符串 * * @param command * @return */  
    public String sendCommand(String command) {  
        try {  
            write(command);  
            return readUntil(prompt + " ");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  

    /** * 关闭连接 */  
    public void disconnect() {  
        try {  
            telnet.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    public static void main(String[] args) {  
        try {  
            System.out.println("启动Telnet...");  
            String ip = "10.1.126.3";  
            int port = 23;  
            String user = "root";  
            String password = "2160i2015";  
            NetTelnet telnet = new NetTelnet(ip, port, user, password);  
            telnet.sendCommand("export LANG=en"); 
            System.out.println("显示结果");
            for(int i=0;i<10;i++){
            	String r = telnet.sendCommand("uname -a");                  
                System.out.println(r);
                i++;
                Thread.sleep(2 * 1000);
            }           
            System.out.println("结束");
            telnet.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  