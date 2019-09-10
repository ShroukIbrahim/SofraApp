package com.moshrouk.sofra.ui.fragment.Client.clientcycle;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.moshrouk.sofra.data.model.client.clientlogin.ClientLogin;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.cart.CartOrderDetalisFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.helper.HelperMethod.replace;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_ADDRESS;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_EMAIL;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_LOGIN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_NAME;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_PASSWORD;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);

    Unbinder unbinder;
    @BindView(R.id.fragment_login_email)
    TextInputEditText ClientEmail;
    @BindView(R.id.fragment_login_password)
    TextInputEditText ClientPassword;
    @BindView(R.id.fragment_login_forget_password)
    TextView ClientForgetPassword;
    @BindView(R.id.fragment_login_login)
    Button ClientLogin;
    @BindView(R.id.fragment_login_open_register)
    Button ClientRegister;
    @BindView(R.id.LoginFragment_Progress_Bar)
    android.widget.ProgressBar ProgressBar;
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
        ClientEmail.setText(LoadData(getActivity(), CLIENT_EMAIL));
        ClientPassword.setText(LoadData(getActivity(), CLIENT_PASSWORD));
    }

    public void login() {
        ProgressBar.setVisibility(View.VISIBLE);
        String email = ClientEmail.getText().toString();
        String password = ClientPassword.getText().toString();
        clientApiServices.clientLogin(email, password).enqueue(new Callback<ClientLogin>() {
            @Override
            public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                if (response.body().getStatus()==1) {
                    SaveData(getActivity(), CLIENT_API_TOKEN, response.body().getData().getApiToken());
                    SaveData(getActivity(), CLIENT_EMAIL, response.body().getData().getClient().getEmail());
                    SaveData(getActivity(), CLIENT_PASSWORD, ClientPassword.getText().toString());
                    SaveData(getActivity(), CLIENT_NAME, response.body().getData().getClient().getName());
                    SaveData(getActivity(),CLIENT_ADDRESS,response.body().getData().getClient().getAddress()+"-"
                           + response.body().getData().getClient().getRegion().getName()+"-"
                           + response.body().getData().getClient().getRegion().getCity().getName());
                    CartOrderDetalisFragment cartOrderDetalisFragment = new CartOrderDetalisFragment();
                    replace(cartOrderDetalisFragment,getFragmentManager(),R.id.client_home_frame,null,null);

//                    Intent Login = new Intent(getActivity(), ClientHomeActivity.class);
//                    startActivity(Login);
                    SaveData(getActivity(),CLIENT_LOGIN,true);
                    RegisterToken();
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<ClientLogin> call, Throwable t) {

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


        generalApiServices.clientRegisterToken(msg, LoadData(getActivity(), CLIENT_API_TOKEN),
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
                ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                replace(resetPasswordFragment, getFragmentManager(), R.id.activity_usercycle, null, null);

                break;
            case R.id.fragment_login_login:
                login();
                break;
            case R.id.fragment_login_open_register:
                RegisterFragment registerFragment = new RegisterFragment();
                replace(registerFragment, getFragmentManager(), R.id.activity_usercycle, null, null);
                break;
        }
    }
}
