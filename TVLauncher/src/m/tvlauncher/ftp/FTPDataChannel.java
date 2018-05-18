package m.tvlauncher.ftp;

import java.io.InputStream;
import java.io.OutputStream;

public interface FTPDataChannel {
	
	public void action(InputStream input, OutputStream output, boolean binaryMode) throws Throwable;
	
}
