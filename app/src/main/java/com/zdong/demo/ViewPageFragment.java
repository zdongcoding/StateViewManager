package com.zdong.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class ViewPageFragment extends Fragment {

    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            Map<Integer,Fragment> fragments = new HashMap();
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = fragments.get(position);
                if (fragment==null) {
                    fragments.put(position, DemoFragment.Instance.INSTANCE.newInstance(position));
                }
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }
}
