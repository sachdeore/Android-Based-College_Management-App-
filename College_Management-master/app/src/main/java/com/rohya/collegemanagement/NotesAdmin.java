package com.rohya.collegemanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotesAdmin extends AppCompatActivity {

    Button addNotes;
    EditText inputName;
    EditText inputValue;

    // Get a reference to the Firestore database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_admin);

        addNotes = findViewById(R.id.addNotes);
        inputName = findViewById(R.id.inputName);
        inputValue = findViewById(R.id.inputValue);

        addNotes.setOnClickListener(view -> {
            // Get the text entered in the EditTexts
            String name = inputName.getText().toString().trim();
            String value = inputValue.getText().toString().trim();

            // Check if the inputs are not empty
            if (!name.isEmpty() && !value.isEmpty()) {

                // Create a Map object with the data to be added
                Map<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("value", value);

                // Add a new document with a generated ID
                db.collection("notes")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(NotesAdmin.this, "Note added successfully", Toast.LENGTH_SHORT).show();
                                inputName.setText(""); // Clear the EditTexts
                                inputValue.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NotesAdmin.this, "Error adding note", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                // Show an error message if any input is empty
                Toast.makeText(this, "Please enter name and value", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
