import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameTimer extends JPanel {
    private int t=30;//������ʱ30��
    private String gTurn = "in_time";
    
    public String getCurrTime() {
    	return gTurn;
    }
    
    
    public void paint(Graphics g) {
            
        Timer timer = new Timer(); //����timer
        timer.schedule(new timertask(), 1000);//ÿ��һ��ִ��һ��

        super.paint(g);
        g.setColor(Color.RED);
        g.drawString("Time Left:  "+t+" s", 10, 10);
    }
            
    public void refresh(){
        t=30;//���ü�����
        gTurn = "in_time";
    }
    
    
    private class timertask extends TimerTask {
        public void run() {
            t=t-1;//һ���ȥ��
            repaint();// ˢ��
            if(t<=0) {
                //System.exit(0);//��ʱ�˳�
            	gTurn = "exit";
            }
        }
    }
            
} 