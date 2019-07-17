package m.tvlauncher.remotecontroler;

import android.app.Application;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.Socket;

public class RemoteController extends Application {
	public Socket socket;
	public OutputStream os;
	public BufferedReader br;
}
