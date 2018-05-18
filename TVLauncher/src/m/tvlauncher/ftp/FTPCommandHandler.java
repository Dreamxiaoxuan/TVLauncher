package m.tvlauncher.ftp;

public interface FTPCommandHandler {

	public boolean handleCommand(String param, FTPClient client) throws Throwable;
	
}
