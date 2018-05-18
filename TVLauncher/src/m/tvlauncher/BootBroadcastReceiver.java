package m.tvlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
	private static boolean started;
	
	public void onReceive(Context context, Intent intent) {
		if (!started) {
			started = true;
			Intent ii = new Intent(context, MainActivity.class);
			ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(ii);
		}
	}
}
