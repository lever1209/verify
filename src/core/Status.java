package core;

import java.util.Random;

import net.dv8tion.jda.api.entities.Activity;

public class Status {

	private static int selection = 0;

	private static Activity[] actArray = {

			Activity.watching("for " + Global.Prefix + "help"),
			Activity.watching(
					Main.bot.getUserCache().asList().size() + " users in " + Main.bot.getGuilds().size() + " servers"),
			Activity.watching("my lead developer eat a watermelon whole"),
			// Activity.watching("for v!help"),
			// Activity.watching("for v!help"),
	};

	public static void shuffle() {

		int rand = new Random().nextInt(actArray.length);

		Main.bot.getPresence().setActivity(actArray[rand]);
		selection = rand;

	}

	public static void set(int interger) {

		Main.bot.getPresence().setActivity(actArray[interger]);
		selection = interger;

	}

	public static void increment() {
		if (!(selection + 1 > actArray.length)) {
			Main.bot.getPresence().setActivity(actArray[selection + 1]);
			System.out.println("inc");
		} else {
			Main.bot.getPresence().setActivity(actArray[0]);
			System.out.println("inc [0]");
		}
	}

}
