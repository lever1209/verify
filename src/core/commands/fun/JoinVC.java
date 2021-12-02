package core.commands.fun;

import java.util.ArrayList;
import java.util.List;

import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinVC implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		Member self = event.getGuild().getSelfMember();
		GuildVoiceState selfVS = self.getVoiceState();

		if (selfVS.inVoiceChannel()) {
			event.getChannel().sendMessage("Sorry, but i am already occupied in another channel. . .").queue();
			return;
		}

		Member member = event.getMember();
		GuildVoiceState memberVS = member.getVoiceState();

		if (!memberVS.inVoiceChannel()) {
			event.getChannel().sendMessage("Sorry, but you are not in a voice channel. . .").queue();
			return;
		}

		AudioManager am = event.getGuild().getAudioManager();
		VoiceChannel vc = memberVS.getChannel();

		if (!self.hasPermission(Permission.VOICE_CONNECT)) {
			event.getChannel()
					.sendMessage("Sorry, but i dont have the " + Permission.VOICE_CONNECT + " permission. . .").queue();
			return;
		}

		event.getChannel().sendMessage("Connecting to vc: " + vc.getId()).queue();
		am.openAudioConnection(vc);

	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "join" };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Selects the current vc or vcid for the bot to join";
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return "idk, havnt written it yet";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isPremium() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		// TODO Auto-generated method stub
		return Permission.VOICE_CONNECT;
	}

	@Override
	public HelpPage getPage() {
		// TODO Auto-generated method stub
		return HelpPage.Fun;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}
