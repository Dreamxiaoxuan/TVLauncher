package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandCWD implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		File newDir = null;
		if ("..".equals(param) || (".." + File.separator).equals(param)) {
			if (client.workingDir.isEmpty()) {
				client.reply("550 The directory does not exists");
			} else {
				newDir = client.getWorkingDirFile().getParentFile();
			}
		} else if (".".equals(param) || ("." + File.separator).equals(param)) {
			client.reply("250 Requested file action okay, directory change to " + client.getWorkingDirFile().getAbsolutePath());
		} else {
			if (client.isTouchablePath(param)) {
				newDir = new File(param);
			} else {
				newDir = new File(client.getWorkingDirFile(), param);
			}
		}
		
		if (newDir != null) {
			if (newDir.exists() && newDir.isDirectory()) {
				String path = newDir.getAbsolutePath();
				if (path.equals(client.rootDir)) {
					client.workingDir = "";
				} else {
					path = path.substring(client.rootDir.length());
					if (path.startsWith(File.separator)) {
						path = path.substring(File.separator.length());
					}
					client.workingDir = path;
				}
				client.reply("250 Requested file action okay, directory change to " + newDir.getAbsolutePath());
			} else {
				client.reply("501 Syntax error in parameters or arguments");
			}
		}
		return false;
	}
	
}