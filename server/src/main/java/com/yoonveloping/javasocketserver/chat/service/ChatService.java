package com.yoonveloping.javasocketserver.chat.service;

import com.yoonveloping.javasocketserver.database.FileService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ChatService {

	private String name;
	private final List<PrintWriter> writerList;
	private final FileService fileService;
	private static final String REQUEST_SPLIT_REGEX = ":";

	public ChatService(List<PrintWriter> writerList, FileService fileService) {
		this.writerList = writerList;
		this.fileService = fileService;
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
		int count = 0;
		try {
			count = fileService.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("count = " + count);
		broadcast(count + "번째 방문자입니다.");
		broadcast(name + "님이 입장했습니다.");
		addWriter(writer);
	}

	public void chat(PrintWriter printWriter, String request) {
		final List<String> token = List.of(request.split(REQUEST_SPLIT_REGEX));
		if (token.size() != 2) {
			return;
		}
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
		if ("quit".equals(header)) {
			doQuit(printWriter);
			return;
		}
		broadcast("EXCEPTION");
	}

	private void broadcast(String message) {
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
