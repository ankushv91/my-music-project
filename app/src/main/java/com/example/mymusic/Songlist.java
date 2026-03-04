package com.example.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Songlist extends AppCompatActivity {

    String[] songNames = {"Song 1", "Song 2", "Song 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);
        ListView listView = findViewById(R.id.listViewSongs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.activity_songitem,
                R.id.songName,
                songNames
        );
        listView.setAdapter(adapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position,
                                         id) -> {
            Intent intent = new Intent(Songlist.this,
                    Home.class);
            intent.putExtra("songIndex", position);
            startActivity(intent);
        });
    }
}