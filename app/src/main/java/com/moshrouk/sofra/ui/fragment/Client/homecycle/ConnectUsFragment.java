package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.addContact.AddContact;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectUsFragment extends Fragment {


    @BindView(R.id.client_name)
    TextInputEditText clientName;
    @BindView(R.id.client_email)
    TextInputEditText clientEmail;
    @BindView(R.id.client_phone)
    TextInputEditText clientPhone;
    @BindView(R.id.message_content)
    TextInputEditText messageContent;
    @BindView(R.id.complaint)
    RadioButton complaint;
    @BindView(R.id.suggestion)
    RadioButton suggestion;
    @BindView(R.id.inquiry)
    RadioButton inquiry;
    @BindView(R.id.send_massage)
    Button sendMassage;
    Unbinder unbinder;

    GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private String type;


    public ConnectUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void AddContact() {

        generalApiServices.addContact(clientName.getText().toString(),
                clientEmail.getText().toString(),
                clientPhone.getText().toString(),
                type, messageContent.getText().toString())
                .enqueue(new Callback<AddContact>() {
                    @Override
                    public void onResponse(Call<AddContact> call, Response<AddContact> response) {
                        if (response.body().getStatus().equals(1)) {

                            Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<AddContact> call, Throwable t) {
                        Log.d("response", t.getMessage());
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.complaint, R.id.suggestion, R.id.inquiry, R.id.send_massage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.complaint:
                type = "complaint";

                break;
            case R.id.suggestion:
                type = "suggestion";

                break;
            case R.id.inquiry:
                type = "inquiry";

                break;
            case R.id.send_massage:
                AddContact();
                break;
        }
    }
}
