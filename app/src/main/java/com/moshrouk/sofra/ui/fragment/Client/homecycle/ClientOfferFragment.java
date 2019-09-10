package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.general.OfferAdapter;
import com.moshrouk.sofra.data.model.general.offers.OfferData;
import com.moshrouk.sofra.data.model.general.offers.Offers;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientOfferFragment extends Fragment {


    @BindView(R.id.offer_list)
    RecyclerView offerList;
    Unbinder unbinder;

    private OfferAdapter offerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<OfferData> offerData = new ArrayList<>();
    GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);


    public ClientOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_client_offer, container, false);
        unbinder = ButterKnife.bind(this,view);

        offerList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        offerList.setLayoutManager(linearLayoutManager);
        offerAdapter = new OfferAdapter(getActivity(), offerData);
        offerList.setAdapter(offerAdapter);
        getAllOffer();
        
        return view;
    }

    private void getAllOffer() {
        generalApiServices.getAllOffers().enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                if (response.body().getStatus().equals(1)) {
                    offerData.addAll(response.body().getData().getData());
                    offerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });
    }

}
