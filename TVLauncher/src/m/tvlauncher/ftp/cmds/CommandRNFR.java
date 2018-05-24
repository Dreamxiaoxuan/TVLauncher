package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandRNFR implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		if (param.isEmpty()) {
			client.reply("550 Request action not taken");
		} else {
			File targetFile;
			if (client.isTouchablePath(param)) {
				targetFile = new File(param);
			} else {
				targetFile = new File(client.getWorkingDirFile(), param);
			}
			
			if (targetFile.exists()) {
				client.setRenameFrom(targetFile);
				client.reply("350 Filename noted, now send RNTO");
			} else {
				client.reply("450 Cannot rename nonexistent file");
			}
		}
		return false;
	}
	
}