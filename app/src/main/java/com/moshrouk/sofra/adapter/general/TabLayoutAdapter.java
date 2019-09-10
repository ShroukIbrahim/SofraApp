package com.moshrouk.sofra.adapter.general;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    ArrayList<String> fragmentTittle;

    public TabLayoutAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> fragmentTittle) {
        super(fm);
        this.fragments = fragments;
        this.fragmentTittle = fragmentTittle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle( int position  ) {
        return fragmentTittle.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size() ;
    }
}





