    package com.example.tabar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class Login extends AppCompatActivity { private static final String TAG = "EmailPassword";
        // [START declare_auth]
        FirebaseAuth mAuth;
        EditText UserL,PassL;
        FirebaseUser user;
        Button LoginB;
        TextView forgetTextLink;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            UserL=findViewById(R.id.userNameL);
            PassL=findViewById(R.id.PasswordL);
            LoginB=findViewById(R.id.LoginB);
            forgetTextLink=findViewById(R.id.ResetB);

            // [START initialize_auth]
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            user=mAuth.getCurrentUser();
            ActionBar actionBar;
            actionBar = getSupportActionBar();
            ColorDrawable colorDrawable
                    = new ColorDrawable(Color.parseColor("#0060FF"));
            actionBar.setBackgroundDrawable(colorDrawable);

            // [END initialize_auth]

           }


        public void Login(View view) {
            String email= String.valueOf(UserL.getText());
            String password= String.valueOf(PassL.getText());
            if (String.valueOf(UserL.getText()).isEmpty()){
                UserL.setError("Email required!");
                return;
            }
            if (String.valueOf(PassL.getText()).isEmpty()){
                PassL.setError("password required!");
                return;
            }
            if (String.valueOf(PassL.getText()).length()<6){
                PassL.setError("Password must cantain more than 5 digits");
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                            AlertDialog.Builder PasswordResetD= new AlertDialog.Builder(Login.this);
                            PasswordResetD.setTitle("verify account ");
                            PasswordResetD.setMessage("Verify account now?");
                            PasswordResetD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                    /*Intent sendemail=new Intent(Intent.ACTION_MAIN);
                    sendemail.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(sendemail);*/
                                    FirebaseUser FUser=mAuth.getCurrentUser();
                                    FUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Login.this,"verification email sent",Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("tag","OnFailer:Email not sent"+e.getMessage());
                                        }
                                    });
                                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/"));
                                    startActivity(intent);
                                }
                            });
                            PasswordResetD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(Login.this,"succeeeeeded",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            });
                            PasswordResetD.create().show();


                    }else {
                        Toast.makeText(Login.this, "User Not Found",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        // [START on_start_check_user]
        /*@Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                reload();
            }
        }*/
        // [END on_start_check_user]

        /*private void signIn(String email, String password) {
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(Login.this,"succeeeeeeeeeeeeeded",Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]
        }*/

        /*private void sendEmailVerification() {
            // Send verification email
            // [START send_email_verification]
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Email sent
                        }
                    });
            // [END send_email_verification]
        }*/

        private void reload() { }

        private void updateUI(FirebaseUser user) {

        }


        public void MoveToS(View view) {
            if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "You already have account", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);}
            }

        public void ResetPass(View view) {
            EditText resetMail= new EditText(view.getContext());
            AlertDialog.Builder PasswordResetD= new AlertDialog.Builder(view.getContext());
            PasswordResetD.setTitle("Reset Password ?");
            PasswordResetD.setMessage("Enter your email to recieve reset link");
            PasswordResetD.setView(resetMail);
            PasswordResetD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                String mail=resetMail.getText().toString();
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                   Toast.makeText(Login.this,"Reset link sent to your email",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,"ERROR!! Reset link is not sent"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                }
            });
            PasswordResetD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            PasswordResetD.create().show();
        }
    }
