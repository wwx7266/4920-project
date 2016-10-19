import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/*������Ƿ������˵ĵȴ��ͻ�������*/
public class Server extends Thread {
    ServerUI ui;
    ServerSocket ss;
    BufferedReader reader;
    PrintWriter writer;

    public Server(ServerUI ui) {
        this.ui = ui;
        this.start();
    }

    public void run() {
        try {

            ss = new ServerSocket(1228);
            ui.clients=new ArrayList<Socket>();
            println("�����������ɹ����˿�1228");
            
            while (true) {
                println("�ȴ��ͻ���");
                Socket client = ss.accept();
                ui.clients.add(client);
                println("���ӳɹ�" + client.toString());
                new ListenerClient(ui, client);
            }
        } catch (IOException e) {
            println("����������ʧ�ܣ��˿�1228");
            println(e.toString());
            e.printStackTrace();
        }

    }

    public synchronized void sendMsg(String msg) {
        try {
            for (int i = 0; i < ui.clients.size(); i++) {
                Socket client = ui.clients.get(i);
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.println(msg);
            }

        } catch (Exception e) {
            println(e.toString());
        }
    }

    public void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
            System.out.println(s + "\n");
        }
    }

    public void closeServer() {
        try {
            if (ss != null)
                ss.close();
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}