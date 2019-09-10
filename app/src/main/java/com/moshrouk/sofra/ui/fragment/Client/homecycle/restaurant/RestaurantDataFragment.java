package com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.general.TabLayoutAdapter;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDataFragment extends Fragment {


    @BindView(R.id.restaurant_image)
    CircleImageView RestaurantImage;
    @BindView(R.id.restaurant_name)
    TextView RestaurantName;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.view_pag)
    ViewPager viewPag;
    Unbinder unbinder;
    TabLayoutAdapter tapLayoutAdapter;
    public static int restaurantId;
    private String restaurantName,restaurantImage;

    public RestaurantDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_data, container, false);
        unbinder = ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            restaurantId = bundle.getInt("id");
            restaurantName = bundle.getString("restaurantName");
            restaurantImage = bundle.getString("restaurantImage");
        }
        RestaurantName.setText(restaurantName);
        ImageViewExecution.LodeImage(getActivity(),restaurantImage,RestaurantImage,R.drawable.reslogo);

        initViewPager();
        return view;
    }

    private void initViewPager( ) {
        FoodMenuFragment foodMenuFragment = new FoodMenuFragment(restaurantId);
        CommentsRatingsFragment commentsRatingsFragment = new CommentsRatingsFragment(restaurantId);
        RestaurantInfoFragment restaurantInfoFragment = new RestaurantInfoFragment(restaurantId);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(foodMenuFragment);
        fragments.add(commentsRatingsFragment);
        fragments.add(restaurantInfoFragment);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("Food Menu");
        titles.add("Comments & Ratings");
        titles.add("Restaurant Info");




        tapLayoutAdapter = new TabLayoutAdapter(getFragmentManager(),fragments,titles);
        viewPag.setAdapter(tapLayoutAdapter);
        tab.setupWithViewPager(viewPag);

    }


}
