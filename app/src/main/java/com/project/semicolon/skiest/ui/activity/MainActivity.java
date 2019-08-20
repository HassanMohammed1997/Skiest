package com.project.semicolon.skiest.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.semicolon.skiest.Constants;
import com.project.semicolon.skiest.DataSource;
import com.project.semicolon.skiest.R;
import com.project.semicolon.skiest.adapter.WeaklyWeatherAdapter;
import com.project.semicolon.skiest.databinding.MainActivityBinding;
import com.project.semicolon.skiest.model.FakeWeaklyData;
import com.project.semicolon.skiest.model.response.WeatherResponse;
import com.project.semicolon.skiest.util.SharedPrefUtil;
import com.project.semicolon.skiest.viewmodel.WeatherViewModel;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private WeaklyWeatherAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //data binding(like ButterKnife or findViewById).
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new WeaklyWeatherAdapter(this);

        createProgressBar();


        //create new instance from view model.
        final WeatherViewModel viewModel = ViewModelProviders.of(this)
                .get(WeatherViewModel.class);

        //get country name from shared preference to pass it to api and fetch the weather data.
        String countryName = String.valueOf(SharedPrefUtil.getData(this, Constants.KEY_ADDRESS, ""));


        setupRecycler();


        //call getCurrentWeather method to fetch the weather data from view model based on country name...
        viewModel.getCurrentWeather(countryName, "", "").observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse response) {
                //check if weather response isn't null
                if (response != null) {
                    //save current temperature in Shared preference.(I use it in AppWidget)
                    SharedPrefUtil.save(MainActivity.this, Constants.KEY_TEMP, response.getMain().getTemp());

                    //visible root layout is response isn't null...
                    binding.root.setVisibility(View.VISIBLE);
                    //display location name in location edit text.
                    binding.location.setText(String.format("%s", response.getName()));

                    //get weather main from response
                    //Weather Main type: Clouds, Clear, Rain, ThunderStorm and etc...
                    String main = response.getWeather().get(0).getMain();
                    //display weather main in weather desc text view.
                    binding.weatherDesc.setText(main);

                    //get vector icon from drawable class based on weather main. (ic_clouds, ic_clear,
                    //ic_rain, ic_thunderstorm, and etc)
                    int iconId = convertMainToIcon("ic_" + main.trim().toLowerCase(), R.drawable.class);
                    if (iconId != -1) {
                        binding.weatherIcon.setImageResource(iconId);

                    } else {
                        binding.weatherIcon.setImageResource(R.drawable.ic_haze);
                    }


                    //display weather icon in weatherImageView.

                    //get current temp from response and display it.
                    binding.weatherTemp.setText(String.format("%s%s", (int) response.getMain().getTemp()
                            , getString(R.string.celsius_degree)));

                    progressDialog.dismiss();


                }


            }
        });

        //create fake weakly data (Because this api is paid, so I made this fake data).
        List<FakeWeaklyData> fakeWeaklyData = DataSource.createFakeWeaklyData();
        adapter.setList(fakeWeaklyData);


    }

    private void createProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching data");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void setupRecycler() {
        binding.weaklyWeatherRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.weaklyWeatherRecycler.setHasFixedSize(true);
        binding.weaklyWeatherRecycler.setAdapter(adapter);
    }

    private int convertMainToIcon(String main, Class<?> c) {
        try {
            Field f = c.getDeclaredField(main);
            if (f != null)
                return f.getInt(f);

            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }


    }

}
