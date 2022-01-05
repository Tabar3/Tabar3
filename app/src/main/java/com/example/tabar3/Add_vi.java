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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_vi extends AppCompatActivity {
    CheckBox ch1,ch2;
    Button addv;
    EditText n,num;
    String id;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vi);
        ch1=findViewById(R.id.ch1);
        ch2=findViewById(R.id.ch2);
        addv=findViewById(R.id.addv);
        n=findViewById(R.id.moN);
        num=findViewById(R.id.moNu);
        Intent i = getIntent();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();

        addv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDB();
                Intent i2 = new Intent(Add_vi.this, tabar3Ch.class);
                i2.putExtra("CharId",id);
                startActivity(i2);
            }
        });


    }

String MoId;
    public void AddToDB() {

        fStore = FirebaseFirestore.getInstance();


        if (ch1.isChecked())
            arr="Bank";
        else if (ch2.isChecked())
           arr="Wallet";


        MoId = fStore.collection("Charities").document(id).collection("Bank").document().getId();

        Map<String, Object> itemsAdv = new HashMap<>();
        itemsAdv.put("moId", MoId);
        itemsAdv.put("charId", id);
        itemsAdv.put("moName", n.getText().toString());
        itemsAdv.put("type", arr);
        itemsAdv.put("moNum", num.getText().toString());

        DocumentReference documentReference =fStore.collection("Charities").document(id).collection("Bank").document(MoId);
        documentReference.set(itemsAdv).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Add_vi.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Add_vi.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Add_vi.this, MoId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });


    }

}