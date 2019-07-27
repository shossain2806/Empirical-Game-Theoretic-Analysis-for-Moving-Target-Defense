
enum ServerRunningStatus{
	UP,
	Down
}

public class ServerStatus{
	private ServerRunningStatus runningStatus;
	private long downAtTime;
	
	public ServerRunningStatus getRunningStatus() {
		return runningStatus;
	}

	public long getDownAtTime() {
		return downAtTime;
	}

	public ServerStatus() {
		setServerStatusRunning();
	}
	
	void setServerStatusRunning() {
		runningStatus = ServerRunningStatus.UP;
		downAtTime = 0;
	}
	
	void setServerStatusDownAtTime(long time) {
		runningStatus = ServerRunningStatus.Down;
		downAtTime = time;
	}
}