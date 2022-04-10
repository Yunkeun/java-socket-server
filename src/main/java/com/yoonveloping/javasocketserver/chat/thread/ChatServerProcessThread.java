package com.yoonveloping.javasocketserver.chat.thread;

import com.yoonveloping.javasocketserver.chat.service.ChatService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatServerProcessThread extends Thread {

	private final Socket socket;
	private final ChatService chatService;

	public ChatServerProcessThread(Socket socket, ChatService chatService) {
		this.socket = socket;
		this.chatService = chatService;
	}

	public void run() {
		try {
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			while (true) {
				final String request = bufferedReader.readLine();
				if (request == null) {
					System.out.println("연결 끊김");
					chatService.doQuit(printWriter);
					break;
				}
				chatService.chat(printWriter, request);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
