package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandUNRCG implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("500 Syntax error, command unrecognized");
		return false;
	}
	
}
