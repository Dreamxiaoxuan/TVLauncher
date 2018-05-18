package m.tvlauncher.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainPageEventHandler {

	public void OnSetting(View v) {
		Context context = v.getContext();
		Intent ii = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
		context.startActivity(ii);
	}
	
}
