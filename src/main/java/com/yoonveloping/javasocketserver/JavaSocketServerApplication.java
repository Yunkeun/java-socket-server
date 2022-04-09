package com.yoonveloping.javasocketserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaSocketServerApplication {

	private static final int PORT = 9000;

	public static void main(String[] args) {
		makeServer();
	}

	private static ServerSocket connectSocketPort() {
		try {
			return new ServerSocket(PORT);
		} catch (IOException IOE) {
			System.out.println("해당 포트가 열려있습니다.");
		}
		return null;
	}

	private static void makeServer() {
		ServerSocket serverSocket = connectSocketPort();
		try {
			System.out.println("서버가 열렸습니다.");
			Socket socket = serverSocket.accept();
			BufferedReader bufferedReader = readClientMessage(socket);
			BufferedWriter bufferedWriter = writeMessageToClient(socket);
			bufferedWriter.flush();

			socket.close();
			serverSocket.close();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static BufferedWriter writeMessageToClient(Socket socket) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bufferedWriter.write("서버에서 알려드립니다.");
		bufferedWriter.newLine();
		return bufferedWriter;
	}

	private static BufferedReader readClientMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String message = bufferedReader.readLine();
		System.out.println("Client message: " + message);
		return bufferedReader;
	}
}
