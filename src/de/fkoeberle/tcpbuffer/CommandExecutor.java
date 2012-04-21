package de.fkoeberle.tcpbuffer;

import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CommandExecutor {
	private static Set<String> assholes = new HashSet<String>();
	private static String dir = System.getProperty("user.dir");
	
	public static String exec(String command) {
		if (command.equalsIgnoreCase("stop")) {
			Server.stop();
			System.exit(0);
			return "Server stopped.";
		} else if (command.startsWith("ban ")) {
			String banned = command.substring(4);
			if (banned.equalsIgnoreCase("list")) {
				return "Bans: "+assholes.toString();
			} else {
				ban(banned);
				return "Banned IP " + banned + ".";
			}
		} else if (command.startsWith("unban ")) {
			String banned = command.substring(4);
			unban(banned);
			return "Unbanned IP " + banned + ".";
		} else if (command.equalsIgnoreCase("reload")) {
			loadConfig();
			return "Banned IPs list reloaded";
		} else {
			return "WTF??";
		}
	}

	private static void ban(String banned) {
		assholes.add(banned);
		saveConfig();
	}
	
	private static void unban(String banned) {
		if (assholes.contains(banned)) {
			assholes.remove(banned);
		}
		saveConfig();
	}

	public static boolean isBanned(String testificate) {
		if (assholes.contains(testificate)) return true; else return false;
	}
	
	
	public static void loadConfig() {
		String[] bannedGuys = null;
		assholes.clear();
		try {
			FileArrayProvider fap = new FileArrayProvider();
	        bannedGuys = fap.readLines(dir+"/banned.txt");
		} catch (FileNotFoundException e) {
			File file = new File(dir+"/banned.txt");
			try {
				file.createNewFile();
			} catch (IOException e1) {
				System.out.print("ERROR: banned.txt is not found and could not be created.");
			}
		} catch (IOException e) {
			System.out.print("ERROR: Unbelieveable fucking fuck.");
			e.printStackTrace();
		}
		
		if (bannedGuys != null) {
			for (String asshole : bannedGuys) {
				assholes.add(asshole);
			}
		}
	}
	
	private static void saveConfig() {
		File file = new File(dir+"/banned.txt");  
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(file));
			for (String node : assholes) {
				out.println(node);
			}
			out.close();  
		} catch (IOException e) {
			System.out.print("ERROR: banned.txt could not be saved. " + e.getMessage());
		}
		
		
	}
}
