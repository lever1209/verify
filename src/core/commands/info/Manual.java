package core.commands.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Global;
import core.Main;
import core.enums.BotMode;
import core.enums.HelpPage;
import core.listeners.Command;
import core.manual.pages.Advanced;
import core.manual.pages.Basics;
import core.manual.pages.FoxFuckHouse;
import core.manual.pages.Page;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Manual implements Command {

	// private static String[] list = { "Test of manual", "page two", "page three"
	// };

	private final Map<Integer, Page> pages = new HashMap<>();

	public Manual() {
		addPage(new Basics());
		addPage(new Advanced());
		addPage(new FoxFuckHouse());
	}

	private void addPage(Page p) {
		if (!pages.containsKey(p.getID())) {
			pages.put(p.getID(), p);
		}
	}

	public Collection<Page> getPages() {
		return pages.values();
	}

	public Page getPage(int pageNumber) {
		return pages.get(pageNumber);
	}

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		TextChannel channel = event.getChannel();

		if (args.size() == 0) {

			StringBuilder sB = new StringBuilder();

			for (Page i : getPages()) {

				sB.append(i.getID() + " : " + i.getName()[0] + "\n");

			}

			channel.sendMessage("List of pages: \n" + sB).queue();

		} else if (args.size() == 1) {
			try {

				Object contents = getPage(Integer.parseInt(args.get(0))).getContents();

				if (contents instanceof String) {
					event.getChannel()
							.sendMessage("Manual page " + (Integer.parseInt(args.get(0))) + ", Named "
									+ getPage(Integer.parseInt(args.get(0))).getName() + "\n\n" + ((String) contents))
							.queue();
				} else if (contents instanceof EmbedBuilder) {
					event.getChannel().sendMessage(((EmbedBuilder) contents).build()).queue();
				} else {
					event.getChannel().sendMessage("Result type is unknown. . .").queue();
				}

			} catch (NumberFormatException e) {

			}
			if (args.get(0).contentEquals("-l") | args.get(0).contentEquals("-list")) {
				channel.sendMessage("").queue();
			} else if (args.get(0).contentEquals("-f") | args.get(0).contentEquals("-flat")) {

				for (Page i : getPages()) {
					Object contents = i.getContents();
					if (contents != null) {
						if (contents instanceof String) {
							event.getChannel().sendMessage(i.getName() + " contents:\n" + ((String) contents)).queue();
						} else if (contents instanceof EmbedBuilder) {
							event.getChannel().sendMessage(i.getName() + " contents:\n"
									+ ((EmbedBuilder) contents).build().toData().toString()).queue();
						} else {
							event.getChannel().sendMessage("Result type is unknown. . .").queue();
						}
					} else {
						event.getChannel().sendMessage("Sorry, but no results were found. . .").queue();
					}
				}
				return;
			}

		} else if (args.size() >= 2) {

			if (args.get(0).contentEquals("-s") | args.get(0).contentEquals("-search")) {

				StringBuilder sB = new StringBuilder();
				if (Main.mode == BotMode.DEV)
					sB.append("Here are the pages that matched your search, with a give of 0.75\n");
				else
					sB.append("Here are the pages that matched your search\n");
				for (Page i : getPages()) {
					for (String j : i.getName()) {
						double result = similarity(j, args.get(1));
						if (Main.mode == BotMode.DEV) {
							if (result >= 0.75) {
								sB.append(i.getID() + " " + i.getName()[0] + " : " + result + "\n");
							} else if (j.toLowerCase().contains(args.get(1).toLowerCase())) {
								sB.append(i.getID() + " " + i.getName()[0] + " : Container\n");
							} else {
								// sB.append(i.getName() + " fail " + result+"\n");
							}
						} else {
							if (result >= 0.75) {
								sB.append(i.getID() + " " + i.getName()[0]);
							} else if (j.toLowerCase().contains(args.get(1).toLowerCase())) {
								sB.append(i.getID() + " " + i.getName()[0]);
							} else {
								// sB.append(i.getName() + " fail " + result+"\n");
							}
						}
					}
				}

				if (!sB.toString().contentEquals("Here are the results of your search, with a give of 0.75\n")) {
					event.getChannel().sendMessage(sB).queue();
				} else {
					event.getChannel().sendMessage("Sorry, but no results were found. . .").queue();
				}

			}
		}

	}

	@Override
	public String[] getCalls() {
		return new String[] { "manual", "howto", "man", "how", "tutorial" };
	}

	@Override
	public String getHelp() {
		return "An interactive command that will explain everything about verify";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getName()
				+ " [page number | [-h | -help] | [-f | -flat] | [[-s | -search] <search terms>]]";
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

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 */
	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }
		/*
		 * // If you have Apache Commons Text, you can use it to calculate the edit
		 * distance:
		 * LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
		 * return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double)
		 * longerLength;
		 */
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}
