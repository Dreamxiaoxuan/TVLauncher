package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandQUIT implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("221 Service closing control connection");
		return true;
	}
	
}