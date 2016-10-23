package Client;

import java.io.IOException;
import java.util.ArrayList;

public class ClientTester {

	public static void main(String[] args) throws IOException {
		ClientConnection test = new ClientConnection(args[0], "password", "localhost", 25000);
		//System.out.println(test.login());
		//System.out.println(test.register());
		//System.out.println(test.getUnits());
		ArrayList<MatchUnit> testUnits = test.RequestBattle(args[0]+"-firstArmy");
		String[] testUnits1 = test.BattleAction(Integer.parseInt(args[1]));
		String[] testUnits2 = test.BattleAction(Integer.parseInt(args[1]));
		//test.BattleAction(Integer.parseInt(args[1]));
		System.out.println("done");
	}

}
