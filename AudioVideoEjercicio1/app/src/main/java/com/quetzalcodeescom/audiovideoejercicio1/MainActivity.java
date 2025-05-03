package com.quetzalcodeescom.audiovideoejercicio1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_AUDIO_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private static final int STORAGE_PERMISSION_CODE = 100;

    private Button btnSelectAudio, btnSelectVideo;
    private VideoView videoView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificar y solicitar permisos de almacenamiento
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }

        // Inicializar vistas
        btnSelectAudio = findViewById(R.id.btnSelectAudio);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);
        videoView = findViewById(R.id.videoView);

        // Configurar listeners
        btnSelectAudio.setOnClickListener(v -> selectAudio());
        btnSelectVideo.setOnClickListener(v -> selectVideo());
    }

    private void selectAudio() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_AUDIO_REQUEST);
    }

    private void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();

            if (requestCode == PICK_AUDIO_REQUEST) {
                playAudio(selectedFileUri);
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                playVideo(selectedFileUri);
            }
        }
    }

    private void playAudio(Uri audioUri) {
        try {
            // Liberar recursos previos
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.prepare();
            mediaPlayer.start();

            Toast.makeText(this, "Reproduciendo audio...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al reproducir audio", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void playVideo(Uri videoUri) {
        try {
            // Liberar recursos previos
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);

            // Configurar controles de reproducción
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.start();
            Toast.makeText(this, "Reproduciendo video...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al reproducir video", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}