package m.tvlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import m.tvlauncher.ftp.FTPServer;
import m.tvlauncher.main.App;
import m.tvlauncher.main.MainPageClickHandler;
import m.tvlauncher.main.MainPageEventHandler;
import m.tvlauncher.main.MainPageKeyHandler;

public class MainActivity extends Activity {
	private static final int COL_SIZE = 4;
	private static final int ROW_SIZE = 3;
	
	private MainPageKeyHandler key;
	private MainPageClickHandler click;
	private int spacing;
	private ArrayList<App> apps;
	private BroadcastReceiver receiver;
	private FTPServer ftp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TVLog.init(this);
		MainPageEventHandler handler = new MainPageEventHandler();
		key = new MainPageKeyHandler(handler);
		click = new MainPageClickHandler(handler);
		
		View rlMain = View.inflate(this, R.layout.activity_main, null);
		setContentView(rlMain);
		BitmapDrawable bg = getBg();
		if (bg != null) {
			rlMain.setBackground(bg);
		}
		View vSetting = findViewById(R.id.vSetting);
		spacing = ((RelativeLayout.LayoutParams) vSetting.getLayoutParams()).rightMargin;
		vSetting.setOnKeyListener(key);
		vSetting.setOnClickListener(click);
		
		refreshTime();
		
		apps = new ArrayList<App>();
		new Thread() {
			public void run() {
				genAppList();
			}
		}.start();
		
		ftp = new FTPServer();
		ftp.start(8848, "/sdcard");
		
		new Thread(){
			public void run() {
//				WirelessADB.start();
			}
		}.start();
	}
	
	private BitmapDrawable getBg() {
		try {
			String[] bgs = getAssets().list("bgs");
			Random rnd = new Random();
			String bgn = bgs[rnd.nextInt(bgs.length)];
			InputStream is = getAssets().open("bgs/" + bgn);
			BitmapDrawable bg = new  BitmapDrawable(getResources(), BitmapFactory.decodeStream(is));
			is.close();
			return bg;
		} catch (Throwable t) {
			TVLog.log(t);
		}
		return null;
	}
	
	private void refreshTime() {
		final TextView tvTime = (TextView) findViewById(R.id.tvTime);
		Runnable r = new Runnable() {
			private int count;
			
			public void run() {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				int formatRes = R.string.time_format_am;
				if (Calendar.PM == cal.get(Calendar.AM_PM)) {
					formatRes = R.string.time_format_pm;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat(getString(formatRes));
				tvTime.setText(sdf.format(cal.getTime()));
				
				count++;
				if (count == 300) {
					BitmapDrawable bg = getBg();
					if (bg != null) {
						findViewById(R.id.rlMain).setBackground(bg);
					}
					count = 0;
				}
				
				tvTime.postDelayed(this, 333);
			}
		};
		tvTime.post(r);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void genAppList() {
		final ArrayList<App> apps = new ArrayList<App>();
		List<PackageInfo> pis = getPackageManager().getInstalledPackages(0);
		for (PackageInfo pi : pis) {
			String packageName = pi.packageName;
			if (pi.applicationInfo.enabled && !packageName.equals(getPackageName()) 
					&& !"com.android.settings".equals(packageName)) {
				Intent iLaunch = getPackageManager().getLaunchIntentForPackage(pi.packageName);
				if (iLaunch != null) {
					App app = new App();
					app.iLaunch = iLaunch;
					app.packageName = pi.packageName;
					app.appName = pi.applicationInfo.loadLabel(getPackageManager()).toString();
					app.icon = pi.applicationInfo.loadIcon(getPackageManager());
					apps.add(app);
				}
			}
		}
		
		runOnUiThread(new Runnable() {
			public void run() {
				refreshUI(apps);
				startPackageReceiver();
			}
		});
	}
	
	private void refreshUI(ArrayList<App> list) {
		apps.clear(); 
		apps.addAll(list);
		LinearLayout llList = (LinearLayout) findViewById(R.id.llList);
		llList.removeAllViews();
		
		int width = ((View) llList.getParent()).getWidth() / COL_SIZE;
		int height = ((View) llList.getParent()).getHeight() / ROW_SIZE;
		int cCount = apps.size() / ROW_SIZE;
		cCount = (apps.size() % ROW_SIZE != 0) ? (cCount + 1) : cCount;
		
		View first = null;
		for (int i = 0; i < cCount; i++) {
			LinearLayout llRow = new LinearLayout(this);
			llRow.setOrientation(LinearLayout.VERTICAL);
			llList.addView(llRow, new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			for (int j = 0; j < ROW_SIZE; j++) {
				RelativeLayout rlItem = (RelativeLayout) View.inflate(this, R.layout.view_app_list_item, null);
				rlItem.setPadding(spacing / 2, spacing / 2, spacing / 2, spacing / 2);
				llRow.addView(rlItem, new LinearLayout.LayoutParams(width, height));
				if (i == 0 && j == 0) {
					first = rlItem;
				}
				
				if (i * ROW_SIZE + j >= apps.size()) {
					rlItem.setVisibility(View.INVISIBLE);
				} else {
					rlItem.setTag(apps.get(i * ROW_SIZE + j));
					rlItem.setOnKeyListener(key);
					rlItem.setOnClickListener(click);
					rlItem.setVisibility(View.VISIBLE);
					LinearLayout llItem = (LinearLayout) ((LinearLayout) rlItem.getChildAt(0)).getChildAt(0);
					refreshApp(apps.get(i * ROW_SIZE + j), llItem);
					rlItem.setOnFocusChangeListener(new OnFocusChangeListener() {
						public void onFocusChange(View v, boolean hasFocus) {
							RelativeLayout rl = (RelativeLayout) v;
							View v1 = rl.getChildAt(0);
							if (hasFocus) {
								v.setPadding(0, 0, 0, 0);
								v1.setBackgroundColor(0x8dffffff);
							} else {
								v.setPadding(spacing / 2, spacing / 2, spacing / 2, spacing / 2);
								v1.setBackgroundColor(0x2dffffff);
							}
						}
					});
				}
			}
		}
		
		if (first != null) {
			first.requestFocus();
		}
	}

	private void refreshApp(App app, LinearLayout llItem) {
		View vIcon = llItem.findViewById(R.id.vIcon);
		vIcon.setBackground(app.icon);
		
		TextView tvName = (TextView) llItem.findViewById(R.id.tvName);
		tvName.setText(app.appName);
	}
	
	private void startPackageReceiver() {
		if (receiver == null) {
			receiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					new Thread() {
						public void run() {
							genAppList();
						}
					}.start();
				}
			};
		}
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
		filter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		filter.addDataScheme("package");
		try {
			unregisterReceiver(receiver);
		} catch (Throwable t) {}
		registerReceiver(receiver, filter);
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (ftp != null) {
			ftp.quit();
		}
		new Thread(){
			public void run() {
//				WirelessADB.stop();
			}
		}.start();
	}
}
