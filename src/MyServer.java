import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class MyServer {

	//定义保存所有Socket的ArrayList
	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// param 端口号
		ServerSocket ss = new ServerSocket(60000);
		while(true){
			//此代码会阻塞，将一直等待别人连接
			Socket s = ss.accept();
			socketList.add(s);
			//每当客户端连接后启动一条ServerThread线程为该客户端服务
			new Thread(new ServerThread(s)).start();
			new Thread(new Thread2(s)).start();
		}
	}

}
