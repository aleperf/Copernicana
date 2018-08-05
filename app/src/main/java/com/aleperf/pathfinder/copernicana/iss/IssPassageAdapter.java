package com.aleperf.pathfinder.copernicana.iss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.IssPassage;
import com.aleperf.pathfinder.copernicana.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IssPassageAdapter extends RecyclerView.Adapter<IssPassageAdapter.IssPassageHolder> {

    private Context context;
    private List<IssPassage> issPassages;

    public IssPassageAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public IssPassageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.iss_passage_item, parent, false);
        return new IssPassageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssPassageHolder holder, int position) {
        if (issPassages != null) {
            holder.bindIssPassageHolder(issPassages.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (issPassages != null) {
            return issPassages.size();
        }
        return 0;
    }

    public void setIssPassages(List<IssPassage> issPassages) {
        this.issPassages = issPassages;
        notifyDataSetChanged();
    }

    public class IssPassageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date_time_value)
        TextView passageDateAndTime;
        @BindView(R.id.duration_value)
        TextView duration;

        public IssPassageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindIssPassageHolder(IssPassage issPassage) {
            String dateAndTime = Utils.getLocalTimeFromUnixTimestamp(issPassage.getRisetime());
            int[] minutesAndSeconds = Utils.getDurationInMinutesAndSecond(issPassage.getDuration());
            passageDateAndTime.setText(dateAndTime);
            String formatString = context.getString(R.string.iss_pass_item_duration_format);
            String durationString = String.format(formatString, minutesAndSeconds[0], minutesAndSeconds[1]);
            duration.setText(durationString);
        }
    }
}
