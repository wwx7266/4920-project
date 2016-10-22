package Server;

import java.util.*;
import java.util.concurrent.TimeUnit;

//holds a list of ongoing battle objects
//determines from player level who should be matched with who
//holds a list of users currently in matchmaking
public class BattleMgmt{
	
	ArrayList<Battle> battleList = new ArrayList<Battle>();
	ArrayList<Player> queue = new ArrayList<Player>();

	Account accounts = null;
	Market market = null;
	
	BattleMgmt(Account accounts, Market market){
		this.accounts = accounts;
		this.market = market;
	}
	//proceeds with matchmaking with respect to a particular player, returns true if successfully matched, false otherwise
	public boolean matchMaker(String login, String armyName) throws Exception{
		/* 
		 * retrieve player from match maker queue
		 * if not in queue, add to queue
		 * check against existing players in queue
		 * if no similar levels in queue or badly matched armies add 1 pass to player object
		 * else pair into battle object
		 */
		Battle newBattle = null;
		if(!isInQueue(login)){
			addToQueue(login, armyName);
		}
		//set the upper and lower boundaries for the matchmaker to create a match
		Player player = getPlayer(login);
		int lowLevel = player.level - player.passesInQueue;
		int highLevel = player.level + player.passesInQueue;
		double lowArmy = player.armyStrength * ( 0.95 - player.passesInQueue * 0.01);
		double highArmy = player.armyStrength * (1.05 - player.passesInQueue * 0.01);
		//compare each player to the criteria
		for(Player match : queue){
			if(match.equals(player)){
				continue;
			}
			if((match.level <= highLevel && match.level >= lowLevel )&&(match.armyStrength <= highArmy && match.armyStrength >= lowArmy)){
				newBattle = new Battle(player, match, this);
				battleList.add(newBattle);
				removeFromQueue(match.getName());
				removeFromQueue(player.getName());
				return true;
			}
		}
		player.passesInQueue++;
		return false;
	}
	//if in battle, returns a formatted string containing the battle object, else returns "false";
	//
	public String isInBattle(String login){
		for(Battle battle : battleList){
			if(battle.player1.getName().equals(login)){
				//format of return:
				//##<player1>=#<unit1>=#<unit2>=##etc..##<player2>=#<unit1>=#etc..
				//<unit> = unitID-#unit.toString
				String returnBattle = "##" + login;
				for(Unit unit : battle.army1){
					returnBattle += "=#" + unit.getID() + "-#" + unit;
				}
				returnBattle += "##" + battle.player2.getName();
				for(Unit unit : battle.army2){
					returnBattle += "=#" + unit;
				}
				return returnBattle;
			}
			if(battle.player2.getName().equals(login)){
				//format of return:
				//##<player1>=#<unit1>=#<unit2>=##etc..##<player2>=#<unit1>=#etc..
				String returnBattle = "##" + login;
				for(Unit unit : battle.army2){
					returnBattle += "=#" + unit;
				}
				returnBattle += "##" + battle.player1.getName();
				for(Unit unit : battle.army1){
					returnBattle += "=#" + unit;
				}
				return returnBattle;
			}
		}
		return "false";
	}
	//removes a battle from the list given the battle object (to be called from within a battle object)
	public void removeBattle(Battle battle){
		battleList.remove(battle);
	}
	//adds a player to the battleQueue
	public boolean addToQueue(String login, String armyName){
		Player toAdd = null;
		//check if already in queue
		if(!isInQueue(login)){
			return true;
		}
		//if not in queue, add
		try{
			toAdd = new Player(login, armyName, this);
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
	//checks if a player is in the queue
	public boolean isInQueue(String login){
		for(Player player: queue){
			if(player.getName().equals(login)){
				return true;
			}
		}
		return false;
	}
	//gets a player in the queue based on login
	public Player getPlayer(String login){
		Player toReturn = null;
		for(Player player: queue){
			if(player.getName().equals(login)){
				toReturn = player;
			}
		}
		return toReturn;
	}
}

class Battle {
	public Player player1 = null;
	public Player player2 = null;
	public ArrayList<Unit> army1 = null;
	public ArrayList<Unit> army2 = null;
	public Unit unitBattle1 = null;
	public Unit unitBattle2 = null;
	public ArrayList<String> battleLog1 = new ArrayList<String>();
	public ArrayList<String> battleLog2 = new ArrayList<String>();
	public boolean finished = false;
	
	public Engine battleEngine = null;
	
	public BattleMgmt instance = null;
	
	public Battle(Player player1, Player player2, BattleMgmt instance) throws Exception{
		this.instance = instance;
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.army1 = getUnits(player1);
		this.army2 = getUnits(player2);
		
		this.battleEngine = new Engine(this);
	}
	
	//retrieves the units in a player's army, if the army dosen't exist, returns null
	public ArrayList<Unit> getUnits(Player player) throws Exception{
		/*ArrayList<Unit> output = new ArrayList<Unit>();
		ArrayList<String[]> units = instance.accounts.displayArmyBattle(player.getName(), player.getArmyName());
		if(units.isEmpty()){
			return null;
		}
		for(String[] unit : units){
			// each unit is of the format (ID, Name, Type, ArmyName, ownerID, Attack, Defense, HP, EXP)
			output.add(new Unit(unit[1],Integer.parseInt(unit[0]) , unitCode(unit[2]), Integer.parseInt(unit[7]), Integer.parseInt(unit[5]), Integer.parseInt(unit[6]), unit[4]));
		}
		return output;*/
		return player.units;
	}
	public void simulateBattle(){
		//check if player both players have moves and both battle logs are empty
		if(!(battleLog1.isEmpty() && battleLog2.isEmpty() && unitBattle1 != null && unitBattle2 != null)){
			return;
		}
		battleEngine.battle(unitBattle1, unitBattle2);
	}
	//converts the unit type into a code for the engine
	public int unitCode(String type){
		if(type.equalsIgnoreCase("Swordsman")){
			return 0;
		}else if(type.equalsIgnoreCase("Pikeman")){
			return 1;
		}else if(type.equalsIgnoreCase("Knight")){
			return 2;
		} else if(type.equalsIgnoreCase("Archer")){
			return 3;
		} else {
			return -1;
		}
	}
}
//represents a player's information in battle and is also used in matchmaking
class Player {
	String login;
	int level;
	String armyName;
	int armyStrength = 0;
	int damage = 0;
	int EXP = 0;
	int passesInQueue = 0;
	ArrayList<Unit> units = new ArrayList<Unit>();
	Player(String login, String armyName, BattleMgmt instance) throws Exception{
		this.login = login;
		this.level = instance.accounts.getLevel(login);
		this.armyName = armyName;
		getUnits(instance);
	}
	//populates the player object with a unit list and calculates the strength of the player army
	private void getUnits(BattleMgmt instance) throws Exception {
		ArrayList<String[]> units = instance.accounts.displayArmyBattle(login, armyName);
		this.armyStrength = 0;
		for(String[] unit : units){
			// each unit is of the format (ID, Name, Type, ArmyName, ownerID, Attack, Defense, HP, EXP)
			this.units.add(new Unit(unit[1],Integer.parseInt(unit[0]) , unitCode(unit[2]), Integer.parseInt(unit[7]), Integer.parseInt(unit[5]), Integer.parseInt(unit[6]), unit[4]));
			this.armyStrength += Integer.parseInt(unit[7]);
		}
	}
	//getter methods
	public String getName(){
		return login;
	}
	public int getLevel(){
		return level;
	}
	public String getArmyName(){
		return armyName;
	}
	public int unitCode(String type){
		if(type.equalsIgnoreCase("Swordsman")){
			return 0;
		}else if(type.equalsIgnoreCase("Pikeman")){
			return 1;
		}else if(type.equalsIgnoreCase("Knight")){
			return 2;
		} else if(type.equalsIgnoreCase("Archer")){
			return 3;
		} else {
			return -1;
		}
	}
}
class Unit {
	private final int SWORDSMAN = 0;
	private final int PIKEMAN = 1;
	private final int KNIGHT = 2;
	private final int ARCHER = 3;
	
	private String name;
	private int id;
	private int type;
	private int max_HP;
	private int current_HP;
	private int attack;
	private int defense;
	private int damageDelt;
	private int unitsDestroyed;
	private int exp;
	private String owner;
	private Boolean isDead;
	
	public Unit(int type, int hp, int attack, int defense){
		this.name = " ";
		this.type = type;
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = attack;
		this.defense = defense;
		this.exp = 0;
		this.damageDelt = 0;
		this.unitsDestroyed = 0;
		this.isDead = false;
	}
	
	public int getID() {
		return this.id;
	}

	public Unit(String name, int type, int hp, int attack, int defense){
		this.name = name;
		this.type = type;
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = attack;
		this.defense = defense;
		this.exp = 0;
		this.damageDelt = 0;
		this.unitsDestroyed = 0;
		this.isDead = false;
	}
	
	public Unit(String name, int id, int type, int hp, int attack, int defense, String owner){
		this.name = name;
		this.id = id;
		this.type = type;
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = attack;
		this.defense = defense;
		this.exp = 0;
		this.damageDelt = 0;
		this.unitsDestroyed = 0;
		this.owner = owner;
		this.isDead = false;
	}
	
	public String toString(){
		return getTypeName() + "  -  HP: " + current_HP + "/" + max_HP + "   ATK: " + attack + "   DEF: " + defense;		
	}
	
	public String getUnitDetail() {
		return name + "/" + type + " - HP: " + current_HP + "/" + max_HP + " ATK: " + attack + " DEF: " + defense;	
	}
	
	// GETTERS AND SETTERS
	public String getName(){
		return name;
	}
 	public String getTypeName(){
 		switch(type){
 		case 0:
 			return "Swordsman";
 		case 1:
 			return "Pikeman";
 		case 2:
 			return "Knight";
 		case 3:	
 			return "Archer";
 		default:
 			return "Unknown";
 		}
	}
 	public int getType(){
 		return type;
 	}
 	public String getOwner(){
 		return owner;
 	}
	public int getMaxHP(){
		return max_HP;
	}
	public int getCurrentHP(){
		return current_HP;
	}
	public int getAttack(){
		return attack;
	}
	public int getDefence(){
		return defense;
	}
	public int getExp(){
		return exp;
	}
	public int getDamageDelt(){
		return damageDelt;
	}
	public int getUnitsDestroyed(){
		return unitsDestroyed;
	}
	
	public void setOwner(String name){
		this.owner = name;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setType(int type){
		this.type = type;
	}
	public void set_Curr_HP(int x){
		current_HP = x;
	}
	public void dealDamage(int damage){
		current_HP -= damage;
		if(current_HP<=0){
			isDead = true;
			current_HP = 0;
		}
	}
	public Boolean isDead(){
		return isDead;
	}
}
class Engine {
	private final int SWORDSMAN = 0;
	private final int PIKEMAN = 1;
	private final int KNIGHT = 2;
	private final int ARCHER = 3;
	
	private final double ADVANTAGE = 1.1;
	private final double EQUAL = 1;
	private final double DISADVANTAGE = 0.9;

	private final double HITRATE = 0.85;
	
	private Unit turnPriorityUnit, noPriorityUnit; 
	
	private Random rand;
	
	private Battle battle;
	
	public Engine(Battle battle) {
		rand = new Random(System.currentTimeMillis());
		this.battle = battle;
	}
	
	// resolve battle between a & b
	// for sandbox 1 is player, 2 is enemy
	public void battle(Unit unit1, Unit unit2){
		determineTurnOrder(unit1, unit2);
		while(!unit1.isDead()&&!unit2.isDead()){
			pause();
			attack(turnPriorityUnit,noPriorityUnit);
			if(!noPriorityUnit.isDead()){
				pause();
				attack(noPriorityUnit,turnPriorityUnit);
			}
			
		}
	}
	
	private void determineTurnOrder(Unit unit1,Unit unit2){
		rand = new Random(System.currentTimeMillis());
		if(unit1.getType() == unit2.getType()){
			Boolean temp = rand.nextBoolean();
			if(temp){
				turnPriorityUnit = unit1;
				noPriorityUnit = unit2;
				printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority");
			}else{
				turnPriorityUnit = unit2;
				noPriorityUnit = unit1;
				printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority");
			}
				
		}else{
			if(unit1.getType() == 3){
				turnPriorityUnit = unit1;
				noPriorityUnit = unit2;
				printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority");
			}else if (unit2.getType() == 3){
				turnPriorityUnit = unit2;
				noPriorityUnit = unit1;
				printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority");
			}
			else{
				Boolean temp = rand.nextBoolean();
				if(temp){
					turnPriorityUnit = unit1;
					noPriorityUnit = unit2;
					printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority");
				}else{
					turnPriorityUnit = unit2;
					noPriorityUnit = unit1;
					printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority");
				}
			}
		}
		
	}
	
	private void attack(Unit unit1,Unit unit2){
		rand = new Random(System.currentTimeMillis());
		if(rand.nextDouble()<=HITRATE){
			dealDamage(unit1,unit2);
		}else{
			printLog("The Unit " + unit1.getName() + "" + unit1.getTypeName() + " (" + unit1.getOwner() + ") attack missed");
		}
		if(unit2.isDead()){
			printLog("Unit "+ unit2.getName() + "" + unit2.getTypeName() + " (" + unit2.getOwner() + ") has been defeated in battle");
		}
	}
	
	// first one attacking 2nd one
	private void dealDamage(Unit unit1,Unit unit2){
		double modifier=0.0;
		int damage = 0;
		if(unit1.getType() == 0){
			switch(unit2.getType()){
			case 0:
				modifier = EQUAL;
				break;
			case 1:
				modifier = DISADVANTAGE;
				break;
			case 2:
				modifier = ADVANTAGE;
				break;
			case 3:
				modifier = ADVANTAGE;
				break;
			}
			
		}else if(unit1.getType() == 1){
			switch(unit2.getType()){
			case 0:
				modifier = ADVANTAGE;
				break;
			case 1:
				modifier = EQUAL;
				break;
			case 2:
				modifier = DISADVANTAGE;
				break;
			case 3:
				modifier = ADVANTAGE;
				break;
			}
		}else if(unit1.getType() == 2){
			switch(unit2.getType()){
			case 0:
				modifier = DISADVANTAGE;
				break;
			case 1:
				modifier = ADVANTAGE;
				break;
			case 2:
				modifier = EQUAL;
				break;
			case 3:
				modifier = ADVANTAGE;
				break;
			}
		}else if(unit1.getType() == 3){
			switch(unit2.getType()){
			case 0:
				modifier = DISADVANTAGE;
				break;
			case 1:
				modifier = DISADVANTAGE;
				break;
			case 2:
				modifier = DISADVANTAGE;
				break;
			case 3:
				modifier = EQUAL;
				break;
			}
		}
		damage = (int)(unit1.getAttack()*modifier);
		damage = terrainModifier(damage);
		damage -= unit2.getDefence();
		if(damage<0){
			damage = 0;
		}
		unit2.dealDamage(damage);
		printLog(unit1.getName() + "" + unit1.getTypeName() + " (" + unit1.getOwner() + ") deals " + damage + " damage to" +
				unit2.getName() + "" + unit2.getTypeName() + " HP:" + unit2.getCurrentHP() + "/" + unit2.getMaxHP());
				
	}
	
	//adds the move result to the battle log in the battle object
	private void printLog(String string) {
		battle.battleLog1.add(string);
		battle.battleLog2.add(string);
	}

	// unit a attacking unit b
	private int hitRate(int a, int b){
		
		return 0;
	}
	
	// for future use
	private int terrainModifier(int damage){
		double modifier = 1.0;
		return (int)(modifier *  damage);
	}
	
	private void pause(){
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}