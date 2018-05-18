package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandABOR implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		client.closeDataChannel();
		client.reply("421 Service not available, closing control connection");
		return false;
	}
	
}