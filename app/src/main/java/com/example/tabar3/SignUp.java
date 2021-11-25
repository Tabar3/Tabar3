package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    EditText UserS,passS,UserName,PhoneN,Location;
    Button signUpB;
    RadioGroup G1;
    RadioButton ChC,DonC;
    TextView AccT;
    String CharId,UsersId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        UserS=findViewById(R.id.userSign);
        passS=findViewById(R.id.passSign);
        UserName=findViewById(R.id.userNameS);
        Location=findViewById(R.id.LocationSign);
        PhoneN=findViewById(R.id.PhoneNSign);
        G1=findViewById(R.id.G1);
        AccT=findViewById(R.id.AccT);
        ChC=findViewById(R.id.CharCheck);
        DonC=findViewById(R.id.DonatCheck);
        PhoneN=findViewById(R.id.PhoneNSign);
        signUpB=findViewById(R.id.signUpB);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(this,"You already have account",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }
    public void AddToDB() {

        fStore = FirebaseFirestore.getInstance();

        if (ChC.isChecked()){
        CharId = fStore.collection("Charities").document().getId();
        Map<String, Object> CharUsers = new HashMap<>();
        CharUsers.put("CharId", FirebaseAuth.getInstance().getUid());
        CharUsers.put("CahrityEmail", UserS.getText().toString());
        CharUsers.put("CharityPhone", PhoneN.getText().toString());
        CharUsers.put("CharityName", UserName.getText().toString());
        CharUsers.put("CharityLoc",Location.getText().toString());

        DocumentReference documentReference = fStore.collection("Charities").document(CharId);
        documentReference.set(CharUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SignUp.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(SignUp.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(SignUp .this, CharId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });}
        if (DonC.isChecked()){
            UsersId = fStore.collection("Users").document().getId();
            Map<String, Object> DonUsers = new HashMap<>();
            DonUsers.put("UserId", FirebaseAuth.getInstance().getUid());
            DonUsers.put("UserEmail", UserS.getText().toString());
            DonUsers.put("UserPhone", PhoneN.getText().toString());
            DonUsers.put("UserName", UserName.getText().toString());
            DonUsers.put("UserLoc",Location.getText().toString());

            DocumentReference documentReference2 = fStore.collection("Users").document(UsersId);
            documentReference2.set(DonUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(SignUp.this, "mabrooooooook", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(SignUp.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                    Toast.makeText(SignUp .this, UsersId, Toast.LENGTH_LONG).show();
                    Log.d("myTag", e.getMessage());

                }
            });}

    }

    public void signUp(View view) {
        String email= String.valueOf(UserS.getText()).trim();
        String password= String.valueOf(passS.getText()).trim();
        if (String.valueOf(UserS.getText()).isEmpty()){
            UserS.setError("Email required!");
            return;
        }
        if (String.valueOf(passS.getText()).isEmpty()){
            passS.setError("password required!");
            return;
        }

        if (String.valueOf(passS.getText()).length()<6){
            passS.setError("email must cantain more than 5 digits");
            return;
        }
        if (String.valueOf(UserName.getText()).isEmpty()){
            UserName.setError("Phone number required!");
            return;
        }
        if (String.valueOf(PhoneN.getText()).isEmpty()){
            PhoneN.setError("User name required!");
            return;
        }
        if (String.valueOf(Location.getText()).isEmpty()){
            Location.setError("Location required!");
            return;
        }
        if (!ChC.isChecked() & !DonC.isChecked()){
            AccT.setError("Please choose account type");
            return;
        }


        //createAccount(email,password);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this,"succeeeeeded",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    AddToDB();
                    finish();
                }else{
                    Toast.makeText(SignUp.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();}
            }
        });

    }


    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }



}