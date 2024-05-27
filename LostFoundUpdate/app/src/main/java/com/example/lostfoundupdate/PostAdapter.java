package com.example.lostfoundupdate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> postList;
    ItemClickListener listener;
    public PostAdapter(List<Post> data, ItemClickListener listener) {
        this.postList = data;
        this.listener = listener;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.textView);
        }

        public void bind(Post post) {
            titleTV.setText(post.getDescription());
            titleTV.setOnClickListener(view -> listener.onItemClick(post));
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface ItemClickListener {
        void onItemClick(Post post);
    }
}
