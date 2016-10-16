import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameUI extends JPanel {
	static public JPanel pl;
	private JLabel backgroundImg;
	
	private Font GameFont = MainMenuUI.myfont;
	
	private  JPanel backgroundPanel;
	private  JPanel PanelContainer;
	private  JPanel title;
	private  JPanel middle;
	private  JPanel bottom;
	
	private ArrayList<Army> armyList;
	private JScrollPane armyListPane;
	private JTextArea chatArea;
	
	GameUI(JPanel p){
		pl = p;
		ini_GameUI();
	}
	
	public JPanel getGameUI(){
		return backgroundPanel;
	}
	
	public void ini_GameUI(){
		backgroundPanel = new JPanel(){
			protected void paintComponent(Graphics g){
				 java.net.URL imgURL = getClass().getResource("image/321.jpg");
				 ImageIcon icon = new ImageIcon(imgURL);
				 Image img = icon.getImage();  
	             g.drawImage(img, 0, 0, icon.getIconWidth(),  
	                        icon.getIconHeight(), icon.getImageObserver());   
			}
		};
		backgroundPanel.setLayout(new CardLayout());
		
		PanelContainer = new JPanel();
		PanelContainer.setBounds(0, 0, 1280, 960);
		PanelContainer.setBackground(null);
		PanelContainer.setBorder(null);
		PanelContainer.setOpaque(false);
		
		setupTitle();
		setupMiddle();
		setupBottom();
		
		
		PanelContainer.setLayout(new GridBagLayout());
		 GridBagConstraints c1 = new GridBagConstraints(); 
			c1.gridx = 0; 
			c1.gridy = 0; 
			c1.weightx = 1.0; 
			c1.weighty = 10; 
			c1.fill = GridBagConstraints.BOTH ; 
			c1.insets = new Insets(150,0,150,0);
					

		    PanelContainer.add(title, c1); 
			
			GridBagConstraints c2 = new GridBagConstraints(); 
			c2.gridx = 0; 
			c2.gridy = 1; 
			c2.weightx = 1.0; 
			c2.weighty = 20; 
			c2.fill = GridBagConstraints.BOTH ; 
			c2.insets = new Insets(0,800,0,0);
					
			PanelContainer.add(middle,c2); 
			 
			GridBagConstraints c3 = new GridBagConstraints();
			c3.gridx = 0;
			c3.gridy = 2;
			c3.weightx = 1.0;
			c3.weighty = 120;
			c3.fill = GridBagConstraints.BOTH;
			c3.insets = new Insets(150,0,0,0);
			
			PanelContainer.add(bottom, c3);
		
		backgroundPanel.add(PanelContainer);
		backgroundPanel.setVisible(true);
	}
	
	private void setupTitle(){
		title = new JPanel();
		
//		java.net.URL buttonURL = this.getClass().getResource("image/button/002.png");
//		myButton LoginB = new myButton(new ImageIcon(buttonURL).getImage());
//		myButton SandBoxB = new myButton(new ImageIcon(buttonURL).getImage());
		
		JButton SummaryB = new JButton("Unit Summary");
		JButton TradeB = new JButton("Trade");
		JButton SandBoxB = new JButton("SandBox Mode");
		
		SummaryB.addActionListener(new summaryAction());
		TradeB.addActionListener(new tradeAction());
		SandBoxB.addActionListener(new sandboxAction());
		
		title.add(SummaryB);
		title.add(TradeB);
		title.add(SandBoxB);
		
		
		title.setBackground(null);
		title.setOpaque(false);
	}
	
	private void setupMiddle(){
		middle = new JPanel();
		
		middle.add(creatArmyList());
		
		middle.setBackground(null);
		middle.setOpaque(false);
	}
	
	private void setupBottom(){
		bottom = new JPanel();
		
		
		chatArea = new JTextArea();
		chatArea.setPreferredSize(new Dimension(400, 200));
		bottom.add(chatArea);
		
		bottom.setBackground(null);
		bottom.setOpaque(false);
	}
	
	private JScrollPane creatArmyList() {
		armyList = new ArrayList<Army>();
		
		armyList.add(new Army(backgroundPanel, "army_1"));
		armyList.add(new Army(backgroundPanel, "army_2"));
		armyList.add(new Army(backgroundPanel, "army_3"));
		armyList.add(new Army(backgroundPanel, "army_4"));
		armyList.add(new Army(backgroundPanel, "army_5"));
		armyList.add(new Army(backgroundPanel, "army_6"));
		armyList.add(new Army(backgroundPanel, "army_7"));
		
		int i;
		armyListPane = new JScrollPane();

		JTextField armyTitle = new JTextField("Your Armys");
		armyTitle.setPreferredSize(new Dimension(100, 30));
		armyTitle.setHorizontalAlignment(JTextField.CENTER);
		armyTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		armyTitle.setEditable(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		buttonPanel.add(armyTitle);
		for(i = 0; i < armyList.size(); i++) {
			JButton currB = armyList.get(i).getArmyButton();

			currB.setPreferredSize(new Dimension(100, 40));
			buttonPanel.add(currB);
		}

		armyListPane.setViewportView(buttonPanel);
		armyListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		armyListPane.setPreferredSize(new Dimension(200, 150));
		
		return armyListPane;
	}
	
	private class summaryAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			pl.add(new UnitSummaryUI(pl));
			((CardLayout) pl.getLayout()).next(pl);
		}
	}
	
	private class tradeAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			pl.add(new TradeSystemUI(pl).getTradeSystemUI());
			((CardLayout) pl.getLayout()).next(pl);
		}
	}
	
	private class sandboxAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			pl.add(new SandBoxGUI(pl));
			((CardLayout) pl.getLayout()).next(pl);
		}
	}
}
