package core.listeners;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NickChangeListener extends ListenerAdapter {

	public void onNicknameChange(@Nonnull GuildMemberUpdateNicknameEvent event) {
		System.out.println("RAN");
		if (event.getMember().getId().contentEquals("342047193294831619")) {
			System.out.println("curse");
		}
		if (event.getMember().getId().contentEquals("611729915645263878")) {
			System.out.println("setter");
		}
		System.out.println(event.getEntity().getAsMention());
		System.out.println(event.getNewNickname());
		System.out.println(event.getUser().getAsMention());
	}

}
