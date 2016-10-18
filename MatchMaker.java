
import java.util.*;


public class MatchMaker {
	
	ArrayList<String> userList;
	HashMap userMap;
	

    public MatchMaker() {
    	userList = new ArrayList<>();
    	userMap = new HashMap();
    }
    
    private void fillHash(){
    	userMap.put("Lance", new Integer(100));
    	userMap.put("Jon", new Integer(120));
    	userMap.put("Jimmy", new Integer(91));
    	userMap.put("Carl", new Integer(104));
    	userMap.put("Bob", new Integer(85));
    }
    
    public void matchPlayers(){
    	 Set item = userMap.entrySet();
    	 Iterator i = item.iterator();
    	 
         while(i.hasNext()) {
             Map.Entry me = (Map.Entry)i.next();
             System.out.print(me.getKey() + ": ");
             System.out.println(me.getValue());
          }
    }
    
    public ArrayList<String> createMatches(){
    	return null;
    }
    
    


    public static void main (String[] args) {
    	MatchMaker test = new MatchMaker();
    }
}
