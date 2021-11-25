package com.example.tabar3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Fragment f ;
    Button f1,f2,f3;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    /*private void setSupportActionBar(Toolbar toolbar) {

    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.account) {
            Intent intent = new Intent(this, Accounts.class);
            startActivity(intent);
        } else if (id==R.id.login){
            if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Sorry,You already logged in !",Toast.LENGTH_SHORT).show();
            }
            else{
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);}
        }else if (id==R.id.history){
            Intent intent = new Intent(this,History.class);
            startActivity(intent);
        }else if (id==R.id.notifications){
            Intent intent = new Intent(this,Notifications.class);
            startActivity(intent);
        }else if (id==R.id.setting){
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        }else if (id==R.id.privacy){
            Intent intent = new Intent(this,Privacy.class);
            startActivity(intent);
        }else if (id==R.id.report){
            Intent intent = new Intent(this,Reports.class);
            startActivity(intent);
        }else if (id==R.id.aboutUs){
            Intent intent = new Intent(this,AboutUs.class);
            startActivity(intent);
        }else if (id==R.id.Logout) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        Toast.makeText(this,"Logout succefuly",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
        // else if (id== R.id.setting){}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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