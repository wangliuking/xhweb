package com.tcpServer;
import java.net.Socket; 
import java.net.InetAddress; 
import java.net.UnknownHostException; 
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStream; 
import java.io.BufferedReader; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStreamWriter;
import java.io.PrintWriter; 
import java.io.IOException; 
public class TcpClient { 
    public static void main(String[] args) throws IOException, InterruptedException { 
        try { 
            byte a[] = { 127,0,0,1 }; 
            InetAddress address = InetAddress.getByAddress(a); 
            Socket socket = new Socket(address,9001);             
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            int count = 0;
            String line;            
            
            while (true) {
            	if(count==0){
            		line = "{\"cmdtype\":\"userlogin\",\"userid\":\"test\",\"passwd\":\"123\",\"serialnumber\":\"qwertyui123\"}";
                    writer.write(line+"\n");
                    writer.flush();
            	}else if(count==1){
            		 line = "{\"cmdtype\":\"getuserinfo\",\"userid\":\"test\",\"serialnumber\":\"qwertyui123\"}";
            		 writer.write(line+"\n");
                     writer.flush();
            	}           	
				if (reader.ready()) {
					line = reader.readLine(); 
		            System.out.println("client got: " + line);				
				}
				Thread.sleep(1000 * 10);
				count++;
			}          
           /* writer.close(); 
            reader.close(); 
            socket.close();*/
            
            /*OutputStream os = socket.getOutputStream(); 
            PrintWriter out = new PrintWriter(os,true); 
            InputStream is = socket.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(is); 
            BufferedReader in = new BufferedReader(isr); 
            String line; 
            line = "{\"cmdtype\":\"userlogin\",\"userid\":\"admin\",\"passwd\":\"12345\",\"serialnumber\":\"qwertyui123\"}";
            out.println(line); 
            
            line = in.readLine(); 
            System.out.println("client got: " + line); 
            out.close(); 
            in.close(); 
            socket.close();*/ 
        } catch (UnknownHostException e) { 
            System.out.println(e); 
        } catch (IOException e) { 
            System.out.println(e); 
        } 
    } 
}
