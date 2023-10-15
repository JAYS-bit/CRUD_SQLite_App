package com.example.sqliteapp;

import static com.example.sqliteapp.MainActivity.data;
import static com.example.sqliteapp.MainActivity.updatePosition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ActivityRecyclerView extends AppCompatActivity {
    RecyclerView recyclerView;
     MyCustomAdapter adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.recyclerView);
        Bundle bundle= getIntent().getExtras();
        dbHelper = new DBHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this
        ));
        adapter= new MyCustomAdapter(data,ActivityRecyclerView.this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));  // Replace 'adapter' with your adapter instance
        itemTouchHelper.attachToRecyclerView(recyclerView);





    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();
        Cursor cursor = dbHelper.getUserData();

        if (cursor != null) {
            ArrayList<UserModel> data = extractDataFromCursor(cursor);
            adapter.setData(data);
        }
    }

    private ArrayList<UserModel> extractDataFromCursor(Cursor cursor) {
        ArrayList<UserModel> data = new ArrayList<>();
        int nameIndex = cursor.getColumnIndex("name");
        int contactIndex = cursor.getColumnIndex("contact");
        int dobIndex = cursor.getColumnIndex("dob");

        if (nameIndex != -1 && contactIndex != -1 && dobIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(nameIndex);
                    String contact = cursor.getString(contactIndex);
                    String dob = cursor.getString(dobIndex);
                    UserModel item = new UserModel(name, contact, dob);
                    data.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return data;
    }

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback{

        private MyCustomAdapter adapter;
        public SwipeToDeleteCallback(MyCustomAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter.removeItem(position);
        }
    }



}

