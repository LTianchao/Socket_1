package server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class HttpMirror {
	public static void main(String[] args){
		try{
			int port = Integer.parseInt(args[0]);
			ServerSocket ss = new ServerSocket(port);
			while(true){
				Socket client = ss.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());
				out.print("HTTP/1.0 200 /n");
				out.print("Content-Type: text/plain/n");
				out.print("/n");
				
				String line;
				while((line=in.readLine())!= null){
					if(line.length()==0) break;
					out.print(line + "/n");
				}
				
				out.close();
				in.close();
				client.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
