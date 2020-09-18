package com.example.myclub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myclub.Activity.Field.ActivityMainField;
import com.example.myclub.Activity.Player.ActivityMainPlayer;
import com.example.myclub.Activity.Team.ActivityMainTeam;
import com.example.myclub.R;

public class ActivityHome extends AppCompatActivity {

    private  CardView cardProfile;
    private  CardView cardTeam;
    private  CardView cardListTeam;
    private  CardView cardListField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cardProfile  = findViewById( R.id.cardProfile);
        cardTeam  = findViewById( R.id.cardTeams);
        cardListTeam  = findViewById( R.id.cardListTeam);
        cardListField  = findViewById( R.id.cardListField);

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainPlayer.class);
                startActivity(intent);
            }
        });

        cardTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainTeam.class);
                startActivity(intent);
            }
        });

        cardListTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainTeam.class);
                startActivity(intent);
            }
        });

        cardListField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityMainField.class);
                startActivity(intent);
            }
        });


    }
}