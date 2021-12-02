package core.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

import core.Global;
import core.Main;
import core.autoActions.CreateAction;
import core.commands.dev.AddDev;
import core.commands.dev.DevHelp;
import core.commands.dev.EmbedTest;
import core.commands.dev.Multi;
import core.commands.dev.Todo;
import core.commands.dev.ToggleDevCommands;
import core.commands.easterEgg.OHeyMark;
import core.commands.easterEgg.Stinky;
import core.commands.easterEgg.Yiff;
import core.commands.extra.Rebuild;
import core.commands.extra.Suggestions;
import core.commands.fun.AFK;
import core.commands.fun.JoinVC;
import core.commands.fun.Pass;
import core.commands.general.Invite;
import core.commands.general.Support;
import core.commands.info.Credits;
import core.commands.info.Help;
import core.commands.info.Manual;
import core.commands.info.Ping;
import core.commands.info.Premium;
import core.commands.info.ServerInfo;
import core.commands.info.WhoIs;
import core.commands.moderation.Clean;
import core.commands.moderation.PrefixChange;
import core.commands.moderation.SetJoinChannel;
import core.commands.moderation.SetJoinMessage;
import core.commands.moderation.TempBan;
import core.commands.testing.Flags;
import core.commands.testing.TimeoutTest;
import core.enums.BotMode;
import core.listeners.timeout.CommandTimeout;
import core.listeners.timeout.TimeoutManager;
import core.tools.Permissions;
import core.tools.Tools;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class CommandManager {

	private final Map<String, Command> commands = new HashMap<>();

	public CommandManager() {
		addCommand(new Help(this));
		addCommand(new SetJoinChannel());
		addCommand(new Rebuild());
		addCommand(new Ping());
		addCommand(new SetJoinMessage());
		addCommand(new PrefixChange());
		addCommand(new WhoIs());
		addCommand(new AFK());
		// addCommand(new PermissionsFileManager());
		addCommand(new Invite());
		addCommand(new Support());
		addCommand(new Suggestions());
		addCommand(new Pass());
		addCommand(new Clean());
		addCommand(new Manual());
		addCommand(new ServerInfo());
		addCommand(new TempBan());
		addCommand(new Credits());
		addCommand(new Premium());
		addCommand(new JoinVC());
		addCommand(new Flags());

		// if (Main.devCommands) {
		// DEV COMMANDS
		addCommand(new DevHelp(this));
		addCommand(new Multi());
		// LEGACY addCommand(new FileManaging());
		addCommand(new Todo());
		addCommand(new EmbedTest());
		addCommand(new CreateAction());
		addCommand(new AddDev());
		addCommand(new TimeoutTest());
		// }

		addCommand(new ToggleDevCommands());

		// EASTER EGG COMMANDS
		addCommand(new Yiff());
		addCommand(new Stinky());
		addCommand(new OHeyMark());

		// TESTING COMMANDS
		// addCommand(new TempDeleteMessage());

	}

	private void addCommand(Command c) {
		for (String i : c.getCalls()) {
			if (!commands.containsKey(i)) {
				commands.put(i, c);
			}
		}
	}

	public Collection<Command> getCommands() {
		return commands.values();
	}

	public Command getCommand(String commandName) {
		if (commandName == null) {
			return null;
		}
		return commands.get(commandName);
	}

	void run(GuildMessageReceivedEvent event, String[] split) {
		// final String msg = event.getMessage().getContentRaw();

		final String commandString = split[0].toLowerCase();
		if (commands.containsKey(commandString)) {

			final Command command = commands.get(commandString);

			if (command.getTimeout() > 0) {
				System.out.println("contained");
				String key = command.getName() + ":" + event.getMember().getIdLong();
				final CommandTimeout tm = TimeoutManager.getTimeout(key);
				if (tm != null) {
					System.out.println("exists");
					long now = System.currentTimeMillis();
					boolean e = tm.getDelayDate() <= now;
					if (!e) {
						System.out.println("wait");
						event.getChannel()
								.sendMessage("Sorry, but you have to wait " + (tm.getDelayDate() - now) + "ms").queue();
						if (!Global.DEVIDS.contains(event.getAuthor().getIdLong()) | true) {
							return;
						}
					} else {
						System.out.println("not wait");
						TimeoutManager.delTimeout(key);
						TimeoutManager.addTimeout(command.getName() + ":" + event.getMember().getIdLong(),
								new CommandTimeout(event.getMember().getIdLong(), event.getGuild().getIdLong(), command,
										command.getTimeout()));

					}
				} else {
					System.out.println("not exist");

					TimeoutManager.addTimeout(command.getName() + ":" + event.getMember().getIdLong(),
							new CommandTimeout(event.getMember().getIdLong(), event.getGuild().getIdLong(), command,
									command.getTimeout()));

				}
			} else {
				System.out.println("not contained");
			}

			// if (commands.get(command).moderatorPerms()) {

			// }

			final List<String> args = Arrays.asList(split).subList(1, split.length);
			try {
				Global.executor.execute(() -> {
					try {
						final StopWatch stopWatch = new StopWatch();
						stopWatch.start();
						// flag extractor
						final ArrayList<String> newArgs = new ArrayList<String>();
						boolean time = false;
						// int iter = 0;
						ArrayList<Long> devList = Global.DEVIDS;
						boolean skip = false;
						boolean skipPerms = false;
						for (String x : args) {
							switch (x) {
							case "\\time":
								time = true;
								event.getChannel().sendMessage("Recording time to run command...").queue();
								break;
							case "\\ghost":
								// delete = true;
								break;
							case "\\dct":
								devList = new ArrayList<Long>(Arrays.asList());
								break;
							case "\\perm":
								if (devList.contains(event.getMember().getIdLong())) {
									skipPerms = true;
								} else {
									event.getChannel().sendMessage("You do not have permission to use this flag. . .")
											.queue();
									return;
								}
								break;
							case "\\raw":
								event.getChannel()
										.sendMessage(
												"raw message content:```" + event.getMessage().getContentRaw() + "```")
										.queue();
								skip = true;
								break;
							case "\\del":
								event.getMessage().delete().queue();
								break;
							case "\\mem":
								event.getChannel().sendMessage(String.valueOf(Runtime.getRuntime().freeMemory()) + "/"
										+ String.valueOf(Runtime.getRuntime().maxMemory())).queue();
								break;
							default:
								newArgs.add(x);
							}
						}

						if (!isPubliclyApplicable(command) & !devList.contains(event.getMember().getIdLong())) {
							event.getChannel().sendMessage(
									"Sorry, but you dont have permission from my developers to run that command. . .")
									.queue();
							return;
						}
						if (!(command instanceof core.commands.dev.ToggleDevCommands)) {
							if (!isPubliclyApplicable(command) & devList.contains(event.getMember().getIdLong())
									& !Main.devCommands) {
								event.getChannel().sendMessage("check TDC").queue();
								return;
							}
						}
						if (command.isPremium() & Global.premiumHoes.contains(event.getMember().getIdLong())) {
							event.getChannel().sendMessage("Sorry, but you dont have premium. . .").queue();
							return;
						}
						if (!skipPerms) {
							if (!event.getMember().hasPermission(command.getRequiredPermission())) {
								MessageAction msg = event.getChannel().sendMessage(
										"Sorry, but you do not have permission to use this command!\nMissing : "
												+ Permissions.permissionToString(command.getRequiredPermission()));
								msg.queue();
								return;
							}
						}
						// System.out.println("final run");
						if (!skip) {
							command.run(newArgs, event, devList);
						}

						stopWatch.stop();
						if (time) {
							// System.out.println("Time: " + stopWatch.getTime(TimeUnit.MILLISECONDS));
							event.getChannel()
									.sendMessage("Time to run: " + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms")
									.queue();
						}

						// commands.get(command).run(args, event);
						newArgs.clear();
					} catch (Throwable e) {
						// e.printStackTrace();
						if (Main.mode == BotMode.DEV) {
							final StringWriter sw = new StringWriter();
							final PrintWriter pw = new PrintWriter(sw);
							e.printStackTrace(pw);
							try {
								event.getChannel().sendMessage("```yaml\n" + sw.toString() + "```").queue();
							} catch (java.lang.IllegalArgumentException e1) {
								event.getChannel().sendMessage("Stack trace too large to post, using BMSG").queue();
								Tools.bigMessage(sw.toString(), event.getChannel(), "yml");
							}
							System.out.println("Error caught in: " + e.toString());
							e.printStackTrace();
						} else {
							event.getChannel().sendMessage("```yaml\n" + e + "```").queue(); // sends limited message
							e.printStackTrace();
						}
					}
				});

			} catch (RejectedExecutionException e) {
				e.printStackTrace();
				if (Main.mode == BotMode.DEV) {
					final StringWriter sw = new StringWriter();
					final PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					try {
						event.getChannel().sendMessage("```yaml\n" + sw.toString() + "```").queue();
					} catch (java.lang.IllegalArgumentException e1) {
						event.getChannel().sendMessage("Stack trace too large to post, using BMSG").queue();
						Tools.bigMessage(sw.toString(), event.getChannel(), "yml");
					}
					System.out.println("Error caught in: " + e.toString());
					e.printStackTrace();
				} else {
					event.getChannel()
							.sendMessage("FATAL ERROR:\n```\n" + e + "```please report this to the support server")
							.queue(); // sends limited message
					e.printStackTrace();
				}
			}

			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			// Date date = new Date(System.currentTimeMillis());
			// String timeC = formatter.format(date);
			// System.out.println(timeC + "\n" + "Guild: " + event.getGuild().getName() + "
			// " + event.getGuild().getId()+ " " + event.getChannel().getId() + Colors.GREEN
			// + "\nCommand used by: "+ event.getAuthor().getId() + Colors.RESET + " " +
			// event.getAuthor().getAsTag()+ "\nMessage contents: [" +
			// event.getMessage().getContentRaw().toString() + "]"+ "\nCommand called: \"" +
			// command + "\"");

		}
	}

	private boolean isPubliclyApplicable(Command command) {
		boolean cut = true;
		// for (HelpPage i : HelpPage.values()) {
		switch (command.getPage()) {
		case DEV:
			cut = false;
			break;
		case EGG:
			// cut = true;
			break;
		case Extra:
			break;
		case Fun:
			break;
		case General:
			break;
		case Info:
			break;
		case Moderation:
			break;
		case TESTING:
			cut = false;
			break;
		default:
			cut = false;
			throw new Error("w h a t\nthis should never trigger, EVER");
		}
		// }

		return cut;
	}
}
