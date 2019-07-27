import java.util.ArrayList;


public class MTDSimulator {
	public static final long T = 1000; // in millis
	public static final int M = 10; // server count 
	
	private long elapsedTime; // currentTime
	private boolean toFinish;
	
	private Player attacker;
	private Player defender;
	
	ArrayList<Server> serverList;
	
	public MTDSimulator() {
		// TODO Auto-generated constructor stub
		elapsedTime = 0;
		toFinish = false;
		serverList = Server.createServers(M);
		attacker = new Attacker(serverList);
		defender = new Defender(serverList);
		
	}

	void startSimulation() {
		
		boolean flag = false;
		while (!toFinish) 
		{
			elapsedTime++; 
			setServersStatusAtTime(elapsedTime);
		    if (flag == false) {
		    	attacker.executeActionAtTime(elapsedTime);
		    }
		    else {
		    	
		    	defender.executeActionAtTime(elapsedTime);
		    }
		    flag =  !flag;
		    toFinish = (elapsedTime >= T);
		}
		
	}
	
	void setServersStatusAtTime(long time) {
		for(Server server : serverList) {
			server.setServerStatusAtTime(time);
		}
	}
	
	void serverStatusAfterSimulation() {
		EventLogger.getEventLogger().logEvent("Summary: ");
		for(int i = 0 ; i < serverList.size(); i++) {
			Server server = serverList.get(i);
			EventLogger.getEventLogger().logEvent("Server: "+server.getId()+" "+server.getServerOwner()+" status: "+server.getServerRunningStatus());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MTDSimulator simulator =  new MTDSimulator();
		
		System.out.println("Simulation Started....");
		
		simulator.startSimulation();
		simulator.serverStatusAfterSimulation();
		
		EventLogger.getEventLogger().stopLogging();
		System.out.println("Simulation Ended....");
	}

}
