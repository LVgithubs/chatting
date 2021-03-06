package site.metacoding.chat_v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        BufferedReader reader;
        BufferedWriter writer;
        boolean isLogin = true;

        public 고객전담스레드(Socket socket) {
            this.socket = socket;

            try {
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();

            }

        }

        @Override
        public void run() {
            while (isLogin) {
                try {
                    String inputData = reader.readLine();
                    System.out.println("from  클라이언트 : " + inputData);

                    for (고객전담스레드 t : 고객리스트) { // 왼쪽 : 컬렉션 타입 ,오른쪽 : 컬렉션
                        t.writer.write(inputData + "\n");
                        t.writer.flush();
                    }
                    // 메세지를 받았으니까 List < 고객전담스레드 > 고객리스트 <== 여기에 담긴
                    // 모든 클라이언트에게 메세지를 전송 for each문 돌려서!
                } catch (Exception e) {
                    try {
                        System.out.println("오류내용 : " + e.getMessage());
                        isLogin = false;
                        고객리스트.remove(this);
                        reader.close();
                        writer.close();
                        socket.close();
                    } catch (Exception e1) {
                        // TODO: handle exception
                        System.out.println("연결해제 프로세스 실패 : " + e1.getMessage());
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        new MyServerSocket();
    }

}
