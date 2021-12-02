package core.commands.easterEgg;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Stinky implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		event.getChannel().sendMessage("https://foxis.very-stinky.com/kre6528hf9a.gif").queue();
	}

	@Override
	public String[] getCalls() {
		return new String[] { "stinky" };
	}

	@Override
	public String getHelp() {
		return "fox is stinky";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.EGG;
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
