package com.example.gitrepo;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<RepositoryEntity> repositories;
    private OnItemClickListener listener;


    public RepositoryAdapter(List<RepositoryEntity> repositories) {
        this.repositories = repositories;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Constructor and other methods...

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RepositoryEntity currentRepo = repositories.get(position);
        holder.textRepoName.setText(currentRepo.repoName);
        holder.textRepoDescription.setText(currentRepo.repoDescription);

        holder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        TextView textRepoName;
        TextView textRepoDescription;
        ImageButton buttonShare;

        public RepositoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textRepoName = itemView.findViewById(R.id.textRepoName);
            textRepoDescription = itemView.findViewById(R.id.textRepoDescription);
            buttonShare = itemView.findViewById(R.id.buttonShare);
        }
    }
}
