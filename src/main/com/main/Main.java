package com.main;
import java.util.Scanner;

import com.commandparser.CommandParser;

public class Main {

	public static void main(String[] args)
	{
		CommandParser parser = new CommandParser();

		while (true) {
			System.out.println("Please type any command to continue Risk game ...");
			Scanner sc = new Scanner(System.in);
			String command = sc.nextLine();
			parser.processCommands(command);
		}
	}
}
