package site.metacoding.chat_v2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServerSocket {

    // 리스너 (연결 받기) = 메인 스레드
    // 서버는 메세지 받기(클라이언트 수 마다)
    // 서버는 메세지 보내기 (클라이언트 수 마다)
    ServerSocket serverSocket;
    List<고객전담스레드> 고객리스트;

    public MyServerSocket() {
        try {
            serverSocket = new ServerSocket(2000);
            고객리스트 = new ArrayList<>();
            // while 연걸을 받을때 마다 계속 돌아가야하기 때문
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트 연결됨");
                고객전담스레드 t = new 고객전담스레드(socket);
                고객리스트.add(t);
                System.out.println("고객리스트 크기 " + 고객리스트.size());
                new Thread(t).start();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
    }

    class 고객전담스레드 implements Runnable {
        Socket socket;

        public 고객전담스레드(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }

}
