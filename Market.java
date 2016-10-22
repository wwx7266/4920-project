package Server;

import java.sql.*;
import java.util.ArrayList;

public class Market{
	String jdbc_Driver = "com.mysql.jdbc.Driver";
    
	String db_Address = "jdbc:mysql://localhost/";
	String db_Name    = "Game";

    String userName = "Game";
    String password = "admin";
    
    Account accounts = null;
    
	Market(Account accounts) throws Exception{
		this.accounts = accounts;
	}

	//used to sell units, seller may specify price
	public boolean sell(String seller, int unitID, int amount) throws Exception{
		String command = "INSERT INTO market (UnitId, Owner, Price) VALUES ('"+unitID+"', '"+accounts.getID(seller)+"', '"+amount+"')";
		try {
			executeSQL(command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//returns true if the current unit is for sale
	public boolean isOnMarketList(int unitID) throws SQLException{
		ArrayList<String> unitList = new ArrayList<String>();
		String command = "SELECT UnitId FROM market WHERE UnitId = '"+unitID+"'";
		
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
			unitList.add(result.getString("UnitId"));
		}
		
		stat.close();
		conn.close();
		if(!unitList.isEmpty()){
			return true;
		}
		return false;
	}
	//purchases a unit and deliverers it to the specified owner (to test)
	public boolean buy(String login, int unitID, String armyName) throws Exception{
		int price = getPrice(unitID);
		//only go through with buy action if all transactions pass, else roll back
		if(!this.accounts.changeFunds(this.accounts.getLogin(this.getOwner(unitID)), price)){
			return false;
		}
		if(!this.accounts.changeFunds(login, price * -1)){
			this.accounts.changeFunds(this.accounts.getLogin(this.getOwner(unitID)), price * -1);
			return false;
		}
		if(!this.accounts.moveUnit(unitID, login, armyName)){
			this.accounts.changeFunds(this.accounts.getLogin(this.getOwner(unitID)), price * -1);
			this.accounts.changeFunds(login, price);
			return false;
		}
		remove(unitID);
		return true;
	}
	private boolean remove(int unitID) {
		String command = "DELETE FROM market WHERE UnitId = '"+unitID+"'";
		try {
			executeSQL(command);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	// displays all unit data and prices on sell list(to test)
	public String display() throws SQLException{
		//join roster and market on UnitId and return relevant info
		//gets a string representation of a single unit
		
		String output = null;
		String command = "SELECT * FROM roster a, market b where a.UnitId = b.UnitId";
		
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
			output = "##" + result.getString("UnitId")
					+ "##" + result.getString("UnitName")
					+ "##" + result.getString("UnitType")
					+ "##" + result.getString("ArmyName")
					+ "##" + result.getString("Attack")
					+ "##" + result.getString("Defense")
					+ "##" + result.getString("EXP")
					+ "##" + result.getString("Price");
					
		}
		stat.close();
		conn.close();
		
		return output;
	}
	
	// function to initialize a database and create table call Market
	public void initialiseMarket() throws Exception{
		 
		Statement stat = null;
	    Connection conn = null;
		try{
	    	Class.forName(jdbc_Driver);
	    	String url = db_Address + db_Name;

	    	conn = DriverManager.getConnection(url, userName, password);
	    	stat = conn.createStatement();
	    	stat.executeUpdate("create table market( "
	    			+ "UnitId int PRIMARY KEY NOT NULL, "
	    			+ "Owner int NOT NULL, "
	    			+ "Price int NOT NULL"
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
    		e.printStackTrace();
    	}catch(SQLException e){
    		System.out.println("An error occure when excute SQL!");
    		e.printStackTrace();
    	} 
    	stat.close();
    	conn.close();	
	}
	
	// function to get data from database (but not edit table e.g. select) (inoperable)
	public ResultSet search_data(String command) throws Exception{
		String jdbc_Driver = "com.mysql.jdbc.Driver";
	    
		String db_Address = "jdbc:mysql://localhost/";
		String db_Name    = "Game";
	   
	    String userName = "Game";
	    String password = "admin";

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
    	stat.close();
    	conn.close();	
    	return result;
	}
	//get price of a target unit
	public int getPrice(int unitID) throws Exception{
		int returnResult = 0;
		String command = "SELECT * " +
						 "FROM market " +
						 "WHERE UnitId = '"+unitID+"'";
		
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
			returnResult = Integer.parseInt(result.getString("Price"));
		}
		
		stat.close();
		conn.close();
		
		return returnResult;
	}
	//get the ownerID of a target unit
	public int getOwner(int unitID) throws Exception{
		int returnResult = 0;
		String command = "SELECT * " +
						 "FROM market " +
						 "WHERE UnitId = '"+unitID+"'";
		
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
			returnResult = Integer.parseInt(result.getString("Owner"));
		}
		
		stat.close();
		conn.close();
		
		return returnResult;
	}
}
