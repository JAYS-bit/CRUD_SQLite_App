package com.example.sqliteapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sqliteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static ArrayList<UserModel>data= new ArrayList<>();
     DBHelper DB;

    public static int  updatePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DB= new DBHelper(this);

        binding.btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT=binding.name.getText().toString();
                String contactTXT= binding.contact.getText().toString();
                String dobTXT=binding.dob.getText().toString();
                data.add(new UserModel(nameTXT,contactTXT,dobTXT));

                Boolean checkInsertData =DB.insertUserData(nameTXT,contactTXT,dobTXT);
                if(checkInsertData)
                    Toast.makeText(MainActivity.this, "New entry inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "failed to Insert", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel u = new UserModel(binding.name.getText().toString(),binding.contact.getText().toString(),binding.dob.getText().toString());
                Boolean checkUpdateData =DB.updateUserData(binding.name.getText().toString(),binding.contact.getText().toString(),binding.dob.getText().toString());
                for( int i=0;i<data.size();i++){
                    if(data.get(i).equals(u)){
                        data.get(i).contact= u.contact;
                        data.get(i).dob=u.dob;
                        updatePosition=i;

                    }

                }
                if(checkUpdateData)
                    Toast.makeText(MainActivity.this, "entry updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "failed to update", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<data.size();i++){
                    if(data.get(i).name.equals(binding.name.getText().toString()))
                            data.remove(i);
                }

                Boolean checkDeleteData = DB.deleteUserData(binding.name.getText().toString());

                if(checkDeleteData)
                    Toast.makeText(MainActivity.this, "entry deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "failed to delete", Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res= DB.getUserData();
                if(res.getCount()==0) {
                    Toast.makeText(MainActivity.this, "No entry", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this,ActivityRecyclerView.class);
                startActivity(i);



               /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();*/
            }

        });


    }



}