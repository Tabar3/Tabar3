package com.example.tabar3;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Fragment f ;
    ImageView a1;
    DocumentReference dRef;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView a2,a3;
    androidx.appcompat.widget.Toolbar toolbar;
    Button c,a,d2,d,chA,usA;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        d=findViewById(R.id.Don);
        c=findViewById(R.id.Char);
        a=findViewById(R.id.Adv);
        d2=findViewById(R.id.Don2);
        chA=findViewById(R.id.charAcc);
        usA=findViewById(R.id.userAcc);



        fStore = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
           try {
               id =mAuth.getCurrentUser().getUid();
           }
           catch (Exception e)
           {

               id ="null";
           }
            dRef = fStore.collection("Charities").document(id);
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    d.setVisibility(View.GONE);

                }});
            dRef = fStore.collection("Users").document(id);
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    if(!(documentSnapshot.getBoolean("accept"))){
                    d.setVisibility(View.GONE);}

                }});
            dRef = fStore.collection("Admin").document(id);
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    d.setVisibility(View.GONE);
                    chA.setVisibility(View.VISIBLE);
                    usA.setVisibility(View.VISIBLE);
                }});

        chA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, charAcc.class);
                startActivity(intent);

            }
        });
        usA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserAcc.class);
                startActivity(intent);

            }
        });
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
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
       // String id2 =mAuth.getCurrentUser().getUid();
        if (id == R.id.account) {
            dRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Intent intent = new Intent(this, Charity_Info.class);
                    intent.putExtra("CharitiesInfo", mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(this, Accounts.class);
                    intent.putExtra("UserInfo", mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }

            });

        } else if (id==R.id.login){
            if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                Toast.makeText(this,"Sorry,You already logged in !",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);}
        }else if (id==R.id.history){
            dRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Intent intent = new Intent(this, Adv2.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(this, UserHistory.class);
                    startActivity(intent);
                }

            });


        }else if (id==R.id.booked){
            dRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Intent intent = new Intent(this, CharBooked.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(this, UserBooked.class);
                    startActivity(intent);
                }

            });}
        else if (id==R.id.notifications){
            Intent intent = new Intent(this,Notifications.class);
            startActivity(intent);
        }else if (id==R.id.setting){
            Intent intent = new Intent(this,Setting.class);
            startActivity(intent);
        }else if (id==R.id.aboutUs){
            Intent intent = new Intent(this,AboutUs.class);
            startActivity(intent);
        }else if (id==R.id.Logout) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,Login.class);
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
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
        chA.setVisibility(View.GONE);
        usA.setVisibility(View.GONE);
        color();
        c.setBackground(getDrawable(R.drawable.ch1));
        c.setTextColor(Color.parseColor("#FFFFFF"));
       replaceFragment(new Charities());
       //Fragment selectedFragment = new Charities();

    }
    public void tow(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 2222222222");
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
        chA.setVisibility(View.GONE);
        usA.setVisibility(View.GONE);
        color();
        a.setBackground(getDrawable(R.drawable.ad1));
        a.setTextColor(Color.parseColor("#FFFFFF"));
        replaceFragment(new Advertisement());
    }
    public void third(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 33333333333");
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
        chA.setVisibility(View.GONE);
        usA.setVisibility(View.GONE);
        color();
        d.setBackground(getDrawable(R.drawable.dn1));
        d.setTextColor(Color.parseColor("#FFFFFF"));
         replaceFragment(new Donations());
    }
    public void third2(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 333333333332");
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
        chA.setVisibility(View.GONE);
        usA.setVisibility(View.GONE);
        color();
        d2.setBackground(getDrawable(R.drawable.d1));
        d2.setTextColor(Color.parseColor("#FFFFFF"));
        replaceFragment(new Don());
    }

    private void color(){
        a.setBackground(getDrawable(R.drawable.ad2));
        //a.setTextColor(Color.parseColor("#0060FF"));
        c.setBackground(getDrawable(R.drawable.ch2));
        c.setTextColor(Color.parseColor("#0060FF"));
        d.setBackground(getDrawable(R.drawable.dn2));
        d.setTextColor(Color.parseColor("#0060FF"));
        d2.setBackground(getDrawable(R.drawable.d2));
        d2.setTextColor(Color.parseColor("#0060FF"));


    }
}