package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;

public class DeleteMessage extends Action {

	@Override
	public String type() {
		return "deletemessage";
	}

	@Override
	public void start() {
		Main.bot.getTextChannelById(channelID).deleteMessageById(messageID).queue();
	}

	@Override
	public String[] keys() {
		return new String[] { "channelID", "messageID" };
	}
}
