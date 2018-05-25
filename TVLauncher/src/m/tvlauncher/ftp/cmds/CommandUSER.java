package m.tvlauncher.ftp.cmds;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandUSER implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("331 User name okay, need password.");
		TVLog.log("Message: user " + param + " Form " + client.getHostName() + "Login");
		return false;
	}
	
}