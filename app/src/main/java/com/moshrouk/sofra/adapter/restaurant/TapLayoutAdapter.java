package com.moshrouk.sofra.adapter.restaurant;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RCurrentOrderFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RNewOrderFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RPreviousOrderFragment;

public class TapLayoutAdapter extends FragmentPagerAdapter {

    String[] tabarray = new String[]{"New Order" ,"Current Order","Previous Order"};
    Integer tabnumber = 3;


    public TapLayoutAdapter(FragmentManager fm ) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position  ) {
        return tabarray [position];
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                RNewOrderFragment rNewOrderFragment = new RNewOrderFragment();
                return  rNewOrderFragment;
            case 1:
                RCurrentOrderFragment rCurrentOrderFragment = new RCurrentOrderFragment();
                return  rCurrentOrderFragment;
            case 2:
                RPreviousOrderFragment rPreviousOrderFragment = new RPreviousOrderFragment();
                return rPreviousOrderFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}


