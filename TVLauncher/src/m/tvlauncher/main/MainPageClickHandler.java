package m.tvlauncher.main;

import m.tvlauncher.R;
import android.view.View;
import android.view.View.OnClickListener;

public class MainPageClickHandler implements OnClickListener {

	private MainPageEventHandler handler;
	
	public MainPageClickHandler(MainPageEventHandler handler) {
		this.handler = handler;
	}
	
	public void onClick(View v) {
		if (v.getId() == R.id.vSetting) {
			handler.OnSetting(v);
		} else {
			App app = (App) v.getTag();
			if (app != null) {
				v.getContext().startActivity(app.iLaunch);
			}
		}
	}
	
}
