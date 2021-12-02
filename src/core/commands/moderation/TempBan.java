package core.commands.moderation;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TempBan implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.size() == 2) {

		} else if (args.size() == 4) {
			try {
				Member ban = event.getGuild().getMemberById(Long.parseLong(args.get(2).replaceAll("[^0-9]", "")));
				ban.ban(0);
				long delay;
				switch (args.get(1).toLowerCase()) {
				case "day":
					delay = Long.parseLong(args.get(3)) * 86400000;
					break;
				case "hour":
					delay = Long.parseLong(args.get(3)) * 3600000;
					break;
				case "minute":
					delay = Long.parseLong(args.get(3)) * 60000;
					break;
				default:
					throw new IllegalArgumentException("Enter the correct unit. . .");
				}

				new core.autoActions.actions.UnBan().setGuildID(event.getGuild().getIdLong()).setUserID(ban.getIdLong())
						.setTime(delay);
			} catch (Exception e) {
				throw new Exception("something went wrong", e);
			}
			event.getChannel().sendMessage("Banned user. . .").queue();
		} else {
			Tools.wrongUsage(event.getChannel(), this);
		}

	}

	@Override
	public String[] getCalls() {
		return new String[] { "tempban", "tmpb" };
	}

	@Override
	public String getHelp() {
		return "Bans a user for a set amount of milliseconds by default\nUnits supported: day, hour, minute";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getName() + " <<userID> <delay> | <-u | -unit> <unit> <userID> <delay>>";
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public Permission getRequiredPermission() {
		return Permission.BAN_MEMBERS;
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
		return 0;
	}
}
