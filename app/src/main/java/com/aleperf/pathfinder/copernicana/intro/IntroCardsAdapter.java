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


import butterknife.BindView;
import butterknife.ButterKnife;

import com.aleperf.pathfinder.copernicana.intro.SummaryFragment.SectionSelector;

public class IntroCardsAdapter extends RecyclerView.Adapter<IntroCardsAdapter.IntroCardViewHolder> {


    private final int NUMBER_OF_CARDS = 3;


    private Context context;


    public IntroCardsAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public IntroCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.generic_intro_item, parent, false);
        return new IntroCardViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull IntroCardViewHolder holder, int position) {
        holder.bindGenericCard(position);

    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_CARDS;
    }


    public class IntroCardViewHolder extends ViewHolder implements View.OnClickListener {

        private final int[] CARD_BACKGROUNDS = {R.drawable.nasa_43566_unsplash, R.drawable.blue_marble_card,
                R.drawable.iss_over_earth};
        private final int APOD_INDEX = 0;
        private final int BLUE_MARBLE_INDEX = 1;
        private final int ISS_INDEX = 2;


        @BindView(R.id.generic_intro_card_title)
        TextView introCardTitle;
        @BindView(R.id.generic_intro_card_subtitle)
        TextView introCardSubtitle;
        @BindView(R.id.generic_intro_card_image)
        ImageView genericIntroImage;


        public IntroCardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        public void bindGenericCard(int position) {

            switch (position) {
                case APOD_INDEX:
                    introCardTitle.setText(context.getString(R.string.apod_card_rv_title));
                    introCardSubtitle.setText(context.getString(R.string.apod_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[APOD_INDEX]);
                    break;
                case BLUE_MARBLE_INDEX:
                    introCardTitle.setText(context.getString(R.string.epic_title));
                    introCardSubtitle.setText(context.getString(R.string.epic_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[BLUE_MARBLE_INDEX]);
                    break;
                default:
                    introCardTitle.setText(context.getString(R.string.iss_title));
                    introCardSubtitle.setText(context.getString(R.string.iss_subtitle));
                    genericIntroImage.setImageResource(CARD_BACKGROUNDS[ISS_INDEX]);

            }
        }

        @Override
        public void onClick(View v) {
            if (context instanceof SectionSelector) {
                SectionSelector sectionSelector = (SectionSelector) context;
                sectionSelector.selectSection(getAdapterPosition());
            }
        }
    }
}
