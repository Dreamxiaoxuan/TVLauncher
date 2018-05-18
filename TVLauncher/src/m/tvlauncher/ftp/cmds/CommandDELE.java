package m.tvlauncher.ftp.cmds;

import java.io.File;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandDELE implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		File targetFile;
		if (client.isTouchablePath(param)) {
			targetFile = new File(param);
		} else {
			targetFile = new File(client.getWorkingDirFile(), param);
		}
		
		if (deleteFile(targetFile)) {
			client.reply("250 Request file action ok,complete");
		} else {
			client.reply("550 Request action not taken");
		}
		return false;
	}
	
	private boolean deleteFile(File file) {
		if (file.isDirectory()) {
			String[] names = file.list();
			if (names != null) {
				for (String name : names) {
					if (!deleteFile(new File(file, name))) {
						return false;
					}
				}
			}
		}
		return file.delete();
	}
	
}