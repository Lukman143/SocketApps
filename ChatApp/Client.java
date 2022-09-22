import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    Client(){
        try {
            System.out.println("Sending request server...!");
            socket=new Socket("127.0.0.1",7777);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {
            System.out.println("Connection closed...!");
            //e.printStackTrace();
        }
    }


    public void startReading() {

//Here I have created thread to read client data
        Runnable r1 = () -> {
            System.out.println("Reader.started...!");
            try {
            while (true) {
                    String msg = bufferedReader.readLine();
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat...!");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
            }
            } catch (Exception e) {
               // e.printStackTrace();
                System.out.println("Connection closed...!");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        //ye thread data user lenga or client ko send karenga
        Runnable r2 = () -> {
            System.out.println("Writer.started...!");
            try {
                while (!socket.isClosed()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String   content = br.readLine();
                    if (content.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat...!");
                        socket.close();
                        break;
                    }
                printWriter.println(content);
                printWriter.flush();
            }
            } catch (Exception e) {
              //  e.printStackTrace();
                System.out.println("Connection closed...!");
            }
        };
        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("Client.main");
        new Client();
    }
}
