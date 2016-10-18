


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.*;




public class SandBoxGUI extends JPanel {
	
	private JPanel pl;
    private JLabel jcomp1;
    private JLabel jcomp2;
    private JTextArea jcomp3;
    private JButton jcomp4;
    private JList jcomp5;
    private JList enemy_list;
    private JTextArea jcomp6;
    private JTextArea jcomp7;
    private JTextArea jcomp8;
    private JButton jcomp9;
    private JButton jcomp10;
    private JButton jcomp11;
    private JTextField turn_num;
    

    private InGameHandle gHandle;
    private DefaultListModel my_listModel;
    private DefaultListModel enemy_listModel;
    private DefaultListModel armyModel;
    private JScrollPane statePane;
    private JScrollPane newstatePane;
    private JList assigned_army;
    private JPanel assigned_army_pane;
    private GameTimer gTimer;
    private Timer turn_timer;
    private int turn_count;

    public SandBoxGUI(JPanel p) {
    	pl = p;
    	
    	turn_count = 5;

        my_listModel = new DefaultListModel();
        enemy_listModel = new DefaultListModel();

        
        //construct components
        jcomp1 = new JLabel ("Turns Left");
        jcomp2 = new JLabel ("Turn Timer");
        jcomp3 = new JTextArea (5, 5);
        jcomp4 = new JButton ("End Turn");
        jcomp5 = new JList (my_listModel);
        enemy_list = new JList (enemy_listModel);
        jcomp6 = new JTextArea (5, 5);
        jcomp7 = new JTextArea (5, 5);
        jcomp8 = new JTextArea (5, 5);
        turn_num = new JTextField();
        
        jcomp9 = new JButton ("Generator Army");
        jcomp10 = new JButton ("Assign");
        jcomp11 = new JButton ("Attack");

        gHandle = new InGameHandle(jcomp7);
    	jcomp5.addMouseListener(new jListClick());
    	enemy_list.addMouseListener(new jListClick());
        jcomp4.addActionListener(new changeTurnAction());
    	jcomp5.addListSelectionListener(new my_jListSelect());
    	enemy_list.addListSelectionListener(new enemy_jListSelect());
        jcomp9.addActionListener(new genOffenceAction());
        jcomp10.addActionListener(new assignAction());
        jcomp11.addActionListener(new attackAction());
        jcomp11.addActionListener(new combatLogAction());

        //adjust size and set layout
        setPreferredSize (new Dimension (1280, 960));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (enemy_list);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (jcomp9);
        add (jcomp10);
        add (jcomp11);
        add (turn_num);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (25, 40, 100, 25);
        jcomp2.setBounds (605, 40, 100, 25);
        jcomp3.setBounds (25, 250, 175, 250);
        jcomp4.setBounds (595, 435, 105, 60);
        jcomp5.setBounds (220, 425, 350, 70);
        enemy_list.setBounds (220, 90, 350, 70);
        jcomp6.setBounds (160, 15, 405, 70);
        jcomp7.setBounds (25, 180, 80, 50);
        jcomp8.setBounds (25, 100, 80, 50);
        jcomp9.setBounds (595, 240, 155, 60);
        jcomp10.setBounds (595, 300, 105, 60);
        jcomp11.setBounds (595, 370, 105, 60);
        turn_num.setBounds (100, 40, 40, 20);
        
        turn_num.setText(Integer.toString(turn_count));
        
       
        JScrollPane combat_log_Pane = new JScrollPane();
        combat_log_Pane.add(jcomp6);
        combat_log_Pane.setBounds(160, 15, 405, 70);
        combat_log_Pane.setViewportView(jcomp6);
        add(combat_log_Pane);  
        
        JScrollPane my_listPane = new JScrollPane();
        my_listPane.add(jcomp5);
        my_listPane.setBounds(220, 425, 350, 70);
        my_listPane.setViewportView(jcomp5);
        add(my_listPane);
        
        JScrollPane enemy_listPane = new JScrollPane();
        enemy_listPane.add(enemy_list);
        enemy_listPane.setBounds(220, 90, 350, 70);
        enemy_listPane.setViewportView(enemy_list);
        add(enemy_listPane);
        
        
        gTimer = new GameTimer();
        gTimer.setBounds(605, 90, 100, 140);
        add(gTimer);
        
        turn_timer = new Timer();
        turn_timer.schedule(new timeCheck(), 0, 1000);//check every 0.5 second

    }
    
    
    
