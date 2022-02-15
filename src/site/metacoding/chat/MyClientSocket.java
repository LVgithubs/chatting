package site.metacoding.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {
    Socket socket;
    BufferedWriter writer;
    BufferedReader reader;
    PrintWriter pw;
    Scanner chatting = new Scanner(System.in);
    String inputData;
    boolean connQuit = true;

    public MyClientSocket() {

        try {
            socket = new Socket("localhost", 2000);
            // 혼자 할때는 localhost 로 진행할 것
            new Thread(() -> {

                try {
                    while (connQuit) {
                        reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        inputData = reader.readLine();
                        if (inputData.equals("종료")) {
                            break;
                        }
                        System.out.println("받은 메세지 : " + inputData);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }).start();

            while (connQuit) {
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                inputData = chatting.nextLine();

                if (inputData.equals("종료")) {
                    break;
                }
                writer.write(inputData + "\n");
                writer.flush();
                // pw = new PrintWriter(socket.getOutputStream(), true);
                // pw.println(inputData);

            }

            // 마지막 메세지 끝에는 \n이 필요하다.
            // 그게 메세지의 끝이라는걸 알려준다.
            // 윈도우는 \n 리눅스는 \rn
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new MyClientSocket();
    }
}
