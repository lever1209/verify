package core.commands.fun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.IO.ObjectIO;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AFK implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.isEmpty()) {
			new File(Global.ROOT + "USER/" + event.getMember().getId() + "/awayMessage").delete();
			event.getChannel().sendMessage("Cleared message").queue();
		} else {
			StringBuilder sB = new StringBuilder();

			for (String i : args) {
				sB.append(i + " ");
			}

			ObjectIO.writeObject(String.valueOf(sB),
					Global.ROOT + "USER/" + event.getMember().getId() + "/awayMessage");
			event.getChannel().sendMessage("Set your message to : " + sB).queue();
		}
	}

	@Override
	public String[] getCalls() {

		return new String[] { "away", "afk", "pingmessage" };
	}

	@Override
	public String getHelp() {

		return "Sets an away message for when you get pinged\nLeave blank to delete the message. . .";
	}

	@Override
	public boolean isHidden() {

		return false;
	}

	@Override
	public Permission getRequiredPermission() {

		return Permission.MESSAGE_WRITE;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Fun;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " [message]";
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
