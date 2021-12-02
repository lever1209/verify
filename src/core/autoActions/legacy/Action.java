package core.autoActions.legacy;

import java.time.Instant;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import core.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public class Action {

	@SuppressWarnings("unchecked")
	public static void createAction(Object event, String action, long milliSecondDelay) throws ParseException {
		JSONObject obj = new JSONObject();

		if (event instanceof GuildMessageReceivedEvent) {
			StringBuilder users = new StringBuilder();
			for (Member i : ((GuildMessageReceivedEvent) event).getMessage().getMentionedMembers()) {
				users.append(i.getIdLong());
			}
			for (String i : ((GuildMessageReceivedEvent) event).getMessage().getContentRaw().replaceAll("[^0-9]", " ")
					.split("\\s+")) {
				if (!i.isBlank()) {
					try {
						if (Main.bot.getUserById(i) != null) {
							users.append(i);
						}
					} catch (Exception e) {

					}
				}
			}
			obj.put("everyone", ((GuildMessageReceivedEvent) event).getMessage().mentionsEveryone());
			obj.put("messageID", ((GuildMessageReceivedEvent) event).getMessage().getIdLong());
			obj.put("userID", ((GuildMessageReceivedEvent) event).getAuthor().getId());
			obj.put("usersInMessage", users.toString());
			obj.put("delay", Instant.ofEpochMilli(milliSecondDelay).toEpochMilli());
			obj.put("type", action);
			obj.put("guildID", ((GuildMessageReceivedEvent) event).getGuild().getId());
			obj.put("channelID", ((GuildMessageReceivedEvent) event).getChannel().getId());
			// obj.put("messageID", "");
		}
		if (event instanceof PrivateMessageReceivedEvent) {
			StringBuilder users = new StringBuilder();
			for (Member i : ((PrivateMessageReceivedEvent) event).getMessage().getMentionedMembers()) {
				users.append(i.getIdLong());
			}
			for (String i : ((PrivateMessageReceivedEvent) event).getMessage().getContentRaw().replaceAll("[^0-9]", " ")
					.split("\\s+")) {
				if (!i.isBlank()) {
					try {
						if (Main.bot.getUserById(i) != null) {
							users.append(i);
						}
					} catch (Exception e) {

					}
				}
			}
			obj.put("everyone", ((PrivateMessageReceivedEvent) event).getMessage().mentionsEveryone());
			obj.put("messageID", ((PrivateMessageReceivedEvent) event).getMessage().getIdLong());
			obj.put("userID", ((PrivateMessageReceivedEvent) event).getAuthor().getId());
			obj.put("usersInMessage", users.toString());
			obj.put("delay", Instant.ofEpochMilli(milliSecondDelay).toEpochMilli());
			obj.put("type", action);
			obj.put("guildID", null); // TODO add "guild:56354373232" and similar via "metadata" and booleans for
										 // toggle normal gets
			obj.put("channelID", ((PrivateMessageReceivedEvent) event).getChannel().getId());
			// obj.put("messageID", "");
		}
		if (event instanceof MessageReceivedEvent) {
			StringBuilder users = new StringBuilder();
			for (Member i : ((MessageReceivedEvent) event).getMessage().getMentionedMembers()) {
				users.append(i.getIdLong());
			}
			for (String i : ((MessageReceivedEvent) event).getMessage().getContentRaw().replaceAll("[^0-9]", " ")
					.split("\\s+")) {
				if (!i.trim().isBlank()) {
					try {
						if (Main.bot.getUserById(i) != null) {
							users.append(i);
						}
					} catch (Exception e) {

					}
				}
			}
			obj.put("everyone", ((MessageReceivedEvent) event).getMessage().mentionsEveryone());
			obj.put("messageID", ((MessageReceivedEvent) event).getMessage().getIdLong());
			obj.put("userID", ((MessageReceivedEvent) event).getAuthor().getId());
			obj.put("usersInMessage", users.toString());
			obj.put("delay", Instant.ofEpochMilli(milliSecondDelay).toEpochMilli());
			obj.put("type", action);
			obj.put("guildID", ((MessageReceivedEvent) event).getGuild().getId());
			obj.put("channelID", ((MessageReceivedEvent) event).getChannel().getId());
			// obj.put("messageID", "");
		}

	}

	{/*
		 * 
		 * if (Instant.ofEpochSecond(seconds).toEpochMilli() >
		 * Instant.now().toEpochMilli()) {
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * protected BotActionType action;
		 * protected Object[] stringInput;
		 * protected int seconds = 0;
		 * 
		 * public Action(BotActionType type, Object[] args) {
		 * this.action = type;
		 * this.stringInput = args;
		 * }
		 * 
		 * public Action(BotActionType type, Object[] args, int seconds) {
		 * 
		 * this.action = type;
		 * this.stringInput = args;
		 * this.seconds = seconds;
		 * 
		 * }
		 * 
		 * public BotActionType getAction() {
		 * return action;
		 * }
		 * 
		 * public void setActivityStream(String message, String streamURL) {
		 * try {
		 * Main.bot.getPresence().setActivity(Activity.streaming(message, streamURL));
		 * } catch (Exception e) {
		 * throw e;
		 * }
		 * 
		 * }
		 * 
		 * public void setActivity(byte type, String message) {
		 * try {
		 * switch (type) {
		 * case 0:
		 * Main.bot.getPresence().setActivity(null);
		 * break;
		 * case 1:
		 * Main.bot.getPresence().setActivity(Activity.listening(message));
		 * break;
		 * case 2:
		 * Main.bot.getPresence().setActivity(Activity.playing(message));
		 * break;
		 * case 3:
		 * Main.bot.getPresence().setActivity(Activity.watching(message));
		 * break;
		 * }
		 * 
		 * } catch (Exception e) {
		 * throw e;
		 * }
		 * 
		 * }
		 * 
		 * public Object getApplicationInfo(ApplicationInfoType type) {
		 * 
		 * try {
		 * switch (type) {
		 * case blank:
		 * 
		 * break;
		 * case ID:
		 * break;
		 * case IDLong:
		 * break;
		 * case description:
		 * break;
		 * case doesBotRequireCodeGrant:
		 * break;
		 * case iconID:
		 * break;
		 * case iconURL:
		 * break;
		 * default:
		 * break;
		 * 
		 * }
		 * 
		 * // new ApplicationInfo().
		 * 
		 * } catch (Exception e) {
		 * throw e;
		 * }
		 * 
		 * return null;
		 * }
		 * 
		 * {/*
		 * 
		 * 
		 * try {
		 * switch (type) {
		 * case 0:
		 * 
		 * break;
		 * }
		 * 
		 * } catch (Exception e) {
		 * throw e;
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * public Object startAction(Object[] types, String[] Strings) {
		 * // List<Activity> activity = new ArrayList<Activity>();
		 * // List<ActivityFlag> activityFlag = new ArrayList<ActivityFlag>();
		 * List<ApplicationInfo> applicationInfo = new ArrayList<ApplicationInfo>();
		 * List<ApplicationTeam> applicationTeam = new ArrayList<ApplicationTeam>();
		 * List<Category> category = new ArrayList<Category>();
		 * List<ChannelType> channelType = new ArrayList<ChannelType>();
		 * List<ClientType> clientType = new ArrayList<ClientType>();
		 * List<EmbedType> embedType = new ArrayList<EmbedType>();
		 * List<Emote> emote = new ArrayList<Emote>();
		 * List<Guild> guild = new ArrayList<Guild>();
		 * List<GuildChannel> guildChannel = new ArrayList<GuildChannel>();
		 * List<GuildVoiceState> guildVoiceState = new ArrayList<GuildVoiceState>();
		 * List<Icon> icon = new ArrayList<Icon>();
		 * List<IFakeable> iFakaeble = new ArrayList<IFakeable>();
		 * List<IMentionable> iMentionable = new ArrayList<IMentionable>();
		 * List<Invite> invite = new ArrayList<Invite>();
		 * List<IPermissionHolder> iPermissionHolder = new
		 * ArrayList<IPermissionHolder>();
		 * List<ISnowflake> iSnowflake = new ArrayList<ISnowflake>();
		 * List<ListedEmote> listedEmote = new ArrayList<ListedEmote>();
		 * List<Member> member = new ArrayList<Member>();
		 * List<Message> message = new ArrayList<Message>();
		 * List<MessageActivity> messageActivity = new ArrayList<MessageActivity>();
		 * List<MessageChannel> messageChannel = new ArrayList<MessageChannel>();
		 * List<MessageEmbed> messageEmbed = new ArrayList<MessageEmbed>();
		 * List<MessageHistory> messageHistory = new ArrayList<MessageHistory>();
		 * List<MessageReaction> messageReaction = new ArrayList<MessageReaction>();
		 * List<MessageType> messageType = new ArrayList<MessageType>();
		 * List<PermissionOverride> permissionOverride = new
		 * ArrayList<PermissionOverride>();
		 * List<PrivateChannel> privateChannel = new ArrayList<PrivateChannel>();
		 * List<RichPresence> richPresence = new ArrayList<RichPresence>();
		 * List<Role> role = new ArrayList<Role>();
		 * List<SelfUser> selfUser = new ArrayList<SelfUser>();
		 * StoreChannel[] storeChannel;
		 * TeamMember[] teamMember;
		 * TextChannel[] textChannel;
		 * User[] user;
		 * VoiceChannel[] voiceChannel;
		 * Webhook[] webhook;
		 * WebhookType[] webhookType;
		 * 
		 * for (Object i : types) {
		 * if (i instanceof Guild) {
		 * 
		 * }
		 * }
		 * 
		 * switch (this.action) {
		 * case UnavailableGuildJoined:
		 * break;
		 * case UnavailableGuildLeave:
		 * break;
		 * case createEmote:
		 * for (Guild i : guild) {
		 * for (String j : Strings) {
		 * if (j.contains("-emoteName")) {
		 * // i.createEmote(j, icon.get(0), role).queue();
		 * }
		 * }
		 * 
		 * }
		 * break;
		 * case createPermission: // TODO wtf is a permission? is it what you set to a
		 * channel? if so its not
		 * // needed
		 * break;
		 * case createRole:
		 * for (Guild i : guild) {
		 * i.createRole().queue(); // TODO
		 * }
		 * break;
		 * case createTextChannel:
		 * 
		 * for (Guild i : guild) {
		 * for (String j : Strings) {
		 * if (j.contains("-textChannel")) {
		 * i.createTextChannel(j).queue();
		 * }
		 * }
		 * }
		 * 
		 * break;
		 * case createVoiceChannel:
		 * 
		 * for (Guild i : guild) {
		 * for (String j : Strings) {
		 * if (j.contains("-voiceChannel")) {
		 * i.createVoiceChannel(j).queue();
		 * }
		 * }
		 * }
		 * 
		 * break;
		 * case unMuteVC:
		 * for (Member i : member) {
		 * i.mute(false).queue();
		 * }
		 * break;
		 * case toggleDeafenVC:
		 * for (Member i : member) {
		 * 
		 * if (i.getVoiceState().isDeafened()) {
		 * i.deafen(false).queue();
		 * } else {
		 * i.deafen(true).queue();
		 * }
		 * 
		 * }
		 * break;
		 * case toggleMuteVC:
		 * for (Member i : member) {
		 * 
		 * if (i.getVoiceState().isMuted()) {
		 * i.mute(false).queue();
		 * } else {
		 * i.mute(true).queue();
		 * }
		 * 
		 * }
		 * break;
		 * case unDeafenVC:
		 * for (Member i : member) {
		 * i.deafen(false).queue();
		 * }
		 * break;
		 * case deafenVC:
		 * 
		 * for (Member i : member) {
		 * i.deafen(true).queue();
		 * }
		 * 
		 * break;
		 * case deleteEmote:
		 * for (Emote i : emote) {
		 * i.delete().queue();
		 * }
		 * break;
		 * case deleteMessage:
		 * for (Message i : message) {
		 * i.delete().queue();
		 * }
		 * break;
		 * case deletePermission:
		 * break;
		 * case deleteRole:
		 * for (Role i : role) {
		 * i.delete().queue();
		 * }
		 * break;
		 * case deleteTextChannel:
		 * for (TextChannel i : textChannel) {
		 * i.delete().queue();
		 * }
		 * break;
		 * case deleteVoiceChannel:
		 * for (VoiceChannel i : voiceChannel) {
		 * i.delete().queue();
		 * }
		 * break;
		 * case editMessage:
		 * for (Message i : message) {
		 * for (String j : Strings) {
		 * i.editMessage(j).queue();
		 * }
		 * }
		 * break;
		 * case endDM:
		 * break;
		 * case guildAvailable:
		 * boolean[] unavailables = {};
		 * for (Guild i : guild) {
		 * ArrayUtils.add(unavailables, Main.bot.isUnavailable(i.getIdLong()));
		 * }
		 * return unavailables;
		 * case guildBan:
		 * for (Member j : member) {
		 * j.ban(0).queue();
		 * }
		 * break;
		 * case guildDeafenVC:
		 * break;
		 * case guildInviteCreate:
		 * return null;
		 * case guildInviteDelete:
		 * return null;
		 * case guildJoin:
		 * return null;
		 * case guildKick:
		 * for (Member i : member) {
		 * i.kick().queue();
		 * }
		 * break;
		 * case guildLeave:
		 * for (Guild i : guild) {
		 * i.leave().queue();
		 * }
		 * break;
		 * case guildMemberRemove: // TODO
		 * break;
		 * case guildMuteVC: // TODO
		 * break;
		 * case guildReady: // TODO
		 * break;
		 * case guildSuppress: // TODO
		 * break;
		 * case guildUnavailable: // TODO
		 * break;
		 * case guildUpdateAFKChannel: // TODO
		 * break;
		 * case guildUpdateAfkTimeout:
		 * for (Guild i : guild) {
		 * // i.getAfkChannel() TODO
		 * }
		 * break;
		 * case guildUpdateBanner:
		 * for (Guild i : guild) {
		 * // TODO
		 * }
		 * break;
		 * case guildUpdateBoostCount:
		 * break;
		 * case guildUpdateBoostTier:
		 * break;
		 * case guildUpdateDescription:
		 * for (Guild i : guild) {
		 * // TODO
		 * }
		 * break;
		 * case guildUpdateExplicitContentLevel:
		 * // guild[0].getExplicitContentLevel(). TODO
		 * break;
		 * case guildUpdateFeatures:
		 * for (Guild i : guild) {
		 * // TODO
		 * }
		 * break;
		 * case guildUpdateIcon:
		 * for (Guild i : guild) {
		 * i.getIconUrl();
		 * }
		 * break;
		 * case guildUpdateMFALevel: // TODO
		 * break;
		 * case guildUpdateMaxMembers: // TODO
		 * break;
		 * case guildUpdateMaxPresences: // TODO
		 * break;
		 * case guildUpdateName:
		 * for (Guild i : guild) {
		 * // i.getName() TODO
		 * }
		 * break;
		 * case guildUpdateNotificationLevel: // TODO
		 * break;
		 * case guildUpdateOwner: // TODO
		 * break;
		 * case guildUpdateRegion: // TODO
		 * break;
		 * case guildUpdateSplash: // TODO
		 * break;
		 * case guildUpdateSystemChannel: // TODO
		 * break;
		 * case guildUpdateVanityCode: // TODO
		 * break;
		 * case guildUpdateVerificationLevel: // TODO
		 * break;
		 * case guildVoiceStream: // TODO what
		 * break;
		 * case joinVC:
		 * for (VoiceChannel i : voiceChannel) {
		 * // Gets the channel in which the bot is currently connected.
		 * VoiceChannel connectedChannel =
		 * i.getGuild().getSelfMember().getVoiceState().getChannel();
		 * // Checks if the bot is connected to a voice channel.
		 * if (connectedChannel == null) {
		 * // Get slightly fed up at the user.
		 * throw new IllegalStateException();
		 * }
		 * // Disconnect from the channel.
		 * i.getGuild().getAudioManager().closeAudioConnection();
		 * // Notify the user.
		 * throw new IllegalStateException();
		 * }
		 * break;
		 * case leaveVC:
		 * break;
		 * case moveVC:
		 * break;
		 * case muteVC:
		 * break;
		 * case removeAllReactions:
		 * break;
		 * case removeReaction:
		 * break;
		 * case removeReactionEmote:
		 * break;
		 * case selfActivityEnd:
		 * break;
		 * case selfActivityStart:
		 * break;
		 * case selfDeafenVC:
		 * break;
		 * case selfMuteVC:
		 * break;
		 * case selfTyping:
		 * break;
		 * case sendEmbed:
		 * break;
		 * case sendMessage:
		 * break;
		 * case sendReaction:
		 * break;
		 * case startDM:
		 * break;
		 * case updateActivityOrder:
		 * break;
		 * case updateAvatar:
		 * break;
		 * case updateDiscriminator:
		 * break;
		 * case updateEmoteName:
		 * break;
		 * case updateEmoteRoles:
		 * break;
		 * case updateFlag:
		 * break;
		 * case updateNickname:
		 * break;
		 * case updatePermission:
		 * break;
		 * case updateRoleColor:
		 * break;
		 * case updateRoleHoist:
		 * break;
		 * case updateRoleMentionable:
		 * break;
		 * case updateRoleName:
		 * break;
		 * case updateRolePermissions:
		 * break;
		 * case updateRolePosition:
		 * break;
		 * case updateStatus:
		 * break;
		 * case updateTextChannelCategory:
		 * break;
		 * case updateTextChannelName:
		 * break;
		 * case updateTextChannelPosition:
		 * break;
		 * case updateTextChannelSlowMode:
		 * break;
		 * case updateTextChannelToNSFW:
		 * break;
		 * case updateTextChannelTopic:
		 * break;
		 * case updateUserName:
		 * break;
		 * case updateVoiceChannelBitrate:
		 * break;
		 * case updateVoiceChannelCategory:
		 * break;
		 * case updateVoiceChannelName:
		 * break;
		 * case updateVoiceChannelPosition:
		 * break;
		 * case updateVoiceChannelUserLimit:
		 * break;
		 * default:
		 * break;
		 * 
		 * }
		 * return false;
		 * 
		 * /*
		 */
	}

}
