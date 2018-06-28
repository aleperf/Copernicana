package com.aleperf.pathfinder.copernicana.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroCardsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final int APOD_VIEW_TYPE = 0;
    private final int GENERIC_CARD_TYPE = 1;
    private final int NUMBER_OF_CARDS = 4;


    private Apod apod;
    private Context context;


    public IntroCardsAdapter(Context context) {
        this.context = context;
        this.apod = apod;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case APOD_VIEW_TYPE:
               view = inflater.inflate(R.layout.apod_intro_item, parent, false);
               return new ApodViewHolder(view);

            default:
                view = inflater.inflate(R.layout.generic_intro_item, parent, false);
                return new GenericCardViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case APOD_VIEW_TYPE:
                ApodViewHolder apodViewHolder = (ApodViewHolder) holder;
                apodViewHolder.bindApod();
                break;
            default:
                GenericCardViewHolder genericCardViewHolder = (GenericCardViewHolder) holder;
                genericCardViewHolder.bindGenericCard(position);
        }

    }

    @Override
    public int getItemCount() {
        if(apod == null){
            return 0;
        }
        return NUMBER_OF_CARDS;
    }

    public void setApod(Apod newApod){
        this.apod = newApod;
        notifyDataSetChanged();
    }


    public class ApodViewHolder extends ViewHolder {

        private final int APOD_PLACEHOLDER = R.drawable.nasa_43566_unsplash;
        private final int APOD_ERROR = R.drawable.nasa_43566_unsplash;
        private final String IMAGE_MEDIA_TYPE = "image";
        private final String VIDEO_MEDIA_TYPE = "video";

        @BindView(R.id.apod_card_background)
        ImageView apodBackground;
        @BindView(R.id.apod_card_rv_sub)
        TextView apodSubtitle;
        @BindView(R.id.apod_card_rv_date)
        TextView apodDate;
        @BindView(R.id.media_type_image_icon)
        ImageView imageIcon;
        @BindView(R.id.media_type_video_icon)
        ImageView videoIcon;


        public ApodViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindApod() {
            if(apod != null) {
                Picasso.get().load(apod.getUrl())
                        .placeholder(APOD_PLACEHOLDER)
                        .error(APOD_ERROR)
                        .into(apodBackground);
            apodSubtitle.setText(apod.getTitle());
            apodDate.setText(apod.getDate());
            String mediaType = apod.getMediaType();
            if(mediaType == VIDEO_MEDIA_TYPE){
                imageIcon.setVisibility(View.GONE);
                videoIcon.setVisibility(View.VISIBLE);
            } else {
                imageIcon.setVisibility(View.VISIBLE);
                videoIcon.setVisibility(View.GONE);
            }

            }


        }

    }


    public class GenericCardViewHolder extends ViewHolder {

        private final int[] CARD_BACKGROUNDS = {R.drawable.blue_marble_card,
                R.drawable.mars_revived_crop, R.drawable.iss_over_earth};
        private final int BLUE_MARBLE_INDEX = 0;
        private final int MARS_INDEX = 1;
        private final int ISS_INDEX = 2;
        private final int BLUE_MARBLE_CARD_POS = 1;
        private final int MARS_CARD_POS = 2;
        private final int ISS_CARD_POS = 3;

        @BindView(R.id.generic_intro_card_title)
        TextView introCardTitle;
        @BindView(R.id.generic_intro_card_subtitle)
        TextView introCardSubtitle;
        @BindView(R.id.generic_intro_card_image)
        ImageView genericIntroImage;


        public GenericCardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bindGenericCard(int position) {

            switch (position) {
                case BLUE_MARBLE_CARD_POS:
                    introCardTitle.setText(context.getString(R.string.epic_title));
                    introCardSubtitle.setText(context.getString(R.string.epic_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[BLUE_MARBLE_INDEX]);
                    break;
                case MARS_CARD_POS:
                    introCardTitle.setText(context.getString(R.string.mars_title));
                    introCardSubtitle.setText(context.getString(R.string.mars_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[MARS_INDEX]);
                    break;
                default:
                    introCardTitle.setText(context.getString(R.string.iss_title));
                    introCardSubtitle.setText(context.getString(R.string.iss_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[ISS_INDEX]);

            }
        }

    }
}
