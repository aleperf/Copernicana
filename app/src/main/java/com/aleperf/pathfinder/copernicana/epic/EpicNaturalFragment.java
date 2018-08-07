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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EpicNaturalFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.epic_natural_recycler_view)
    RecyclerView naturalEpicRecyclerView;
    @Inject
    ViewModelProvider.Factory factory;
    LiveData<List<Epic>> naturalEpic;
    EpicAdapter epicAdapter;

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

        View rootView = inflater.inflate(R.layout.fragment_epic_natural, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        epicAdapter = new EpicAdapter(getActivity(), EpicAdapter.NOT_FROM_SEARCH);
        int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),columnCount);
        naturalEpicRecyclerView.setAdapter(epicAdapter);
        naturalEpicRecyclerView.setLayoutManager(gridLayoutManager);
        EpicNaturalViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(EpicNaturalViewModel.class);
        naturalEpic = viewModel.getNaturalEpic();
        subscribe();

    }

    public void subscribe(){
        Observer<List<Epic>> epicObserver = new Observer<List<Epic>>() {
            @Override
            public void onChanged(@Nullable List<Epic> epics) {
                if(epics != null && epics.size() > 0){
                    epicAdapter.setEpic(epics);
                }
            }
        };
        naturalEpic.observe(this, epicObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
