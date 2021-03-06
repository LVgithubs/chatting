package site.metacoding.chat_v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {

    Socket socket;
    // 채팅을 작성하는데 필요한 스레드
    BufferedWriter writer;
    Scanner sc;

    // 서버에서 받아오는 메세지를 읽어오는 스레드
    BufferedReader reader;

    public MyClientSocket() {

        try {
            socket = new Socket("192.168.0.132", 2000);
            // 192.168.0.132
            // localhost
            sc = new Scanner(System.in);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            읽기전담스레드 t = new 읽기전담스레드();
            new Thread(t).start();

            while (true) {
                String keyboardInputData = sc.nextLine();
                writer.write(keyboardInputData + "\n"); // 버퍼에 담는것
                writer.flush(); // 버퍼에 담긴 것을 stream 으로 흘려보내기
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    class 읽기전담스레드 implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                while (true) {
                    String inputData = reader.readLine();
                    System.out.println(" 받은 메세지  : " + inputData);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    public static void main(String[] args) {
        new MyClientSocket();
    }
}
