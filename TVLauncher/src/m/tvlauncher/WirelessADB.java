package m.tvlauncher;

import java.io.OutputStream;

public class WirelessADB {
	
	public static void start() {
		try {
			Process p = Runtime.getRuntime().exec("su");
			OutputStream os = p.getOutputStream();
			os.write("setprop service.adb.tcp.port 5555\n".getBytes("utf-8"));
			os.flush();
			os.write("stop adbd\n".getBytes("utf-8"));
			os.flush();
			os.write("start adbd\n".getBytes("utf-8"));
			os.flush();
			os.write("exit\n".getBytes("utf-8"));
			os.flush();
			p.waitFor();
			p.destroy();
			TVLog.log("wireless adb started");
		} catch (Throwable t) {
			TVLog.log(t);
		}
	}
	
	public static void stop() {
		try {
			Process p = Runtime.getRuntime().exec("su");
			OutputStream os = p.getOutputStream();
			os.write("setprop service.adb.tcp.port -1\n".getBytes("utf-8"));
			os.flush();
			os.write("stop adbd\n".getBytes("utf-8"));
			os.flush();
			os.write("start adbd".getBytes("utf-8"));
			os.flush();
			os.write("exit\n".getBytes("utf-8"));
			os.flush();
			p.waitFor();
			p.destroy();
			TVLog.log("wireless adb stopped");
		} catch (Throwable t) {
			TVLog.log(t);
		}
	}
	
}
