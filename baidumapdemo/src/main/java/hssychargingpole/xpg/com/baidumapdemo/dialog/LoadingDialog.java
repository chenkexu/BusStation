package hssychargingpole.xpg.com.baidumapdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import hssychargingpole.xpg.com.baidumapdemo.R;


/**
 * @author Mazoh
 * @version 2.0.4
 * @Description
 * @createDate 2015年9月6日
 */

public class LoadingDialog extends Dialog {

	private AnimationDrawable adAnimationDrawable = null;
	private boolean cancelBackPress;

	public LoadingDialog(Context context, int msgId) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		setContentView(R.layout.loading_dialog);
		ImageView iv = (ImageView) findViewById(R.id.iv);
		adAnimationDrawable = (AnimationDrawable) iv.getBackground();
		adAnimationDrawable.start();
		((TextView) findViewById(R.id.tv)).setText(msgId);
		setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, int msgId, boolean cancelBackPress) {
		super(context,android.R.style.Theme_Translucent_NoTitleBar);
		setContentView(R.layout.loading_dialog);
		ImageView iv = (ImageView) findViewById(R.id.iv);
		adAnimationDrawable = (AnimationDrawable) iv.getBackground();
		adAnimationDrawable.start();
		((TextView) findViewById(R.id.tv)).setText(msgId);
		setCanceledOnTouchOutside(false);
		this.cancelBackPress = cancelBackPress;
	}

	@Override
	public void onBackPressed() {
		if(!cancelBackPress)
			super.onBackPressed();
	}

	public void setMsg(int msgId){
		((TextView) findViewById(R.id.tv)).setText(msgId);
	}

	public void showDialog() {
		try {
			show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dismiss() {
		try {
			super.dismiss();
			if (adAnimationDrawable != null) {
				adAnimationDrawable.stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
