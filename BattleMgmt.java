package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//holds a list of ongoing battle objects
//determines from player level who should be matched with who
//holds a list of users currently in matchmaking
public class BattleMgmt implements Runnable{
	ArrayList<Battle> battleList = new ArrayList<Battle>();
	Queue matchQueue = null;
	Account accounts = null;
	Market market = null;
	BattleMgmt(Account accounts, Market market){
		this.accounts = accounts;
		this.market = market;
		matchQueue = new Queue(this);
	}
	public void run(){
		//matchmaking goes in here
		//moves users picked into a battle object instance
	}	
	public Queue returnQueue(){
		return this.matchQueue;
	}
}
//handles the match making queue server side
class Queue{
	ArrayList<Player> queue = new ArrayList<Player>();
	ArrayList<Battle> battleList = new ArrayList<Battle>();
	BattleMgmt instance = null;
	Queue(BattleMgmt instance){
		this.instance = instance;
	}
	//adds a player to the battleQueue
	public boolean addToQueue(String login, String armyName){
		Player toAdd = null;
		try{
			toAdd = new Player(login, armyName, instance);
		} catch(Exception e){
			return false;
		}
		this.queue.add(toAdd);
		return true;
		
	}
	//removes a player from the queue given their login
	public void removeFromQueue(String login){
		Player toRemove = null;
		for(Player player: queue){
			if(player.getName().equals(login)){
				toRemove = player;
			}
		}
		queue.remove(toRemove);
	}
}

class Battle {
	Player player1 = null;
	Player player2 = null;
	ArrayList<Unit> army1 = null;
	ArrayList<Unit> army2 = null;
	ArrayList<String> commandSet1 = new ArrayList<String>();
	ArrayList<String> commandSet2 = new ArrayList<String>();
	String state = null;
	
	BattleMgmt instance = null;
	public Battle(Player player1, Player player2, BattleMgmt instance) throws Exception{
		this.instance = instance;
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.army1 = getUnits(player1);
		this.army2 = getUnits(player2);
	}
	
	//retrieves the units in a player's army, if the army dosen't exist, returns null
	public ArrayList<Unit> getUnits(Player player) throws Exception{
		ArrayList<Unit> output = new ArrayList<Unit>();
		ArrayList<String[]> units = instance.accounts.displayArmyBattle(player.getName(), player.getArmyName());
		if(units.isEmpty()){
			return null;
		}
		for(String[] unit : units){
			output.add(new Unit(Integer.parseInt(unit[0]), unit[2], Integer.parseInt(unit[5]),Integer.parseInt(unit[4]),Integer.parseInt(unit[5]),Integer.parseInt(unit[6])));
		}
		return output;
	}
	
}
//represents a player's information in battle and is also used in matchmaking
class Player {
	String login;
	int level;
	String armyName;
	Player(String login, String armyName, BattleMgmt instance) throws Exception{
		this.login = login;
		this.level = instance.accounts.getLevel(login);
		this.armyName = armyName;
	}
	public String getName(){
		return login;
	}
	public int getLevel(){
		return level;
	}
	public String getArmyName(){
		return armyName;
	}
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
	private int ID;
	
	public Unit(int unitID, String type, int maxhp, int attack, int defence, int lv){
		this.ID = unitID;
		this.Type = type;
		this.Name = type;
		this.Max_HP = maxhp;
		this.Curr_HP = maxhp;
		this.Attack = attack;
		this.Defence = defence;
		this.BP = 0;  //need to implement how to calculate the battle points	
		this.Lv = lv;
		this.Exp = 0;
		
	}
	public int getID(){
		return this.ID;
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
