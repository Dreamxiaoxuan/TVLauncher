package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandPWD implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("257 \"" + client.getWorkingDirFile().getAbsolutePath() + "\" is current directory.");
		return false;
	}
	
}