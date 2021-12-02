package core.autoActions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import core.Global;
import core.Main;
import core.autoActions.actions.Ban;
import core.autoActions.actions.DeleteMessage;
import core.autoActions.actions.Leave;
import core.autoActions.actions.SendMessage;
import core.autoActions.actions.ToggleRole;
import core.autoActions.actions.UnBan;
import core.enums.HelpPage;
import core.listeners.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CreateAction implements Command {

	private final Map<String, Action> actions = new HashMap<>();

	public CreateAction() {
		addAction(new SendMessage());
		addAction(new Ban());
		addAction(new Leave());
		addAction(new DeleteMessage());
		addAction(new UnBan());
		addAction(new ToggleRole());
	}

	private void addAction(Action p) {
		if (!actions.containsKey(p.type())) {
			actions.put(p.type(), p);
		}
	}

	public Collection<Action> getActions() {
		return actions.values();
	}

	public Action getAction(String actionType) {
		return actions.get(actionType);
	}

	public Action newAction(String type) {
		try {
			Action action = getAction(type);
			action.setActionID();
			action.setType(type);
			return action;
		} catch (Exception e) {
			throw new NullPointerException("Action type not found. . .");
		}
	}

	public Action newAction(JSONObject jobj) {
		try {
			Action action = getAction(jobj.get("type").toString()).build();

			try {
				action.setChannelID(Long.parseLong(jobj.get("channelID").toString()));
			} catch (Exception e) {
				// e.printStackTrace();
				action.setChannelID(0L);
			}
			try {
				action.setActionID(Long.parseLong(jobj.get("actionID").toString()));
			} catch (Exception e) {
				// e.printStackTrace();
				action.setActionID();
			}
			try {
				action.setType(jobj.get("type").toString());
			} catch (Exception e) {
				// e.printStackTrace();

				throw new NullPointerException("type not found, and is required");

			}

			try {
				action.setString(jobj.get("string").toString());
			} catch (Exception e) {
				// e.printStackTrace();
				action.setString("");
			}

			try {
				action.setGuildID(Long.parseLong(jobj.get("guildID").toString()));
			} catch (Exception e) {
				// e.printStackTrace();
				action.setGuildID(0L);
			}
			try {
				action.setMessageID(Long.parseLong(jobj.get("messageID").toString()));
			} catch (Exception e) {
				// e.printStackTrace();
				action.setMessageID(0L);
			}
			try {
				action.setUserID(Long.parseLong(jobj.get("userID").toString()));
			} catch (Exception e) {
				// e.printStackTrace();
				action.setUserID(0L);
			}
			try {
				long parseTime = Long.parseLong(jobj.get("time").toString());
				action.setTime(parseTime);
				// System.out.println(jobj.get("time").toString()+"time from parser"+parseTime);
			} catch (Exception e) {
				// e.printStackTrace();
				action.setTime(20L);
			}

			return action;

		} catch (Exception e) {
			throw new NullPointerException("Type must be supplied. . .");
		}

	}

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {

		if (args.size() == 1) {
			// System.out.println("size 1");
			if (args.get(0).contentEquals("-l") | args.get(0).contentEquals("-list")) {
				StringBuilder sB = new StringBuilder();
				for (Action i : getActions()) {

					sB.append("**Type:** " + i.type().toString() + "\n**Keys:** ");
					for (String j : i.keys()) {
						sB.append(j + " ");
					}
					sB.append("\n\n");

				}
				event.getChannel().sendMessage(sB).queue();
				return;
			}
		}

		event.getMessage().addReaction(assets.Reactions.VS_CHECK).queue();

		StringBuilder sB = new StringBuilder();

		for (String i : args) {
			sB.append(i + " ");
		}

		// System.out.println("bparse");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(sB.toString());

		Action action = newAction(jobj);

		Main.actionList.add(action);
		// event.getChannel().sendMessage(action.toJSON()+"\n"+jobj+"\n"+(action.getTime()-System.currentTimeMillis())).queue();

	}

	@Override
	public String[] getCalls() {
		return new String[] { "createaction", "action", "ca" };
	}

	@Override
	public String getHelp() {
		return "A developer command for manually creating actions\nWhen creating a new action, follow the json syntax and action names can be revealed by using "
				+ Global.Prefix + getName() + " [-l | -list]";
	}

	@Override
	public String getUsage() {
		return Global.Prefix + getName() + " <jsonString | [-l | -list]>";
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public HelpPage getPage() {
		return HelpPage.DEV;
	}

	@Override
	public String getName() {
		return getCalls()[0];
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