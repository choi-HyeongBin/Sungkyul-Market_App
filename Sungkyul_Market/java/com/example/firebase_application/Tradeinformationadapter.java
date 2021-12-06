package com.example.firebase_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.dialogplus.DialogPlus;


import java.util.HashMap;
import java.util.Map;

public class Tradeinformationadapter extends FirebaseRecyclerAdapter<Conditioninformation,Tradeinformationadapter.CustomViewHolder> {

    public Tradeinformationadapter(@NonNull FirebaseRecyclerOptions<Conditioninformation> options) { super(options); }

    @Override
    protected void onBindViewHolder(@NonNull final CustomViewHolder holder,final int position, @NonNull final Conditioninformation model) {

        Glide.with(holder.itemView.getContext())
                .load(model.getImv())
                .into(holder.condition_imv);

        holder.condition_postname.setText(model.getName());
        holder.condition_useremail.setText(model.getUseremail());
        holder.condition_money.setText(model.getMoney());
        holder.condition_date.setText(model.getDate());
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_list_item2,parent,false);
        return new CustomViewHolder(view);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView condition_imv;
        TextView condition_postname;
        TextView condition_money;
        TextView condition_date;
        TextView condition_useremail;

        public CustomViewHolder(@NonNull  View itemView) {
            super(itemView);
            condition_imv=itemView.findViewById(R.id.selllistitem2_imv1);
            condition_postname=itemView.findViewById(R.id.selllistitem2_tv1);
            condition_money=itemView.findViewById(R.id.selllistitem2_tv4);
            condition_date=itemView.findViewById(R.id.selllistitem2_tv3);
            condition_useremail =itemView.findViewById(R.id.selllistitem2_tv2);
        }

    }
}
