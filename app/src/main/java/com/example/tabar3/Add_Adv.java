package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Add_Adv extends AppCompatActivity  {
    EditText Des, Num,name;
    Button add;
    DocumentReference dRef;
    FirebaseFirestore fStore;
    RadioButton c1, c2, c3 ,c4 ,c5;
    ImageView img,detaImg;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    String checkArr ;
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String AdvId;
    TextView txtDeta;
    String id;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adv);
        add = findViewById(R.id.add_Adv_DB);
        Des = findViewById(R.id.AdvDes);
        name=findViewById(R.id.AdvName);
        Num = findViewById(R.id.NumF);
        c1 = findViewById(R.id.f1);
        c2 = findViewById(R.id.f2);
        c3 = findViewById(R.id.f3);
        c4 = findViewById(R.id.f4);
        c5 = findViewById(R.id.f5);
        img = findViewById(R.id.imgBook);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Des = findViewById(R.id.AdvDes);
        Intent i = getIntent();
        mAuth = FirebaseAuth.getInstance();
        id =mAuth.getCurrentUser().getUid();



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToDB();
                Intent i2 = new Intent(Add_Adv.this, MainActivity.class);
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

        dRef = fStore.collection("Charities").document(id);
        dRef.get().addOnSuccessListener((documentSnapshot2) -> {
            if (documentSnapshot2 != null && documentSnapshot2.exists()) {

        if (c1.isChecked())
            checkArr="طعام";
        else if (c2.isChecked())
            checkArr="ملابس";
        else if (c3.isChecked())
            checkArr="مفروشات و ادوات";
        else if (c4.isChecked())
            checkArr="خدمات عامة";
        else if (c5.isChecked())
            checkArr="اخرى";


        AdvId = fStore.collection("Advertisement").document().getId();
        uploadImg(mImageUri);
        Map<String, Object> itemsAdv = new HashMap<>();
        itemsAdv.put("advId", AdvId);
        itemsAdv.put("charId", id);
        itemsAdv.put("charName", documentSnapshot2.getString("charityName"));
        itemsAdv.put("advDes", Des.getText().toString());
        itemsAdv.put("advName", name.getText().toString());
        itemsAdv.put("typeOfAdv", checkArr);
        itemsAdv.put("advNum", Num.getText().toString());

        DocumentReference documentReference = fStore.collection("Advertisement").document(AdvId);
        documentReference.set(itemsAdv).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Add_Adv.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Add_Adv.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Add_Adv.this, AdvId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
                checkArr="";

            }});

    }

    private void uploadImg(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Advertisement/" + AdvId + "/mainImage.jpg");
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
    /*public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
        txtDeta.setText(date);
    }*/
}
