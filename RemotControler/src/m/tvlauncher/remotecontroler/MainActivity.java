package m.tvlauncher.remotecontroler;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

import org.json.JSONObject;

public class MainActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Object[][] keys = {
				{"电源", KeyEvent.KEYCODE_TV_POWER}, null, null,
				{"音量+", KeyEvent.KEYCODE_VOLUME_UP}, {"静音", KeyEvent.KEYCODE_VOLUME_MUTE}, {"音量-", KeyEvent.KEYCODE_VOLUME_DOWN},
				{"主页", KeyEvent.KEYCODE_HOME}, {"上", KeyEvent.KEYCODE_DPAD_UP}, {"菜单", KeyEvent.KEYCODE_MENU},
				{"左", KeyEvent.KEYCODE_DPAD_LEFT}, {"确定", KeyEvent.KEYCODE_DPAD_CENTER}, {"右", KeyEvent.KEYCODE_DPAD_RIGHT},
				{"设置", KeyEvent.KEYCODE_SETTINGS}, {"下", KeyEvent.KEYCODE_DPAD_DOWN}, {"返回", KeyEvent.KEYCODE_BACK},
				{"1", KeyEvent.KEYCODE_NUMPAD_1}, {"2", KeyEvent.KEYCODE_NUMPAD_2}, {"3", KeyEvent.KEYCODE_NUMPAD_3},
				{"4", KeyEvent.KEYCODE_NUMPAD_4}, {"5", KeyEvent.KEYCODE_NUMPAD_5}, {"6", KeyEvent.KEYCODE_NUMPAD_6},
				{"7", KeyEvent.KEYCODE_NUMPAD_7}, {"8", KeyEvent.KEYCODE_NUMPAD_8}, {"9", KeyEvent.KEYCODE_NUMPAD_9},
				{"频道+", KeyEvent.KEYCODE_CHANNEL_UP}, {"0", KeyEvent.KEYCODE_NUMPAD_0}, {"频道-", KeyEvent.KEYCODE_CHANNEL_DOWN}
		};
		TableView table = (TableView) findViewById(R.id.tvKeys);
		table.initTable(3, 9);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 9; y++) {
				Object[] key = keys[y * 3 + x];
				if (key != null) {
					Button btn = new Button(this);
					btn.setText((String) key[0]);
					btn.setTag(key[1]);
					table.getCell(x, y).addView(btn, lp);
					btn.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							sendKey((Integer) v.getTag());
						}
					});
				}
			}
		}
		
		findViewById(R.id.btnText).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText etText = (EditText) findViewById(R.id.etText);
				sendText(etText.getText().toString());
			}
		});
	}
	
	private void sendKey(final int key) {
		new Thread() {
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("action", 3);
					json.put("key", key);
					RemoteController rc = (RemoteController) getApplication();
					rc.os.write((json + "\n").getBytes("utf-8"));
					rc.os.flush();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}.start();
	}
	
	private void sendText(final String text) {
		new Thread() {
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("action", 4);
					json.put("text", text);
					RemoteController rc = (RemoteController) getApplication();
					rc.os.write((json + "\n").getBytes("utf-8"));
					rc.os.flush();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}.start();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		new Thread() {
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("action", 2);
					RemoteController rc = (RemoteController) getApplication();
					rc.os.write((json + "\n").getBytes("utf-8"));
					rc.os.flush();
					rc.socket.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				
				System.exit(0);
			}
		}.start();
	}
}