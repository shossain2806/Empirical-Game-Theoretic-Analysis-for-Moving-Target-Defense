import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


enum PlayerType{
	Attacker,
	Defender
}

public class Server {
	
	public static final double Alpha = 0.05;
	public static final long TimeToMaintainServer = 7; // delta
	
	private PlayerType serverOwner;
	private ServerStatus serverStatus;
	private int id;
	private long numberOfProbesAfterReImaged;
	private long serverWillRestartAtTime;
	private AttackerNotifier attackerNotifier;
	private DefenderNotifier defenderNotifier;
	/*
	 * construct method
	 */
	public Server(int id) {
		// TODO Auto-generated constructor stub
		
		serverOwner = PlayerType.Defender;
		serverStatus = new ServerStatus();
		numberOfProbesAfterReImaged = 0;
		this.id = id;
	}
	
	public void addAttackerNotifier(AttackerNotifier listener) {
		attackerNotifier = listener;
	}
	
	public void addDefenderNotifier(DefenderNotifier listener) {
		defenderNotifier = listener;
	}
	
	public long getProbeCount() {
		return numberOfProbesAfterReImaged;
	}
	
	public PlayerType getServerOwner() {
		return this.serverOwner;
	}
	
	public ServerRunningStatus getServerRunningStatus() {
		return this.serverStatus.getRunningStatus();
	}
	/*
	 * getter method
	 */
	public int getId() {
		return id;
	}

	/*
	 * probe Action
	 */
	public void probeActionAtTime(long time) {
	
		if(serverStatus.getRunningStatus() == ServerRunningStatus.Down) {
			return;
		}
		
		if(serverOwner == PlayerType.Attacker) {
			return;
		}
	
		numberOfProbesAfterReImaged++;
		
		double x =  - (Alpha*(numberOfProbesAfterReImaged + 1));
		double probability = 1 - Math.exp(x) ;

		if(probability > 0.99) { // to avoid floating point precision 
			serverOwner = PlayerType.Attacker;
			serverStatus.setServerStatusRunning();
		}
		attackerNotifier.probeOccur(this,time);
		defenderNotifier.probeDetected(this, time);
	}
	
	
	/*
	 * reImage Action
	 */
	public void reImageActionAtTime(long time) {
		
		if(serverStatus.getRunningStatus() == ServerRunningStatus.Down) {
			return;
		}
		
		if(serverOwner == PlayerType.Defender) {
			return;
		}
		
		serverOwner = PlayerType.Defender;
		serverStatus.setServerStatusDownAtTime(time);
		numberOfProbesAfterReImaged = 0;
		
		defenderNotifier.reImageDone(this, time);
		attackerNotifier.attackerloseControlOfServer(this, time);
		serverWillRestartAtTime = time + TimeToMaintainServer;
	}

	/*
	 * factory method
	 */
	public static ArrayList<Server> createServers(int size){
		ArrayList<Server> list = new ArrayList<Server>();
		for (int i = 0 ; i < size ; i++) {
			Server server = new Server(i);
			list.add(server);
		}
		return list;
	}
	
	public void setServerStatusAtTime(long time) {
		
		if(time < serverWillRestartAtTime) {
			return;
		}
		if(getServerRunningStatus() == ServerRunningStatus.Down) {
			serverWillRestartAtTime = 0;
			serverStatus.setServerStatusRunning();
		}
	}

}
