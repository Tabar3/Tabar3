package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SettAccount extends AppCompatActivity {

   /*  EditText phone,name,location ;
     Button login ;
    FirebaseFirestore fStore;
    String AdvId;

  /*  @Override

    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett_account);
    }

    private void getViews() {
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        phone = findViewById(R.id.phone);

        login = findViewById(R.id.login);


    }

//try to use FirebaseFire... then getinst....  then getDocument then the FirebaseAuth.getUID;
//Remember me <3
      /*  public void ChangeOnDB() {
        fStore = FirebaseFirestore.getInstance();
        AdvId = fStore.collection("Charities").document().getId();
        Map<String, Object> changeI = new HashMap<>();
        if (location.getText()!=null)
            changeI.replace("CharityLoc",location.getText());
        if (phone.getText()!=null)
            changeI.replace("CharityPhone",phone.getText());
        if (name.getText()!=null)
            changeI.replace("CharityName",name.getText());

        DocumentReference documentReference = fStore.collection("Charities").document(AdvId);
        documentReference.set(changeI).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SettAccount.this, "succefully changed", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(SettAccount.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(SettAccount.this, AdvId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
    }

    public void SaveCh(View view) {
        if (location.getText()!=null || name.getText()!=null || phone.getText()!=null){
            ChangeOnDB();}
        else {
            Toast.makeText(this,"Nothing changed!",Toast.LENGTH_LONG).show();
        }
   }*/
}