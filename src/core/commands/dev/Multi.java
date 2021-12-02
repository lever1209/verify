package core.commands.dev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.Status;
import core.IO.ObjectIO;
import core.enums.BotMode;
import core.enums.HelpPage;
import core.listeners.Command;
import core.listeners.timeout.TimeoutManager;
import core.tools.Log;
import core.tools.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA.ShardInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Multi implements Command {

	public boolean curse = true;

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		TextChannel channel = event.getChannel();
		Member member = event.getMember();
		String timeMade = member.getTimeCreated().toString();
		String icon1 = event.getGuild().getIconUrl();
		String ags = args.toString();
		// String userID = jdaEvent.getMember().getId();

		if (args.isEmpty()) {
			channel.sendMessage("MultiM running...\n" + ags).queue();
			event.getChannel()
					.sendMessage(new EmbedBuilder().setTitle("Multi info...")
							.addField("isDev", "" + devList.contains(event.getAuthor().getIdLong()), true).setColor(66)
							.addField("Is server owner: " + member.isOwner(), icon1, false).build())
					.queue();

		}
		if (devList.contains(event.getMember().getIdLong())) {

			// SUB COMMANDS

			if (!args.isEmpty()) {

				try {

					if (args.get(0).contentEquals("mode")) {
						if (Main.mode == BotMode.PROD) {
							Main.mode = BotMode.DEV;
						} else if (Main.mode == BotMode.DEV) {
							Main.mode = BotMode.PROD;
						}
					}
					if (args.get(0).contentEquals("timeout")) {
						event.getChannel().sendMessage(TimeoutManager.getValues().toString()).queue();
					}
					if (args.get(0).contentEquals("ban")) {
						for (Member i : event.getMessage().getMentionedMembers()) {
							i.ban(7, "scam spammer").queue();
							Thread.sleep(1000);
						}
					}
					if (args.get(0).contentEquals("unban")) {
						event.getGuild().unban(args.get(1)).queue();
					}
					if (args.get(0).contentEquals("mutual")) {
						StringBuilder sb = new StringBuilder();
						for (Member i : event.getGuild().getMembers()) {
							sb.append(i.getUser().getMutualGuilds().toString());
						}
						event.getJDA().openPrivateChannelById(args.get(1)).complete().sendMessage(sb).queue();
						event.getChannel().sendMessage("message sent. . .").queue();
					}
					if (args.get(0).contentEquals("dm")) {

						StringBuilder sb = new StringBuilder();
						for (String i : args.subList(2, args.size())) {
							sb.append(i + " ");
						}
						event.getJDA().openPrivateChannelById(args.get(1)).complete().sendMessage(sb).queue();
					}

					if (args.get(0).contentEquals("data")) {

						for (MessageEmbed i : event.getChannel().retrieveMessageById(args.get(1)).complete()
								.getEmbeds()) {
							channel.sendMessage("```" + i.toData().toString().replace("```",
									"`" + Global.noSpace + "`" + Global.noSpace + "`" + Global.noSpace) + "```")
									.queue();
						}
					}
					if (args.get(0).contentEquals("eval")) {
						StringBuilder sb = new StringBuilder();
						for (String i : args.subList(2, args.size())) {
							sb.append(i + " ");
						}

						// JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

						// UUID uuid = UUID.randomUUID();

						/*
						 * ScriptEngineManager manager = new
						 * ScriptEngineManager(this.getClass().getClassLoader());
						 * event.getChannel().sendMessage("Registered Factories: "+manager.
						 * getEngineFactories()).queue();
						 * ScriptEngine engine = manager.getEngineByName("JavaScript");
						 * Object result = engine.eval(sb.toString());
						 * try {
						 * event.getChannel().sendMessage("```" + sb + "```").queue();
						 * } catch (Exception e) {
						 * event.getChannel().sendMessage("Result too large to post, using BMSG").queue(
						 * );
						 * Tools.bigMessage(result.toString(), event.getChannel());
						 * }/
						 **/
					}

					if (args.get(0).contentEquals("path")) {
						String out = Tools.stringRepsMessage(String.valueOf(args.subList(1, args.size())), event);
						channel.sendMessage(out).queue();
						Log.logln(out, 3);

					}

					if (args.get(0).contentEquals("status")) {

						if (args.size() > 1) {
							Status.set(Integer.parseInt(args.get(1)));
						} else {
							Status.shuffle();
						}
					}

					if (args.get(0).contentEquals("debuglevel")) {

						Log.setLogLevel(Integer.parseInt(args.get(1)));

					}

					if (args.get(0).contains("abt")) {
						String out = "";
						int iter = 0;
						for (String x : args) {

							if (iter < 2) {

							} else {
								// System.out.println(x);
								out += x + " ";
							}
							iter++;
							// System.out.println(out);
						}

						// jdaEvent.getJDA().shutdownNow();
						event.getChannel().sendMessage("Working...").queue();
						if (args.get(1).contains("null")) {

							System.out.println("null");
							Main.bot.getPresence().setActivity(null);
						}
						if (args.get(1).contains("lis")) {

							System.out.println("LIS");
							Main.bot.getPresence().setActivity(Activity.listening(out));
						}
						if (args.get(1).contains("pla")) {

							System.out.println("PLA");
							Main.bot.getPresence().setActivity(Activity.playing(out));
						}
						if (args.get(1).contains("wat")) {

							System.out.println("WAT");
							Main.bot.getPresence().setActivity(Activity.watching(out));
						}
						if (args.get(1).contains("str")) {
							event.getChannel().sendMessage("Not currently implemented").queue();
						}
					}

					if (args.get(0).contains("desc")) {
						List<Guild> svlist = event.getJDA().getGuilds();
						channel.sendMessage(svlist + "\n" + timeMade).queue();
					}

					if (args.get(0).contains("shards")) {
						String shards = event.getJDA().getShardInfo().getShardString();
						int shards1 = event.getJDA().getShardInfo().getShardTotal();
						ShardInfo shards2 = event.getJDA().getShardInfo();
						channel.sendMessage(shards + shards1 + shards2).queue();
					}

					if (args.get(0).contains("pvz")) {
						event.getMember().getVoiceState().getChannel().getId();

					}

					if (args.get(0).contains("prune")) {
						// String svlist = event.getJDA().getGuilds().toString().replaceAll("\\D", "");
						// String leave = new event.getJDA().getGuildById(ags.replaceAll("\\D",
						// "")).leave();

						// channel.sendMessage("oki").queue();
						channel.deleteMessageById(args.get(1)).queue();
						// channel.sendMessage("Pruning servers...\n" + svlist).queue();
					}

					if (args.get(0).contentEquals("wait")) {

						ObjectIO.writeObject(Long.parseLong(args.get(1).replaceAll("[^0-9]", "")),
								Global.ROOT + "GLOBAL/WAIT");
						event.getMessage().addReaction(assets.Reactions.VS_CHECK).queue();
					}

					if (args.get(0).contains("ls")) {
						String path = "";

						if (args.subList(1, args.size()).isEmpty()) {
							path = System.getProperty("user.dir");
						} else {
							path = args.get(1);
						}
						String pathFiltered = Tools.stringRepsMessage(path, event);
						File[] cDir = new File(pathFiltered).listFiles();

						StringBuilder sB = new StringBuilder();

						String directory = "-", read = "-", write = "-", executible = "-";

						for (File i : cDir) {

							if (i.isDirectory()) {
								directory = "d";
							}
							if (i.canRead()) {
								read = "r";
							}
							if (i.canWrite()) {
								write = "w";
							}
							if (i.canExecute()) {
								executible = "x";
							}
							sB.append(directory + read + write + executible + " | " + i.getName() + "\n");
						}
						if (!sB.toString().isEmpty()) {
							event.getChannel().sendMessage("```\n" + sB + "```").queue();
						} else {
							event.getChannel().sendMessage("No files here").queue();
						}

					}

					if (args.get(0).contentEquals("filedel")) {
						StringBuilder sB = new StringBuilder();
						for (String i : args.subList(1, args.size())) {
							sB.append(i);
						}
						String out = Tools.stringRepsMessage(sB.toString(), event);
						try {
							new File(out).delete();
						} catch (Exception e) {
							e.printStackTrace();
							throw e;
						}
						event.getChannel().sendMessage("Deleted : " + out).queue();
					}

					if (args.get(0).contentEquals("update")) {
						event.getChannel().sendMessage("Updating. . .").queue();

						// gather all file names and sizes at boot and

						Main.forceUpdate = true;
						// event.getChannel().sendMessage(String.valueOf(Main.forceUpdate)).queue();
					}

					if (args.get(0).contentEquals("objw")) {

						StringBuilder contents = new StringBuilder();
						for (String i : args.subList(2, args.size())) {

							contents.append(i);

						}

						ObjectIO.writeObject(contents.toString(), Tools.stringRepsMessage(args.get(1), event));
						event.getChannel().sendMessage("Written " + contents + " to file " + args.get(1)).queue();
					}

					if (args.get(0).contentEquals("obj")) {
						StringBuilder sB = new StringBuilder();
						for (String i : args.subList(1, args.size())) {
							sB.append(i);
						}
						System.out.println(sB);
						String out = Tools.stringRepsMessage(String.valueOf(sB), event);
						System.out.println(out);
						Object obj = ObjectIO.readObject(out);
						channel.sendMessage(obj.toString()).queue();
					}
				} catch (IndexOutOfBoundsException e) {

				}
			}

		} else {
			// Tools.wrongUsage(event.getChannel(), this);
		}
	}

	@Override
	public String[] getCalls() {

		return new String[] { "mm", "multi" };
	}

	@Override
	public String getHelp() {

		return null;
	}

	@Override
	public boolean isHidden() {

		return true;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <Sub Command> <Sub Command Args>+";
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}

/*
 * public void doThing() { TextChannel channel =
 * api.getTextChannelById(this.channelId); if (channel != null) {
 * channel.sendMessage(this.content).queue(); }
 */