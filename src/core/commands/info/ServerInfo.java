package core.commands.info;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ServerInfo implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		// if(args.size() == 0) {
		// event.getChannel().sendMessage("YOU ARE A FURRY!");
		//
		EmbedBuilder embed = new EmbedBuilder();
		TextChannel channel = event.getChannel();
		// User user = event.getAuthor();
		// Member member = event.getMember();
		Guild guild = event.getGuild();

		StringBuilder main = new StringBuilder();
		main.append("Guild ID: " + guild.getId() + "\n");
		main.append("Owner: " + guild.getMemberById(guild.getOwnerIdLong()).getUser().getAsTag() + "\n");

		if (guild.getDescription() != null) {
			main.append("Description: " + guild.getDescription() + "\n");
		}

		main.append("Boost count: " + guild.getBoostCount() + "\n");

		if (guild.getVanityCode() != null) {
			main.append("Vanity code: " + guild.getVanityCode() + "\n");
		}
		main.append("Boost tier: " + guild.getBoostTier() + "\n");
		main.append("Emote count: " + guild.getEmotes().size() + "\n");

		main.append("Role count: " + guild.getRoles().size() + "\n");
		main.append("Member count: " + guild.getMembers().size() + "\n");
		/*
		 * try {
		 * if (event.getMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
		 * List<Invite> invites = guild.retrieveInvites().complete();
		 * if (!invites.isEmpty()) {
		 * main.append("Invites:\n" + invites + "\n");
		 * }
		 * }
		 * 
		 * } catch (Exception e) {
		 * }
		 */
		// main.append("Role count: " + guild.().size() + "\n");
		embed.setAuthor(guild.getName()).setColor(Global.embedColor)
				.setFooter(event.getMember().getUser().getName(), event.getMember().getUser().getEffectiveAvatarUrl())
				.setTimestamp(Instant.now()).setTitle("Server Information. . .")
				.addField("Main info", main.toString(), true).setThumbnail(guild.getIconUrl());
		channel.sendMessage(embed.build()).queue();

	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getCalls() {
		return new String[] { "serverinfo", "server", "guild", "si" };
	}

	@Override
	public String getHelp() {
		return "Gives assorted info";
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
	public String getUsage() {
		return Global.Prefix + getCalls()[0];
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
