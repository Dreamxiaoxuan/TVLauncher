package m.tvlauncher.ftp.cmds;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;

public class CommandPORT implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		try {
			client.setPASVMode(false);
			String[] parts = param.split(",");
			String hostName = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
			int port = Integer.parseInt(parts[4]) * 256 + Integer.parseInt(parts[5]);
			client.setDataChannelAddress(hostName, port);
			client.reply("200 Command okay");
		} catch (Throwable t) {
			TVLog.log(t);
			client.reply("501 Syntax error in parameters or arguments");
		}
		return false;
	}
	
}