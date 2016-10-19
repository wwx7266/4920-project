


import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;





public class MainMenuUI extends JPanel{
	
	public static Font myfont;
    
	public static JPanel pl;
    
    private JLabel tit;
		
	private JTextField tf1; 
	private JTextField tf2; 
	
	private JLabel lb1; 
	private JLabel lb2; 
	
	private myButton LoginB;
	private myButton SandBoxB;
	
	private  JPanel backgroundPanel;
	private  JPanel PanelContainer;
	private  JPanel title;
	private  JPanel middle;
	private  JPanel bottom;
	
	MainMenuUI(JPanel p){
		pl = p;
		init_background();
	}
	
	public JPanel getMainMenuUI(){
		return backgroundPanel;
	}
	
	public void init_background(){
		
 
		 try {
              myfont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Font/ARHei.ttf"));
         } catch (FontFormatException | IOException ex) {
             ex.printStackTrace();
         }
		 
		 
			
		backgroundPanel = new JPanel(){
			protected void paintComponent(Graphics g){
				 java.net.URL imgURL = getClass().getResource("image/main_manu3.jpg");
				 ImageIcon icon = new ImageIcon(imgURL);
				 Image img = icon.getImage();  
	             g.drawImage(img, 0, 0, icon.getIconWidth(),  
	                        icon.getIconHeight(), icon.getImageObserver());   
			}
		};
		backgroundPanel.setLayout(new CardLayout());
		
		PanelContainer = new JPanel();
		PanelContainer.setBounds(0, 0, 1280, 960);
						
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
		c2.insets = new Insets(0,0,0,0);
				
		PanelContainer.add(middle,c2); 
		PanelContainer.add(Box.createRigidArea (new Dimension(15, 100))); 
		 
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0;
		c3.gridy = 2;
		c3.weightx = 1.0;
		c3.weighty = 120;
		c3.fill = GridBagConstraints.BOTH;
		c3.insets = new Insets(0,0,0,0);
		
		PanelContainer.add(bottom, c3);
		
		PanelContainer.setBackground(null); 
		PanelContainer.setOpaque(false);
		PanelContainer.setBorder(null);
		
		backgroundPanel.add(PanelContainer);
		backgroundPanel.setBorder(null);
		
		backgroundPanel.setVisible(true);
		
	}
	
	private void setupTitle(){
		title = new JPanel();
		tit = new JLabel("War Game!");
		tit.setFont(myfont.deriveFont(Font.BOLD, 48f));
		title.add(tit);
		title.setBackground(null);
		title.setOpaque(false);
	}
	
	private void setupMiddle(){
		middle = new JPanel();
		
		JPanel mid = new JPanel();
		mid.setLayout(new GridBagLayout());
		
	    GridBagConstraints c1 = new GridBagConstraints(); 
		c1.gridx = 0; 
		c1.gridy = 0; 
		c1.weightx = 1.0; 
		c1.weighty = 100; 
		c1.fill = GridBagConstraints.BOTH ; 
		c1.insets = new Insets(0,0,0,0);
		
	    GridBagConstraints c2 = new GridBagConstraints(); 
		c2.gridx = 0; 
		c2.gridy = 0; 
		c2.weightx = 1.0; 
		c2.weighty = 1.0; 
		c2.fill = GridBagConstraints.BOTH ; 
		c2.insets = new Insets(0,0,0,0);
	

		

		mid.setBackground(null);
		mid.setOpaque(false);

		
		
		lb1 = new JLabel("Username:" ); 
		lb2 = new JLabel("Password:" );  
		
		tf1=new JTextField(20);  
		tf2=new JTextField(20); 
		
		mid.add(lb1, c1);  
		mid.add(tf1, c2);  
		mid.add(lb2, c1);  
		mid.add(tf2, c2); 
		lb1.setFont(myfont.deriveFont(Font.BOLD, 28f));
		lb1.setForeground(new Color(195, 195, 195));
		lb2.setFont(myfont.deriveFont(Font.BOLD, 28f));
		lb2.setForeground(new Color(195, 195, 195));
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		
//		middle.add(Box.createRigidArea (new Dimension(550, 15))); 
		middle.add(mid);
//		middle.add(Box.createRigidArea (new Dimension(550, 15))); 

		middle.setBackground(null);
		middle.setOpaque(false);
	}

