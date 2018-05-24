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
import m.tvlauncher.ftp.cmds.CommandOPTS;
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
	USER(CommandUSER.class),   // 输入登录用户名
	PASS(CommandPASS.class),   // 检验登录密码
//	ACCT(CommandACCT.class),
	CDUP(CommandCDUP.class),   // 返回上层目录
//	SMNT(CommandSMNT.class),
	CWD(CommandCWD.class),     // 进入指定目录
	QUIT(CommandQUIT.class),   // 客户端退出
//	REIN(CommandREIN.class),
	PORT(CommandPORT.class),   // 设置供服务端主动数据连接使用的客户端地址和端口号
	PASV(CommandPASV.class),   // 进入服务端被动模式，返回服务端数据连接的地址和端口号
	TYPE(CommandTYPE.class),   // 设置后续操作的数据类型
//	STRU(CommandSTRU.class),
//	MODE(CommandMODE.class),
	RETR(CommandRETR.class),   // 上传数据到服务端
	STOR(CommandSTOR.class),   // 下载数据到客户端
//	STOU(CommandSTOU.class),
//	APPE(CommandAPPE.class),
//	ALLO(CommandALLO.class),
//	REST(CommandREST.class),
	RNFR(CommandRNFR.class),   // 设置重命操作的名源文件
	RNTO(CommandRNTO.class),   // 将重命名操作的源文件重命名为指定名字
	ABOR(CommandABOR.class),   // 取消当前操作，断开数据连接
	DELE(CommandDELE.class),   // 删除文件
	RMD(CommandRMD.class),     // 删除目录及其下的文件
	XMKD(CommandXMKD.class),   // 新建目录
	MKD(CommandMKD.class),     // 新建目录
	PWD(CommandPWD.class),     // 获取当前目录完整路径
	LIST(CommandLIST.class),   // 获取当前目录文件列表
	NLST(CommandNLST.class),   // 获取当前目录下的目录列表
//	SITE(CommandSITE.class),
	SYST(CommandSYST.class),   // 获取服务器类型
//	HELP(CommandHELP.class),
	NOOP(CommandNOOP.class),   // 无操作
	XPWD(CommandXPWD.class),   // 获取当前目录完整路径
	OPTS(CommandOPTS.class),   // 设置客户端支持的特性
	UNRCG(CommandUNRCG.class); // 客户端错误，不能识别的命令
	
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
