package core.commands.dev;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ToggleDevCommands implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (event.getMember().getIdLong() != Global.curse) {
			return;
		}

		if (Main.devCommands) {
			Main.devCommands = false;
			event.getChannel().sendMessage("```Disabled EDC```").queue();
		} else {
			Main.devCommands = true;
			event.getChannel().sendMessage("```Enabled EDC```").queue();
		}
	}

	@Override
	public String[] getCalls() {
		return new String[] { "tdc" };
	}

	@Override
	public String getHelp() {
		return "If you need help with this command, you should not be using it. . .";
	}

	@Override
	public String getUsage() {
		return null;
	}

	@Override
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 60000;
	}

}
