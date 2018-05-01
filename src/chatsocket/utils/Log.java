package chatsocket.utils;


public final class Log {
	private static ILogable logable = new StdLogable();

	Log() {
	}

	public static void i(Object obj) {
		logable.i(obj);
	}

	public static void l(Object obj) {
		logable.l(obj);
	}

	public static void setLogable( ILogable _logable) {
		if(_logable != null)
			logable = _logable;
	}

	public static ILogable getLogable() {
		return logable;
	}

	private static class StdLogable implements ILogable {

		@Override
		public void i(Object obj) {
			System.out.println(obj != null ? obj.toString() : "NULL");
		}

		@Override
		public void l(Object obj) {
			System.out.println(obj != null ? obj.toString() : "NULL");
		}

	}

	public interface ILogable {
		void i(Object obj);

		void l(Object obj);
	}
}
