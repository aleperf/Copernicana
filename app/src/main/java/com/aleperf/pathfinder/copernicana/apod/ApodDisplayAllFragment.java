package com.aleperf.pathfinder.copernicana.apod;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ApodDisplayAllFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.apod_display_all_recycler_view)
    RecyclerView displayAllRecyclerView;
    @BindView(R.id.apod_latest_empty_text_view)
    TextView emptyTextView;
    @BindView(R.id.apod_latest_empty_image)
    ImageView emptyImage;
    private ApodAdapter apodAdapter;
    @Inject
    ViewModelProvider.Factory factory;
    ApodDisplayAllViewModel displayAllViewModel;
    LiveData<List<Apod>> allApods;



    public ApodDisplayAllFragment(){
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_apod_display_all, container, false);
        unbinder =  ButterKnife.bind(this, rootView);
        apodAdapter = new ApodAdapter(getActivity());
        int columnCount = getActivity().getResources().getInteger(R.integer.favorites_column_count);
        GridLayoutManager layoutManager =  new GridLayoutManager(getActivity(), columnCount);
        displayAllRecyclerView.setAdapter(apodAdapter);
        displayAllRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayAllViewModel = ViewModelProviders.of(this, factory).get(ApodDisplayAllViewModel.class);
        allApods = displayAllViewModel.getAllApods();
        subscribeAllApods();
    }


    private void subscribeAllApods(){
        Observer<List<Apod>> allApodsObserver = new Observer<List<Apod>>() {
            @Override
            public void onChanged(@Nullable List<Apod> apodList) {
                if(apodList != null && apodList.size() > 0){
                    apodAdapter.setApod(apodList);
                    emptyTextView.setVisibility(View.GONE);
                    emptyImage.setVisibility(View.GONE);
                } else {
                    apodAdapter.setApod(apodList);
                    emptyTextView.setVisibility(View.GONE);
                    emptyImage.setVisibility(View.GONE);
                }

            }
        };
        allApods.observe(this, allApodsObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
