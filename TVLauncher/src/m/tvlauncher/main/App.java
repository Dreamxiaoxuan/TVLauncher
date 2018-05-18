package m.tvlauncher.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class App {
	public String packageName;
	public String appName;
	public Drawable icon;
	public Intent iLaunch;
	
	public String toString() {
		return appName + ": " + packageName;
	}
}
