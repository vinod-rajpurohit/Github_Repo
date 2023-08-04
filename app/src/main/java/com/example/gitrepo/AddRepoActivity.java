package com.example.gitrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRepoActivity extends AppCompatActivity {

    private EditText editTextOwner, editTextRepoName,editTextRepoDesc;
    private Button buttonAdd;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);

        editTextOwner = findViewById(R.id.editTextOwner);
        editTextRepoName = findViewById(R.id.editTextRepoName);
        buttonAdd = findViewById(R.id.buttonAdd);
        editTextRepoDesc = findViewById(R.id.editTextRepodesc);
        progressBar = findViewById(R.id.progressBar);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owner = editTextOwner.getText().toString().trim();;
                String repoName = editTextRepoName.getText().toString().trim();
                String description = editTextRepoDesc.getText().toString().trim();
                if (!owner.isEmpty() && !repoName.isEmpty() && !description.isEmpty()) {}
                    else {
                    Toast.makeText(getApplicationContext(),"Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                Log.d("Tracking","Working Fine");

                progressBar.setVisibility(View.VISIBLE);

                GitHubService gitHubService = RetrofitClientInstance.getRetrofitInstance().create(GitHubService.class);
                Call<RepositoryResponse> call = gitHubService.getRepositoryDetails(owner, repoName);

                call.enqueue(new Callback<RepositoryResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<RepositoryResponse> call,
                                           @NonNull Response<RepositoryResponse> response) {


                        Log.d("RESPONSE",response.toString());
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {

                            RepositoryResponse repository = response.body();

                            // Save repository details to Room
                            RepositoryEntity entity = new RepositoryEntity();
                            entity.owner = owner;
                            entity.repoName = repoName;
                            assert repository != null;
                            entity.repoDescription = description;
                            entity.repoUrl = repository.html_url;
                            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();


                            // Get the existing Room database instance from the application context
                            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

// Using separate thread for database operations
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Insert the entity into the database
                                    appDatabase.repositoryDao().insertRepository(entity);

                                    // Navigate to LandingActivity
                                    Intent intent = new Intent(AddRepoActivity.this, LandingScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).start();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Project", Toast.LENGTH_LONG).show();
                            // Handle API call failure
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RepositoryResponse> call, @NonNull Throwable t) {
                        // Handle API call failure when I get NO response

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Network Issue or Server Error", Toast.LENGTH_LONG).show();


                    }
                });
            }
        });

    }
}