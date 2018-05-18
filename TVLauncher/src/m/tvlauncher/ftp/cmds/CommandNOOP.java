package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandNOOP implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("200 OK.");
		return false;
	}
	
}