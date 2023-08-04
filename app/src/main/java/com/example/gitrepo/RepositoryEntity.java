package com.example.gitrepo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database Tables Creation File
@Entity(tableName = "repositories")
public class RepositoryEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String owner;
    public String repoName;
    public String repoDescription;
    public String repoUrl;

    // Constructors, getters, setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    public void setRepoDescription(String repoDescription) {
        this.repoDescription = repoDescription;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }
}
