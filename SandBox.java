package client;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class SandBox {
	private ArrayList<Unit> attacker = new ArrayList<Unit>();
	private ArrayList<Unit> defence = new ArrayList<Unit>();
	
	public void start(){
		boolean end = false;
		Scanner input = new Scanner(System.in);
		System.out.println("Start SandBox Mode!");
		System.out.println("Enter -h for help;");
		System.out.println("-i to generate army");
		System.out.println("-s to show you current army state;");
		System.out.println("-g to start the game);");

		while(!end){
			String st = input.next();
			st.replaceAll("\\s+$", "");
			switch(st){
				case "-i":
					System.out.println("Random generate army for both team?(Y/N)");
					String s = input.next();
					if(s.equalsIgnoreCase("Y")){
						random_input(input);
					}else if(s.equalsIgnoreCase("N")){
						manual_input(input);
					}else{
						System.out.println("invaild input, re-enter.");
						continue;
					}
				case "-h":
					System.out.println("Enter -h for help;");
					System.out.println("-i to generate army");
					System.out.println("-s to show you current army state;");
					System.out.println("-g to start the game);");
					continue;
				case "-s":
					int i = 0;
					while(i < attacker.size()){
						System.out.println("attacker: ");
						attacker.get(i).show_state();
						i++;
					}
					i = 0;
					while(i < defence.size()){
						System.out.println("defence: ");
						defence.get(i).show_state();
						i++;
					}
					continue;
				case "-g":
					Game_Handler game = new Game_Handler(attacker, defence );
					game.start(input);
					end = true;
					break;
				default:
					continue;
					
			}
			
		}
		input.close();
		System.out.println("Game Over Bye-Bye !");
	}
	
	private void manual_input(Scanner input){
		
		System.out.println("Initializing attacker army ........  ");
		for(int i=0; i<10; i++){

			System.out.println("Please enter Unit type: ");
			String Type = input.next();
			
			System.out.println("Please enter Unit Max_HP: ");
			int Max_HP = Integer.parseInt(input.next());
			
			System.out.println("Please enter Unit Attack: ");
			int Attack = Integer.parseInt(input.next());
			
			System.out.println("Please enter Unit Defence: ");
			int Defence = Integer.parseInt(input.next());

			System.out.println("Please enter Unit Lv: ");
			int Lv = Integer.parseInt(input.next());
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			attacker.add(tmp);			
		}
		
		System.out.println("Initializing defence army ........ ");
		for(int i=0; i<10; i++){

			System.out.println("Please enter Unit type: ");
			String Type = input.next();
			
			System.out.println("Please enter Unit Max_HP: ");
			int Max_HP = Integer.parseInt(input.next());
			
			System.out.println("Please enter Unit Attack: ");
			int Attack = Integer.parseInt(input.next());
			
			System.out.println("Please enter Unit Defence: ");
			int Defence = Integer.parseInt(input.next());

			System.out.println("Please enter Unit Lv: ");
			int Lv = Integer.parseInt(input.next());
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			defence.add(tmp);			
		}

	}

	private void random_input(Scanner input){
		
		Random generator = new Random();
		System.out.println("Initializing attacker army ........ ");
		for(int i=0; i<5; i++){

			System.out.println("Please enter Unit type: ");
			String Type = input.next();
			
			int Max_HP = generator.nextInt(15);
			
			int Attack = generator.nextInt(10);
			
			int Defence = generator.nextInt(10);

			int Lv = generator.nextInt(5);
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			this.attacker.add(tmp);			
		}
		System.out.println("Initializing Defence army ........ ");
		for(int i=0; i<5; i++){

			System.out.println("Please enter Unit type: ");
			String Type = input.next();
			
			int Max_HP = generator.nextInt(15);
			
			int Attack = generator.nextInt(10);
			
			int Defence = generator.nextInt(10);

			int Lv = generator.nextInt(5);
			
			Unit tmp = new Unit(Type, Max_HP, Attack, Defence, Lv);
			this.defence.add(tmp);			
		}
	}
}
