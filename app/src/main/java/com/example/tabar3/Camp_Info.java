package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Camp_Info extends AppCompatActivity {
    TextView txtCN,txtCD1,txtCD2;
    FirebaseFirestore fStore;
    DocumentReference dRef;
    StorageReference storageReference;
    ImageView imgInfo,delete;

    private RecyclerView mRecyclerView;
    ListenerRegistration AdvListener;
    CampAdabter campAdabter;
    ListenerRegistration ItemListListener;
    ImageView imgVi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_info);
        txtCN=findViewById(R.id.txtCampN);
        txtCD1=findViewById(R.id.txtCampD1);
        txtCD2=findViewById(R.id.txtCampD2);
        delete = findViewById(R.id.delete);
        imgInfo=findViewById(R.id.InfoCampImg);
        imgVi=findViewById(R.id.vi2);
        fStore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String s= intent.getStringExtra("Camp");
        String s1= intent.getStringExtra("Char");
        dRef = fStore.collection("Charities").document(s1).collection("Campaign").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {

                txtCN.setText(documentSnapshot.getString("campName"));
                txtCD1.setText(documentSnapshot.getString("campDes1"));
                txtCD2.setText(documentSnapshot.getString("campDes2"));
                imgVi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Camp_Info.this, Edit_Camp_Info.class);
                        intent.putExtra("EditCampInfo", s);
                        startActivity(intent);

                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dRef = fStore.collection("Charities").document(s1).collection("Campaign").document(s);
                        dRef.delete();
                        StorageReference bookReference = storageReference .child("Campaign/"+s+"/mainImage.jpg");
                        bookReference.delete();
                        Intent intent = new Intent(Camp_Info.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }});

        storageReference= FirebaseStorage.getInstance().getReference();
        if (s != null) {
            StorageReference bookReference = storageReference .child("Campaign/"+s+"/mainImage.jpg");

            bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(Camp_Info.this).load(uri).into(imgInfo);
                }
            });
        }

    }
}