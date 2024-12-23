import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    public static void main(String[] args)  {
        try {
            Socket client = new Socket();
            client.setSoTimeout(3000);
            client.connect(new InetSocketAddress(Inet4Address.getLocalHost(),8010),3000);
            System.out.println("客户端已连接");
            System.out.println(client.getLocalPort());
            System.out.println("Start!");
            clinetHandle(client);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private static  void clinetHandle(Socket client) throws Exception{
        Boolean flag = true ;
        String BYE = "bye";
        //客户端键盘输入
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader keyBoard = new BufferedReader(inputStreamReader);

        //客户socket的输出
        OutputStream outputStream = client.getOutputStream();
        PrintStream clientOut = new PrintStream(outputStream);

        //客户
        InputStream inputStream = client.getInputStream();
        BufferedReader clientIn = new BufferedReader(new InputStreamReader(inputStream));

        do {
            String clentReadLine = keyBoard.readLine();

            clientOut.println(clentReadLine);

            System.out.println(clientIn.readLine());
            if(BYE.equalsIgnoreCase(clentReadLine)) {
                flag = false;
                clientIn.close();
                inputStream.close();
                clientOut.close();
                outputStream.close();
                keyBoard.close();
                inputStreamReader.close();
                client.close();
            }
        }while (flag);


    }
}
