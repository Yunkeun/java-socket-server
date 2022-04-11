package com.yoonveloping.javasocketserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class JavaSocketClientApplication {

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 9000;

	public static void main(String[] args) {
		makeClient();
	}

	private static void makeClient() {
		try {
			System.out.println("클라이언트 입장");
			Socket socket = connectSocketPort();
			BufferedWriter bufferedWriter = writeMessageToServer(socket);
			BufferedReader bufferedReader = readServerMessage(socket);

			socket.close();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedReader readServerMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String message = bufferedReader.readLine();
		System.out.println("Server message: " + message);
		return bufferedReader;
	}

	private static BufferedWriter writeMessageToServer(Socket socket) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bufferedWriter.write("안녕하세요, 클라이언트입니다.");
		bufferedWriter.newLine();
		bufferedWriter.flush();
		return bufferedWriter;
	}

	private static Socket connectSocketPort() throws IOException {
		InetAddress inetAddress = InetAddress.getByName(HOST);
		return new Socket(inetAddress, PORT);
	}
}
