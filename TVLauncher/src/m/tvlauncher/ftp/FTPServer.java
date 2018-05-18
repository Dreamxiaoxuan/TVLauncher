package m.tvlauncher.ftp;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap.SimpleEntry;
import java.util.Enumeration;

import m.tvlauncher.TVLog;

public class FTPServer {
	private boolean start;
	private ServerSocket server;
	
	public synchronized void start(final int port, final String rootDir) {
		if (!start) {
			new Thread() {
				public void run() {
					try {
						server = new ServerSocket(port, 1);
						TVLog.log("server start at: " + getIPAddress());
						Socket incoming = server.accept();
						while (incoming != null) {
							TVLog.log("connected client: " + incoming.getInetAddress().getHostAddress());
							FTPClient client = new FTPClient(incoming);
							client.rootDir = rootDir;
							client.serverAddress = getIPAddress();
							client.serverPort = port;
							handleClient(client);
							incoming = server.accept();
						}
					} catch (Throwable t) {
						TVLog.log(t);
					}
				}
			}.start();
			start = true;
		}
	}
	
	private String getIPAddress() throws Throwable {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface intf = en.nextElement();
			Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
			while (enumIpAddr.hasMoreElements()) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					return inetAddress.getHostAddress();
				}
			}
		}
		return "127.0.0.1";
	}
	
	private void handleClient(final FTPClient client) {
		new Thread(){
			public void run() {
				try {
					client.reply("220 Service ready for new user");
					boolean finished = false;
					while (!finished) {
						SimpleEntry<FTPCommand, String> res = client.nextCommand();
						if (res == null) {
							finished = true;
						} else {
							FTPCommand cmd = res.getKey();
							String param = res.getValue();
							TVLog.log("Command:" + cmd + " Parameter:" + param);
							finished = cmd.handlerCommand(param, client);
						}
					}
				} catch (Throwable t) {
					TVLog.log(t);
				} finally {
					client.close();
				}
			}
		}.start();
	}
	
	public synchronized void quit() {
		if (start && server != null && !server.isClosed()) {
			start = false;
			try {
				server.close();
			} catch (Throwable t) {
				TVLog.log(t);
			}
			server = null;
		}
	}
	
}
