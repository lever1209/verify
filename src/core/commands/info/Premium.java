package core.commands.info;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Premium implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		try {
			if (args.size() == 0) {
				if (Global.premiumHoes.contains(event.getAuthor().getIdLong())) {
					event.getChannel().sendMessage("You have premuim " + assets.Emojis.thumbsUp).queue();
				} else {
					event.getChannel().sendMessage("you do not have premium ;-;").queue();
				}

			} else if (args.size() == 1) {
				long uid = Long.parseLong(args.get(0).replaceAll("[^0-9]", ""));
				try {
					Main.bot.retrieveUserById(uid).complete();
				} catch (Throwable e) {
					event.getChannel().sendMessage("This ID is invalid ```yaml\n" + e.toString() + "```").queue();
					return;
				}

				if (Global.premiumHoes.contains(uid)) {
					event.getChannel().sendMessage("This user has premuim " + assets.Emojis.thumbsUp).queue();
				} else {
					event.getChannel().sendMessage("This user does not have premium ;-;").queue();
				}

			} else {
				Tools.wrongUsage(event.getChannel(), this);
			}
		} catch (

		java.lang.NumberFormatException e) {
			event.getChannel().sendMessage("Sorry, but the number you entered is not valid. . .\n" + e.toString())
					.queue();
		}
	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "premium", "pre", };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Tests if a user has premium or not. . .";
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return Global.Prefix + getName() + " [userID]";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPremium() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelpPage getPage() {
		// TODO Auto-generated method stub
		return HelpPage.Info;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

}
