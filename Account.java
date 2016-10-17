package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Account {
	String jdbc_Driver = "com.mysql.jdbc.Driver";
    
	String db_Address = "jdbc:mysql://localhost/";
	String db_Name    = "Game";

    String userName = "Game";
    String password = "admin";
    
	public Account(){
		
	}
	//will register given user if not in database, if user is in database then will return false
	public boolean register(String login, String pswrd){
		String command = "INSERT INTO account (Login, Password, Level, gold) VALUES ('"+login+"', '"+pswrd+"', '0', '0')";
		try {
			executeSQL(command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//will login user if exist in database and has matching password, if login fails, will return false
	public boolean login(String login, String pswrd) throws SQLException{
		ArrayList<String> userList = new ArrayList<String>();
		ArrayList<String> pass = new ArrayList<String>();
		String command = "SELECT Login, Password FROM account WHERE Login = '"+login+"' AND Password = '"+pswrd+"'";
		
		Statement stat = null;
	    Connection conn = null;
	    ResultSet result = null;
		try{
		Class.forName(jdbc_Driver);
		String url = db_Address+db_Name;
		conn = DriverManager.getConnection(url, userName, password);
		stat = conn.createStatement();
		result = stat.executeQuery(command);
		
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found !");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("An error occure when excute SQL!");
			e.printStackTrace();
		} 
		
		while(result.next()){
			userList.add(result.getString("Login"));
			pass.add(result.getString("Password"));
		}
		
		stat.close();
		conn.close();
		if(!userList.isEmpty()){
			return true;
		}
		return false;
	}
	//changes the amount of gold in a person's account, returns false if not enough funds or sql error
	public boolean changeFunds(String login, int amount) throws Exception{
		int funds = getFunds(login);
		if((amount < 0) && (funds < amount)){
			return false;
		} else {
			funds = funds + amount;
		}
		String command = "UPDATE account SET Gold = '"+funds+"' WHERE Login = '"+login+"'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//gets current account balance, -1 if funds arn't available
	public int getFunds(String login) throws NumberFormatException, SQLException{
		int funds = -1;
		String command = "SELECT Gold FROM account WHERE Login = '"+login+"'";
		
		Statement stat = null;
	    Connection conn = null;
	    ResultSet result = null;
		try{
		Class.forName(jdbc_Driver);
		String url = db_Address+db_Name;
		conn = DriverManager.getConnection(url, userName, password);
		stat = conn.createStatement();
		result = stat.executeQuery(command);
		
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found !");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("An error occure when excute SQL!");
			e.printStackTrace();
		} 
		
		while(result.next()){
			funds = Integer.parseInt(result.getString("Gold"));
		}
		
		stat.close();
		conn.close();
		return funds;
	}
	//increments the level of a particular player
	public void incrementLevel(String login) throws Exception{
		String command = "UPDATE account SET Level = Level + '1' WHERE Id = '" + getID(login) + "'";
		try {
			executeSQL(command);
		} catch (Exception e) {
		}
	}
	//Initializes table in database
	public void initiateCredentials() throws Exception{
	    Statement stat = null;
	    Connection conn = null;
		try{
	    	Class.forName(jdbc_Driver);
	    	String url = db_Address + db_Name;

	    	conn = DriverManager.getConnection(url, userName, password);
	    	stat = conn.createStatement();
	    	stat.executeUpdate("create table account( "
	    			+ "Id int PRIMARY KEY NOT NULL AUTO_INCREMENT, "
	    			+ "Login varchar(30) BINARY NOT NULL, "
	    			+ "Password varchar(60) BINARY NOT NULL,"
	    			+ "Level int NOT NULL, "
	    			+ "Gold int Not NULL, "
	    			+ "UNIQUE (Login) "
	    			+ ")");
	    	
	    	}catch(ClassNotFoundException e){
	    		System.out.println("Class Not Found !");
	    		e.printStackTrace();
	    	}catch(SQLException e){
	    		System.out.println("An error occure when excute SQL!");
	    		e.printStackTrace();
	    	} 
	    	stat.close();
	    	conn.close();
	}
	//retrieves friends associated with a single individual
	public ArrayList<String> displayFriends(String login) throws Exception{
		
		ArrayList<String> returnResult = new ArrayList<String>();
		String command = "SELECT * " +
						 "FROM Friends a, Account b " +
						 "WHERE a.Id = b.Id AND b.Login = '"+login+"'";
		
		Statement stat = null;
	    Connection conn = null;
	    ResultSet result = null;
		try{
		Class.forName(jdbc_Driver);
		String url = db_Address+db_Name;
		conn = DriverManager.getConnection(url, userName, password);
		stat = conn.createStatement();
		result = stat.executeQuery(command);
		
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found !");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("An error occure when excute SQL!");
			e.printStackTrace();
		} 
		
		while(result.next()){
			returnResult.add(result.getString("friend"));
		}
		
		stat.close();
		conn.close();
		
		return returnResult;
	}
	//adds a name to the friends list
	public boolean addFriend(String login, String friend) throws Exception{
		if(login.equals(friend)){
			return false;
		}
		int userID = getID(login);
		String command = "INSERT INTO friends (Id, Friend) VALUES ('"+userID+"', '"+friend+"')";
		try {
			executeSQL(command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	//delete friend association between user and friend
	public boolean deleteFriend(String login, String friend) throws Exception{
		int userID = getID(login);
		String command = "DELETE FROM friends WHERE Id = '"+userID+"' AND Friend = '"+friend+"'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//Initializes friends list, only one copy can exist at any one time
	public void initiateFriendsList() throws Exception{
		Statement stat = null;
	    Connection conn = null;
		try{
	    	Class.forName(jdbc_Driver);
	    	String url = db_Address + db_Name;

	    	conn = DriverManager.getConnection(url, userName, password);
	    	stat = conn.createStatement();
	    	stat.executeUpdate("create table friends( "
	    			+ "Id int NOT NULL, "
	    			+ "Friend varchar(30) BINARY NOT NULL, "
	    			+ "UNIQUE (Id, Friend) "
	    			+ ")");
	    	
	    	}catch(ClassNotFoundException e){
	    		System.out.println("Class Not Found !");
	    		e.printStackTrace();
	    	}catch(SQLException e){
	    		System.out.println("An error occure when excute SQL!");
	    		e.printStackTrace();
	    	} 
	    	stat.close();
	    	conn.close();
	}
	//army and unit functions
	public boolean createUnit(String login, String type, String army) throws Exception{
		//type initialized, (type, attack, defense, EXP)
		String[] type1 = {"Swordsman","2", "2","0"};
		String[] type2 = {"Archer","3", "1","0"};
		String[] type3 = {"Knight","1", "3","0"};
		String[] in = null;
		//load unit type for input into DB
		if(type.equals("Swordsman")){
			in = type1;
		}else if(type.equals("Archer")){
			in = type2;
		}else if(type.equals("Knight")){
			in = type3;
		} else {
			return false;
		}
		//insert into db
		int userID = getID(login);
		String command = "INSERT INTO roster (Owner, UnitType, ArmyName, Attack, Defense, EXP) VALUES ('"+userID+"', '"+in[0]+"', '"+army+"', '"+in[1]+"', '"+in[2]+"', '"+in[3]+"')";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//creates an army given a starting unit type
	public boolean createArmy(String login, String army, String initUnitType) throws Exception{
		return createUnit(login, initUnitType, army);
	}
	//outputs a string representation of an army for a given user
	public String displayArmy(String login) throws Exception{
		String output = "";
		String command = "SELECT * FROM roster WHERE Owner = '"+getID(login)+"'";
		
		Statement stat = null;
	    Connection conn = null;
	    ResultSet result = null;
		try{
		Class.forName(jdbc_Driver);
		String url = db_Address+db_Name;
		conn = DriverManager.getConnection(url, userName, password);
		stat = conn.createStatement();
		result = stat.executeQuery(command);
		
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found !");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("An error occure when excute SQL!");
			e.printStackTrace();
		} 
		while(result.next()){
			output = output + "##"+result.getString("UnitId")
					+ "##" + result.getString("UnitName")
					+ "##" + result.getString("UnitType")
					+ "##" + result.getString("ArmyName")
					+ "##" + result.getString("Attack")
					+ "##" + result.getString("Defense")
					+ "##" + result.getString("EXP");
					
		}
		stat.close();
		conn.close();
		
		return output;
	}

	//moves a unit between users given a new owner user and a new army name, if a new army for that user is
	//specified, then that army is created with that unit as the sole occupant(useful when selling)
	public boolean moveUnit(int unitID, String newOwner, String armyName) throws Exception{
		
		String command = "UPDATE roster SET Owner = '" + getID(newOwner) + "' AND ArmyName = '"+armyName+"' WHERE UnitId = '" + unitID + "'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//moves an army between users given a new owner user (useful when selling)
	public boolean moveArmy(String oldOwner, String armyName,String newOwner, String newArmyName) throws Exception{
		
		String command = "UPDATE roster SET Owner = '" + getID(newOwner) + "', ArmyName = '"+ newArmyName +"' WHERE UnitId IN ('";
		ArrayList<String> IDList = unitIDFromArmy(oldOwner, armyName);
		if(IDList.isEmpty()){
			return false;
		}
		for(String ID : unitIDFromArmy(oldOwner, armyName)){
			command = command  + ID +"', '";
		}
		command = command.substring(0, command.length() - 4) + "')";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//gets ID's of units in a particular army
	public ArrayList<String> unitIDFromArmy(String owner, String armyName) throws Exception{
		ArrayList<String> returnResult = new ArrayList<String>();
		String command = "SELECT UnitID " +
						 "FROM roster " +
						 "WHERE Owner = '"+getID(owner)+"' AND ArmyName = '"+armyName+"'";
		
		Statement stat = null;
	    Connection conn = null;
	    ResultSet result = null;
		try{
		Class.forName(jdbc_Driver);
		String url = db_Address+db_Name;
		conn = DriverManager.getConnection(url, userName, password);
		stat = conn.createStatement();
		result = stat.executeQuery(command);
		
		}catch(ClassNotFoundException e){
			System.out.println("Class Not Found !");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("An error occure when excute SQL!");
			e.printStackTrace();
		} 
		
		while(result.next()){
			returnResult.add(result.getString("UnitId"));
		}
		
		stat.close();
		conn.close();
		
		return returnResult;
	}
	//deletes a unit from the DB given its ID and the owner's user name
	public boolean deleteUnit(String login, int unitID) throws Exception{
		String command = "DELETE FROM roster WHERE UnitId = '"+unitID+"' AND Owner = '"+getID(login)+"'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//deletes an army from the DB given its name and the owner's user name
	public boolean deleteArmy(String login, String armyName) throws Exception{
		String command = "DELETE FROM roster WHERE ArmyName = '"+armyName+"' AND Owner = '"+getID(login)+"'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean levelUnit(int unitID, int attackInc, int defenseInc, int EXPDec){
		//problem may come from '' around number
		String command = "UPDATE roster SET Attack = Attack + '" + attackInc + "' AND Defense = Defense + '"+defenseInc+"' AND EXP = EXP - '"+EXPDec+"' WHERE UnitID = '" + unitID + "'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//Initialize DB for units and armies, shows units and what army they are assigned to
	public void initialiseRoster() throws Exception{
		Statement stat = null;
	    Connection conn = null;
		try{
	    	Class.forName(jdbc_Driver);
	    	String url = db_Address + db_Name;

	    	conn = DriverManager.getConnection(url, userName, password);
	    	stat = conn.createStatement();
	    	stat.executeUpdate("create table roster( "
	    			+ "UnitId int PRIMARY KEY NOT NULL AUTO_INCREMENT, "
	    			+ "Owner int NOT NULL, "
	    			+ "UnitName varchar(30) BINARY, "
	    			+ "UnitType varchar(30) NOT NULL, "
	    			+ "ArmyName varchar(30) BINARY NOT NULL, "
	    			+ "Attack int NOT NULL, "
	    			+ "Defense int NOT NULL, "
	    			+ "EXP int NOT NULL"
	    			+ ")");
	    	
	    	}catch(ClassNotFoundException e){
	    		System.out.println("Class Not Found !");
	    		e.printStackTrace();
	    	}catch(SQLException e){
	    		System.out.println("An error occure when excute SQL!");
	    		e.printStackTrace();
	    	} 
	    	stat.close();
	    	conn.close();
	}
	//end section to test
	// function to execute SQL command (edit table e.g. insert, delete etc.) 
		public void executeSQL(String command) throws Exception{
			String jdbc_Driver = "com.mysql.jdbc.Driver";
		    
			String db_Address = "jdbc:mysql://localhost/";
			String db_Name    = "Game";
		   
		    String userName = "Game";
		    String password = "admin";

		    Statement stat = null;
		    Connection conn = null;
	    	try{
	    	Class.forName(jdbc_Driver);
	    	String url = db_Address+db_Name;
	    	conn = DriverManager.getConnection(url, userName, password);
	    	stat = conn.createStatement();
	    	stat.executeUpdate(command);
	    	
	    	}catch(ClassNotFoundException e){
	    		System.out.println("Class Not Found !");
	    		//e.printStackTrace();
	    	}catch(SQLException e){
	    		System.out.println("An error occure when excute SQL!");
	    		//e.printStackTrace();
	    	} 
	    	stat.close();
	    	conn.close();	
		}
		
		//gets the DB id for a particular user
		public int getID(String login) throws Exception{
			int returnResult = 0;
			String command = "SELECT * " +
							 "FROM Account " +
							 "WHERE Login = '"+login+"'";
			
			Statement stat = null;
		    Connection conn = null;
		    ResultSet result = null;
			try{
			Class.forName(jdbc_Driver);
			String url = db_Address+db_Name;
			conn = DriverManager.getConnection(url, userName, password);
			stat = conn.createStatement();
			result = stat.executeQuery(command);
			
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found !");
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("An error occure when excute SQL!");
				e.printStackTrace();
			} 
			
			while(result.next()){
				returnResult = Integer.parseInt(result.getString("Id"));
			}
			
			stat.close();
			conn.close();
			
			return returnResult;
		}
		//retrieves the current amount of gold in the user's account
		public int getGold(String login) throws Exception{
			int returnResult = 0;
			String command = "SELECT * " +
							 "FROM Account " +
							 "WHERE Login = '"+login+"'";
			
			Statement stat = null;
		    Connection conn = null;
		    ResultSet result = null;
			try{
			Class.forName(jdbc_Driver);
			String url = db_Address+db_Name;
			conn = DriverManager.getConnection(url, userName, password);
			stat = conn.createStatement();
			result = stat.executeQuery(command);
			
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found !");
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("An error occure when excute SQL!");
				e.printStackTrace();
			} 
			
			while(result.next()){
				returnResult = Integer.parseInt(result.getString("Gold"));
			}
			
			stat.close();
			conn.close();
			
			return returnResult;
		}	
		//retrieves the current level of the target account
		public int getLevel(String login) throws Exception{
			int returnResult = 0;
			String command = "SELECT * " +
							 "FROM Account " +
							 "WHERE Login = '"+login+"'";
			
			Statement stat = null;
		    Connection conn = null;
		    ResultSet result = null;
			try{
			Class.forName(jdbc_Driver);
			String url = db_Address+db_Name;
			conn = DriverManager.getConnection(url, userName, password);
			stat = conn.createStatement();
			result = stat.executeQuery(command);
			
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found !");
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("An error occure when excute SQL!");
				e.printStackTrace();
			} 
			
			while(result.next()){
				returnResult = Integer.parseInt(result.getString("Level"));
			}
			
			stat.close();
			conn.close();
			
			return returnResult;
		}	
		public String getLogin(int ID) throws Exception{
			String returnResult = null;
			String command = "SELECT * " +
							 "FROM Account " +
							 "WHERE Id = '"+ID+"'";
			
			Statement stat = null;
		    Connection conn = null;
		    ResultSet result = null;
			try{
			Class.forName(jdbc_Driver);
			String url = db_Address+db_Name;
			conn = DriverManager.getConnection(url, userName, password);
			stat = conn.createStatement();
			result = stat.executeQuery(command);
			
			}catch(ClassNotFoundException e){
				System.out.println("Class Not Found !");
				e.printStackTrace();
			}catch(SQLException e){
				System.out.println("An error occure when excute SQL!");
				e.printStackTrace();
			} 
			
			while(result.next()){
				returnResult = result.getString("Login");
			}
			
			stat.close();
			conn.close();
			
			return returnResult;
		}	
}
