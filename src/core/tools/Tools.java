package core.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import core.Global;
import core.enums.Misc;
import core.listeners.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class Tools {

	public static void wrongUsage(TextChannel tc, Command c) {
		tc.sendMessage("Wrong Command Usage!\n" + c.getUsage()).queue();
	}

	public static List<Long> getIDFromMessage(String[] args) {
		List<Long> ids = new ArrayList<Long>();
		for (String i : args) {
			long targetID = Long.parseLong(i.replaceAll("[^0-9]", ""));
			ids.add(targetID);
		}
		return ids;
	}

	public static boolean fileIOWRITE(String path, String input, Misc mode) {
		try {

			if (mode == Misc.append) {
				FileWriter myWriter = new FileWriter(path, true);
				myWriter.append(input + "\n");
				myWriter.close();
			} else {
				FileWriter myWriter = new FileWriter(path);
				myWriter.write(input);
				myWriter.close();
			}

			// File pathe = new File(path);
			// System.out.println("Successfully wrote to the file.\n" +
			// pathe.getAbsolutePath());
			// pathe = null;
			return true;
		} catch (IOException e) {
			// System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	public static String fileIOREAD(String path) throws FileNotFoundException {

		File myObj = new File(path);
		// @SuppressWarnings("resource")
		StringBuilder sB = new StringBuilder();
		Scanner scanner = new Scanner(myObj);
		while (scanner.hasNextLine()) {
			sB.append(scanner.nextLine());
		}
		scanner.close();
		// System.out.println(myObj.getAbsolutePath().toString());
		return sB.toString();
		// }
		// myReader.close();

		// return null;
	}

	public static boolean PMDevIdCheck(PrivateMessageReceivedEvent event, ArrayList<Long> devList) {

		if (devList.toString().contains(event.getAuthor().getId())) {
			// System.out.println("pass: " + event.getAuthor().getId());
			return true;
		} else {
			// System.out.println("fail: " + event.getAuthor().getId());
			// fileIOWRITE("/DEBUGWARNINGS/"+member.getId()+".FAIL",
			// DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").toString());
			return false;
		}
	}

	public static String convertStreamToString(InputStream is, String format) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, format));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static void bigMessage(String messageContents, TextChannel channel) {

		// channel.sendMessage("Failed to send message...\nPackaging data and uploading
		// packet...").queue();
		String fileName = "bigMessage.txt";
		// Tools.fileIOWRITE(fileName, messageContents, Misc.overwrite);
		channel.sendFile(new ByteArrayInputStream(messageContents.getBytes()), fileName).queue();
		// System.out.println("Sent big message...");

	}

	public static void bigMessage(String messageContents, TextChannel channel, String extension) {

		// channel.sendMessage("Failed to send message...\nPackaging data and uploading
		// packet...").queue();
		String fileName = "bigMessage." + extension;
		// Tools.fileIOWRITE(fileName, messageContents, Misc.overwrite);
		channel.sendFile(new ByteArrayInputStream(messageContents.getBytes()), fileName).queue();
		// System.out.println("Sent big message...");

	}

	public static final List<Class<?>> getClassesInPackage(String packageName) {
		String path = packageName.replaceAll("\\.", File.separator);
		List<Class<?>> classes = new ArrayList<>();
		String[] classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator"));

		String name;
		for (String classpathEntry : classPathEntries) {
			if (classpathEntry.endsWith(".jar")) {
				File jar = new File(classpathEntry);
				try {
					JarInputStream is = new JarInputStream(new FileInputStream(jar));
					JarEntry entry;
					while ((entry = is.getNextJarEntry()) != null) {
						name = entry.getName();
						if (name.endsWith(".class")) {
							if (name.contains(path) && name.endsWith(".class")) {
								String classPath = name.substring(0, entry.getName().length() - 6);
								classPath = classPath.replaceAll("[\\|/]", ".");
								classes.add(Class.forName(classPath));
							}
						}
					}
					is.close();
				} catch (Exception ex) {
					ex.getStackTrace();
				}
			} else {
				try {
					File base = new File(classpathEntry + File.separatorChar + path);
					for (File file : base.listFiles()) {
						name = file.getName();
						if (name.endsWith(".class")) {
							name = name.substring(0, name.length() - 6);
							classes.add(Class.forName(packageName + "." + name));
						}
					}
				} catch (Exception ex) {
					ex.getStackTrace();
				}
			}
		}

		return classes;
	}

	public static boolean createDirs(String path) throws IOException {

		Path fileP = Paths.get(path);
		if (!Files.exists(fileP)) {
			Files.createDirectory(fileP);
			return true;
		} else {
			return false;
		}
	}

	public static Long convertToLong(Object o) {
		String stringToConvert = String.valueOf(o);
		Long convertedLong = Long.parseLong(stringToConvert);
		return convertedLong;
	}

	public static String stringRepsMessage(String input, GuildMessageReceivedEvent event) {
		return input.trim().strip()

				// FOLDERS .replaceAll("(?i)foo", "");

				.replace("$/", System.getProperty("user.dir") + "/").replace("$root", Global.ROOT)
				.replace("$server", Global.ROOT + "GUILD/" + event.getGuild().getId() + "/")
				.replace("$guild", Global.ROOT + "GUILD/" + event.getGuild().getId() + "/")
				.replace("$user", Global.ROOT + "USER/" + event.getMember().getId() + "/")
				.replace("$member", Global.ROOT + "USER/" + event.getMember().getId() + "/")
				.replace("$channel", Global.ROOT + "GUILD/CHANNEL/" + event.getChannel().getId() + "/")

				// SNOWFLAKES

				.replace("-server", event.getGuild().getName()).replace("-guild", event.getGuild().getName())
				.replace("-member", event.getMember().getEffectiveName())
				.replace("-user", event.getMember().getUser().getName())
				.replace("-channel", event.getChannel().getName()).replace("-ping", event.getMember().getAsMention());
	}

	public static String stringRepsJoin(String input, GuildMemberJoinEvent event) {
		return input.trim().strip()

				// FOLDERS

				.replace("$root", Global.ROOT)
				.replace("$server", Global.ROOT + "GUILD/" + event.getGuild().getId() + "/")
				.replace("$guild", Global.ROOT + "GUILD/" + event.getGuild().getId() + "/")
				.replace("$user", Global.ROOT + "USER/" + event.getMember().getId() + "/")
				.replace("$member", Global.ROOT + "USER/" + event.getMember().getId() + "/")

				// SNOWFLAKES

				.replace("-server", event.getGuild().getName()).replace("-guild", event.getGuild().getName())
				.replace("-member", event.getMember().getEffectiveName())
				.replace("-user", event.getMember().getUser().getName())
				.replace("-ping", event.getMember().getAsMention());
	}

}
