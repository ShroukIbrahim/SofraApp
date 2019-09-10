package com.moshrouk.sofra.ui.fragment.Restaurant.restaurantcycle;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.model.restaurant.restaurantlogin.RestaurantLogin;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.activity.Restaurant.RestaurantHomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.SaveData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_EMAIL;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_NAME;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.LoginFragment_Progress_Bar)
    ProgressBar LoginFragmentProgressBar;
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    @BindView(R.id.fragment_login_email)
    TextInputEditText LoginEmail;
    @BindView(R.id.fragment_login_password)
    TextInputEditText LoginPassword;
    @BindView(R.id.fragment_login_forget_password)
    TextView ForgetPassword;
    @BindView(R.id.fragment_login_login)
    Button Login;
    @BindView(R.id.fragment_login_open_register)
    Button OpenRegister;
    Unbinder unbinder;
    private String msg;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        dataUserShrPreferences();
        return view;
    }

    private void dataUserShrPreferences() {
        LoginEmail.setText(LoadData(getActivity(), USER_EMAIL));
        LoginPassword.setText(LoadData(getActivity(), USER_PASSWORD));
    }

        public void login() {
        LoginFragmentProgressBar.setVisibility(View.VISIBLE);
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();
        restaurantApiServices.restaurantLogin(email, password).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                if (response.body().getStatus()==-1) {
                    SaveData(getActivity(), USER_API_TOKEN, response.body().getData().getApiToken());
                    SaveData(getActivity(), USER_EMAIL, response.body().getData().getUser().getEmail());
                    SaveData(getActivity(), USER_PASSWORD, LoginPassword.getText().toString());
                    SaveData(getActivity(), USER_NAME, response.body().getData().getUser().getName());
                    Intent Login = new Intent(getActivity(), RestaurantHomeActivity.class);
                    startActivity(Login);
                    RegisterToken();
                    LoginFragmentProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });


    }
    // Register Token
    public void RegisterToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                         msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });


        generalApiServices.restaurantRegisterToken(msg, LoadData(getActivity(), USER_API_TOKEN),
                "android").enqueue(new Callback<NotificationsToken>() {
            @Override
            public void onResponse(Call<NotificationsToken> call, Response<NotificationsToken> response) {

                try {
                    if (response.body().getStatus().equals(1)) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<NotificationsToken> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick({R.id.fragment_login_forget_password, R.id.fragment_login_login, R.id.fragment_login_open_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_forget_password:
                ForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                        HelperMethod.replace(resetPasswordFragment, getFragmentManager(), R.id.resturant_cycle, null, null);
                    }
                });
                break;
            case R.id.fragment_login_login:
                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        login();
                    }
                });

                break;
            case R.id.fragment_login_open_register:
                OpenRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RegisterFragment registerFragment = new RegisterFragment();
                        HelperMethod.replace(registerFragment, getFragmentManager(), R.id.resturant_cycle, null, null);

                    }
                });

                break;
        }
    }
}
