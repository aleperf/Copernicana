package com.aleperf.pathfinder.copernicana.epic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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


    public EpicAdapter(Context context){
        this.context = context;
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

        public EpicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindNaturalEpic(int position) {
            Epic epic = naturalEpic.get(position);
            String imageUrl = Utils.buildEpicNaturalImageUrl(epic.getDate(), epic.getImage());
            Log.d("uffa", "imageUrl: " + imageUrl);
            GlideApp.with(context).load(imageUrl)
                    .error(R.drawable.blue_marble_card)
                    .placeholder(R.drawable.blue_marble_card)
                    .thumbnail(0.3f)
                    .into(epicImage);
        }

        @Override
        public void onClick(View v) {
            if (context instanceof EpicElementSelector) {
                EpicElementSelector epicElementSelector = (EpicElementSelector) context;
                epicElementSelector.selectEpic(naturalEpic.get(getAdapterPosition()).getIdentifier());
            }
        }
    }
}
