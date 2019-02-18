package com.zdong.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class ViewPageFragment extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_layout);
        viewPager=findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            Map<Integer,Fragment> fragments = new HashMap();
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragments.get(position);
                if (fragment==null) {
                    fragment = DemoFragment.Instance.INSTANCE.newInstance(position);
                    fragments.put(position, fragment);
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }
}
