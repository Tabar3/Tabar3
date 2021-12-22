package com.example.tabar3;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
@RequiresApi(api = Build.VERSION_CODES.N)
public class SettAccount extends AppCompatActivity {
   /*  EditText phone,name,location ;
     Button login;
    FirebaseFirestore fStore;
    String AdvId;
  /*  @Override
    /*protected void onCreate(Bundle savedInstanceState) {
=======

public class SettAccount extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText Name,Location,PhoneN,NewEmail,PasCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett_account);
    fStore=FirebaseFirestore.getInstance();
    mAuth=FirebaseAuth.getInstance();
    user=mAuth.getCurrentUser();
    Name=findViewById(R.id.name);
    Location=findViewById(R.id.location);
    PhoneN=findViewById(R.id.phone);
    PasCon=findViewById(R.id.PasswordConfirm);
    NewEmail=findViewById(R.id.Newmail);
    }


    private void getViews() {
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        phone = findViewById(R.id.phone);
        login = findViewById(R.id.login);


    public void SaveCs(View view) {
       if (FirebaseAuth.getInstance().getCurrentUser()==null){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
            Toast.makeText(this,"عذرا يجب تسجيل الدخول!",Toast.LENGTH_SHORT).show();
        }
    if (!String.valueOf(Name.getText()).isEmpty()){
        Changes("UserName","charityName",Name);
    }
        if (!String.valueOf(Location.getText()).isEmpty()){
            Changes("UserLoc","charityLoc",Location);
        }
        if (!String.valueOf(PhoneN.getText()).isEmpty()){
            Changes("UserPhone","charityPhone",PhoneN);
        }
    if (String.valueOf(PhoneN.getText()).isEmpty()&&String.valueOf(Location.getText()).isEmpty()&&String.valueOf(Name.getText()).isEmpty())
    {


        Toast.makeText(this, "لم يتم تحديث البيانات,الحقول فارغة", Toast.LENGTH_SHORT).show();
    }
    }

    public void changE(View view) {
        if (PasCon.getText().toString().isEmpty()){
            PasCon.setError("يجب تاكيد كلمة المرور");
        return;
        }
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(),PasCon.getText().toString() ); // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        //Now change your email address \\
                        //----------------Code for Changing Email Address----------\\
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(NewEmail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User email address updated.");
                                        }
                                    }
                                });
                        //----------------------------------------------------------\\
                    }
                });
    Toast.makeText(SettAccount.this,"تم تغيير البريد الالكتروني بنجاح",Toast.LENGTH_LONG).show();
    Changes("UserEmail","charityEmail",NewEmail);
    }

public void Changes(String y,String z,EditText x){
    DocumentReference docRef = fStore.collection("Users").document(mAuth.getCurrentUser().getUid());
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document != null) {
                    if (user.getUid().toString().equals(document.getString("UserId"))){
                        //String IdAdv;
                        //IdAdv = fStore.collection("Users").document().getId();
                        Map<String, Object> itemsAdv = new HashMap<>();
                        fStore.collection("Users").document(mAuth.getCurrentUser().getUid()).update(y,x.getText().toString());
                        Toast.makeText(SettAccount.this, "نم التغيير بنجاح", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DocumentReference docRef = fStore.collection("Charities").document(mAuth.getCurrentUser().getUid());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document != null) {
                                        if (user.getUid().toString().equals(document.getString("charityId")))
                                        {
                                            fStore.collection("Charities").document(mAuth.getCurrentUser().getUid()).update(z,x.getText().toString());
                                            Toast.makeText(SettAccount.this, "نم التغيير بنجاح", Toast.LENGTH_SHORT).show();
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
}

