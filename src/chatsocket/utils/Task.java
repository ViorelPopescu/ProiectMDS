package chatsocket.utils;

public final class Task {
	Task() {
	}

	public static void run(Runnable run) {
		if(run != null)
		{
		Thread thread = new Thread(run);
		thread.setDaemon(true);
		thread.start();
		}
	}
}
