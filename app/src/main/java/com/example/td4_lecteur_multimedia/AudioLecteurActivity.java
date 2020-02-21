package com.example.td4_lecteur_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioLecteurActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    @BindView(R.id.music_name)
    TextView musicNameTv;

    @BindView(R.id.play_audio_button)
    ImageButton playAudioButton;

    @BindView(R.id.pause_audio_button)
    ImageButton pauseAudioButton;

    @BindView(R.id.exit_button)
    Button exitButton;

    private MediaPlayer audioPlayer;

    private String nomMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_lecteur);
        ButterKnife.bind(this);

        Bundle params = getIntent().getExtras();
        String localisationRessource = params.getString(getResources().getString(R.string.key_ressource_localisation));

        if (localisationRessource.equals(getResources().getString(R.string.ressource_locale))) {
            int pathRessource = params.getInt(getResources().getString(R.string.key_ressource_path));
            audioPlayer = MediaPlayer.create(this, pathRessource);

            TypedValue value = new TypedValue();
            getResources().getValue(pathRessource, value, true);
            nomMusic = value.string.toString().split("/")[2];
        } else if (localisationRessource.equals(getResources().getString(R.string.ressource_distante))) {
            String pathRessource = params.getString(getResources().getString(R.string.key_ressource_path));
            audioPlayer = new MediaPlayer();
            audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                audioPlayer.setDataSource(pathRessource);
                audioPlayer.setOnPreparedListener(this);
                audioPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] deconstructedPath = pathRessource.split("/");
            nomMusic = deconstructedPath[deconstructedPath.length - 1];
        } else {
            finish();
        }

        playAudioButton.setOnClickListener(new PlayAudio());
        pauseAudioButton.setOnClickListener(new PauseAudio());
        exitButton.setOnClickListener(new ExitApplication());

        playAudioButton.setEnabled(false);
        pauseAudioButton.setEnabled(false);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Toast.makeText(getApplicationContext(), "Fin de la préparation !", Toast.LENGTH_LONG);
        int duree = audioPlayer.getDuration();
        int seconds = duree / 1000;
        int minute = seconds / 60;

        String time = "( " + minute + ":" + (seconds - minute * 60) + " )";
        musicNameTv.setText(nomMusic + " " + time);

        playAudioButton.setEnabled(true);
        pauseAudioButton.setEnabled(true);
    }

    private class PlayAudio implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            audioPlayer.start();
        }
    }

    private class PauseAudio implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            audioPlayer.pause();
        }
    }

    private class ExitApplication implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            audioPlayer.stop();
            finish();
        }
    }

}
