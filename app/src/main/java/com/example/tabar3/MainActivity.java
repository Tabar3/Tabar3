package com.example.tabar3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Fragment f ;
    DocumentReference dRef;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    FirebaseUser user;
    private  FirebaseStorage storage;
    private StorageReference reference;
    TextView HeaderUN,dd;
    Uri imageUri;
    androidx.appcompat.widget.Toolbar toolbar;
    Button c,a,d2,d;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        d = findViewById(R.id.Don);
        c = findViewById(R.id.Char);
        a = findViewById(R.id.Adv);
        d2 = findViewById(R.id.Don2);
        dd = findViewById(R.id.dd);
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        color();
        c.setBackground(getDrawable(R.drawable.ch1));
        replaceFragment(new Charities());

        try {
            id = mAuth.getCurrentUser().getUid();
        } catch (Exception e) {

            id = "null";
        }
        dRef = fStore.collection("Charities").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                d.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);

            }
        });
        dRef = fStore.collection("Users").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                if (!(documentSnapshot.getBoolean("accept"))) {
                    d.setVisibility(View.GONE);
                    dd.setVisibility(View.GONE);
                }

            }
        });
        dRef = fStore.collection("Admin").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
                d.setVisibility(View.GONE);
                dd.setVisibility(View.GONE);

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.HeaderUN);
        ImageView img = (ImageView) header.findViewById(R.id.UserIM);


        navigationView.setNavigationItemSelectedListener(this);
//////////////////////////////////////////////////////لوضع الاسم والصورة على الheader في الnavigation menu

            dRef = fStore.collection("Users").document(id);
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    if (id.equals(documentSnapshot.getString("UserId"))) {
                        username.setText(documentSnapshot.getString("UserName"));
                        StorageReference bookReference = storageReference.child("User/" + id + "/mainImage.jpg");

                        bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(header.getContext()).load(uri).into(img);
                            }
                        });

                    }
                } else {

                    dRef = fStore.collection("Charities").document(id);
                    dRef.get().addOnSuccessListener((documentSnapshot2) -> {
                        if (documentSnapshot2 != null && documentSnapshot2.exists()) {
                            if (id.equals(documentSnapshot2.getString("charityId"))) {
                                username.setText(documentSnapshot2.getString("charityName"));
                                StorageReference bookReference = storageReference.child("Charities/" + id + "/mainImage.jpg");

                                bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(MainActivity.this).load(uri).into(img);
                                    }
                                });

                            }
                        }


                    });
                }


            });
        }



    /*private void setSupportActionBar(Toolbar toolbar) {

    }*/
    private void SetUsername(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNamePref = preferences.getString("HeaderUN", "DEFAULT");
        //Change the Username in R.layout.header
    }
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
            if (mAuth.getCurrentUser()==null){
                Toast.makeText(this, "يرجى تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
                dRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
                dRef.get().addOnSuccessListener((documentSnapshot) -> {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Intent intent = new Intent(this, Charity_Info.class);
                        intent.putExtra("CharitiesInfo", mAuth.getCurrentUser().getUid());
                        startActivity(intent);
                    } else {
                        dRef = fStore.collection("Users").document(mAuth.getCurrentUser().getUid());
                        dRef.get().addOnSuccessListener((documentSnapshot2) -> {
                            if (documentSnapshot2 != null && documentSnapshot2.exists()) {
                                Intent intent = new Intent(this, Accounts.class);
                                intent.putExtra("UserInfo", mAuth.getCurrentUser().getUid());
                                startActivity(intent);
                            } else {

                                Intent intent = new Intent(this, Admin.class);
                                startActivity(intent);
                            }
                        });

                    }

                });
            }
        } else if (id==R.id.Login){
            if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                Toast.makeText(this,"Sorry,You already logged in !",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);}
        }else if (id==R.id.history){
            if (mAuth.getCurrentUser()==null){
            Toast.makeText(this, "يرجى تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }else {
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


        }}else if (id==R.id.booked){
            if (mAuth.getCurrentUser()==null){
                Toast.makeText(this, "يرجى تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else {
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

            });}}
        else if (id==R.id.setting){
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
        color();
        c.setBackground(getDrawable(R.drawable.ch1));
       replaceFragment(new Charities());
       //Fragment selectedFragment = new Charities();

    }
    public void tow(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 2222222222");
        color();
        a.setBackground(getDrawable(R.drawable.ad1));
        replaceFragment(new Advertisement());
    }
    public void third(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 33333333333");
        color();
        d.setBackground(getDrawable(R.drawable.dn1));
         replaceFragment(new Donations());
    }
    public void third2(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 333333333332");

        color();
        d2.setBackground(getDrawable(R.drawable.d1));
        replaceFragment(new Don());
    }

    private void color(){
        a.setBackground(getDrawable(R.drawable.ad2));
        c.setBackground(getDrawable(R.drawable.ch2));
        d.setBackground(getDrawable(R.drawable.dn2));
        d2.setBackground(getDrawable(R.drawable.d2));


    }
}