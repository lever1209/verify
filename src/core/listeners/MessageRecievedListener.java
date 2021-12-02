package core.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import core.Global;
import core.Main;
import core.IO.ObjectIO;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageRecievedListener extends ListenerAdapter {

	public final CommandManager m = new CommandManager();

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		System.out.println("Running...");
		System.out.println(event.getGuildTotalCount());
	}

	@Override
	public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();

		if (event.getMessage().getContentRaw().equalsIgnoreCase(Global.Prefix + Global.Prefix)
				&& Global.DEVIDS.contains(event.getMember().getIdLong())) {
			Message msg1 = event.getMessage();
			msg1.addReaction(assets.Reactions.VS_CHECK).queue();
			long timeNow = System.currentTimeMillis();
			System.out.println("Logging out of Discord API : Dev ID used : " + event.getMember().getId());
			Main.bot.getPresence().setActivity(Activity.watching("the shutdown script"));
			Global.running = false;
			while (true) {
				if (System.currentTimeMillis() > timeNow + 2000) {
					break;
				}
			}

			event.getJDA().shutdownNow(); // fixes shutdown exception, was : shutdown();
			System.exit(0);
		} else if (event.getMessage().getContentRaw().equalsIgnoreCase(Global.Prefix + "leave")) {
			if (event.getMember().isOwner() | Global.DEVIDS.contains(event.getMember().getIdLong())) {
				event.getGuild().leave().queue();
				event.getMember().hasPermission(
						Arrays.asList(new Permission[] { Permission.KICK_MEMBERS, Permission.BAN_MEMBERS }));
			}
			return;
		}
		String cPrefix = Global.Prefix;
		try {
			cPrefix = ObjectIO.readObject(Global.ROOT + "GUILD/" + event.getGuild().getId() + "/prefix").toString();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		String[] prefixArray = { cPrefix, "<@!" + event.getJDA().getSelfUser().getId() + "> ",
				"<@!" + event.getJDA().getSelfUser().getId() + ">", }; // TODO fix ping prefix

		for (String i : prefixArray) {
			if (!event.getAuthor().isBot() & msg.getContentRaw().startsWith(i)) {
				String[] split = msg.getContentRaw().replaceFirst("(?i)" + Pattern.quote(i), "").split("\\s+");
				m.run(event, split);
				break;
			}
		}
		if (!event.getAuthor().isBot()) {
			for (Member i : event.getMessage().getMentionedMembers()) {
				if (new File(Global.ROOT + "USER/" + i.getId() + "/awayMessage").exists()) {
					try {
						event.getChannel().sendMessage("Away message from : " + i.getEffectiveName() + " "
								+ event.getAuthor().getAsMention() + "\n"
								+ ObjectIO.readObject(Global.ROOT + "USER/" + i.getId() + "/awayMessage").toString())
								.queue(msg2 -> {
									msg2.delete().queueAfter(30, TimeUnit.SECONDS);
								});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			if (msg.getContentRaw().contentEquals("ups") & Global.DEVIDS.contains(event.getMember().getIdLong())) {
				event.getChannel().sendMessage("updating").queue();
				Main.forceUpdate = true;
			}
		} catch (Exception e) {
		}
		// System.out.println(msg.getContentRaw());

	}
}
