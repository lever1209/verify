package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;

public class Ban extends Action {

	@Override
	public String type() {
		return "ban";
	}

	@Override
	public void start() {

		Main.bot.getGuildById(guildID).unban(String.valueOf(userID)).queue();

	}

	@Override
	public String[] keys() {
		return new String[] { "userID", "guildID" };
	}

}
