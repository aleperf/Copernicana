package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AstronautsFragment extends Fragment {

    @BindView(R.id.astronauts_recycler_view)
    RecyclerView astronautsRV;
    private Unbinder unbinder;
    @BindView(R.id.astronaut_empty_text_view)
    TextView emptyMessage;
    @Inject
    ViewModelProvider.Factory factory;
    private AstronautsViewModel astronautsViewModel;
    private AstronautsAdapter adapter;
    private LiveData<List<Astronaut>> astronauts;


    public AstronautsFragment() {
        // Required empty public constructor
    }


    public static AstronautsFragment newInstance() {
        AstronautsFragment fragment = new AstronautsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_astronauts, container, false);
        unbinder =  ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        astronautsViewModel = ViewModelProviders.of(this, factory).get(AstronautsViewModel.class);
        astronauts = astronautsViewModel.getAstronauts();
        adapter = new AstronautsAdapter(getActivity());
        int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        astronautsRV.setAdapter(adapter);
        astronautsRV.setLayoutManager(gridLayoutManager);
        subscribeAstronauts();

    }

    private void subscribeAstronauts(){
        Observer<List<Astronaut>> observer = new Observer<List<Astronaut>>() {
            @Override
            public void onChanged(@Nullable List<Astronaut> astronautsInSpace) {
                if(astronautsInSpace != null){
                    adapter.setAstronauts(astronautsInSpace);
                    emptyMessage.setVisibility(View.GONE);
                } else {
                    emptyMessage.setVisibility(View.VISIBLE);
                }
            }
        };

        astronauts.observe(this, observer);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
