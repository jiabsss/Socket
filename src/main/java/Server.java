import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8010);
            System.out.println("New Server :"+serverSocket.getInetAddress());
            System.out.println(serverSocket.getLocalPort());
            System.out.println("---------Ready---------");
            do {
                //阻塞
                Socket accept = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(accept);
                clientHandler.start();
            }while (true);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class ClientHandler extends Thread {
        private Socket socket ;
        private Boolean flag = true ;
        private String BYE = "bye";
        public ClientHandler(Socket client){
            this.socket= client;
        }

        @Override
        public void run()  {
            PrintStream printStream =null;
            BufferedReader bufferedReader = null;
            try {
                super.run();
                System.out.println("客户端连接");
                System.out.println( socket.getLocalAddress() );
                System.out.println( socket.getLocalPort() );
                //打印流
                 printStream = new PrintStream(socket.getOutputStream());
                 bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do{
                    String readLine = bufferedReader.readLine();
                    if(BYE.equalsIgnoreCase(readLine)&&socket.getKeepAlive()){
                        flag=false;
                        System.out.println("客户端退出");

                    }else if(null==readLine){

                    }else{
                        System.out.println(readLine);
                        printStream.println(readLine.length());
                    }

                }while (flag);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                bufferedReader.close();
                printStream.close();
                socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
