package com.example.gitrepo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// DATABASE CREATION
@Database(entities = {RepositoryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RepositoryDao repositoryDao();

    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "git-hub-repo1") //I have  Set the database name here
                .build();
    }
}
