import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Defender extends Player implements DefenderNotifier{
	
	public Defender(ArrayList<Server> serverList) {
		super(serverList);
		// TODO Auto-generated constructor stub
		for(Server server :  serverList) {
			server.addDefenderNotifier(this);
		}
		
	}
	
	private Server findNextServer() {
		
		Server server =  null;
		for (Server serverInList : servers) {
			if(serverInList.getServerOwner() == PlayerType.Attacker && 
					serverInList.getServerRunningStatus() == ServerRunningStatus.UP) {
				server = serverInList;
			}
		}
		if(server == null) {
			for (Server serverInList : servers) {
				if(serverInList.getServerOwner() == PlayerType.Defender && 
						serverInList.getServerRunningStatus() == ServerRunningStatus.UP) {
					if(serverInList.getProbeCount() > 0) {
						if(server == null) {
							server = serverInList;
						}
						else if (server.getProbeCount() < serverInList.getProbeCount()) {
							server = serverInList;
						}
					}
				}
			}
			
		}
		return server;		
	}

	@Override
	public void executeActionAtTime(long time) {
		
		// TODO Auto-generated method stub				
		Server reImageServer = findNextServer();
		if(reImageServer == null) {
			//System.out.println("All Servers are in defenders control!!");
			return;
		}
		reImageServer.reImageActionAtTime(time);
	}

	@Override
	public void reImageDone(Server server, long time) {
		
		EventLogger.getEventLogger().logEvent("ReImage Succeeded for server: "+server.getId()+" "+"at time: "+ time);
		computeUtility(time);
	}

	@Override
	public void probeDetected(Server server, long time) {
		
		double[] v = {0.0,0.2,0.5,0.8};
		
		int bound = 4;
		int index = ThreadLocalRandom.current().nextInt(0, bound);
		
		double p = 1 - v[index];
		
		if(p < 1.0) {
			return;
		}
		if(server.getServerOwner() == PlayerType.Attacker ) {
			server.reImageActionAtTime(time);
		}
	
	}



}
