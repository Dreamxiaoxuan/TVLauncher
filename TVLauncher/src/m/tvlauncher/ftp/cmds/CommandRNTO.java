package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandRNTO implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		if (param.isEmpty()) {
			client.reply("450 Cannot rename nonexistent file");
		} else {
			File targetFile = client.toFile(param);
			if (targetFile.exists()) {
				client.reply("550 File " + targetFile.getAbsolutePath() + " already exists");
			} else {
				client.reply(client.renameTo(targetFile));
			}
		}
		return false;
	}
	
}