package core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.IO.ObjectIO;
import core.autoActions.Action;
import core.autoActions.CreateAction;
import core.enums.BotMode;
import core.enums.Misc;
import core.listeners.GuildMemberJoin;
import core.listeners.MessageRecievedListener;
import core.listeners.NickChangeListener;
import core.listeners.PMRecievedListener;
import core.listeners.ReactionListener;
import core.listeners.timeout.CommandTimeout;
import core.listeners.timeout.TimeoutManager;
import core.terminal.Colors;
import core.tools.Log;
import core.tools.Tools;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import services.Server;

public class Main {

	public static BotMode mode = BotMode.DEV;
	public static final boolean offline = false;

	public static final List<Action> actionList = new ArrayList<Action>();

	static Object runMode = null;
	public static JDA bot;
	public static JSONObject version;
	public static boolean forceUpdate = false;
	public static boolean running = true;
	public static final long pid = ProcessHandle.current().pid();
	public static boolean respawn = false;
	public static boolean devCommands = true;

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws LoginException, InterruptedException, IOException {
		int dPing = (int) services.UptimePing.sendPing("www.discord.com");
		// System.out.println(System.getProperty("java.home"));
		System.out.println(Main.pid + " : MEM : " + Runtime.getRuntime().freeMemory());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date(System.currentTimeMillis());
		String timeC = formatter.format(date);

		System.out.println(pid + " : " + timeC + "\n" + System.getProperty("user.dir"));
		/*
		 * try {
		 * // System.out.println("Ping to google: " + UptimePing.sendPing("8.8.8.8"));
		 * // System.out.println("Ping to discord: " +
		 * UptimePing.sendPing("www.discord.com"));
		 * // System.out.println("Ping to router: "+UptimePing.sendPing("uwu_owo.net");
		 * } catch (IOException e) {
		 * offline = true;
		 * System.exit(99);
		 * }/
		 **/

		// mode =

		// System.out.println(JoinActions.password(99));

		boolean skip = false;

		try {
			if (args[0].contentEquals("dev")) {
				mode = BotMode.DEV;
				skip = true;
			} else if (args[0].contentEquals("prod")) {
				mode = BotMode.PROD;
				skip = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		if (args.length < 1 | skip) {

			if (lock("verify.lock")) {
				System.out.println(pid + " : is locked, sending update instead. . .");
				try {
					Socket cSocket = new Socket("127.0.0.1", 1209);
					DataOutputStream dOut = new DataOutputStream(cSocket.getOutputStream());
					dOut.writeByte(2);
					// System.out.println("WRITE");
					dOut.flush();

					dOut.close();

					cSocket.close();
					// System.out.println("CLOSE");
				} catch (Exception e) {
					e.printStackTrace();

				}
				System.exit(-1);

			} else {
				System.out.println(pid + " : locked system. . .");
			}

			try {
				version = (JSONObject) new JSONParser().parse(new FileReader(new File(".version")));
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				System.exit(-88);
			}

			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					System.out.println(pid + " : Shutdown Thread start. . .");
				}
			}, "Shutdown-thread"));

			System.out.println(pid + " : starting phoenix system. . .");

