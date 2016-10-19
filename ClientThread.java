import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    ClientUI ui;
    Socket client;
    BufferedReader reader;
    PrintWriter writer;

    public ClientThread(ClientUI ui) {
        this.ui = ui;
        try {
            client = new Socket("127.0.0.1", 1228);
            println("Connected: port 1228");
            reader = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream(), true);
            // ���Ϊ true���� println��printf �� format ������ˢ�����������
        } catch (IOException e) {
            println("Fail to connect:Port 1228");
            println(e.toString());
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {
        String msg = "";
        while (true) {
            try {
                msg = reader.readLine();
            } catch (IOException e) {
                println("disconnect");

                break;
            }
            if (msg != null && msg.trim() != "") {
                println(">>" + msg);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            writer.println(msg);
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
    
}