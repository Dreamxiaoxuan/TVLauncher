package m.tvlauncher.remotecontroler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TableView extends LinearLayout {
	public TableView(Context context) {
		super(context);
	}
	
	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void initTable(int rows, int lines) {
		setOrientation(VERTICAL);
		setWeightSum(lines);
		LayoutParams lpLine = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpLine.weight = 1;
		LayoutParams lpRow = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpRow.weight = 1;

		for (int i = 0; i < lines; i++) {
			LinearLayout llLine = new LinearLayout(getContext());
			llLine.setId(i);
			llLine.setWeightSum(rows);
			addView(llLine, lpLine);
			for (int j = 0; j < rows; j++) {
				LinearLayout llRow = new LinearLayout(getContext());
				llRow.setId(j);
				llLine.addView(llRow, lpRow);
			}
		}
	}

	public LinearLayout getCell(int row, int line) {
		LinearLayout llLine = (LinearLayout) getChildAt(line);
		return (LinearLayout) llLine.getChildAt(row);
	}
	
}
