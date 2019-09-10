package com.moshrouk.sofra.ui.fragment.Client.homecycle.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.ItemOfOrderAdapter;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Item;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartOrderDetailssecondFragment extends Fragment {


    @BindView(R.id.restaurant_image)
    CircleImageView restaurantImage;
    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.order_address)
    TextView orderAddress;
    @BindView(R.id.items_list)
    RecyclerView itemsList;
    @BindView(R.id.orderprice)
    TextView orderprice;
    @BindView(R.id.delivery_cost)
    TextView deliveryCost;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.paymentby)
    TextView paymentby;
    private Unbinder unbinder;
    private int orderId;

    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private int PaymentMethod;
    private List<Item> itmes = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    ItemOfOrderAdapter itemOfOrderAdapter;


    public CartOrderDetailssecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_order_detailssecond, container, false);
        unbinder = ButterKnife.bind(this, view);
        itemsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        itemsList.setLayoutManager(layoutManager);
        itemOfOrderAdapter = new ItemOfOrderAdapter(getActivity(), itmes);
        itemsList.setAdapter(itemOfOrderAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            orderId = bundle.getInt("id");

        }
        showOrder();
        return view;

    }

    private void showOrder() {
        restaurantApiServices.showOrder(LoadData(getActivity(),CLIENT_API_TOKEN),orderId)
                .enqueue(new Callback<OrderData>() {
                    @Override
                    public void onResponse(Call<OrderData> call, Response<OrderData> response) {
                        if (response.isSuccessful()) {
                            restaurantName.setText(response.body().getRestaurant().getName());
                            ImageViewExecution.LodeImage(getActivity(),response.body().getRestaurant().getPhotoUrl()
                                    ,restaurantImage,R.drawable.reslogo);
                            orderAddress.setText(response.body().getClient().getAddress());
                            PaymentMethod=Integer.valueOf(response.body().getPaymentMethodId());
                            if (PaymentMethod==1) {
                                paymentby.setText("Cash");

                            }
                            else
                                paymentby.setText("Online");

                            totalPrice.setText(response.body().getTotal());
                            deliveryCost.setText(response.body().getDeliveryCost());
                            orderprice.setText(response.body().getCost());
                            itmes.addAll(response.body().getItems());
                            itemOfOrderAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(Call<OrderData> call, Throwable t) {

                    }
                });
    }


}

