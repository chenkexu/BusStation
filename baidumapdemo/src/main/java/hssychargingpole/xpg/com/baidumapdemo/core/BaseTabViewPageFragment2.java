package hssychargingpole.xpg.com.baidumapdemo.core;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxhl.core.utils.L;

import java.util.ArrayList;
import java.util.List;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.adapter.ViewpagerAdapter;

/**
 * Created by lenovo on 2017/3/15.
 */
public abstract class BaseTabViewPageFragment2 extends BaseLazyFragment{


    private TabLayout tabLayout;

    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private List<String> tagList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList<>();
        tagList = new ArrayList<>();
    }

    @Override
    public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.core_tab_viewpager_layout, container, false);
        return view;
    }

    @Override
    public void builderView(View rootView) {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        initViewpagerFragmentList(fragmentList, tagList);

        /**
         * 注意了： 这里使用getChildFragmentManager()，而不是getSupportFragmentManager()
         * 原因是：BaseTabViewPageFragment显示在Fragment，如果在Fragment中再显示多个Fragment，得使用getChildFragmentManager()
         * getSupportFragmentManager()适用于Fragment显示有Activity中切换
         */
//        ViewpagerAdapter adapter = new ViewpagerAdapter(
//                getActivity().getSupportFragmentManager(), fragmentList, tagList);

        ViewpagerAdapter adapter = new ViewpagerAdapter(
                getChildFragmentManager(), fragmentList, tagList);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 值为TabLayout.GRAVITY_FILL 或 TabLayout.GRAVITY_CENTER
     * @param gravity
     */
    protected void setTabGravity(int gravity){
        tabLayout.setTabGravity(gravity);
    }

    /**
     * TabLayout.MODE_FIXED 或TabLayout.MODE_SCROLLABLE
     * @param mode
     */
    protected void setTabMode(int mode){
        tabLayout.setTabMode(mode);
    }

    protected abstract void initViewpagerFragmentList(List<Fragment> fragmentList, List<String> tagList);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("----------------------->>.");
    }
}