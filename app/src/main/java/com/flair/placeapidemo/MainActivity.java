package com.flair.placeapidemo;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private PlacesClient placesClient;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyBTU7gjdUIGCdaqdncz0pW3S_UwplJYLtw");

// Create a new Places client instance.
        placesClient = Places.createClient(this);


        initView();


    }

    private void initView() {

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        imageView = findViewById(R.id.imageView);

        findViewById(R.id.btnWorkOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkManagerOne.class).build();

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);

                WorkManager.getInstance().getStatusById(oneTimeWorkRequest.getId()).observeForever(new Observer<WorkStatus>() {
                    @Override
                    public void onChanged(@Nullable WorkStatus workStatus) {
                        boolean status = workStatus.getState().isFinished();
                        Log.e("TESTING"," This is from work one and values of status - " + status);
                        Utility.writeLogFileToDevice(getApplicationContext(), " This is from work one and values of status - " + status);
                    }
                });

            }
        });

        autoCompleteTextView.setThreshold(3);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, R.layout.adapter_place_array, placesClient);
        autoCompleteTextView.setAdapter(mPlaceArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        Glide.with(this)
                .load(R.drawable.johnny_depp)
               .apply(RequestOptions.circleCropTransform())
        .into(imageView);


    }


    protected AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            assert item != null;
            final String placeId = String.valueOf(item.placeId);
        }
    };
}
