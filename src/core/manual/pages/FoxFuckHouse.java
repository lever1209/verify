package core.manual.pages;

import net.dv8tion.jda.api.EmbedBuilder;

public class FoxFuckHouse implements Page {

	@Override
	public String[] getName() {
		return new String[] { "Fox" };
	}

	@Override
	public int getID() {
		return 3;
	}

	@Override
	public Object getContents() {

		EmbedBuilder eB = new EmbedBuilder();

		eB.setTitle("uwu owo", "https://example.com");

		eB.addField("Field Name", "String Value", true);

		return eB;
	}

}
