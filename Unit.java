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
	private int pre_turn_exp;
	private int pre_turn_enemy_exp;
	private int exp_change;
	private String battle_case;

	
	public Unit(String type, int maxhp, int attack, int defence, int lv){
		Type = type;
		Name = type;
		Max_HP = maxhp;
		Curr_HP = maxhp;
		Attack = attack;
		//Defence = defence;
		BP = 0;  //need to implement how to calculate the battle points	
		Lv = lv;
		Exp = 0;
		pre_turn_exp = 0;
		pre_turn_enemy_exp = 0;
		exp_change = 0;
		
	}
	
	public void checkLvUp(){
		if(Exp > 150*Lv){
			Lv++;
			Exp = Exp - 150*Lv;
			Max_HP += 2;
			Curr_HP = Max_HP;
		}
		else
			return;
	}
	
	public String show_combat_log() {
		String ret = "";
		if(battle_case.equals("case_1")) {
			ret = "You get " + Integer.toString(exp_change)
					+ " Exp" + "\n"
					+ "Enemy unit destroyed" + "\n";
		} else if(battle_case.equals("case_2")) {
			ret = "Enemy get " + Integer.toString(exp_change)
			+ " Exp" + "\n"
			+ "Your unit destroyed" + "\n";			
		} else if(battle_case.equals("case_3")) {
			ret = "Enemy and Your unit destroyed" + "\n";			
		} else if(battle_case.equals("case_0")) {
			ret = "No unit destroyed" + "\n";			
		}
		return ret;
	}
	
	public String getUnitName() {
		return this.Name;
	}
	
	public String getUnitDetail(String army_name) {
		return "Type: " + Type + "\n"
				 + "Name: " + Name + "\n"
				 + "HP: " + Curr_HP +" " + Max_HP + "\n"
				 + "Attack: " + Attack + "\n"
				// + "Defence: " + Defence + "\n"
				 + "Lv: " + Lv + "\n" + "Exp: " + Exp;	
	}
	
	public int fight(Unit enemy){
		Curr_HP -= enemy.get_Attack();
		enemy.set_Curr_HP(enemy.get_Curr_HP() - Attack);
		
		if(enemy.get_Curr_HP() <= 0 && Curr_HP > 0){
			Exp = Exp + 100 + (enemy.get_Lv() - Lv)*10;
			checkLvUp();
			exp_change = (int)(Exp - pre_turn_exp);
			pre_turn_exp = Exp;
			battle_case = "case_1";
			return 1; //only enemy unit destroyed
		}else if(enemy.get_Curr_HP() > 0 && Curr_HP <= 0){
			enemy.Exp = enemy.Exp + 100 + (Lv - enemy.get_Lv()*10 );
			enemy.checkLvUp();
			exp_change = (int)(enemy.Exp - pre_turn_enemy_exp);
			pre_turn_enemy_exp = enemy.Exp;
			battle_case = "case_2";
			return 2; //only my unit destroyed
		}else if(enemy.get_Curr_HP() <= 0 && Curr_HP <= 0){
			battle_case = "case_3";
			return 3; //both unit destroyed
		}else{
			battle_case = "case_0";
			return 0; //no unit destroyed
		}
	}
	
	public void show_state(){
		System.out.format("Type: %s "
						 + "Name: %s "
						 + "HP: %d/%d "
						 + "Attack: %d "
						// + "Defence: %d "
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
//	public void set_Defence(int x){
//		Defence = x;
//	}
	public void set_Lv(int x){
		Lv = x;
	}
	
	private void less(){
		Curr_HP--;
	}
}
