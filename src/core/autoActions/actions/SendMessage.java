package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;

public class SendMessage extends Action {

	public void start() { // BUG when we implement sharding, this will
							 // always fail because it uses
							 // Main.bot.getJDA(); and that is only one
							 // instance of the bot, potential solution
							 // is to make an array and use for bot: if
							 // has channel: do action in channel

		// System.out.println("message");
		Main.bot.getTextChannelById(this.channelID).sendMessage(this.string).queue();
	}

	@Override
	public String[] keys() {
		return new String[] { "string", "channelID" };
	}

	@Override
	public String type() {
		return "sendmessage";
	}

}
