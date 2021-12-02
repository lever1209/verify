package core.commands.dev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FileManaging implements Command {

	public String getchPath = "";
	public String benchL = "";
	public String generalUseExit = "";

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		boolean isDev = devList.contains(event.getMember().getIdLong());
		if (isDev) {

			File[] pathnames;
			TextChannel channel = event.getChannel();
			Member member = event.getMember();
			try {
				if (args.isEmpty()) {
					channel.sendMessage(
							"This is the file utility for nopalmo, yes you are allowed to store files here, however, "
									+ "you cannot keep more then 10mb of stuff at the moment, you also cannot upload"
									+ " files larger then 2 mb in size..."
									+ "\nThis tool is mainly used for users uploading new code for me to add to the bot")
							.queue();

				}

				if (args.get(0).contains("getch")) {
					// if (Tools.devIdCheck(event.getMember().getUser(), event.getChannel(),
					// devList)) {
					getchPath = args.get(1);
					System.out.println(getchPath);
					// channel.sendMessage(getchPath).queue();
					try {
						try {
							if (!getchPath.startsWith("/")) {
								channel.sendMessage("Here's the file").addFile(new File(getchPath)).queue();

							} else {
								if (isDev) {
									channel.sendMessage("Here's the file").addFile(new File(getchPath)).queue();
								} else {
									channel.sendMessage(
											"Sorry, but you dont have permission to access this directory...").queue();
								}
							}
						} catch (net.dv8tion.jda.api.exceptions.InsufficientPermissionException e) {
							channel.sendMessage("Sorry, but i dont have permission to upload files...").queue();
							// }

							getchPath = "";
						}
					} catch (java.lang.IllegalArgumentException e) {
						channel.sendMessage("Sorry, but there is no file named \"" + getchPath + "\" here").queue();
					}
				}

				if (args.get(0).contains("list")) {
					channel.sendMessage("Retrieving user files...").queue();
					File filea = new File("UPLOADS/" + member.getUser().getId() + "/");
					pathnames = filea.listFiles();
					try {
						List<File> outa = Arrays.asList(pathnames);
						outa.forEach(PATH -> {
							generalUseExit += "\n" + PATH.getName();
						});

						if (outa != null) {
							channel.sendMessage("Here is your list of files~\n```" + generalUseExit + "\n```").queue();
						}
					} catch (java.lang.NullPointerException e) {
						channel.sendMessage("You have no files stored here...\n" + e).queue();
					}
					generalUseExit = "";
				}

				if (args.get(0).contains("remove")) {
					if (isDev) {
						getchPath = args.get(1);
						System.out.println(getchPath);
						channel.sendMessage(getchPath).queue();
						try {
							channel.sendMessage("Removing the file...").queue();

						} catch (net.dv8tion.jda.api.exceptions.InsufficientPermissionException e) {
							channel.sendMessage("Sorry, but i dont have permission to upload files...").queue();
						}
						getchPath = "";
					}
				}

				if (args.get(0).contains("up")) {
					// getchPath = args.get(1);
					try {
						event.getMessage().getAttachments().forEach(ATTACHMENT -> {

							if (!ATTACHMENT.getFileName().endsWith(".png") | !ATTACHMENT.getFileName().endsWith(".jpg")
									| !ATTACHMENT.getFileName().endsWith(".java")
									| !ATTACHMENT.getFileName().endsWith(".gif")
									| !ATTACHMENT.getFileName().endsWith(".mov")
									| !ATTACHMENT.getFileName().endsWith(".mp4")
									| !ATTACHMENT.getFileName().endsWith(".mp3")
									| !ATTACHMENT.getFileName().endsWith(".jar")) {
								channel.sendMessage("Sorry, but thats an unrecognized file format...").queue();
							} else {

								// String timeC = java.time.LocalDateTime.now().toString();
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
								Date date = new Date(System.currentTimeMillis());
								String timeC = formatter.format(date);
								File theDir = new File("UPLOADS/" + event.getAuthor().getId() + "/");
								if (!theDir.exists()) {
									theDir.mkdirs();
								}
								System.out.println(timeC);
								if (ATTACHMENT.getFileName().endsWith(".zip")) {
									ATTACHMENT.downloadToFile("UPLOADS/" + event.getAuthor().getId() + "/" + timeC + "."
											+ ATTACHMENT.getFileName());
								}
							}
						});

						channel.sendMessage(
								"File \"" + event.getMessage().getAttachments().get(0).getFileName() + "\" recieved...")
								.queue();

					} catch (java.lang.IllegalArgumentException | java.lang.IndexOutOfBoundsException e) {
						channel.sendMessage(e.getCause().toString()).queue();
					}
					// System.out.println(getchPath);
					// channel.sendMessage(getchPath).queue();
					getchPath = "";
				}

				if (args.get(0).contains("print")) {
					if (isDev) {
						getchPath = args.get(1);
						// System.out.println(getchPath);
						// channel.sendMessage(getchPath).queue();

						File foil = new File(getchPath);
						// System.out.println(foil);
						Scanner myReader;
						try {
							myReader = new Scanner(foil);
							while (myReader.hasNextLine()) {

								String data = myReader.nextLine();
								channel.sendMessage("Output:\n```" + data + "\n```").queue();
							}
						} catch (FileNotFoundException e) {

							e.printStackTrace();
						}
					}
				}

				if (args.get(0).contains("bench")) {
					if (isDev) {
						getchPath = args.get(1);
						channel.sendMessage("Preparing to load file...").queue();

						// if (getchPath.startsWith("\"")){//&getchPath.endsWith("\"")) {
						// String aids=getchPath.replace("\\A\\\"+\\Z\\\"", " ");//.split("\\s+");
						// System.out.println(aids+"CUNT");
						// getchPath=getchPath.replace("\"", "");
						// System.out.println(getchPath+": CUNT");
						// }
						// System.out.println(getchPath+": CUNT");
						// System.out.println(getchPath);
						// channel.sendMessage(getchPath).queue();

						File fioread = new File(getchPath);

						benchL = "";
						// System.out.println(foil);

						try {
							Scanner myReaderp;

							StopWatch stopWatch = new StopWatch();
							stopWatch.start();
							// Do something
							myReaderp = new Scanner(fioread);
							while (myReaderp.hasNextLine()) {

								String data = myReaderp.nextLine();
								System.out.println(data);
							}
							stopWatch.stop();
							myReaderp.close();
							System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
							channel.sendMessage("Time to read file \"" + getchPath + "\" is: "
									+ stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms\nFile is" + fioread.length() / 1024
									+ "KB").queue();
						} catch (FileNotFoundException | java.lang.NullPointerException e) {

							// e.printStackTrace();
							System.out.println(e.getCause());

							try {
								StopWatch stopWatch = new StopWatch();
								stopWatch.start();
								byte[] array = Files.readAllBytes(Paths.get(getchPath));
								System.out.println(array.toString());
								stopWatch.stop();
								System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
								channel.sendMessage("Time to read file \"" + getchPath + "\" is: "
										+ stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms\nFile is" + array.length / 1024
										+ "KB\nError caused a switch to get byte mode, may increase time...").queue();
								// Tools.fileIOWRITE(args.get(2), array.toString(), false);

								array = null;
								getchPath = null;

							} catch (IOException e2) {

								e2.printStackTrace();
							} // / 1024

						}
						getchPath = "";
					}

				}
			} catch (java.lang.IndexOutOfBoundsException e) {

			}
		}

	}

	@Override
	public String[] getCalls() {

		return new String[] { "file" };
	}

	@Override
	public String getHelp() {

		return null;
	}

	@Override
	public boolean isHidden() {

		return true;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public Permission getRequiredPermission() {

		return null;
	}

	@Override
	public String getName() {

		return getCalls()[0];
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + "<something>+";
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
