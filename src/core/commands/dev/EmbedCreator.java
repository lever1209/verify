package core.commands.dev;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class EmbedCreator implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

	}

	@Override
	public String[] getCalls() {
		return new String[] { "createembed", "ce" };
	}

	@Override
	public String getHelp() {
		return "Command for creating embeds because fox is a dumbass";
	}

	@Override
	public String getUsage() {
		return "uwu owo i dont fucking know";
	}

	@Override
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}
