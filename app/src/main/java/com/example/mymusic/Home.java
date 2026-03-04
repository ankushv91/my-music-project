package com.example.mymusic;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Runnable runnable;
    private List<Integer> songList;
    private int currentPosition = 0;
    private ObjectAnimator rotateAnimator;
    private ImageView albumImage;
    private ImageButton pauseBtn;
    ImageView listBtn;
    TextView songName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        seekBar = findViewById(R.id.seekBar);
        ImageButton prevBtn = findViewById(R.id.prevBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        ImageButton nextBtn = findViewById(R.id.nextBtn);
        albumImage = findViewById(R.id.img2);
// Initialize Song List
        songList = new ArrayList<>();
        songList.add(R.raw.song1);
        songList.add(R.raw.song2);
        songList.add(R.raw.song3);

// Rotation Animation
        rotateAnimator = ObjectAnimator.ofFloat(albumImage, "rotation", 0f, 360f);
        rotateAnimator.setDuration(3000);
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotateAnimator.setInterpolator(new LinearInterpolator());
// Get selected song index
        int songIndex = getIntent().getIntExtra("songIndex", 0);
        currentPosition = songIndex;
        playSong(currentPosition);

        setSongName();

// ▶ PREVIOUS
        prevBtn.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
            } else {
                currentPosition = songList.size() - 1;
            }
            playSong(currentPosition);
            setSongName();
        });
// ⏯ PAUSE / RESUME
        pauseBtn.setOnClickListener(v -> {
            if (mediaPlayer == null) return;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                rotateAnimator.pause();
            } else {
                mediaPlayer.start();
                rotateAnimator.resume();
                updateSeekBar();
            }
        });
// ⏭ NEXT
        nextBtn.setOnClickListener(v -> {
            if (currentPosition < songList.size() - 1) {
                currentPosition++;
            } else {
                currentPosition = 0;
            }
            playSong(currentPosition);
            setSongName();
        });
// 🎚 SeekBar Drag
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {

                    mediaPlayer.seekTo(progress);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
// BACK BUTTON HANDLING (Correct Place)
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        releaseMediaPlayer();
                        finish(); // Go back to SongList
                    }
                });

        listBtn = findViewById(R.id.listBtn);
        listBtn.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, Songlist.class));
        });
    }
    // Play Song
    private void playSong(int position) {
        releaseMediaPlayer();
        mediaPlayer = MediaPlayer.create(this, songList.get(position));
        mediaPlayer.start();
        rotateAnimator.start();
        pauseBtn.setImageResource(R.drawable.baseline_pause_24);
        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar();
        mediaPlayer.setOnCompletionListener(mp -> {
            if (currentPosition < songList.size() - 1) {
                currentPosition++;
            } else {
                currentPosition = 0;
            }
            playSong(currentPosition);
        });
    }
    private void updateSeekBar() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }

            }
        };
        handler.post(runnable);
    }
    private void releaseMediaPlayer() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (rotateAnimator != null) {
            rotateAnimator.cancel();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    void setSongName() {
        songName = findViewById(R.id.songName);
        switch (currentPosition) {
            case 0:songName.setText("Song 1");
                break;
            case 1:songName.setText("Song 2");
                break;
            case 2:songName.setText("Song 3");
                break;
        }
    }
}
