package com.example.sqliteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

    ArrayList<UserModel>data;
    Context context;

    public MyCustomAdapter(ArrayList<UserModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_item,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameTXT.setText(data.get(position).name);
        holder.contactTXT.setText(data.get(position).contact);
        holder.dobTXT.setText(data.get(position).dob);
    }

    public void setData(ArrayList<UserModel> newData) {
        this.data = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTXT,contactTXT,dobTXT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTXT =itemView.findViewById(R.id.nameLayout);
            contactTXT=itemView.findViewById(R.id.contactLayout);
            dobTXT=itemView.findViewById(R.id.dobLayout);


        }
    }
}
