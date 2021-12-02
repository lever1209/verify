package core.listeners.timeout;

import core.listeners.Command;

public class CommandTimeout {

	private long userID;
	private long guildID;
	private long delayDate = System.currentTimeMillis() + 3000;
	private Command command;

	/**
	 * 
	 * @param userID
	 * @param guildID
	 * @param command
	 */
	public CommandTimeout(long userID, long guildID, Command command) {
		this.setUserID(userID);
		this.setGuildID(guildID);
		this.setCommand(command);
	}

	/**
	 * 
	 * @param userID
	 * @param guildID
	 * @param command
	 * @param delayDate
	 */
	public CommandTimeout(long userID, long guildID, Command command, long delayDate) {
		this.setUserID(userID);
		this.setGuildID(guildID);
		this.setDelayDate(System.currentTimeMillis() + delayDate);
		this.setCommand(command);
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getDelayDate() {
		return delayDate;
	}

	public void setDelayDate(long delayTime) {
		this.delayDate = delayTime;
	}

	public long getGuildID() {
		return guildID;
	}

	public void setGuildID(long guildID) {
		this.guildID = guildID;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getKey() {
		return command.getName() + ":" + getUserID();
	}

	public long ms() {
		return (getDelayDate() - System.currentTimeMillis());
	}

}
