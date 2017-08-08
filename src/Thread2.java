import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class Thread2 implements Runnable {

	//定义当前线程所处理的Socket
	Socket s = null;
	//该线程所处理的Socket所对应的输入流
	BufferedReader br = null;

	public Thread2(Socket s) throws IOException {
		this.s = s;
		//初始化该Socket对应的输入流
		br = new BufferedReader(new InputStreamReader(s.getInputStream(),
				"utf-8"));
	}
	
	/**
	 * 读取控制台输入
	 * */
	private void readConsole() {
	     //数组缓冲
        byte[] b = new byte[1024];
        //有效数据个数
        int n = 0;
        try{
         while(true){
              //提示信息
              System.out.println("请输入：");
              //读取数据
              n = System.in.read(b);
              //转换为字符串
              String str = new String(b,0,n - 2);
            //发送到客户端
	          for(Socket s : MyServer.socketList){
					OutputStream os = s.getOutputStream();
					os.write(("服务器发送"+str + "\n").getBytes("utf-8"));
				}
              //判断是否是quit
              if(str.equalsIgnoreCase("quit")){
                        break; //结束循环
              }
              //回显内容
              System.out.println("输入内容为：" + str);
             }
        }catch(Exception e){}
	}
	

	@Override
	public void run() {
		readConsole();
		
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
