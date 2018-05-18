package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandTYPE implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		if (param.equals("I") || param.equals("L 8")) {
			client.setBinaryDataChannel(true);
			client.reply("200 Binary type set");
		} else if (param.equals("A") || param.equals("A N")) {
			client.setBinaryDataChannel(false);
			client.reply("200 ASCII type set");
		} else {
			client.reply("503 Malformed TYPE command");
		}
		return false;
	}
	
}