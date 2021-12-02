package core.commands.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Global;
import core.autoActions.legacy.JoinActions;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Pass implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.size() == 1) {
			if (args.get(0).contentEquals("--uuid") | args.get(0).contentEquals("-u")) {
				UUID uuid = UUID.randomUUID();
				event.getChannel().sendMessage(uuid.toString()).queue();
			} else {
				try {
					int length = Integer.parseInt(args.get(0).replaceAll("[^0-9]", ""));
					if (length <= 400) {
						event.getChannel().sendMessage("`" + JoinActions.password(length) + "`").queue();
					}
				} catch (IndexOutOfBoundsException | NumberFormatException e) {
					Tools.wrongUsage(event.getChannel(), this);
				}
			}
		} else {
			Tools.wrongUsage(event.getChannel(), this);
		}
		// JoinActions.reaction(event, event.getChannel().getId(), "eeeeeeee");
	}

	@Override
	public String[] getCalls() {
		return new String[] { "password", "pass", };
	}

	@Override
	public String getHelp() {
		return "Generates a password with max length of 400, this is generated with java.util.Random()\nCan also generate a type 4 UUID with --uuid or -u";
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
		return HelpPage.Fun;
	}

	@Override
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <Length>";
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
