package core.commands.easterEgg;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OHeyMark implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		// TODO Auto-generated method stub
		event.getChannel().sendMessage("Hewlo mark").queue();
	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "mark", "ma" };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "this is a secret";
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "idk";
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
		return HelpPage.EGG;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

}
