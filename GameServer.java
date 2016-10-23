package Server;

import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
	public static void main(String[] args) throws Exception {
		//specify server port
		/*
		int serverPort = 2500;
		
		Account accounts = new Account();
		Market market = new Market(accounts);
		accounts.addMarket(market);
		BattleMgmt battleMgmt = new BattleMgmt(accounts, market);
		if(args[1].equals("-i")){
			install(accounts, market, battleMgmt);
		}
		//set up battle management
		
		//set up server
		ServerConnection serverConnection = new ServerConnection(serverPort, accounts, market);
		Thread serverSideMain = new Thread(serverConnection);
		serverSideMain.start();
		*/
		Account accounts = new Account();
		Market market = new Market(accounts);
		accounts.addMarket(market);
		BattleMgmt test = new BattleMgmt(accounts, market);
		
		ServerConnection connection = new ServerConnection(25000, accounts, market, test);
		connection.run();
		//install(accounts, market, test);
		
		//accounts.register("Alex1", "password");
		//accounts.register("Alex2", "password");
		//accounts.register("Alex3", "password");
		//System.out.println(test(accounts, market, test));
		//System.out.println(accounts.displayArmy("Alex1"));
		//market.sell("Alex1", 3, 500);
		//accounts.levelUnit(4, 1, 2, 3, 0);
		//System.out.println(market.display());
		//accounts.changeFunds("Alex2", 10000);
		//market.buy("Alex1", 3, "Alex1-firstArmy");
		/*System.out.println(market.display());
		ArrayList<String[]> armyDisp = accounts.displayArmyBattle("Alex2", "Alex2-firstArmy");
		for(String[] dsp: armyDisp){
			System.out.println(dsp[0] + " " + dsp[1] + " " + dsp[2] + " " + dsp[3] + " " + dsp[4] + " " + dsp[5] + " " + dsp[6] + " "
					+ dsp[7] + " " + dsp[8]);
		}*/
		//System.out.println(accounts.getID("Alex4"));
		//Player testPlayer1 = new Player("Alex1", "Alex1-firstArmy", test);
		//Player testPlayer2 = new Player("Alex2", "Alex2-firstArmy", test);
		//Battle testBattle = new Battle(testPlayer1, testPlayer2, test);
		//System.out.println(testBattle.getUnits(testPlayer1));
		//getUnits()
		
		
	//for testing network functions
	//private static String test(Account accounts, Market markets, BattleMgmt battleManagement) throws SQLException, Exception {
		/*String output = null;
		if(accounts.login(user, password)){
			output = accounts.displayArmy(user);
		} else {
			output = "##Couldn't Login";
		}
		return "SvrRes##"+user+output;*/
		/*String display = markets.display();
		return "SvrRes##"+user+"##display##"+accounts.getGold(user)+display;*/
		/*boolean purchase = markets.sell("Alex1", 5, 1000);
		 * 
		return "SvrRes##"+user+"##purchase##"+purchase;*/
		//boolean purchase = markets.buy(user, 5, user+"-firstArmy");
		//return "SvrRes##"+user+"##display##"+accounts.getGold(user)+purchase;
		//return "SvrRes##" + user + accounts.displayArmy(user);
		
		//battle test
		/*int count = 0;
		boolean testLoop = battleManagement.isInBattle("Alex1").equalsIgnoreCase("false");
		while(testLoop){
			System.out.println(testBattleMatchMaker("Alex1", "Alex1-firstArmy", battleManagement));
			System.out.println(testBattleMatchMaker("Alex2", "Alex2-firstArmy", battleManagement));
			testLoop = battleManagement.isInBattle("Alex1").equalsIgnoreCase("false");
			count++;
		}
		//there is a battle at this stage
		System.out.println(testBattleMove("Alex1", "2", battleManagement));
		System.out.println(testBattleMove("Alex1", "-1", battleManagement));
		System.out.println(testBattleMove("Alex2", "5", battleManagement));
		System.out.println(testBattleMove("Alex1", "-1", battleManagement));
		return null;*/
		
	}
	private static String testBattleMove(String user, String unitID, BattleMgmt battleManagement){
		String output = "";
		Battle battle = battleManagement.getBattle(user);
		//select unit to be battled against
		if(battle.player1.login.equals(user)){
			for(Unit unit : battle.army1){
				if(unit.getID() == Integer.parseInt(unitID)){
					battle.unitBattle1 = unit;
				}
			}
		} else {
			for(Unit unit : battle.army2){
				if(unit.getID() == Integer.parseInt(unitID)){
					battle.unitBattle2 = unit;
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
	
	
	public static void install(Account accounts, Market market, BattleMgmt battleMgmt) throws Exception{
		accounts.initiateCredentials();
		accounts.initiateFriendsList();
		accounts.initialiseRoster();
		market.initialiseMarket();
	}

}
