package client;

import java.util.Random;

public class Spot {
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
