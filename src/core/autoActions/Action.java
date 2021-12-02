package core.autoActions;

import java.util.Random;

import org.json.simple.JSONObject;

import core.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;

public abstract class Action {

	public abstract String type();

	public abstract void start();

	Action build() {
		return this;
	}

	public abstract String[] keys();

	protected long guildID, messageID, userID, channelID, actionID, time, roleID, emoteID;
	protected String string, type;

	public Action setTime(long offset) {
		this.time = System.currentTimeMillis() + offset;
		return this;
	}

	public long getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public long getChannelID() {
		return channelID;
	}

	public Action setGuildID(long id) {
		this.guildID = id;
		return this;
	}

	public Action setString(String string) {
		this.string = string;
		return this;
	}

	public Action setMessageID(long id) {
		this.messageID = id;
		return this;
	}

	public Action setUserID(long id) {
		this.userID = id;
		return this;
	}

	public Action setLongID(long id) {
		this.roleID = id;
		return this;
	}

	public Action setChannelID(long id) {
		this.channelID = id;
		return this;
	}

	public Action setActionID() {
		for (Action i : Main.actionList) {
			if (i.getActionID() == this.getActionID()) {
				this.actionID = new Random().nextLong();
			}
		}

		if (this.actionID == 0) {
			this.actionID = new Random().nextLong();
		}

		return this;
	}

	public Action setActionID(long id) {
		this.actionID = id;
		return this;
	}

	public long getActionID() {
		return this.actionID;
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {

		JSONObject jobj = new JSONObject();

		jobj.put("channelID", this.channelID);
		jobj.put("guildID", this.guildID);
		jobj.put("messageID", this.messageID);
		jobj.put("userID", this.userID);
		jobj.put("string", this.string);
		jobj.put("time", this.time);
		jobj.put("actionID", this.actionID);
		jobj.put("type", this.type);
		jobj.put("roleID", this.roleID);
		jobj.put("emoteID", this.emoteID);

		return jobj.toString();
	}

	public Action setType(String type) {
		this.type = type;
		return this;
	}

	public Action setRole(long roleID) {
		this.roleID = roleID;
		return this;
	}

	protected JDA bot;
	protected Role role;
	protected Emote emote;
	// Emoji emoji = bot.getEmoji

	public Action init() {
		this.bot = Main.bot;
		this.role = bot.getRoleById(roleID);
		this.emote = bot.getEmoteById(emoteID);
		return this;
	}

}
