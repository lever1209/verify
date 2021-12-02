package core.commands.info;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.Main;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.RestAction;

public class WhoIs implements Command {

	public RestAction<Message> sendMessage(User user, TextChannel context, String content) {
		return user.openPrivateChannel() // RestAction<PrivateChannel>
				.flatMap((channel) -> channel.sendMessage(content)) // RestAction<Message>
				.onErrorFlatMap(ErrorResponse.CANNOT_SEND_TO_USER::test,
						(error) -> context.sendMessage("Cannot send direct message to " + user.getAsMention()));
		// RestAction<Message> (must be the same as above)
	}

	public boolean perms = false;
	private List<Long> failedUsers = new ArrayList<Long>();

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		List<String> newArgs = new ArrayList<String>();

		for (String i : args) {
			if (i.toLowerCase().contentEquals("-perms")) {
				perms = true;
			}
		}

		for (int i = 0; i < args.size(); i++) {
			if (args.get(i).replaceAll("[^0-9]", "").isBlank()) {
			} else {
				newArgs.add(args.get(i));
			}
		}

		if (newArgs.size() < 1) {
			newArgs.add(event.getMember().getId());
		}

		net.dv8tion.jda.api.exceptions.ErrorResponseException ex = null;

		for (long i : Tools.getIDFromMessage(newArgs.toArray(new String[0]))) {

			try {
				User user = Main.bot.retrieveUserById(i).complete();

				boolean isMember = false;

				EmbedBuilder embed = new EmbedBuilder();
				StringBuilder sB = embed.getDescriptionBuilder();
				int color = 0;
				StringBuilder roles = new StringBuilder();
				Member member = null;
				if (event.getGuild().isMember(user)) {
					isMember = true;
					member = event.getGuild().getMember(user);
					color = event.getGuild().getMember(user).getColorRaw();
					embed.setColor(color);
				} else {
					isMember = false;
					embed.setColor(Color.cyan);
					embed.setAuthor(user.getAsTag());
				}

				if (isMember) {
					if (member.getRoles().size() <= 10) {
						for (Role j : member.getRoles()) {
							roles.append(j.getAsMention() + " ");
						}
					} else {
						roles.append("Too many to list .-.");
					}
					embed.setAuthor(user.getAsTag() + " : " + member.getId());
					if (member.getNickname() != null) {
						sB.append("\nNick : " + member.getNickname());
					}
					sB.append("\nRoles : " + roles + "\n Color : " + member.getColorRaw());

					sB.append("\nClients : " + member.getActiveClients());

					sB.append("\nStatus : " + member.getOnlineStatus());
					if (perms) {
						sB.append("\nPerms : ");
						for (Permission j : member.getPermissions()) {
							sB.append(j.getName());
						}

					}
					if (member.getTimeBoosted() != null) {
						sB.append("\nBoost time : " + member.getTimeBoosted());
					}
					String joinedDate = member.getTimeJoined().getYear() + "-"
							+ member.getTimeJoined().getMonth().getValue() + "-"
							+ member.getTimeJoined().getDayOfMonth() + "-" + member.getTimeJoined().getHour() + "-"
							+ member.getTimeJoined().getMinute() + "-" + member.getTimeJoined().getSecond();
					sB.append("\nTime joined : " + joinedDate);
					sB.append("\nIs owner : " + member.isOwner());
				}
				String createdDate = user.getTimeCreated().getYear() + "-" + user.getTimeCreated().getMonth().getValue()
						+ "-" + user.getTimeCreated().getDayOfMonth() + "-" + user.getTimeCreated().getHour() + "-"
						+ user.getTimeCreated().getMinute() + "-" + user.getTimeCreated().getSecond();
				sB.append("\nTime created : " + createdDate);
				sB.append("\nFlags : " + user.getFlags());
				sB.append("\nIs bot : " + user.isBot());
				sB.append("\nMention : " + user.getAsMention());
				embed.setThumbnail(user.getAvatarUrl());

				if (devList.contains(user.getIdLong())) {
					embed.setFooter("This user is a member of my dev team!", "attachment://coolCat.png");
					if (embed.isValidLength()) {
						event.getChannel().sendMessage(embed.build())
								.addFile(new File("ASSETS/IMAGE/coolCat.png"), "coolCat.png").queue();
					} else {
						event.getChannel().sendMessage(
								"Sorry, but the embed is too large to send. . .\nplease report this to the support server")
								.queue();
					}
				} else {
					if (embed.isValidLength()) {
						event.getChannel().sendMessage(embed.build()).queue();
					} else {

						event.getChannel().sendMessage(
								"Sorry, but the embed is too large to send. . .\nplease report this to the support server")
								.queue();
					}
				}

			} catch (Exception e) {
				failedUsers.add(i);
				ex = (ErrorResponseException) e;
			}

		}

		// System.out.println("reached user post " + failedUsers);

		StringBuilder failedUsersString = new StringBuilder();

		for (long i : failedUsers) {
			failedUsersString.append(i + "\n");
		}
		if (!failedUsersString.toString().isBlank() && failedUsers.size() > 1) {
			if (ex != null) {
				event.getChannel().sendMessage("```yaml\n" + ex.toString() + "```").queue();
			}
			if (failedUsersString.length() >= 1000) {
				// event.getChannel().sendMessage("There are too many to list in a message, so
				// here is a file. . .").addFile(new File(), "failed_ids.txt").queue();
				event.getChannel().sendMessage("There are too many failed ids to list in the message").queue();
			} else {
				event.getChannel().sendMessage("The following users dont exist. . .\n" + failedUsersString).queue();
			}
		}
		failedUsers.clear();
	}

	@Override
	public String[] getCalls() {

		return new String[] { "whois", "user" };
	}

	@Override
	public String getHelp() {

		return "Gives info on a user";
	}

	@Override
	public boolean isHidden() {

		return false;
	}

	@Override
	public Permission getRequiredPermission() {

		return Permission.MESSAGE_WRITE;
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
	public String getUsage() {
		return Global.Prefix + getCalls()[0] + " [User id/ping]+ [-perms]";
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
