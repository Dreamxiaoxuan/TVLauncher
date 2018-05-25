package m.tvlauncher.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap.SimpleEntry;

import m.tvlauncher.TVLog;

public class FTPClient {
	private Socket client;
	private BufferedReader reader;
	private BufferedWriter writer;
	public String rootDir;
	public String workingDir;
	private String dataChannelHost;
	private int dataChannelPort;
	private Socket dataChannel;
	private boolean binaryDataChannel;
	private boolean pasvMode;
	private ServerSocket pasvServer;
	private File renameFrom;
	private String encoding;
	
	public FTPClient(Socket client) {
		this.client = client;
		binaryDataChannel = true;
		encoding = "utf-8";
	}
	
	public SimpleEntry<FTPCommand, String> nextCommand() throws Throwable {
		if (reader == null) {
			InputStreamReader isr = new InputStreamReader(client.getInputStream(), encoding);
			reader = new BufferedReader(isr);
		}
		
		String line = reader.readLine();
		if (line == null) {
			return null;
		}
		
		String cmdStr;
		String param;
		int p = line.indexOf(" ");
		if (p > 0) {
			cmdStr = line.substring(0, p).toUpperCase();
			param = line.substring(p + 1);
		} else {
			cmdStr = line.toLowerCase().toUpperCase();
			param = "";
		}
		
		FTPCommand cmd = null;
		try {
			cmd = FTPCommand.valueOf(cmdStr.toUpperCase());
		} catch (Throwable t) {
			TVLog.log(t);
		}
		
		if (cmd == null) {
			cmd = FTPCommand.UNRCG;
			return new SimpleEntry<FTPCommand, String>(cmd, line);
		} else {
			return new SimpleEntry<FTPCommand, String>(cmd, param);
		}
	}
	
	public void reply(String message) throws Throwable {
		if (writer == null) {
			OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream(), encoding);
			writer = new BufferedWriter(osw);
		}
		TVLog.log("replay: " + message);
		writer.append(message).append(newLine());
		writer.flush();
	}
	
	public String newLine() {
		return "\r\n";
	}
	
	public void close() {
		if (client != null && client.isConnected()) {
			try {
				client.close();
			} catch (Throwable t) {
				TVLog.log(t);
			}
			client = null;
			reader = null;
			writer = null;
		}
	}
	
	public String getHostName() {
		return client.getInetAddress().getHostName();
	}
	
	public File getWorkingDirFile() {
		return new File(rootDir, workingDir);
	}
	
	public boolean isTouchablePath(String path) {
		if (path.startsWith(rootDir)) {
			String subPath = path.substring(rootDir.length());
			return (subPath.isEmpty() || subPath.startsWith(File.separator));
		}
		return false;
	}
	
	public void setDataChannelAddress(String hostName, int port) {
		dataChannelHost = hostName;
		dataChannelPort = port;
	}
	
	public void setBinaryDataChannel(boolean binaryMode) {
		binaryDataChannel = binaryMode;
	}
	
	public int setPASVMode(boolean enable) throws Throwable {
		pasvMode = enable;
		if (pasvMode) {
			pasvServer = new ServerSocket(0);
			return pasvServer.getLocalPort();
		} else {
			if (pasvServer != null && !pasvServer.isClosed()) {
				try {
					pasvServer.close();
				} catch (Throwable t) {
					TVLog.log(t);
				}
				pasvServer = null;
			}
			return 0;
		}
	}
	
	public void openDataChannel(FTPDataChannel channel) {
		try {
			if (pasvMode) {
				dataChannel = pasvServer.accept();
			} else {
				dataChannel = new Socket(dataChannelHost, dataChannelPort);
			}
			channel.action(dataChannel.getInputStream(), dataChannel.getOutputStream(), binaryDataChannel);
		} catch (Throwable t) {
			TVLog.log(t);
		} finally {
			closeDataChannel();
		}
	}
	
	public void closeDataChannel() {
		if (dataChannel != null && dataChannel.isConnected()) {
			try {
				dataChannel.close();
			} catch (Throwable t) {
				TVLog.log(t);
			}
			dataChannel = null;
			dataChannelHost = null;
			dataChannelPort = 0;
			binaryDataChannel = true;
		}
		if (pasvServer != null && !pasvServer.isClosed()) {
			try {
				pasvServer.close();
			} catch (Throwable t) {
				TVLog.log(t);
			}
			pasvServer = null;
			pasvMode = false;
		}
	}
	
	public void setRenameFrom(File renameFrom) {
		this.renameFrom = renameFrom;
	}
	
	public String renameTo(File destFile) {
		File srcFile = renameFrom;
		renameFrom = null;
		if (srcFile == null) {
			return "550 Rename error, maybe RNFR not sent";
		} else if (srcFile.renameTo(destFile)) {
			return "250 rename successful";
		} else {
			return "550 Error during rename operation";
		}
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
