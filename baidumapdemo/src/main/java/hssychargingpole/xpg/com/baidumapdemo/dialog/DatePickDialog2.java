package hssychargingpole.xpg.com.baidumapdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.view.widget.OnWheelChangedListener;
import hssychargingpole.xpg.com.baidumapdemo.view.widget.WheelView;
import hssychargingpole.xpg.com.baidumapdemo.view.widget.adapters.NumericWheelAdapter;


/**
 * @Description
 * @author Joke Huang
 * @createDate 2015年1月6日
 * @version 1.0.0
 */

public class DatePickDialog2 extends Dialog implements OnClickListener,
		OnWheelChangedListener {

	private static final int START_YEAR = 2016;

	public static interface OnChangedListener {
		void onChanged(DatePickDialog2 tpd, int newYear, int newMonth,
					   int newDay, int oldYear, int oldMonth, int oldDay);
	}

	public static interface OnOkListener {
		void onOk(DatePickDialog2 tpd, int year, int month, int day);
	}

	private TextView tv_title;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private NumericWheelAdapter nwaYear;
	private NumericWheelAdapter nwaMonth;
	private NumericWheelAdapter nwaDay28;
	private NumericWheelAdapter nwaDay29;
	private NumericWheelAdapter nwaDay30;
	private NumericWheelAdapter nwaDay31;
	private OnChangedListener onChangedListener;
	private OnOkListener onOkListener;
	private View.OnClickListener onCacelListener;
	private ImageView cancel;
	private Button btn_ok;

	public DatePickDialog2(Context context) {
		super(context, R.style.dialog_no_frame);
		init(context);
		setTime(System.currentTimeMillis());
	}

	private void init(Context context) {
		setContentView(R.layout.dialog_date_picker2);

		tv_title = (TextView) findViewById(R.id.tv_title);

		nwaYear = new NumericWheelAdapter(context, START_YEAR, 2100, "%02d");
		nwaMonth = new NumericWheelAdapter(context, 1, 12, "%02d");
		nwaDay28 = new NumericWheelAdapter(context, 1, 28, "%02d");
		nwaDay29 = new NumericWheelAdapter(context, 1, 29, "%02d");
		nwaDay30 = new NumericWheelAdapter(context, 1, 30, "%02d");
		nwaDay31 = new NumericWheelAdapter(context, 1, 31, "%02d");
		nwaYear.setItemResource(R.layout.wheel_text_item_mid);
		nwaMonth.setItemResource(R.layout.wheel_text_item_mid);
		nwaDay28.setItemResource(R.layout.wheel_text_item_mid);
		nwaDay29.setItemResource(R.layout.wheel_text_item_mid);
		nwaDay30.setItemResource(R.layout.wheel_text_item_mid);
		nwaDay31.setItemResource(R.layout.wheel_text_item_mid);
		nwaYear.setItemTextResource(R.id.tv1);
		nwaMonth.setItemTextResource(R.id.tv1);
		nwaDay28.setItemTextResource(R.id.tv1);
		nwaDay29.setItemTextResource(R.id.tv1);
		nwaDay30.setItemTextResource(R.id.tv1);
		nwaDay31.setItemTextResource(R.id.tv1);
		nwaYear.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaMonth.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaDay28.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaDay29.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaDay30.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaDay31.setTextColor(context.getResources().getColor(R.color.text_black));
		nwaYear.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));
		nwaMonth.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));
		nwaDay28.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));
		nwaDay29.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));
		nwaDay30.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));
		nwaDay31.setTextColorUnselect(context.getResources().getColor(
				R.color.text_gray_light));

		wv_year = (WheelView) findViewById(R.id.wv_year);
		wv_month = (WheelView) findViewById(R.id.wv_month);
		wv_day = (WheelView) findViewById(R.id.wv_day);
		wv_year.setViewAdapter(nwaYear);
		wv_month.setViewAdapter(nwaMonth);
		// wv_day.setViewAdapter(nwaDay31);
		updateDayAdapter();
		wv_year.setVisibleItems(5);
		wv_month.setVisibleItems(5);
		wv_day.setVisibleItems(5);
		wv_year.setBackgroundVisible(false);
		wv_month.setBackgroundVisible(false);
		wv_day.setBackgroundVisible(false);
		wv_year.setForegroundVisible(false);
		wv_month.setForegroundVisible(false);
		wv_day.setForegroundVisible(false);
		wv_year.setDrawShadows(false);
		wv_month.setDrawShadows(false);
		wv_day.setDrawShadows(false);
		wv_year.setCyclic(true);
		wv_month.setCyclic(true);
		wv_day.setCyclic(true);
		wv_year.addChangingListener(this);
		wv_month.addChangingListener(this);
		wv_day.addChangingListener(this);

		cancel = (ImageView) findViewById(R.id.cancel);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		cancel.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {

		if (wheel == wv_year) {
			updateDayAdapter();
			if (onChangedListener != null) {
				onChangedListener.onChanged(this, newValue + START_YEAR,
						getMonth(), getDay(), oldValue + START_YEAR,
						getMonth(), getDay());
			}
			return;
		}
		if (wheel == wv_month) {
			updateDayAdapter();
			if (onChangedListener != null) {
				onChangedListener.onChanged(this, getYear(), newValue + 1,
						getDay(), getYear(), oldValue + 1, getDay());
			}
			return;
		}
		if (wheel == wv_day) {
			if (onChangedListener != null) {
				onChangedListener.onChanged(this, getYear(), getMonth(),
						newValue + 1, getYear(), getMonth(), oldValue + 1);
			}
			return;
		}
	}

	private void updateDayAdapter() {
		Calendar c = Calendar.getInstance();
		c.set(getYear(), getMonth() - 1, 1);
		int oldDayIndex = wv_day.getCurrentItem();
		int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		switch (maxDayOfMonth) {
		case 28:
			wv_day.setViewAdapter(nwaDay28);
			break;
		case 29:
			wv_day.setViewAdapter(nwaDay29);
			break;
		case 30:
			wv_day.setViewAdapter(nwaDay30);
			break;
		case 31:
			wv_day.setViewAdapter(nwaDay31);
			break;
		default:
			break;
		}
		if (oldDayIndex > wv_day.getViewAdapter().getItemsCount() - 1) {
			wv_day.setCurrentItem(wv_day.getViewAdapter().getItemsCount() - 1);
		} else {
			wv_day.setCurrentItem(oldDayIndex);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel:
			dismiss();
			break;
		case R.id.btn_ok:
			dismiss();
			if (onOkListener != null)
				onOkListener.onOk(this, getYear(), getMonth(), getDay());
			break;

		default:
			break;
		}
	}

	public void setTitle(String title) {
		tv_title.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		tv_title.setText(titleId);
	}

	public int getYear() {
		return wv_year.getCurrentItem() + START_YEAR;
	}

	public int getMonth() {
		return wv_month.getCurrentItem() + 1;
	}

	public int getDay() {
		return wv_day.getCurrentItem() + 1;
	}

	public void setTime(int year, int month, int day) {
		wv_year.setCurrentItem(year >= START_YEAR ? year - START_YEAR
				: START_YEAR);
		wv_month.setCurrentItem(month - 1);
		updateDayAdapter();
		wv_day.setCurrentItem(day - 1);
	}

	public void setTime(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		setTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH));
	}

	public OnChangedListener getOnChangedListener() {
		return onChangedListener;
	}

	public void setOnChangedListener(OnChangedListener onChangedListener) {
		this.onChangedListener = onChangedListener;
	}

	public OnOkListener getOnOkListener() {
		return onOkListener;
	}

	public void setOnOkListener(OnOkListener onOkListener) {
		this.onOkListener = onOkListener;
	}

}
