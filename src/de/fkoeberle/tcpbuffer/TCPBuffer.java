/*
 * This file is made available under the Creative Commons CC0 1.0 Universal Public Domain Dedication:
 * 
 * http://creativecommons.org/publicdomain/zero/1.0/deed.en
 * 
 * The person who associated a work with this deed has dedicated the work to the public domain 
 * by waiving all of his or her rights to the work worldwide under copyright law, including all
 * related and neighboring rights, to the extent allowed by law. You can copy, modify, distribute 
 * and perform the work, even for commercial purposes, all without asking permission. 
 * 
 */
package de.fkoeberle.tcpbuffer;

public class TCPBuffer {
	public static void main(String[] args) {
		String targetAddress = getProperty("target.address", "obsidian-mc.ru");
		String targetPortString = getProperty("target.port",
				Constants.MINECRAFT_DEFAULT_PORT_STRING);
		String portString = getProperty("port", Constants.DEFAULT_PORT_STRING);
		String periodString = getProperty(Constants.PERIOD_PROPERTY,
				Constants.PERIOID_DEFAULT_VALUE);
		Server server = new Server();
		server.addEventListener(new EventListener() {

			@Override
			public void handleEvent(String event) {
				System.out.println(event);
			}
		});
		if (!periodString.equals(Constants.PERIOID_DEFAULT_VALUE)) {
			server.setPeriodInMS(periodString);
		}
		
		if(!Boolean.parseBoolean(System.getProperty("server", Boolean.TRUE.toString()))) server.setHosting(true);
		
		if (server.isHosting()) {
			System.out.println("Starting TCPBuffer server...");
			targetAddress = getProperty("target.address", "localhost"); // ACTUAL MINECRAFT SERVER ADDRESS
			targetPortString = getProperty("target.port", Constants.MINECRAFT_DEFAULT_PORT_STRING);  // ACTUAL MINECRAFT SERVER PORT
			portString = getProperty("port", Constants.DEFAULT_PORT_STRING); // LISTENING TO
			CommandExecutor.loadConfig();
		} else {
			System.out.println("Starting TCPBuffer client...");
			targetAddress = getProperty("target.address", "obsidian-mc.ru"); // WHERE TO CONNECT
			targetPortString = getProperty("target.port", Constants.DEFAULT_PORT_STRING);  // WHAT PORT TO CONNECT
			portString = getProperty("port", Constants.MINECRAFT_DEFAULT_PORT_STRING); // PROXY PORT
		}
		
		server.startServer(targetAddress, targetPortString, portString);
	}

	public static String getProperty(String property, String defaultValue) {
		String value = System.getProperty(property);
		if (value == null) value = defaultValue;
		return value;
	}
}
