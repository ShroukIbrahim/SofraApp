package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.TapLayoutAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ROrderListFragment extends Fragment {


    @BindView(R.id.order_fragment_tab)
    TabLayout orderFragmentTab;
    @BindView(R.id.order_fragment_pag)
    ViewPager orderFragmentPag;
    Unbinder unbinder;
    TapLayoutAdapter tapLayoutAdapter;

    public ROrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restarunt_order_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        tapLayoutAdapter = new TapLayoutAdapter(getActivity().getSupportFragmentManager());
        orderFragmentPag.setAdapter(tapLayoutAdapter);
        orderFragmentTab.setupWithViewPager(orderFragmentPag);

        return view;
    }

}
