package com.aleperf.pathfinder.copernicana;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aleperf.pathfinder.copernicana.apod.ApodDisplayAllViewModel;
import com.aleperf.pathfinder.copernicana.apod.ApodFavoritesViewModel;
import com.aleperf.pathfinder.copernicana.apod.ApodSearchViewModel;
import com.aleperf.pathfinder.copernicana.apod.ApodViewModel;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.epic.EpicEnhancedViewModel;
import com.aleperf.pathfinder.copernicana.epic.EpicNaturalViewModel;
import com.aleperf.pathfinder.copernicana.intro.IntroViewModel;
import com.aleperf.pathfinder.copernicana.iss.AstronautsViewModel;
import com.aleperf.pathfinder.copernicana.iss.IssPositionViewModel;

import javax.inject.Inject;

public class CopernicanaViewModelProviderFactory implements ViewModelProvider.Factory {

    private final AstroRepository astroRepository;

    @Inject
    public CopernicanaViewModelProviderFactory(AstroRepository astroRepository) {
        this.astroRepository = astroRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ApodViewModel.class)) {
            return (T) new ApodViewModel(astroRepository);
        } else if (modelClass.isAssignableFrom(IntroViewModel.class)) {
            return (T) new IntroViewModel(astroRepository);
        } else if (modelClass.isAssignableFrom(ApodSearchViewModel.class)) {
            return (T) new ApodSearchViewModel(astroRepository);
        } else if (modelClass.isAssignableFrom(ApodFavoritesViewModel.class)) {
            return (T) new ApodFavoritesViewModel(astroRepository);
        } else if (modelClass.isAssignableFrom(ApodDisplayAllViewModel.class)) {
            return (T) new ApodDisplayAllViewModel(astroRepository);
        } else if (modelClass.isAssignableFrom(IssPositionViewModel.class)) {
            return (T) new IssPositionViewModel(astroRepository);
        } else if(modelClass.isAssignableFrom(AstronautsViewModel.class)){
            return (T) new AstronautsViewModel(astroRepository);
        } else if(modelClass.isAssignableFrom(EpicNaturalViewModel.class)){
            return (T) new EpicNaturalViewModel(astroRepository);
        } else if(modelClass.isAssignableFrom(EpicEnhancedViewModel.class)){
            return (T) new EpicEnhancedViewModel(astroRepository);
        }
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