			Thread socketServerThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (running & !Server.serverShutdown) {
						Server.sockets();
					}
				}
			}, "server-thread");

			Thread commandThread = new Thread(new Runnable() {

				@Override
				public void run() {
					Server.commands();
				}
			}, "command-thread");

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			if (!offline) { // TODO spot to init
				Global.executor = Executors.newCachedThreadPool();
				Global.executorService = new ThreadPoolExecutor(1, (Global.numOfCores * (1 + dPing / 15)), 0L,
						TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
				if (mode == BotMode.PROD) {
					runMode = TokenVault.key00.toString(); // main
					Global.Prefix = "v!";
					bot();

				} else if (mode == BotMode.DEV) {
					runMode = TokenVault.key01.toString(); // dev
					Global.Prefix = "'";
					Global.ROOT = ".ROOT-DEV/";
					Global.embedColor = 16761035;
					bot();

				} else {
					System.out
							.println(Colors.RED + pid + " : Please select a valid run mode...\n\"ver\" for main\n\"dev"
									+ "\" for the development build" + Colors.RESET);
					System.exit(-1);
				}

			} else {
				Thread.sleep(1000);
			}
			stopWatch.stop();
			System.out.println(pid + " : MS to init jda : " + stopWatch.getTime(TimeUnit.MILLISECONDS));
			// System.exit(0);
			System.out.println(pid + " : mode : " + mode);

			if (SystemUtils.IS_OS_LINUX) {
				System.out.println(Colors.GREEN + pid + " : Running linux..." + Colors.RESET);
			}
			if (SystemUtils.IS_OS_WINDOWS) {
				System.out.println(Colors.RED + pid + " : Running windows..." + Colors.RESET);
			}
			// Map<String, String> result = Tools.jsonCfgReader();
			// System.out.println();
			// System.out.println("A = " + result.get(A));
			// System.out.println("B = " + result.get(B));
			if (!offline) {
				bot.getPresence().setActivity(Activity.listening("music while i cache my actions. . ."));
			}
			System.out.println("loading actions. . .");
			for (String i : new File(Global.ROOT + "GLOBAL/ACTIONS/").list()) {
				if (new File(i).getName().endsWith(".action")) {
					String read = Tools.fileIOREAD(Global.ROOT + "GLOBAL/ACTIONS/" + i);
					System.out.println(read);
					try {
						actionList.add(new CreateAction().newAction((JSONObject) new JSONParser().parse(read)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (!offline) {
				bot.awaitStatus(net.dv8tion.jda.api.JDA.Status.CONNECTED);
				bot.getPresence().setStatus(OnlineStatus.ONLINE);
				bot.getPresence().setActivity(null);
				bot.getPresence().setIdle(false);
			}

			final int port = 1209;// Integer.parseInt(scan.nextLine());

			try {
				final Socket cSocket = new Socket("127.0.0.1", port);
				final DataOutputStream dOut = new DataOutputStream(cSocket.getOutputStream());
				dOut.writeByte(3);
				dOut.flush();
				dOut.close();
				cSocket.close();
				System.out.println(pid + " : sent respawn packet");
			} catch (java.net.ConnectException e) {
				System.out.println(pid + " : Failed to send respawn, assuming self is first run");
			} catch (Exception e) {
				e.printStackTrace();
			}

			socketServerThread.start();
			if (mode == BotMode.DEV && false) {
				commandThread.start(); // BUG unneeded in this stage and respawn does not inherit input, so this
										 // command manager for text input from console will not work after respawn
										 // requiring a full stop and restart, test on dev while working on a fix
				// FIXME separate bot manager program that will send the bot commands via
				// sockets
			}
			System.out.println(pid + " : start looping");
			System.out.println(Main.pid + " : " + Thread.activeCount() + " : " + Thread.currentThread());
			if (!offline) {
				Global.suggestionBox = bot.getTextChannelById(844597253373165648L);
			}
			coreLoop();

		} else if (args[0].contentEquals("update")) {

			if (lock("verifyUpdate.lock")) {
				System.exit(1);
			}

			System.out.println(pid + " : UPDATING");

			final File version = new File(".version");
			long verNum = 0;
			if (!version.exists()) {
				version.createNewFile();
				System.err.println(pid + " : version file not found, creating new version file");
			}

			try {
				JSONObject obj2 = (JSONObject) new JSONParser().parse(new FileReader(version));

				if (obj2.get("build") instanceof Long) {
					verNum = (Long) obj2.get("build") + 1;
				}

			} catch (IOException | ParseException e) {
				// e.printStackTrace();
			}
			final JSONObject obj = new JSONObject();
			obj.put("date", System.currentTimeMillis());
			obj.put("build", verNum);
			System.out.println(obj);

			final FileWriter fW = new FileWriter(version);
			fW.write(obj.toJSONString());
			fW.close();

		}

	}

	private static boolean lock(String lockName) {
		final String userHome = System.getProperty("user.home");
		final File file = new File(userHome, lockName);
		try {
			final FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			final FileLock lock = fc.tryLock();
			if (lock == null) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			throw new Error(e);
		}

	}

	private static boolean unlock(String lockName) {
		final String userHome = System.getProperty("user.home");
		return new File(userHome, lockName).delete();

	}

	private static void coreLoop() throws IOException, InterruptedException {

		long lastTime = System.currentTimeMillis();
		long lastTimeFive = lastTime;
		long lastTime3mins = lastTime;
		long lastTimeStatus = lastTime;
		long lastTimeUpdateCheck = lastTime;
		long wait = -1;
		wait = Tools.convertToLong(ObjectIO.readObject(Global.ROOT + "GLOBAL/WAIT"));

		if (wait > 0) {

			while (Global.running) {

				if (Server.socketShutdown) {
					if (!offline) {
						bot.shutdown();
					} else {
						System.out.println(pid + " : dry run, skipping shutdown");
					}
					System.exit(0);
				}

				long now = System.currentTimeMillis();

				if (now > lastTimeUpdateCheck + 2000 | forceUpdate) {
					lastTimeUpdateCheck = now;
					try {
						JSONObject newVer = (JSONObject) new JSONParser().parse(new FileReader(new File(".version")));
						if ((Long) version.get("build") < (Long) newVer.get("build") | forceUpdate) {
							forceUpdate = false;
							System.out.println(pid + " : NEW VERSION FOUND, UPDATING . . .");
							String javaBin = "";
							String cDir = (System.getProperty("user.dir"));
							// System.out.println(javaBin + cDir);
							StringBuilder sB = new StringBuilder();
							String split = "";
							if (SystemUtils.IS_OS_WINDOWS) {
								split = ";";
								javaBin = (System.getProperty("java.home") + "\\bin\\java");
							} else {
								split = ":";
								javaBin = (System.getProperty("java.home") + "/bin/java");
							}
							for (File i : new File(cDir).listFiles()) {

								if (i.getName().endsWith(".jar")) {

									sB.append(i + split);
								}

							}

							if (sB.toString().endsWith(";") | sB.toString().endsWith(":")) {
								sB.replace(sB.length(), sB.length(), "");
							}
							// System.out.println(sB);

							unlock("verify.lock");

							if (mode == BotMode.PROD) {
								ProcessBuilder pB = new ProcessBuilder(javaBin, "-classpath",
										cDir + "/bin" + split + sB, "core.Main", "prod");
								pB.redirectOutput(Redirect.INHERIT);
								pB.redirectError(Redirect.INHERIT);
								pB.redirectInput(Redirect.INHERIT);
								pB.start();
							} else {
								ProcessBuilder pB = new ProcessBuilder(javaBin, "-classpath",
										cDir + "/bin" + split + sB, "core.Main", "dev");
								pB.redirectOutput(Redirect.INHERIT);
								pB.redirectError(Redirect.INHERIT);
								pB.redirectInput(Redirect.INHERIT);
								pB.start();
							}

							for (Action i : actionList) {
								Tools.fileIOWRITE(Global.ROOT + "GLOBAL/ACTIONS/" + i.getActionID() + ".action",
										i.toJSON(), Misc.overwrite);
							}

							while (!respawn) {
								// System.out.println("AWAIT RESPAWN . . .");
								Thread.sleep(3);
							}

							if (!offline) {
								bot.shutdown();
							} else {
								System.out.println(pid + " : dry run, skipping shutdown");
							}
							System.exit(0);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						// e.printStackTrace();
					}

				}

				if (now > lastTime3mins + 1800) {

					for (CommandTimeout i : TimeoutManager.getValues()) {
						if (i.ms() <= 0) {
							TimeoutManager.delTimeout(i.getKey());
						}
					}

					lastTime3mins = now;
					List<Integer> removeIndexList = new ArrayList<Integer>();
					// System.out.println("bfor");
					for (int i = 0; i < actionList.size(); i++) {
						// System.out.println("for" + actionList.get(i).type() + "\n" +
						// actionList.get(i).getTime() + "\n"+ System.currentTimeMillis());
						if (actionList.get(i).getTime() <= System.currentTimeMillis()) {
							try {
								actionList.get(i).start();
							} catch (Throwable e) {
								// bot.getGuildChannelById(actionList.get(i).getChannelID()).getGuild().getTextChannelById(actionList.get(i)));
								try {
									bot.getTextChannelById(actionList.get(i).getChannelID()).sendMessage(e.toString())
											.queue();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
							// System.out.println(actionList.get(i).getActionID());
							removeIndexList.add(i);
						}

					}

					for (int i : removeIndexList) {

						actionList.remove(i);

					}
					/**/
				}

				if (now > lastTimeFive + 5000) {

					// actionList.add(new
					// CreateAction().newAction("ban").setGuildID(42069).setUserID(1337).setTime(90000l));
					// for (Action i : actionList) {

					// System.out.println(i.type() + " : " + i.getActionID());

					// }

					lastTimeFive = now;
					wait = Tools.convertToLong(ObjectIO.readObject(Global.ROOT + "GLOBAL/WAIT"));
					Log.logln("READT : " + wait + " : " + now, 4);

					// System.out.println(pid + " : 5 seconds : " + forceUpdate + " : " +
					// System.currentTimeMillis());

					if (Runtime.getRuntime().totalMemory() > Runtime.getRuntime().maxMemory() - 30000) {
						System.out.println(Main.pid + " : CLEAN : " + Runtime.getRuntime().totalMemory());
						Runtime.getRuntime().gc();
					} /**/

				}

				if (now > lastTimeStatus + wait * 10 & !offline & Global.shuffle) {
					lastTimeStatus = now;
					Status.shuffle();
				}

				if (now > lastTime + wait) {
					// LOOP HERE
					lastTime = now;
					Log.logln("TIMER : " + wait + " : " + now, 4);
				}
				if (wait <= 0) {
					break;
				}
			}
		}
	}

	public static void bot() throws LoginException, InterruptedException, IOException {
		bot = JDABuilder.createDefault(runMode.toString())

				.setActivity(Activity.playing("a game while i load. . .")).setStatus(OnlineStatus.DO_NOT_DISTURB)

				.setMaxBufferSize(Integer.MAX_VALUE)

				.setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL)

				.enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS,
						// GatewayIntent.DIRECT_MESSAGE_TYPING,
						GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_EMOJIS,
						GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS,
						// GatewayIntent.GUILD_MESSAGE_TYPING,
						GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES)

				.enableCache(CacheFlag.CLIENT_STATUS, CacheFlag.EMOTE, CacheFlag.ACTIVITY, CacheFlag.MEMBER_OVERRIDES,
						CacheFlag.VOICE_STATE)

				.setIdle(true)

				.setAutoReconnect(true)

				.addEventListeners(new MessageRecievedListener()).addEventListeners(new ReactionListener())
				.addEventListeners(new NickChangeListener()).addEventListeners(new PMRecievedListener())
				.addEventListeners(new GuildMemberJoin())

				// .useSharding(0, 2) // TODO rewrite for shards using JDA[]

				// .setEventPool(new ThreadPoolExecutor(1, (Global.numOfCores * (1 + dPing /
				// 15)), 0L,
				// TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()), true)
				.setEnableShutdownHook(true)

				.build();
	}

}
