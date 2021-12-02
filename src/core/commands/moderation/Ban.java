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

public class Ban implements Command {

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
			if (!moderator.hasPermission(Permission.BAN_MEMBERS) || !moderator.canInteract(target)) {
				event.getChannel().sendMessage("You do not have the " + Permission.BAN_MEMBERS + " permission. . .")
						.queue();
				return;
			}
			if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
				event.getChannel().sendMessage("I cant ban members. . .").queue();
				return;
			}
			if (!event.getGuild().getSelfMember().canInteract(target)) {
				event.getChannel().sendMessage("I cant ban this user because they have a role higher than mine. . .")
						.queue();
				return;
			}
			int days = 0;
			String reason = "";
			try {
				days = Integer.parseInt(args.get(1));
			} catch (Exception e) {
			}
			try {
				if (days == 0) {
					reason = String.join(" ", args.subList(1, args.size()));
				} else {
					reason = String.join(" ", args.subList(2, args.size()));
				}
			} catch (Exception e) {
			}
			try {
				target.ban(days == 0 ? days : 0, reason == "" ? null : reason).queue();
				event.getChannel().sendMessage("beaned " + target.getUser().getName()).queue();
			} catch (Exception e) {
				event.getChannel().sendMessage("failed to bean " + target.getUser().getName()).queue();
			}
		}
	}

	@Override
	public String[] getCalls() {
		return new String[] { "ban", "bean" };
	}

	@Override
	public String getHelp() {
		return "Bans a user from your guild, banning by member name is not allowed as it may result in banning the wrong user";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <userID | @user>";
	}

	@Override
	public boolean isHidden() {
		return false;
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
		return 5000;
	}
}
