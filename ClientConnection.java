package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


public class ClientConnection {
	
	String user = null;
	String password = null;
	String serverAddress = null;
	int serverPort = 25000;
	String command = null;
	String[] result = null;
	boolean connect = false;
	Connection connection;
	
	ClientConnection(String user, String password, String serverAddress, int serverPort){
		this.user = user;
		this.password = password;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.connection = new Connection(serverAddress,serverPort);
	}
	public void connect(){
		this.connect = true;
	}
	public void disconnect(){
		this.connect = false;
	}
	//registers a user with the initialized information
	public boolean register() throws IOException{
		this.command = "register";
		String payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
		String[] result = payload.split("##");
		if(result[2].equals("true")){
			return true;
		} else {
			return false;
		}
	}
	//logs in user with presented parameters
	public boolean login() throws IOException{
		String command = "login";
		String payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
		String[] result = payload.split("##");
		if(!result[2].equals("Couldn't Login")){
			return true;
		} else {
			return false;
		}
	}
	public ArrayList<Unit> getUnits() throws IOException{
		this.command = "infoReq";
		String payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
		String[] result = payload.split("##");
		ArrayList<String> rawUnits = new ArrayList<String>(Arrays.asList(result));
		return convertStringToUnit(rawUnits);
	}
	private ArrayList<Unit> convertStringToUnit(ArrayList<String> rawUnits){
		ArrayList<Unit> output = new ArrayList<Unit>();
		int count = 0;
		for(String rawUnit : rawUnits){
			if(count < 2){
				count++;
				continue;
			}
			String[] params = rawUnit.split("#-#");
			//type, hp, attack, defense
			output.add(new Unit(params[1],Integer.parseInt(params[0]), typeToCode(params[2]), Integer.parseInt(params[5]), Integer.parseInt(params[4]), Integer.parseInt(params[6]), user));
		}
		return output;
	}
	private int typeToCode(String type) {
		if(type.equals("Swordsman")){
			return 0;
		} else if(type.equals("Pikeman")){
			return 1;
		} else if(type.equals("Knight")){
			return 2;
		} else if(type.equals("Archer")){
			return 3;
		}
		return -1;
	}
	//sends a battle request to the server, 
	//will receive a reply mirroring request if successful
	public ArrayList<MatchUnit> RequestBattle(String army) throws IOException{
		ArrayList<MatchUnit> resultList = new ArrayList<MatchUnit>();
		
		String command = "battleReq##"+army;
		String payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
		String[] result = payload.split("##");
		while(result[3].equals("wait")){
			payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
			result = payload.split("##");
		}
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(result));
		int count = 0;
		for(String line : input){
			if(count < 3){
				//skip 3 times
				count++;
				continue;
			}
			if(line.length() < 4){
				continue;
			}
			String[] split = line.split("#-#");
			resultList.add(new MatchUnit(split[1], Integer.parseInt(split[0]), split[2]));
		}
		return resultList;
	}
	//sends the move made by user in battle, any number of moves may be tacked onto
	//the end of the message
	public String[] BattleAction(int UnitID) throws IOException{
		String command = "battleMove##"+ UnitID;
		String payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##"+command);
		String[] result = payload.split("##");
		while(result[3].equals("wait")){
			payload = connection.sendRecieve("UsrReq##"+user+"##"+password+"##battleMove##-1");
			result = payload.split("##");
			System.out.println(result[2]);
		}
		return result;
	}
	//used to request information from the server on current armies and their composition
	public String[] requestInfo(){
		this.command = "infoReq";
		return this.result;
	}
	//
}
class ClientSide implements Runnable{
	ClientConnection info = null;
	public ClientSide(ClientConnection clientConnection) {
		this.info = clientConnection;
	}

	public void run(){
		
		String payload = null;
		String commandLine = null;
		Connection connection = new Connection(info.serverAddress,info.serverPort);
		while(true){
			if(info.connect){
				try {
					info.disconnect();
					//commands are: register, login, battleReq, battleMove, infoReq, logout
					/* sub commands
					 * battleReq##<battleMode>##<army id>
					 * battleMove##<unitID>
					 */
					payload = connection.sendRecieve("UsrReq##"+info.user+"##"+info.password+"##"+info.command);
					/*payload in form
					 * register: SvrRes##<username>##true
					 * login: SvrRes##<username>##true
					 * battleReq: SvrRes##<username>##<battleMode>##<army id>
					 * battleMove: SvrRes##wait
					 * 				svrRes##message##<message1>##<message2>## etc.
					 * 				svrRes##turnEnd##<message1>##<message2>## etc.
					 * 					 * infoReq: SvrRes##<username>##army:<number>##<unit1info>##<unit2info> etc.
					*/
					info.result = payload.split("##");
				} catch (IOException e) {
					System.out.println("Connection failed, trying to reestablish");
				}
			}
		}
	}
}
class Connection{
	public String serverName = null;
	public int portNumber = 25000;
	public Socket serverConnection = null;
	public PrintWriter upload = null;
	public BufferedReader download = null;
	
	
	public Connection(String serverName, int portNumber){
		this.serverName = serverName;
		this.portNumber = portNumber;
		
	}

	public String sendRecieve(String payload) throws IOException{
		String serverPayload = null;
		Socket serverConnection = null;

		serverConnection = new Socket(this.serverName, this.portNumber);

		PrintWriter upload = null;

		upload = new PrintWriter(serverConnection.getOutputStream(), true);
		upload.println(payload);

		BufferedReader download = null;

		download = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
		serverPayload = download.readLine();
		
		upload.close();
		download.close();
		serverConnection.close();
		
		return serverPayload;
	}
	public void pullEst() throws UnknownHostException, IOException{
		serverConnection = new Socket (this.serverName,this.portNumber);
		upload = new PrintWriter(serverConnection.getOutputStream(), true);
		download = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
	}
	public void pullStop() throws IOException{
		upload.close();
		download.close();
		serverConnection.close();
	}
	
}
