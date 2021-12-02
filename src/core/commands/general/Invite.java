package core.commands.general;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Invite implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		event.getChannel().sendMessage("Here is the bots invite link:\nhttps://discord.com/oauth2/authorize?client_id="
				+ Main.bot.getSelfUser().getIdLong() + "&permissions=-1&scope=bot").complete();

	}

	@Override
	public String[] getCalls() {
		return new String[] { "invite", };
	}

	@Override
	public String getHelp() {
		return "Returns an invite link for the bot. . .";
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
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 20000;
	}
}
