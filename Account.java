
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
    

    
	Account(){
		
	}
	//will register given user if not in database, if user is in database then will return false
	public boolean register(String login, String pswrd){
		String command = "INSERT INTO account (Login, Password) VALUES ('"+login+"', '"+pswrd+"')";
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
			//for(String check: pass){
			//	if(check.equals(pswrd)){
			//		return true;
			//	}
			//}
			return true;
		}
		return false;
	}
	//Initializes table in database
	public void initiate() throws Exception{
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
	    			+ "Password varchar(60) BINARY NOT NULL, "
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
}
