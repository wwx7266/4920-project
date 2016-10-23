import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Engine {
	private final int SWORDSMAN = 0;
	private final int PIKEMAN = 1;
	private final int KNIGHT = 2;
	private final int ARCHER = 3;
	
	private final double ADVANTAGE = 1.1;
	private final double EQUAL = 1;
	private final double DISADVANTAGE = 0.9;

	private final double HITRATE = 0.85;
	private final double CRITICALHITRATE = 0.15;
	private final double DODGERATE = 0.10;
	
	private final double DOUBLESTRIKECHANCE = 0.10;
	
	private Object obj;
	
	private Unit turnPriorityUnit, noPriorityUnit; 
	
	private Random rand;
	
	public Engine() {
		rand = new Random(System.currentTimeMillis());
	}
	
	public Engine(Object obj) {
		rand = new Random(System.currentTimeMillis());
		this.obj = obj;
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
				printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority\n");
			}else{
				turnPriorityUnit = unit2;
				noPriorityUnit = unit1;
				printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority\n");
			}
				
		}else{
			if(unit1.getType() == 3){
				turnPriorityUnit = unit1;
				noPriorityUnit = unit2;
				printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority\n");
			}else if (unit2.getType() == 3){
				turnPriorityUnit = unit2;
				noPriorityUnit = unit1;
				printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority\n");
			}
			else{
				Boolean temp = rand.nextBoolean();
				if(temp){
					turnPriorityUnit = unit1;
					noPriorityUnit = unit2;
					printLog("Unit " + unit1.getTypeName() + "("  + unit1.getOwner() + ") has Turn Priority\n");
				}else{
					turnPriorityUnit = unit2;
					noPriorityUnit = unit1;
					printLog("Unit " + unit2.getTypeName() + "("  + unit2.getOwner() +  ") has Turn Priority\n");
				}
			}
		}
		
	}
	
	private void attack(Unit unit1,Unit unit2){
		rand = new Random(System.currentTimeMillis());
		if(rand.nextDouble()<HITRATE){
			dealDamage(unit1,unit2);
		}else{
			printLog(unit1.getName() + "" + unit1.getTypeName() + " (" + unit1.getOwner() + ") attack missed\n");
		}
		if(unit2.isDead()){
			printLog("Unit "+ unit2.getName() + "" + unit2.getTypeName() + " (" + unit2.getOwner() + ") has been defeated in battle\n");
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
		damage = isCriticalHit(damage);
		damage -= unit2.getDefence();
		if(damage<0){
			damage = 0;
		}
		unit2.dealDamage(damage);
		printLog(unit1.getName() + "" + unit1.getTypeName() + " (" + unit1.getOwner() + ") deals " + damage + " damage to" +
				unit2.getName() + "" + unit2.getTypeName() + " HP:" + unit2.getCurrentHP() + "/" + unit2.getMaxHP() + "\n");
				
	}
	
	private int isCriticalHit(int damage){
		if( rand.nextInt(100)<(CRITICALHITRATE*100) ){
			printLog("CRITICAL HIT! ");
			return (2 * damage);
		}
		else{
			return damage;
		}
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
	
	// ONLY FOR USE WITH SANDBOX GUI
	private void printLog(String text){
		System.out.print(text);
		((SandBoxGUI) obj).addTextToLog(text);
	}
}
