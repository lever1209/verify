package core.commands.extra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.IO.ObjectIO;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PermissionsFileManager implements Command {

	private boolean isEnabled = true;

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		if (isEnabled) {
			if (args.get(0).contentEquals("obj")) {
				StringBuilder sB = new StringBuilder();
				for (String i : args.subList(1, args.size())) {
					sB.append(i);
				}
				System.out.println(sB);
				String out = Tools.stringRepsMessage(String.valueOf(sB), event);
				System.out.println(out);
				Object obj = ObjectIO.readObject(out);
				event.getChannel().sendMessage(obj.toString()).queue();
			} else if (args.get(0).contentEquals("ls")) {
				String path = "";

				if (args.subList(1, args.size()).isEmpty()) {
					path = System.getProperty("user.dir");
				} else {
					path = args.get(1);
				}
				String pathFiltered = Tools.stringRepsMessage(path, event);
				File[] cDir = new File(pathFiltered).listFiles();

				StringBuilder sB = new StringBuilder();

				for (File i : cDir) {

					String directory = "-", read = "-", write = "-", executible = "-";

					if (i.isDirectory()) {
						directory = "d";
					}
					if (i.canRead()) {
						read = "r";
					}
					if (i.canWrite()) {
						write = "w";
					}
					if (i.canExecute()) {
						executible = "x";
					}
					sB.append(directory + read + write + executible + " | " + i.getName() + "\n");
				}
				if (!sB.toString().isEmpty()) {
					event.getChannel().sendMessage("```\n" + sB + "```").queue();
				} else {
					event.getChannel().sendMessage("No files here").queue();
				}

			} else if (args.get(0).contentEquals("getch")) {

				StringBuilder sB = new StringBuilder();
				if (args.subList(1, args.size()).isEmpty()) {
					sB.append(System.getProperty("user.dir"));
				} else {
					for (String i : args.subList(1, args.size())) {
						sB.append(i);
					}
				}
				String path = Tools.stringRepsMessage(sB.toString(), event);
				File file = new File(path.toString());
				if (file.exists()) {
					if (file.canRead()) {
						if (!file.isDirectory()) {
							if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_ATTACH_FILES)) {

								if (devList.contains(event.getMember().getIdLong())) {
									event.getChannel().sendMessage("Found file at " + path.toString()).addFile(file)
											.queue();
								} else {
									event.getChannel().sendMessage("You do not have permission to access this file")
											.queue();
								}

							} else {
								event.getChannel().sendMessage(path + " I cannot upload files").queue();
							}
						} else {
							event.getChannel().sendMessage(path + " Is a directory").queue();
						}
					} else {
						event.getChannel().sendMessage(path + " Cannot be read").queue();
					}
				} else {
					event.getChannel().sendMessage(path + " Does not exist").queue();
				}

			} else if (args.get(0).contentEquals("txt")) {

				StringBuilder sB = new StringBuilder();
				if (args.subList(1, args.size()).isEmpty()) {
					sB.append(System.getProperty("user.dir"));
				} else {
					for (String i : args.subList(1, args.size())) {
						sB.append(i);
					}
				}
				String path = Tools.stringRepsMessage(sB.toString(), event);
				File file = new File(path.toString());
				if (file.exists()) {
					if (file.canRead()) {
						if (!file.isDirectory()) {
							if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_ATTACH_FILES)) {

								if (devList.contains(event.getMember().getIdLong())) {

									event.getChannel().sendMessage("Here is your file :")
											.addFile(file, file.getName() + ".txt").queue();

								} else {
									event.getChannel().sendMessage("You do not have permission to access this file")
											.queue();
								}

							} else {
								event.getChannel().sendMessage(path + " I cannot upload files").queue();
							}
						} else {
							event.getChannel().sendMessage(path + " Is a directory").queue();
						}
					} else {
						event.getChannel().sendMessage(path + " Cannot be read").queue();
					}
				} else {
					event.getChannel().sendMessage(path + " Does not exist").queue();
				}
			}

		}
	}

	@Override
	public String[] getCalls() {

		return new String[] { "file" };
	}

	@Override
	public String getHelp() {

		return "Allows users to manage their own data\nModes:\ntxt : posts the desired file in a text format\nls : lists all files in the directory\ngetch : posts the file";
	}

	@Override
	public boolean isHidden() {

		return true;
	}

	@Override
	public Permission getRequiredPermission() {

		return Permission.MESSAGE_ATTACH_FILES;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.Extra;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " <Mode> <Path>";
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
