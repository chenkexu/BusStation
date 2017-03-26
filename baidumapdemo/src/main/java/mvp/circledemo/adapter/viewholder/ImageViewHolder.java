package mvp.circledemo.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;

import hssychargingpole.xpg.com.baidumapdemo.R;
import mvp.circledemo.widgets.MultiImageView;



/**
 * Created by suneee on 2016/8/16.
 *
 * 图片的Viewholder
 */
public class ImageViewHolder extends CircleViewHolder {
    /** 图片*/
    public MultiImageView multiImageView;

    public ImageViewHolder(View itemView){
        super(itemView, TYPE_IMAGE);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View subView = viewStub.inflate();
        MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
        if(multiImageView != null){
            this.multiImageView = multiImageView;
        }
    }


}
