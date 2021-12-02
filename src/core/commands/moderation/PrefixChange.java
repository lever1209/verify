package core.commands.moderation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.IO.ObjectIO;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PrefixChange implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.isEmpty()) {
			new File(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/prefix").delete();
			event.getChannel().sendMessage("Deleted prefix").queue();
		} else {
			ObjectIO.writeObject(args.get(0), Global.ROOT + "GUILD/" + event.getGuild().getId() + "/prefix");
			event.getChannel().sendMessage("Changed prefix to " + args.get(0)).queue();
		}
	}

	@Override
	public String[] getCalls() {

		return new String[] { "prefix" };
	}

	@Override
	public String getHelp() {

		return "Set your prefix!";
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
		return Global.Prefix + getCalls()[0] + " <Prefix>";
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
