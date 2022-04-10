package com.yoonveloping.javasocketserver.chat.service;

import java.io.PrintWriter;
import java.util.List;

public class ChatService {

	private String name;
	private final List<PrintWriter> writerList;
	private static final String REQUEST_SPLIT_REGEX = ":";

	public ChatService(List<PrintWriter> writerList) {
		this.writerList = writerList;
	}

	public void doQuit(PrintWriter writer) {
		removeWriter(writer);
		broadcast(this.name + "님이 퇴장했습니다.");
	}

	public void doMessage(String message) {
		broadcast(this.name + ":" + message);
	}

	public void doJoin(String name, PrintWriter writer) {
		this.name = name;
		broadcast(name + "님이 입장했습니다.");
		addWriter(writer);
	}

	public void chat(PrintWriter printWriter, String request) {
		final List<String> token = List.of(request.split(REQUEST_SPLIT_REGEX));
		System.out.println("token = " + token);
		divideByHeader(printWriter, token);
	}

	private void divideByHeader(PrintWriter printWriter, List<String> token) {
		String header = token.get(0);
		String data = token.get(1);
		if ("join".equals(header)) {
			doJoin(data, printWriter);
			return;
		}
		if ("message".equals(header)) {
			doMessage(data);
			return;
		}
		doQuit(printWriter);
	}

	private void broadcast(String message) {
		System.out.println("message = " + message);
		synchronized (writerList) {
			writerList.forEach(writer -> {
					writer.println(message);
					writer.flush();
				}
			);
		}
	}

	private void addWriter(PrintWriter writer) {
		synchronized (writerList) {
			writerList.add(writer);
		}
	}

	private void removeWriter(PrintWriter writer) {
		synchronized (writerList) {
			writerList.remove(writer);
		}
	}
}
