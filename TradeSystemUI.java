import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TradeSystemUI extends JPanel {
	
	private JPanel pl;
	
	private JPanel contentPane;
	private JPanel init_interface;
	private JTextField txtTrading;
	private JTextField txtInput;
	private JButton searchButton;
	private JTable table;
	private JTextField txtStats;
	private JButton helpButton;
	private JButton btnBack_start;

	
	
	
	public JPanel getTradeSystemUI() {
		return contentPane;
	}
	

	
	public TradeSystemUI(JPanel p) {
		pl = p;
		//setBounds(100, 100, 600, 600);
		//setTitle("Trade System");
		//this.addComponentListener(new rePosition());
		contentPane = new JPanel(){
			protected void paintComponent(Graphics g){
				 java.net.URL imgURL = getClass().getResource("image/321.jpg");
				 ImageIcon icon = new ImageIcon(imgURL);
				 Image img = icon.getImage();  
	             g.drawImage(img, 0, 0, icon.getIconWidth(),  
	                        icon.getIconHeight(), icon.getImageObserver());   
			}
		};
		contentPane.setOpaque(false);
		contentPane.setBorder(null);
		contentPane.setLayout(new CardLayout(0, 0));
		
		((CardLayout) contentPane.getLayout()).show(contentPane, "first");

		init_interface = new JPanel();
		contentPane.add(init_interface, "first");
		init_interface.setLayout(new GridLayout(0, 1, 0, 0));
		init_interface.setOpaque(false);

		JPanel top = new JPanel();
		init_interface.add(top);
		top.setBackground(null);
		top.setOpaque(false);
		top.setLayout(null);

		

		txtTrading = new JTextField() {
			protected void paintComponent(Graphics g) {
				int x = 0, y = 0;
				g.drawImage(new ImageIcon(getClass().getResource("image/Trading.png")).getImage(), x, y, getSize().width,
						getSize().height, this);
			}
		};
		txtTrading.setBorder(null);
		txtTrading.setBounds(0, 0, 250, 300);
		txtTrading.setColumns(10);
		txtTrading.setOpaque(false);
		txtTrading.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrading.setEditable(false);
		
		top.add(txtTrading);



		txtInput = new JTextField(20);
		txtInput.setFont(new Font("SansSerif", Font.BOLD, 30));
		txtInput.setBounds(300, 55, 125, 60);

		top.add(txtInput);		



		searchButton = new JButton() {
			protected void paintComponent(Graphics g) {
				int x = 0, y = 0;
				g.drawImage(new ImageIcon(getClass().getResource("image/SearchButton.png")).getImage(), x, y, getSize().width,
						getSize().height, this);
			}
		};

		searchButton.setBounds(450, 55, 125, 60);
		searchButton.setBorder(null);
		searchButton.setContentAreaFilled(false);
		top.add(searchButton);

		searchButton.addActionListener(new searchAction());
		searchButton.addMouseListener(new magnifyButton());
		

		

		JPanel middle = new JPanel();
		init_interface.add(middle);
		middle.setBackground(null);
		middle.setOpaque(false);
		middle.setLayout(null);	

		
		
	    Object[][] tableData =   
        {  
            new Object[]{"" , "" , ""},  
            new Object[]{"", "" , ""},  
            new Object[]{"", "" , ""},  
            new Object[]{"", "" , ""},  
            new Object[]{"" , "" , ""},  
            new Object[]{"" , "" , ""}, 
            new Object[]{"" , "" , ""},  
            new Object[]{"" , "" , ""}  
        };  

        Object[] columnTitle = {"ID" , "NAME" , "GOLD"};  


        table = new JTable(tableData , columnTitle) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for(int i=0;i<table.getColumnCount();i++){

       	 table.getColumnModel().getColumn(i).setPreferredWidth(40); 

	    }
        table.setRowHeight(40);
        table.setPreferredScrollableViewportSize(new Dimension(550, 30));

        
        

        table.setBorder(null);
        table.getTableHeader().setSize(40, 40);
        table.getTableHeader().setFont(new Font("Dialog", 1, 20));
        table.getTableHeader().setBackground(new Color(200, 200, 200));

          
        middle.setLayout(new BorderLayout());
        middle.add(new JScrollPane(table),BorderLayout.CENTER); 

			


		JPanel start_bottom = new JPanel();
		init_interface.add(start_bottom);
		start_bottom.setOpaque(false);
		start_bottom.setLayout(null);

		

		btnBack_start = new JButton() {
			protected void paintComponent(Graphics g) {
				int x = 0, y = 0;
				g.drawImage(new ImageIcon(getClass().getResource("image/button_back.png")).getImage(), x, y, getSize().width,
						getSize().height, this);
			}
		};
		btnBack_start.addActionListener(new backStartAction());
		btnBack_start.setBounds(425, 90, 40, 40);
		btnBack_start.setBorder(null);
		btnBack_start.setContentAreaFilled(false);
		btnBack_start.addMouseListener(new magnifyButton());
		start_bottom.add(btnBack_start);	
		

		
		txtStats = new JTextField() {
			protected void paintComponent(Graphics g) {
				int x = 0, y = 0;
				g.drawImage(new ImageIcon(getClass().getResource("image/Stats.png")).getImage(), x, y, getSize().width,
						getSize().height, this);
			}
		};
		txtStats.setBorder(null);
		txtStats.setOpaque(false);
		txtStats.setEditable(false);
		txtStats.setBounds(20, 100, 100, 100);
				
		start_bottom.add(txtStats);
		

		
		helpButton = new JButton() {
			protected void paintComponent(Graphics g) {
				int x = 0, y = 0;
				g.drawImage(new ImageIcon(getClass().getResource("image/button_help.png")).getImage(), x, y, getSize().width,
						getSize().height, this);
			}
		};
		helpButton.setBounds(525, 90, 40, 40);
		helpButton.setBorder(null);
		helpButton.setContentAreaFilled(false);
		helpButton.addMouseListener(new magnifyButton());
				
		start_bottom.add(helpButton);
		
		contentPane.setVisible(true);
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

	private class searchAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			((CardLayout) contentPane.getLayout()).show(contentPane, "second");
		}
	}

	private class exitAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/*
	private class helpAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			HelpFrame hf = new HelpFrame();
			hf.setBounds(getX(), getY(), getWidth(), getHeight());
			hf.setVisible(true);
			setVisible(false);
		}
	}
	*/

	private class backStartAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			((CardLayout) pl.getLayout()).previous(pl);
			pl.remove(contentPane);
		}
	}

//	public void paintComponent(Graphics g) {
//		int x = 0, y = 0;
//		java.net.URL imgURL = getClass().getResource("image/321.jpg");
//		
//		ImageIcon icon = new ImageIcon(imgURL);
//		g.drawImage(icon.getImage(), x, y, getSize().width, getSize().height, this);
//		while (true) {
//			g.drawImage(icon.getImage(), x, y, this);
//			if (x > getSize().width && y > getSize().height)
//				break;
//			
//			if (x > getSize().width) {
//				x = 0;
//				y += icon.getIconHeight();
//			} else
//				x += icon.getIconWidth();
//		}
//	}
}