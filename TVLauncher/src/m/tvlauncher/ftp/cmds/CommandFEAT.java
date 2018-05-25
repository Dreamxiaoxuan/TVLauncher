package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandFEAT implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.reply("211-Features supported by FTP Server");
		client.reply(" UTF8");
		client.reply("211 End");
		return false;
	}
	
}
