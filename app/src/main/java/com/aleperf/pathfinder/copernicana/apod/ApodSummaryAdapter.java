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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApodSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final int[] sectionImages = {R.drawable.apod_placeholder,
            R.drawable.search_ico, R.drawable.star_white,R.drawable.camera_ico, R.drawable.apod_error};
    private final int placeholderIndex = 0;
    private final int errorIndex = 4;
    private final int searchIndex = 1;
    private final int favoriteIndex = 2;
    private final int photoIndex = 3;
    private final int VIEW_TYPE_APOD = 0;
    private final int VIEW_TYPE_SECTION = 1;
    private final int numberOfSections = 4;

    private Apod apod;

    public interface ApodSectionSelector {

        void selectSection(int position);

        void selectApodSection(Apod apod);
    }


    public ApodSummaryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case VIEW_TYPE_APOD:
                view = inflater.inflate(R.layout.apod_detail_card, parent, false);
                return new ApodCardHolder(view);
            default:
                view = inflater.inflate(R.layout.summary_item, parent, false);
                return new SectionHolder(view);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ApodCardHolder apodCardHolder = (ApodCardHolder) holder;
            apodCardHolder.bindApod();
        } else {
            SectionHolder sectionHolder = (SectionHolder) holder;
            sectionHolder.bindSection(position);
        }

    }

    @Override
    public int getItemCount() {
        return numberOfSections;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_APOD;
        } else {
            return VIEW_TYPE_SECTION;
        }
    }

    public void setApod(Apod apod) {
        this.apod = apod;
        notifyDataSetChanged();
    }

    public class ApodCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.apod_detail_card_image)
        ImageView apodImage;
        @BindView(R.id.apod_detail_card_title)
        TextView apodTitle;
        @BindView(R.id.apod_detail_card_date)
        TextView apodDate;
        @BindView(R.id.media_type_icon)
        ImageView mediaTypeIcon;

        public ApodCardHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindApod() {
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
                    if(apod.getUrl().contains("youtube")){
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
            if (context instanceof ApodSectionSelector) {
                ApodSectionSelector apodSectionSelector = (ApodSectionSelector) context;
                if (apod != null) {
                    apodSectionSelector.selectApodSection(apod);
                }
            }
        }
    }


    public class SectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.section_image)
        ImageView sectionImage;
        @BindView(R.id.section_title)
        TextView sectionTitle;


        public SectionHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindSection(int position) {
            switch (position) {
                case 1:
                    sectionImage.setImageResource(sectionImages[searchIndex]);
                    sectionTitle.setText(context.getString(R.string.section_search));
                    break;
                case 2:
                    sectionImage.setImageResource(sectionImages[favoriteIndex]);
                    sectionTitle.setText(context.getString(R.string.section_fav));
                    break;
                default:
                    sectionImage.setImageResource(sectionImages[photoIndex]);
                    sectionTitle.setText(context.getString(R.string.section_all_pics));
            }
        }

        @Override
        public void onClick(View v) {
            if(context instanceof ApodSectionSelector){
                ApodSectionSelector apodSectionSelector = (ApodSectionSelector)context;
                apodSectionSelector.selectSection(getAdapterPosition());
            }
        }
    }
}
