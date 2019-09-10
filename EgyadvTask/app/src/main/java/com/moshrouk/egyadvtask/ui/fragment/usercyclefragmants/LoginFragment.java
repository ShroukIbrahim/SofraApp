package com.moshrouk.egyadvtask.ui.fragment.usercyclefragmants;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.egyadvtask.R;
import com.moshrouk.egyadvtask.data.model.loginuser.LoginUser;
import com.moshrouk.egyadvtask.data.reset.ApiServices;
import com.moshrouk.egyadvtask.helper.HelperMethod;
import com.moshrouk.egyadvtask.ui.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.egyadvtask.data.reset.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    @BindView(R.id.login_fragment_email_txt)
    TextInputEditText Email;
    @BindView(R.id.login_fragment_password_txt)
    TextInputEditText Password;
    @BindView(R.id.login_fragment_forget_password)
    TextView ForgetPassword;
    @BindView(R.id.login_fragment_login)
    Button Login;
    @BindView(R.id.login_fragment_register)
    TextView Register;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;

    private String email, password;
    Unbinder unbinder;


    private ApiServices apiServices = getClient().create(ApiServices.class);

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        HelperMethod.disappearKeypad(getActivity(), view);
        return view;
    }

    // Login Method
    private void login() {
        ProgressBar.setVisibility(View.VISIBLE);
        email = Email.getText().toString();
        password = Password.getText().toString();



        apiServices.onLogin(email, password).enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                Log.i("onResponse", "OK: " + response.raw());
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(getActivity(), response.body().getMessage() + "..", Toast.LENGTH_SHORT).show();
                    ProgressBar.setVisibility(View.GONE);
                    Intent openHome = new Intent(getActivity(), HomeActivity.class);
                    startActivity(openHome);
                } else {
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                Log.i("onResponse", "NO: ");
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                ProgressBar.setVisibility(View.GONE);

            }
        });

    }

    @OnClick({R.id.login_fragment_forget_password, R.id.login_fragment_login, R.id.login_fragment_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_fragment_forget_password:
                // open forget password fragment
                break;
            case R.id.login_fragment_login:
                login();
                break;
            case R.id.login_fragment_register:
                // open register fragment
                break;
        }
    }
}
