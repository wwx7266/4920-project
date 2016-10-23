package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.Account;

public class ServerConnection{
	
	ServerSocket server = null;
	int serverPort = 2500;
	Account accounts = null;
	Market markets = null;
	BattleMgmt battleManager = null;
	//Initializes a server with a specified port
	ServerConnection(int serverPort, Account accounts, Market markets, BattleMgmt battleManager){
		this.accounts = accounts;
		this.markets = markets;
		this.battleManager = battleManager;
		try {
			this.server = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("couldn't open a socket on port " + serverPort);
			System.exit(0);
			return;
		}
	}
	
	//Initializes a server with the default 2500 port
	ServerConnection(){
		this.accounts = new Account();
		try {
			this.server = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("couldn't open a socket on port " + serverPort);
			System.exit(0);
			return;
		}
	}
	public void run(){
		String output = null;
		
		while(true){
			Socket connection = null;
			try {
				connection = server.accept();
			} catch (IOException e) {
				System.out.println("failed to accept new connection");
				continue;
			}
			System.out.println("accepted new connection from: " + connection);
			
			BufferedReader incomingData = null;
			try {
				incomingData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} catch (IOException e1) {
				System.out.println("couldn't establish a connection");
				continue;
			}
			
			String sepInput[] = null;
			try {
				sepInput = incomingData.readLine().split("##");
			
				output = processRequest(sepInput);
			} catch (Exception e) {
				System.out.println("couldn't read from connection");
			}
	
			DataOutputStream outgoingData = null;
			try {
				outgoingData = new DataOutputStream(connection.getOutputStream());
				outgoingData.writeBytes(output);
			} catch (IOException e) {
				System.out.println("couldn't send to output stream");
			}
			
			
			
			try {
				incomingData.close();
			
				outgoingData.close();
				connection.close();
			} catch (IOException e) {
				System.out.println("couldn't close connection properly");
			}
		}
	}

