package core.commands.easterEgg;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Yiff implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		event.getChannel().sendMessage(assets.Emojis.kirboWada).queue();

	}

	@Override
	public String[] getCalls() {
		return new String[] { "yiff", "yoff", "hentai", "woman", };
	}

	@Override
	public String getHelp() {
		return "fek off";
	}

	@Override
	public String getUsage() {
		return "pp";
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
		return HelpPage.EGG;
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
