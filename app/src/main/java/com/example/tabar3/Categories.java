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
import java.util.HashMap;
import java.util.Map;

public class Categories extends AppCompatActivity {
    TextView f1 , f3 , clo1 , t1, t3, s1, s3;
    EditText fe2,fe4,cloe2,desE,te2,te4,se2,se4;
    RadioButton c1,c2,c3,cc,cc1,cc2,ch,us;
    RadioGroup r1,r2,r5;
    DocumentReference dRef;
    String arr;
    FirebaseFirestore fStore;
    String  categotyId,radio1;
    Button add_DB,mButtonChooseImage;
    FirebaseAuth mAuth;
    public Uri mImageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ImageView img,detaImg;
    private static final int PICK_IMAGE_REQUEST = 1;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Var();
         id =mAuth.getCurrentUser().getUid();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        Intent i =getIntent();
        if(i.getStringExtra("category").equals("food")){
            food_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food_cat_DB();
                    Intent i2 = new Intent(Categories.this,MainActivity.class);
                    startActivity(i2);
                }
            });
        }
        else if(i.getStringExtra("category").equals("clo")){
            clo_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clo_cat_DB();
                    Intent i2 = new Intent(Categories.this,MainActivity.class);
                    startActivity(i2);
                }
            });
        }
        else if(i.getStringExtra("category").equals("tool")){
            tools_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tool_cat_DB();
                    Intent i2 = new Intent(Categories.this,MainActivity.class);
                    startActivity(i2);
                }
            });}
        else if(i.getStringExtra("category").equals("sar")){
            ser_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ser_cat_DB();
                    Intent i2 = new Intent(Categories.this,MainActivity.class);
                    startActivity(i2);
                }
            });}
        else {
            ser_cat();
            add_DB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    other_cat_DB();
                    Intent i2 = new Intent(Categories.this,MainActivity.class);
                    startActivity(i2);
                }
            });

        }

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
    r5=findViewById(R.id.r5);
    ch=findViewById(R.id.ch);
    us=findViewById(R.id.us);
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
        radio1="??????????";
    if(c2.isChecked())
        radio1="????????";
    if(c3.isChecked())
        radio1="??????";
    if(ch.isChecked() && us.isChecked())
        arr="3";
    else if(us.isChecked())
        arr="2";
    else if (ch.isChecked())
        arr="1";

    categotyId = fStore.collection("Users").document(id).collection("category").document().getId();
    uploadImg(mImageUri);
    Map<String,Object> itemsFood = new HashMap<>();
    itemsFood.put("catId", categotyId);
    itemsFood.put("name", fe2.getText().toString());
    itemsFood.put("Des", desE.getText().toString());
    itemsFood.put("type", radio1);
    itemsFood.put("show", arr);
    itemsFood.put("numHuman", fe4.getText().toString());
    itemsFood.put("UserId", id);
    itemsFood.put("booked", false);
    itemsFood.put("bookedName", "Non");
    itemsFood.put("bookedId", "");
    itemsFood.put("typeCat","food_category");


    DocumentReference documentReference = fStore.collection("Users")
     .document(id).collection("category").document(categotyId);
    documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {
            Toast.makeText(Categories.this, "mabrooooooook", Toast.LENGTH_LONG).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull @NotNull Exception e) {
            Toast.makeText(Categories.this, "toz 3lekom", Toast.LENGTH_LONG).show();
            Toast.makeText(Categories.this, categotyId, Toast.LENGTH_LONG).show();
            Log.d("myTag", e.getMessage());

        }
    });
    radio1="";
    arr="";
}

    public void clo_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(ch.isChecked() && us.isChecked())
            arr="3";
        else if(us.isChecked())
            arr="2";
        else if (ch.isChecked())
            arr="1";

        if(cc1.isChecked())
            radio1="????????";
        if(cc.isChecked())
            radio1="????????";
        if(cc2.isChecked())
            radio1="???????? ?? ????????";

        categotyId = fStore.collection("Users").document(
                id).collection("category").document().getId();
        uploadImg(mImageUri);
        Map<String,Object> itemsFood = new HashMap<>();
        itemsFood.put("catId", categotyId);
        itemsFood.put("numclothe", cloe2.getText().toString());
        itemsFood.put("Des", desE.getText().toString());
        itemsFood.put("type", radio1);
        itemsFood.put("UserId", id);
        itemsFood.put("booked", false);
        itemsFood.put("bookedName", "Non");
        itemsFood.put("bookedId", "");
        itemsFood.put("show", arr);
        itemsFood.put("typeCat","clothe_category");

        DocumentReference documentReference = fStore.collection("Users")
                .document(id).collection("category").document(categotyId);
        documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Categories.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Categories.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Categories.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        radio1="";
        arr="";
    }
    public void tool_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(ch.isChecked() && us.isChecked())
            arr="3";
        else if(us.isChecked())
            arr="2";
        else if (ch.isChecked())
            arr="1";


        categotyId = fStore.collection("Users").document(id).collection("category").document().getId();
        uploadImg(mImageUri);
        Map<String,Object> itemsFood = new HashMap<>();
        itemsFood.put("catId", categotyId);
        itemsFood.put("toolAge", te4.getText().toString());
        itemsFood.put("Des", desE.getText().toString());
        itemsFood.put("type", te2.getText().toString());
        itemsFood.put("UserId", id);
        itemsFood.put("booked", false);
        itemsFood.put("bookedName", "Non");
        itemsFood.put("bookedId", "");
        itemsFood.put("show", arr);
        itemsFood.put("typeCat","tool_category");


        DocumentReference documentReference = fStore.collection("Users")
                .document("id").collection("category").document(categotyId);
        documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Categories.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Categories.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Categories.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        arr="";

    }
    public void ser_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(ch.isChecked() && us.isChecked())
            arr="3";
        else if(us.isChecked())
            arr="2";
        else if (ch.isChecked())
            arr="1";

        categotyId = fStore.collection("Users").document(
                id).collection("category").document().getId();
        uploadImg(mImageUri);
        Map<String,Object> itemsFood = new HashMap<>();
        itemsFood.put("catId", categotyId);
        itemsFood.put("name", se2.getText().toString());
        itemsFood.put("Des", desE.getText().toString());
        itemsFood.put("type", se4.getText().toString());
        itemsFood.put("UserId", id);
        itemsFood.put("booked", false);
        itemsFood.put("bookedName", "Non");
        itemsFood.put("show", arr);
        itemsFood.put("bookedId", "");
        itemsFood.put("typeCat","serves_category");

        DocumentReference documentReference = fStore.collection("Users")
                .document(id).collection("category").document(categotyId);
        documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Categories.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Categories.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Categories.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        arr="";
    }

    public void other_cat_DB(){
        fStore = FirebaseFirestore.getInstance();
        if(ch.isChecked() && us.isChecked())
            arr="3";
        else if(us.isChecked())
            arr="2";
        else if (ch.isChecked())
            arr="1";

        categotyId = fStore.collection("Users").document(
                id).collection("category").document().getId();
        uploadImg(mImageUri);
        Map<String,Object> itemsFood = new HashMap<>();
        itemsFood.put("catId", categotyId);
        itemsFood.put("name", se2.getText().toString());
        itemsFood.put("Des", desE.getText().toString());
        itemsFood.put("type", se4.getText().toString());
        itemsFood.put("UserId", id);
        itemsFood.put("booked", false);
        itemsFood.put("bookedName", "Non");
        itemsFood.put("bookedId", "");
        itemsFood.put("show", arr);
        itemsFood.put("typeCat","other_category");

        DocumentReference documentReference = fStore.collection("Users")
                .document(id).collection("category").document(categotyId);
        documentReference.set(itemsFood).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Categories.this, "mabrooooooook", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Categories.this, "toz 3lekom", Toast.LENGTH_LONG).show();
                Toast.makeText(Categories.this, categotyId, Toast.LENGTH_LONG).show();
                Log.d("myTag", e.getMessage());

            }
        });
        arr="";
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
        StorageReference riversRef = storageReference.child("Categoty/" + categotyId + "/mainImage.jpg");
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