package com.aleperf.pathfinder.copernicana.epic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.utilities.SummaryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EpicSummaryFragment extends Fragment {


    private Unbinder unbinder;
    @BindView(R.id.epic_summary_rv)
    RecyclerView recyclerView;

    private final int[] images = {R.drawable.blue_marble_card, R.drawable.earth_natural,
            R.drawable.earth_enhanced, R.drawable.star_white, R.drawable.star_icon, R.drawable.search_ico};
    private String[] titles;
    private final int sections = 6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary_epic, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titles = new String[]{getString(R.string.epic_natural_label), getString(R.string.epic_enhanced_label),
                getString(R.string.epic_natural_favorites_label), getString(R.string.epic_enhanced_favorites_label),
                getString(R.string.epic_search_label)};
        SummaryAdapter summaryAdapter = new SummaryAdapter(getActivity(), images, titles, sections);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(summaryAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