	private String processRequest(String[] sepInput) throws Exception {
		String user = null;
		String password = null;
		Object userAccount = null;
		boolean purchase = false;
		user = sepInput[1];
		password = sepInput[2];
		
			if(sepInput[3].equals("register")){
				//create user account and return true if successful
				return "SvrRes##"+user+"##"+accounts.register(user, password);
				
			} else if(sepInput[3].equals("login")){
				//indicate that account exists and log user in
				String output = null;
				if(accounts.login(user, password)){
					output = accounts.displayArmy(user);
				} else {
					output = "##Couldn't Login";
				}
				return "SvrRes##"+user+output;
				
			} else if(sepInput[3].equals("battleReq")){
				//input user credentials, battle mode then army id to get the matched opponent
				//check if the unit is in battle
				
				return testBattleMatchMaker(user, sepInput[4], this.battleManager);
				
			} else if(sepInput[3].equals("battleMove")){
				//input split string and receive a full set of battle instructions
				//return is of form svrRes##user##<state>##<output>
				//<output> = <unitSummary>##<messageLog>
				//<messageLog> = <message1>-#<message2>-# etc...
				//<unitSummary> = <unitID1>-#<owner1>-#<unit1Summary>=#<unitID2>-#<owner2>-#<unit2Summary>
				
				return testBattleMove(user, sepInput[4], this.battleManager);
				
			} else if(sepInput[3].equals("infoReq")){
				//input info request, receive account info
				return "SvrRes##" + user + accounts.displayArmy(user);
				
			} else if(sepInput[3].equals("infoReqEcon")){
				//input economy request, receive available units for purchase
				String display = markets.display();
				return "SvrRes##"+user+"##"+accounts.getGold(user)+"##"+display;
				
			} else if(sepInput[3].equals("purchase")){
				//send a purchase request and receive a true/false result based on price of unit and available resources
				purchase = markets.buy(user, Integer.parseInt(sepInput[4]), sepInput[5]);
				return "SvrRes##"+user+"##purchase##"+accounts.getGold(user)+"##"+purchase;

			}else if(sepInput[3].equals("sell")){
				//send a purchase request and receive a true/false result based on price of unit and available resources
				purchase = markets.sell(user, Integer.parseInt(sepInput[5]), Integer.parseInt(sepInput[6]));
				return "SvrRes##"+user+"##sell##"+accounts.getGold(user)+"##"+purchase;

			} else if(sepInput[3].equals("chatUp")){
				//send a message to server
				//chatUp(user, password, sepInput[4]);
				return "SvrRes##"+user+"##chatUp##true";
			} else if(sepInput[3].equals("friendOp")){
				//check what operation must be done on the friends list with respect to the owner
				if(sepInput[4].equals("display")){
					//display all friends associated with user
					ArrayList<String> friends = accounts.displayFriends(user);
					String output = "SvrRes##"+user+"##friendOp";
					for(String friend : friends){
						output = output + "##" + friend;
					}
					return output;
				} else if(sepInput[4].equals("add")){
					//add friend relationship to db
					return "SvrRes##"+user+"##friendOp##"+accounts.addFriend(user, sepInput[5]);
				} else if(sepInput[4].equals("delete")){
					//remove friend relationship to db
					return "SvrRes##"+user+"##friendOp##"+accounts.deleteFriend(user, sepInput[5]);
				}
			}
		return null;
	}
	//provokes the calculation of the battle and the creation of the output stream
	private static String testBattleMove(String user, String unitID, BattleMgmt battleManagement){
		String output = "";
		int player = 0;
		Battle battle = battleManagement.getBattle(user);
		//select unit to be battled against
		if(battle.player1.login.equals(user)){
			for(Unit unit : battle.army1){
				if(unit.getID() == Integer.parseInt(unitID)){
					battle.unitBattle1 = unit;
					player = 1;
				}
			}
		} else {
			for(Unit unit : battle.army2){
				if(unit.getID() == Integer.parseInt(unitID)){
					battle.unitBattle2 = unit;
					player = 2;
				}
			}
		}
		//simulate battle
		boolean firstLoop = true;
		output = battleManagement.getBattle(user).getState() + "##";
		for(String message : battleManagement.getBattle(user).simulateBattle(user)){
			if(firstLoop){
				output = battleManagement.getBattle(user).getState() + "##";
				firstLoop = false;
			}
			output += message + "-#";
		}
		//add battle ended tag if battle has ended
		
		//remove trailing -#
		output = output.substring(0, output.length()-2);
		output += "##";
		for(Unit unit : battleManagement.getBattle(user).army1){
			output += unit.getID() + "-#" + unit.getOwner() + "-#" + unit.getUnitDetail() + "=#";
		}
		//remove trailing +#
		output = output.substring(0, output.length()-2);
		//add all messages
		output += "##";
		if(player == 1){
			for(String message : battleManagement.getBattle(user).battleLog1){
				output += message + "-#";
			}
			battleManagement.getBattle(user).battleLog1 = new ArrayList<String>();
		} else {
			for(String message : battleManagement.getBattle(user).battleLog2){
				output += message + "-#";
			}
			battleManagement.getBattle(user).battleLog2 = new ArrayList<String>();
		}
		//remove trailing "-#"
		output = output.substring(0, output.length()-2);
		
		return "SvrRes##"+user+"##"+output;
	}
	private static String testBattleMatchMaker(String user, String armyName, BattleMgmt battleManager) throws Exception{
		String armies = battleManager.isInBattle(user);
		if(!armies.equals("false")){
			return "SvrRes##"+user+"##battleReq##"+ armies;
		}
		//add player to queue
		battleManager.addToQueue(user, armyName);
		//call matchmaker on queue
		battleManager.matchMaker(user, armyName);
		//send back information on user and opponent armies
		armies = battleManager.isInBattle(user);
		if(armies.equals("false")){
			return "SvrRes##"+user+"##battleReq##wait";
		}
		return "SvrRes##"+user+"##battleReq"+ armies;
	}
}
