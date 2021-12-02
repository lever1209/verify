package core.manual.pages;

import net.dv8tion.jda.api.EmbedBuilder;

public class Advanced implements Page {

	@Override
	public String[] getName() {
		return new String[] { "Advanced" };
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public Object getContents() {
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(getName()[0]).addField("Backend",
				"Every 5 days, the bot will cycle all registered users and sync all available users to disk, this process\n"
						+ "will remove users that have left the server, and add new ones if they were not added at the join date(see manual page newcomers)\n"
						+ "",
				false)

		;

		return embed;
	}

}
