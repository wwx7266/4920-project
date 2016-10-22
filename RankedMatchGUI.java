import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class RankedMatchGUI extends JPanel {
	

	private final int RESX = 1280;
	private final int RESY = 960;
	
	private ArrayList<Unit> playerUnits;
	private ArrayList<Unit> enemyUnits;
	
    private JButton resetArmyButton;
    private JButton fightButton;
    private JButton createCustomUnitButton;
    private JButton quitButton;
    private JTextArea gameLogTA;
    private JLabel gameLogLabel;
    private JList playerUnitList;
    private JList enemyUnitList;
    private JLabel enemyArmyLabel;
    private JLabel yourArmyLabel;
    private JLabel enemyUnitLabel;
    private JLabel playerUnitLabel;
    private JTextField enemyUnitSelected;
    private JTextField playerUnitSelected;
    private JLabel topLabel;
    private Border line, margin, compound;
    private JScrollPane scrollPanePlayer;
    private JScrollPane scrollPaneEnemy;
    private JScrollPane scroll;
    
    private Engine engine;
    
    

    public RankedMatchGUI() {
    	init();					// used to initialize random army
    	
    	engine = new Engine(this);
        //construct preComponents

        //construct components
        resetArmyButton = new JButton ("Reset Army");
        fightButton = new JButton ("Simulate Fight");
        createCustomUnitButton = new JButton ("Create Custom Unit");
        quitButton = new JButton ("Quit");
        gameLogTA = new JTextArea (10, 10);
        
        // ADD UNITS TO UNIT LIST
        //playerUnitList = new JList (playerUnitListItems);
        //enemyUnitList = new JList (enemyUnitListItems);
        initTextList();
        
        gameLogLabel = new JLabel ("Game Log");
        enemyArmyLabel = new JLabel ("Enemy Army (Select a Unit to Fight Against)");
        yourArmyLabel = new JLabel ("Your Army (Select a Unit)");
        enemyUnitLabel = new JLabel ("Enemy Unit Selected:");
        playerUnitLabel = new JLabel ("Player Unit Selected:");
        enemyUnitSelected = new JTextField (50);
        playerUnitSelected = new JTextField (50);
        topLabel = new JLabel ("TOP LABEL");
        
        scrollPanePlayer = new JScrollPane (playerUnitList, 
        		   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneEnemy = new JScrollPane (enemyUnitList, 
        		   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll = new JScrollPane (gameLogTA, 
        		   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(gameLogTA);
        scrollPanePlayer.setViewportView(playerUnitList);
        scrollPaneEnemy.setViewportView(enemyUnitList);
        
        //JList action listeners
		playerUnitList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			      //int i = playerUnitList.getSelectedIndex();
				if(playerUnitList.getSelectedValue() != null){
					playerUnitSelected.setText((String) playerUnitList.getSelectedValue().toString());
				}
				else{
					playerUnitSelected.setText("");
				}
			}
		});
		
		enemyUnitList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			      //int i = enemyUnitList.getSelectedIndex();
				if(enemyUnitList.getSelectedValue() != null){
					enemyUnitSelected.setText((String) enemyUnitList.getSelectedValue().toString());
				}
				else{
					enemyUnitSelected.setText("");
				}
			}
		});
        
		
        //JButton action listeners
        resetArmyButton.addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) {
        		  init();
        		  updateTextList();
        	  } 
        });
        
        fightButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) { 
        		
        		(new Thread() {
        			  public void run() {
        	        		Unit a,b;
        	        		b = enemyUnits.get(enemyUnitList.getSelectedIndex());
        	        		a = playerUnits.get(enemyUnitList.getSelectedIndex());
        	        		engine.battle(a,b);
        			  }    			  
        		}).start();
      	  } 
        });
        
        createCustomUnitButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) { 
      	    
      	  } 
        });
      
        quitButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) { 
    	    
    	  } 
        });
        
        
        //change appearance
        resetArmyButton.setBackground(new Color(59, 89, 182));
        resetArmyButton.setForeground(Color.WHITE);
        resetArmyButton.setFocusPainted(false);
        resetArmyButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        fightButton.setBackground(new Color(59, 89, 182));
        fightButton.setForeground(Color.WHITE);
        fightButton.setFocusPainted(false);
        fightButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        line = new LineBorder(Color.red);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        fightButton.setBorder(compound);
        
        createCustomUnitButton.setBackground(new Color(59, 89, 182));
        createCustomUnitButton.setForeground(Color.WHITE);
        createCustomUnitButton.setFocusPainted(false);
        createCustomUnitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        quitButton.setBackground(new Color(239, 29, 22));
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
       
        line = new LineBorder(Color.green);
        margin = new EmptyBorder(5, 15, 5, 15);
        compound = new CompoundBorder(line, margin);
        quitButton.setBorder(compound);
        
        
        gameLogTA.setBackground(Color.lightGray);
        enemyUnitList.setBackground(Color.lightGray);
        playerUnitList.setBackground(Color.lightGray);
        enemyUnitSelected.setBackground(Color.red);
        playerUnitSelected.setBackground(Color.blue);
        setBackground(Color.white);
        
        //set components properties
        resetArmyButton.setToolTipText ("Click to generate a preseet army");
        fightButton.setToolTipText ("Start Battle");
        createCustomUnitButton.setToolTipText ("Assign selected unit");
        quitButton.setToolTipText ("Return to main menu");
        gameLogTA.setEditable(false);
        enemyUnitSelected.setEnabled (false);
        playerUnitSelected.setEnabled (false);

        //adjust size and set layout
        //setPreferredSize (new Dimension (941, 727));
        //setPreferredSize (new Dimension (1000, 720));
        setPreferredSize (new Dimension (RESX, RESY));
        setLayout (null);

        //add components
        add (resetArmyButton);
        add (fightButton);
        add (createCustomUnitButton);
        add (quitButton);
        //add (gameLogTA);
        add (gameLogLabel);
        //add (playerUnitList);
        //add (enemyUnitList);
        add (enemyArmyLabel);
        add (yourArmyLabel);
        add (enemyUnitLabel);
        add (playerUnitLabel);
        add (enemyUnitSelected);
        add (playerUnitSelected);
        add (topLabel);
        add (scrollPanePlayer);
        add (scrollPaneEnemy);
        add (scroll);

        //set component bounds (only needed by Absolute Positioning)
        int offsetx = 320;
        int offsety = 240;
        resetArmyButton.setBounds 	(20, 645 + offsety, 180, 60);
        fightButton.setBounds 		(785 + offsetx, 332 + (offsety/2), 120, 60);
        createCustomUnitButton.setBounds (225, 645 + offsety, 180, 60);
        quitButton.setBounds 		(740 + offsetx, 645 + offsety, 180, 60);
        
        enemyArmyLabel.setBounds 	(335 + offsetx, 85 + (offsety/2), 250, 25);
        yourArmyLabel.setBounds 	(335 + offsetx, 410 + (offsety/2), 250, 25);
        enemyUnitLabel.setBounds 	(390 + offsetx, 330 + (offsety/2), 250, 25);
        playerUnitLabel.setBounds 	(390 + offsetx, 370 + (offsety/2), 250, 25);
        enemyUnitSelected.setBounds (515 + offsetx, 330 + (offsety/2), 265, 25);
        playerUnitSelected.setBounds(515 + offsetx, 370 + (offsety/2), 265, 25);
        topLabel.setBounds 			(25, 5, 900, 80);
        
        scroll.setBounds 			(20, 115, 360 + offsety, 510 + offsety);
        scrollPanePlayer.setBounds 	(335 + offsetx, 440 + (offsety/2), 585, 180 + (offsety/2));
        scrollPaneEnemy.setBounds 	(335 + offsetx, 115 , 585, 180 + (offsety/2));
        
        gameLogTA.setBounds 		(20, 115, 360 + offsety, 510 + offsety);
        gameLogLabel.setBounds 		(20, 85, 100 + offsety, 25);
        playerUnitList.setBounds 	(335 + offsetx, 440 + (offsety/2), 585, 180 + (offsety/2));
        enemyUnitList.setBounds 	(335 + offsetx, 115 , 585, 180 + (offsety/2));
        
    }

    // initialize the random army
    public void init(){
    	Random rand = new Random();
    	Unit temp;
    	playerUnits = new ArrayList<Unit>();
    	enemyUnits = new ArrayList<Unit>();
    	for(int ctr=0;ctr<5;ctr++){
    		temp = new Unit(rand.nextInt(4),rand.nextInt(40)+11,rand.nextInt(10)+1,rand.nextInt(10)+1);
    		temp.setOwner("Player");
    		playerUnits.add(temp);
    		temp = new Unit(rand.nextInt(4),rand.nextInt(40)+11,rand.nextInt(10)+1,rand.nextInt(10)+1);
    		temp.setOwner("Enemy");
    		enemyUnits.add(temp);
    	}
    }
    
    public void updateTextList(){
		playerUnitList.setListData(playerUnits.toArray());
		enemyUnitList.setListData(enemyUnits.toArray());
    }
    
    public void initTextList(){
		playerUnitList = new JList(playerUnits.toArray());
		enemyUnitList = new JList(enemyUnits.toArray());
    }
    
    public void addTextToLog(String text){
    	gameLogTA.append(text+"\n");
    }
    
    public void forceTAUpdate(){
    	
    }
    
    public static void main (String[] args) {
        JFrame frame = new JFrame ("War Game Economies");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new RankedMatchGUI());
        frame.pack();
        frame.setVisible (true);
    }
}
