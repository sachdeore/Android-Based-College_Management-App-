package com.rohya.collegemanagement;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText txt_name, txt_email, txt_password, txt_confirmPassword, txt_MasterCode;
    TextView txt_click_signIn;
    Button btn_signUp;

    private PrefrenceManager prefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_name = findViewById(R.id.inputName);
        txt_email = findViewById(R.id.inputEmail);
        txt_password = findViewById(R.id.inputPassword);
        txt_confirmPassword = findViewById(R.id.inputConfirmPassword);
        txt_click_signIn = findViewById(R.id.textSignIn);
        txt_MasterCode = findViewById(R.id.inputCode);
        btn_signUp = findViewById(R.id.buttonSignUp);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInvaldSignUpDetails())
                {
                    signUp();
                }
            }
        });

        txt_click_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SingInActivity.class);
                startActivity(i);
            }
        });

    }

    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    private boolean isInvaldSignUpDetails()
    {
        if (txt_name.getText().toString().trim().isEmpty()) {
            showToast("Enter name");
            return false;
        } else if (txt_email.getText().toString().trim().isEmpty()) {
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( txt_email.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false;
        } else if (txt_password.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else if (txt_confirmPassword.getText().toString().trim().isEmpty()) {
            showToast("Confirm your password");
            return false;
        } else if (txt_password.toString().equals(txt_confirmPassword.getText().toString())){
            showToast("Password & confirm password must be same");
            return false;
        } else if (parseInt(txt_MasterCode.getText().toString())!=0000) {
            showToast("Invalid Master Code");
            return false;
        } else
        {
            return true;
        }
    }

    private  void signUp() {

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put("Name",txt_name.getText().toString().trim());
        user.put("Email",txt_email.getText().toString());
        user.put("Password",txt_password.getText().toString().trim());
        user.put("allow", true);
        database.collection("collection")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent i = new Intent(getApplicationContext(), SingInActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast(e.getMessage());
                    }
                });


    }
}