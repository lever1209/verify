package core.autoActions.legacy;

import java.util.Random;

import org.json.simple.JSONObject;

import core.tools.Tools;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class JoinActions {

	@SuppressWarnings("unchecked")
	public static void reaction(GuildMemberJoinEvent event, long filteringChannelID, String messageContents) {

		JSONObject obj = new JSONObject();
		event.getGuild().getTextChannelById(filteringChannelID)
				.sendMessage(Tools.stringRepsJoin(messageContents, event)).queue(msg -> {
					msg.addReaction(assets.Reactions.thumbsUp).queue();
					msg.addReaction(assets.Reactions.kirboWada).queue();

					obj.put("guildID", msg.getGuild().getId());
					obj.put("messageID", msg.getId());
					obj.put("channelID", msg.getChannel().getId());
					obj.put("memberID", event.getMember().getId());
				});

	}

	public static String password(int length) {
		String[] cha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", // "`",
				"~", "1", "!", "2", "@", "3", "#", "4", "$", "5", "%", "6", "^", "7", "&", "8", "*", "9", "(", "0", ")",
				"-", "_", "=", "+", "[", "{", "]", "}", "\\", "|", ";", ":", "'", "\"", ",", "<", ".", ">", "/", "?" };
		StringBuilder sB = new StringBuilder();
		for (int i = 0; i <= length; i++) {
			sB.append(cha[new Random().nextInt(cha.length)]);
		}
		return sB.toString();
	}

	@SuppressWarnings("unchecked")
	public static void reaction(GuildMessageReceivedEvent event, String id, String messageContents) {
		JSONObject obj = new JSONObject();
		event.getGuild().getTextChannelById(id).sendMessage(Tools.stringRepsMessage(messageContents, event))
				.queue(msg -> {
					msg.addReaction(assets.Reactions.thumbsUp).queue();
					msg.addReaction(assets.Reactions.kirboWada).queue();

					obj.put("guildID", msg.getGuild().getId());
					obj.put("messageID", msg.getId());
					obj.put("channelID", msg.getChannel().getId());
					obj.put("memberID", event.getMember().getId());
				});
	}

}
