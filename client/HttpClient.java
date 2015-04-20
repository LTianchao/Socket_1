package client;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
public class HttpClient {
	public static void main(String[] args){
		try{
			if((args.length != 1) && (args.length != 2)){
				throw new IllegalArgumentException("Wrong number of args.");
			}
			OutputStream outToFile;
			if(args.length==2){
				outToFile = new FileOutputStream(args[1]);
			}else{
				outToFile = System.out;
			}
			URL url = new URL(args[0]);
			String protocol = url.getProtocol();
			if(!protocol.equals("http")){
				throw new IllegalArgumentException("Must use 'http:' protocol.");
			}
			String host = url.getHost();
			int port = url.getPort();
			if(port == -1) {
				port = 80;
			}
			
			String filename = url.getFile();
			Socket socket = new Socket(host, port);
			
			InputStream fromServer = socket.getInputStream();
			PrintWriter toServer = new PrintWriter(socket.getOutputStream());
			toServer.print("GET " + filename + "/n/n");
			toServer.flush();
			
			byte[] buffer = new byte[4096];
			int bytes_read;
			while((bytes_read = fromServer.read(buffer)) != -1){
				outToFile.write(buffer, 0, bytes_read);
			}
			socket.close();
			outToFile.close();
						
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
