package hssychargingpole.xpg.com.baidumapdemo.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class FixHeightListView extends ListView {

	public FixHeightListView(Context context) {
		super(context);
	}

	public FixHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixHeightListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec;
		if (!isInEditMode()) {
			expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		} else {
			expandSpec = heightMeasureSpec;
		}
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	public void setSelector(int resID) {
		super.setSelector(R.color.transparent);
	}

}
