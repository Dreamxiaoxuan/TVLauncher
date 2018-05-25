package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandMKD implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		File targetDir = client.toFile(param);
		if (targetDir.exists()) {
			client.reply("550 Request action not taken");
		} else if (targetDir.mkdirs()) {
			client.reply("250 Request file action ok,complete");
		} else {
			client.reply("550 Request action not taken");
		}
		return false;
	}
	
}