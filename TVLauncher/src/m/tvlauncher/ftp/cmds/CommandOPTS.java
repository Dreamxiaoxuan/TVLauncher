package m.tvlauncher.ftp.cmds;

import java.util.Locale;

import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPCommandHandler;

public class CommandOPTS implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		String[] parts = param.split(" ");
		String opt = parts[0].toUpperCase(Locale.US);
		if ("UTF8".equals(opt)) {
			String switcher;
			if (parts.length > 1) {
				switcher = parts[1].toUpperCase(Locale.US);
			} else {
				switcher = "ON";
			}
			if ("ON".equals(switcher)) {
				client.setEncoding("utf-8");
			} else {
				client.setEncoding("ascii");
			}
			client.reply("200 OPTS accepted");
		} else {
			client.reply("502 Unrecognized option");
		}
		return false;
	}
	
}
