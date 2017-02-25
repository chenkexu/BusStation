package com.wxhl.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.wxhl.core.R;

import static com.wxhl.core.R.id.tv_tip_msg;

public class TipInfoLayout extends FrameLayout {
    private ProgressWheel mPbProgressBar;
    private ImageView mTvTipState;
    private TextView mTvTipMsg;

    private Context context;
    private Button reset;

    public TipInfoLayout(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.core_tip_info_layout, null, false);
        mPbProgressBar = (ProgressWheel) view.findViewById(R.id.tv_tip_loading);//滚动条
        mTvTipState = (ImageView) view.findViewById(R.id.tv_tip_state);
        mTvTipMsg = (TextView) view.findViewById(tv_tip_msg);//滚动条的文字信息
        reset = (Button) view.findViewById(R.id.btn_reset_first);
        completeLoading();
        addView(view);


    }

    /*public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }*/

    public void setLoading() {
        setLoading(context.getString(R.string.tip_loading));
    }

    /**
     * 正在加载
     * @param message
     */
    public void setLoading(String message) {
        //this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.reset.setVisibility(View.GONE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_loading));
    }

    /**
     * 完成加载
     */
    public void completeLoading(){
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
        this.reset.setVisibility(View.GONE);
    }

    public void setNetworkError() {
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_network);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_network_error));
        this.reset.setVisibility(View.VISIBLE);
    }

    public void setLoadError(String message){
        setLoadError();
        this.mTvTipMsg.setText(message);
    }

    public void setLoadError() {
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_loaderror);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_error));
    }

    public void setEmptyData(String message){
        setEmptyData();
        this.mTvTipMsg.setText(message);
    }

    public void setEmptyData() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_empty);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_empty));
    }

    /**
     * 不显示任何提示
     */
    public void setCancelShow(){
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
    }

    public Button getResetBtn() {
        return reset;
    }
}
