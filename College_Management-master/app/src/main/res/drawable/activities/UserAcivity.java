package com.rohya.vaartaalaap.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rohya.vaartaalaap.R;
import com.rohya.vaartaalaap.adapters.UserAdapters;
import com.rohya.vaartaalaap.databinding.ActivityUserAcivityBinding;
import com.rohya.vaartaalaap.models.User;
import com.rohya.vaartaalaap.utilities.Constants;
import com.rohya.vaartaalaap.utilities.PrefrenceManager;

import java.util.ArrayList;
import java.util.List;

public class UserAcivity extends AppCompatActivity {

    private ActivityUserAcivityBinding binding;
    private PrefrenceManager prefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAcivityBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_acivity);
        prefrenceManager = new PrefrenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void showErrorMessage()
    {
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void getUsers()
    {
        loading(true);
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_USERS)
                    .get()
                    .addOnCompleteListener(task -> {
                        loading(false);
                        String currentUserId = prefrenceManager.getString((Constants.KEY_USER_ID));
                        if(task.isSuccessful() && task.getResult() != null)
                        {
                            List<User> users = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                            {
                                if (currentUserId.equals(queryDocumentSnapshot.getId()))
                                {
                                    continue;
                                }
                                User user = new User();
                                user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                                user.emai = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                                user.image = queryDocumentSnapshot.getString(Constants.KEY_KEY_IMAGE);
                                user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                                users.add(user);
                            }
                            if (users.size() > 0)
                            {
                                UserAdapters userAdapters = new UserAdapters(users);
                                binding.userRecylerView.setAdapter(userAdapters);
                                binding.userRecylerView.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                showErrorMessage();
                            }
                        } else
                        {
                            showErrorMessage();
                        }
                    });
    }

    private void loading(Boolean isLoading)
    {
        if(isLoading)
        {
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}