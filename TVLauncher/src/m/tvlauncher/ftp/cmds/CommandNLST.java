package m.tvlauncher.ftp.cmds;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.FTPCommandHandler;
import m.tvlauncher.ftp.FTPClient;
import m.tvlauncher.ftp.FTPDataChannel;

public class CommandNLST implements FTPCommandHandler {
	
	public boolean handleCommand(String param, final FTPClient client) throws Throwable {
		File targetDir;
		if (param.isEmpty()) {
			targetDir = client.getWorkingDirFile();
		} else {
			if (client.isTouchablePath(param)) {
				targetDir = new File(param);
			} else {
				targetDir = new File(client.getWorkingDirFile(), param);
			}
		}
		
		if (targetDir.isDirectory()) {
			final File dir = targetDir;
			final ArrayList<File> files = new ArrayList<File>();
			targetDir.list(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					File file = new File(dir, filename);
					if(file.isDirectory()) {
						files.add(file);
					}
					return false;
				}
			});
			if (files.size() > 0) {
				client.reply("150 Opening ASCII mode data connection for /bin/ls.");
				final SimpleDateFormat fmtLess6M = new SimpleDateFormat(" MMM dd HH:mm ", Locale.US);
				final SimpleDateFormat fmtMore6M = new SimpleDateFormat(" MMM dd  yyyy ", Locale.US);
				client.openDataChannel(new FTPDataChannel() {
					public void action(InputStream input, OutputStream output, boolean binaryMode) throws Throwable {
						try {
							OutputStreamWriter osw = new OutputStreamWriter(output, client.getEncoding());
							for (File file : files) {
								String line = "drwxr-xr-x 1 owner group          " + file.length();
								long mTime = file.lastModified();
								long delta = System.currentTimeMillis() - mTime;
								SimpleDateFormat fmt = delta < 15552000000L ? fmtLess6M : fmtMore6M;
								line += fmt.format(new Date(mTime));
								line += file.getName();
								osw.write(line + client.newLine());
							}
							osw.flush();
							osw.close();
							client.reply("226 Transfer complete !");
						} catch (Throwable t) {
							TVLog.log(t);
							client.reply("451 Requested action aborted: local error in processing");
						}
					}
				});
			}
		} else {
			client.reply("451 Requested action aborted: local error in processing");
		}
		return false;
	}
	
}