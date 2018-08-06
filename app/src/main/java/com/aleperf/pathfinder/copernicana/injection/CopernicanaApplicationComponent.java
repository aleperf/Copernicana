package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;

import com.aleperf.pathfinder.copernicana.apod.ApodDetailFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodDisplayAllFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodFavoritesFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodSearchFragment;
import com.aleperf.pathfinder.copernicana.apod.ApodSummaryFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicEnhancedDetailFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicEnhancedDetailSearchFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicEnhancedFavFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicEnhancedFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicNaturalDetailFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicNaturalDetailSearchFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicNaturalFavFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicNaturalFragment;
import com.aleperf.pathfinder.copernicana.epic.EpicSearchFragment;
import com.aleperf.pathfinder.copernicana.intro.IntroActivity;
import com.aleperf.pathfinder.copernicana.intro.SummaryFragment;
import com.aleperf.pathfinder.copernicana.iss.AstronautDetailFragment;
import com.aleperf.pathfinder.copernicana.iss.AstronautsFragment;
import com.aleperf.pathfinder.copernicana.iss.IssPositionFragment;
import com.aleperf.pathfinder.copernicana.database.UpdateService;


import dagger.Component;

@CopernicanaApplicationScope
@Component(modules ={ApisServiceModule.class, ApplicationModule.class, AstroDatabaseModule.class})
public interface CopernicanaApplicationComponent {

    void inject(SummaryFragment summaryFragment);
    void inject(ApodSummaryFragment apodSummaryFragment);
    void inject(ApodDetailFragment apodDetailFragment);
    void inject(ApodSearchFragment apodSearchFragment);
    void inject(ApodFavoritesFragment apodFavoritesFragment);
    void inject(ApodDisplayAllFragment apodDisplayAllFragment);
    void inject(IntroActivity mainActivity);
    void inject(EpicNaturalFragment epicNaturalFragment);
    void inject(EpicEnhancedFragment epicEnhancedFragment);
    void inject(EpicNaturalDetailFragment epicNaturalDetailFragment);
    void inject(EpicEnhancedDetailFragment epicEnhancedDetailFragment);
    void inject(EpicNaturalFavFragment epicNaturalFavFragment);
    void inject(EpicEnhancedFavFragment epicEnhancedFavFragment);
    void inject(EpicSearchFragment epicSearchFragment);
    void inject(EpicNaturalDetailSearchFragment detailSearchFragment);
    void inject(EpicEnhancedDetailSearchFragment enhancedDetailSearchFragment);
    void inject(IssPositionFragment issPositionFragment);
    void inject(AstronautsFragment astronautsFragment);
    void inject(AstronautDetailFragment astronautDetailFragment);
    void inject (UpdateService updateService);
    Application application();
}
