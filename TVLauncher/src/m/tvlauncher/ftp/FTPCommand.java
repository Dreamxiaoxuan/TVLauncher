package m.tvlauncher.ftp;

import m.tvlauncher.TVLog;
import m.tvlauncher.ftp.cmds.CommandABOR;
import m.tvlauncher.ftp.cmds.CommandACCT;
import m.tvlauncher.ftp.cmds.CommandALLO;
import m.tvlauncher.ftp.cmds.CommandAPPE;
import m.tvlauncher.ftp.cmds.CommandCDUP;
import m.tvlauncher.ftp.cmds.CommandCWD;
import m.tvlauncher.ftp.cmds.CommandDELE;
import m.tvlauncher.ftp.cmds.CommandHELP;
import m.tvlauncher.ftp.cmds.CommandLIST;
import m.tvlauncher.ftp.cmds.CommandMKD;
import m.tvlauncher.ftp.cmds.CommandMODE;
import m.tvlauncher.ftp.cmds.CommandNLST;
import m.tvlauncher.ftp.cmds.CommandNOOP;
import m.tvlauncher.ftp.cmds.CommandPASS;
import m.tvlauncher.ftp.cmds.CommandPASV;
import m.tvlauncher.ftp.cmds.CommandPORT;
import m.tvlauncher.ftp.cmds.CommandPWD;
import m.tvlauncher.ftp.cmds.CommandQUIT;
import m.tvlauncher.ftp.cmds.CommandREIN;
import m.tvlauncher.ftp.cmds.CommandREST;
import m.tvlauncher.ftp.cmds.CommandRETR;
import m.tvlauncher.ftp.cmds.CommandRMD;
import m.tvlauncher.ftp.cmds.CommandRNFR;
import m.tvlauncher.ftp.cmds.CommandRNTO;
import m.tvlauncher.ftp.cmds.CommandSITE;
import m.tvlauncher.ftp.cmds.CommandSMNT;
import m.tvlauncher.ftp.cmds.CommandSTOR;
import m.tvlauncher.ftp.cmds.CommandSTOU;
import m.tvlauncher.ftp.cmds.CommandSTRU;
import m.tvlauncher.ftp.cmds.CommandSYST;
import m.tvlauncher.ftp.cmds.CommandTYPE;
import m.tvlauncher.ftp.cmds.CommandUNRCG;
import m.tvlauncher.ftp.cmds.CommandUSER;
import m.tvlauncher.ftp.cmds.CommandXMKD;
import m.tvlauncher.ftp.cmds.CommandXPWD;

public enum FTPCommand {
	USER(CommandUSER.class),
	PASS(CommandPASS.class),
//	ACCT(CommandACCT.class),
	CDUP(CommandCDUP.class),
//	SMNT(CommandSMNT.class),
	CWD(CommandCWD.class),
	QUIT(CommandQUIT.class),
//	REIN(CommandREIN.class),
	PORT(CommandPORT.class),
//	PASV(CommandPASV.class),
	TYPE(CommandTYPE.class),
//	STRU(CommandSTRU.class),
//	MODE(CommandMODE.class),
	RETR(CommandRETR.class),
	STOR(CommandSTOR.class),
//	STOU(CommandSTOU.class),
//	APPE(CommandAPPE.class),
//	ALLO(CommandALLO.class),
//	REST(CommandREST.class),
//	RNFR(CommandRNFR.class),
//	RNTO(CommandRNTO.class),
	ABOR(CommandABOR.class),
	DELE(CommandDELE.class),
	RMD(CommandRMD.class),
	XMKD(CommandXMKD.class),
	MKD(CommandMKD.class),
	PWD(CommandPWD.class),
	LIST(CommandLIST.class),
//	NLST(CommandNLST.class),
//	SITE(CommandSITE.class),
	SYST(CommandSYST.class),
//	HELP(CommandHELP.class),
	NOOP(CommandNOOP.class),
	XPWD(CommandXPWD.class),
	UNRCG(CommandUNRCG.class);
	
	private FTPCommandHandler handler;
	
	private FTPCommand(Class<? extends FTPCommandHandler> handlerType) {
		try {
			handler = handlerType.newInstance();
		} catch (Throwable t) {
			TVLog.log(t);
		}
	}
	
	public boolean handlerCommand(String param, FTPClient client) throws Throwable {
		return handler.handleCommand(param, client);
	}
	
}
