package core.commands.general;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class JoinMode implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		event.getChannel().sendMessage("this system is still in development, so this command is disabled").queue();
	}

	public String[] getCalls() {

		return new String[] { "joinmode", "jm" };
	}

	@Override
	public String getHelp() {

		return "Selects the mode for handling new people";
	}

	@Override
	public boolean isHidden() {

		return false;
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
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " [Mode]";
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
