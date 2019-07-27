
public interface DefenderNotifier {
	public abstract void reImageDone(Server server,long time);
	public abstract void probeDetected(Server server, long time);
}
