package core.commands.testing;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.autoActions.actions.DeleteMessage;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TempDeleteMessage implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		Main.actionList.add(new DeleteMessage().setChannelID(Long.parseLong(args.get(0)))
				.setMessageID(Long.parseLong(args.get(1))).setTime(Long.parseLong(args.get(2))));
	}

	@Override
	public String[] getCalls() {
		return new String[] { "tmpdelm" };
	}

	@Override
	public String getHelp() {
		return "deletes a message after a delay using the action engine";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getName() + " <channelID> <messageID> <delay>";
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public Permission getRequiredPermission() {
		return Permission.MESSAGE_MANAGE;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.TESTING;
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
