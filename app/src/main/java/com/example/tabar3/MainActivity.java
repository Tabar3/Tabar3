package com.example.tabar3;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    Fragment f ;
    Button f1,f2,f3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment1,fragment);
        fragmentTransaction.commit();

    }

    public void one(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab");
        replaceFragment(new Charities());
    }
    public void tow(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 2222222222");
        replaceFragment(new Advertisement());
    }
    public void third(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 33333333333");
        replaceFragment(new Donations());
    }
}