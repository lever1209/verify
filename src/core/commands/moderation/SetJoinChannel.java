package core.commands.moderation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.IO.JSONIO;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SetJoinChannel implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		if (!new File(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin").exists()) {
			new File(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin").createNewFile();
		}
		JSONIO.keyWrite("joinChannel", event.getChannel().getId(),
				Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin");
		event.getChannel().sendMessage("Set this channel as the default join channel!").queue();
	}

	@Override
	public String[] getCalls() {

		return new String[] { "setJoinChannel", "channel", "sc" };
	}

	@Override
	public String getHelp() {

		return "Sets the join message channel";
	}

	@Override
	public boolean isHidden() {

		return false;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Moderation;
	}

	@Override
	public Permission getRequiredPermission() {

		return Permission.KICK_MEMBERS;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <Channel id/ping>";
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
