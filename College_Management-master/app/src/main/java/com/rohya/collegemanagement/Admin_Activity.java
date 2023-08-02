package com.rohya.collegemanagement;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Admin_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnAddData = findViewById(R.id.btnAddData);
        Button btnnotes = findViewById(R.id.btnnotes);

        btnnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotesAdmin.class);
                startActivity(i);
            }
        });
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity_2.class));
            }
        });

        Button btnSedNotify = findViewById(R.id.btnSendNotification);
        btnSedNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://dashboard.onesignal.com/apps/60287ee3-cae2-4049-a3a6-b25d0ce0eec9#outcomes=os__click__count,os__confirmed_delivery__count";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        Button btnAnnounce = findViewById(R.id.btnAnnounce);
        btnAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddAnnouncement.class));
            }
        });

        Button btnq = findViewById(R.id.btnQuiz);

        btnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddQuiz.class));
            }
        });
    }

}