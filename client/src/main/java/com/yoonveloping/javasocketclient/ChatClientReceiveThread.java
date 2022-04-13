package com.yoonveloping.javasocketclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientReceiveThread extends Thread {

	private final Socket socket;
	private final BufferedReader response;
	private final PrintWriter request;

	public ChatClientReceiveThread(Socket socket, BufferedReader response, PrintWriter request) {
		this.socket = socket;
		this.response = response;
		this.request = request;
	}

	@Override
	public void run() {
		try {
			BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			while (true) {
				String receiveData = request.readLine();
				System.out.println(receiveData);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (socket.isClosed()) {
				System.out.println("정상 종료");
				return;
			}
			e.printStackTrace();
		}
	}
}
