package com.yoonveloping.javasocketclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication2 {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9000;

	public static void main(String[] args) {

		Scanner scanner = null;
		Socket socket = null;
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);
			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 4. reader/writer 생성
			BufferedReader response = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "utf-8"));
			// 값을 읽어드림
			PrintWriter request = new PrintWriter(
				new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			// 값을 밖으로 보냄

			// 5. join 프로토콜
			System.out.print("이름>> ");
			String nickname = scanner.nextLine();
			request.println("join:" + nickname);
			request.flush();

			// 6. chatClientReceive Thread 시작
			ChatClientReceiveThread chatClientreceivethread = new ChatClientReceiveThread(socket,
				response, request);
			chatClientreceivethread.start();

			// 7. 키보드 입력 처리
			while (true) {
				System.out.print(">> ");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					// 8. quit 프로토콜 처리
					request.println("quit");
					break;
				} else {
					// 9.메세지 처리
					request.println("message:" + input); // 안끝냇으니깐 input에 내가 적은거 출력되게
				}
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