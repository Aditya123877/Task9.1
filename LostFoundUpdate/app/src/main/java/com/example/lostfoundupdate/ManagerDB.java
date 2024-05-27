package com.example.lostfoundupdate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManagerDB extends SQLiteOpenHelper {
    private static final String POSTS_TABLE_NAME = "post";
    private static final String DB_NAME = "ManagerDB.db";
    public ManagerDB(Context context) {
        super(context, DB_NAME, null, 1);;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, description TEXT, datetime TEXT, location TEXT, type TEXT)";
        String createUsersTable = "CREATE TABLE " + POSTS_TABLE_NAME + query;
        sqLiteDatabase.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // "Post" table execution
    public Post getPost(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(POSTS_TABLE_NAME, new String[] { "id", "name", "phone", "description", "datetime", "location", "type" },
                "id=?", new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Post post = new Post(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        cursor.close();
        return post;
    }


    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + POSTS_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Post post = new Post(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6));
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }


    public Post createPost(Post newPost) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", newPost.getName());
            values.put("phone", newPost.getPhone());
            values.put("description", newPost.getDescription());
            values.put("datetime", newPost.getDate());
            values.put("location", newPost.getLocation());
            values.put("type", newPost.getType());

            long id = db.insert(POSTS_TABLE_NAME, null, values);
            newPost.setId(id);  // Assuming Post has a setId method to set the ID after insertion
            return newPost;
        } catch (Exception e) {
            Log.e("ManagerDB", "Failed to create post: " + e.getMessage());
            return null;
        }
    }

    public boolean deletePost(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(POSTS_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

}
