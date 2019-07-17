package m.tvlauncher.remotecontroler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LauncherActivity extends Activity implements OnClickListener {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		findViewById(R.id.btnConnect).setOnClickListener(this);
	}
	
	public void onClick(View v) {
		EditText et = (EditText) findViewById(R.id.etIP);
		final String ip = et.getText().toString();
		final Toast t = Toast.makeText(this, "连接中...", Toast.LENGTH_SHORT);
		t.show();
		new Thread() {
			public void run() {
				try {
					final Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, 2468), 5000);
					final OutputStream os = socket.getOutputStream();
					JSONObject json = new JSONObject();
					json.put("action", 1);
					os.write((json + "\n").getBytes("utf-8"));
					os.flush();
					InputStream is = socket.getInputStream();
					final BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
					String line = br.readLine();
					if (line != null) {
						json = new JSONObject(line);
						final int res = json.optInt("res");
						runOnUiThread(new Runnable() {
							public void run() {
								t.cancel();
								if (res == 0) {
									Toast.makeText(LauncherActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
								} else {
									RemoteController rc = (RemoteController) getApplication();
									rc.socket = socket;
									rc.os = os;
									rc.br = br;
									startActivity(new Intent(LauncherActivity.this, MainActivity.class));
									LauncherActivity.this.finish();
								}
							}
						});
					}
				} catch (Throwable t) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(LauncherActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
//							startActivity(new Intent(LauncherActivity.this, MainActivity.class));
//							LauncherActivity.this.finish();
						}
					});
				}
			}
		}.start();
	}
}
