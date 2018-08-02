package com.aleperf.pathfinder.copernicana.iss;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.model.Astronaut;

import java.util.List;

import javax.inject.Inject;

public class AstronautsViewModel extends ViewModel {

    private LiveData<List<Astronaut>> astronauts;
    private LiveData<Astronaut> astronautLDWithId;
    private int astronautId;


    AstroRepository astroRepository;

    @Inject
    public AstronautsViewModel(AstroRepository astroRepository){
        this.astroRepository = astroRepository;
        astronauts= astroRepository.getAllAstronauts();
    }


    public LiveData<List<Astronaut>> getAstronauts() {
        return astronauts;
    }

    public LiveData<Astronaut> getAstronautWithId(int id){
        if(id == astronautId && astronautLDWithId != null){
            return astronautLDWithId;
        } else {
            astronautId = id;
            astronautLDWithId = astroRepository.getAstronautWithId(id);
            return astronautLDWithId;
        }

    }

    public void reloadAstronauts(){
        astroRepository.updateRepository();
    }
}
