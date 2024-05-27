package com.example.lostfoundupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;

public class ShowActivity extends AppCompatActivity {
    RecyclerView PostRV;
    PostAdapter postAdapter;
    ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        managerDB = new ManagerDB(this);
        setupRecyclerview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerview();
    }

    private void setupRecyclerview() {
        PostRV = findViewById(R.id.PostRecyclerView);
        postAdapter = new PostAdapter(managerDB.getPosts(), new PostAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                Intent intent = new Intent(ShowActivity.this, RemoveActivity.class);
                intent.putExtra("Post", post);
                startActivity(intent);
            }
        });
        PostRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        PostRV.setAdapter(postAdapter);
    }
}