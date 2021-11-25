package com.example.tabar3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintAttribute;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    public void bte(View view) {
        Intent sendemail=new Intent(Intent.ACTION_SEND);
        sendemail.setData(Uri.parse("mailto"));
        sendemail.setType("message/rfc822");
        sendemail.putExtra(Intent.EXTRA_EMAIL,"abuhmaiddania@gmail.com");
        sendemail.putExtra(Intent.EXTRA_SUBJECT,"عنوان الرسالة");
        sendemail.putExtra(Intent.EXTRA_TEXT,"موضوع الرسالة");
        startActivity(sendemail);





    }
}