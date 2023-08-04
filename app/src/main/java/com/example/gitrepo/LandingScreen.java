package com.example.gitrepo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LandingScreen extends AppCompatActivity  {

    private RecyclerView repoRecyclerView;
    private List<RepositoryEntity> repositories = new ArrayList<>();
    private RepositoryAdapter repositoryAdapter;
    private TextView textNoDataFound;

    private Button Addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        textNoDataFound = findViewById(R.id.textTrackFavoriteRepo);
        Addbtn= findViewById(R.id.action_add);



        repoRecyclerView = findViewById(R.id.repoRecyclerView);

        repositoryAdapter = new RepositoryAdapter(repositories);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repoRecyclerView.setAdapter(repositoryAdapter);



        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingScreen.this, AddRepoActivity.class);
                startActivity(intent);
            }
        });

        repositoryAdapter.setOnItemClickListener(new RepositoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    String repoUrl = repositories.get(position).repoUrl;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl));
                    startActivity(browserIntent);

            }
        });

        // Handling share button click to share repo details
        repositoryAdapter.setOnItemClickListener(new RepositoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String repoName = repositories.get(position).repoName;
                String repoUrl = repositories.get(position).repoUrl;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this GitHub repo!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Repository: " + repoName + "\nURL: " + repoUrl);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });



        if (repositories.isEmpty()) {
            textNoDataFound.setVisibility(View.VISIBLE);
        } else {
            textNoDataFound.setVisibility(View.INVISIBLE);
        }


        loadRepositoriesFromRoom();


        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AddRepoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRepositoriesFromRoom() {

        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "git-hub-repo1").build();

        // Storing  the appDatabase in the application context for access in other activities
        ((MyApplication) getApplication()).setAppDatabase(appDatabase);

        new Thread(new Runnable() {
            @Override
            public void run() {

                // Loading  repositories from the database using RepositoryDao
                List<RepositoryEntity> loadedRepositories = appDatabase.repositoryDao().getAllRepositories();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        repositories.clear();
                        repositories.addAll(loadedRepositories);
                        repositoryAdapter.notifyDataSetChanged();
                        if (repositories.isEmpty()) {
                            textNoDataFound.setVisibility(View.VISIBLE);
                        } else {
                            textNoDataFound.setVisibility(View.INVISIBLE);
                        }

                        // Close the database
                        appDatabase.close();

                    }
                });
            }
        }).start();
      }





}
