package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;

public class Leave extends Action {

	@Override
	public String type() {
		return "leave";
	}

	@Override
	public void start() {
		Main.bot.getGuildById(guildID).leave().queue();
	}

	@Override
	public String[] keys() {
		return new String[] { "guildID" };
	}

}
