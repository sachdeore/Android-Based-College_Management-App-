package com.rohya.collegemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity {
    EditText name, email, course, imgUrl;
    Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toast.makeText(getApplicationContext(), "In Floating", Toast.LENGTH_SHORT).show();

        name = findViewById(R.id.edtName);
        email = findViewById(R.id.edtEmail);
        course = findViewById(R.id.edtCourse);
        imgUrl = findViewById(R.id.edtUrl);

        btnadd = findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("name", name.getText().toString());
                map.put("courses", course.getText().toString());
                map.put("email", email.getText().toString());
                map.put("turl", imgUrl.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("teachers").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}