package Server;

import java.util.*;

//holds a list of ongoing battle objects
//determines from player level who should be matched with who
//holds a list of users currently in matchmaking
public class BattleMgmt {
	ArrayList<Battle> battleList = new ArrayList<Battle>();
	Queue matchQueue = null;
	BattleMgmt(){
		
	}
	//adds a player to the battle queue
	//
	public void addToQueue(String login, String armyName){
		
	}
	//removes user from battle queue
	public void removeFromQueue(String login){
		
	}
	//determines which battle the player is involved with and makes moves based on input
	public void makeMove(String login, String moves){
		
	}
	//returns false if the player isn't involved in a battle in the battle list
	//returns the current state of the board if the user is in a battle
	public String isInBattle(){
		
	}
	
}
//handles the match making queue server side
class Queue implements Runnable{
	ArrayList<String[]> queue = new ArrayList<String[]>();
	BattleMgmt instance = null;
	Queue(BattleMgmt instance){
		this.instance = instance;
	}
	public void run(){
		//matchmaking goes in here
		//moves users picked into a battle object instance
	}
	public void addToQueue(String login, String armyName){
		
	}
	public void removeFromQueue(String login){
		
	}
}

class Battle {
	ArrayList<Player> player1;
	ArrayList<Player> player2;
	ArrayList<Unit> army1;
	ArrayList<Unit> army2;
	
}

class Player {
	String login;
	int level;
	String ArmyName;
	
}
class Unit {
	static int WORRIOR_TYPE = 0;
	static int LANCER_TYPE  = 1;
	static int KNIGHT_TYPE  = 2;
	static int ROUGE_TYPE   = 3;
	
	private String Type;
	private String Name;
	private int Max_HP;
	private int Curr_HP;
	private int BP;
	private int Attack;
	private int Defence;
	private int Lv;
	private int Exp;
	
	public Unit(String type, int maxhp, int attack, int defence, int lv){
		Type = type;
		Name = type;
		Max_HP = maxhp;
		Curr_HP = maxhp;
		Attack = attack;
		Defence = defence;
		BP = 0;  //need to implement how to calculate the battle points	
		Lv = lv;
		Exp = 0;
		
	}
	
	public int fight(Unit enemy){
		if(Attack > enemy.get_Defence()){
			enemy.set_Curr_HP( enemy.get_Curr_HP() - (Attack - enemy.get_Defence()) );
		}else{
			enemy.less();
		}
		if(enemy.get_Attack() > Defence){
			Curr_HP = Curr_HP - (enemy.get_Attack() - Defence);
		}else{
			Curr_HP--;
		}
		if(enemy.get_Curr_HP() <= 0 && Curr_HP > 0){
			Exp = Exp + 100 + (enemy.get_Lv() - Lv)*10;
			if(Exp > 150 * Lv){
				Exp = Exp - 150 * Lv;
				Lv ++;
			}
			return 1; //only enemy unit destroyed
		}else if(enemy.get_Curr_HP() > 0 && Curr_HP <= 0){
			return 2; //only my unit destroyed
		}else if(enemy.get_Curr_HP() <= 0 && Curr_HP <= 0){
			return 3; //both unit destroyed
		}else{
			return 0; //no unit destroyed
		}
	}
	
	public void show_state(){
		System.out.format("Type: %s "
						 + "Name: %s "
						 + "HP: %d/%d "
						 + "Attack: %d "
						 + "Defence: %d "
						 + "Lv: %d Exp: %d \n", 
						 Type, Name, Curr_HP, Max_HP, Attack, Defence, Lv, Exp  );
	}
	 
	
 	public String get_type(){
		return Type;
	}
	private int get_Max_HP(){
		return Max_HP;
	}
	private int get_Curr_HP(){
		return Curr_HP;
	}
	private int get_BP(){
		return BP;
	}
	private int get_Attack(){
		return Attack;
	}
	private int get_Defence(){
		return Defence;
	}
	private int get_Lv(){
		return Lv;
	}
	
	public void set_type(String name){
		Type = name;
	}
	public void set_Max_HP(int x){
		Max_HP = x;
	}
	public void set_Curr_HP(int x){
		Curr_HP = x;
	}
	public void set_BP(int x ){
		BP = x;
	}
	public void set_Attack(int x){
		Attack  = x;
	}
	public void set_Defence(int x){
		Defence = x;
	}
	public void set_Lv(int x){
		Lv = x;
	}
	
	private void less(){
		Curr_HP--;
	}
}
class Spot{
	static int HIGHGROUND = 0;
	static int GRESS = 1;
	static int HILL  = 2;
	static int desert = 3;
	
	private Unit assigned_def;
	private Unit assigned_att;
	private int terrain;
	
	public Spot(){
		Random generator = new Random();
		assigned_def = null;
		assigned_att = null;
		terrain = generator.nextInt(3);
	}
	
	public void def_des(){
		assigned_def = null;
	}
	
	public void att_des(){
		assigned_att = null;
	}
	
	public void def(Unit unit){
		assigned_def = unit;
	}
	
	public void att(Unit unit){
		assigned_att = unit;
	}
	
	public Unit get_assigened_def(){
		return assigned_def;
	}
	
	public Unit get_assigened_att(){
		return assigned_att;
	}
	
	public void show_info(){
		System.out.print("the terrain of this spot is ");
		switch(terrain){
			case 0:
				System.out.println("High Ground");
				break;
			case 1:
				System.out.println("Gress");
				break;
			case 2:
				System.out.println("Hill");
				break;
			case 3:
				System.out.println("desert");
				break;
		}
		if(assigned_def == null){
			System.out.println("Defense side have not assigned any unit!");
		}else{
			System.out.println("The army info: ");
			assigned_def.show_state();
		}
	}
}