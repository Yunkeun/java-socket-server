package com.yoonveloping.javasocketclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientApplication2 {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9000;

	public static void main(String[] args) {

		Scanner scanner = null;
		Socket socket = null;
		try {
			scanner = new Scanner(System.in);
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter request = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			System.out.print("이름>> ");
			String name = scanner.nextLine();
			request.println("join:" + name);
			request.flush();

			ChatClientReceiveThread chatClientreceivethread = new ChatClientReceiveThread(socket, response, request);
			chatClientreceivethread.start();

			while (true) {
				System.out.print(">> ");
				String input = scanner.nextLine();
				if ("quit".equals(input)) {
					request.println("quit");
					break;
				}
				request.println("message:" + input);
			}
		} catch (IOException e) {
			log("[client] error: " + e);
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static void log(String logString) {
		System.out.println(logString);
	}
}
