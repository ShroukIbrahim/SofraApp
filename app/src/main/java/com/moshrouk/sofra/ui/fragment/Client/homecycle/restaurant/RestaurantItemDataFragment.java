package com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.RestaurantListAdapter;
import com.moshrouk.sofra.data.local.AppDatabase;
import com.moshrouk.sofra.data.local.Item;
import com.moshrouk.sofra.helper.ImageViewExecution;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantItemDataFragment extends Fragment {


    @BindView(R.id.restaurant_item_image)
    ImageView restaurantItemImage;
    @BindView(R.id.restaurant_item_name)
    TextView restaurantItemName;
    @BindView(R.id.item_description)
    TextView itemDescription;
    @BindView(R.id.price_of_item)
    TextView priceOfItem;
    @BindView(R.id.preparing_time)
    TextView preparingTime;
    @BindView(R.id.special_order)
    TextInputEditText specialOrder;
    @BindView(R.id.increase)
    FloatingActionButton increase;
    @BindView(R.id.quantity_num)
    TextView quantityNum;
    @BindView(R.id.decrease)
    FloatingActionButton decrease;
    @BindView(R.id.add_cart)
    FloatingActionButton addCart;
    Unbinder unbinder;
    private int Quantity, itemId;
    private String restaurantName, restaurantImage, restaurantItemDescription, PreparingTime, restaurantItemPrice;
    private int idItem;
    private RestaurantListAdapter restaurantListAdapter;
    private int quantity;
    private AppDatabase database;
    private boolean checkItems = true;
    private boolean checkIdResruarant = false;
    private int restaurantId = restaurantListAdapter.Rid;


    public RestaurantItemDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_item_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            itemId = bundle.getInt("id");
            //restaurantId = bundle.getString("restaurantId");
            restaurantName = bundle.getString("restaurantName");
            restaurantImage = bundle.getString("restaurantImage");
            restaurantItemDescription = bundle.getString("restaurantItemDescription");
            PreparingTime = bundle.getString("PreparingTime");
            restaurantItemPrice = bundle.getString("restaurantItemPrice");

        }
        getItemData();
        return view;
    }

    private void getItemData() {
        ImageViewExecution.LodeImage(getActivity(),
                restaurantImage,
                restaurantItemImage, R.drawable.pizzamin);
        restaurantItemName.setText(restaurantName);
        itemDescription.setText(restaurantItemDescription);
        priceOfItem.setText(restaurantItemPrice);
        preparingTime.setText(PreparingTime);

    }

    @OnClick({R.id.increase, R.id.decrease, R.id.add_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.increase:
                quantity = Integer.parseInt(quantityNum.getText().toString());
                quantity++;
                quantityNum.setText("" + quantity);

                break;
            case R.id.decrease:
                quantity = Integer.parseInt(quantityNum.getText().toString());
                if (quantity != 1) {
                    quantity--;
                    quantityNum.setText("" + quantity);
                }
                break;
            case R.id.add_cart:

                database = AppDatabase.getAppDatabase(getContext());
                final Item item = new Item((restaurantId), idItem, restaurantName,
                        restaurantItemDescription,
                        Integer.valueOf(quantityNum.getText().toString()),
                        restaurantImage, restaurantItemPrice, 0);

                if (database.getItemDAO().getItems().size() == 0) {
                    database.getItemDAO().insert(item);
                    return;
                }

                if (database.getItemDAO().getItemByIdRestaurant().size() == 0) {
                    database.getItemDAO().insert(item);
                    return;
                }

                for (int i = 0; i < database.getItemDAO().getItems().size(); i++) {

                    if (database.getItemDAO().getItems().get(i).getIdRestaurant().equals(restaurantId)) {

                        checkIdResruarant = true;
                    }
                }

                if (checkIdResruarant) {

                    for (int j = 0; j < database.getItemDAO().getItems().size(); j++) {
                           if (database.getItemDAO().getItems().get(j).getIdItems().equals(idItem)) {
                            checkItems = false;
                        }
                    }
                    checkIdResruarant = false;
                } else {

                    checkItems = false;
                    if (database.getItemDAO().getItems().size() != 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("choose anther restaurant");
                        builder.setMessage("Delete All Items");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                database.getItemDAO().deleteAll();

                                database.getItemDAO().insert(item);

                                return;
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.show();
                    } else {
                        database.getItemDAO().insert(item);

                    }
                }

                if (checkItems) {

                    database.getItemDAO().insert(item);
                    checkItems = false;

                    getActivity().onBackPressed();

                } else {

                    database.getItemDAO().update(idItem, Integer.valueOf(quantityNum.getText().toString()));

                    Toast.makeText(getContext(), "Add Successful ", Toast.LENGTH_LONG).show();
                    checkItems = true;
                }
                break;
        }
    }
}
