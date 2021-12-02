package core.commands.extra;

import java.util.ArrayList;
import java.util.List;

import assets.Reactions;
import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Suggestions implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		StringBuilder sB = new StringBuilder();

		for (String i : args) {
			sB.append(i + " ");
		}

		if (!sB.toString().isEmpty()) {

			event.getChannel().sendMessage("Your suggestion has been submitted, thanks~!").queue();

			Message msg = Global.suggestionBox.sendMessage("```md\n# Suggestion from : " + event.getMember().getId()
					+ " : " + event.getMember().getUser().getAsTag() + "```\n" + sB).complete();
			msg.addReaction(Reactions.VS_CHECK).queue();
			msg.addReaction(Reactions.VS_NOTCHECK).queue();
		} else {
			// event.getChannel().sendMessage("Sorry, but you must suggest
			// something").queue();
			Tools.wrongUsage(event.getChannel(), this);
		}
	}

	@Override
	public String[] getCalls() {
		return new String[] { "suggestion", "suggest", "sug" };
	}

	@Override
	public String getHelp() {
		return "Posts a suggestion in our suggestion channel";
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
		return HelpPage.Extra;
	}

	@Override
	public String getName() {
		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <Suggestion>+";
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
