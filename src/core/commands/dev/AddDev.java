package core.commands.dev;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AddDev implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		// TODO Auto-generated method stub
		Global.DEVIDS.add(Long.parseLong(args.get(0).replaceAll("[^0-9]", "")));
		event.getMessage().addReaction(assets.Reactions.thumbsUp).queue();
	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "adddev" };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
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
		return HelpPage.DEV;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 60000;
	}

}
