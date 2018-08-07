package com.aleperf.pathfinder.copernicana.epic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicAdapter extends RecyclerView.Adapter<EpicAdapter.EpicHolder> {

    private Context context;
    private List<Epic> naturalEpic;
    private String flag;
    public static String FLAG_FROM_SEARCH = "adapter created from search";
    public static String NOT_FROM_SEARCH = "not from search";


    public interface EpicNaturalSelector {

        void selectEpic(Epic epic);
    }

    public interface EpicNaturalSearchSelector {
        void selectEpicFromSearch(Epic epic);
    }


    public EpicAdapter(Context context, String flag) {
        this.context = context;
        this.flag = flag;
    }


    @NonNull
    @Override
    public EpicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.epic_item, parent, false);
        return new EpicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpicHolder holder, int position) {
        holder.bindNaturalEpic(position);

    }

    @Override
    public int getItemCount() {
        if (naturalEpic != null) {
            return naturalEpic.size();
        }
        return 0;
    }

    public void setEpic(List<Epic> epics) {
        naturalEpic = epics;
        notifyDataSetChanged();
    }

    public class EpicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.epic_image)
        ImageView epicImage;
        @BindView(R.id.epic_date_and_hour)
        TextView epicDateAndHour;

        public EpicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindNaturalEpic(int position) {
            Epic epic = naturalEpic.get(position);
            String date = epic.getDate();
            String formattedDate = Utils.getEpicDateFormat(date);
            if (formattedDate != null) {
                epicDateAndHour.setText(formattedDate);
            }
            String imageUrl = Utils.buildEpicNaturalImageUrl(epic.getDate(), epic.getImage());
            GlideApp.with(context).load(imageUrl)
                    .error(R.drawable.blue_marble_error)
                    .placeholder(R.drawable.blue_marble_placeholder)
                    .thumbnail(0.3f)
                    .into(epicImage);
        }

        @Override
        public void onClick(View v) {
            if (!flag.equals(FLAG_FROM_SEARCH)) {
                if (context instanceof EpicNaturalSelector) {
                    EpicNaturalSelector epicNaturalSelector = (EpicNaturalSelector) context;
                    epicNaturalSelector.selectEpic(naturalEpic.get(getAdapterPosition()));
                }
            } else {

                if(context instanceof EpicNaturalSearchSelector){
                    EpicNaturalSearchSelector searchSelector = (EpicNaturalSearchSelector) context;
                    searchSelector.selectEpicFromSearch(naturalEpic.get(getAdapterPosition()));
                }
            }
        }
    }
}
