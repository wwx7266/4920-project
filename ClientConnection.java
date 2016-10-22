
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ClientConnection {
	
	String user = null;
	String password = null;
	String serverAddress = null;
	int serverPort = 25000;
	String command = null;
	String[] result = null;
	boolean connect = false;
	Connection connection = null;
	
	//ClientConstructor that may take the user name, password, server address and server port
	ClientConnection(String user, String password, String serverAddress, int serverPort){
		//Initialization of user credentials
		this.user = user;
		this.password = password;
		//Establishment of server connection
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.connection = new Connection(this.serverAddress, this.serverPort);
	}
	
	//ClientConnection constructor using default port 2500
	ClientConnection(String user, String password, String serverAddress){
		//Initialization of user credentials
		this.user = user;
		this.password = password;
		//Establishment of server connection
		this.serverAddress = serverAddress;
		this.connection = new Connection(this.serverAddress, this.serverPort);
	}
	/*commands are: register, login, battleReq, battleMove, infoReq, logout
	 * 
	 * sub commands
	 * battleReq##<battleMode>##<army id>
	 * battleMove##<action1>##<action2>## etc.
	 *
	 * response in form
	 * register: SvrRes##<username>##true
	 * login: SvrRes##<username>##<true/false>
	 * battleReq: SvrRes##<username>##<battleMode>##<opponent>
	 * battleMove: SvrRes##<username>##battle##<turnNumber>##<result1>##<result2>## etc.
	 * infoReq: SvrRes##<username>##army:<number>##<unit1info>##<unit2info> etc.
	 * 
	 */

	
	//registers a user with the initialized information
	public String[] register() throws IOException{
		this.command = "register";
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//logs in user with presented parameters
	public String[] login() throws IOException{
		this.command = "login";
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	
	//sends a battle request to the server, 
	//will receive a reply request with non null opponent if successful
	public String[] RequestBattle(String battleMode, int armyID) throws IOException{
		/*
		 * command in form:
		 * battleReq##<battleMode>##<army id>
		 */
		this.command = "battleReq##"+battleMode+"##"+armyID;
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//sends the move made by user in battle, any number of moves may be tacked onto
	//the end of the message
	public String[] BattleAction(ArrayList<String> battleActions) throws IOException{
		/*
		 * command in form:
		 * battleMove##<action1>##<action2>## etc.
		 * 
		 * response in form:
		 * SvrRes##<username>##battle##<turnNumber>##<result1>##<result2>## etc.
		 */
		this.command = "battleMove##";
		for(String action : battleActions){
			this.command = this.command + action + "##";
		}
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to request information from the server on current armies and their composition
	public String[] requestInfo() throws IOException{
		/*
		 * server response in form:
		 * SvrRes##<username>##army:<number>##<unit1info>##<unit2info> etc.
		 */
		this.command = "infoReq";
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to request current information on the economy
	public String[] requestInfoEcon() throws IOException{
		this.command = "infoReqEcon";
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to purchase units from the store
	public String[] purchaseEcon(int unitID) throws IOException{
		/*
		 * command in form:
		 * purchase##<unitID>
		 * 
		 * Response:
		 * purchase##<true/false>
		 */
		this.command = "purchase##" + unitID;
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to send a message via chat
	public String[] chatUp(String payload) throws IOException{
		this.command = "chatUp##" + payload;
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to add a friend to account
	public String[] addFriend(String friend) throws IOException{
		this.command = "friendOp##add##" + friend;
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to remove a friend from the account
	public String[] removeFriend(String friend) throws IOException{
		this.command = "friendOp##delete##" + friend;
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
	//used to retrieve current friends list from server
	public String[] displayFriend() throws IOException{
		this.command = "friendOp##display";
		this.result = connection.sendRecieve("UsrReq##"+this.user+"##"+this.password+"##"+this.command).split("##");
		return this.result;
	}
}
/*
 * this class can, given a server and socket, send and receive data from a server
 */
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
	
	//will send a given string to a server and receive the response and convert back into ASCII characters
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
}
