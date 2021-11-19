package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Charity_Campaign extends AppCompatActivity {
    EditText eName , eDes1 , eDes2;
    Button b,mButtonChooseImage;
    ImageView img;
    FirebaseFirestore fStore;
    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String CampId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_campaign);
        img=findViewById(R.id.imgCamp);
        eName=findViewById(R.id.CampName);
        eDes1=findViewById(R.id.CampDes1);
        eDes2=findViewById(R.id.CampDes2);
        b=findViewById(R.id.add_Camp_DB);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AddToDB();
                Intent i2 = new Intent(Charity_Campaign.this, MainActivity.class);
                startActivity(i2);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            img.setImageURI(mImageUri);

        }
    }


    public void AddToDB() {
        fStore = FirebaseFirestore.getInstance();
        CampId=fStore.collection("Charities").document("KhAq43bEv0ZEdD2DyWRE").collection("Campaign").document().getId();

        Map<String, Object> itemsCamp = new HashMap<>();
        itemsCamp.put("campId", CampId);
        itemsCamp.put("campName", eName.getText().toString());
        itemsCamp.put("campDes1", eDes1.getText().toString());
        itemsCamp.put("campDes2", eDes2.getText().toString());


        DocumentReference documentReference = fStore.collection("Charities").document("KhAq43bEv0ZEdD2DyWRE").collection("Campaign").document(CampId);
        documentReference.set(itemsCamp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Charity_Campaign.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Charity_Campaign.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Charity_Campaign.this, CampId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        uploadImg(mImageUri);

    }

    private void uploadImg(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Campaign/" + CampId + "/mainImage.jpg");
        riversRef.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploded", Snackbar.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage:" + (int) progressPercent + "%");
            }
        });


    }



}