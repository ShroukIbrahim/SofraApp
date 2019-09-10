package com.moshrouk.sofra.ui.fragment.Client.clientcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.password.resetpassword.ResetPassword;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {


    @BindView(R.id.fragment_reset_password_email)
    TextInputEditText Email;
    @BindView(R.id.fragment_forget_password_step1_send_code)
    Button SendCode;
    Unbinder unbinder;
    private ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    private void resetPassword() {
        String email = Email.getText().toString();
        clientApiServices.resetPassword(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus().equals(1)){
                    Toast.makeText(getActivity(),response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
                    NewPasswordFragment newPasswordFragment = new NewPasswordFragment();
                    HelperMethod.replace(newPasswordFragment,getFragmentManager(),R.id.resturant_cycle,null,null);
                }
                else
                    Toast.makeText(getActivity(),response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.fragment_forget_password_step1_send_code)
    public void onViewClicked() {
        SendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }


}
