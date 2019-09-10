package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.notifications_token.NotificationsToken;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.activity.client.ClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.clean;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    @BindView(R.id.offer)
    TextView offer;
    @BindView(R.id.connect_us)
    TextView connectUs;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.logout)
    TextView logout;
    Unbinder unbinder;
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private String msg;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.offer, R.id.connect_us, R.id.about, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.offer:
                ClientOfferFragment clientOfferFragment = new ClientOfferFragment();
                HelperMethod.replace(clientOfferFragment, getFragmentManager(), R.id.client_home_frame, null, null);

                break;
            case R.id.connect_us:
                ConnectUsFragment connectUsFragment = new ConnectUsFragment();
                HelperMethod.replace(connectUsFragment, getFragmentManager(), R.id.client_home_frame, null, null);
                break;
            case R.id.about:
                AboutFragment aboutFragment = new AboutFragment();
                HelperMethod.replace(aboutFragment, getFragmentManager(), R.id.client_home_frame, null, null);
                break;
            case R.id.logout:
                ShowDialog();

                break;
        }

    }

    private void ShowDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Logout...");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "LogOut Successful", Toast.LENGTH_SHORT).show();
                clean(getActivity());
                RemoveToken();
                startActivity(new Intent(getActivity(), ClientActivity.class));

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

    }
    //Remove Token
    public void RemoveToken() {
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


        generalApiServices.clientRemoveToken(msg, LoadData(getActivity(), CLIENT_API_TOKEN)).enqueue(new Callback<NotificationsToken>() {
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


}