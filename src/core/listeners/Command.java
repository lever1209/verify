package core.listeners;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

	void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception;

	String[] getCalls();

	String getHelp();

	String getUsage();

	String getName();

	boolean isHidden();

	boolean isPremium();

	Permission getRequiredPermission();

	HelpPage getPage();

	int getTimeout();

}
