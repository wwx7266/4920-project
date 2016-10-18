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
    private int t=30;//倒数计时30秒
    private String gTurn = "in_time";
    
    public String getCurrTime() {
    	return gTurn;
    }
    
    
    public void paint(Graphics g) {
            
        Timer timer = new Timer(); //创建timer
        timer.schedule(new timertask(), 1000);//每隔一秒执行一次

        super.paint(g);
        g.setColor(Color.RED);
        g.drawString("Time Left:  "+t+" s", 10, 10);
    }
            
    public void refresh(){
        t=30;//重置计数器
        gTurn = "in_time";
    }
    
    
    private class timertask extends TimerTask {
        public void run() {
            t=t-1;//一秒过去了
            repaint();// 刷新
            if(t<=0) {
                //System.exit(0);//超时退出
            	gTurn = "exit";
            }
        }
    }
            
} 