package Server;

import java.util.ArrayList;

public class MainServer {
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
		//install(accounts, market, test);
		
		//accounts.register("Alex1", "password");
		//accounts.register("Alex2", "password");
		//accounts.register("Alex3", "password");
		
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
		Player testPlayer1 = new Player("Alex1", "Alex1-firstArmy", test);
		Player testPlayer2 = new Player("Alex2", "Alex2-firstArmy", test);
		Battle testBattle = new Battle(testPlayer1, testPlayer2, test);
		System.out.println(testBattle.getUnits(testPlayer1));
		//getUnits()
		
		
	}
	public static void install(Account accounts, Market market, BattleMgmt battleMgmt) throws Exception{
		accounts.initiateCredentials();
		accounts.initiateFriendsList();
		accounts.initialiseRoster();
		market.initialiseMarket();
	}

}
