package core.commands.info;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Credits implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		EmbedBuilder eB = new EmbedBuilder();

		User curse = Main.bot.getUserById(342047193294831619L);
		// User fox = Main.bot.getUserById(0L);

		eB.setTitle("Credits:");
		eB.addField("Developers:", curse.getAsMention(), true);
		eB.addField("Host:", "<@563808552288780322> with https://fateslist.xyz/\nThanks guys!!", true);
		// eB.addField("Fox:", fox.getAsMention(), true);
		eB.addField("Noteworthy mentions:",
				"The folks over at stack overflow for percentage string comparison"
						+ "\nThe JDA discord server for dealing with my noobish questions"
						+ "\nAnd all of you, for using the bot",
				false);
		// eB.addField("Host:", "", true);
		// eB.addField("Host:", "", true);
		// eB.addField("Host:", "", true);

		eB.addField("Libraries used:",
				"JDA-4.30_277\nJSON-simple-1.1.1\nApache-Commons-Lang3-3.12\nlavaplayer-1.3.78\noshi-core-5.8.0(soon)",
				true);
		eB.setThumbnail("attachment://verify.gif");
		eB.setColor(Global.embedColor);

		event.getChannel().sendMessage(eB.build())
				.addFile(new File("ASSETS/IMAGE/verifyRainbowPFP-smol.gif"), "verify.gif").queue();

	}

	@Override
	public String[] getCalls() {
		return new String[] { "credits" };
	}

	@Override
	public String getHelp() {
		return "Gives information about this bots creators. . .";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Info;
	}

	@Override
	public String getName() {
		return getCalls()[0];
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
