import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;



public class InGameHandle {
	private ArrayList<Unit> attacker = new ArrayList<Unit>();
	private ArrayList<Unit> defence = new ArrayList<Unit>();
	private Spot spot = new Spot();
	private Hashtable<String, Integer> ht;
	private String curr_turn;
	private String chosen_army;
	//private String pre_turn_chosen_army;
	
	
	public InGameHandle() {
		ht = new Hashtable<String, Integer>();
		setTurn("defence_turn");
	}
	
	
	public void setTurn(String new_turn) {
		this.curr_turn = new_turn;
	}
	
	public String getTurn() {
		return this.curr_turn;
	}
	
	public ArrayList<Unit> getAttacker() {
		return this.attacker;
	}
	
	public ArrayList<Unit> getDefence() {
		return this.defence;
	}
	
	public void setClickedArmy(String chosen_army) {
		this.chosen_army = chosen_army;
	}
	
	public String getClickedArmy() {
		return chosen_army;
	}
	
	public String showArmyState(String army_name) {
		String ret = "";
		if(curr_turn.equals("defence_turn")) {
			int index = ht.get(army_name);
			Unit army_unit = defence.get(index);
			ret = army_unit.getUnitDetail(army_name);
		} else if(curr_turn.equals("offence_turn")) {
			int index = ht.get(army_name);
			Unit army_unit = attacker.get(index);
			ret = army_unit.getUnitDetail(army_name);
		}
		return ret;
	}


	public void defenceTurnAssign(JTextArea showOperation) {
		if(spot.get_assigened_def() == null) {
			int index = 0;
			index = ht.get(chosen_army);

			spot.def(defence.get(index));
			//System.out.print("Unit :");
			//defence.get(index).show_state();
			//System.out.println(" has been assigned");
			defence.remove(index);
			
			//renew hash
			for(int i=0; i < defence.size(); i++) {
				ht.put(defence.get(i).getUnitName(), i);
			}

			showOperation.setText("Current spot assigned army "
  					+ "\n\n"
  					+ chosen_army);			
			
		}else{
			showOperation.setText("You already have assigned a unit!");

		}
		//pre_turn_chosen_army = chosen_army;
	}
	
	public void offenceTurnAssign(JTextArea showOperation) {
		if(spot.get_assigened_att() == null) {
			int index = 0;
			index = ht.get(chosen_army);
	
			spot.att(attacker.get(index));
			//System.out.print("Unit :");
			//attacker.get(index).show_state();
			//System.out.println(" has been assigned");
			attacker.remove(index);
			
			//renew hash
			for(int i=0; i < attacker.size(); i++) {
				ht.put(attacker.get(i).getUnitName(), i);
			}
	
			showOperation.setText("Current spot assigned army "
						+ "\n\n"
						+ chosen_army);	
			
		}else{
			showOperation.setText("You already have assigned a unit!");

		}
		//pre_turn_chosen_army = chosen_army;
	}
	
	public void defenceTurnAttack(JTextArea showOperation) {
		if(spot.get_assigened_att() != null) {
			if(spot.get_assigened_def() != null) {
				String def_army_name = spot.get_assigened_def().getUnitName();
				def_fight(showOperation);
				
				showOperation.replaceSelection("Current spot attacked army "
	  					+ "\n\n"
	  					+ def_army_name);	
				
			}else{
				showOperation.setText("You already attacked !");
	
			}
		}
	}
	
	public void offenceTurnAttack(JTextArea showOperation) {
		if(spot.get_assigened_def() != null) {
			if(spot.get_assigened_att() != null) {
				String att_army_name = spot.get_assigened_att().getUnitName();
				off_fight(showOperation);
				
				showOperation.replaceSelection("Current spot attacked army "
						+ "\n\n"
						+ att_army_name);	
				
			}else{
				showOperation.setText("You already attacked !");
	
			}	
		}
	}
	
	
	private void def_fight(JTextArea showOperation){
		//if(spot.get_assigened_att() != null){
		if(spot.get_assigened_def() == null){
			showOperation.setText("Defence has not assigned any unit!");
		}else{
			int res = spot.get_assigened_def().fight(spot.get_assigened_att());
			//System.out.format("res is %d", res);
			showOperation.setText("res is " + Integer.toString(res) + "\n\n");
			switch(res){
				case 1:
					spot.att_des();
					break;
				case 2:
					spot.def_des();
					break;
				case 3:
					spot.att_des();
					spot.def_des();
					break;
				default:
					break;
			}	
		}
		//}
	}
	
	private void off_fight(JTextArea showOperation){
		//if(spot.get_assigened_def() != null){
		if(spot.get_assigened_att() == null){
			showOperation.setText("Attacker has not assigned any unit!");
		}else{
			int res = spot.get_assigened_att().fight(spot.get_assigened_def());
			//System.out.format("res is %d", res);
			showOperation.setText("res is " + Integer.toString(res) + "\n\n");
			switch(res){
				case 1:
					spot.att_des();
					break;
				case 2:
					spot.def_des();
					break;
				case 3:
					spot.att_des();
					spot.def_des();
					break;
				default:
					break;
			}	
		}
		//}
	}
	
	
	//Transform to defence / offence turn
	public void changeTurn(JTextArea showOperation) {
		if(curr_turn.equals("defence_turn")) {
			setTurn("offence_turn");
			showOperation.setText("offence_turn");
		} else if(curr_turn.equals("offence_turn")) {
			setTurn("defence_turn");
			showOperation.setText("defence_turn");
		}
	}
	
	
	public void random_input(JTextArea showOperation) {
		
		Random generator = new Random();
		showOperation.setText("Initializing attacker army ........ ");
		for(int i=0; i<5; i++){

			String Type = "Army_" + Integer.toString(i+6);
			
			int Max_HP = generator.nextInt(15);
			
			int Attack = generator.nextInt(10);
			
			int Defence = generator.nextInt(10);

			int Lv = generator.nextInt(5);
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			this.attacker.add(tmp);
			
			ht.put(Type, i);
		}
		showOperation.setText("Initializing Defence army ........ ");
		for(int i=0; i<5; i++){

			String Type = "Army_" + Integer.toString(i+1);
			
			int Max_HP = generator.nextInt(15);
			
			int Attack = generator.nextInt(10);
			
			int Defence = generator.nextInt(10);

			int Lv = generator.nextInt(5);
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			this.defence.add(tmp);
			
			ht.put(Type, i);
		}
	}
	
}
