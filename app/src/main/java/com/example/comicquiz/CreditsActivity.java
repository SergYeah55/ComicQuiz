package com.example.comicquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreditsActivity extends AppCompatActivity {

    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        goBack = (Button) findViewById(R.id.buttonBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(toMainActivity);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent toMainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }
}