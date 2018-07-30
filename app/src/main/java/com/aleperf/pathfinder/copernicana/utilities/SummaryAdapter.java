package com.aleperf.pathfinder.copernicana.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int[] images;
    private String[] titles;
    int sections;
    private final static int HEADER_VIEW_TYPE = 0;
    private final static  int SECTION_VIEW_TYPE = 1;

    public interface SectionSelector{
        void selectSection(int position);
    }


    public SummaryAdapter(Context context, int[] images, String[] titles, int sections){
        this.context = context;
        this.images = images;
        this.sections = sections;
        this.titles = titles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                view  = inflater.inflate(R.layout.summary_header, parent, false);
                return new HeaderHolder(view);
            default:
                view = inflater.inflate(R.layout.summary_item, parent, false);
                return new SectionHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == HEADER_VIEW_TYPE){
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.bindHeaderHolder(images[position]);
        } else {
            SectionHolder sectionHolder = (SectionHolder) holder;
            sectionHolder.bindSectionHolder(images[position], titles[position - 1]);
        }

    }

    @Override
    public int getItemCount() {
        return sections;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER_VIEW_TYPE;
        }
        return SECTION_VIEW_TYPE;
    }

    public class SectionHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

         @BindView(R.id.section_image)
         ImageView sectionImage;
         @BindView(R.id.section_title)
         TextView sectionTitle;

        public SectionHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindSectionHolder(int imageRes, String title){
            sectionImage.setImageResource(imageRes);
            sectionTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if(context instanceof SectionSelector){
                SectionSelector sectionSelector = (SectionSelector) context;
                sectionSelector.selectSection(getAdapterPosition());
            }
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.summary_header_image)
        ImageView header;

        public HeaderHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindHeaderHolder(int imageRes){
            header.setImageResource(imageRes);
        }
    }
}
