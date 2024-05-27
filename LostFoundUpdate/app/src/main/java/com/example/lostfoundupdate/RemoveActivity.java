package com.example.lostfoundupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RemoveActivity extends AppCompatActivity {
    Post post;
    TextView TextView;
    Button RemoveBTN;
    ManagerDB managerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        post = (Post) getIntent().getSerializableExtra("Post");
        managerDB = new ManagerDB(this);

        TextView = findViewById(R.id.TextView);
        RemoveBTN = findViewById(R.id.RemoveButton);

        StringBuilder st = new StringBuilder();
        st.append("******* " + post.getType() + " *******\n\n\n");
        st.append("Phone: " + post.getPhone() + "\n\n");
        st.append("Description: " + post.getDescription() + "\n\n");
        st.append("Date: " + post.getDate() + "\n\n");
        st.append("Location: " + post.getLocation() + "\n\n");
        TextView.setText(st);

        // Setup remove button
        RemoveBTN.setEnabled(true);
        RemoveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerDB.deletePost((int) post.getId());
                TextView.setText("This post has been deleted");
                RemoveBTN.setEnabled(false);
            }
        });
    }
}