package Client;

public class Unit {
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
