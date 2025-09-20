import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        int port = 6379;
        try (ServerSocket serverSocket = new ServerSocket(port);
             // Wait for connection from client.
             Socket clientSocket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            String content;
//            int i=0;
//            int messageLength = 1;
            while ((content = reader.readLine())!=null){
                System.out.println("content: "+content);
//                if(content.substring(content.length()-2).contains("*")){
//                    messageLength=Integer.parseInt(content.substring(content.length()-1)) * 2;
//                }
                if("PING".equals(content)) {
                    outputStream.write("+PONG\r\n".getBytes());
                    outputStream.flush();
//                    i=0;
                }
//                i++;
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
