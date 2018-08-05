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
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EpicEnhancedFavFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.epic_fav_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.epic_fav_empty_text_view)
    TextView emptyTextView;
    private EpicEnhancedAdapter epicAdapter;
    private LiveData<List<EpicEnhanced>> enhancedFavorites;
    @Inject
    ViewModelProvider.Factory factory;



    public EpicEnhancedFavFragment(){}

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

        View rootView = inflater.inflate(R.layout.fragment_epic_fav, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        epicAdapter =  new EpicEnhancedAdapter(getActivity(), EpicEnhancedAdapter.NOT_FROM_SEARCH);
        int columnCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),columnCount);
        recyclerView.setAdapter(epicAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        EpicEnhancedFavViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(EpicEnhancedFavViewModel.class);
        enhancedFavorites = viewModel.getFavoriteEnhancedEpic();
        subscribe();
    }
    public void subscribe(){
        Observer<List<EpicEnhanced>> epicObserver = new Observer<List<EpicEnhanced>>() {
            @Override
            public void onChanged(@Nullable List<EpicEnhanced> epics) {
                if(epics != null && epics.size() > 0){
                    epicAdapter.setEpicEnhanced(epics);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyTextView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        };
        enhancedFavorites.observe(this, epicObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
