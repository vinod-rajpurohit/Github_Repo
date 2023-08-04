package com.example.gitrepo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

//Interface for Rest Api EndPoints
public interface GitHubService {

      @GET("repos/{owner}/{repo}")
    Call<RepositoryResponse> getRepositoryDetails(
            @Path("owner") String owner,
            @Path("repo") String repo
    );
}
