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

public class Kick implements Command {
	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		if (args.size() < 1) {
			Tools.wrongUsage(event.getChannel(), this);
		} else {
			long targetID = Long.parseLong(args.get(0).replaceAll("[^0-9]", ""));
			Member target = event.getGuild().getMemberById(targetID);
			if (target == null) {
				event.getChannel().sendMessage("User does not exist. . .").queue();
			}
			Member moderator = event.getMember();
			// Remember to check if the modertor canInteract with the target as well!
			if (!moderator.hasPermission(Permission.KICK_MEMBERS) || !moderator.canInteract(target)) {
				event.getChannel().sendMessage("You do not have the " + Permission.KICK_MEMBERS + " permission. . .")
						.queue();
				return;
			}
			if (!event.getGuild().getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
				event.getChannel().sendMessage("I cant kick members. . .").queue();
				return;
			}
			if (!event.getGuild().getSelfMember().canInteract(target)) {
				event.getChannel().sendMessage("I cant kick this user because they have a role higher than mine. . .")
						.queue();
				return;
			}
			StringBuilder reason = new StringBuilder();
			if (args.size() > 1) {
				for (String i : args.subList(1, args.size())) {
					reason.append(i);
				}
			}
			try {
				target.kick(reason.toString() == "" ? null : reason.toString()).queue();
				event.getChannel().sendMessage("beaned " + target.getUser().getName()).queue();
			} catch (Exception e) {
				event.getChannel().sendMessage("failed to bean " + target.getUser().getName()).queue();
			}
		}
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getCalls() {
		return new String[] { "Kick" };
	}

	@Override
	public String getHelp() {
		return "Kicks a user";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
	}

	@Override
	public Permission getRequiredPermission() {
		return Permission.KICK_MEMBERS;
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
