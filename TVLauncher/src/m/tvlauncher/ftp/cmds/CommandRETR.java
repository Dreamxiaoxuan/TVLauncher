package m.tvlauncher.ftp.cmds;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPDataChannel;

public class CommandRETR extends CommandCOPY {
	
	public boolean handleCommand(String param, final FTPClient client) throws Throwable {
		File f;
		if (client.isTouchablePath(param)) {
			f = new File(param);
		} else {
			f = new File(client.getWorkingDirFile(), param);
		}
		
		final File file = f;
		client.openDataChannel(new FTPDataChannel() {
			public void action(InputStream input, OutputStream output, boolean binaryMode) throws Throwable {
				try {
					FileInputStream fis = new FileInputStream(file);
					if (binaryMode) {
						client.reply("150 Opening Binary mode data connection for " + file.getAbsolutePath());
						copy(fis, output);
					} else {
						client.reply("150 Opening ASCII mode data connection for " + file.getAbsolutePath());
						InputStreamReader isr = new InputStreamReader(fis, client.getEncoding());
						OutputStreamWriter osw = new OutputStreamWriter(output, client.getEncoding());
						copy(isr, osw);
					}
					fis.close();
					client.reply("226 Transfer complete !");
				} catch (Throwable t) {
					TVLog.log(t);
					client.reply("451 Requested action aborted: local error in processing");
				}
			}
		});
		return false;
	}
	
}