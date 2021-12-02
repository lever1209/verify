package services;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import core.Main;

public class Server {

	public static boolean socketShutdown = false;
	public static boolean serverShutdown = false;

	public static void sockets() {
		try {
			boolean done = false;

			while (!done & !Server.serverShutdown) {
				ServerSocket serverSocket = new ServerSocket(1209);
				Socket socket = serverSocket.accept();
				DataInputStream dIn = new DataInputStream(socket.getInputStream());
				System.out.println(Main.pid + " : SOCKET RECEIVED");
				byte messageType = dIn.readByte();

				switch (messageType) {
				case 1: // FORCE SHUTDOWN
					System.out.println(Main.pid + " : SOCKET : SHUTDOWN");
					socketShutdown = true;
					serverShutdown = true;
					socket.close();
					serverSocket.close();
					dIn.close();

					break;
				case 2: // FORCE UPDATE
					System.out.println(Main.pid + " : SOCKET : UPDATE");
					Main.forceUpdate = true;
					// serverShutdown = true;
					socket.close();
					serverSocket.close();
					dIn.close();
					break;
				case 3: // RESPAWN
					System.out.println(Main.pid + " : SOCKET : RESPAWN");
					Main.respawn = true;
					serverShutdown = true;
					socket.close();
					serverSocket.close();
					dIn.close();
					break;
				default:
					done = true;
					break;
				}

			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static void commands() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print(Main.pid + " " + "" + " ~> ");
			String command = scan.nextLine();

			if (command.contentEquals("update")) {
				Main.forceUpdate = true;
			}

			if (command.contentEquals("time")) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				Date date = new Date(System.currentTimeMillis());
				String timeC = formatter.format(date);
				System.out.println(System.currentTimeMillis() + " : " + timeC);
			}
			// System.out.println("CLOSE");
			// scan.close();
		}
	}

}
