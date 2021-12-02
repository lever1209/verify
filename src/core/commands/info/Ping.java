package core.commands.info;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

import core.Global;
import core.enums.HelpPage;
import core.listeners.Command;
import core.tools.Tools;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Ping implements Command {

	@Override
	public void run(List<String> args, GuildMessageReceivedEvent event, ArrayList<Long> devList) throws Exception {
		if (args.size() == 0) {
			event.getChannel().sendMessage("Pong!\n" + services.UptimePing.sendPing("www.discord.com") + "ms").queue();

		} else if ((args.get(0).contentEquals("-shards") | args.get(0).contentEquals("-s"))
				& devList.contains(event.getMember().getIdLong())) {

			List<JDA> shardList = event.getJDA().getShardManager().getShards();
			StringBuilder sB = new StringBuilder();
			for (JDA i : shardList) {
				sB.append(i.getRestPing());
			}

			event.getChannel().sendMessage(sB).queue();

		} else if (args.get(0).contentEquals("-all") | args.get(0).contentEquals("-a")) {

			event.getChannel().sendMessage("Gathering data...").queue(msg -> {
				try {
					long timeToProcess = 0;
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					String out = "Pong!\n" + "Google: " + services.UptimePing.sendPing("www.google.com") + "ms\n"
							+ "JDA Gateway: " + event.getJDA().getGatewayPing() + "ms\n" + "www.discord.com: "
							+ services.UptimePing.sendPing("www.discord.com") + "ms";

					stopWatch.stop();
					System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
					timeToProcess = stopWatch.getTime(TimeUnit.MILLISECONDS);
					msg.editMessage(out + "\nTime to process: " + timeToProcess + "ms").queue();
				} catch (Exception e) {

				}

			});
		} else {
			Tools.wrongUsage(event.getChannel(), this);
		}

	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String getHelp() {
		return "Gives you the ping of the bot\n" + "Usage: `" + Global.Prefix + getCalls()[0] + "`";
	}

	@Override
	public String[] getCalls() {

		return new String[] { "ping", "pong" };
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
		return Global.Prefix + getCalls()[0] + " [-all] [-shards]";
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
