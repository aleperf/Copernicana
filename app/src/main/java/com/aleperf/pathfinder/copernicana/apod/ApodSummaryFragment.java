package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApodSummaryFragment extends Fragment {

    @BindView(R.id.apod_summary_rv)
    RecyclerView apodSummaryRecyclerView;
    private Unbinder unbinder;
    @Inject
    ViewModelProvider.Factory factory;
    private ApodViewModel apodViewModel;
    private LiveData<Apod> apodLiveData;
    private Apod apod;
    private ApodSummaryAdapter adapter;


    public ApodSummaryFragment(){
        //default empty constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) this.getActivity().getApplication())
                .getCopernicanaApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_summary_apod, container, false);
       unbinder =  ButterKnife.bind(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        apodSummaryRecyclerView.setLayoutManager(layoutManager);
        adapter = new ApodSummaryAdapter(getActivity());
        apodSummaryRecyclerView.setAdapter(adapter);

       return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apodViewModel = ViewModelProviders.of(this, factory).get(ApodViewModel.class);
        apodLiveData = apodViewModel.getLastApod();
        subscribeApod();

    }

    private void subscribeApod(){
        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apod) {
                if(apod != null){
                    Log.d("uffa", "apod in apod summary + diverso da null");
                    adapter.setApod(apod);
                    adapter.notifyDataSetChanged();
                }

            }
        };
        apodLiveData.observe(this, apodObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
