package core.autoActions.actions;

import core.Main;
import core.autoActions.Action;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

public class ToggleRole extends Action {

	@Override
	public String type() {
		return "togglerole";
	}

	@Override
	public void start() {

		Guild guild = Main.bot.getGuildById(guildID);
		Role role = Main.bot.getRoleById(roleID);
		if (guild.getMemberById(userID).getRoles().contains(role)) {
			guild.removeRoleFromMember(userID, role).queue();
		} else {
			guild.addRoleToMember(userID, role).queue();
		}
	}

	@Override
	public String[] keys() {
		return new String[] { "guildID", "roleID" };
	}

}
