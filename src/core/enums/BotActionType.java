package core.enums;

public enum BotActionType {

	// self user events
	updateUserName, updateDiscriminator, updateAvatar, updateStatus, updateActivityOrder, updateFlag, selfTyping,
	selfActivityStart, selfActivityEnd,

	updateNickname,

	// guild text channel message events
	sendMessage, editMessage, deleteMessage, sendEmbed, sendReaction, removeReaction, removeAllReactions,
	removeReactionEmote,

	// textchannel events
	createTextChannel, deleteTextChannel, updateTextChannelName, updateTextChannelTopic, updateTextChannelPosition,
	updateTextChannelToNSFW, updateTextChannelCategory, updateTextChannelSlowMode,

	// voice channel events
	createVoiceChannel, deleteVoiceChannel, updateVoiceChannelName, updateVoiceChannelPosition,
	updateVoiceChannelUserLimit, updateVoiceChannelBitrate, updateVoiceChannelCategory,

	// guild vc events
	joinVC, moveVC, leaveVC, muteVC, deafenVC, toggleMuteVC, toggleDeafenVC, unMuteVC, unDeafenVC, guildMuteVC,
	guildDeafenVC, selfMuteVC, selfDeafenVC, guildSuppress, guildVoiceStream,

	// permission updates
	deletePermission, updatePermission, createPermission,

	// PM/DM events
	startDM, endDM,

	// guild events
	guildReady, guildJoin, guildLeave, guildAvailable, guildUnavailable, UnavailableGuildJoined, UnavailableGuildLeave,
	guildBan, guildKick, guildMemberRemove,

	guildUpdateAFKChannel, guildUpdateAfkTimeout, guildUpdateSystemChannel, guildUpdateExplicitContentLevel,
	guildUpdateIcon, guildUpdateMFALevel, guildUpdateName, guildUpdateNotificationLevel, guildUpdateOwner,
	guildUpdateRegion, guildUpdateSplash, guildUpdateVerificationLevel, guildUpdateFeatures, guildUpdateVanityCode,
	guildUpdateBanner, guildUpdateDescription, guildUpdateBoostTier, guildUpdateBoostCount, guildUpdateMaxMembers,
	guildUpdateMaxPresences,

	// invite events
	guildInviteCreate, guildInviteDelete,

	// roles
	createRole, deleteRole,

	updateRoleColor, updateRoleHoist, updateRoleMentionable, updateRoleName, updateRolePermissions, updateRolePosition,

	// emotes
	createEmote, deleteEmote,

	updateEmoteName, updateEmoteRoles, // ? TODO research on this

	/*
	 * ban,
	 * kick,
	 * sendMessage,
	 * deleteMessage,
	 * editMessage,
	 * addRole,
	 * removeRole,
	 * deleteRole,
	 */

}
