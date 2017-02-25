package hssychargingpole.xpg.com.baidumapdemo.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hssychargingpole.xpg.com.baidumapdemo.R;


/**
 * @Description
 * @author Joke Huang
 * @createDate 2015年1月7日
 * @version 1.0.0
 */

public class BaseDialog extends Dialog implements
		View.OnClickListener {

	protected TextView title;
	protected TextView content;
	protected Button leftBtn;
	protected Button rightBtn;
	public BaseDialog(Context context) {
		/*
		 * 构造函数设置Dialog样式
		 */
		super(context, R.style.dialog_no_frame);
	}

	@Override
	public void setContentView(int layoutResId) {
		super.setContentView(layoutResId);
		findTitle(R.id.tv_title1);
//		findContent(R.id.tv_title);
		findLeftBtn(R.id.btn_cancel);
		findRightBtn(R.id.btn_ok);
		setLeftListener(this);
		setRightListener(this);
	}




	@Override
	public void onClick(View v) {
		dismiss();
	}

	public void findTitle(int resId) {
		View v = findViewById(resId);
		if (v != null && (v instanceof TextView))
			title = (TextView) v;
	}

	public void findContent(int resId) {
		View v = findViewById(resId);
		if (v != null && (v instanceof TextView))
			content = (TextView) v;
	}

	public void findLeftBtn(int resId) {
		View v = findViewById(resId);
		if (v != null && (v instanceof Button))
			leftBtn = (Button) v;
	}

	public void findRightBtn(int resId) {
		View v = findViewById(resId);
		if (v != null && (v instanceof Button))
			rightBtn = (Button) v;
	}

	public void setTitle(String str) {
		if (title != null)
			title.setText(str);
	}

	@Override
	public void setTitle(int resId) {
		if (title != null)
			title.setText(resId);
	}

	public void setContent(String str) {
		if (content != null)
			content.setText(str);
	}

	public void setContent(int resId) {
		if (content != null)
			content.setText(resId);
	}

	public void setLeftBtnText(String str) {
		if (leftBtn != null)
			leftBtn.setText(str);
	}

	public void setLeftBtnText(int resId) {
		if (leftBtn != null)
			leftBtn.setText(resId);
	}

	public void setRightBtnText(String str) {
		if (rightBtn != null)
			rightBtn.setText(str);
	}

	public void setRightBtnText(int resId) {
		if (rightBtn != null)
			rightBtn.setText(resId);
	}

	public void setLeftListener(View.OnClickListener listener) {
		if (leftBtn != null)
			leftBtn.setOnClickListener(listener);
	}

	public void setRightListener(View.OnClickListener listener) {
		if (rightBtn != null)
			rightBtn.setOnClickListener(listener);
	}
}
