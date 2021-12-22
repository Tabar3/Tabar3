package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void acc(View view) {
        Intent intent = new Intent(this,SettAccount.class);
        startActivity(intent);
    }

    public void rep(View view) {
        Intent intent = new Intent(this,Report.class);
        startActivity(intent);
    }

    public void pri(View view) {
        Intent intent = new Intent(this,Privacy.class);
        startActivity(intent);
    }


}