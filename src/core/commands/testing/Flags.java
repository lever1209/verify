package core.commands.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Flags implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		List<String> flags = new ArrayList<String>();
		List<String> newArgs = new ArrayList<String>();
		List<String> failedFlags = new ArrayList<String>();
		// List<String> successFlags = new ArrayList<String>();

		Map<String, Integer> flagMap = new HashMap<>();

		flagMap.put("w", 1);
		flagMap.put("r", 0);
		flagMap.put("--owo", 0);
		flagMap.put("--uwu", 1);

		StringBuilder sB = new StringBuilder();
		int cArg = 0;
		for (String i : args) {
			cArg++;
			System.out.println(cArg);
			if (i.startsWith("--")) {
				System.out.println("-- : " + i);
				if (flagMap.containsKey(i)) {
					System.out.println("contains -- : " + i);
					flags.add(i);
					for (int j = cArg; j <= flagMap.get(i); j++) {
						System.out.println("flagArgFor -- : " + j);
						sB.append(i + " : " + args.get(j) + "\n");
					}
				} else {
					System.out.println("Failed -- : " + i);
					failedFlags.add(i);
				}

			} else if (i.startsWith("-")) {
				System.out.println("- : " + i);
				for (char j : i.toCharArray()) {
					System.out.println("for - : " + i);
					if (j != '-') {
						if (flagMap.containsKey(String.valueOf(j))) {
							System.out.println("contains - : " + j);
							flags.add(String.valueOf(j));
							for (int k = cArg; k <= flagMap.get(String.valueOf(j)); k++) {
								System.out.println("flagArgFor - : " + args.get(k));
								sB.append(j + " : " + args.get(k) + "\n");
							}
						} else {
							System.out.println("failed - : " + j);
							failedFlags.add(String.valueOf(j));
						}
					}

				}
			} else {
				System.out.println("newArg : " + i);
				newArgs.add(i);
			}

		}
		System.out.println("END");
		event.getChannel()
				.sendMessage("Registered Flags: " + flagMap.keySet() + "\nSuccessful Flags: " + flags.toString()
						+ "\nFailed Flags: " + failedFlags.toString() + "\nNew Arguments: " + newArgs.toString()
						+ "\nStringBuilder:\n\n" + sB.toString())
				.queue();

	}

	@Override
	public String[] getCalls() {
		// TODO Auto-generated method stub
		return new String[] { "flags", "flag", "f" };
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "A testing command for the flagging engine";
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return Global.Prefix + getName() + " <flags>";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getCalls()[0];
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPremium() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Permission getRequiredPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelpPage getPage() {
		// TODO Auto-generated method stub
		return HelpPage.TESTING;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}
