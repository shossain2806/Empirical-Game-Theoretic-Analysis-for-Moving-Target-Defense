
public interface AttackerNotifier {

	public abstract void probeOccur(Server server,long time);
	public abstract void attackerloseControlOfServer(Server server, long time);
}
