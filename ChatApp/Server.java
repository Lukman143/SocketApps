import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    Server() {
        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("Server is reddy to accept connection...!");
            System.out.println("Waiting...!");
            socket = serverSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("Connection closed...!");
        }
    }

    public void startReading() {

//Here I have created thread to read client data
        Runnable r1 = () -> {
            System.out.println("Reader.started...!");

            while (true) {
                try {
                    String msg = bufferedReader.readLine();
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat...!");
                        break;
                    }
                    System.out.println("Client : " + msg);
                } catch (Exception e) {
                   // e.printStackTrace();
                    System.out.println("Connection closed...!");break;
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        //ye thread data user lenga or client ko send karenga
        Runnable r2 = () -> {
            System.out.println("Writer.started...!");

            while (!socket.isClosed()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String content = null;
                try {
                    content = br.readLine();
                } catch (Exception e) {
                // e.printStackTrace();
                    System.out.println("Connection closed...!");break;

                }
                printWriter.println(content);
                printWriter.flush();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Server going to Start..!");
        new Server();
    }
}
