package core.listeners;

import javax.annotation.Nonnull;

import core.Main;
import core.enums.BotMode;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PMRecievedListener extends ListenerAdapter {

	@Override
	public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
		if (!event.getAuthor().isBot()) {
			if (Main.mode == BotMode.PROD) {
				event.getChannel().sendMessage(
						"Sorry! but there is no need for the bot to use dms rn as it is a *server* user verification bot\nif enough people request it, i will add this feature later")
						.queue();
			} else {
				event.getChannel()
						.sendMessage("https://tenor.com/view/rick-astley-rick-roll-dancing-dance-moves-gif-14097983")
						.queue();
			}
		}
	}

	/*
	 * public final CommandManager m = new CommandManager();
	 * 
	 * @Override
	 * public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent
	 * event) {
	 * Message msg = event.getMessage();
	 * 
	 * if (event.getMessage().getContentRaw().equalsIgnoreCase(Constants.Prefix +
	 * Constants.Prefix)
	 * && Tools.PMDevIdCheck(event, Constants.DEVIDS)) {
	 * Message msg1 = event.getMessage();
	 * msg1.addReaction(assets.Reactions.VS_CHECK).queue();
	 * long timeNow = System.currentTimeMillis();
	 * System.out.println("Logging out of Discord API : Dev ID used : " +
	 * event.getAuthor().getId());
	 * Main.bot.getPresence().setActivity(Activity.watching("the shutdown script"));
	 * Constants.running = false;
	 * while (true) {
	 * if (System.currentTimeMillis() > timeNow + 2000) {
	 * break;
	 * }
	 * }
	 * 
	 * event.getJDA().shutdownNow(); // fixes shutdown exception, was : shutdown();
	 * System.exit(0);
	 * }
	 * 
	 * 
	 * if (!event.getAuthor().isBot() &
	 * msg.getContentRaw().startsWith(Constants.Prefix)) {
	 * System.out.println("Command call recieved...\n[" + msg.getContentRaw() +
	 * "]");
	 * String[] split = msg.getContentRaw().replaceFirst("(?i)" +
	 * Pattern.quote(Constants.Prefix), "").split("\\s+");
	 * m.run(event, split);
	 * break;
	 * 
	 * }
	 * 
	 * }
	 */
}
