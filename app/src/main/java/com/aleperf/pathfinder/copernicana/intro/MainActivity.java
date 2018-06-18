package ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.intro.MainActivityViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    MutableLiveData<Apod> apodLiveData;
    @BindView(R.id.apod_image)
    ImageView apodImage;
    @BindView(R.id.apod_date)
    TextView date;
    @BindView(R.id.apod_explanation)
    TextView explanation;
    @BindView(R.id.apod_title)
    TextView title;
    @BindView(R.id.apod_copyright)
    TextView copyright;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        apodLiveData = model.getApod();
        subscribeApod();
    }

    private void subscribeApod() {
        Observer<Apod> apodObserver = new Observer<Apod>() {
            @Override
            public void onChanged(@Nullable Apod apodResult) {
                if(apodResult != null){
                    Picasso.get().load(apodResult.getUrl()).error(R.drawable.nasa_43566_unsplash)
                            .placeholder(R.drawable.nasa_43566_unsplash).into(apodImage);
                    if(apodResult.getCopyright() != null){
                        copyright.setVisibility(View.VISIBLE);
                        copyright.setText(String.format(getString(R.string.apod_copyright_label_image),
                                apodResult.getCopyright()));
                    } else {
                        copyright.setVisibility(View.GONE);
                    }

                    date.setText(apodResult.getDate());
                    explanation.setText(apodResult.getExplanation());
                    title.setText(apodResult.getTitle());
                }
            }
        };

        apodLiveData.observe(this, apodObserver);
    }
}
