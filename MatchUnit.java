package Client;

public class MatchUnit {
	String owner = null;
	int unitID = -1;
	String about = null;
	MatchUnit(String owner, int unitID, String about){
		this.owner = owner;
		this.unitID = unitID;
		this.about = about;
	}
	public String getOwner(){
		return owner;
	}
	public int getUnitID(){
		return unitID;
	}
	String getDescription(){
		return about;
	}
}
