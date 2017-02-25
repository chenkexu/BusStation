package hssychargingpole.xpg.com.baidumapdemo.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hssychargingpole.xpg.com.baidumapdemo.MyApplication;


/**
 * Created by CaiBingZhang on 15/9/5.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;


    /**
     * 在Fragment获取Activity
     * @return
     */
    protected AbstractActivity getAbstractActivity(){
        return (AbstractActivity)getActivity();
    }

    /**
     * 获取项目中Application
     * @return
     */
    public MyApplication getMyApplication(){
        return getAbstractActivity().getMyApplication();
    }


    /**
     * onCreateView返回的就是fragment要显示的view。
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView = getRootView(inflater,container,savedInstanceState);

            builderView(rootView);
        }else{
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
                p.removeAllViewsInLayout();
            }
        }
        return rootView;
    }

    public abstract View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void builderView(View rootView);

}
