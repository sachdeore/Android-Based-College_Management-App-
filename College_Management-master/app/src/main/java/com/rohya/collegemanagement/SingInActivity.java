package com.rohya.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SingInActivity extends AppCompatActivity {

    EditText txt_email, txt_password;
    TextView txt_createAcc;
    Button btn_signIn;
    boolean allow = true;

    private PrefrenceManager prefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        txt_email = findViewById(R.id.inputEmail);
        txt_password = findViewById(R.id.inputPassword);
        txt_createAcc = findViewById(R.id.textCreateNewAccount);
        btn_signIn = findViewById(R.id.buttonSignIn);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInvalidSignUpDetails())
                {
                    sigIn();
                }
            }
        });

        txt_createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void sigIn() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("collection")
                .whereEqualTo("Email", txt_email.getText().toString().trim())
                .whereEqualTo("Password", txt_password.getText().toString().trim())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocumentChanges().size() > 0 )
                    {
                        Intent intent = new Intent(getApplicationContext(), Admin_Activity.class);
                        intent.putExtra("allow", true);
                        startActivity(intent);
                        showToast("Success Sign-in");
                    } else
                    {
                        showToast("Unable to sign in");
                    }
                });
    }
    private boolean isInvalidSignUpDetails()
    {
        if (txt_email.getText().toString().trim().isEmpty()) {
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( txt_email.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false;
        } else if (txt_password.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else
        {
            return true;
        }
    }

    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}