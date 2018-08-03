package com.aleperf.pathfinder.copernicana.epic;

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

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EpicEnhancedFragment extends Fragment {


    private Unbinder unbinder;
    @BindView(R.id.epic_enhanced_recycler_view)
    RecyclerView epicEnhancedRecyclerview;
    @Inject
    ViewModelProvider.Factory factory;
    LiveData<List<EpicEnhanced>> epicEnhanced;
    EpicEnhancedAdapter epicAdapter;

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

        View rootView = inflater.inflate(R.layout.fragment_epic_enhanced, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        epicAdapter = new EpicEnhancedAdapter(getActivity());
        int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),columnCount);
        epicEnhancedRecyclerview.setAdapter(epicAdapter);
        epicEnhancedRecyclerview.setLayoutManager(gridLayoutManager);
        EpicEnhancedViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(EpicEnhancedViewModel.class);
        epicEnhanced= viewModel.getEnhancedEpic();
        subscribe();

    }

    public void subscribe(){
        Observer<List<EpicEnhanced>> epicObserver = new Observer<List<EpicEnhanced>>() {
            @Override
            public void onChanged(@Nullable List<EpicEnhanced> epics) {
                if(epics != null && epics.size() > 0){
                    epicAdapter.setEpicEnhanced(epics);
                }
            }
        };
        epicEnhanced.observe(this, epicObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
