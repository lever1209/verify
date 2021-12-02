package core.commands.dev;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class EmbedTest implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		EmbedBuilder embed = new EmbedBuilder();

		embed.addField("Field Name", "String value of field", true);
		StringBuilder desc = embed.getDescriptionBuilder();
		desc.append("This is the description");
		embed.appendDescription("{text that has been appended to the description}");
		embed.setAuthor("Embed author name", null, "attachment://shiet.png");
		embed.setColor(Global.embedColor);
		embed.setFooter("Footer text", "attachment://coolCat.png");
		embed.setImage("attachment://wumpus.png");
		embed.setThumbnail("attachment://pfp.png");
		embed.setTimestamp(Instant.now());
		embed.setTitle("Title that can be set with a url for a hyperlink", "https://www.example.com/");
		Field field = new Field("Manual field name", "Manual field value", true, true);
		embed.addField(field);

		event.getChannel().sendMessage(embed.build()).addFile(new File("ASSETS/IMAGE/coolCat.png"), "coolCat.png")
				.addFile(new File("ASSETS/IMAGE/pfp.png"), "pfp.png")
				.addFile(new File("ASSETS/IMAGE/wumpus.jpg"), "wumpus.png")
				.addFile(new File("ASSETS/IMAGE/shiet.png"), "shiet.png").queue();
		if (args.size() == 1) {
			if (args.get(0).contentEquals("-j") | args.get(0).contentEquals("-json")) {

				event.getChannel().sendMessage("```" + embed.build().toData().toString() + "```").queue();

			}
		}
	}

	@Override
	public String[] getCalls() {
		return new String[] { "embedtest", "embed", "em" };
	}

	@Override
	public String getHelp() {
		return "Posts a detailed test with embeds";
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public Permission getRequiredPermission() {
		return Permission.MESSAGE_READ;
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
		return Global.Prefix + getCalls()[0] + " [-json | -j]";
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
