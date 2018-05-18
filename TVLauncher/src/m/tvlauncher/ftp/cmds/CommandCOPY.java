package m.tvlauncher.ftp.cmds;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import m.tvlauncher.ftp.FTPCommandHandler;

public abstract class CommandCOPY implements FTPCommandHandler {
	
	protected void copy(InputStream is, OutputStream os) throws Throwable {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		byte[] buf = new byte[1024 * 16];
		int len = bis.read(buf);
		while (len != -1) {
			bos.write(buf, 0, len);
			len = bis.read(buf);
		}
		bos.flush();
	}
	
	protected void copy(InputStreamReader isr, OutputStreamWriter osw) throws Throwable {
		BufferedReader br = new BufferedReader(isr);
		BufferedWriter bw = new BufferedWriter(osw);
		String line = br.readLine();
		while (line != null) {
			bw.append(line);
			bw.newLine();
			line = br.readLine();
		}
		bw.flush();
	}
	
}
