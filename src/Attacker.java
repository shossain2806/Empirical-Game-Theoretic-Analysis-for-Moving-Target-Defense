import java.util.ArrayList;

public class Attacker extends Player  implements AttackerNotifier{
	

	public Attacker(ArrayList<Server> serverList) {
		super(serverList);
		// TODO Auto-generated constructor stub
		for(Server server :  serverList) {
			server.addAttackerNotifier(this);
		}
		
	}
	
	private Server findNextServer() {
		
		Server server =  null;
		for (Server serverInList : servers) {
			if(serverInList.getServerOwner() == PlayerType.Defender && serverInList.getServerRunningStatus() == ServerRunningStatus.UP) {
				if(server == null) {
					server = serverInList;
				}
				else if(server.getProbeCount() < serverInList.getProbeCount()){
					server = serverInList;
				}
			}
		}
		return server;		
	}

	@Override
	public void executeActionAtTime(long time) {
		
		Server probeServer = findNextServer();
		if(probeServer == null) {
			//System.out.println("All Servers are in attacker's control!!");
			return;
		}
		probeServer.probeActionAtTime(time);

	}

	@Override
	public void probeOccur(Server server, long time) {
		
		if(server.getServerOwner() == PlayerType.Attacker) {
			
			EventLogger.getEventLogger().logEvent("Probe Succeeded for server: "+server.getId()+" "+"at time: "+ time);
			computeUtility(time);
		}
	}

	@Override
	public void attackerloseControlOfServer(Server server, long time) {
		// TODO Auto-generated method stub
		EventLogger.getEventLogger().logEvent("Attacker loses control of server: "+server.getId()+" "+"at time: "+ time);
		computeUtility(time);
	}

}
