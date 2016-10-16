package Server;

import java.sql.*;
import java.util.ArrayList;

public class Market implements DataBase_interface{
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
	//purchases a unit and deliverers it to the specified owner
	public boolean buy(String login, int UnitId){
		//retrieve price
		//compare with purchaser gold
		//if good, transfer unit and gold and return true
		//else return false
	}
	// displays all unit data and prices on sell list
	public String display(){
		//join roster and market on UnitId and return relevant info
	}
	/*
	// function to buy unit by player (delete a row in market table)
	public boolean buy(int id, int money) throws Exception{
		String command = "SELECT * FROM Market WHERE Id = " + String.valueOf(id);
		
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
		if(result.getInt("Price") > money){
			System.out.println("Not Enough Money!");
	    	stat.close();
	    	conn.close();
			return false;
		}else{
			command = "DELETE FROM Market WHERE Id = " + String.valueOf(id);
	    	stat.close();
	    	conn.close();
			executeSQL(command);
			return true;
		}
	}
	
	// function to search item by given type and print out result	
	public void search_item(String item ) throws Exception{
		String command = "Select * FROM market WHERE Unit_type = '" + item + "'";
		//ResultSet result =  search_data(command);
		//test
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
		//end test
		while (result.next())
	        {
	            System.out.println(result.getInt("id") 
	            				   + " Unit type: " 
	            				   + result.getString("Unit_type") 
	            				   + " Unit_name: "
	            				   + result.getString("Unit_name")
	            				   + " Price: "
	            				   + result.getInt("price"));
	        }
    	}
    	//start test
    	catch(ClassNotFoundException e){
    		System.out.println("Class Not Found !");
    		e.printStackTrace();
    	}catch(SQLException e){
    		System.out.println("An error occure when excute SQL!");
    		e.printStackTrace();
    	} 
    	stat.close();
    	conn.close();
    	//end test
	}
	*/
	// function to initialize a database and create table call Market
	public void createDatabase() throws Exception{
		 
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
		
}
