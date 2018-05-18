package m.tvlauncher.ftp.cmds;

import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPServer;

public class CommandPASV implements FTPCommandHandler {
	
	public boolean handleCommand(String param, FTPClient client) throws Throwable {
		StringBuilder sb = new StringBuilder("227 Entering Passive Mode (");
		sb.append(client.serverAddress.replace('.', ','));
		sb.append(",");
		sb.append(client.serverPort / 256);
		sb.append(",");
		sb.append(client.serverPort % 256);
		sb.append(").\r\n");
		client.reply(sb.toString());
		return false;
	}
	
}