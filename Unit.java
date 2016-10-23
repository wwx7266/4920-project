public class Unit {
	private final int SWORDSMAN = 0;
	private final int PIKEMAN = 1;
	private final int KNIGHT = 2;
	private final int ARCHER = 3;
	
	private String name;
	private int type;
	private int max_HP;
	private int current_HP;
	//private int BP;					// DO KNOW WHAT BATTLE POINTS IS
	private int attack;
	private int defense;
	//private int level;				// NOT USED ATM
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
		this.isDead = false;
	}
	
	public Unit(String name, int type, int hp, int attack, int defense){
		this.name = name;
		this.type = type;
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = attack;
		this.defense = defense;
		this.exp = 0;		
		this.isDead = false;
	}
	
	public Unit(String name, int type, int hp, int attack, int defense, String owner){
		this.name = name;
		this.type = type;
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = attack;
		this.defense = defense;
		this.exp = 0;		
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
	
	public void setOwner(String name){
		this.owner = name;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setType(int type){
		this.type = type;
	}
	public void setStats( int hp, int atk, int def){
		this.max_HP = hp;
		this.current_HP = hp;
		this.attack = atk;
		this.defense = def;
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
	
	
/**
 * 	public void set_Max_HP(int x){
		max_HP = x;
	}
	public void set_BP(int x ){
		BP = x;
	}
	public void set_Attack(int x){
		attack  = x;
	}
//	public void set_Defence(int x){
//		Defence = x;
//	}
	public void set_Lv(int x){
		level = x;
	}
	
	private void less(){
		current_HP--;
	}
**/
}
