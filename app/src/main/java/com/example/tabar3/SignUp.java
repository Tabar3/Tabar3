package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    FirebaseAuth mAuth;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    public Uri mImageUri;
    ImageView img;
    FirebaseFirestore fStore;
    EditText UserS,passS,UserName,PhoneN,Location;
    Button signUpB , mButtonChooseImage;
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
        img=findViewById(R.id.imgChar);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(this,"You already have account",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }
    public void AddToDB() {
        Toast.makeText(SignUp.this, "khaaaaaaaaaaaara", Toast.LENGTH_SHORT).show();
        fStore = FirebaseFirestore.getInstance();
        if (ChC.isChecked()){
        CharId = FirebaseAuth.getInstance().getUid();
        Map<String, Object> CharUsers = new HashMap<>();
        CharUsers.put("charityId", CharId);
        CharUsers.put("charityEmail", UserS.getText().toString());
        CharUsers.put("charityPhone", PhoneN.getText().toString());
        CharUsers.put("charityName", UserName.getText().toString());
        CharUsers.put("charityLoc",Location.getText().toString());
        CharUsers.put("charityDes",CharId);
            CharUsers.put("typeOfUser","Char");

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
        });
            uploadImgChar(mImageUri);
        }
        if (DonC.isChecked()){
            UsersId = FirebaseAuth.getInstance().getUid();
            Map<String, Object> DonUsers = new HashMap<>();
            DonUsers.put("UserId", UsersId);
            DonUsers.put("UserEmail", UserS.getText().toString());
            DonUsers.put("UserPhone", PhoneN.getText().toString());
            DonUsers.put("UserName", UserName.getText().toString());
            DonUsers.put("UserLoc",Location.getText().toString());
            DonUsers.put("typeOfUser","User");
            DocumentReference documentReference2=fStore.collection("Users").document(UsersId);
            documentReference2.set(DonUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(SignUp.this, "yaaaaaa rab", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this, "Noooooooooooooooo", Toast.LENGTH_SHORT).show();
                }
            });
            //Toast.makeText(SignUp.this, "hiiiiiiiiiiiiiiiiiiiiiiiii", Toast.LENGTH_SHORT).show();
            //uploadImgUser(mImageUri);
        }

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

    private void uploadImgChar(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("Charities/" + CharId + "/mainImage.jpg");
        riversRef.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               // pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // pd.dismiss();
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
    private void uploadImgUser(Uri uri) {

        final ProgressDialog pd = new ProgressDialog((this));
        pd.setTitle("Uploading Image ...");
        pd.show();

        //final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("User/" + CharId + "/mainImage.jpg");
        riversRef.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
               // pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // pd.dismiss();
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