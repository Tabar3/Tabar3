package com.example.tabar3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Fragment f ;
    ImageView a1;
    DocumentReference dRef;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    FirebaseUser user;
    TextView a2,a3,HeaderUN;
    LinearLayout l1;
    androidx.appcompat.widget.Toolbar toolbar;
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
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        fStore=FirebaseFirestore.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null){
        if (!user.isEmailVerified()){
            AlertDialog.Builder PasswordResetD= new AlertDialog.Builder(MainActivity.this);
            PasswordResetD.setTitle("verify account ");
            PasswordResetD.setMessage("Verify account now?");
            PasswordResetD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*Intent sendemail=new Intent(Intent.ACTION_MAIN);
                    sendemail.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(sendemail);*/
                    FirebaseUser FUser=mAuth.getCurrentUser();
                    FUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,"verification email sent",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","OnFailer:Email not sent"+e.getMessage());
                        }
                    });
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://mail.google.com/"));
                    startActivity(intent);
                }
            });
            PasswordResetD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            PasswordResetD.create().show();
        }}
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.HeaderUN);
        navigationView.setNavigationItemSelectedListener(this);
//////////////////////////////////////////////////////لوضع الاسم والصورة على الheader في الnavigation menu
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            Random r=new Random();
            username.setText("Guest"+r.nextInt(100));
        }else {

            DocumentReference docRef = fStore.collection("Users").document(mAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (user.getUid().toString().equals(document.getString("UserId"))) {
                                username.setText(document.getString("UserName"));
                            } else {
                                DocumentReference docRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                if (user.getUid().toString().equals(document.getString("charityId"))) {
                                                    username.setText(document.getString("charityName"));
                                                }
                                            } else {
                                                Log.d("LOGGER", "No such document");
                                            }
                                        } else {
                                            Log.d("LOGGER", "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("LOGGER", "No such document");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });

        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            Intent intent = new Intent(this, Accounts.class);
            startActivity(intent);
            /*dRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
            dRef.get().addOnSuccessListener((documentSnapshot) -> {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Intent intent = new Intent(this, Charity_Info.class);
                    intent.putExtra("CharitiesInfo", mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(this, Accounts.class);
                    startActivity(intent);
                }

            });*/

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
            Intent intent = new Intent(this, UserHistory.class);
            startActivity(intent);
        }else if (id==R.id.notifications){
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
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
       replaceFragment(new Charities());
    }
    public void tow(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 2222222222");
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
        replaceFragment(new Advertisement());
    }
    public void third(View view) {
        Log.d("tag","yaaaaaaaaaaaaa raaaaaaaab 33333333333");
        a1.setVisibility(View.GONE);
        a2.setVisibility(View.GONE);
        a3.setVisibility(View.GONE);
         replaceFragment(new Donations());
    }
}