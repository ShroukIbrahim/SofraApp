package com.moshrouk.sofra.ui.fragment.Restaurant.restaurantcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPasswordFragment extends Fragment {


    @BindView(R.id.fragment_new_password_validation_code)
    TextInputEditText ValidationCode;
    @BindView(R.id.fragment_new_password_password)
    TextInputEditText NewPassword;
    @BindView(R.id.fragment_new_password_cpassword)
    TextInputEditText Cpassword;
    @BindView(R.id.fragment_forget_password_change_password)
    Button ChangePassword;
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    Unbinder unbinder;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;

    public NewPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void ChangePassword() {
        ProgressBar.setVisibility(View.VISIBLE);
        String validationCode = ValidationCode.getText().toString();
        String password = NewPassword.getText().toString();
        String cpassword = Cpassword.getText().toString();
        restaurantApiServices.newPassword(validationCode, password, cpassword).enqueue(new Callback<com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword>() {
            @Override
            public void onResponse(Call<com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword> call, Response<com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword> response) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                    ProgressBar.setVisibility(View.GONE);
                } else
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.moshrouk.sofra.data.model.general.password.newpassword.NewPassword> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.fragment_forget_password_change_password)
    public void onViewClicked() {
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });
    }


}
