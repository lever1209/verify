package core.commands.testing;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import core.listeners.Command;
import core.listeners.timeout.TimeoutManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TimeoutTest implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		event.getChannel().sendMessage("Testing. . .").queue();
		event.getChannel().sendMessage(TimeoutManager.getKeys().toString()).queue();
	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "testtimeout", "tt" };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Tests the timeout system. . .";
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return true;
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
		return HelpPage.TESTING;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 10000;
	}
}
