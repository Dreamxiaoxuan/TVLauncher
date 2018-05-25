package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandSIZE implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		File targetFile = client.toFile(param);
		if (targetFile.exists()) {
			if (targetFile.isFile()) {
				client.reply("213 " + targetFile.length());
			} else {
				client.reply("550 Cannot get the size of a non-file");
			}
		} else {
			client.reply("550 Cannot get the SIZE of nonexistent object");
		}
		return false;
	}
	
}