import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {

	//定义当前线程所处理的Socket
	Socket s = null;
	//该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	public ServerThread(Socket s) throws IOException {
		this.s = s;
		//初始化该Socket对应的输入流
		br = new BufferedReader(new InputStreamReader(s.getInputStream(),
				"utf-8"));
	}

	@Override
	public void run() {
		try
		{
			String content = null;
			//采用循环不断从Socket中读取客户端发送过来的数据
			while((content = readFromClient())!=null){
				//遍历socketList中的每个Socket
				//将读到的内容向每个Socket发送一次
				for(Socket s : MyServer.socketList){
					OutputStream os = s.getOutputStream();
					//简单处理模拟应答
					os.write((content + "——返回自服务器\n").getBytes("utf-8"));
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取客户端数据
	 * @return
	 */
	public String readFromClient()
	{
		try{
			return br.readLine();
		}catch(IOException e){	//如果捕捉到异常，表明该Socket对应的客户端已经被关闭
			//删除该Socket
			MyServer.socketList.remove(s);
		}
		return null;
	}

}
