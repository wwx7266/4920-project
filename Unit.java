package client;

public class Unit {
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
