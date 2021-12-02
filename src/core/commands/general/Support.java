package core.commands.general;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Support implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		event.getChannel().sendMessage("Here is the support server invite:").queue();
		// , since your server may have a rule against posting invites, it may get
		// removed, if that happens, use "+Global.Prefix+"dm
		// "+commandCall()[0]).queue();
		event.getChannel().sendMessage("https://discord.gg/bCPqyR9Yqz").queue();
	}

	@Override
	public String[] getCalls() {
		return new String[] { "support", };
	}

	@Override
	public String getHelp() {
		return "Posts an invite to the support server. . .";
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
		return HelpPage.General;
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
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