    private class timeCheck extends TimerTask {
        public void run() {

            //exit and start next turn
            if(gTimer.getCurrTime().equals("exit")) {
    			
            	gHandle.changeTurn(jcomp7);
    			jcomp3.setText("");
    			showJList(my_listModel);
    	    	if(gHandle.getTurn().equals("defence_turn")) {
    	    		gHandle.changeTurn(jcomp8);
    	    		showJList(enemy_listModel);
    	    		gHandle.setTurn("defence_turn");

    	    	} else if(gHandle.getTurn().equals("offence_turn")) {
    	    		gHandle.changeTurn(jcomp8);
    	    		showJList(enemy_listModel);
    	    		gHandle.setTurn("offence_turn");
    	    	}
    			removeStatePane();
    			
    			gTimer.refresh();
    			turn_count--;
    			turn_num.setText(Integer.toString(turn_count));
            }
            if(turn_count == 0) {
            	System.exit(0);
            }
        }
    }
    
    
    public void showJList(DefaultListModel listModel) {
    	
    	if(listModel.getSize() == 0) {
	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		
	    		ArrayList<Unit> defence = gHandle.getDefence();   		
	    		for(int i=0; i<5; i++) {
	    			listModel.addElement(defence.get(i).getUnitName());
	    		}
	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		
	    		ArrayList<Unit> attacker = gHandle.getAttacker();    		
	    		for(int i=0; i<5; i++) {
	    			listModel.addElement(attacker.get(i).getUnitName());
	    		}
	    	}
    	} else {
    		

	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		
	    		int i;
	    		ArrayList<Unit> defence = gHandle.getDefence();   		
	    		for(i=0; i < defence.size(); i++) {

	    			listModel.setElementAt(defence.get(i).getUnitName(), i);
	    		}

	    		if(i < listModel.getSize()) {
		    		for(; i < listModel.getSize(); i++) {
		    			listModel.setElementAt("null", i);
		    		}
	    		}
	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		
	    		int i;
	    		ArrayList<Unit> attacker = gHandle.getAttacker();    		
	    		for(i=0; i < attacker.size(); i++) {

	    			listModel.setElementAt(attacker.get(i).getUnitName(), i);
	    		}
	    		
	    		if(i < listModel.getSize()) {
		    		for(; i < listModel.getSize(); i++) {
		    			listModel.setElementAt("null", i);
		    		}
	    		}
	    	}    		
    	}
    }
    
    public void showAssignedArmy() {
    	
    	assigned_army_pane = new JPanel();
		armyModel = new DefaultListModel();	    		
		assigned_army = new JList(armyModel);
		assigned_army_pane.add(assigned_army);
		assigned_army.setBounds (210, 360, 40, 20);
		assigned_army_pane.setBounds (210, 360, 50, 30);
    	
		assigned_army.addMouseListener(new assignedArmyClick());
		armyModel.removeAllElements();
		if(armyModel.getSize() == 0) {
			armyModel.addElement(gHandle.getAssignedArmy().getUnitName());
		} else {
			armyModel.setElementAt(gHandle.getAssignedArmy().getUnitName(), 0);
		}
		add(assigned_army_pane);
    }
    
    public void removeStatePane() {
    	if(statePane != null) {
	        Container parent = statePane.getParent();
	        parent.remove(statePane);
	        parent.revalidate();
	        parent.repaint();
	        statePane = null;
    	}
    	if(newstatePane != null) {
	        Container parent = newstatePane.getParent();
	        parent.remove(newstatePane);
	        parent.revalidate();
	        parent.repaint();
	        newstatePane = null;
    	}
    }

	
	private class changeTurnAction implements ActionListener{
		public void actionPerformed(ActionEvent a){

			gHandle.changeTurn(jcomp7);
			jcomp3.setText("");
			showJList(my_listModel);
	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		gHandle.changeTurn(jcomp8);
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("defence_turn");

	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		gHandle.changeTurn(jcomp8);
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("offence_turn");
	    	}
	    	
			removeStatePane();
			turn_count--;
			turn_num.setText(Integer.toString(turn_count));
		}
	}
	
	private class assignAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
			if(gHandle.getEnemyAssign() == false) {
		    	if(gHandle.getTurn().equals("defence_turn")) {
		    		gHandle.defenceTurnAssign(jcomp3);
		    		
		    		showAssignedArmy();
	
		    	} else if(gHandle.getTurn().equals("offence_turn")) {
		    		
		    		gHandle.offenceTurnAssign(jcomp3);
		    		
		    		showAssignedArmy();
		    	}
			} else {
		    	if(gHandle.getTurn().equals("defence_turn")) {
		    		gHandle.offenceTurnAssign(jcomp3);
		    		
		    		showAssignedArmy();
	
		    	} else if(gHandle.getTurn().equals("offence_turn")) {
		    		
		    		gHandle.defenceTurnAssign(jcomp3);
		    		
		    		showAssignedArmy();
		    	}
			}
	    	showJList(my_listModel);
	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		gHandle.setTurn("offence_turn");
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("defence_turn");

	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		gHandle.setTurn("defence_turn");
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("offence_turn");
	    	}
			removeStatePane();
		}
	}
	
	private class attackAction implements ActionListener{
		public void actionPerformed(ActionEvent a){
	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		gHandle.defenceTurnAttack(jcomp3);

	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		
	    		gHandle.offenceTurnAttack(jcomp3);
	    	}
	    	showJList(my_listModel);
	    	showJList(enemy_listModel);
			removeStatePane();
		}
	}
	
	private class combatLogAction implements ActionListener{
		public void actionPerformed(ActionEvent a){

			jcomp6.replaceSelection(gHandle.getAttackLog());
		}
	}
	
	private class genOffenceAction implements ActionListener{
		public void actionPerformed(ActionEvent a){

			gHandle.random_input(jcomp3);
			showJList(my_listModel);
	    	if(gHandle.getTurn().equals("defence_turn")) {
	    		gHandle.changeTurn(jcomp8);
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("defence_turn");

	    	} else if(gHandle.getTurn().equals("offence_turn")) {
	    		gHandle.changeTurn(jcomp8);
	    		showJList(enemy_listModel);
	    		gHandle.setTurn("offence_turn");
	    	}
		}
	}
	
	
	private class my_jListSelect implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent arg0) {
            if (!arg0.getValueIsAdjusting()) {
            	String army_name = jcomp5.getSelectedValue().toString();

            	if(army_name != null && army_name.length() != 0) {
            		gHandle.setClickedArmy(army_name);
            		gHandle.setEnemyAssign(false);
            	}            
            }
        }
	}
	
	
	private class enemy_jListSelect implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent arg0) {
            if (!arg0.getValueIsAdjusting()) {
            	String army_name = enemy_list.getSelectedValue().toString();

            	if(army_name != null && army_name.length() != 0) {
            		gHandle.setClickedArmy(army_name);
            		gHandle.setEnemyAssign(true);
            	}            
            }
        }
	}

	
	private class jListClick implements MouseListener{
	    public void mouseClicked(MouseEvent e){
	        if(e.getClickCount()==2){

	            JTextArea jta = new JTextArea();
	            jta.setBounds(250, 250, 300, 100);
	            jta.setText(gHandle.showArmyState(gHandle.getClickedArmy()));

	            statePane = new JScrollPane();
	            statePane.add(jta);
	            statePane.setBounds(250, 250, 300, 100);
	            statePane.setViewportView(jta);
	            add(statePane); 
	        }
	    }

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	
	private class assignedArmyClick implements MouseListener{
	    public void mouseClicked(MouseEvent e){
	        if(e.getClickCount()==2){

	            JTextArea jtaa = new JTextArea();
	            jtaa.setBounds(250, 250, 300, 100);
	            Unit curr_unit = gHandle.getAssignedArmy();
	            jtaa.setText(curr_unit.getUnitDetail(curr_unit.getUnitName()));

	            newstatePane = new JScrollPane();
	            newstatePane.add(jtaa);
	            newstatePane.setBounds(250, 250, 300, 100);
	            newstatePane.setViewportView(jtaa);
	            add(newstatePane);
	            armyModel.removeAllElements();
	        }
	    }

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}		
	}

}
