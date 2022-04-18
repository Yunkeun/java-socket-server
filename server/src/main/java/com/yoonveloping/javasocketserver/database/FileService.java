package com.yoonveloping.javasocketserver.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {

	private static final String REGEX = "^[0-9]*$";
	private static final String PATH = "/server/src/main/resources/counter";

	public int writeFile() throws IOException {
		String workingDirectory = calculateWorkingDirectory();
		int count = readFile(workingDirectory);
		FileWriter fileWriter = new FileWriter(workingDirectory + PATH);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(Integer.toString(++count));
		bufferedWriter.flush();
		return count;
	}

	private int readFile(String workingDirectory) throws IOException {
		File file = new File(workingDirectory + PATH);
		if (file.exists() && file.length() == 0) {
			return 0;
		}
		FileReader fileReader = new FileReader(workingDirectory + PATH);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String readLine = bufferedReader.readLine();
		return calculateCount(readLine);
	}

	private int calculateCount(String count) {
		if (count.matches(REGEX)) {
			return Integer.parseInt(count);
		}
		return 0;
	}

	private String calculateWorkingDirectory() {
		return System.getProperty("user.dir");
	}
}
