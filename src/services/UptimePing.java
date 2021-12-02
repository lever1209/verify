package services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class UptimePing {

	public static long sendPing(String IP) throws IOException {
		/*
		 * StopWatch stopWatch = new StopWatch(); stopWatch.start(); InetAddress sender
		 * = InetAddress.getByName(IP); System.out.println("Pinging: " + IP);
		 * stopWatch.stop(); long timeP = stopWatch.getTime(TimeUnit.MICROSECONDS);
		 * System.out.println(timeP); if
		 * (sender.isReachable(5000)){//.isReachable(5000)) {
		 * System.out.println("Successfully pinged: " + IP); return timeP; } else {
		 * System.out.println("Failed to ping: " + IP); return -1; }
		 */

		int port = 80;
		long timeToRespond = 0;

		InetAddress inetAddress = InetAddress.getByName(IP);
		InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(true);

		Date start = new Date();
		if (sc.connect(socketAddress)) {
			Date stop = new Date();
			timeToRespond = (stop.getTime() - start.getTime());

		}

		// System.out.println("fix me");

		if (socketAddress.getAddress() == null) {
			throw new NullPointerException("Host not found. . .");
		} else {
			return timeToRespond;
		}

	}
}
