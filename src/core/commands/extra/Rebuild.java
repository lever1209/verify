package core.commands.extra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Rebuild implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.size() < 1) {
			event.getChannel().sendMessage("Rebuilding user directories").queue(message -> {
				try {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					long userID = event.getMember().getIdLong();
					Tools.createDirs(Global.ROOT + "USER/" + userID);
					stopWatch.stop();
					message.editMessage(
							message.getContentRaw() + "\nDone!\n" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms")
							.queue();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} else

		if (args.get(0).contentEquals("-guild") | args.get(0).contentEquals("-server")) {
			event.getChannel().sendMessage("Creating guild directories. . .").queue(message -> {

				try {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					long guildID = event.getGuild().getIdLong();
					long channelID = event.getChannel().getIdLong();
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID);
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/CHANNEL/");
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/CHANNEL/" + channelID);
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS");
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS/REACT");
					Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS/TEMPS");
					stopWatch.stop();
					message.editMessage(
							message.getContentRaw() + "\nDone!\n" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms")
							.queue();

				} catch (IOException e) {
					e.printStackTrace();
				}

			});
		} else if (args.get(0).contentEquals("--all")
				| args.get(0).contentEquals("-a") & devList.contains(event.getMember().getIdLong())) {

			event.getChannel().sendMessage("Rebuilding all directories\n(this may take awhile. . .)").queue(message -> {
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				for (User j : Main.bot.getUserCache().asList()) {
					try {
						Tools.createDirs(Global.ROOT + "USER/" + j.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				for (Guild i : Main.bot.getGuildCache().asList()) {
					long guildID = i.getIdLong();

					for (Member j : i.getMembers()) {
						try {
							Tools.createDirs(Global.ROOT + "USER/" + j.getId());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						Tools.createDirs(Global.ROOT + "GUILD/" + guildID);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/CHANNEL/");
					} catch (IOException e) {
						e.printStackTrace();
					}
					for (GuildChannel j : i.getChannels()) {
						try {
							Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/CHANNEL/" + j.getId());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					try {
						Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS/REACT");
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Tools.createDirs(Global.ROOT + "GUILD/" + guildID + "/TASKS/TEMPS");
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				stopWatch.stop();
				message.editMessage(
						message.getContentRaw() + "\nDone!\n" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms")
						.queue();
			});

		}
		/*
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 * Tools.createDirs();
		 */
	}

	@Override
	public String[] getCalls() {

		return new String[] { "rebuild" };
	}

	@Override
	public String getHelp() {

		return "Rebuilds your servers directory structure, in case something goes wrong";
	}

	@Override
	public boolean isHidden() {

		return false;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Moderation;
	}

	@Override
	public Permission getRequiredPermission() {

		return Permission.MESSAGE_WRITE;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " [-server | -guild]";
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
