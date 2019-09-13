package com.caketuzz.ghbrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.activity.RepoDetailActivity;
import com.caketuzz.ghbrowser.model.Repo;

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter {

    private List<Repo> repos;

    public ReposListAdapter(List<Repo> repos){
        this.repos = repos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final Repo item = repos.get(position);
        ((ReposViewHolder)holder).repoId.setText(item.getId());
        ((ReposViewHolder)holder).repoName.setText(item.getName());
        ((ReposViewHolder)holder).repoFullName.setText(item.getFullName());
        Glide.with(holder.itemView.getContext())
                .load(item.getOwner().getAvatarUrl())
                .into(((ReposViewHolder)holder).repoAvatar);
        if (item.isFork())
            ((ReposViewHolder)holder).repoFork.setVisibility(View.VISIBLE);
        else
            ((ReposViewHolder)holder).repoFork.setVisibility(View.GONE);
        // Open Detail Activity on click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = holder.itemView.getContext();
                c.startActivity(
                        RepoDetailActivity.getIntent(c, item));
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    private class ReposViewHolder extends RecyclerView.ViewHolder{
        ImageView repoAvatar;
        TextView repoName;
        TextView repoFullName;
        TextView repoId;
        ImageView repoFork;

        public ReposViewHolder(@NonNull View itemView) {
            super(itemView);
            repoAvatar = itemView.findViewById(R.id.iv_repo_avatar);
            repoName = itemView.findViewById(R.id.tv_repo_name);
            repoFullName = itemView.findViewById(R.id.tv_repo_fullname);
            repoId = itemView.findViewById(R.id.tv_repo_id);
            repoFork = itemView.findViewById(R.id.iv_nb_fork);
        }
    }
}
