package core.commands.dev;

import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Todo implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		event.getChannel().sendMessage("" + assets.Emojis.VS_CHECK + " Password Verification\r\n" + ""
				+ assets.Emojis.VS_CHECK + " Reaction Verification.\r\n" + "" + assets.Emojis.VS_CHECK
				+ " Command Verification.\r\n" + "" + assets.Emojis.VS_CHECK + " Captcha Verification.\r\n" + ""
				+ assets.Emojis.VS_CHECK + " Form Verification (manual).\r\n" + "" + assets.Emojis.VS_CHECK
				+ " Discord Built in verification.\r\n" + "" + assets.Emojis.VS_CHECK + " Verify Setup Command\r\n"
				+ "~~" + assets.Emojis.VS_CHECK + " Help Command~~\r\n" + "~~" + assets.Emojis.VS_CHECK
				+ " whois command~~\r\n" + "~~" + assets.Emojis.VS_CHECK + " Server Info Command~~\r\n" + ""
				+ assets.Emojis.VS_CHECK + " AFK Command\r\n" + "" + assets.Emojis.VS_CHECK + " Ban Command\r\n" + ""
				+ assets.Emojis.VS_CHECK + " Massban command\r\n" + "" + assets.Emojis.VS_CHECK + " Tempban Command\r\n"
				+ "" + assets.Emojis.VS_CHECK + " Softban Command\r\n" + "" + assets.Emojis.VS_CHECK
				+ " Kick Command\r\n" + "" + assets.Emojis.VS_CHECK + " Mute Command"
		// + "\n`IMPORTANT : MOVE ALL BACKEND BOT INTERACTIONS TO RAM - 2022?`"
		).queue();

	}

	@Override
	public String[] getCalls() {

		return new String[] { "todo" };
	}

	@Override
	public String getHelp() {

		return "A todo list created to keep track of stuff to do(duh)";
	}

	@Override
	public boolean isHidden() {

		return true;
	}

	@Override
	public Permission getRequiredPermission() {

		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
	}

	@Override
	public boolean isPremium() {
		return false;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

}
