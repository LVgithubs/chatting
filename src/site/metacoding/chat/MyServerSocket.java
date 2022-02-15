package site.metacoding.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServerSocket {

    ServerSocket serverSocket; // 리스너 - 연결 여부를 지속적으로 확인 하는 라이브러리 (연결 = 세션)
    Socket socket; // 메세지 통신
    BufferedReader reader;
    String inputData;
    Scanner sc;
    BufferedWriter writer;
    boolean connQuit = true;

    public void 해윤() {
        System.out.println("해윤 메서드 실행");
    }

    public MyServerSocket() {
        try {
            // 1. 서버 소켓 생성 (리스너)
            // 잘알려진 포트 0~1023 (지정이 되어 있는 경우가 많기 때문에 충돌이 날 수 있다.)
            serverSocket = new ServerSocket(1077); // 내부적으로 while을 달고 있다.
            // 연결이 들어오는지를 계속해서 확인하고 있어야 하기 때문!
            System.out.println("서버 소켓 생성됨");
            // 192.168.0.132

            socket = serverSocket.accept();
            System.out.println("클라이언트 연결.");

            sc = new Scanner(System.in);

            new Thread(() -> {
                while (true) {
                    try {
                        writer = new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));
                        String inputData = sc.nextLine();
                        writer.write(inputData + "\n");
                        writer.flush();
                        // System.out.println("보낸 메세지 : " + inputData);
                        if (inputData.equals("종료")) {

                            break;
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                inputData = reader.readLine();
                if (inputData.equals("종료")) {

                    break;
                }
                System.out.println("받은 메세지 : " + inputData);

            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("통신 오류 발생 : " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
        System.out.println("메인 종료");
    }
}