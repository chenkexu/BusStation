package hssychargingpole.xpg.com.baidumapdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wxhl.core.utils.CoreUtil;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.core.AbstractActivity;
import hssychargingpole.xpg.com.baidumapdemo.fragment.FragmentContact;
import hssychargingpole.xpg.com.baidumapdemo.fragment.FragmentFind;
import hssychargingpole.xpg.com.baidumapdemo.fragment.FragmentMe2;
import hssychargingpole.xpg.com.baidumapdemo.fragment.FragmentWeixin;

public class MainActivity extends AbstractActivity {
    private int index = 0;
    private Fragment[] fragments;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private String[] titles = {"微信","通讯录","发现","我的"};
    private long start;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void widgetClick(View v) {
            switch (v.getId()){
                case R.id.fab:
                    startActivityNetWork(SignActivity.class);
                    break;
            }
    }

    @Override
    protected void init() {

        setActionBarTitle("主页面");
        getToolbar().setLogo(R.mipmap.ic_launcher);
//        setActionBarTitle("");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        floatingActionButton.setOnClickListener(this);

        fragments = new Fragment[4];
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int location) {
                Fragment fragment = fragments[location];
                if (fragment == null) {
                    switch (location) {
                        case 0:
                            fragments[location] = new FragmentWeixin();
                            break;
                        case 1:
                            fragments[location] = new FragmentContact();
                            break;
                        case 2:
                            fragments[location] = new FragmentFind();
                            break;
                        case 3:
                            fragments[location] = new FragmentMe2();
                            break;
                        default:
                            break;
                    }
                }
                return fragments[location];
            }

            @Override
            public int getCount() {

                return fragments.length;
            }
        };

        //viewPager的數據源
        viewPager.setAdapter(fragmentPagerAdapter);

        //viewPager的监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                RadioButton rb_elect = (RadioButton) radioGroup.getChildAt(position);
                rb_elect.setChecked(true);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        //radioGroup的改变的监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_weixin:
                        index = 0;
                        break;
                    case R.id.rb_contact:
                        index = 1;
                        break;
                    case R.id.rb_find:
                        index = 2;
                        break;
                    case R.id.rb_me:
                        index = 3;
                        break;
                    default:
                        break;
                }
                viewPager.setCurrentItem(index,false);
        }
            });
     }


    /**
     * 再按一次退出应用
     */
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - start < 2000) {
            //退出整个应用
            CoreUtil.exitApp(this);
        }else{
            Toast.makeText(this, "请再按一次退出应用", Toast.LENGTH_SHORT).show();
            start = System.currentTimeMillis();
        }
    }
}
