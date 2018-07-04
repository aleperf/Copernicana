package com.aleperf.pathfinder.copernicana.intro;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Display a summary of all the main app arguments
 */

public class SummaryFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory factory;
    IntroViewModel model;
    private LiveData<Apod> apodLiveData;
    @BindView(R.id.summary_recycler_view)
    RecyclerView summaryRecyclerView;
    private Unbinder unbinder;
    private IntroCardsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CopernicanaApplication) getActivity().getApplication()).getCopernicanaApplicationComponent().inject(this);
        setRetainInstance(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_summary, container, false);
        unbinder = ButterKnife.bind(this, root);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new IntroCardsAdapter(this.getActivity());
        summaryRecyclerView.setAdapter(adapter);
        summaryRecyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = ViewModelProviders.of(this, factory).get(IntroViewModel.class);
        apodLiveData = model.getApod();
        subscribeApod();


    }

    private void subscribeApod() {

        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apodResult) {
                if (apodResult != null) {
                    adapter.setApod(apodResult);
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
