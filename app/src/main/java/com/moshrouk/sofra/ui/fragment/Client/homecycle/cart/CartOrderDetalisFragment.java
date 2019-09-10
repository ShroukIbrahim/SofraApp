package com.moshrouk.sofra.ui.fragment.Client.homecycle.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.local.AppDatabase;
import com.moshrouk.sofra.data.model.client.neworder.NewOrder;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.helper.HelperMethod.replace;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_ADDRESS;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_NAME;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_PHONE;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartOrderDetalisFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.add_not)
    TextInputEditText addNot;
    @BindView(R.id.client_address)
    TextView clientAddress;
    @BindView(R.id.cash)
    RadioButton cash;
    @BindView(R.id.online)
    RadioButton online;
    @BindView(R.id.orderprice)
    TextView orderprice;
    @BindView(R.id.delivery_cost)
    TextView deliveryCost;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.confirm_order)
    Button confirmOrder;
    private int paymentMethods = 1;
    private View view;
    private boolean checked = false;

    private ClientApiServices apiServerClient = getClient().create(ClientApiServices.class);

    List<Integer> items = new ArrayList<>();
    List<Integer> quantities = new ArrayList<>();
    List<String> notes = new ArrayList<>();
    private AppDatabase database;
    private int  IdRestaurant;
    public static Double Total;

    public CartOrderDetalisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart_order_detalis, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientAddress.setText(LoadData(getActivity(), CLIENT_ADDRESS));

        Bundle bundle = getArguments();
        if (bundle != null) {
            Total = bundle.getDouble("Total");
            IdRestaurant = bundle.getInt("RestaurantId");
        }




        return view;
    }


    @OnClick({R.id.cash, R.id.online, R.id.confirm_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cash:
                if (checked)
                    paymentMethods = 2;
                break;
            case R.id.online:
                if (checked)
                    paymentMethods = 1;
                break;
            case R.id.confirm_order:
                sendOrder();
                break;
        }
    }

    private void sendOrder() {
        totalPrice.setText("" + Total);
        database = AppDatabase.getAppDatabase(getContext());
        for (int i = 0; i < database.getItemDAO().getItems().size(); i++) {
            items.add(database.getItemDAO().getItems().get(i).getIdItems());
            quantities.add(database.getItemDAO().getItems().get(i).getQuantity());
            notes.add(database.getItemDAO().getItems().get(i).getDescription());
        }

            apiServerClient.sendOrder(LoadData(getActivity(), CLIENT_API_TOKEN),
                    IdRestaurant, addNot.getText().toString(),
                    clientAddress.getText().toString(),
                    paymentMethods, LoadData(getActivity(), CLIENT_PHONE),
                    LoadData(getActivity(), CLIENT_NAME),
                    items,
                    quantities,
                    notes).enqueue(new Callback<NewOrder>() {
                @Override
                public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {
                    try {
                        if (response.body().equals(1)) {

                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            database.getItemDAO().deleteAll();

                            Bundle bundle = new Bundle();
                            bundle.putInt("id", response.body().getData().getOrder().getId());


                            CartOrderDetailssecondFragment cartOrderDetailssecondFragment = new CartOrderDetailssecondFragment();
                            cartOrderDetailssecondFragment.setArguments(bundle);

                            assert getFragmentManager() != null;
                            replace(cartOrderDetailssecondFragment, getFragmentManager(), R.id.client_home_frame, null, null);


                        }
                    } catch (Exception e) {
                        e.getMessage();

                    }

                }

                @Override
                public void onFailure(Call<NewOrder> call, Throwable t) {
                }
            });

        }

    }
