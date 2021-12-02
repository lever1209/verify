package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import net.dv8tion.jda.api.entities.TextChannel;

public class Global {

	public static String Prefix = "v!";
	public static String botName = "Verify";

	public static ArrayList<Long> DEVIDS = new ArrayList<Long>(
			Arrays.asList(342047193294831619L, 811074285161676840L, 557218928368156674L));

	public static List<Long> premiumHoes = Arrays.asList(749007306376609835L);

	public static TextChannel suggestionBox = null;

	// public static String status = "Running normally";
	public static String ROOT = ".ROOT/";
	public static final char noSpace = '​';

	public static int embedColor = 3066993;

	public static boolean running = true;

	public static boolean close = false;
	public static long curse = 342047193294831619L;
	public static boolean shuffle = true;

	public final static int numOfCores = Runtime.getRuntime().availableProcessors();

	public static Executor executor;
	public static ExecutorService executorService;
}
