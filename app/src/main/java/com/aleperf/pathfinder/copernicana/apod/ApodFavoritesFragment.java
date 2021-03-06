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


public class ApodFavoritesFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.apod_favorites_recycler_view)
    RecyclerView favoritesRecyclerView;
    private ApodAdapter apodAdapter;
    @BindView(R.id.apod_favorites_empty_image)
    ImageView emptyViewImage;
    @BindView(R.id.apod_favorites_empty_text_view)
    TextView emptyTextView;
    @Inject
    ViewModelProvider.Factory factory;
    ApodFavoritesViewModel favoritesViewModel;
    LiveData<List<Apod>> apodFavorites;


    public ApodFavoritesFragment(){
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
        View rootView = inflater.inflate(R.layout.fragment_apod_favorites, container, false);
        unbinder =  ButterKnife.bind(this, rootView);
        apodAdapter = new ApodAdapter(getActivity());
        int columnCount = getActivity().getResources().getInteger(R.integer.favorites_column_count);
        GridLayoutManager layoutManager =  new GridLayoutManager(getActivity(), columnCount);
        favoritesRecyclerView.setAdapter(apodAdapter);
        favoritesRecyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoritesViewModel = ViewModelProviders.of(this, factory).get(ApodFavoritesViewModel.class);
        apodFavorites = favoritesViewModel.getFavoritesApod();
        subscribeAllFavorites();



    }


    private void subscribeAllFavorites(){
        Observer<List<Apod>> allFavoritesObserver = new Observer<List<Apod>>() {
            @Override
            public void onChanged(@Nullable List<Apod> apodList) {
                if(apodList != null && apodList.size() > 0){
                    emptyViewImage.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.GONE);
                    apodAdapter.setApod(apodList);
                } else{
                    emptyViewImage.setVisibility(View.VISIBLE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }

            }
        };
        apodFavorites.observe(this, allFavoritesObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
