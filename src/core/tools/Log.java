package core.tools;

public class Log {

	private static int storedLevel = 0;

	public static int getLogLevel() {
		return storedLevel;
	}

	public static void log(Object in, int levelRequired) {

		if (storedLevel >= levelRequired) {
			System.out.print(String.valueOf(in));
		}
	}

	public static void logln(Object in, int levelRequired) {

		if (storedLevel >= levelRequired) {
			System.out.println(String.valueOf(in));
		}
	}

	public static void setLogLevel(int level) {
		Log.storedLevel = level;
	}

}
