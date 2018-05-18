package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandPASS implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		if (checkPASS(param)) {
			client.reply("230 User logged in, proceed");
			client.workingDir = "";
			return false;
		} else {
			client.reply("530 Not logged in");
			return true;
		}
	}
	
	private boolean checkPASS(String password) {
		// skip
		return true;
	}
	
}