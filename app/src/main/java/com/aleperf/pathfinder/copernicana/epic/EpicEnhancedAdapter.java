package com.aleperf.pathfinder.copernicana.epic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.GlideApp;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Epic;
import com.aleperf.pathfinder.copernicana.model.EpicEnhanced;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpicEnhancedAdapter extends RecyclerView.Adapter<EpicEnhancedAdapter.EpicEnhancedHolder> {

    Context context;
    List<EpicEnhanced> epicEnhanced;


    public interface EpicEnhancedSelector {

        void selectEpicEnhanced(EpicEnhanced epicEnhanced);
    }


    public EpicEnhancedAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public EpicEnhancedAdapter.EpicEnhancedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.epic_item, parent, false);
        return new EpicEnhancedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpicEnhancedAdapter.EpicEnhancedHolder holder, int position) {
        holder.bindEpicEnhanced(position);
    }

    @Override
    public int getItemCount() {
        if (epicEnhanced != null) {
            return epicEnhanced.size();
        }
        return 0;
    }

    public void setEpicEnhanced(List<EpicEnhanced> epics) {
        epicEnhanced = epics;
        notifyDataSetChanged();
    }

    public class EpicEnhancedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.epic_image)
        ImageView epicImage;
        @BindView(R.id.epic_date_and_hour)
        TextView epicDateAndHour;

        public EpicEnhancedHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindEpicEnhanced(int position) {
            Epic epic = epicEnhanced.get(position);
            String date = epic.getDate();
            String formattedDate = Utils.getEpicDateFormat(date);
            if (formattedDate != null) {
                epicDateAndHour.setText(formattedDate);
            }
            String imageUrl = Utils.buildEpicEnhancedImageUrl(epic.getDate(), epic.getImage());
            GlideApp.with(context).load(imageUrl)
                    .error(R.drawable.blue_marble_error)
                    .placeholder(R.drawable.blue_marble_placeholder)
                    .thumbnail(0.3f)
                    .into(epicImage);
        }

        @Override
        public void onClick(View v) {
            if (context instanceof EpicEnhancedSelector) {
                EpicEnhanced epic = epicEnhanced.get(getAdapterPosition());
                EpicEnhancedSelector selector = (EpicEnhancedSelector) context;
                selector.selectEpicEnhanced(epic);
            }
        }
    }
}
