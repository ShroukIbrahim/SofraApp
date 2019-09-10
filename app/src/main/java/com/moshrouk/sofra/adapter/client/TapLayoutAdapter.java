package com.moshrouk.sofra.adapter.client;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moshrouk.sofra.ui.fragment.Client.homecycle.ClientCurrentOrderFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.ClientPreviousOrderFragment;


public class TapLayoutAdapter extends FragmentPagerAdapter {

    String[] tabarray = new String[]{"Current Order" ,"Previous Order",};
    Integer tabnumber=2;


    public TapLayoutAdapter( FragmentManager fm ) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle( int position  ) {
        return tabarray [position];
    }


    @Override
    public Fragment getItem( int position)
    {
        switch (position)
        {
            case 0:
                ClientCurrentOrderFragment clientCurrentOrderFragment = new ClientCurrentOrderFragment();
                return  clientCurrentOrderFragment;
            case 1:
                ClientPreviousOrderFragment clientPreviousOrderFragment = new ClientPreviousOrderFragment();
                return  clientPreviousOrderFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}


