package com.example.td4_lecteur_multimedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.audio_spinner)
    Spinner audioSpinner;
    @BindView(R.id.audio_image_view)
    ImageView audioImageView;
    @BindView(R.id.video_spinner)
    Spinner videoSpinner;
    @BindView(R.id.video_image_view)
    ImageView videoImageView;

    int audioItemPosition;
    int videoItemPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.audio_files, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        audioSpinner.setAdapter(adapter);
        audioSpinner.setOnItemSelectedListener(new AudioTrackSelected());

        adapter = ArrayAdapter.createFromResource(this,
                R.array.video_files, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        videoSpinner.setAdapter(adapter);
        videoSpinner.setOnItemSelectedListener(new VideoSelected());

        audioImageView.setOnClickListener(new AudioButton());
        videoImageView.setOnClickListener(new VideoButton());
    }

    private class AudioTrackSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            audioItemPosition = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class VideoSelected implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            videoItemPosition = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class AudioButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), AudioLecteurActivity.class);
            if (audioItemPosition == 0) {
                String keyLocalisation = getResources().getString(R.string.key_ressource_localisation);
                String valueLocalisation = getResources().getString(R.string.ressource_locale);
                intent.putExtra(keyLocalisation, valueLocalisation);

                String keyPath = getResources().getString(R.string.key_ressource_path);
                intent.putExtra(keyPath, R.raw.tchaikovsky);
            } else if (audioItemPosition == 1) {
                String keyLocalisation = getResources().getString(R.string.key_ressource_localisation);
                String valueLocalisation = getResources().getString(R.string.ressource_distante);
                intent.putExtra(keyLocalisation, valueLocalisation);

                String keyPath = getResources().getString(R.string.key_ressource_path);
                intent.putExtra(keyPath, "https://www.iutbayonne.univ-pau.fr/~dalmau/testTPs/Walkyries.mp3");
            }
            startActivity(intent);
        }
    }

    private class VideoButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), VideoLecteurActivity.class);
            if (videoItemPosition == 0) {
                String keyLocalisation = getResources().getString(R.string.key_ressource_localisation);
                String valueLocalisation = getResources().getString(R.string.ressource_locale);
                intent.putExtra(keyLocalisation, valueLocalisation);

                String keyPath = getResources().getString(R.string.key_ressource_path);
                intent.putExtra(keyPath, R.raw.aeon_flux);
            } else if (videoItemPosition == 1) {
                String keyLocalisation = getResources().getString(R.string.key_ressource_localisation);
                String valueLocalisation = getResources().getString(R.string.ressource_distante);
                intent.putExtra(keyLocalisation, valueLocalisation);

                String keyPath = getResources().getString(R.string.key_ressource_path);
                intent.putExtra(keyPath, "https://www.iutbayonne.univ-pau.fr/~dalmau/testTPs/Pixar_for_the_birds.3gp");
            }
            startActivity(intent);
        }
    }
}
