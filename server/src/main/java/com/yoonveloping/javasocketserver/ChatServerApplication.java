package com.yoonveloping.javasocketserver;

import com.yoonveloping.javasocketserver.chat.controller.ChatController;
import java.io.IOException;
import java.net.ServerSocket;

public class ChatServerApplication {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			ChatController chatController = new ChatController(serverSocket);
			chatController.start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
