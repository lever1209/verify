package core.tools;

import java.util.EnumSet;

import net.dv8tion.jda.api.Permission;

public class Permissions {

	public static boolean hierarchy(EnumSet<Permission> user, Permission level) {

		short exitVal = 99;
		for (Permission i : user) {
			short userPermLevel = permsOrder(i);
			if (userPermLevel > exitVal) {
				exitVal = userPermLevel;
			}

		}
		return (exitVal < permsOrder(level));
	}

	private static short permsOrder(Permission perm) {
		short level = 99;
		switch (perm) {
		case ADMINISTRATOR:
			level = 0;
			break;
		case MANAGE_SERVER:
			level = 1;
			break; // "MANAGE_SERVER";
		case MANAGE_ROLES:
			level = 2;
			break; // "MANAGE_ROLES";
		case VIEW_GUILD_INSIGHTS:
			level = 3;
			break; // "VIEW_GUILD_INSIGHTS";
		case MANAGE_WEBHOOKS:
			level = 4;
			break; // "MANAGE_WEBHOOKS";
		case MANAGE_PERMISSIONS:
			level = 5;
			break; // "MANAGE_PERMISSIONS";
		case MANAGE_CHANNEL:
			level = 6;
			break; // "MANAGE_CHANNEL";
		case MANAGE_EMOTES:
			level = 7;
			break; // "MANAGE_EMOTES";
		case BAN_MEMBERS:
			level = 8;
			break; // "BAN_MEMBERS";
		case KICK_MEMBERS:
			level = 9;
			break; // "KICK_MEMBERS";
		case MESSAGE_MANAGE:
			level = 10;
			break; // "MESSAGE_MANAGE";
		case VOICE_DEAF_OTHERS:
			level = 10;
			break; // "VOICE_DEAF_OTHERS";
		case VOICE_MOVE_OTHERS:
			level = 10;
			break; // "VOICE_MOVE_OTHERS";
		case VOICE_MUTE_OTHERS:
			level = 10;
			break; // "VOICE_MUTE_OTHERS";
		case NICKNAME_MANAGE:
			level = 10;
			break; // "NICKNAME_MANAGE";
		case MESSAGE_MENTION_EVERYONE:
			level = 11;
			break; // "MESSAGE_MENTION_EVERYONE";
		case VIEW_AUDIT_LOGS:
			level = 12;
			break; // "VIEW_AUDIT_LOGS";
		case NICKNAME_CHANGE:
			level = 13;
			break; // "NICKNAME_CHANGE";
		case CREATE_INSTANT_INVITE:
			level = 14;
			break; // "CREATE_INSTANT_INVITE";
		case PRIORITY_SPEAKER:
			level = 15;
			break; // "PRIORITY_SPEAKER";
		case MESSAGE_TTS:
			level = 16;
			break; // "MESSAGE_TTS";
		case MESSAGE_EMBED_LINKS:
			level = 17;
			break; // "MESSAGE_EMBED_LINKS";
		case VOICE_STREAM:
			level = 17;
			break; // "VOICE_STREAM";
		case MESSAGE_ATTACH_FILES:
			level = 18;
			break; // "MESSAGE_ATTACH_FILES";
		case MESSAGE_EXT_EMOJI:
			level = 19;
			break; // "MESSAGE_EXT_EMOJI";
		case MESSAGE_ADD_REACTION:
			level = 20;
			break; // "MESSAGE_ADD_REACTION";
		case VIEW_CHANNEL:
			level = 21;
			break; // "VIEW_CHANNEL";
		case MESSAGE_HISTORY:
			level = 22;
			break; // "MESSAGE_HISTORY";
		case MESSAGE_READ:
			level = 23;
			break; // "MESSAGE_READ";
		case MESSAGE_WRITE:
			level = 23;
			break; // "MESSAGE_WRITE";
		case VOICE_CONNECT:
			level = 23;
			break; // "VOICE_CONNECT";
		case VOICE_SPEAK:
			level = 23;
			break; // "VOICE_SPEAK";
		case VOICE_USE_VAD:
			level = 99; // FIXME VAD IS UNKNOWN
			break; // "VOICE_USE_VAD";
		case UNKNOWN:
			level = 99;
			break; // "UNKNOWN - you should not be getting this, report it to the devs via the
		// support server";
		default:
			level = 99;
			break; // "null - you should not be getting this, report it to the devs via the support
		// server";
		}
		return level;
	}

	public static String permissionToString(Permission perm) {

		switch (perm) {
		case ADMINISTRATOR:
			return "ADMINISTRATOR";
		case BAN_MEMBERS:
			return "BAN_MEMBERS";
		case CREATE_INSTANT_INVITE:
			return "CREATE_INSTANT_INVITE";
		case KICK_MEMBERS:
			return "KICK_MEMBERS";
		case MANAGE_CHANNEL:
			return "MANAGE_CHANNEL";
		case MANAGE_EMOTES:
			return "MANAGE_EMOTES";
		case MANAGE_PERMISSIONS:
			return "MANAGE_PERMISSIONS";
		case MANAGE_ROLES:
			return "MANAGE_ROLES";
		case MANAGE_SERVER:
			return "MANAGE_SERVER";
		case MANAGE_WEBHOOKS:
			return "MANAGE_WEBHOOKS";
		case MESSAGE_ADD_REACTION:
			return "MESSAGE_ADD_REACTION";
		case MESSAGE_ATTACH_FILES:
			return "MESSAGE_ATTACH_FILES";
		case MESSAGE_EMBED_LINKS:
			return "MESSAGE_EMBED_LINKS";
		case MESSAGE_EXT_EMOJI:
			return "MESSAGE_EXT_EMOJI";
		case MESSAGE_HISTORY:
			return "MESSAGE_HISTORY";
		case MESSAGE_MANAGE:
			return "MESSAGE_MANAGE";
		case MESSAGE_MENTION_EVERYONE:
			return "MESSAGE_MENTION_EVERYONE";
		case MESSAGE_READ:
			return "MESSAGE_READ";
		case MESSAGE_TTS:
			return "MESSAGE_TTS";
		case MESSAGE_WRITE:
			return "MESSAGE_WRITE";
		case NICKNAME_CHANGE:
			return "NICKNAME_CHANGE";
		case NICKNAME_MANAGE:
			return "NICKNAME_MANAGE";
		case PRIORITY_SPEAKER:
			return "PRIORITY_SPEAKER";
		case UNKNOWN:
			return "UNKNOWN - you should not be getting this, report it to the devs via the support server";
		case VIEW_AUDIT_LOGS:
			return "VIEW_AUDIT_LOGS";
		case VIEW_CHANNEL:
			return "VIEW_CHANNEL";
		case VIEW_GUILD_INSIGHTS:
			return "VIEW_GUILD_INSIGHTS";
		case VOICE_CONNECT:
			return "VOICE_CONNECT";
		case VOICE_DEAF_OTHERS:
			return "VOICE_DEAF_OTHERS";
		case VOICE_MOVE_OTHERS:
			return "VOICE_MOVE_OTHERS";
		case VOICE_MUTE_OTHERS:
			return "VOICE_MUTE_OTHERS";
		case VOICE_SPEAK:
			return "VOICE_SPEAK";
		case VOICE_STREAM:
			return "VOICE_STREAM";
		case VOICE_USE_VAD:
			return "VOICE_USE_VAD";
		default:
			return "null - you should not be getting this, report it to the devs via the support server";

		}

	}

}
