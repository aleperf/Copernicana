package com.aleperf.pathfinder.copernicana.apod;

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
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodAdapter extends RecyclerView.Adapter<ApodAdapter.ApodViewHolder> {


    private Context context;
    private List<Apod> apods;


    public interface ApodDetailLauncher {
        void showApodDetail(Apod apod);
    }

    public ApodAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public ApodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.apod_detail_card, parent, false);
        return new ApodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodViewHolder holder, int position) {
        if (apods != null) {
            holder.bindApod(apods.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (apods != null) {
            return apods.size();
        }
        return 0;
    }

    public void setApod(List<Apod> apodList) {
        this.apods = apodList;
        notifyDataSetChanged();
    }


    public class ApodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final int[] sectionImages = {R.drawable.apod_placeholder,
                R.drawable.search_ico, R.drawable.star_white, R.drawable.camera_ico, R.drawable.apod_error};
        private final int placeholderIndex = 0;
        private final int errorIndex = 4;


        @BindView(R.id.apod_detail_card_image)
        ImageView apodImage;
        @BindView(R.id.apod_detail_card_title)
        TextView apodTitle;
        @BindView(R.id.apod_detail_card_date)
        TextView apodDate;
        @BindView(R.id.media_type_icon)
        ImageView mediaTypeIcon;

        public ApodViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindApod(Apod apod) {
            if (apod != null) {
                String photoUrl;
                if (apod.getMediaType().equals(Apod.MEDIA_TYPE_IMAGE)) {
                    photoUrl = apod.getUrl();
                    mediaTypeIcon.setImageResource(R.drawable.picture_card_ico);
                    GlideApp.with(context)
                            .load(photoUrl)
                            .placeholder(sectionImages[placeholderIndex])
                            .error(sectionImages[errorIndex])
                            .into(apodImage);
                } else {
                    mediaTypeIcon.setImageResource(R.drawable.youtube_blue);
                    //if this is a valid youtube video show a thumbnail
                    if (apod.getUrl().contains("youtube")) {
                        photoUrl = Utils.buildVideoThumbnailFromUrl(apod.getUrl());
                        GlideApp.with(context)
                                .load(photoUrl)
                                .placeholder(sectionImages[placeholderIndex])
                                .error(sectionImages[errorIndex])
                                .into(apodImage);
                    } else {
                        //this is an invalid youtube video show a placeholder
                        apodImage.setImageResource(sectionImages[placeholderIndex]);
                    }
                }

                apodTitle.setText(apod.getTitle());
                apodDate.setText(Utils.getFormattedDate(apod.getDate(), context));

            }
        }

        @Override
        public void onClick(View v) {
            if (context instanceof ApodDetailLauncher) {
                ApodDetailLauncher launcher = (ApodDetailLauncher) context;
                Apod apod = apods.get(getAdapterPosition());
                launcher.showApodDetail(apod);
            }


        }
    }
}
