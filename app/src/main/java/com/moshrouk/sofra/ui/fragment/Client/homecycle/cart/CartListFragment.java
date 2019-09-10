package com.moshrouk.sofra.ui.fragment.Client.homecycle.cart;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.MyCartAdapter;
import com.moshrouk.sofra.data.local.AppDatabase;
import com.moshrouk.sofra.data.local.Item;
import com.moshrouk.sofra.ui.activity.client.ClientActivity;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.moshrouk.sofra.helper.HelperMethod.replace;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_LOGIN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadBoolean;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {


    @BindView(R.id.cart_list)
    RecyclerView cartList;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.sum_total)
    TextView sumTotal;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.more)
    Button more;
    @BindView(R.id.total)
    RelativeLayout total;
    Unbinder unbinder;

    private MyCartAdapter myCartAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Item> items = new ArrayList<>();
    AppDatabase appDatabase ;
    private Double total_t;
    public static int count_num;


    public CartListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        unbinder = ButterKnife.bind(this,view);
        cartList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        cartList.setLayoutManager(linearLayoutManager);
        myCartAdapter = new MyCartAdapter(items,getActivity());
        cartList.setAdapter(myCartAdapter);
        getCartItems();
        if (!appDatabase.getItemDAO().getItems().isEmpty()) {
            total.setVisibility(View.VISIBLE);
            sumTotal.setText(""+myCartAdapter.total);



        }

        return view;
    }

    private void getCartItems() {
        appDatabase  = AppDatabase.getAppDatabase(getContext());
        items.addAll(appDatabase.getItemDAO().getItems());
        myCartAdapter.notifyDataSetChanged();
    }



    @OnClick({R.id.confirm, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (!LoadBoolean(getActivity(), String.valueOf(CLIENT_LOGIN))) {
                    Intent Login = new Intent(getActivity(), ClientActivity.class);
                    startActivity(Login);
                }

                else {

                    Bundle bundle = new Bundle();
                    bundle.putDouble("Total", myCartAdapter.getTotal());
                    bundle.putInt("RestaurantId", myCartAdapter.RestaurantId);
                    CartOrderDetalisFragment cartOrderDetalisFragment = new CartOrderDetalisFragment();
                    replace(cartOrderDetalisFragment, getFragmentManager(), R.id.client_home_frame, null, null);
                    cartOrderDetalisFragment.setArguments(bundle);
                }
                break;
            case R.id.more:
                HomeFragment homeFragment = new HomeFragment();
                replace(homeFragment,getFragmentManager(),R.id.client_home_frame,null,null);
                break;
        }
    }
}
