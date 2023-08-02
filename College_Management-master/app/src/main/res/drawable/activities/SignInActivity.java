package com.rohya.vaartaalaap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rohya.vaartaalaap.databinding.ActivitySignInBinding;
import com.rohya.vaartaalaap.utilities.Constants;
import com.rohya.vaartaalaap.utilities.PrefrenceManager;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PrefrenceManager prefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefrenceManager = new PrefrenceManager(getApplicationContext());
        if (prefrenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN))
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners()
    {
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(v -> {
            if(isValidSignInDetails())
            {
                sigIn();
            }
        });
    }

    private void sigIn()
    {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, binding.inputEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocumentChanges().size() > 0 )
                   {
                       DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                       prefrenceManager.putBooleans(Constants.KEY_IS_SIGNED_IN, true);
                       prefrenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                       prefrenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                       prefrenceManager.putString(Constants.KEY_KEY_IMAGE, documentSnapshot.getString(Constants.KEY_KEY_IMAGE));
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                   } else
                   {
                        loading(false);
                        showToast("Unable to sign in");
                   }
                });
    }

    private void loading(Boolean isLoading)
    {
        if (isLoading)
        {
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails()
    {
        if(binding.inputEmail.getText().toString().trim().isEmpty())
        {
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false;
        } else if (binding.inputPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else
        {
            return true;
        }
    }

}