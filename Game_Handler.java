package client;

import java.util.ArrayList;
import java.util.Scanner;

public class Game_Handler {
	private ArrayList<Unit> attacker;
	private ArrayList<Unit> defence;
	private Spot spot;
	
	public Game_Handler(ArrayList<Unit> att, ArrayList<Unit> def){
		attacker = att;
		defence  = def;
		spot = new Spot();
	}
	
	public void start( Scanner input){
		System.out.println("Game Start!");
//		boolean ack = false;
		while(!attacker.isEmpty() | !defence.isEmpty()){
			System.out.println("Defence turn! Please assign army by enter -a ");
			boolean flag_end = false;
			boolean flag_assign = false;
			boolean flag_fight = false;
			//Defense turn;
			while(!flag_end){
				System.out.println("Please enter a command!");
				String in = input.next();
				
				in.replaceAll("\\s+$", "");
				switch(in){
					case "-a":
						if(spot.get_assigened_def() == null && flag_assign == false){
							System.out.println("which unit do you want to send? enter a int");
							int index = 0;
							in = input.next();
							index = Integer.parseInt(in);
							if(index > defence.size()-1){
								System.out.println("Input out of Bound!Please Re-enter");
								break;
							}
							flag_assign = true;
							spot.def(defence.get(index));
							System.out.print("Unit :");
							defence.get(index).show_state();
							System.out.println(" has been assigned");
							defence.remove(index);
							break;
						}else{
							System.out.println("You already have assigned a unit!");
							break;
						}
					case "-p":
						spot.show_info();
						break;
					case "-f":
						if(flag_fight == true){
							System.out.println("You already attacked !");
							break;
						}
						flag_fight = true;
						def_fight();
						break;
					case "-e":
						flag_end = true;
						break;
					default:
						break;
				}
			}
			flag_end = false;
			flag_fight = false;
			flag_assign = false;
			
			//Offense turn;
			System.out.println("Offense turn! Please enter -a to assign a unit!");
			while(!flag_end){
				System.out.println("Please enter a command!");
				String in = input.next();
				
				in.replaceAll("\\s+$", "");
				switch(in){
					case "-a":
						if(flag_assign){
							System.out.println("You already have assigned a unit!");
							break;
						}
						System.out.println("which unit do you want to send? enter a int");
						int index = 0;
						in = input.next();
						index = Integer.parseInt(in);
						if(index > attacker.size()-1){
							System.out.println("Input out of Bound!Please Re-enter");
							break;
						}
						spot.att(attacker.get(index));
						System.out.print("Unit :");
						attacker.get(index).show_state();
						System.out.println(" has been assigned");
						flag_assign = true;
						attacker.remove(index);
						break;
					case "-p":
						if(spot.get_assigened_att() == null){
							System.out.println("Your Unit has been desitroied!");
							break;
						}
						spot.get_assigened_att().show_state();
						break;
					case "-f":
						if(flag_fight == true){
							System.out.println("You already attacked !");
							break;
						}
						flag_fight = true;
						off_fight();
						break;
					case "-e":
						flag_end = true;
						break;
				}
			}
		}
	}
	
	private void def_fight(){
		if(spot.get_assigened_att() == null){
			System.out.println("Attacker has not assigned any unit!");
		}else{
			int res = spot.get_assigened_def().fight(spot.get_assigened_att());
			System.out.format("res is %d", res);
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
	}
	
	private void off_fight(){
		if(spot.get_assigened_att() == null){
			System.out.println("Attacker has not assigned any unit!");
		}else{
			int res = spot.get_assigened_att().fight(spot.get_assigened_def());
			System.out.format("res is %d", res);
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
	}
}
