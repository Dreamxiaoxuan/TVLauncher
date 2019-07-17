package m.tvlauncher.ftp.cmds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPDataChannel;

public class CommandSTOR extends CommandCOPY {
	
	public boolean handleCommand(String param, final FTPClient client) throws Throwable {
		if (param.isEmpty()) {
			client.reply("501 Syntax error in parameters or arguments");
		} else {
			final File file = client.toFile(param);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			client.openDataChannel(new FTPDataChannel() {
				public void action(InputStream input, OutputStream output, boolean binaryMode) throws Throwable {
					try {
						FileOutputStream fos = new FileOutputStream(file);
						if (binaryMode) {
							client.reply("150 Opening Binary mode data connection for " + file.getAbsolutePath());
							copy(input, fos);
						} else {
							client.reply("150 Opening ASCII mode data connection for " + file.getAbsolutePath());
							OutputStreamWriter osw = new OutputStreamWriter(fos, client.getEncoding());
							InputStreamReader isr = new InputStreamReader(input, client.getEncoding());
							copy(isr, osw);
						}
						fos.close();
						client.reply("226 Transfer complete !");
					} catch (Throwable t) {
						TVLog.log(t);
						client.reply("451 Requested action aborted: local error in processing");
					}
				}
			});
		}
		return false;
	}
	
}