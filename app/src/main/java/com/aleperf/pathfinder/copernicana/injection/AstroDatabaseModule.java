package com.aleperf.pathfinder.copernicana.injection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.aleperf.pathfinder.copernicana.CopernicanaViewModelProviderFactory;
import com.aleperf.pathfinder.copernicana.database.ApodDao;
import com.aleperf.pathfinder.copernicana.database.AstroDatabase;
import com.aleperf.pathfinder.copernicana.database.AstroRepository;
import com.aleperf.pathfinder.copernicana.database.AstroRepositoryImpl;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.network.ApisService;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApisServiceModule.class, ApplicationModule.class})
public class AstroDatabaseModule {

    private AstroDatabase astroDatabase;

    @Inject
    public AstroDatabaseModule(Application application) {
        this.astroDatabase = Room.databaseBuilder(application, AstroDatabase.class, "AstroDatabase.db").build();
    }

    @Provides
    @CopernicanaApplicationScope
    public AstroRepository provideAstroRepository(ApodDao apodDao, ApisService apisService){
        return new AstroRepositoryImpl(apodDao, apisService);
    }

    @Provides
    @CopernicanaApplicationScope
    public ApodDao provideApodDao(){
        return astroDatabase.apodDao();
    }

    @Provides
    public AstroDatabase provideAstroDatabase(){
        return astroDatabase;
    }

    @Provides
    @CopernicanaApplicationScope
    ViewModelProvider.Factory  getViewModelFactory(AstroRepository astroRepository){
        return new CopernicanaViewModelProviderFactory(astroRepository);

    }


}
