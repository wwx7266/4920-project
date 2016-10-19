import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/*这个类是服务器端的UI*/
public class ServerUI extends JFrame {
    public static void main(String[] args) {
        ServerUI serverUI = new ServerUI();
    }

    public JButton btStart;//启动服务器
    public JButton btSend;//发送信息按钮
    public JTextField tfSend;//需要发送的文本信息
    public JTextArea taShow;//信息展示
    public Server server;//用来监听客户端连接
    static List<Socket> clients;//保存连接到服务器的客户端

    public ServerUI() {
        super("服务器端");
        btStart = new JButton("启动服务");
        btSend = new JButton("发送信息");
        tfSend = new JTextField(10);
        taShow = new JTextArea();

        btStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server = new Server(ServerUI.this);
            }
        });
        btSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.sendMsg(tfSend.getText());
                tfSend.setText("");
            }
        });

        JPanel top = new JPanel(new FlowLayout());
        top.add(tfSend);
        top.add(btSend);
        top.add(btStart);
        this.add(top, BorderLayout.SOUTH);
        final JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setViewportView(this.taShow);
        this.taShow.setEditable(false);
        this.add(sp, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocation(100, 200);
        this.setVisible(true);
    }
}