package m.tvlauncher;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class TVLog {
	private static Handler handler;
	
	public static void init(final Context context) {
		handler = new Handler(Looper.getMainLooper(), new Callback() {
			private Toast toast;
			
			public boolean handleMessage(Message message) {
				String text = String.valueOf(message.obj);
				if (toast == null) {
					toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
				} else {
					toast.setText(text);
					toast.setDuration(Toast.LENGTH_LONG);
				}
				toast.show();
				return false;
			}
		});
	}
	
	public static void log(String text) {
		Message msg = new Message();
		msg.obj = text;
		handler.sendMessage(msg);
		System.out.println(text);
	}
	
	public static void log(Throwable t) {
		Message msg = new Message();
		msg.obj = t.getMessage();
		handler.sendMessage(msg);
		t.printStackTrace();
	}
	
}