	private void setupBottom(){
		bottom = new JPanel();
		
//		java.net.URL buttonURL = this.getClass().getResource("image/button/002.png");
//		myButton LoginB = new myButton(new ImageIcon(buttonURL).getImage());
//		myButton SandBoxB = new myButton(new ImageIcon(buttonURL).getImage());
		
		JButton LoginB = new JButton("Login");
		JButton Reg = new JButton("Registe");
		JButton SandBoxB = new JButton("SandBox Mode");
		
		LoginB.addActionListener(new loginAction());
		Reg.addActionListener(new registeAction());
		
		bottom.add(LoginB);
		bottom.add(Reg);
		bottom.add(SandBoxB);
		
		bottom.setBackground(null);
		bottom.setOpaque(false);
	}
	
	private class myButton extends JButton{
		private Image image;
		public myButton(){};
		public myButton(Image image){
			this.image = image;
		}
		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(this.image, 0, 0, 256,
					256, null);
		};

	}
	
	
	private class magnifyButton implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			

		}

		@Override
		public void mousePressed(MouseEvent e) {
			

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
			JButton btn = (JButton) e.getComponent();
			btn.setBounds(btn.getX() - 10, btn.getY() - 10, btn.getWidth() + 20, btn.getHeight() + 20);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			
			JButton btn = (JButton) e.getComponent();
			btn.setBounds(btn.getX() + 10, btn.getY() + 10, btn.getWidth() - 20, btn.getHeight() - 20);
		}

	}
	
	private class loginAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			String username = tf1.getText();
			String password = tf2.getText();
			System.out.println(username + ' ' + password);
			String message = "Login#" + username + "#" + password;
			ChatterClient client = new ChatterClient();
			String[] recv = client.send(message).split("#");
			if(recv[2].equalsIgnoreCase("fail")){
				tf1.setText("");
				tf2.setText("");
				System.out.println("Login FAIL!");
			}else if(recv[2].equalsIgnoreCase("success")){
				message = "UserLoading#"+username;
				Player.UserName = username;
				String[] armyinfo = client.send(message).split("#");
				for(int i=0; i< armyinfo.length; i++){
				System.out.print("info is: " +armyinfo[i] + "\n");}
				if(armyinfo[2].length() == 0){
					Random generator = new Random();
					for(int i=0; i<5; i++){
						String Type = "Army_" + Integer.toString(i+6);
						
						int Max_HP = generator.nextInt(15);
						while(Max_HP == 0){
							Max_HP = generator.nextInt(15);
						}
						
						int Attack = generator.nextInt(10);
						while(Attack == 0){
							Attack = generator.nextInt(15);
						}

						int Lv = 1;
						
						message = "newunit#"+username+"#"+
								  Type+
								  "%"+Max_HP+
								  "%"+Attack+
								  "%"+Lv;
						String[] result = client.send(message).split("#");
						if(result[1].equalsIgnoreCase("success")){
							Unit tmp = new Unit(Type, Max_HP, Attack, Lv);						
							Player.Army.add(tmp);	
						}			
					}
				}else{
					System.out.print("String is :" + armyinfo[2]+"\n");
					String[] Units = armyinfo[2].split("&");
					for(int i=0; i<Units.length-1; i++){
						System.out.print("String is :" + Units[i]+"\n");
						String[] Unit = Units[i].split("%");
						String Type = Unit[0];
						int Max_HP = Integer.parseInt(Unit[1]);
						int Attack = Integer.parseInt(Unit[2]);
						int Lv = Integer.parseInt(Unit[3]);
						Unit tmp = new Unit(Type, Max_HP, Attack, Lv);						
						Player.Army.add(tmp);
					}
				}
				paintGameUI();
				System.out.println("Login SUCCESS!");
			}
		}
	}
	
	private class registeAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			String username = tf1.getText();
			String password = tf2.getText();
			System.out.println(username + ' ' + password);
			String message = "Registe#" + username + "#" + password;
			ChatterClient client = new ChatterClient();
			String[] recv = client.send(message).split("#");
			System.out.println(client.send(message));
			if(recv[2].equalsIgnoreCase("fail")){
				tf1.setText("");
				tf2.setText("");
				System.out.println("Registe FAIL!");
			}else if(recv[2].equalsIgnoreCase("success")){
				tf1.setText("Registe SUCCESS!");
				tf2.setText("");
				System.out.println("Registe SUCCESS!");
			}
		}
	}
	
	
	public void paintGameUI(){
//		backgroundPanel.setVisible(false);
//		backgroundPanel.updateUI();
		//start tmp = new start();
		JPanel tmp = new GameUI(pl).getGameUI(); 
		pl.add(tmp);
		((CardLayout) pl.getLayout()).next(pl);
	}
	
}
