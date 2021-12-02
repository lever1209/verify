package core.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.json.simple.parser.ParseException;

import core.Global;
import core.Main;
import core.IO.JSONIO;
import core.tools.Tools;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
		// System.out.println(event.getMember().getUser().getName() + " joined");
		if (event.getMember().getUser().isBot()) {
			// System.out.println(event.getMember().getUser().getName() + " is a bot");
			// 690330688670662827
		}
		try {
			String joinMessage = assets.Emojis.VS_CHECK + " " + event.getMember().getUser().getName() + " has joined!";
			try {
				joinMessage = JSONIO
						.keyRead("joinMessage", Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin")
						.toString();
				Object channelID = JSONIO.keyRead("joinChannel",
						Global.ROOT + "GUILD/" + event.getGuild().getId() + "/userJoin");
				long channelId = Tools.convertToLong(channelID);
				TextChannel channel = Main.bot.getGuildById(event.getGuild().getId()).getTextChannelById(channelId);
				channel.sendMessage(Tools.stringRepsJoin(joinMessage, event)).queue();
			} catch (FileNotFoundException e) {

			} catch (ParseException e) {
				e.printStackTrace();
			}

			// JoinActions.reaction(event, channelId, joinMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
