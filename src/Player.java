import java.util.ArrayList;

public abstract class Player {
	
	protected ArrayList<Server> servers;
	public abstract void executeActionAtTime(long time) ;
	
	public  Player(ArrayList<Server> serverList) {
		this.servers = serverList;
		
	}
	
	void computeUtility(long time) {
		
		PlayerType type =  (this instanceof Attacker) ? PlayerType.Attacker :  PlayerType.Defender;
		
		int numberOfserversIncontroll = 0;
		int countOfDownServer = 0;
		double weight = 1.0;
		double theta1 = 0.5;//considering majority
		double theta2 = 0.5;//considering majority
		for(Server server : servers) {
			if(server.getServerOwner() == type && server.getServerRunningStatus() == ServerRunningStatus.UP) {
				numberOfserversIncontroll++;
			}
			else if(server.getServerRunningStatus() == ServerRunningStatus.Down) {
				countOfDownServer++;
			}
		}
		
		double fact1 =  ((double)numberOfserversIncontroll / (double)MTDSimulator.M );
		double func1 = computeSigmoidFunction(fact1, theta1, theta2);
		double fact2 = (double)(numberOfserversIncontroll + countOfDownServer) / (double)MTDSimulator.M;
		double func2 = computeSigmoidFunction( fact2, theta1, theta2);
		double utility = (weight * func1) + 
						  ((1 - weight) * func2);
		EventLogger.getEventLogger().logEvent("Utility of "+ type +" = "+utility + " at time : "+time+" , server count: "+numberOfserversIncontroll);
	}
	
	double computeSigmoidFunction(double x, double t1, double t2) {
		
		double result = 1 / ( 1 + Math.exp( - (t1 * (x - t2) )));
		
		return result;
	}
}


