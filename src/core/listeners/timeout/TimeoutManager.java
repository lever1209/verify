package core.listeners.timeout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimeoutManager {

	private static final Map<String, CommandTimeout> timeouts = new HashMap<>();

	public static void addTimeout(@Nonnull String key, @Nonnull CommandTimeout commandTimeout) {
		if (!timeouts.containsKey(key)) {
			timeouts.put(key, commandTimeout);
		} else {
			throw new IllegalArgumentException("Key already exists. . .");
		}
	}

	@Nullable
	public static CommandTimeout getTimeout(@Nonnull String key) {
		return timeouts.get(key);
	}

	public static Collection<CommandTimeout> getValues() {
		return timeouts.values();
	}

	public static List<String> getKeys() {
		List<String> output = new ArrayList<String>();
		for (CommandTimeout i : getValues()) {
			output.add("CMD NAME : " + i.getCommand().getName() + "\nGUILDID : " + i.getGuildID() + "\nUSERID : "
					+ i.getUserID() + "\nDELAYLEFT : " + (i.getDelayDate() - System.currentTimeMillis()));
		}
		return output;
	}

	public static boolean delTimeout(@Nonnull String key) {
		try {
			timeouts.remove(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
