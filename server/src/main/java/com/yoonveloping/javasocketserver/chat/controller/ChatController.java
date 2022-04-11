package com.yoonveloping.javasocketserver.chat.controller;

import com.yoonveloping.javasocketserver.chat.thread.ChatServerProcessThread;
import com.yoonveloping.javasocketserver.chat.service.ChatService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

	private static final int PORT = 9000;
	private static final String LOCALHOST = "127.0.0.1";
	private final ServerSocket serverSocket;

	public ChatController(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void start() throws IOException {
		bindPortWith(serverSocket);
		List<PrintWriter> writerList = new ArrayList<>();
		waitRequest(serverSocket, writerList);
	}

	private void waitRequest(ServerSocket serverSocket, List<PrintWriter> writerList)
		throws IOException {
		while (true) {
			Socket socket = serverSocket.accept();
			final ChatService chatService = new ChatService(writerList);
			final ChatServerProcessThread thread = new ChatServerProcessThread(socket, chatService);
			thread.start();
		}
	}

	private void bindPortWith(ServerSocket serverSocket) throws IOException {
//		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String hostAddress = LOCALHOST;
		serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
		consoleLog("연결 대기중입니다. - " + hostAddress + ": " + PORT);
	}

	private void consoleLog(String log) {
		System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
	}
}