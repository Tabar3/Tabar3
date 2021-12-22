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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.HashMap;
import java.util.Map;

public class Edit_Cat_Info extends AppCompatActivity {
    TextView f1 , f3 , clo1 , t1, t3, s1, s3;
    EditText fe2,fe4,cloe2,desE,te2,te4,se2,se4;
    RadioButton c1,c2,c3,cc,cc1,cc2;
    RadioGroup r1,r2;
    DocumentReference dRef;
    FirebaseFirestore fStore;
    String  categotyId,radio1;
    Button add_DB,mButtonChooseImage;
    FirebaseAuth mAuth;
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ImageView img,detaImg;
    private static final int PICK_IMAGE_REQUEST = 1;
    int x;
    String s,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cat_info);
        Var();
        fStore=FirebaseFirestore.getInstance();
        Intent i = getIntent();
        x=0;
        s = i.getStringExtra("EditCatInfo");
        s2= i.getStringExtra("UserId");
        dRef = fStore.collection("Users").document(s2).collection("category").document(s);
        dRef.get().addOnSuccessListener((documentSnapshot) -> {
            if (documentSnapshot != null && documentSnapshot.exists()) {
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
                x=1;
            }
        });
                storageReference = FirebaseStorage.getInstance().getReference();
                if (s != null) {
                    StorageReference bookReference = storageReference.child("Categoty/"+s+"/mainImage.jpg");

                    bookReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(Edit_Cat_Info.this).load(uri).into(img);
                        }
                    });
                }

        if(i.getStringExtra("type").equals("food_category")){
            food_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food_cat_DB();
                    Intent i2 = new Intent(Edit_Cat_Info.this,Cat_Info.class);
                    startActivity(i2);
                }
            });
        }
        else if(i.getStringExtra("type").equals("clothe_category")){
            clo_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clo_cat_DB();
                    Intent i2 = new Intent(Edit_Cat_Info.this,Cat_Info.class);
                    startActivity(i2);
                }
            });
        }
        else if(i.getStringExtra("type").equals("tool_category")){
            tools_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tool_cat_DB();
                    Intent i2 = new Intent(Edit_Cat_Info.this,Cat_Info.class);
                    startActivity(i2);
                }
            });}
        else if(i.getStringExtra("type").equals("serves_category")){
            ser_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ser_cat_DB();
                    Intent i2 = new Intent(Edit_Cat_Info.this,Cat_Info.class);
                    startActivity(i2);
                }
            });}
        else {
            ser_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    other_cat_DB();
                    Intent i2 = new Intent(Edit_Cat_Info.this,Cat_Info.class);
                    startActivity(i2);
                }
            });

        }

    }
        else
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();



        });}
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

    public void Var(){
        f1=findViewById(R.id.f1);
        f3=findViewById(R.id.f3);
        fe2=findViewById(R.id.f2);
        fe4=findViewById(R.id.f4);
        clo1=findViewById(R.id.clo1);
        cloe2=findViewById(R.id.clo2);
        desE=findViewById(R.id.des);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        cc=findViewById(R.id.cc);
        cc1=findViewById(R.id.cc1);
        cc2=findViewById(R.id.cc2);
        t1=findViewById(R.id.t1);
        t3=findViewById(R.id.t3);
        te2=findViewById(R.id.te2);
        te4=findViewById(R.id.te4);
        s1=findViewById(R.id.s1);
        s3=findViewById(R.id.s3);
        se2=findViewById(R.id.se2);
        se4=findViewById(R.id.se4);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        add_DB = findViewById(R.id.add_Cate_DB);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        img=findViewById(R.id.imgAddCate);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    public void food_cat_DB(){
        fStore = FirebaseFirestore.getInstance();

        if(c1.isChecked())
            radio1="منزلي";
        if(c2.isChecked())
            radio1="جاهز";
        if(c3.isChecked())
            radio1="اخر";

        categotyId = fStore.collection("Users").document(s2).collection("category").document().getId();
        if(x==1){
            uploadImg(mImageUri); }


        DocumentReference documentReference = fStore.collection("Users")
                .document(s2).collection("category").document(s);
        documentReference.update("name", fe2.getText().toString(),"Des", desE.getText().toString(),
                "type", radio1,"numHuman", fe4.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Cat_Info.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        radio1="";
    }

    public void clo_cat_DB(){
        fStore = FirebaseFirestore.getInstance();

        if(cc1.isChecked())
            radio1="صيفي";
        if(cc.isChecked())
            radio1="شتوي";
        if(cc2.isChecked())
            radio1="صيفي و شتوي";

        if(x==1){
            uploadImg(mImageUri); }


        DocumentReference documentReference = fStore.collection("Users")
                .document(s2).collection("category").document(s);
        documentReference.update("numclothe", cloe2.getText().toString(),"Des", desE.getText().toString()
        ,"type", radio1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Cat_Info.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        radio1="";
    }
    public void tool_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(x==1){
            uploadImg(mImageUri); }

        DocumentReference documentReference = fStore.collection("Users")
                .document(s2).collection("category").document(s);
        documentReference.update("toolAge", te4.getText().toString(),"Des", desE.getText().toString(),
                "type", te2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Cat_Info.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });

    }
    public void ser_cat_DB(){
        fStore = FirebaseFirestore.getInstance();

        if(x==1){
            uploadImg(mImageUri); }

        DocumentReference documentReference = fStore.collection("Users")
                .document(s2).collection("category").document(s);
        documentReference.update("name", se2.getText().toString(),"Des", desE.getText().toString(),
                "type", se4.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Cat_Info.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });

    }

    public void other_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(x==1){
            uploadImg(mImageUri); }

        DocumentReference documentReference = fStore.collection("Users")
                .document(s2).collection("category").document(s);
        documentReference.update("name", se2.getText().toString(),"Des", desE.getText().toString()
        ,"type", se4.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Edit_Cat_Info.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Edit_Cat_Info.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Edit_Cat_Info.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });

    }

    public void food_cat (){
        f1.setVisibility(View.VISIBLE);
        f3.setVisibility(View.VISIBLE);
        fe2.setVisibility(View.VISIBLE);
        fe4.setVisibility(View.VISIBLE);
        desE.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);

    }
    public void clo_cat (){
        clo1.setVisibility(View.VISIBLE);
        cloe2.setVisibility(View.VISIBLE);
        desE.setVisibility(View.VISIBLE);
        r2.setVisibility(View.VISIBLE);

    }
    public void tools_cat (){
        f1.setVisibility(View.VISIBLE);
        fe2.setVisibility(View.VISIBLE);
        t1.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);
        te2.setVisibility(View.VISIBLE);
        te4.setVisibility(View.VISIBLE);
        clo1.setVisibility(View.VISIBLE);
        cloe2.setVisibility(View.VISIBLE);
        desE.setVisibility(View.VISIBLE);

    }

    public void ser_cat (){
        s1.setVisibility(View.VISIBLE);
        s3.setVisibility(View.VISIBLE);
        se2.setVisibility(View.VISIBLE);
        se4.setVisibility(View.VISIBLE);
        desE.setVisibility(View.VISIBLE);

    }
    private void uploadImg(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Categoty/" + s + "/mainImage.jpg");
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