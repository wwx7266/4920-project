import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class start { 
	private static final int MIN_WIDTH = 1280;  
    private static final int MIN_HEIGHT = 960; 
    public static JFrame frame;
    public static JPanel PanelContainer;
    static void addPanel(JPanel P){
    	frame.add(P);
    }
    
    start(){
		JFrame frame = new JFrame("War Game");
		frame.setSize(1280, 960);
		Toolkit tk = frame.getToolkit();  
		Dimension dm = tk.getScreenSize();  
 
		frame.setBounds((dm.width - MIN_WIDTH) / 2, (dm.height - MIN_HEIGHT) / 2, MIN_WIDTH, MIN_HEIGHT);  
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		PanelContainer = new JPanel();
		PanelContainer.setBounds(0, 0, MIN_WIDTH, MIN_HEIGHT);
		frame.setContentPane(PanelContainer);
		
		PanelContainer.add(new MainMenuUI(PanelContainer).getMainMenuUI());
		PanelContainer.setLayout(new CardLayout());
		
		frame.setVisible(true);
    }
	
	public static void main(String[] args) {
		new start();
	}
}