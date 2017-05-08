package pers.bgm.dmis.SendMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/4/22.
 */
public class SendMessages {
    /**
     */
    public static void main(String[] args) throws Exception {
        //while(true){
        try {
            //创建Socket对象
            /*if (args == null || args.length == 0) {
                throw new Exception("参数详解：\n    1、IP_1\n    2、IP_2\n    3、端口\n    4、短信存在的目录\n");
            }*/
            Socket socket_01 = new Socket("192.168.11.151", 10002);
            Socket socket_02 = new Socket("192.168.11.152", 10002);
            //根据输入输出流和服务端连接
            OutputStream outputStream_01 = socket_01.getOutputStream();//获取一个输出流，向服务端发送信息
            PrintWriter printWriter_01 = new PrintWriter(outputStream_01);//将输出流包装成打印流

            OutputStream outputStream_02 = socket_02.getOutputStream();//获取一个输出流，向服务端发送信息
            PrintWriter printWriter_02 = new PrintWriter(outputStream_02);//将输出流包装成打印流

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("F:/mess/source/1/01.txt")));
            String value = "";
            int index = 0;

            while ((value = br.readLine()) != null) {

                String content = MakeMessages.makeCaller() + "zppz" + MakeMessages.makeDate() + "zppz" + value + "zppz" + MakeMessages.makeCallee();
                if (index == 0) {
                    printWriter_01.print(content + "\n");
                    printWriter_01.flush();
                    index = 1;
                } else {
                    printWriter_02.print(content + "\n");
                    printWriter_02.flush();
                    index = 0;
                }
                Thread.sleep(1000);
                }
            socket_01.shutdownOutput();//关闭输出
            printWriter_01.close();
            outputStream_01.close();
            socket_01.close();

            socket_02.shutdownOutput();//关闭输出
            printWriter_02.close();
            outputStream_02.close();
            socket_02.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
    }
}
