package com.example.td4_lecteur_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoLecteurActivity extends AppCompatActivity {

    @BindView(R.id.video)
    VideoView videoPlayer;

    @BindView(R.id.play_video_button)
    ImageButton playVideoButton;

    @BindView(R.id.pause_video_button)
    ImageButton pauseVideoButton;

    @BindView(R.id.exit_button)
    Button exitButton;

    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lecteur);
        ButterKnife.bind(this);


        Bundle params = getIntent().getExtras();
        String localisationRessource = params.getString(getResources().getString(R.string.key_ressource_localisation));

        if (localisationRessource.equals(getResources().getString(R.string.ressource_locale))) {
            int pathRessource = params.getInt(getResources().getString(R.string.key_ressource_path));
            path = Uri.parse("android.resource://" + getPackageName() + "/" + pathRessource);
        } else if (localisationRessource.equals(getResources().getString(R.string.ressource_distante))) {
            String pathRessource = params.getString(getResources().getString(R.string.key_ressource_path));
            path = Uri.parse(pathRessource);
        } else {
            finish();
        }

        videoPlayer.setVideoURI(path);

        exitButton.setOnClickListener(new ExitApplication());

        playVideoButton.setOnClickListener(new PlayVideo());
        pauseVideoButton.setOnClickListener(new PauseVideo());
    }

    private class ExitApplication implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            videoPlayer.stopPlayback();
            finish();
        }
    }

    private class PlayVideo implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            videoPlayer.start();
        }
    }

    private class PauseVideo implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            videoPlayer.pause();
        }
    }
}
