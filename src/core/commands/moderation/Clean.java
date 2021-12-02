package core.commands.moderation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.BotMode;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Clean implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.size() == 1) {
			if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				event.getChannel().sendMessage("You don't have the **MESSAGE_MANAGE** permission!").queue();
				return;
			}
			int num = 0;
			try {
				num = Integer.parseInt(args.get(0));
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Enter a number dumbass");
			}
			event.getMessage().delete().complete();
			int currentNum = num / 100;
			if (currentNum == 0) {
				List<Message> msg = event.getChannel().getHistory().retrievePast(num).complete();
				event.getChannel().purgeMessages(msg);
				// event.getChannel().sendMessage("Successfully purged `" + num + "`
				// messages.").queue();
				return;
			}
			try {
				for (int i = 0; i <= currentNum; i++) {
					if (i == num) {
						List<Message> msg = event.getChannel().getHistory().retrievePast(num).complete();
						event.getChannel().purgeMessages(msg);
						// event.getChannel().sendMessage("Successfully purged `" + num + "`
						// messages.").queue();
					} else {
						List<Message> msg = event.getChannel().getHistory().retrievePast(100).complete();
						event.getChannel().purgeMessages(msg);
						num -= 100;
					}
				}
			} catch (Exception e) {
				if (Main.mode == BotMode.DEV) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					event.getChannel().sendMessage("```\n" + sw.toString() + "```").queue();
					System.out.println("Error caught in: " + e.toString());
					e.printStackTrace();
				} else {
					event.getChannel().sendMessage("```\n" + e + "```").queue(); // sends limited message
					e.printStackTrace();
				}
				return;
			}
		} else if (args.size() == 2) {

			if (args.get(0).contentEquals("-id") | args.get(0).contentEquals("-i")) {
				long msgID = Long.parseLong(args.get(1));
				event.getChannel().retrieveMessageById(msgID).complete();

				for (Message i : event.getChannel().getIterableHistory()) {
					if (i.getIdLong() == msgID) {
						break;
					} else {
						i.delete().queue();
						Thread.sleep(1000);
					}
				}
				// i need a better way to get args, flags, and args of flags
			} else if (args.get(0).contentEquals("-u") | args.get(0).contentEquals("-user")) {

				long userID = Long.parseLong(args.get(1).replaceAll("[^0-9]", ""));
				if (event.getJDA().getUserById(userID) == null) {
					throw new NullPointerException("Null user id");
				}

				for (Message i : event.getChannel().getIterableHistory()) {
					if (i.getAuthor().getIdLong() == userID) {
						i.delete().complete();
						Thread.sleep(1000);
					}

				}

			} else {
				Tools.wrongUsage(event.getChannel(), this);
			}

		} else {
			Tools.wrongUsage(event.getChannel(), this);
		}

	}

	@Override
	public String[] getCalls() {
		return new String[] { "purge", "p", "cls", "deletemessage", };
	}

	@Override
	public String getHelp() {
		return "A usefull tool for mass removing messages from a channel. . .";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <<amount> | [-u | -user] <userID/Ping> | [-i | -id] <messageID>> ";
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Moderation;
	}

	@Override
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 20000;
	}
}
