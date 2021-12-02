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

public class SetJoinMessage implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		StringBuilder sB = new StringBuilder();

		for (String i : args) {
			sB.append(i + " ");
		}

		String write = sB.toString();

		if (!new File(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin").exists()) {
			new File(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin").createNewFile();
		}
		JSONIO.keyWrite("joinMessage", write, Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin");

		event.getChannel().sendMessage("Set join message to : " + write).queue();

	}

	@Override
	public String[] getCalls() {

		return new String[] { "setjoinmessage", "message", "sm" };
	}

	@Override
	public String getHelp() {

		return "Set the join message when someone joins the server. . .";
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
		return Global.Prefix + getCalls()[0] + " <Message>+";
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
