package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandCDUP implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		if (client.workingDir.isEmpty()) {
			client.reply("550 Current directory has no parent");
		} else {
			File parent = client.getWorkingDirFile().getParentFile();
			if (parent != null) {
				String path = parent.getAbsolutePath();
				if (path.equals(client.rootDir)) {
					client.workingDir = "";
				} else {
					path = path.substring(client.rootDir.length());
					if (path.startsWith(File.separator)) {
						path = path.substring(File.separator.length());
					}
					client.workingDir = path;
				}
				client.reply("200 Command okay");
			} else {
				client.reply("550 Current directory has no parent");
			}
		}
		return false;
	}
	
}