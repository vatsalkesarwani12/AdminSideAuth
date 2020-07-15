package com.vatsal.kesarwani.adminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    Context context;
    ArrayList<DataParam> mList;

    public RequestAdapter(Context context, ArrayList<DataParam> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.email.setText(mList.get(position).getEmail());
        holder.stat.setText(mList.get(position).getStatus()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+mList.get(position).getEmail(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,AcceptReject.class);
                intent.putExtra("mail",mList.get(position).getEmail());
                intent.putExtra("pass",mList.get(position).getPassword());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView email,stat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.mail);
            stat=itemView.findViewById(R.id.stat);
        }
    }
}
