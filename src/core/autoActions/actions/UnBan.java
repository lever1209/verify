package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;

public class UnBan extends Action {

	@Override
	public String type() {
		return "unban";
	}

	@Override
	public void start() {
		Main.bot.getGuildById(guildID).unban(Main.bot.getUserById(userID)).queue();
	}

	@Override
	public String[] keys() {
		return new String[] { "guildID", "memberID" };
	}

}
