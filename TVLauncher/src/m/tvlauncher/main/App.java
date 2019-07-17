package m.tvlauncher.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public abstract class App {
	public String packageName;
	public String appName;
	public Drawable icon;
	public Intent iLaunch;
	
	public String toString() {
		return appName + ": " + packageName;
	}
	
	public abstract boolean displayable();
	
	public abstract boolean removable();
	
	public abstract void launch(Context context);
}
