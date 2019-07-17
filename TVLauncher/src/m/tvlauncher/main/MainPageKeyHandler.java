package m.tvlauncher.main;

import m.tvlauncher.R;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnKeyListener;

public class MainPageKeyHandler implements OnKeyListener {
	private MainPageEventHandler handler;
	
	public MainPageKeyHandler(MainPageEventHandler handler) {
		this.handler = handler;
	}
	
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			v.playSoundEffect(SoundEffectConstants.CLICK);
			if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				View vv = v.getParent().focusSearch(v, View.FOCUS_LEFT);
				if (vv != null) {
					vv.requestFocus();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
				View vv = v.getParent().focusSearch(v, View.FOCUS_UP);
				if (vv != null) {
					vv.requestFocus();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
				View vv = v.getParent().focusSearch(v, View.FOCUS_RIGHT);
				if (vv != null) {
					vv.requestFocus();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
				View vv = v.getParent().focusSearch(v, View.FOCUS_DOWN);
				if (vv != null) {
					vv.requestFocus();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
					|| keyCode == KeyEvent.KEYCODE_ENTER
					|| keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
				onOK(v);
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_MENU) {
				onMenu(v);
				return true;
			} else if (keyCode >= KeyEvent.KEYCODE_0 
					&& keyCode <= KeyEvent.KEYCODE_9) {
				onNumber(v, keyCode - KeyEvent.KEYCODE_0);
				return true;
			} else if (keyCode >= KeyEvent.KEYCODE_NUMPAD_0 
					&& keyCode <= KeyEvent.KEYCODE_NUMPAD_9) {
				onNumber(v, keyCode - KeyEvent.KEYCODE_NUMPAD_0);
				return true;
			}
		}
		return false;
	}
	
	private void onOK(View v) {
		if (v.getId() == R.id.vSetting) {
			handler.OnSetting(v);
		} else {
			App app = (App) v.getTag();
			if (app != null) {
				app.launch(v.getContext());
			}
		}
	}
	
	private void onMenu(View v) {
		App app = (App) v.getTag();
		if (app != null && app.removable()) {
			Uri uri = Uri.parse("package:" + app.packageName);
	        Intent intent = new Intent(Intent. ACTION_DELETE ,uri);
	        v.getContext().startActivity(intent);
		}
	}
	
	private void onNumber(View v, int number) {
		
	}
	
}
