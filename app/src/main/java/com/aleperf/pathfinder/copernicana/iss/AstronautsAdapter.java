package com.aleperf.pathfinder.copernicana.iss;

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
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AstronautsAdapter extends RecyclerView.Adapter<AstronautsAdapter.AstronautHolder> {

    private Context context;
    private List<Astronaut> astronauts;
    private FirebaseStorage firebaseStorage;


    public interface AstronautSelector{
        void selectAstronaut(int id);
    }


    public AstronautsAdapter(Context context){
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
    }


    @NonNull
    @Override
    public AstronautHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.astronaut_card_item, parent, false);
        return new AstronautHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AstronautHolder holder, int position) {
          if(astronauts != null){
              Astronaut astronaut = astronauts.get(position);
              holder.bindAstronautHolder(astronaut);
          }
    }

    @Override
    public int getItemCount() {
        if(astronauts != null){
            return astronauts.size();
        }
        return 0;
    }

    public void setAstronauts(List<Astronaut> astronautsInsSpace){
        astronauts = astronautsInsSpace;
    }

    public class AstronautHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        @BindView(R.id.epic_image)
        ImageView astronautImage;
        @BindView(R.id.epic_date_and_hour)
        TextView astronautName;

        public AstronautHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindAstronautHolder(Astronaut astronaut){

            StorageReference storageRef = firebaseStorage.getReference().child(astronaut.getPhoto());
            GlideApp.with(context).load(storageRef)
                    .error(R.drawable.astronaut_place_holder)
                    .placeholder(R.drawable.astronaut_place_holder)
                    .into(astronautImage);
            astronautName.setText(astronaut.getName());
        }

        @Override
        public void onClick(View v) {
             if(context instanceof  AstronautSelector){
                 AstronautSelector selector = (AstronautSelector) context;
                 selector.selectAstronaut(astronauts.get(getAdapterPosition()).getId());
             }
        }
    }


}
